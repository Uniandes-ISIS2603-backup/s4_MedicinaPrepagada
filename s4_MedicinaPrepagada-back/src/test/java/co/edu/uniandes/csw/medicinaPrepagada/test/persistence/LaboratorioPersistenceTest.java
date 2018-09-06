/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.medicinaPrepagada.test.persistence;
import co.edu.uniandes.csw.medicinaPrepagada.entities.LaboratorioEntity;
import co.edu.uniandes.csw.medicinaPrepagada.persistence.LaboratorioPersistence;
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
import uk.co.jemos.podam.api.PodamFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import uk.co.jemos.podam.api.PodamFactoryImpl;

/**
 *
 * @author Santiago Rojas
 */

@RunWith(Arquillian.class)
public class LaboratorioPersistenceTest {
    
    @Inject
    private LaboratorioPersistence laboratorioPersistence;

    @PersistenceContext
    private EntityManager em;

    @Inject
    UserTransaction utx;
    
    private List<LaboratorioEntity> data = new ArrayList<LaboratorioEntity>();
    
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(LaboratorioEntity.class.getPackage())
                .addPackage(LaboratorioPersistence.class.getPackage())
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
        em.createQuery("delete from LaboratorioEntity").executeUpdate();
    }
    
    private void insertData() {
        PodamFactoryImpl factory = new PodamFactoryImpl();
        for (int i = 0; i < 3; i++) {
            LaboratorioEntity entity = factory.manufacturePojo(LaboratorioEntity.class);

            em.persist(entity);
            data.add(entity);
        }
    }
    
    @Test
    public void createLaboratorioTest() {
        PodamFactoryImpl factory = new PodamFactoryImpl();
        LaboratorioEntity newEntity = factory.manufacturePojo(LaboratorioEntity.class);
        LaboratorioEntity result = laboratorioPersistence.create(newEntity);

        Assert.assertNotNull(result);

        LaboratorioEntity entity = em.find(LaboratorioEntity.class, result.getId());
        
        Assert.assertEquals(newEntity.getNombre(), entity.getNombre());
    }
    
    @Test
    public void deleteLaboratorioTest() {
        LaboratorioEntity entity = data.get(0);
        laboratorioPersistence.delete(entity.getId());
        LaboratorioEntity deleted = em.find(LaboratorioEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }
    
    @Test
    public void getAllLaboratorioTest() {
        List<LaboratorioEntity> list = laboratorioPersistence.findAll();
        Assert.assertEquals(data.size(), list.size());
        for (LaboratorioEntity e : list) {
            boolean found = false;
            for (LaboratorioEntity entity : data) {
                if (e.getId()==entity.getId()) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }
    
     @Test
    public void getLaboratorioTest() {
        LaboratorioEntity entity = data.get(0);
        LaboratorioEntity newEntity = laboratorioPersistence.find(entity.getId());
        Assert.assertNotNull(newEntity);
       
        Assert.assertEquals(entity.getNombre(), newEntity.getNombre());
    }
    
    @Test
    public void updateLaboratorioTest() {
        LaboratorioEntity entity = data.get(0);
        PodamFactory factory = new PodamFactoryImpl();
        LaboratorioEntity newEntity = factory.manufacturePojo(LaboratorioEntity.class);

        newEntity.setId(entity.getId());

        laboratorioPersistence.update(newEntity);

        LaboratorioEntity resp = em.find(LaboratorioEntity.class, entity.getId());

        Assert.assertEquals(newEntity.getNombre(), resp.getNombre());
    } 
}
