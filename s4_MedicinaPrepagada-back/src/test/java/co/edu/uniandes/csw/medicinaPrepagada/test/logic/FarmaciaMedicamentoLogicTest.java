/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.medicinaPrepagada.test.logic;

import co.edu.uniandes.csw.medicinaPrepagada.ejb.MedicamentoLogic;
import co.edu.uniandes.csw.medicinaPrepagada.ejb.FarmaciaMedicamentoLogic;
import co.edu.uniandes.csw.medicinaPrepagada.entities.MedicamentoEntity;
import co.edu.uniandes.csw.medicinaPrepagada.entities.FarmaciaEntity;
import co.edu.uniandes.csw.medicinaPrepagada.exceptions.BusinessLogicException;
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
 * Pruebas de la lógica entre el recurso Farmacia y Medicamento
 * @author ncobos
 */

    @RunWith(Arquillian.class)

public class FarmaciaMedicamentoLogicTest {
    
private PodamFactory factory = new PodamFactoryImpl();

    @Inject
    private FarmaciaMedicamentoLogic farmaciaMedicamentoLogic;

    @Inject
    private MedicamentoLogic medicamentoLogic;

    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;

    private FarmaciaEntity farmacia = new FarmaciaEntity();
    private List<MedicamentoEntity> data = new ArrayList<>();

    /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(FarmaciaEntity.class.getPackage())
                .addPackage(MedicamentoEntity.class.getPackage())
                .addPackage(FarmaciaMedicamentoLogic.class.getPackage())
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
        em.createQuery("delete from MedicamentoEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {

        farmacia = factory.manufacturePojo(FarmaciaEntity.class);
        farmacia.setId(1L);
        farmacia.setMedicamentos(new ArrayList<>());
        em.persist(farmacia);

        for (int i = 0; i < 3; i++) {
            MedicamentoEntity entity = factory.manufacturePojo(MedicamentoEntity.class);
            entity.setFarmacias(new ArrayList<>());
            entity.getFarmacias().add(farmacia);
            em.persist(entity);
            data.add(entity);
            farmacia.getMedicamentos().add(entity);
        }
    }

    /**
     * Prueba para asociar una medicamento a un farmacia.
     *
     *
     * @throws co.edu.uniandes.csw.medicamentostore.exceptions.BusinessLogicException
     */
    @Test
    public void addMedicamentoTest() throws BusinessLogicException {
        MedicamentoEntity newMedicamento = factory.manufacturePojo(MedicamentoEntity.class);
        newMedicamento.setNombre("Dolex");
        newMedicamento.setElaboradoPor("Glaxo Smith Kline");
        newMedicamento.setDescripcion("Tomar mucha agua");
        newMedicamento.setCosto(15000);
        newMedicamento.setCantidad("20 mg");

        medicamentoLogic.createMedicamento(newMedicamento);
        MedicamentoEntity medicamentoEntity = farmaciaMedicamentoLogic.addMedicamento(farmacia.getId(), newMedicamento.getId());
        Assert.assertNotNull(medicamentoEntity);

         Assert.assertEquals(medicamentoEntity.getId(), newMedicamento.getId());
        Assert.assertEquals(medicamentoEntity.getNombre(), newMedicamento.getNombre());
        Assert.assertEquals(medicamentoEntity.getElaboradoPor(), newMedicamento.getElaboradoPor());
        Assert.assertEquals(medicamentoEntity.getDescripcion(), newMedicamento.getDescripcion());
        Assert.assertEquals(medicamentoEntity.getCosto(), newMedicamento.getCosto(),0);
        Assert.assertEquals(medicamentoEntity.getCantidad(), newMedicamento.getCantidad());


    }

    /**
     * Prueba para consultar la lista de Medicamentos de un farmacia.
     */
    @Test
    public void getMedicamentosTest() {
        List<MedicamentoEntity> medicamentoEntities = farmaciaMedicamentoLogic.getMedicamentos(farmacia.getId());

        Assert.assertEquals(data.size(), medicamentoEntities.size());
    }

    /**
     * Prueba para consultar una medicamento de un farmacia.
     *
     * @throws co.edu.uniandes.csw.medicamentostore.exceptions.BusinessLogicException
     */
    @Test
    public void getMedicamentoTest() throws BusinessLogicException {
        MedicamentoEntity medicamentoEntity = data.get(0);
        MedicamentoEntity medicamento = farmaciaMedicamentoLogic.getMedicamento(farmacia.getId(), medicamentoEntity.getId());
        Assert.assertNotNull(medicamento);

        Assert.assertEquals(medicamentoEntity.getId(), medicamento.getId());
        Assert.assertEquals(medicamentoEntity.getNombre(), medicamento.getNombre());
        Assert.assertEquals(medicamentoEntity.getElaboradoPor(), medicamento.getElaboradoPor());
        Assert.assertEquals(medicamentoEntity.getDescripcion(), medicamento.getDescripcion());
        Assert.assertEquals(medicamentoEntity.getCosto(), medicamento.getCosto(),0);
        Assert.assertEquals(medicamentoEntity.getCantidad(), medicamento.getCantidad());

    }

    /**
     * Prueba desasociar una medicamento con un farmacia.
     *
     */
    @Test
    public void removeMedicamentoTest() {
        for (MedicamentoEntity medicamento : data) {
            farmaciaMedicamentoLogic.removeMedicamento(farmacia.getId(), medicamento.getId());
        }
        Assert.assertTrue(farmaciaMedicamentoLogic.getMedicamentos(farmacia.getId()).isEmpty());
    }
    
}

