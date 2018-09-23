/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.medicinaPrepagada.test.logic;

import co.edu.uniandes.csw.medicinaPrepagada.ejb.AdministradorLogic;
import co.edu.uniandes.csw.medicinaPrepagada.entities.AdministradorEntity;
import co.edu.uniandes.csw.medicinaPrepagada.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.medicinaPrepagada.persistence.AdministradorPersistence;
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
public class AdministradorLogicTest 
{
    private PodamFactory factory = new PodamFactoryImpl();

    @Inject
    private AdministradorLogic admiLogic;

    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;

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
                .addPackage(AdministradorLogic.class.getPackage())
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
        for (int i = 0; i < 3; i++) 
        {
            AdministradorEntity entity = factory.manufacturePojo(AdministradorEntity.class);
            em.persist(entity);
            data.add(entity);
        }
    }
    
    /**
     * Prueba para crear un Administrador.
     * @throws co.edu.uniandes.csw.medicinaPrepagada.exceptions.BusinessLogicException
     */
    
    @Test
    public void createAdministradorTest() throws BusinessLogicException 
    {
        AdministradorEntity newEntity = factory.manufacturePojo(AdministradorEntity.class);
        newEntity.setContrasena("12345678909");
        AdministradorEntity result = admiLogic.createAdministrador(newEntity);
        Assert.assertNotNull(result);
        AdministradorEntity entity = em.find(AdministradorEntity.class, result.getId());
        Assert.assertEquals(newEntity.getLogin(), entity.getLogin());
        Assert.assertEquals(newEntity.getContrasena(), entity.getContrasena());
        Assert.assertEquals(newEntity.getTipoUsuario(), entity.getTipoUsuario());
    }
    
    /**
     * Prueba para consultar la lista de Administradores.
     */
    
    @Test
    public void getAdministradoresTest()
    {
        List<AdministradorEntity> list = admiLogic.getAdministradores();
        Assert.assertEquals(data.size(), list.size());
        
        for(AdministradorEntity entity : list) 
        {
            boolean found = false;
            
            for(AdministradorEntity storedEntity : data) 
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
     * Prueba para consultar un Administrador.
     */
    
    @Test
    public void getAdministradorTest() 
    {
        AdministradorEntity entity = data.get(0);
        AdministradorEntity resultEntity = admiLogic.getAdministrador(entity.getId());
        Assert.assertNotNull(resultEntity);
        Assert.assertEquals(entity.getId(), resultEntity.getId());
        Assert.assertEquals(entity.getLogin(), resultEntity.getLogin());
        Assert.assertEquals(entity.getContrasena(), resultEntity.getContrasena());
        Assert.assertEquals(entity.getTipoUsuario(), resultEntity.getTipoUsuario());
    }
    
    /**
     * Prueba para actualizar un Administrador.
     */
    
    @Test
    public void updateAdministradorTest() 
    {
        AdministradorEntity entity = data.get(0);
        AdministradorEntity pojoEntity = factory.manufacturePojo(AdministradorEntity.class);

        pojoEntity.setId(entity.getId());

        try 
        {
            admiLogic.updateAdministrador(pojoEntity.getId(), pojoEntity);
        } 
        catch (BusinessLogicException ex) 
        {
            Logger.getLogger(AdministradorLogicTest.class.getName()).log(Level.SEVERE, null, ex);
        }

        AdministradorEntity resp = em.find(AdministradorEntity.class, entity.getId());

        Assert.assertEquals(pojoEntity.getId(), resp.getId());
        Assert.assertEquals(pojoEntity.getLogin(), resp.getLogin());
        Assert.assertEquals(pojoEntity.getContrasena(), resp.getContrasena());
        Assert.assertEquals(pojoEntity.getTipoUsuario(), resp.getTipoUsuario() ); 
    }
    
    /**
     * Prueba para eliminar un Administrador
     * @throws co.edu.uniandes.csw.medicinaPrepagada.exceptions.BusinessLogicException
     */
    
    @Test
    public void deleteAdministradorTest() throws BusinessLogicException 
    {
        AdministradorEntity entity = data.get(0);
        admiLogic.deleteAdministrador(entity.getId());
        AdministradorEntity deleted = em.find(AdministradorEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }
    
}
