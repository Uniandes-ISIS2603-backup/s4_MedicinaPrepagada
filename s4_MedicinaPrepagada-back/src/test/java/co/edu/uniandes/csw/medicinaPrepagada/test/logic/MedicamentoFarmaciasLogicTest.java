/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.medicinaPrepagada.test.logic;

import co.edu.uniandes.csw.medicinaPrepagada.ejb.FarmaciaLogic;
import co.edu.uniandes.csw.medicinaPrepagada.ejb.MedicamentoFarmaciaLogic;
import co.edu.uniandes.csw.medicinaPrepagada.entities.FarmaciaEntity;
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
 * Pruebas de la lógica entre el recurso Medicamento y Farmacia
 * @autor ncobos
 */
    @RunWith(Arquillian.class)

public class MedicamentoFarmaciasLogicTest {
    
    private PodamFactory factory = new PodamFactoryImpl();

    @Inject
    private MedicamentoFarmaciaLogic medicamentoFarmaciaLogic;

    @Inject
    private FarmaciaLogic farmaciaLogic;

    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;

    private MedicamentoEntity medicamento = new MedicamentoEntity();
    private List<FarmaciaEntity> data = new ArrayList<>();

    /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(MedicamentoEntity.class.getPackage())
                .addPackage(FarmaciaEntity.class.getPackage())
                .addPackage(MedicamentoFarmaciaLogic.class.getPackage())
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
        em.createQuery("delete from FarmaciaEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {

        medicamento = factory.manufacturePojo(MedicamentoEntity.class);
        medicamento.setId(1L);
        medicamento.setFarmacias(new ArrayList<>());
        em.persist(medicamento);

        for (int i = 0; i < 3; i++) {
            FarmaciaEntity entity = factory.manufacturePojo(FarmaciaEntity.class);
            entity.setMedicamentos(new ArrayList<>());
            entity.getMedicamentos().add(medicamento);
            em.persist(entity);
            data.add(entity);
            medicamento.getFarmacias().add(entity);
        }
    }

    /**
     * Prueba para asociar una farmacia a un medicamento.
     *
     *
     * @throws co.edu.uniandes.csw.farmaciastore.exceptions.BusinessLogicException
     */
    @Test
    public void addFarmaciaTest() throws BusinessLogicException {
        FarmaciaEntity newFarmacia = factory.manufacturePojo(FarmaciaEntity.class);
        newFarmacia.setNombre("Farmatodo");
        newFarmacia.setUbicacion("Calle 10 # 10-10");
        newFarmacia.setTelefono(4561234L);
        newFarmacia.setLongitud(-72.03);
        newFarmacia.setLatitud(4.05);
        newFarmacia.setCorreo("abc@example.com");

        farmaciaLogic.createFarmacia(newFarmacia);
        FarmaciaEntity farmaciaEntity = medicamentoFarmaciaLogic.addFarmacia(medicamento.getId(), newFarmacia.getId());
        Assert.assertNotNull(farmaciaEntity);

        Assert.assertEquals(farmaciaEntity.getId(), newFarmacia.getId());
        Assert.assertEquals(farmaciaEntity.getNombre(), newFarmacia.getNombre());
        Assert.assertEquals(farmaciaEntity.getCorreo(), newFarmacia.getCorreo());
        Assert.assertEquals(farmaciaEntity.getUbicacion(), newFarmacia.getUbicacion());
        Assert.assertEquals(farmaciaEntity.getLatitud(), newFarmacia.getLatitud(),0);
        Assert.assertEquals(farmaciaEntity.getLatitud(), newFarmacia.getLatitud(),0);


    }

    /**
     * Prueba para consultar la lista de Farmacias de un medicamento.
     */
    @Test
    public void getFarmaciasTest() {
        List<FarmaciaEntity> farmaciaEntities = medicamentoFarmaciaLogic.getFarmacias(medicamento.getId());

        Assert.assertEquals(data.size(), farmaciaEntities.size());
    }

    /**
     * Prueba para consultar una farmacia de un medicamento.
     *
     * @throws co.edu.uniandes.csw.farmaciastore.exceptions.BusinessLogicException
     */
    @Test
    public void getFarmaciaTest() throws BusinessLogicException {
        FarmaciaEntity farmaciaEntity = data.get(0);
        FarmaciaEntity farmacia = medicamentoFarmaciaLogic.getFarmacia(medicamento.getId(), farmaciaEntity.getId());
        Assert.assertNotNull(farmacia);

        Assert.assertEquals(farmaciaEntity.getId(), farmacia.getId());
        Assert.assertEquals(farmaciaEntity.getNombre(), farmacia.getNombre());
        Assert.assertEquals(farmaciaEntity.getCorreo(), farmacia.getCorreo());
        Assert.assertEquals(farmaciaEntity.getUbicacion(), farmacia.getUbicacion());
        Assert.assertEquals(farmaciaEntity.getLatitud(), farmacia.getLatitud(),0);
        Assert.assertEquals(farmaciaEntity.getLatitud(), farmacia.getLatitud(),0);

    }

    /**
     * Prueba desasociar una farmacia con un medicamento.
     *
     */
    @Test
    public void removeFarmaciaTest() {
        for (FarmaciaEntity farmacia : data) {
            medicamentoFarmaciaLogic.removeFarmacia(medicamento.getId(), farmacia.getId());
        }
        Assert.assertTrue(medicamentoFarmaciaLogic.getFarmacias(medicamento.getId()).isEmpty());
    }
}
