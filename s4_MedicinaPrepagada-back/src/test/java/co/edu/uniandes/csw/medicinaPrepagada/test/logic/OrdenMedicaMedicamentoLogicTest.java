/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.medicinaPrepagada.test.logic;

import co.edu.uniandes.csw.medicinaPrepagada.ejb.MedicamentoLogic;
import co.edu.uniandes.csw.medicinaPrepagada.ejb.OrdenMedicaMedicamentoLogic;
import co.edu.uniandes.csw.medicinaPrepagada.entities.MedicamentoEntity;
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
 * Pruebas de la lógica entre el recurso OrdenMedica y Medicamento
 * @author ncobos
 */

    @RunWith(Arquillian.class)

public class OrdenMedicaMedicamentoLogicTest {
    
private PodamFactory factory = new PodamFactoryImpl();

    @Inject
    private OrdenMedicaMedicamentoLogic ordenMedicaMedicamentoLogic;

    @Inject
    private MedicamentoLogic medicamentoLogic;

    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;

    private OrdenMedicaEntity ordenMedica = new OrdenMedicaEntity();
    private List<MedicamentoEntity> data = new ArrayList<>();

    /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(OrdenMedicaEntity.class.getPackage())
                .addPackage(MedicamentoEntity.class.getPackage())
                .addPackage(OrdenMedicaMedicamentoLogic.class.getPackage())
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
        em.createQuery("delete from MedicamentoEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {

        ordenMedica = factory.manufacturePojo(OrdenMedicaEntity.class);
        ordenMedica.setId(1L);
        ordenMedica.setMedicamentos(new ArrayList<>());
        em.persist(ordenMedica);

        for (int i = 0; i < 3; i++) {
            MedicamentoEntity entity = factory.manufacturePojo(MedicamentoEntity.class);
            entity.setOrdenesMedicas(new ArrayList<>());
            entity.getOrdenesMedicas().add(ordenMedica);
            em.persist(entity);
            data.add(entity);
            ordenMedica.getMedicamentos().add(entity);
        }
    }

    /**
     * Prueba para asociar una medicamento a un ordenMedica.
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
        MedicamentoEntity medicamentoEntity = ordenMedicaMedicamentoLogic.addMedicamento(ordenMedica.getId(), newMedicamento.getId());
        Assert.assertNotNull(medicamentoEntity);

         Assert.assertEquals(medicamentoEntity.getId(), newMedicamento.getId());
        Assert.assertEquals(medicamentoEntity.getNombre(), newMedicamento.getNombre());
        Assert.assertEquals(medicamentoEntity.getElaboradoPor(), newMedicamento.getElaboradoPor());
        Assert.assertEquals(medicamentoEntity.getDescripcion(), newMedicamento.getDescripcion());
        Assert.assertEquals(medicamentoEntity.getCosto(), newMedicamento.getCosto(),0);
        Assert.assertEquals(medicamentoEntity.getCantidad(), newMedicamento.getCantidad());


    }

    /**
     * Prueba para consultar la lista de Medicamentos de un ordenMedica.
     */
    @Test
    public void getMedicamentosTest() {
        List<MedicamentoEntity> medicamentoEntities = ordenMedicaMedicamentoLogic.getMedicamentos(ordenMedica.getId());

        Assert.assertEquals(data.size(), medicamentoEntities.size());
    }

    /**
     * Prueba para consultar una medicamento de un ordenMedica.
     *
     * @throws co.edu.uniandes.csw.medicamentostore.exceptions.BusinessLogicException
     */
    @Test
    public void getMedicamentoTest() throws BusinessLogicException {
        MedicamentoEntity medicamentoEntity = data.get(0);
        MedicamentoEntity medicamento = ordenMedicaMedicamentoLogic.getMedicamento(ordenMedica.getId(), medicamentoEntity.getId());
        Assert.assertNotNull(medicamento);

        Assert.assertEquals(medicamentoEntity.getId(), medicamento.getId());
        Assert.assertEquals(medicamentoEntity.getNombre(), medicamento.getNombre());
        Assert.assertEquals(medicamentoEntity.getElaboradoPor(), medicamento.getElaboradoPor());
        Assert.assertEquals(medicamentoEntity.getDescripcion(), medicamento.getDescripcion());
        Assert.assertEquals(medicamentoEntity.getCosto(), medicamento.getCosto(),0);
        Assert.assertEquals(medicamentoEntity.getCantidad(), medicamento.getCantidad());

    }

    /**
     * Prueba desasociar una medicamento con un ordenMedica.
     *
     */
    @Test
    public void removeMedicamentoTest() {
        for (MedicamentoEntity medicamento : data) {
            ordenMedicaMedicamentoLogic.removeMedicamento(ordenMedica.getId(), medicamento.getId());
        }
        Assert.assertTrue(ordenMedicaMedicamentoLogic.getMedicamentos(ordenMedica.getId()).isEmpty());
    }
    
}


