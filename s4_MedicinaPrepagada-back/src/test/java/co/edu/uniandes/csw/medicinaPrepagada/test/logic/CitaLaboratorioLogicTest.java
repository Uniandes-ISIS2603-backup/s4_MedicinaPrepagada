/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.medicinaPrepagada.test.logic;
import co.edu.uniandes.csw.medicinaPrepagada.ejb.CitaLaboratorioLogic;
import co.edu.uniandes.csw.medicinaPrepagada.entities.CitaLaboratorioEntity;

//import co.edu.uniandes.csw.medicinaPrepagada.entities.LaboratorioEntity;
//import co.edu.uniandes.csw.medicinaPrepagada.entities.PacienteEntity;
import co.edu.uniandes.csw.medicinaPrepagada.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.medicinaPrepagada.persistence.CitaLaboratorioPersistence;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.junit.Assert;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
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
 * @author Santiago Rojas
 */

@RunWith(Arquillian.class)
public class CitaLaboratorioLogicTest
{
    private PodamFactory factory = new PodamFactoryImpl();

    @Inject
    private CitaLaboratorioLogic citaLabLogic;

    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;

    private List<CitaLaboratorioEntity> citaLabList = new ArrayList<>();
    
    @Deployment
    public static JavaArchive createDeployment() 
    {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(CitaLaboratorioEntity.class.getPackage())
                .addPackage(CitaLaboratorioLogic.class.getPackage())
                .addPackage(CitaLaboratorioPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }
    
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
    
    private void clearData() {
        em.createQuery("delete from CitaLaboratorioEntity").executeUpdate();
    }
     
    private void insertData() {
        for (int i = 0; i < 3; i++) {
            CitaLaboratorioEntity entity = factory.manufacturePojo(CitaLaboratorioEntity.class);
            em.persist(entity);
            citaLabList.add(entity);
        }
       
    }
    
    @Test
    public void createCitaLaboratorio () throws BusinessLogicException
    {
       Date nueva = new Date(System.currentTimeMillis()+24*60*60*1000);
       CitaLaboratorioEntity newEntity = factory.manufacturePojo(CitaLaboratorioEntity.class);
       newEntity.setDate(nueva);
       citaLabLogic.createCitaLaboratorio(newEntity);
       Assert.assertNotNull(newEntity);
     

    }
    
    
    @Test
    public void getAllCitasTest() throws BusinessLogicException
    {
        List<CitaLaboratorioEntity> list = citaLabLogic.getCitasLab();
        Assert.assertEquals(citaLabList.size(), list.size());
        for (CitaLaboratorioEntity entity : list) {
            boolean found = false;
            for (CitaLaboratorioEntity storedEntity : citaLabList) {
                if (entity.getId().equals(storedEntity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }
    
    @Test
    public void getCitaLabTest() {
        CitaLaboratorioEntity entity = citaLabList.get(0);
        CitaLaboratorioEntity resultEntity = citaLabLogic.getCita(entity.getId());
        Assert.assertNotNull(resultEntity);
        Assert.assertEquals(entity.getId(), resultEntity.getId());
    }
    @Test(expected = BusinessLogicException.class)
    public void crearCitaConFechaAnterior() throws BusinessLogicException
    {
        Date nueva = new Date(System.currentTimeMillis()-24*60*60*1000);
        
        CitaLaboratorioEntity entity = factory.manufacturePojo(CitaLaboratorioEntity.class);
        entity.setDate(nueva);
        citaLabLogic.createCitaLaboratorio(entity);
        
    }
    
    @Test(expected = BusinessLogicException.class)
    public void crearCitaConEspecialidadVacia()throws BusinessLogicException
    {
        String espec = "";
        CitaLaboratorioEntity entity = factory.manufacturePojo(CitaLaboratorioEntity.class);
        entity.setDate(new Date(System.currentTimeMillis()+24*60*60*1000));
        entity.setEspecialidad(espec);
        citaLabLogic.createCitaLaboratorio(entity);
    } 
    
    @Test(expected = BusinessLogicException.class)
    public void crearCitaConComentariosVacia()throws BusinessLogicException
    {
        String comments = "";
        CitaLaboratorioEntity entity = factory.manufacturePojo(CitaLaboratorioEntity.class);
        entity.setDate(new Date(System.currentTimeMillis()+24*60*60*1000));
        entity.setComentarios(comments);
        citaLabLogic.createCitaLaboratorio(entity);
    } 
    @Test(expected = BusinessLogicException.class)
    public void crearCitaConRecomendacionesVacia()throws BusinessLogicException
    {
        String recom = "";
        CitaLaboratorioEntity entity = factory.manufacturePojo(CitaLaboratorioEntity.class);
        entity.setDate(new Date(System.currentTimeMillis()+24*60*60*1000));
        entity.setRecomendaciones(recom);
        citaLabLogic.createCitaLaboratorio(entity);
    } 
    @Test
    public void updateCitaLaboratorioTest() throws BusinessLogicException
    {
        Date nueva = new Date(System.currentTimeMillis()+24*60*60*1000);
        CitaLaboratorioEntity entity = citaLabList.get(0);
        CitaLaboratorioEntity pojoEntity = factory.manufacturePojo(CitaLaboratorioEntity.class);
        
        pojoEntity.setDate(nueva);
        pojoEntity.setId(entity.getId());
        
        citaLabLogic.updateCitaLaboratorio(pojoEntity.getId(), pojoEntity);

        CitaLaboratorioEntity resp = em.find(CitaLaboratorioEntity.class, entity.getId());

        Assert.assertEquals(pojoEntity.getId(), resp.getId());
    }
    @Test(expected = BusinessLogicException.class)
    public void updateCitaLaboratorioConFechaInvalidaTest() throws BusinessLogicException
    {
        Date nueva = new Date(System.currentTimeMillis()-2*24*60*60*1000);
        CitaLaboratorioEntity entity = citaLabList.get(0);
        CitaLaboratorioEntity pojoEntity = factory.manufacturePojo(CitaLaboratorioEntity.class);
        
        pojoEntity.setDate(nueva);
        pojoEntity.setId(entity.getId());
        
        citaLabLogic.updateCitaLaboratorio(pojoEntity.getId(), pojoEntity);

        CitaLaboratorioEntity resp = em.find(CitaLaboratorioEntity.class, entity.getId());

        Assert.assertEquals(pojoEntity.getId(), resp.getId());
    }
  
    @Test(expected=BusinessLogicException.class)
    public void updateCitaLaboratorioConEspecialidadInvalidaTest() throws BusinessLogicException
    {
        Date nueva = new Date(System.currentTimeMillis()+24*60*60*1000);
        CitaLaboratorioEntity entity = citaLabList.get(0);
        CitaLaboratorioEntity pojoEntity = factory.manufacturePojo(CitaLaboratorioEntity.class);
        
        pojoEntity.setDate(nueva);
        pojoEntity.setId(entity.getId());
        pojoEntity.setEspecialidad("");
        
        citaLabLogic.updateCitaLaboratorio(pojoEntity.getId(), pojoEntity);

        CitaLaboratorioEntity resp = em.find(CitaLaboratorioEntity.class, entity.getId());

        Assert.assertEquals(pojoEntity.getId(), resp.getId());
    }
    @Test
    public void deleteCitaLaboratorio() throws BusinessLogicException
    {
        CitaLaboratorioEntity entity = citaLabList.get(0);
        citaLabLogic.deleteCitaLab(entity.getId());
        CitaLaboratorioEntity borrado = em.find(CitaLaboratorioEntity.class, entity.getId());
        Assert.assertNull(borrado);
    }
    
}
