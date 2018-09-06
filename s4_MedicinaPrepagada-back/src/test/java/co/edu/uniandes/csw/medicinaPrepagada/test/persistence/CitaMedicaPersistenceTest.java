/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.medicinaPrepagada.test.persistence;

import co.edu.uniandes.csw.medicinaPrepagada.entities.CitaMedicaEntity;
import co.edu.uniandes.csw.medicinaPrepagada.persistence.CitaMedicaPersistence;
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
public class CitaMedicaPersistenceTest {
    @Inject
    private CitaMedicaPersistence citaMedicaPersistence;

    @PersistenceContext
    private EntityManager em;

    @Inject
    UserTransaction utx;

    private List<CitaMedicaEntity> data = new ArrayList<>();

    /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(CitaMedicaEntity.class.getPackage())
                .addPackage(CitaMedicaPersistence.class.getPackage())
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
        em.createQuery("delete from CitaMedicaEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        PodamFactory factory = new PodamFactoryImpl();
        for (int i = 0; i < 3; i++) {
            CitaMedicaEntity entity = factory.manufacturePojo(CitaMedicaEntity.class);

            em.persist(entity);
            data.add(entity);
        }
    }

    /**
     * Prueba para crear un CitaMedica.
     */
    @Test
    public void createCitaMedicaTest() {
        PodamFactory factory = new PodamFactoryImpl();
        CitaMedicaEntity newEntity = factory.manufacturePojo(CitaMedicaEntity.class);
        CitaMedicaEntity result = citaMedicaPersistence.create(newEntity);

        Assert.assertNotNull(result);

        CitaMedicaEntity entity = em.find(CitaMedicaEntity.class, result.getId());

        Assert.assertEquals(newEntity.getFecha(), entity.getFecha());
        Assert.assertEquals(newEntity.getComentarios(), entity.getComentarios());
        Assert.assertEquals(newEntity.getPacienteAAtender(), entity.getPacienteAAtender());
        Assert.assertEquals(newEntity.getHorarioAtencionAsignado(), entity.getHorarioAtencionAsignado());
    }

    /**
     * Prueba para consultar la lista de CitaMedicas.
     */
    @Test
    public void getCitaMedicasTest() {
        List<CitaMedicaEntity> list = citaMedicaPersistence.findAll();
        Assert.assertEquals(data.size(), list.size());
        for (CitaMedicaEntity ent : list) {
            boolean found = false;
            for (CitaMedicaEntity entity : data) {
                if (ent.getId()==entity.getId()) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }

    /**
     * Prueba para consultar un CitaMedica.
     */
    @Test
    public void getCitaMedicaTest() {
        CitaMedicaEntity entity = data.get(0);
        CitaMedicaEntity newEntity = citaMedicaPersistence.find(entity.getId());
        Assert.assertNotNull(newEntity);
        Assert.assertEquals(newEntity.getFecha(), entity.getFecha());
        Assert.assertEquals(newEntity.getComentarios(), entity.getComentarios());
        Assert.assertEquals(newEntity.getPacienteAAtender(), entity.getPacienteAAtender());
        Assert.assertEquals(newEntity.getHorarioAtencionAsignado(), entity.getHorarioAtencionAsignado());
    }

    /**
     * Prueba para actualizar un CitaMedica.
     */
    @Test
    public void updateCitaMedicaTest() {
        CitaMedicaEntity entity = data.get(0);
        PodamFactory factory = new PodamFactoryImpl();
        CitaMedicaEntity newEntity = factory.manufacturePojo(CitaMedicaEntity.class);

        newEntity.setId(entity.getId());

        citaMedicaPersistence.update(newEntity);

        CitaMedicaEntity resp = em.find(CitaMedicaEntity.class, entity.getId());

        Assert.assertEquals(newEntity.getId(), resp.getId());
        Assert.assertEquals(newEntity.getFecha(), resp.getFecha());
        Assert.assertEquals(newEntity.getComentarios(), resp.getComentarios());
        Assert.assertEquals(newEntity.getPacienteAAtender(), resp.getPacienteAAtender());
        Assert.assertEquals(newEntity.getHorarioAtencionAsignado(), resp.getHorarioAtencionAsignado());
    }

    /**
     * Prueba para eliminar un CitaMedica.
     */
    @Test
    public void deleteCitaMedicaTest() {
        CitaMedicaEntity entity = data.get(0);
        citaMedicaPersistence.delete(entity.getId());
        CitaMedicaEntity deleted = em.find(CitaMedicaEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }
}
