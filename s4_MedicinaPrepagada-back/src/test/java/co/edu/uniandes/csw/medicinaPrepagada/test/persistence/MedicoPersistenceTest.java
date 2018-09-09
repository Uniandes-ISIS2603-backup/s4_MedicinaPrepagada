/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.medicinaPrepagada.test.persistence;

import co.edu.uniandes.csw.medicinaPrepagada.entities.MedicoEntity;
import co.edu.uniandes.csw.medicinaPrepagada.persistence.MedicoPersistence;
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
 * @author Daniel Ivan Romero
 */
@RunWith(Arquillian.class)
public class MedicoPersistenceTest {
    @Inject
    private MedicoPersistence medicoPersistence;

    @PersistenceContext
    private EntityManager em;

    @Inject
    UserTransaction utx;

    private List<MedicoEntity> data = new ArrayList<>();

    /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(MedicoEntity.class.getPackage())
                .addPackage(MedicoPersistence.class.getPackage())
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
        em.createQuery("delete from MedicoEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        PodamFactory factory = new PodamFactoryImpl();
        for (int i = 0; i < 3; i++) {
            MedicoEntity entity = factory.manufacturePojo(MedicoEntity.class);

            em.persist(entity);
            data.add(entity);
        }
    }

    /**
     * Prueba para crear un Medico.
     */
    @Test
    public void createMedicoTest() {
        PodamFactory factory = new PodamFactoryImpl();
        MedicoEntity newEntity = factory.manufacturePojo(MedicoEntity.class);
        MedicoEntity result = medicoPersistence.create(newEntity);

        Assert.assertNotNull(result);

        MedicoEntity entity = em.find(MedicoEntity.class, result.getId());

        Assert.assertEquals(newEntity.getId(), entity.getId());
    }

    /**
     * Prueba para consultar la lista de Medicos.
     */
    @Test
    public void getMedicosTest() {
        List<MedicoEntity> list = medicoPersistence.findAll();
        Assert.assertEquals(data.size(), list.size());
        for (MedicoEntity ent : list) {
            boolean found = false;
            for (MedicoEntity entity : data) {
                if (ent.getId().equals(entity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }

    /**
     * Prueba para consultar un Medico.
     */
    @Test
    public void getMedicoTest() {
        MedicoEntity entity = data.get(0);
        MedicoEntity newEntity = medicoPersistence.find(entity.getId());
        Assert.assertNotNull(newEntity);
        Assert.assertEquals(entity.getId(), newEntity.getId());
        Assert.assertEquals(entity.getCorreo(), newEntity.getCorreo());
    }

    /**
     * Prueba para actualizar un Medico.
     */
    @Test
    public void updateMedicoTest() {
        MedicoEntity entity = data.get(0);
        PodamFactory factory = new PodamFactoryImpl();
        MedicoEntity newEntity = factory.manufacturePojo(MedicoEntity.class);

        newEntity.setId(entity.getId());

        medicoPersistence.update(newEntity);

        MedicoEntity resp = em.find(MedicoEntity.class, entity.getId());

        Assert.assertEquals(newEntity.getNombre(), resp.getNombre());
    }

    /**
     * Prueba para eliminar un Medico.
     */
    @Test
    public void deleteMedicoTest() {
        MedicoEntity entity = data.get(0);
        medicoPersistence.delete(entity.getId());
        MedicoEntity deleted = em.find(MedicoEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }
}
