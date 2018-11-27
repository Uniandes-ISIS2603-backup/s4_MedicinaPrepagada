/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.medicinaPrepagada.test.logic;

import co.edu.uniandes.csw.medicinaPrepagada.ejb.PacienteLogic;
import co.edu.uniandes.csw.medicinaPrepagada.ejb.TarjetaCreditoLogic;
import co.edu.uniandes.csw.medicinaPrepagada.entities.PacienteEntity;
import co.edu.uniandes.csw.medicinaPrepagada.entities.TarjetaCreditoEntity;
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
 *
 * @author MIGUELHOYOS
 */
@RunWith(Arquillian.class)
public class PacienteTarjetasCreditoTest {
   private PodamFactory factory = new PodamFactoryImpl();
    
    @Inject
    private PacienteLogic pacienteLogic;
    
    @Inject
    private TarjetaCreditoLogic tarjetaCreditoLogic;
    
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
                .addPackage(TarjetaCreditoLogic.class.getPackage())
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
        em.createQuery("delete from TarjetaCreditoEntity").executeUpdate();
        em.createQuery("delete from PacienteEntity").executeUpdate();
    }
    
    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData(){
        for(int i = 0; i<3 ; i++){
            PacienteEntity entity = factory.manufacturePojo(PacienteEntity.class);
            List<TarjetaCreditoEntity> tarjetas = new ArrayList<>();
            for(int j = 0; j<3; j++){
                TarjetaCreditoEntity tarj = factory.manufacturePojo(TarjetaCreditoEntity.class);
                tarjetas.add(tarj);
                tarj.setPaciente(entity);
                em.persist(tarj);
            }
            entity.setTarjetasCredito(tarjetas);
            em.persist(entity);
            data.add(entity);
        }
    }
    
    /**
     * prueba para obtener las tarjetas de un paciente
     */
    @Test
    public void getTarjetasTest(){
        List<TarjetaCreditoEntity> tarjetas = data.get(0).getTarjetasCredito();
        List<TarjetaCreditoEntity> foundTarjetas = pacienteLogic.getTarjetasCreditoPaciente(data.get(0).getId());
        Assert.assertEquals(tarjetas.size(), foundTarjetas.size());
    }
    
    /**
     * prueba para asignarle una tarjeta de credito a un paciente
     */
    @Test
    public void asignarTarjetaAPacienteTest() throws BusinessLogicException{
        TarjetaCreditoEntity newTarjeta = factory.manufacturePojo(TarjetaCreditoEntity.class);
        
        newTarjeta.setNumero(5555555555554444L);
        newTarjeta.setFranquicia("MasterCard");
        newTarjeta.setFechaExpiracion("11/21");
        newTarjeta.setCodigoSeguridad(123);
        newTarjeta.setNombreEnTarjeta("NICOLAS MARTOJO TIEROOJIL");
        tarjetaCreditoLogic.createTarjetaCredito(newTarjeta);
        pacienteLogic.agregarTarjetaCreditoAPaciente(data.get(1).getId(), newTarjeta);
        
        boolean found = false;
        
        for(TarjetaCreditoEntity tarj: pacienteLogic.getTarjetasCreditoPaciente(data.get(1).getId())){
            if(tarj.getNumero().equals(newTarjeta.getNumero())){
                found = true;
            }
        }
        Assert.assertTrue(found);
        Assert.assertEquals(4, pacienteLogic.getTarjetasCreditoPaciente(data.get(1).getId()).size());
    }
}
