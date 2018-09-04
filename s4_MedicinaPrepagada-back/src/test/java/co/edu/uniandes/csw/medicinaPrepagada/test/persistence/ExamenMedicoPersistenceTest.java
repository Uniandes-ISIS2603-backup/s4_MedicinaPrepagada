/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.medicinaPrepagada.test.persistence;

import co.edu.uniandes.csw.medicinaPrepagada.entities.ExamenMedicoEntity;
import co.edu.uniandes.csw.medicinaPrepagada.persistence.ExamenMedicoPersistence;
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
 * Pruebas de persistencia de Examenes medicos
 *
 * @author ncobos
 */
@RunWith(Arquillian.class)
public class ExamenMedicoPersistenceTest {

    @Inject
    private ExamenMedicoPersistence examenMedicoPersistence;

    @PersistenceContext
    private EntityManager em;

    @Inject
    UserTransaction utx;

    private List<ExamenMedicoEntity> data = new ArrayList<ExamenMedicoEntity>();

    /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(ExamenMedicoEntity.class.getPackage())
                .addPackage(ExamenMedicoPersistence.class.getPackage())
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
        em.createQuery("delete from ExamenMedicoEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        PodamFactory factory = new PodamFactoryImpl();
        for (int i = 0; i < 3; i++) {

            ExamenMedicoEntity entity = factory.manufacturePojo(ExamenMedicoEntity.class);

            em.persist(entity);

            data.add(entity);
        }
    }

    /**
     * Prueba para crear una ExamenMedico.
     */
    @Test
    public void createExamenMedicoTest() {
        PodamFactory factory = new PodamFactoryImpl();
        ExamenMedicoEntity newEntity = factory.manufacturePojo(ExamenMedicoEntity.class);
        ExamenMedicoEntity result = examenMedicoPersistence.create(newEntity);

        Assert.assertNotNull(result);

        ExamenMedicoEntity entity = em.find(ExamenMedicoEntity.class, result.getId());

        Assert.assertEquals(newEntity.getNombre(), entity.getNombre());
    }

    /**
     * Prueba para consultar la lista de ExamenMedicoes.
     */
    @Test
    public void getExamenMedicosTest() {
        List<ExamenMedicoEntity> list = examenMedicoPersistence.findAll();
        Assert.assertEquals(data.size(), list.size());
        for (ExamenMedicoEntity ent : list) {
            boolean found = false;
            for (ExamenMedicoEntity entity : data) {
                if (ent.getId().equals(entity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }

    /**
     * Prueba para consultar una ExamenMedico.
     */
    @Test
    public void getExamenMedicoTest() {
        ExamenMedicoEntity entity = data.get(0);
        ExamenMedicoEntity newEntity = examenMedicoPersistence.find(entity.getId());
        Assert.assertNotNull(newEntity);
        Assert.assertEquals(entity.getNombre(), newEntity.getNombre());
    }

    /**
     * Prueba para eliminar una ExamenMedico.
     */
    @Test
    public void deleteExamenMedicoTest() {
        ExamenMedicoEntity entity = data.get(0);
        examenMedicoPersistence.delete(entity.getId());
        ExamenMedicoEntity deleted = em.find(ExamenMedicoEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }

    /**
     * Prueba para actualizar una ExamenMedico.
     */
    @Test
    public void updateExamenMedicoTest() {
        ExamenMedicoEntity entity = data.get(0);
        PodamFactory factory = new PodamFactoryImpl();
        ExamenMedicoEntity newEntity = factory.manufacturePojo(ExamenMedicoEntity.class);

        newEntity.setId(entity.getId());

        examenMedicoPersistence.update(newEntity);

        ExamenMedicoEntity resp = em.find(ExamenMedicoEntity.class, entity.getId());

        Assert.assertEquals(newEntity.getNombre(), resp.getNombre());
    }

    /**
     * Prueba para consultar una ExamenMedico por nombre.
     */
    @Test
    public void findExamenMedicoByNombreTest() {
        ExamenMedicoEntity entity = data.get(0);
        ExamenMedicoEntity newEntity = examenMedicoPersistence.findByNombre(entity.getNombre());
        Assert.assertNotNull(newEntity);
        Assert.assertEquals(entity.getNombre(), newEntity.getNombre());

        newEntity = examenMedicoPersistence.findByNombre(null);
        Assert.assertNull(newEntity);
    }
}

