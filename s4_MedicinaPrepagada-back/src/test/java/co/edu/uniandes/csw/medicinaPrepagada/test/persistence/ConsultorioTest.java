/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.medicinaPrepagada.test.persistence;

import co.edu.uniandes.csw.medicinaPrepagada.entities.ConsultorioEntity;
import co.edu.uniandes.csw.medicinaPrepagada.persistence.ConsultorioPersistence;
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
public class ConsultorioTest 
{
    
    
    
    @Inject
    private ConsultorioPersistence consultorioPersistence;

    @PersistenceContext
    private EntityManager em;

    @Inject
    UserTransaction utx;

    private List<ConsultorioEntity> data = new ArrayList<>();
    
    
     /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() 
    {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(ConsultorioEntity.class.getPackage())
                .addPackage(ConsultorioPersistence.class.getPackage())
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
        em.createQuery("delete from ConsultorioEntity").executeUpdate();
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
            ConsultorioEntity entity = factory.manufacturePojo(ConsultorioEntity.class);

            em.persist(entity);
            data.add(entity);
        }
    }
    
    
    
        //TESTS
    
     /**
     * Prueba para crear una Consultorio.
     */
    @Test
    public void createConsultorioTest() 
    {
        PodamFactory factory = new PodamFactoryImpl();
        ConsultorioEntity newEntity = factory.manufacturePojo(ConsultorioEntity.class);
        ConsultorioEntity result = consultorioPersistence.create(newEntity);

        Assert.assertNotNull(result);

        ConsultorioEntity entity = em.find(ConsultorioEntity.class, result.getId());
        //Test Atributos
        Assert.assertEquals(newEntity.getEdificio(), entity.getEdificio());
        Assert.assertEquals(newEntity.getNOficina(), entity.getNOficina());
        
        //Test relaciones
        Assert.assertEquals(newEntity.getEspecialidad(), entity.getEspecialidad());
        Assert.assertEquals(newEntity.getSede(), entity.getSede());
        Assert.assertEquals(newEntity.getHorariosAtencion().size(), entity.getHorariosAtencion().size());
        for (int i=0;i<newEntity.getHorariosAtencion().size();i++)
        {
            Assert.assertEquals(newEntity.getHorariosAtencion().get(i), entity.getHorariosAtencion().get(i));
        }
    
    }
    
    
    
        
     /**
     * Prueba para consultar la lista de Consultorios.
     */
    @Test
    public void getConsultoriosTest() 
    {
        List<ConsultorioEntity> list = consultorioPersistence.findAll();
        Assert.assertEquals(data.size(), list.size());
        for (ConsultorioEntity ent : list)
        {
            boolean found = false;
            for (ConsultorioEntity entity : data) 
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
     * Prueba para consultar un Consultorio.
     */
    @Test
    public void getConsultorioTest()
    {
        ConsultorioEntity entity = data.get(0);
        ConsultorioEntity newEntity = consultorioPersistence.find(entity.getId());
        Assert.assertNotNull(newEntity);
        //Test Atributos
        Assert.assertEquals(newEntity.getEdificio(), entity.getEdificio());
        Assert.assertEquals(newEntity.getNOficina(), entity.getNOficina());
        
        //Test relaciones
        Assert.assertEquals(newEntity.getEspecialidad(), entity.getEspecialidad());
        Assert.assertEquals(newEntity.getSede(), entity.getSede());
        Assert.assertEquals(newEntity.getHorariosAtencion().size(), entity.getHorariosAtencion().size());
        for (int i=0;i<newEntity.getHorariosAtencion().size();i++)
        {
            Assert.assertEquals(newEntity.getHorariosAtencion().get(i), entity.getHorariosAtencion().get(i));
        }

    }
    
    
           /**
     * Prueba para actualizar un Consultorio.
     */
    @Test
    public void updateConsultorioTest()
    {
        ConsultorioEntity entity = data.get(0);
        PodamFactory factory = new PodamFactoryImpl();
        ConsultorioEntity newEntity = factory.manufacturePojo(ConsultorioEntity.class);
        newEntity.setId(entity.getId());
        consultorioPersistence.update(newEntity);
        ConsultorioEntity resp = em.find(ConsultorioEntity.class, entity.getId());
        
        
                //Test Atributos
        Assert.assertEquals(newEntity.getEdificio(), resp.getEdificio());
        Assert.assertEquals(newEntity.getNOficina(), resp.getNOficina());
        
        
        //Test relaciones
        Assert.assertEquals(newEntity.getEspecialidad(), resp.getEspecialidad());
        Assert.assertEquals(newEntity.getSede(), resp.getSede());
        Assert.assertEquals(newEntity.getHorariosAtencion().size(), resp.getHorariosAtencion().size());
        for (int i=0;i<newEntity.getHorariosAtencion().size();i++)
        {
            Assert.assertEquals(newEntity.getHorariosAtencion().get(i), resp.getHorariosAtencion().get(i));
        }
        
    }
    
    
        
     /**
     * Prueba para eliminar un Consultorio.
     */
    @Test
    public void deleteConsultorioTest() 
    {
        ConsultorioEntity entity = data.get(0);
        consultorioPersistence.delete(entity.getId());
        ConsultorioEntity deleted = em.find(ConsultorioEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }
    
}
