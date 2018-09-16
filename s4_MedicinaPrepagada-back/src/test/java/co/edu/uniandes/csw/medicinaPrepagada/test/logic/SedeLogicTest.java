/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.medicinaPrepagada.test.logic;

import co.edu.uniandes.csw.medicinaPrepagada.ejb.SedeLogic;
import co.edu.uniandes.csw.medicinaPrepagada.entities.ConsultorioEntity;
import co.edu.uniandes.csw.medicinaPrepagada.entities.SedeEntity;
import co.edu.uniandes.csw.medicinaPrepagada.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.medicinaPrepagada.persistence.SedePersistence;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

/**
 *
 * @author Simon Guzman
 */
@RunWith(Arquillian.class)
public class SedeLogicTest 
{
   
    private PodamFactory factory = new PodamFactoryImpl();

    @Inject
    private SedeLogic sedeLogic;

    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;

    private List<SedeEntity> data = new ArrayList<>();
    
    /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() 
    {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(SedeEntity.class.getPackage())
                .addPackage(SedeLogic.class.getPackage())
                .addPackage(SedePersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }

    /**
     * Configuración inicial de la prueba.
     */
    @Before
    public void configTest() {
        try {
            utx.begin();
            clearData();
            insertData();
            utx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                utx.rollback();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }
    
        /**
     * Limpia las tablas que están implicadas en la prueba.
     */
    private void clearData() 
    {
        em.createQuery("delete from SedeEntity").executeUpdate();
        em.createQuery("delete from ConsultorioEntity").executeUpdate();
        
    }
    
    
    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        for (int i = 0; i < 3; i++) 
        {
            SedeEntity entity = factory.manufacturePojo(SedeEntity.class);
            em.persist(entity);
            entity.setConsultorios(new ArrayList<>());
            data.add(entity);
        }
        SedeEntity sede = data.get(0);
        ConsultorioEntity entity = factory.manufacturePojo(ConsultorioEntity.class);
        //entity.setSede(sede);
        em.persist(entity);
        sede.getConsultorios().add(entity);
        em.merge(sede);
        
        SedeEntity sede2 = data.get(1);
        ConsultorioEntity entity2 = factory.manufacturePojo(ConsultorioEntity.class);
        //entity.setSede(sede2);
        em.persist(entity2);
        sede2.getConsultorios().add(entity2);
        em.merge(sede2);

        
    }
    
    
        /**
     * Prueba para crear un Sede.
     * @throws co.edu.uniandes.csw.medicinaPrepagada.exceptions.BusinessLogicException
     */
    @Test
    public void createSedeTest() throws BusinessLogicException
    {
        SedeEntity newEntity = factory.manufacturePojo(SedeEntity.class);
        newEntity.setTelefono(876543987L);
        newEntity.setDireccion("Calle 120 # 13 - 22");
        newEntity.setTipoSede(2);
        newEntity.setLatitud(1.0);
        newEntity.setLongitud(-70.3);
        newEntity.setCorreo("pepito@gmail.com");
        SedeEntity result = sedeLogic.createSede(newEntity);
        Assert.assertNotNull(result);
        SedeEntity entity = em.find(SedeEntity.class, result.getId());
        Assert.assertEquals(newEntity.getId(), entity.getId());
        
                //Test Atributos
//        Assert.assertEquals(newEntity.getNombre(), entity.getNombre());
//        Assert.assertEquals(newEntity.getDireccion(), entity.getDireccion());
//        Assert.assertEquals(newEntity.getTipoSede(), entity.getTipoSede());
//        Assert.assertEquals(newEntity.getDescripcion(), entity.getDescripcion());
//        Assert.assertEquals(newEntity.getLatitud(), entity.getLatitud(), 0);
//        Assert.assertEquals(newEntity.getLongitud(), entity.getLongitud(),0);
//        Assert.assertEquals(newEntity.getTelefono(), entity.getTelefono());
//        Assert.assertEquals(newEntity.getCorreo(), entity.getCorreo());
//        //Test relaciones
//        Assert.assertEquals(newEntity.getConsultorios().size(), entity.getConsultorios().size());
//        for (int i=0;i<newEntity.getConsultorios().size();i++)
//        {
//            Assert.assertEquals(newEntity.getConsultorios().get(i), entity.getConsultorios().get(i));
//        }
    }
    
    
    
    
    
    
        /**
     * Prueba para consultar la lista de Sedes.
     */
    @Test
    public void getSedesTest() 
    {
        List<SedeEntity> list = sedeLogic.getSedes();
        Assert.assertEquals(data.size(), list.size());
        for (SedeEntity entity : list) 
        {
            boolean found = false;
            for (SedeEntity storedEntity : data) 
            {
                if (entity.getId().equals(storedEntity.getId()))
                {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }
    
    
    
        /**
     * Prueba para consultar un Sede.
     */
    @Test
    public void getSedeTest() 
    {
        SedeEntity entity = data.get(0);
        SedeEntity resultEntity = sedeLogic.getSede(entity.getId());
        Assert.assertNotNull(resultEntity);
        Assert.assertEquals(entity.getId(), resultEntity.getId());
        
        
        Assert.assertEquals(entity.getNombre(), resultEntity.getNombre());
        Assert.assertEquals(entity.getDireccion(), resultEntity.getDireccion());
        Assert.assertEquals(entity.getTipoSede(), resultEntity.getTipoSede());
        Assert.assertEquals(entity.getDescripcion(), resultEntity.getDescripcion());
        Assert.assertEquals(entity.getLatitud(), resultEntity.getLatitud(), 0);
        Assert.assertEquals(entity.getLongitud(), resultEntity.getLongitud(),0);
        Assert.assertEquals(entity.getTelefono(), resultEntity.getTelefono());
        Assert.assertEquals(entity.getCorreo(), resultEntity.getCorreo());
        
    }
    
    
    
    
    
    
    
    
    
    
    
    
        /**
     * Prueba para eliminar un Sede
     *
     * @throws co.edu.uniandes.csw.bookstore.exceptions.BusinessLogicException
     */
    @Test
    public void deleteSedeTest() throws BusinessLogicException 
    {
        SedeEntity entity = data.get(0);
        sedeLogic.deleteSede(entity.getId());
        SedeEntity deleted = em.find(SedeEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }
    
}
