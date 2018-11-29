/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.medicinaPrepagada.test.logic;

import co.edu.uniandes.csw.medicinaPrepagada.ejb.LaboratorioLogic;
import co.edu.uniandes.csw.medicinaPrepagada.ejb.ExamenLaboratorioLogic;
import co.edu.uniandes.csw.medicinaPrepagada.entities.LaboratorioEntity;
import co.edu.uniandes.csw.medicinaPrepagada.entities.ExamenMedicoEntity;
import co.edu.uniandes.csw.medicinaPrepagada.exceptions.BusinessLogicException;
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
 * Pruebas de la lógica entre el recurso ExamenMedico y Laboratorio 
 * @author ncobos
 */

    @RunWith(Arquillian.class)

public class ExamenLaboratorioLogicTest {
        
        private PodamFactory factory = new PodamFactoryImpl();

    @Inject
    private ExamenLaboratorioLogic examenMedicoLaboratorioLogic;

    @Inject
    private LaboratorioLogic laboratorioLogic;

    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;

    private ExamenMedicoEntity examenMedico = new ExamenMedicoEntity();
    private List<LaboratorioEntity> data = new ArrayList<>();

    /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(ExamenMedicoEntity.class.getPackage())
                .addPackage(LaboratorioEntity.class.getPackage())
                .addPackage(ExamenLaboratorioLogic.class.getPackage())
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
        em.createQuery("delete from LaboratorioEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {

        examenMedico = factory.manufacturePojo(ExamenMedicoEntity.class);
        examenMedico.setId(1L);
        examenMedico.setOrdenesMedicas(new ArrayList<>());
        em.persist(examenMedico);

        for (int i = 0; i < 3; i++) {
            LaboratorioEntity entity = factory.manufacturePojo(LaboratorioEntity.class);
            entity.setExamens(new ArrayList<>());
            entity.getExamens().add(examenMedico);
            em.persist(entity);
            data.add(entity);
            examenMedico.getLaboratorios().add(entity);
        }
    }

    /**
     * Prueba para asociar una laboratorio a un examenMedico.
     *
     *
     * @throws co.edu.uniandes.csw.laboratoriostore.exceptions.BusinessLogicException
     */
    @Test
    public void addLaboratorioTest() throws BusinessLogicException {
        LaboratorioEntity newLaboratorio = factory.manufacturePojo(LaboratorioEntity.class);
       newLaboratorio.setNombre("Laboratorio Asanar");
       newLaboratorio.setDireccion("cra 12 # 12-25");
       newLaboratorio.setLatitud(4.6097100);
       newLaboratorio.setLongitud( -74.0817500);
       newLaboratorio.setHorarioAtencion("Lunes a Sabados, 8:00am a 12:00m. Entrega resultados: 1:00pm a 5:00pm");
       newLaboratorio.setTelefono(new Long(4576221));

        laboratorioLogic.createLaboratorio(newLaboratorio);
        LaboratorioEntity laboratorioEntity = examenMedicoLaboratorioLogic.addLaboratorio(examenMedico.getId(), newLaboratorio.getId());
        Assert.assertNotNull(laboratorioEntity);

         Assert.assertEquals(laboratorioEntity.getId(), newLaboratorio.getId());
        Assert.assertEquals(laboratorioEntity.getNombre(), newLaboratorio.getNombre());
        Assert.assertEquals(laboratorioEntity.getDireccion(), newLaboratorio.getDireccion());
        Assert.assertEquals(laboratorioEntity.getLatitud(), newLaboratorio.getLatitud(),0);
        Assert.assertEquals(laboratorioEntity.getLongitud(), newLaboratorio.getLongitud(),0);
        Assert.assertEquals(laboratorioEntity.getHorarioAtencion(), newLaboratorio.getHorarioAtencion());
        Assert.assertEquals(laboratorioEntity.getTelefono(), newLaboratorio.getTelefono());

    }

    /**
     * Prueba para consultar la lista de Laboratorios de un examenMedico.
     */
    @Test
    public void getLaboratoriosTest() {
        List<LaboratorioEntity> laboratorioEntities = examenMedicoLaboratorioLogic.getLaboratorios(examenMedico.getId());

        Assert.assertEquals(data.size(), laboratorioEntities.size());
    }

    /**
     * Prueba para consultar una laboratorio de un examenMedico.
     *
     * @throws co.edu.uniandes.csw.laboratoriostore.exceptions.BusinessLogicException
     */
    @Test
    public void getLaboratorioTest() throws BusinessLogicException {
        LaboratorioEntity laboratorioEntity = data.get(0);
        LaboratorioEntity laboratorio = examenMedicoLaboratorioLogic.getLaboratorio(examenMedico.getId(), laboratorioEntity.getId());
        Assert.assertNotNull(laboratorio);

        Assert.assertEquals(laboratorioEntity.getId(), laboratorio.getId());
        Assert.assertEquals(laboratorioEntity.getId(), laboratorio.getId());
        Assert.assertEquals(laboratorioEntity.getNombre(), laboratorio.getNombre());
        Assert.assertEquals(laboratorioEntity.getDireccion(), laboratorio.getDireccion());
        Assert.assertEquals(laboratorioEntity.getLatitud(), laboratorio.getLatitud(),0);
        Assert.assertEquals(laboratorioEntity.getLongitud(), laboratorio.getLongitud(),0);
        Assert.assertEquals(laboratorioEntity.getHorarioAtencion(), laboratorio.getHorarioAtencion());
        Assert.assertEquals(laboratorioEntity.getTelefono(), laboratorio.getTelefono());

    }

    /**
     * Prueba desasociar una laboratorio con un examenMedico.
     *
     */
    @Test
    public void removeLaboratorioTest() {
        for (LaboratorioEntity laboratorio : data) {
            examenMedicoLaboratorioLogic.removeLaboratorio(examenMedico.getId(), laboratorio.getId());
        }
        Assert.assertTrue(examenMedicoLaboratorioLogic.getLaboratorios(examenMedico.getId()).isEmpty());
    }
    
}