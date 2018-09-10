/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.medicinaPrepagada.test.logic;

import co.edu.uniandes.csw.medicinaPrepagada.ejb.HistoriaClinicaLogic;
import co.edu.uniandes.csw.medicinaPrepagada.entities.HistoriaClinicaEntity;
import co.edu.uniandes.csw.medicinaPrepagada.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.medicinaPrepagada.persistence.HistoriaClinicaPersistence;
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
public class HistoriaClinicaLogicTest 
{
    private PodamFactory factory = new PodamFactoryImpl();

    @Inject
    private HistoriaClinicaLogic historiaLogic;

    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;

    private List<HistoriaClinicaEntity> data = new ArrayList<>();
    
    /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    
    @Deployment
    public static JavaArchive createDeployment() 
    {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(HistoriaClinicaEntity.class.getPackage())
                .addPackage(HistoriaClinicaLogic.class.getPackage())
                .addPackage(HistoriaClinicaPersistence.class.getPackage())
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
        em.createQuery("delete from HistoriaClinicaEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    
    private void insertData() 
    {
        for (int i = 0; i < 3; i++) 
        {
            HistoriaClinicaEntity entity = factory.manufacturePojo(HistoriaClinicaEntity.class);
            em.persist(entity);
            data.add(entity);
        }
    }
    
    /**
     * Prueba para crear una Historia Clinica.
     * @throws co.edu.uniandes.csw.medicinaPrepagada.exceptions.BusinessLogicException
     */
    
    @Test
    public void createHistoriaClinicaTest() throws BusinessLogicException 
    {
        HistoriaClinicaEntity newEntity = factory.manufacturePojo(HistoriaClinicaEntity.class);
        HistoriaClinicaEntity result = historiaLogic.createHistoriaClinica(newEntity);
        Assert.assertNotNull(result);
        
        HistoriaClinicaEntity entity = em.find(HistoriaClinicaEntity.class, result.getId());
        Assert.assertEquals(newEntity.getId(), entity.getId());
    }
    
    /**
     * Prueba para consultar la lista de historias clinicas.
     */
    
    @Test
    public void getHistoriasClinicasTest()
    {
        List<HistoriaClinicaEntity> list = historiaLogic.getHistoriasClinicas( );
        Assert.assertEquals(data.size(), list.size());
        
        for (HistoriaClinicaEntity entity : list) 
        {
            boolean found = false;
            
            for (HistoriaClinicaEntity storedEntity : data) 
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
     * Prueba para consultar una historia clinica.
     */
    
    @Test
    public void getHistoriaClinicaTest() 
    {
        HistoriaClinicaEntity entity = data.get(0);
        HistoriaClinicaEntity resultEntity = historiaLogic.getHistoriaClinica(entity.getId());
        Assert.assertNotNull(resultEntity);
        
    }
    
    /**
     * Prueba para actualizar una historia clinica.
     */
    
    @Test
    public void updateHistoriaClinicaTest() 
    {
        HistoriaClinicaEntity entity = data.get(0);
        HistoriaClinicaEntity pojoEntity = factory.manufacturePojo(HistoriaClinicaEntity.class);

        pojoEntity.setId(entity.getId());

        historiaLogic.updateHistoriaClinica(pojoEntity.getId(), pojoEntity);

        HistoriaClinicaEntity resp = em.find(HistoriaClinicaEntity.class, entity.getId());

    }
    
    /**
     * Prueba para eliminar una historia clinica
     * @throws co.edu.uniandes.csw.medicinaPrepagada.exceptions.BusinessLogicException
     */
    
    @Test
    public void deleteHistoriaClinicaTest() throws BusinessLogicException 
    {
        HistoriaClinicaEntity entity = data.get(0);
        historiaLogic.deleteHistoriaClinica(entity.getId());
        HistoriaClinicaEntity deleted = em.find(HistoriaClinicaEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }
    
}
