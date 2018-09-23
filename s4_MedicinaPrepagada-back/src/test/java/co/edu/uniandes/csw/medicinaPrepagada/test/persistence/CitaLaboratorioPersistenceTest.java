
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.medicinaPrepagada.test.persistence;

import co.edu.uniandes.csw.medicinaPrepagada.entities.CitaLaboratorioEntity;
import co.edu.uniandes.csw.medicinaPrepagada.persistence.CitaLaboratorioPersistence;
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
 * @author Santiago Rojas
 */
@RunWith(Arquillian.class)
public class CitaLaboratorioPersistenceTest {
    
    @Inject
    private CitaLaboratorioPersistence citaLaboratorioPersistence;

    @PersistenceContext
    private EntityManager em;

    @Inject
    UserTransaction utx;

    private List<CitaLaboratorioEntity> data = new ArrayList<CitaLaboratorioEntity>();

    /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(CitaLaboratorioEntity.class.getPackage())
                .addPackage(CitaLaboratorioPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }
    
    @Before
    public void configTest() {
        try {
            utx.begin();
            em.joinTransaction();
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
    
    private void clearData() {
        em.createQuery("delete from CitaLaboratorioEntity").executeUpdate();
    }
    
    private void insertData() {
        PodamFactory factory = new PodamFactoryImpl();
        for (int i = 0; i < 3; i++) {
            CitaLaboratorioEntity entity = factory.manufacturePojo(CitaLaboratorioEntity.class);

            em.persist(entity);
            data.add(entity);
        }
    }
    
    @Test
    public void createCitaLaboratorioTest() {
        PodamFactory factory = new PodamFactoryImpl();
        CitaLaboratorioEntity newEntity = factory.manufacturePojo(CitaLaboratorioEntity.class);
        CitaLaboratorioEntity result = citaLaboratorioPersistence.create(newEntity);

        Assert.assertNotNull(result);

        CitaLaboratorioEntity entity = em.find(CitaLaboratorioEntity.class, result.getId());
        
        Assert.assertEquals(newEntity.getEspecialidad(), entity.getEspecialidad());
    }
    
    @Test
    public void deleteEditorialTest() {
        CitaLaboratorioEntity entity = data.get(0);
        citaLaboratorioPersistence.delete(entity.getId());
        CitaLaboratorioEntity deleted = em.find(CitaLaboratorioEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }
    
    @Test
    public void getAllCitaLaboratorioTest() {
        List<CitaLaboratorioEntity> list = citaLaboratorioPersistence.findAll();
        Assert.assertEquals(data.size(), list.size());
        for (CitaLaboratorioEntity e : list) {
            boolean found = false;
            for (CitaLaboratorioEntity entity : data) {
                if (e.getId()==entity.getId()) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }
    
     @Test
    public void getCitaLaboratorioTest() {
        CitaLaboratorioEntity entity = data.get(0);
        CitaLaboratorioEntity newEntity = citaLaboratorioPersistence.find(entity.getId());
        Assert.assertNotNull(newEntity);
        //Assert.assertEquals(entity.getDate(), newEntity.getDate());
        Assert.assertEquals(entity.getEspecialidad(), newEntity.getEspecialidad());
    }
    
    @Test
    public void updateCitaLaboratorioTest() {
        CitaLaboratorioEntity entity = data.get(0);
        PodamFactory factory = new PodamFactoryImpl();
        CitaLaboratorioEntity newEntity = factory.manufacturePojo(CitaLaboratorioEntity.class);

        newEntity.setId(entity.getId());

        citaLaboratorioPersistence.update(newEntity);

        CitaLaboratorioEntity resp = em.find(CitaLaboratorioEntity.class, entity.getId());

        Assert.assertEquals(newEntity.getId(), resp.getId());
    } 
    
    @Test 
    public void updateLaboratorioTest()
    {
        CitaLaboratorioEntity entity = data.get(0);
        PodamFactory factory = new PodamFactoryImpl();
        CitaLaboratorioEntity newEntity = factory.manufacturePojo(CitaLaboratorioEntity.class);
        
        newEntity.setLaboratorio(entity.getLaboratorio());
        
        citaLaboratorioPersistence.update(newEntity);
        
        CitaLaboratorioEntity resp = em.find(CitaLaboratorioEntity.class, entity.getId());
        
        Assert.assertEquals(newEntity.getLaboratorio(),resp.getLaboratorio());
        
    }
}
