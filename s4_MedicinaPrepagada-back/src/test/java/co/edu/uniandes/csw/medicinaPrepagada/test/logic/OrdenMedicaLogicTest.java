/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.medicinaPrepagada.test.logic;

import co.edu.uniandes.csw.medicinaPrepagada.ejb.OrdenMedicaLogic;
import co.edu.uniandes.csw.medicinaPrepagada.entities.OrdenMedicaEntity;
import co.edu.uniandes.csw.medicinaPrepagada.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.medicinaPrepagada.persistence.OrdenMedicaPersistence;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
 * @author ni.ramirez10
 */

@RunWith(Arquillian.class)
public class OrdenMedicaLogicTest 
{
    private PodamFactory factory = new PodamFactoryImpl();

    @Inject
    private OrdenMedicaLogic ordenLogic;

    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;

    private List<OrdenMedicaEntity> data = new ArrayList<>();
    
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
                .addPackage(OrdenMedicaLogic.class.getPackage())
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
        for (int i = 0; i < 3; i++) 
        {
            OrdenMedicaEntity entity = factory.manufacturePojo(OrdenMedicaEntity.class);
            em.persist(entity);
            data.add(entity);
        }
    }
    
     /**
     * Prueba para crear una orden medica
     * @throws co.edu.uniandes.csw.medicinaPrepagada.exceptions.BusinessLogicException
     */
    
    @Test
    public void createOrdenMedicaTest() throws BusinessLogicException
    {
        OrdenMedicaEntity newEntity = factory.manufacturePojo(OrdenMedicaEntity.class);
        OrdenMedicaEntity result = ordenLogic.createOrdenMedica(newEntity);
        Assert.assertNotNull(result);
        OrdenMedicaEntity entity = em.find(OrdenMedicaEntity.class, result.getId());
    }
    
    /**
     * Prueba para consultar la lista de ordenes medicas.
     */
    
    @Test
    public void getOrdenesMedicasTest() 
    {
        List<OrdenMedicaEntity> list = ordenLogic.getOrdenesMedicas();
        Assert.assertEquals(data.size(), list.size());
        
        for (OrdenMedicaEntity entity : list) 
        {
            boolean found = false;
            
            for (OrdenMedicaEntity storedEntity : data) 
            {
                if (entity.getId().equals(storedEntity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }
    
    /**
     * Prueba para consultar una ordne medica.
     */
    
    @Test
    public void getOrdenMedicaTest() 
    {
        OrdenMedicaEntity entity = data.get(0);
        OrdenMedicaEntity resultEntity = ordenLogic.getOrdenMedica(entity.getId());
        Assert.assertNotNull(resultEntity);
        Assert.assertEquals(entity.getId(), resultEntity.getId());
    }
    
    /**
     * Prueba para actualizar una orden medica.
     */
    
    @Test
    public void updateOrdenMedicaTest()
    {
        try {
            OrdenMedicaEntity entity = data.get(0);
            OrdenMedicaEntity pojoEntity = factory.manufacturePojo(OrdenMedicaEntity.class);
            
            pojoEntity.setId(entity.getId());
            
            ordenLogic.updateOrdenMedica(pojoEntity.getId(), pojoEntity);
            
            OrdenMedicaEntity resp = em.find(OrdenMedicaEntity.class, entity.getId());
            
            Assert.assertEquals(pojoEntity.getId(), resp.getId());
        } catch (BusinessLogicException ex) {
            Logger.getLogger(OrdenMedicaLogicTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Prueba para eliminar una orden medica
     * @throws co.edu.uniandes.csw.medicinaPrepagada.exceptions.BusinessLogicException
     */
    
    @Test
    public void deleteOrdenMedicaTest() throws BusinessLogicException 
    {
        OrdenMedicaEntity entity = data.get(0);
        ordenLogic.deleteOrdenMedica(entity.getId());
        OrdenMedicaEntity deleted = em.find(OrdenMedicaEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }
    
    
    
}
