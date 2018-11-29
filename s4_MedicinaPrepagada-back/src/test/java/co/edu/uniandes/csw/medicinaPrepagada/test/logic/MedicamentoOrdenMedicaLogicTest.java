/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.medicinaPrepagada.test.logic;

import co.edu.uniandes.csw.medicinaPrepagada.ejb.OrdenMedicaLogic;
import co.edu.uniandes.csw.medicinaPrepagada.ejb.MedicamentoOrdenMedicaLogic;
import co.edu.uniandes.csw.medicinaPrepagada.entities.OrdenMedicaEntity;
import co.edu.uniandes.csw.medicinaPrepagada.entities.MedicamentoEntity;
import co.edu.uniandes.csw.medicinaPrepagada.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.medicinaPrepagada.persistence.MedicamentoPersistence;
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
 * Pruebas de la lógica entre el recurso Medicamento y OrdenMedica
 * @autor ncobos
 */
    @RunWith(Arquillian.class)

public class MedicamentoOrdenMedicaLogicTest {
    
    private PodamFactory factory = new PodamFactoryImpl();

    @Inject
    private MedicamentoOrdenMedicaLogic medicamentoOrdenMedicaLogic;

    @Inject
    private OrdenMedicaLogic ordenMedicaLogic;

    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;

    private MedicamentoEntity medicamento = new MedicamentoEntity();
    private List<OrdenMedicaEntity> data = new ArrayList<>();

    /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(MedicamentoEntity.class.getPackage())
                .addPackage(OrdenMedicaEntity.class.getPackage())
                .addPackage(MedicamentoOrdenMedicaLogic.class.getPackage())
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
        em.createQuery("delete from OrdenMedicaEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {

        medicamento = factory.manufacturePojo(MedicamentoEntity.class);
        medicamento.setId(1L);
        medicamento.setOrdenesMedicas(new ArrayList<>());
        em.persist(medicamento);

        for (int i = 0; i < 3; i++) {
            OrdenMedicaEntity entity = factory.manufacturePojo(OrdenMedicaEntity.class);
            entity.setMedicamentos(new ArrayList<>());
            entity.getMedicamentos().add(medicamento);
            em.persist(entity);
            data.add(entity);
            medicamento.getOrdenesMedicas().add(entity);
        }
    }

    /**
     * Prueba para asociar una ordenMedica a un medicamento.
     *
     *
     * @throws co.edu.uniandes.csw.ordenMedicastore.exceptions.BusinessLogicException
     */
    @Test
    public void addOrdenMedicaTest() throws BusinessLogicException {
        OrdenMedicaEntity newOrdenMedica = factory.manufacturePojo(OrdenMedicaEntity.class);
         newOrdenMedica.setFirmaMedico("Farmatodo");
        newOrdenMedica.setFechaExpedicion("24/10/2018");
        newOrdenMedica.setComentarios("4561234L");
        newOrdenMedica.setValidaHasta("24/01/2019");

        ordenMedicaLogic.createOrdenMedica(newOrdenMedica);
        OrdenMedicaEntity ordenMedicaEntity = medicamentoOrdenMedicaLogic.addOrdenMedica(medicamento.getId(), newOrdenMedica.getId());
        Assert.assertNotNull(ordenMedicaEntity);

        Assert.assertEquals(ordenMedicaEntity.getId(), newOrdenMedica.getId());
        Assert.assertEquals(ordenMedicaEntity.getFirmaMedico(), newOrdenMedica.getFirmaMedico());
        Assert.assertEquals(ordenMedicaEntity.getFechaExpedicion(), newOrdenMedica.getFechaExpedicion());
        Assert.assertEquals(ordenMedicaEntity.getComentarios(), newOrdenMedica.getComentarios());
        Assert.assertEquals(ordenMedicaEntity.getValidaHasta(), newOrdenMedica.getValidaHasta());


    }

    /**
     * Prueba para consultar la lista de OrdenMedicas de un medicamento.
     */
    @Test
    public void getOrdenMedicasTest() {
        List<OrdenMedicaEntity> ordenMedicaEntities = medicamentoOrdenMedicaLogic.getOrdenMedicas(medicamento.getId());

        Assert.assertEquals(data.size(), ordenMedicaEntities.size());
    }

    /**
     * Prueba para consultar una ordenMedica de un medicamento.
     *
     * @throws co.edu.uniandes.csw.ordenMedicastore.exceptions.BusinessLogicException
     */
    @Test
    public void getOrdenMedicaTest() throws BusinessLogicException {
        OrdenMedicaEntity ordenMedicaEntity = data.get(0);
        OrdenMedicaEntity ordenMedica = medicamentoOrdenMedicaLogic.getOrdenMedica(medicamento.getId(), ordenMedicaEntity.getId());
        Assert.assertNotNull(ordenMedica);

        Assert.assertEquals(ordenMedicaEntity.getId(), ordenMedica.getId());
        Assert.assertEquals(ordenMedicaEntity.getFirmaMedico(), ordenMedica.getFirmaMedico());
        Assert.assertEquals(ordenMedicaEntity.getFechaExpedicion(), ordenMedica.getFechaExpedicion());
        Assert.assertEquals(ordenMedicaEntity.getComentarios(), ordenMedica.getComentarios());
        Assert.assertEquals(ordenMedicaEntity.getValidaHasta(), ordenMedica.getValidaHasta());

    }

    /**
     * Prueba desasociar una ordenMedica con un medicamento.
     *
     */
    @Test
    public void removeOrdenMedicaTest() {
        for (OrdenMedicaEntity ordenMedica : data) {
            medicamentoOrdenMedicaLogic.removeOrdenMedica(medicamento.getId(), ordenMedica.getId());
        }
        Assert.assertTrue(medicamentoOrdenMedicaLogic.getOrdenMedicas(medicamento.getId()).isEmpty());
    }
}
