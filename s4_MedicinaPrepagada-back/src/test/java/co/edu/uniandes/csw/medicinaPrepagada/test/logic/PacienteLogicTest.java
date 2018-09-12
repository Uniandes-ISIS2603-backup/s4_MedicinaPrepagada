/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.medicinaPrepagada.test.logic;

import co.edu.uniandes.csw.medicinaPrepagada.ejb.PacienteLogic;
import co.edu.uniandes.csw.medicinaPrepagada.entities.PacienteEntity;
import co.edu.uniandes.csw.medicinaPrepagada.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.medicinaPrepagada.persistence.PacientePersistence;
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
 *Pruebas Logica de Paciente
 * 
 * @author MIGUELHOYOS
 */
@RunWith(Arquillian.class)
public class PacienteLogicTest {
    
    private PodamFactory factory = new PodamFactoryImpl();
    
    @Inject
    private PacienteLogic pacienteLogic;
    
    @PersistenceContext
    private EntityManager  em;
    
    @Inject
    private UserTransaction utx;
    
    private List<PacienteEntity> data = new ArrayList();
    
    /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(PacienteEntity.class.getPackage())
                .addPackage(PacienteLogic.class.getPackage())
                .addPackage(PacientePersistence.class.getPackage())
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
        em.createQuery("delete from PacienteEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        for (int i = 0; i < 3; i++) {
            PacienteEntity entity = factory.manufacturePojo(PacienteEntity.class);
            em.persist(entity);
            data.add(entity);
        }
       
    }
    
    /**
     * Prueba para crear un Paciente.
     */
    @Test
    public void createPacienteTest() throws BusinessLogicException {
        PacienteEntity newEntity = factory.manufacturePojo(PacienteEntity.class);
        newEntity.setFechaNacimiento("21/10/1997");
        newEntity.setMail("abdcd@udad.com");
        newEntity.setNombre("Miguel Hoyos Ruge");
        newEntity.setDireccion("Cra 22#104-82");
        newEntity.setEps("Sura");
        newEntity.setNumeroContacto(new Long(1234567890));
        PacienteEntity result = pacienteLogic.createPaciente(newEntity);
        Assert.assertNotNull(result);
        PacienteEntity entity = em.find(PacienteEntity.class, result.getId());
        Assert.assertEquals(newEntity.getId(), entity.getId());
    }
    
    /**
     * Prueba para consultar la lista de Pacientes.
     */
    @Test
    public void getPacientesTest() {
        List<PacienteEntity> list = pacienteLogic.getPacientes();
        Assert.assertEquals(data.size(), list.size());
        for (PacienteEntity entity : list) {
            boolean found = false;
            for (PacienteEntity storedEntity : data) {
                if (entity.getId().equals(storedEntity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }
    
    /**
     * Prueba para consultar un Paciente.
     */
    @Test
    public void getPacienteTest() {
        PacienteEntity entity = data.get(0);
        PacienteEntity resultEntity = pacienteLogic.getPaciente(entity.getId());
        Assert.assertNotNull(resultEntity);
        Assert.assertEquals(entity.getId(), resultEntity.getId());
    }
    
    /**
     * Prueba para actualizar un Paciente.
     */
    @Test
    public void updatePacienteTest() throws BusinessLogicException {
        PacienteEntity entity = data.get(0);
        PacienteEntity pojoEntity = factory.manufacturePojo(PacienteEntity.class);

        pojoEntity.setId(entity.getId());
        pojoEntity.setNombre("Pepito Andres Perez Rojas");
        pojoEntity.setMail("abdcd@udad.com");
        pojoEntity.setDireccion("Av Esperanza#104B-82");
        pojoEntity.setNumeroContacto(new Long(1234567890));
        pojoEntity.setEps("Nueva Eps");
        pojoEntity.setFechaNacimiento(entity.getFechaNacimiento());

        pacienteLogic.updatePaciente(pojoEntity);

        PacienteEntity resp = em.find(PacienteEntity.class, entity.getId());

        Assert.assertEquals(pojoEntity.getId(), resp.getId());
    }
    
    /**
     * prueba para eliminar un paciente
     */
    @Test
    public void deletePacienteTest(){
        PacienteEntity entity = data.get(0);
        pacienteLogic.deletePaciente(entity.getId());
        PacienteEntity delet = em.find(PacienteEntity.class, entity.getId());
        Assert.assertNull(delet);
    }


}
