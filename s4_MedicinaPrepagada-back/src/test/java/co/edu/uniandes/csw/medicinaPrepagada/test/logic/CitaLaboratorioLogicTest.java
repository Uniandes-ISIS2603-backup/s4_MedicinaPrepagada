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
import java.util.ArrayList;
//import java.util.Date;
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
    /*
    @Test
    public void createCitaLaboratorio () throws BusinessLogicException
    {
       CitaLaboratorioEntity newEntity = factory.manufacturePojo(CitaLaboratorioEntity.class);
       CitaLaboratorioEntity result = citaLabLogic.createCitaLaboratorio(newEntity);
       Assert.assertNotNull(result);
       CitaLaboratorioEntity entity = em.find(CitaLaboratorioEntity.class, result.getId());

    }
    */
    
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
    
    /*
    @Test
    public void updateCitaLaboratorioTest() throws BusinessLogicException
    {
        CitaLaboratorioEntity entity = citaLabList.get(0);
        CitaLaboratorioEntity pojoEntity = factory.manufacturePojo(CitaLaboratorioEntity.class);

        pojoEntity.setId(entity.getId());

        citaLabLogic.updateCitaLaboratorio(pojoEntity.getId(), pojoEntity);

        CitaLaboratorioEntity resp = em.find(CitaLaboratorioEntity.class, entity.getId());

        Assert.assertEquals(pojoEntity.getId(), resp.getId());
    }
    */
    
}
