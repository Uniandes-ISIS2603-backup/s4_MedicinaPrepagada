/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.medicinaPrepagada.test.logic;

import co.edu.uniandes.csw.medicinaPrepagada.ejb.ExamenMedicoLogic;
import co.edu.uniandes.csw.medicinaPrepagada.ejb.LaboratorioExamenLogic;
import co.edu.uniandes.csw.medicinaPrepagada.entities.ExamenMedicoEntity;
import co.edu.uniandes.csw.medicinaPrepagada.entities.LaboratorioEntity;
import co.edu.uniandes.csw.medicinaPrepagada.exceptions.BusinessLogicException;
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
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

/**
 * Pruebas de la lógica entre el recurso Laboratorio y ExamenMedico
 * @author ncobos
 */
    @RunWith(Arquillian.class)

public class LaboratorioExamenLogicTest {
    
        private PodamFactory factory = new PodamFactoryImpl();

    @Inject
    private LaboratorioExamenLogic laboratorioExamenMedicoLogic;

    @Inject
    private ExamenMedicoLogic examenMedicoLogic;

    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;

    private LaboratorioEntity laboratorio = new LaboratorioEntity();
    private List<ExamenMedicoEntity> data = new ArrayList<>();

    /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(LaboratorioEntity.class.getPackage())
                .addPackage(ExamenMedicoEntity.class.getPackage())
                .addPackage(LaboratorioExamenLogic.class.getPackage())
                .addPackage(LaboratorioPersistence.class.getPackage())
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
        em.createQuery("delete from LaboratorioEntity").executeUpdate();
        em.createQuery("delete from ExamenMedicoEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {

        laboratorio = factory.manufacturePojo(LaboratorioEntity.class);
        laboratorio.setId(1L);
        laboratorio.setExamens(new ArrayList<>());
        em.persist(laboratorio);

        for (int i = 0; i < 3; i++) {
            ExamenMedicoEntity entity = factory.manufacturePojo(ExamenMedicoEntity.class);
            entity.setLaboratorios(new ArrayList<>());
            entity.getLaboratorios().add(laboratorio);
            em.persist(entity);
            data.add(entity);
            laboratorio.getExamens().add(entity);
        }
    }

    /**
     * Prueba para asociar una examenMedico a un laboratorio.
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
        ExamenMedicoEntity examenMedicoEntity = laboratorioExamenMedicoLogic.addExamen(laboratorio.getId(), newExamenMedico.getId());
        Assert.assertNotNull(examenMedicoEntity);

        Assert.assertEquals(examenMedicoEntity.getId(), newExamenMedico.getId());
        Assert.assertEquals(examenMedicoEntity.getNombre(), newExamenMedico.getNombre());
        Assert.assertEquals(examenMedicoEntity.getRecomendaciones(), newExamenMedico.getRecomendaciones());
        Assert.assertEquals(examenMedicoEntity.getCosto(), newExamenMedico.getCosto(),0);
    }

    /**
     * Prueba para consultar la lista de ExamenMedicos de un laboratorio.
     */
    @Test
    public void getExamenMedicosTest() {
        List<ExamenMedicoEntity> examenMedicoEntities = laboratorioExamenMedicoLogic.getExamens(laboratorio.getId());

        Assert.assertEquals(data.size(), examenMedicoEntities.size());
    }

    /**
     * Prueba para consultar una examenMedico de un laboratorio.
     *
     * @throws co.edu.uniandes.csw.examenMedicostore.exceptions.BusinessLogicException
     */
    @Test
    public void getExamenMedicoTest() throws BusinessLogicException {
        ExamenMedicoEntity examenMedicoEntity = data.get(0);
        ExamenMedicoEntity examenMedico = laboratorioExamenMedicoLogic.getExamen(laboratorio.getId(), examenMedicoEntity.getId());
        Assert.assertNotNull(examenMedico);

        Assert.assertEquals(examenMedicoEntity.getId(), examenMedico.getId());
        Assert.assertEquals(examenMedicoEntity.getNombre(), examenMedico.getNombre());
        Assert.assertEquals(examenMedicoEntity.getNombre(), examenMedico.getNombre());
        Assert.assertEquals(examenMedicoEntity.getRecomendaciones(), examenMedico.getRecomendaciones());
        Assert.assertEquals(examenMedicoEntity.getCosto(), examenMedico.getCosto(),0);

    }

    /**
     * Prueba desasociar una examenMedico con un laboratorio.
     *
     */
    @Test
    public void removeExamenMedicoTest() {
        for (ExamenMedicoEntity examenMedico : data) {
            laboratorioExamenMedicoLogic.removeExamen(laboratorio.getId(), examenMedico.getId());
        }
        Assert.assertTrue(laboratorioExamenMedicoLogic.getExamens(laboratorio.getId()).isEmpty());
    }
}

