/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.medicinaPrepagada.test.logic;

import co.edu.uniandes.csw.medicinaPrepagada.ejb.CitaLaboratorioLogic;
import co.edu.uniandes.csw.medicinaPrepagada.ejb.PacienteLogic;
import co.edu.uniandes.csw.medicinaPrepagada.entities.CitaLaboratorioEntity;
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
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 *prubas para la logica paciente cita lab
 * 
 * @author MIGUELHOYOS
 */
@RunWith(Arquillian.class)
public class PacienteCitaLabLogicTest {
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
                .addPackage(CitaLaboratorioLogic.class.getPackage())
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
        em.createQuery("delete from CitaLaboratorioEntity").executeUpdate();
    }
    
    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData(){
        for(int i = 0; i<3 ; i++){
            PacienteEntity entity = factory.manufacturePojo(PacienteEntity.class);
            List<CitaLaboratorioEntity> citasLab = new ArrayList<>();
            for(int j = 0; j<3; j++){
                CitaLaboratorioEntity lab = factory.manufacturePojo(CitaLaboratorioEntity.class);
                citasLab.add(lab);
                lab.setPaciente(entity);
                em.persist(lab);
            }
            entity.setCitasLaboratorio(citasLab);
            em.persist(entity);
            data.add(entity);
        }
    }
    
    /**
     * prueba para obtener las citas lab de un paciente
     */
    @Test
    public void getCitasLabTest() throws BusinessLogicException{
        List<CitaLaboratorioEntity> citasLab = data.get(0).getCitasLaboratorio();
        List<CitaLaboratorioEntity> foundCitasLab = pacienteLogic.darCitasLaboratorio(data.get(0).getId());
        Assert.assertEquals(citasLab.size(), foundCitasLab.size());
    }
    
}
