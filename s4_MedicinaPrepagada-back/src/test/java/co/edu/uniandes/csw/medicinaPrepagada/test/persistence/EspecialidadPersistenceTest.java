/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.medicinaPrepagada.test.persistence;

import co.edu.uniandes.csw.medicinaPrepagada.entities.EspecialidadEntity;
import co.edu.uniandes.csw.medicinaPrepagada.persistence.EspecialidadPersistence;
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
public class EspecialidadPersistenceTest {
    @Inject
    private EspecialidadPersistence especialidadPersistence;

    @PersistenceContext
    private EntityManager em;

    @Inject
    UserTransaction utx;

    private List<EspecialidadEntity> data = new ArrayList<>();

    /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(EspecialidadEntity.class.getPackage())
                .addPackage(EspecialidadPersistence.class.getPackage())
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
        em.createQuery("delete from EspecialidadEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        PodamFactory factory = new PodamFactoryImpl();
        for (int i = 0; i < 3; i++) {
            EspecialidadEntity entity = factory.manufacturePojo(EspecialidadEntity.class);

            em.persist(entity);
            data.add(entity);
        }
    }

    /**
     * Prueba para crear un Especialidad.
     */
    @Test
    public void createEspecialidadTest() {
        PodamFactory factory = new PodamFactoryImpl();
        EspecialidadEntity newEntity = factory.manufacturePojo(EspecialidadEntity.class);
        EspecialidadEntity result = especialidadPersistence.create(newEntity);

        Assert.assertNotNull(result);

        EspecialidadEntity entity = em.find(EspecialidadEntity.class, result.getNombre());

        Assert.assertEquals(newEntity.getConsultorios(), entity.getConsultorios());
        Assert.assertEquals(newEntity.getMedicos(), entity.getMedicos());
    }

    /**
     * Prueba para consultar la lista de Especialidads.
     */
    @Test
    public void getEspecialidadesTest() {
        List<EspecialidadEntity> list = especialidadPersistence.findAll();
        Assert.assertEquals(data.size(), list.size());
        for (EspecialidadEntity ent : list) {
            boolean found = false;
            for (EspecialidadEntity entity : data) {
                if (ent.getNombre().equals(entity.getNombre())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }

    /**
     * Prueba para consultar un Especialidad.
     */
    @Test
    public void getEspecialidadTest() {
        EspecialidadEntity entity = data.get(0);
        EspecialidadEntity newEntity = especialidadPersistence.find(entity.getNombre());
        Assert.assertNotNull(newEntity);
        Assert.assertEquals(newEntity.getConsultorios(), entity.getConsultorios());
        Assert.assertEquals(newEntity.getMedicos(), entity.getMedicos());
    }

    /**
     * Prueba para actualizar un Especialidad.
     */
    @Test
    public void updateEspecialidadTest() {
        EspecialidadEntity entity = data.get(0);
        PodamFactory factory = new PodamFactoryImpl();
        EspecialidadEntity newEntity = factory.manufacturePojo(EspecialidadEntity.class);

        newEntity.setNombre(entity.getNombre());

        especialidadPersistence.update(newEntity);

        EspecialidadEntity resp = em.find(EspecialidadEntity.class, entity.getNombre());

        Assert.assertEquals(newEntity.getConsultorios(), resp.getConsultorios());
        Assert.assertEquals(newEntity.getMedicos(), resp.getMedicos());
    }

    /**
     * Prueba para eliminar un Especialidad.
     */
    @Test
    public void deleteEspecialidadTest() {
        EspecialidadEntity entity = data.get(0);
        especialidadPersistence.delete(entity.getNombre());
        EspecialidadEntity deleted = em.find(EspecialidadEntity.class, entity.getNombre());
        Assert.assertNull(deleted);
    }
}
