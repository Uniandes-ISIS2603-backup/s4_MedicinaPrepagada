/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.medicinaPrepagada.test.logic;

import co.edu.uniandes.csw.medicinaPrepagada.ejb.ExamenMedicoLogic;
import co.edu.uniandes.csw.medicinaPrepagada.ejb.OrdenMedicaExamenMedicoLogic;
import co.edu.uniandes.csw.medicinaPrepagada.entities.ExamenMedicoEntity;
import co.edu.uniandes.csw.medicinaPrepagada.entities.OrdenMedicaEntity;
import co.edu.uniandes.csw.medicinaPrepagada.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.medicinaPrepagada.persistence.OrdenMedicaPersistence;
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
 * Pruebas de la lógica entre el recurso OrdenMedica y ExamenMedico
 * @author ncobos
 */
    @RunWith(Arquillian.class)

public class OrdenMedicaExamenMedicoLogicTest {
    
        private PodamFactory factory = new PodamFactoryImpl();

    @Inject
    private OrdenMedicaExamenMedicoLogic ordenMedicaExamenMedicoLogic;

    @Inject
    private ExamenMedicoLogic examenMedicoLogic;

    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;

    private OrdenMedicaEntity ordenMedica = new OrdenMedicaEntity();
    private List<ExamenMedicoEntity> data = new ArrayList<>();

    /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(OrdenMedicaEntity.class.getPackage())
                .addPackage(ExamenMedicoEntity.class.getPackage())
                .addPackage(OrdenMedicaExamenMedicoLogic.class.getPackage())
                .addPackage(OrdenMedicaPersistence.class.getPackage())
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
        em.createQuery("delete from OrdenMedicaEntity").executeUpdate();
        em.createQuery("delete from ExamenMedicoEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {

        ordenMedica = factory.manufacturePojo(OrdenMedicaEntity.class);
        ordenMedica.setId(1L);
        ordenMedica.setExamenesMedicos(new ArrayList<>());
        em.persist(ordenMedica);

        for (int i = 0; i < 3; i++) {
            ExamenMedicoEntity entity = factory.manufacturePojo(ExamenMedicoEntity.class);
            entity.setOrdenesMedicas(new ArrayList<>());
            entity.getOrdenesMedicas().add(ordenMedica);
            em.persist(entity);
            data.add(entity);
            ordenMedica.getExamenesMedicos().add(entity);
        }
    }

    /**
     * Prueba para asociar una examenMedico a un ordenMedica.
     *
     *
     * @throws co.edu.uniandes.csw.examenMedicostore.exceptions.BusinessLogicException
     */
    @Test
    public void addExamenMedicoTest() throws BusinessLogicException {
        ExamenMedicoEntity newExamenMedico = factory.manufacturePojo(ExamenMedicoEntity.class);
        newExamenMedico.setNombre("Farmatodo");
        newExamenMedico.setCosto(15000);
        newExamenMedico.setRecomendaciones("hola");

        examenMedicoLogic.createExamenMedico(newExamenMedico);
        ExamenMedicoEntity examenMedicoEntity = ordenMedicaExamenMedicoLogic.addExamenMedico(ordenMedica.getId(), newExamenMedico.getId());
        Assert.assertNotNull(examenMedicoEntity);

        Assert.assertEquals(examenMedicoEntity.getId(), newExamenMedico.getId());
        Assert.assertEquals(examenMedicoEntity.getNombre(), newExamenMedico.getNombre());
        Assert.assertEquals(examenMedicoEntity.getRecomendaciones(), newExamenMedico.getRecomendaciones());
        Assert.assertEquals(examenMedicoEntity.getCosto(), newExamenMedico.getCosto(),0);
    }

    /**
     * Prueba para consultar la lista de ExamenMedicos de un ordenMedica.
     */
    @Test
    public void getExamenMedicosTest() {
        List<ExamenMedicoEntity> examenMedicoEntities = ordenMedicaExamenMedicoLogic.getExamenesMedicos(ordenMedica.getId());

        Assert.assertEquals(data.size(), examenMedicoEntities.size());
    }

    /**
     * Prueba para consultar una examenMedico de un ordenMedica.
     *
     * @throws co.edu.uniandes.csw.examenMedicostore.exceptions.BusinessLogicException
     */
    @Test
    public void getExamenMedicoTest() throws BusinessLogicException {
        ExamenMedicoEntity examenMedicoEntity = data.get(0);
        ExamenMedicoEntity examenMedico = ordenMedicaExamenMedicoLogic.getExamenMedico(ordenMedica.getId(), examenMedicoEntity.getId());
        Assert.assertNotNull(examenMedico);

        Assert.assertEquals(examenMedicoEntity.getId(), examenMedico.getId());
        Assert.assertEquals(examenMedicoEntity.getNombre(), examenMedico.getNombre());
        Assert.assertEquals(examenMedicoEntity.getNombre(), examenMedico.getNombre());
        Assert.assertEquals(examenMedicoEntity.getRecomendaciones(), examenMedico.getRecomendaciones());
        Assert.assertEquals(examenMedicoEntity.getCosto(), examenMedico.getCosto(),0);

    }

    /**
     * Prueba desasociar una examenMedico con un ordenMedica.
     *
     */
    @Test
    public void removeExamenMedicoTest() {
        for (ExamenMedicoEntity examenMedico : data) {
            ordenMedicaExamenMedicoLogic.removeExamenMedico(ordenMedica.getId(), examenMedico.getId());
        }
        Assert.assertTrue(ordenMedicaExamenMedicoLogic.getExamenesMedicos(ordenMedica.getId()).isEmpty());
    }
}


