/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.medicinaPrepagada.test.logic;

import co.edu.uniandes.csw.medicinaPrepagada.ejb.TarjetaCreditoLogic;
import co.edu.uniandes.csw.medicinaPrepagada.entities.TarjetaCreditoEntity;
import co.edu.uniandes.csw.medicinaPrepagada.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.medicinaPrepagada.persistence.TarjetaCreditoPersistence;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import junit.framework.Assert;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

/**
 *Prueba Logica TarjetaCredito
 * 
 * @author MIGUELHOYOS
 */
@RunWith(Arquillian.class)
public class TarjetaCreditoLogicTest {
    
    private PodamFactory factory = new PodamFactoryImpl();
    
    @Inject
    private TarjetaCreditoLogic tarjetaCreditoLogic;
    
    @PersistenceContext
    private EntityManager em;
    
    @Inject
    private UserTransaction utx;
    
    private List<TarjetaCreditoEntity> data = new ArrayList<>();
    
     /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(TarjetaCreditoEntity.class.getPackage())
                .addPackage(TarjetaCreditoLogic.class.getPackage())
                .addPackage(TarjetaCreditoPersistence.class.getPackage())
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
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        for (int i = 0; i < 3; i++) {
            TarjetaCreditoEntity entity = factory.manufacturePojo(TarjetaCreditoEntity.class);
            em.persist(entity);
            data.add(entity);
        }
       
    }
    
    /**
     * Prueba para crear una tarjeta de credito
     * @throws BusinessLogicException 
     */
    @Test
    public void crearTarjetaCreditoTest() throws BusinessLogicException{
        TarjetaCreditoEntity newEntity = factory.manufacturePojo(TarjetaCreditoEntity.class);
        
        newEntity.setNumero(5555555555554444L);
        newEntity.setFranquicia("MasterCard");
        newEntity.setFechaExpiracion("11/21");
        newEntity.setCodigoSeguridad(123);
        newEntity.setNombreEnTarjeta("NICOLAS MARTOJO TIEROOJIL");
        
        TarjetaCreditoEntity result = tarjetaCreditoLogic.createTarjetaCredito(newEntity);
        Assert.assertNotNull(result);
        
        TarjetaCreditoEntity entity = em.find(TarjetaCreditoEntity.class, result.getNumero());
        Assert.assertEquals(entity.getNumero(), newEntity.getNumero());
    }
    
    /**
     * prueba para eliminar una tarjeta
     */
    @Test
    public void deleteTarjetaCreditoTest(){
        TarjetaCreditoEntity entity = data.get(0);
        tarjetaCreditoLogic.deleteTarjetaCredito(entity.getNumero());
        TarjetaCreditoEntity deleted = em.find(TarjetaCreditoEntity.class, entity.getNumero());
                
    }
    
    /**
     * test para obtener una tarjeta de credito
     */
    @Test
    public void getTarjetaCreditoTest(){
       TarjetaCreditoEntity ent = data.get(0);
       TarjetaCreditoEntity result = tarjetaCreditoLogic.getTarjetaCredito(ent.getNumero());
       Assert.assertNotNull(result);
       Assert.assertEquals(ent.getFechaExpiracion(), result.getFechaExpiracion());
       Assert.assertEquals(ent.getCodigoSeguridad(), result.getCodigoSeguridad());
       Assert.assertEquals(ent.getFranquicia(), result.getFranquicia());
       Assert.assertEquals(ent.getNombreEnTarjeta(), result.getNombreEnTarjeta());
    }
    
    /**
     * Prueba para crear una tarjeta de credito con un numero no valido
     * @throws BusinessLogicException 
     */
    @Test(expected = BusinessLogicException.class)
    public void crearTarjetaCreditoTestConNumeroNoValido() throws BusinessLogicException{
        TarjetaCreditoEntity newEntity = factory.manufacturePojo(TarjetaCreditoEntity.class);
        
        newEntity.setNumero(5555555555554044L);
        newEntity.setFranquicia("MasterCard");
        newEntity.setFechaExpiracion("11/21");
        newEntity.setCodigoSeguridad(123);
        newEntity.setNombreEnTarjeta("NICOLAS MARTOJO TIEROOJIL");
        
        TarjetaCreditoEntity result = tarjetaCreditoLogic.createTarjetaCredito(newEntity);

    }
    
    /**
     * Prueba para crear una tarjeta de credito con un numero que no coincida con la franquicia
     * @throws BusinessLogicException 
     */
    @Test(expected = BusinessLogicException.class)
    public void crearTarjetaCreditoTestConNumeroQueNoCoincidaConLaFranquicia() throws BusinessLogicException{
        TarjetaCreditoEntity newEntity = factory.manufacturePojo(TarjetaCreditoEntity.class);
        
        newEntity.setNumero(5555555555554444L);
        newEntity.setFranquicia("Visa");
        newEntity.setFechaExpiracion("11/21");
        newEntity.setCodigoSeguridad(123);
        newEntity.setNombreEnTarjeta("NICOLAS MARTOJO TIEROOJIL");
        
        TarjetaCreditoEntity result = tarjetaCreditoLogic.createTarjetaCredito(newEntity);

    }
    
    /**
     * Prueba para crear una tarjeta de credito con una fecha de expiarcion que ya paso
     * @throws BusinessLogicException 
     */
    @Test(expected = BusinessLogicException.class)
    public void crearTarjetaCreditoTestConFechaExpiracionPasada() throws BusinessLogicException{
        TarjetaCreditoEntity newEntity = factory.manufacturePojo(TarjetaCreditoEntity.class);
        
        newEntity.setNumero(5555555555554444L);
        newEntity.setFranquicia("MasterCard");
        newEntity.setFechaExpiracion("11/08");
        newEntity.setCodigoSeguridad(123);
        newEntity.setNombreEnTarjeta("NICOLAS MARTOJO TIEROOJIL");
        
        TarjetaCreditoEntity result = tarjetaCreditoLogic.createTarjetaCredito(newEntity);

    }
    
     /**
     * Prueba para crear una tarjeta de credito con una fecha de expiarcion 20 anos mayor
     * @throws BusinessLogicException 
     */
    @Test(expected = BusinessLogicException.class)
    public void crearTarjetaCreditoTestConFechaExpiracionFutura() throws BusinessLogicException{
        TarjetaCreditoEntity newEntity = factory.manufacturePojo(TarjetaCreditoEntity.class);
        
        newEntity.setNumero(5555555555554444L);
        newEntity.setFranquicia("MasterCard");
        newEntity.setFechaExpiracion("11/50");
        newEntity.setCodigoSeguridad(123);
        newEntity.setNombreEnTarjeta("NICOLAS MARTOJO TIEROOJIL");
        
        TarjetaCreditoEntity result = tarjetaCreditoLogic.createTarjetaCredito(newEntity);

    }
    
    /**
     * Prueba para crear una tarjeta de credito con una fecha de expiarcion que no cumpla el formato
     * @throws BusinessLogicException 
     */
    @Test(expected = BusinessLogicException.class)
    public void crearTarjetaCreditoTestConFechaExpiracionNoCumpleFormato() throws BusinessLogicException{
        TarjetaCreditoEntity newEntity = factory.manufacturePojo(TarjetaCreditoEntity.class);
        
        newEntity.setNumero(5555555555554444L);
        newEntity.setFranquicia("MasterCard");
        newEntity.setFechaExpiracion("1150");
        newEntity.setCodigoSeguridad(123);
        newEntity.setNombreEnTarjeta("NICOLAS MARTOJO TIEROOJIL");
        
        TarjetaCreditoEntity result = tarjetaCreditoLogic.createTarjetaCredito(newEntity);

    }
    
     
    /**
     * Prueba para crear una tarjeta de credito con un codigo de seguridad invalido
     * @throws BusinessLogicException 
     */
    @Test(expected = BusinessLogicException.class)
    public void crearTarjetaCreditoTestConCcvInvalido() throws BusinessLogicException{
        TarjetaCreditoEntity newEntity = factory.manufacturePojo(TarjetaCreditoEntity.class);
        
        newEntity.setNumero(5555555555554444L);
        newEntity.setFranquicia("MasterCard");
        newEntity.setFechaExpiracion("11/25");
        newEntity.setCodigoSeguridad(12);
        newEntity.setNombreEnTarjeta("NICOLAS MARTOJO TIEROOJIL");
        
        TarjetaCreditoEntity result = tarjetaCreditoLogic.createTarjetaCredito(newEntity);

    }
    
     /**
     * Prueba para crear una tarjeta de credito con un nombre en la tarjeta invalido
     * @throws BusinessLogicException 
     */
    @Test(expected = BusinessLogicException.class)
    public void crearTarjetaCreditoTestConNombreInvalido() throws BusinessLogicException{
        TarjetaCreditoEntity newEntity = factory.manufacturePojo(TarjetaCreditoEntity.class);
        
        newEntity.setNumero(5555555555554444L);
        newEntity.setFranquicia("MasterCard");
        newEntity.setFechaExpiracion("11/25");
        newEntity.setCodigoSeguridad(123);
        newEntity.setNombreEnTarjeta("nicolas martojo tieroojil");
        
        TarjetaCreditoEntity result = tarjetaCreditoLogic.createTarjetaCredito(newEntity);

    }
}
