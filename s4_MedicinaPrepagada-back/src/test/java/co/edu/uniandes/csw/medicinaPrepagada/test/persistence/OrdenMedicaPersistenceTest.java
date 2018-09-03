/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.medicinaPrepagada.test.persistence;

import co.edu.uniandes.csw.medicinaPrepagada.entities.OrdenMedicaEntity;
import co.edu.uniandes.csw.medicinaPrepagada.persistence.OrdenMedicaPersistence;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

/**
 *
 * @author estudiante
 */
public class OrdenMedicaPersistenceTest 
{
    @Inject
    private OrdenMedicaPersistence ordenPersistence;

    @PersistenceContext
    private EntityManager em;

    @Inject
    UserTransaction utx;

    private List<OrdenMedicaEntity> data = new ArrayList<OrdenMedicaEntity>();
    
    /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    
    @Deployment
    public static JavaArchive createDeployment() 
    {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(OrdenMedicaEntity.class.getPackage())
                .addPackage(OrdenMedicaPersistence.class.getPackage())
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
            catch (Exception e1) 
            {
                e1.printStackTrace();
            }
        }
    }

    /**
     * Limpia las tablas que están implicadas en la prueba.
     */
    
    private void clearData() 
    {
        em.createQuery("delete from OrdenMedicaEntity").executeUpdate();
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
            OrdenMedicaEntity entity = factory.manufacturePojo(OrdenMedicaEntity.class);
            em.persist(entity);
            data.add(entity);
        }
    }
    
    /**
     * Prueba para crear una orden medica.
     */
    
    @Test
    public void createOrdenMedicaTest() 
    {
        PodamFactory factory = new PodamFactoryImpl();
        OrdenMedicaEntity newEntity = factory.manufacturePojo(OrdenMedicaEntity.class);
        OrdenMedicaEntity result = ordenPersistence.create(newEntity);

        Assert.assertNotNull(result);

        OrdenMedicaEntity entity = em.find(OrdenMedicaEntity.class, result.getId());

        Assert.assertEquals(newEntity.getId(), entity.getId());
    }
    
    /**
     * Prueba para consultar la lista de ordenes medicas.
     */
    
    @Test
    public void getOrdenesMedicasTest() 
    {
        List<OrdenMedicaEntity> list = ordenPersistence.findAll();
        Assert.assertEquals(data.size(), list.size());
        
        for (OrdenMedicaEntity ent : list) 
        {
            boolean found = false;
            
            for (OrdenMedicaEntity entity : data) 
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
     * Prueba para consultar una orden medica.
     */
    
    @Test
    public void getOrdenMedicaTest() 
    {
        OrdenMedicaEntity entity = data.get(0);
        OrdenMedicaEntity newEntity = ordenPersistence.find(entity.getId());
        Assert.assertNotNull(newEntity);
        Assert.assertEquals(entity.getId(), newEntity.getId());
    }
    
    /**
     * Prueba para eliminar una orden medica.
     */
    
    @Test
    public void deleteOrdenMedicaTest() 
    {
        OrdenMedicaEntity entity = data.get(0);
        ordenPersistence.delete(entity.getId());
        OrdenMedicaEntity deleted = em.find(OrdenMedicaEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }
    
    /**
     * Prueba para actualizar una orden medica.
     */
    
    @Test
    public void updateOrdenMedicaTest() 
    {
        OrdenMedicaEntity entity = data.get(0);
        PodamFactory factory = new PodamFactoryImpl();
        OrdenMedicaEntity newEntity = factory.manufacturePojo(OrdenMedicaEntity.class);

        newEntity.setId(entity.getId());

        ordenPersistence.update(newEntity);

        OrdenMedicaEntity resp = em.find(OrdenMedicaEntity.class, entity.getId());

        Assert.assertEquals(newEntity.getId(), resp.getId());
    }
    
}
