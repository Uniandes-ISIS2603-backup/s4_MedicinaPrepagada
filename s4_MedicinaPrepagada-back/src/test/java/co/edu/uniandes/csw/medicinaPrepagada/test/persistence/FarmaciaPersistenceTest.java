/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.medicinaPrepagada.test.persistence;

import co.edu.uniandes.csw.medicinaPrepagada.entities.FarmaciaEntity;
import co.edu.uniandes.csw.medicinaPrepagada.persistence.FarmaciaPersistence;
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
 * Pruebas de persistencia de Farmacias
 *
 * @author ncobos
 */
@RunWith(Arquillian.class)
public class FarmaciaPersistenceTest {

    @Inject
    private FarmaciaPersistence farmaciaPersistence;

    @PersistenceContext
    private EntityManager em;

    @Inject
    UserTransaction utx;

    private List<FarmaciaEntity> data = new ArrayList<FarmaciaEntity>();

    /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(FarmaciaEntity.class.getPackage())
                .addPackage(FarmaciaPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }

    /**
     * Configuración inicial de la prueba.
     */
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

    /**
     * Limpia las tablas que están implicadas en la prueba.
     */
    private void clearData() {
        em.createQuery("delete from FarmaciaEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        PodamFactory factory = new PodamFactoryImpl();
        for (int i = 0; i < 3; i++) {

            FarmaciaEntity entity = factory.manufacturePojo(FarmaciaEntity.class);

            em.persist(entity);

            data.add(entity);
        }
    }

    /**
     * Prueba para crear una Farmacia.
     */
    @Test
    public void createFarmaciaTest() {
        PodamFactory factory = new PodamFactoryImpl();
        FarmaciaEntity newEntity = factory.manufacturePojo(FarmaciaEntity.class);
        FarmaciaEntity result = farmaciaPersistence.create(newEntity);

        Assert.assertNotNull(result);

        FarmaciaEntity entity = em.find(FarmaciaEntity.class, result.getId());

        Assert.assertEquals(newEntity.getNombre(), entity.getNombre());
    }

    /**
     * Prueba para consultar la lista de Farmaciaes.
     */
    @Test
    public void getFarmaciasTest() {
        List<FarmaciaEntity> list = farmaciaPersistence.findAll();
        Assert.assertEquals(data.size(), list.size());
        for (FarmaciaEntity ent : list) {
            boolean found = false;
            for (FarmaciaEntity entity : data) {
                if (ent.getId().equals(entity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }

    /**
     * Prueba para consultar una Farmacia.
     */
    @Test
    public void getFarmaciaTest() {
        FarmaciaEntity entity = data.get(0);
        FarmaciaEntity newEntity = farmaciaPersistence.find(entity.getId());
        Assert.assertNotNull(newEntity);
        Assert.assertEquals(entity.getNombre(), newEntity.getNombre());
    }

    /**
     * Prueba para eliminar una Farmacia.
     */
    @Test
    public void deleteFarmaciaTest() {
        FarmaciaEntity entity = data.get(0);
        farmaciaPersistence.delete(entity.getId());
        FarmaciaEntity deleted = em.find(FarmaciaEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }

    /**
     * Prueba para actualizar una Farmacia.
     */
    @Test
    public void updateFarmaciaTest() {
        FarmaciaEntity entity = data.get(0);
        PodamFactory factory = new PodamFactoryImpl();
        FarmaciaEntity newEntity = factory.manufacturePojo(FarmaciaEntity.class);

        newEntity.setId(entity.getId());

        farmaciaPersistence.update(newEntity);

        FarmaciaEntity resp = em.find(FarmaciaEntity.class, entity.getId());

        Assert.assertEquals(newEntity.getNombre(), resp.getNombre());
    }

    /**
     * Prueba para consultar una Farmacia por nombre.
     */
    @Test
    public void findFarmaciaByNombreTest() {
        FarmaciaEntity entity = data.get(0);
        FarmaciaEntity newEntity = farmaciaPersistence.findByNombre(entity.getNombre());
        Assert.assertNotNull(newEntity);
        Assert.assertEquals(entity.getNombre(), newEntity.getNombre());

        newEntity = farmaciaPersistence.findByNombre(null);
        Assert.assertNull(newEntity);
    }
}

