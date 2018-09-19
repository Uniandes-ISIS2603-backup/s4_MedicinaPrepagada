/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.medicinaPrepagada.test.logic;

import co.edu.uniandes.csw.medicinaPrepagada.ejb.FarmaciaLogic;
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
 * Pruebas de logica de farmacia
 *
 * @author ncobos
 */
@RunWith(Arquillian.class)
public class FarmaciaLogicTest {
        private PodamFactory factory = new PodamFactoryImpl();

    @Inject
    private FarmaciaLogic farmaciaLogic;

    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;

    private List<FarmaciaEntity> data = new ArrayList();

   // private List<BookEntity> booksData = new ArrayList();
    
    /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(FarmaciaEntity.class.getPackage())
                .addPackage(FarmaciaLogic.class.getPackage())
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
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        for (int i = 0; i < 3; i++) {
            FarmaciaEntity entity = factory.manufacturePojo(FarmaciaEntity.class);
            em.persist(entity);
            data.add(entity);
        }

    }
    
   /**
     * Prueba para crear un Farmacia.
     */
    @Test
    public void createFarmaciaTest() throws BusinessLogicException {
        FarmaciaEntity newEntity = factory.manufacturePojo(FarmaciaEntity.class);
        newEntity.setNombre("Farmatodo");
        newEntity.setUbicacion("Calle 10 # 10-10");
        newEntity.setTelefono(4561234L);
        newEntity.setLongitud(-72.03);
        newEntity.setLatitud(4.05);
        newEntity.setCorreo("abc@example.com");

        FarmaciaEntity result = farmaciaLogic.createFarmacia(newEntity);
        Assert.assertNotNull(result);
        FarmaciaEntity entity = em.find(FarmaciaEntity.class, result.getId());
        Assert.assertEquals(newEntity.getId(), entity.getId());
        Assert.assertEquals(newEntity.getNombre(), entity.getNombre());
        Assert.assertEquals(newEntity.getUbicacion(), entity.getUbicacion());
        Assert.assertEquals(newEntity.getTelefono(), entity.getTelefono());
        Assert.assertEquals(newEntity.getLatitud(), entity.getLatitud(),0);
        Assert.assertEquals(newEntity.getLongitud(), entity.getLongitud(),0);
        Assert.assertEquals(newEntity.getCorreo(), entity.getCorreo());
    }
    
    /**
     * Prueba para consultar la lista de Farmacias.
     */
    @Test
    public void getFarmaciasTest() {
        List<FarmaciaEntity> list = farmaciaLogic.getFarmacias();
        Assert.assertEquals(data.size(), list.size());
        for (FarmaciaEntity entity : list) {
            boolean found = false;
            for (FarmaciaEntity storedEntity : data) {
                if (entity.getId().equals(storedEntity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }
    
    /**
     * Prueba para consultar un Farmacia.
     */
    @Test
    public void getFarmaciaTest() {
        FarmaciaEntity entity = data.get(0);
        FarmaciaEntity resultEntity = farmaciaLogic.getFarmacia(entity.getId());
        Assert.assertNotNull(resultEntity);
        Assert.assertEquals(entity.getId(), resultEntity.getId());
    }
    
    /**
     * Prueba para actualizar un Farmacia.
     */
    @Test
    public void updateFarmaciaTest() throws BusinessLogicException {
        FarmaciaEntity entity = data.get(1);
        FarmaciaEntity pojoEntity = factory.manufacturePojo(FarmaciaEntity.class);

        pojoEntity.setId(entity.getId());
        pojoEntity.setNombre("Farmatodo");
        pojoEntity.setUbicacion(entity.getUbicacion());
        pojoEntity.setTelefono(4561234L);
        pojoEntity.setLongitud(entity.getLongitud());
        pojoEntity.setLatitud(entity.getLatitud());
        pojoEntity.setCorreo("abc@example.com");

        farmaciaLogic.updateFarmacia(entity.getId(), pojoEntity);

        FarmaciaEntity resp = em.find(FarmaciaEntity.class, entity.getId());

        Assert.assertEquals(pojoEntity.getId(), resp.getId());
        Assert.assertEquals(pojoEntity.getNombre(), resp.getNombre());
        Assert.assertEquals(pojoEntity.getUbicacion(), resp.getUbicacion());
        Assert.assertEquals(pojoEntity.getTelefono(), resp.getTelefono());
        Assert.assertEquals(pojoEntity.getLatitud(), resp.getLatitud(),0);
        Assert.assertEquals(pojoEntity.getLongitud(), resp.getLongitud(),0);
        Assert.assertEquals(pojoEntity.getCorreo(), resp.getCorreo());

        
    }
    
    /**
     * prueba para eliminar un farmacia
     */
    @Test
    public void deleteFarmaciaTest() throws BusinessLogicException{
        FarmaciaEntity entity = data.get(0);
        farmaciaLogic.deleteFarmacia(entity.getId());
        FarmaciaEntity delet = em.find(FarmaciaEntity.class, entity.getId());
        Assert.assertNull(delet);
    }
    
    /**
     * Prueba para crear un farmacia con un nombre reptido
     * @throws BusinessLogicException 
     */
    @Test(expected = BusinessLogicException.class)
    public void crearFarmaciaConNombreRepetido() throws BusinessLogicException{
        FarmaciaEntity newEntity = factory.manufacturePojo(FarmaciaEntity.class);
        newEntity.setNombre(data.get(0).getNombre());
        
        farmaciaLogic.createFarmacia(newEntity);
    }

    
    /**
     * Prueba para crear un farmacia con una ubicacion invalida
     * @throws BusinessLogicException 
     */
    @Test(expected = BusinessLogicException.class)
    public void crearFarmaciaConUbicacionInvalida() throws BusinessLogicException{
        FarmaciaEntity newEntity = factory.manufacturePojo(FarmaciaEntity.class);
        newEntity.setNombre("Farmatodo");
        newEntity.setUbicacion("Calle 10 10-10");
        newEntity.setTelefono(4561234L);
        newEntity.setLongitud(-72.03);
        newEntity.setLatitud(4.05);
        newEntity.setCorreo("abc@example.com");
        
        farmaciaLogic.createFarmacia(newEntity);
    }
    
    /**
     * Prueba para crear un farmacia con un telefono invalido
     * @throws BusinessLogicException 
     */
    @Test(expected = BusinessLogicException.class)
    public void crearFarmaciaConTelefonoInvalido() throws BusinessLogicException{
        FarmaciaEntity newEntity = factory.manufacturePojo(FarmaciaEntity.class);
        newEntity.setNombre("Farmatodo");
        newEntity.setUbicacion("Calle 10 #10-10");
        newEntity.setTelefono(1L);
        newEntity.setLongitud(-72.03);
        newEntity.setLatitud(4.05);
        newEntity.setCorreo("abc@example.com");
        
        farmaciaLogic.createFarmacia(newEntity);
    }

    /**
     * Prueba para crear un farmacia con una longitud invalida
     * @throws BusinessLogicException 
     */
    @Test(expected = BusinessLogicException.class)
    public void crearFarmaciaConLongitudInvalida() throws BusinessLogicException{
        FarmaciaEntity newEntity = factory.manufacturePojo(FarmaciaEntity.class);
        newEntity.setNombre("Farmatodo");
        newEntity.setUbicacion("Calle 10 #10-10");
        newEntity.setTelefono(3456789L);
        newEntity.setLongitud(100000);
        newEntity.setLatitud(4.05);
        newEntity.setCorreo("abc@example.com");
        
        farmaciaLogic.createFarmacia(newEntity);
    }
    
    /**
     * Prueba para crear un farmacia con una latitud invalida
     * @throws BusinessLogicException 
     */
    @Test(expected = BusinessLogicException.class)
    public void crearFarmaciaConLatitudInvalida() throws BusinessLogicException{
        FarmaciaEntity newEntity = factory.manufacturePojo(FarmaciaEntity.class);
        newEntity.setNombre("Farmatodo");
        newEntity.setUbicacion("Calle 10 #10-10");
        newEntity.setTelefono(3456789L);
        newEntity.setLongitud(-72.03);
        newEntity.setLatitud(400);
        newEntity.setCorreo("abc@example.com");
        
        farmaciaLogic.createFarmacia(newEntity);
    }

     /**
     * Prueba para crear un farmacia con una latitud invalida
     * @throws BusinessLogicException 
     */
    @Test(expected = BusinessLogicException.class)
    public void crearFarmaciaConCorreoInvalido() throws BusinessLogicException{
        FarmaciaEntity newEntity = factory.manufacturePojo(FarmaciaEntity.class);
        newEntity.setNombre("Farmatodo");
        newEntity.setUbicacion("Calle 10 #10-10");
        newEntity.setTelefono(3456789L);
        newEntity.setLongitud(-72.03);
        newEntity.setLatitud(4.05);
        newEntity.setCorreo("abc");
        
        farmaciaLogic.createFarmacia(newEntity);
    }
    
     /**
     * Prueba para actualizar una farmacia con un correo inválido
     * @throws BusinessLogicException 
     */
    @Test(expected = BusinessLogicException.class)
    public void updateFarmaciaConCorreoInvalido() throws BusinessLogicException{
        FarmaciaEntity entity = data.get(1);
        FarmaciaEntity pojoEntity = factory.manufacturePojo(FarmaciaEntity.class);
        pojoEntity.setId(entity.getId());
        pojoEntity.setNombre(entity.getNombre());
        pojoEntity.setUbicacion(entity.getUbicacion());
        pojoEntity.setTelefono(entity.getTelefono());
        pojoEntity.setLongitud(entity.getLongitud());
        pojoEntity.setLatitud(entity.getLatitud());
        pojoEntity.setCorreo("abc");
        
        farmaciaLogic.updateFarmacia(entity.getId(), pojoEntity);
    }
    
     /**
     * Prueba para actualizar una farmacia con un telefono inválido
     * @throws BusinessLogicException 
     */
    @Test(expected = BusinessLogicException.class)
    public void updateFarmaciaConTelefonoInvalido() throws BusinessLogicException{
        FarmaciaEntity entity = data.get(1);
        FarmaciaEntity pojoEntity = factory.manufacturePojo(FarmaciaEntity.class);
        pojoEntity.setId(entity.getId());
        pojoEntity.setNombre(entity.getNombre());
        pojoEntity.setUbicacion(entity.getUbicacion());
        pojoEntity.setTelefono(123L);
        pojoEntity.setLongitud(entity.getLongitud());
        pojoEntity.setLatitud(entity.getLatitud());
        pojoEntity.setCorreo(entity.getCorreo());
        
        farmaciaLogic.updateFarmacia(entity.getId(), pojoEntity);
    }
    
}
