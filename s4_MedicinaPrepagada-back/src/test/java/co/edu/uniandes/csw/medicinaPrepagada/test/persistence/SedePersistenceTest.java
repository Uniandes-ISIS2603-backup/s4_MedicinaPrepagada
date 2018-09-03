/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.medicinaPrepagada.test.persistence;

import co.edu.uniandes.csw.medicinaPrepagada.entities.SedeEntity;
import co.edu.uniandes.csw.medicinaPrepagada.persistence.SedePersistence;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import org.junit.Assert;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
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
public class SedePersistenceTest 
{
    
    @Inject
    private SedePersistence sedePersistence;

    @PersistenceContext
    private EntityManager em;

    @Inject
    UserTransaction utx;

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
                .addPackage(SedePersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }
    
    
    /**
     * Configuración inicial de la prueba.
     */
    @Before
    public void configTest() 
    {
        try 
        {
            utx.begin();
            em.joinTransaction();
            clearData();
            insertData();
            utx.commit();
        } 
        catch (Exception e)
        {
            e.printStackTrace();
            try 
            {
                utx.rollback();
            } 
            catch (Exception e1) {
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
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() 
    {
        PodamFactory factory = new PodamFactoryImpl();
        for (int i = 0; i < 3; i++) 
        {
            SedeEntity entity = factory.manufacturePojo(SedeEntity.class);

            em.persist(entity);
            data.add(entity);
        }
    }
    
    //TESTS
    
     /**
     * Prueba para crear una Sede.
     */
    @Test
    public void createSedeTest() 
    {
        PodamFactory factory = new PodamFactoryImpl();
        SedeEntity newEntity = factory.manufacturePojo(SedeEntity.class);
        SedeEntity result = sedePersistence.create(newEntity);

        Assert.assertNotNull(result);

        SedeEntity entity = em.find(SedeEntity.class, result.getId());
        //Test Atributos
        Assert.assertEquals(newEntity.getNombre(), entity.getNombre());
        Assert.assertEquals(newEntity.getDireccion(), entity.getDireccion());
        Assert.assertEquals(newEntity.getTipoSede(), entity.getTipoSede());
        Assert.assertEquals(newEntity.getDescripcion(), entity.getDescripcion());
        Assert.assertEquals(newEntity.getLatitud(), entity.getLatitud(), 0);
        Assert.assertEquals(newEntity.getLongitud(), entity.getLongitud(),0);
        Assert.assertEquals(newEntity.getTelefono(), entity.getTelefono());
        Assert.assertEquals(newEntity.getCorreo(), entity.getCorreo());
        //Test relaciones
        Assert.assertEquals(newEntity.getConsultorios().size(), entity.getConsultorios().size());
        for (int i=0;i<newEntity.getConsultorios().size();i++)
        {
            Assert.assertEquals(newEntity.getConsultorios().get(i), entity.getConsultorios().get(i));
        }

    }
    
    
        /**
     * Prueba para consultar la lista de Sedes.
     */
    @Test
    public void getSedesTest() 
    {
        List<SedeEntity> list = sedePersistence.findAll();
        Assert.assertEquals(data.size(), list.size());
        for (SedeEntity ent : list)
        {
            boolean found = false;
            for (SedeEntity entity : data) 
            {
                if (ent.getId().equals(entity.getId())) 
                
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
        SedeEntity newEntity = sedePersistence.find(entity.getId());
        Assert.assertNotNull(newEntity);
        //Test Atributos
        Assert.assertEquals(newEntity.getNombre(), entity.getNombre());
        Assert.assertEquals(newEntity.getDireccion(), entity.getDireccion());
        Assert.assertEquals(newEntity.getTipoSede(), entity.getTipoSede());
        Assert.assertEquals(newEntity.getDescripcion(), entity.getDescripcion());
        Assert.assertEquals(newEntity.getLatitud(), entity.getLatitud(), 0);
        Assert.assertEquals(newEntity.getLongitud(), entity.getLongitud(),0);
        Assert.assertEquals(newEntity.getTelefono(), entity.getTelefono());
        Assert.assertEquals(newEntity.getCorreo(), entity.getCorreo());
        //Test Relaciones
        Assert.assertEquals(newEntity.getConsultorios().size(), entity.getConsultorios().size());
        for (int i=0;i<newEntity.getConsultorios().size();i++)
        {
            Assert.assertEquals(newEntity.getConsultorios().get(i), entity.getConsultorios().get(i));
        }

    }
    
    
        /**
     * Prueba para actualizar un Sede.
     */
    @Test
    public void updateSedeTest()
    {
        SedeEntity entity = data.get(0);
        PodamFactory factory = new PodamFactoryImpl();
        SedeEntity newEntity = factory.manufacturePojo(SedeEntity.class);
        newEntity.setId(entity.getId());
        sedePersistence.update(newEntity);
        SedeEntity resp = em.find(SedeEntity.class, entity.getId());
        Assert.assertEquals(newEntity.getNombre(), resp.getNombre());
        Assert.assertEquals(newEntity.getDireccion(), resp.getDireccion());
        Assert.assertEquals(newEntity.getTipoSede(), resp.getTipoSede());
        Assert.assertEquals(newEntity.getDescripcion(), resp.getDescripcion());
        Assert.assertEquals(newEntity.getLatitud(), resp.getLatitud(), 0);
        Assert.assertEquals(newEntity.getLongitud(), resp.getLongitud(),0);
        Assert.assertEquals(newEntity.getTelefono(), resp.getTelefono());
        Assert.assertEquals(newEntity.getCorreo(), resp.getCorreo());
        //Test Relaciones
        Assert.assertEquals(newEntity.getConsultorios().size(), resp.getConsultorios().size());
        for (int i=0;i<newEntity.getConsultorios().size();i++)
        {
            Assert.assertEquals(newEntity.getConsultorios().get(i), resp.getConsultorios().get(i));
        }
        
    }
    
     /**
     * Prueba para eliminar un Sede.
     */
    @Test
    public void deleteSedeTest() 
    {
        SedeEntity entity = data.get(0);
        sedePersistence.delete(entity.getId());
        SedeEntity deleted = em.find(SedeEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }
    
    
}
