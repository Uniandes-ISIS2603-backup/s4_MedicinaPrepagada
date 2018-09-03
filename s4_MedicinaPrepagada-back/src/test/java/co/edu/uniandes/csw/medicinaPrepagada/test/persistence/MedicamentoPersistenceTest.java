/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.medicinaPrepagada.test.persistence;

import co.edu.uniandes.csw.medicinaPrepagada.entities.MedicamentoEntity;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import org.jboss.arquillian.junit.Arquillian;
import org.junit.runner.RunWith;
import co.edu.uniandes.csw.medicinaPrepagada.persistence.MedicamentoPersistence;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;


/**
 *
 * @author ncobos
 */
@RunWith(Arquillian.class)
public class MedicamentoPersistenceTest {

    @Inject
    private MedicamentoPersistence medicamentoPersistence;

    @PersistenceContext
    private EntityManager em;

    @Inject
    UserTransaction utx;

    private List<MedicamentoEntity> data = new ArrayList<MedicamentoEntity>();
    
     /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(MedicamentoEntity.class.getPackage())
                .addPackage(MedicamentoPersistence.class.getPackage())
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
        em.createQuery("delete from MedicamentoEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        PodamFactory factory = new PodamFactoryImpl();
        for (int i = 0; i < 3; i++) {

            MedicamentoEntity entity = factory.manufacturePojo(MedicamentoEntity.class);

            em.persist(entity);

            data.add(entity);
        }
    }

    /**
     * Prueba para crear una Medicamento.
     */
    @Test
    public void createMedicamentoTest() {
        PodamFactory factory = new PodamFactoryImpl();
        MedicamentoEntity newEntity = factory.manufacturePojo(MedicamentoEntity.class);
        MedicamentoEntity result = medicamentoPersistence.create(newEntity);

        Assert.assertNotNull(result);

        MedicamentoEntity entity = em.find(MedicamentoEntity.class, result.getId());

        Assert.assertEquals(newEntity.getNombre(), entity.getNombre());
    }

    /**
     * Prueba para consultar la lista de Medicamentoes.
     */
    @Test
    public void getMedicamentosTest() {
        List<MedicamentoEntity> list = medicamentoPersistence.findAll();
        Assert.assertEquals(data.size(), list.size());
        for (MedicamentoEntity ent : list) {
            boolean found = false;
            for (MedicamentoEntity entity : data) {
                if (ent.getId().equals(entity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }

    /**
     * Prueba para consultar un Medicamento.
     */
    @Test
    public void getMedicamentoTest() {
        MedicamentoEntity entity = data.get(0);
        MedicamentoEntity newEntity = medicamentoPersistence.find(entity.getId());
        Assert.assertNotNull(newEntity);
        Assert.assertEquals(entity.getNombre(), newEntity.getNombre());
    }

    /**
     * Prueba para eliminar un Medicamento.
     */
    @Test
    public void deleteMedicamentoTest() {
        MedicamentoEntity entity = data.get(0);
        medicamentoPersistence.delete(entity.getId());
        MedicamentoEntity deleted = em.find(MedicamentoEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }

    /**
     * Prueba para actualizar una Medicamento.
     */
    @Test
    public void updateMedicamentoTest() {
        MedicamentoEntity entity = data.get(0);
        PodamFactory factory = new PodamFactoryImpl();
        MedicamentoEntity newEntity = factory.manufacturePojo(MedicamentoEntity.class);

        newEntity.setId(entity.getId());

        medicamentoPersistence.update(newEntity);

        MedicamentoEntity resp = em.find(MedicamentoEntity.class, entity.getId());

        Assert.assertEquals(newEntity.getNombre(), resp.getNombre());
    }

    /**
     * Prueba para consultar una Medicamento por nombre.
     */
    @Test
    public void findMedicamentoByNombreTest() {
        MedicamentoEntity entity = data.get(0);
        MedicamentoEntity newEntity = medicamentoPersistence.findByName(entity.getNombre());
        Assert.assertNotNull(newEntity);
        Assert.assertEquals(entity.getNombre(), newEntity.getNombre());

        newEntity = medicamentoPersistence.findByName(null);
        Assert.assertNull(newEntity);
    }
}
