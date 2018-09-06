/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.medicinaPrepagada.test.persistence;

import co.edu.uniandes.csw.medicinaPrepagada.entities.AdministradorEntity;
import co.edu.uniandes.csw.medicinaPrepagada.persistence.AdministradorPersistence;
import static java.time.Clock.system;
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
 * @author ni.ramirez10
 */

@RunWith(Arquillian.class)
public class AdministradorPersistenceTest 
{
    @Inject
    private AdministradorPersistence admiPersistence;

    @PersistenceContext
    private EntityManager em;

    @Inject
    UserTransaction utx;

    private List<AdministradorEntity> data = new ArrayList<>();
    
    /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    
    @Deployment
    public static JavaArchive createDeployment() 
    {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(AdministradorEntity.class.getPackage())
                .addPackage(AdministradorPersistence.class.getPackage())
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
        em.createQuery("delete from AdministradorEntity").executeUpdate();
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
            AdministradorEntity entity = factory.manufacturePojo(AdministradorEntity.class);
            em.persist(entity);
            data.add(entity);
        }
    }
    
    /**
     * Prueba para crear un administrador.
     */
    
    @Test
    public void createAdministradorTest() 
    {
        PodamFactory factory = new PodamFactoryImpl();
        AdministradorEntity newEntity = factory.manufacturePojo(AdministradorEntity.class);
        AdministradorEntity result = admiPersistence.create(newEntity);

        Assert.assertNotNull(result);

        AdministradorEntity entity = em.find(AdministradorEntity.class, result.getCedula());

        Assert.assertEquals(newEntity.getCedula(), entity.getCedula());
    }
    
    /**
     * Prueba para consultar la lista de administradores.
     */
    
    @Test
    public void getAdministradoresTest() 
    {
        List<AdministradorEntity> list = admiPersistence.findAll();
        Assert.assertEquals(data.size(), list.size());
        
        for (AdministradorEntity ent : list) 
        {
            boolean found = false;
            
            for (AdministradorEntity entity : data) 
            {
                if (ent.getCedula().equals(entity.getCedula())) 
                {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }
    
    /**
     * Prueba para consultar un administrador.
     */
    
    @Test
    public void getAdministradorTest() 
    {
        AdministradorEntity entity = data.get(0);
        AdministradorEntity newEntity = admiPersistence.find(entity.getCedula());
        Assert.assertNotNull(newEntity);
        Assert.assertEquals(entity.getCedula(), newEntity.getCedula());
    }
    
    /**
     * Prueba para eliminar un administrador.
     */
    
    @Test
    public void deleteAdministradorTest() 
    {
        AdministradorEntity entity = data.get(0);
        admiPersistence.delete(entity.getCedula());
        AdministradorEntity deleted = em.find(AdministradorEntity.class, entity.getCedula());
        Assert.assertNull(deleted);
    }
    
    /**
     * Prueba para actualizar un administrador.
     */
    
    @Test
    public void updateAdministradorTest() 
    {
        AdministradorEntity entity = data.get(0);
        PodamFactory factory = new PodamFactoryImpl();
        AdministradorEntity newEntity = factory.manufacturePojo(AdministradorEntity.class);

        newEntity.setCedula(entity.getCedula());

        admiPersistence.update(newEntity);

        AdministradorEntity resp = em.find(AdministradorEntity.class, entity.getCedula());

        Assert.assertEquals(newEntity.getCedula(), resp.getCedula());
    }
    
}
