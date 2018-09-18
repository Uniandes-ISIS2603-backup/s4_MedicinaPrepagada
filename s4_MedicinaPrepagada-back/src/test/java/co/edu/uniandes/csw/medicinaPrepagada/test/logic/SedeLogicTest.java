/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.medicinaPrepagada.test.logic;

import co.edu.uniandes.csw.medicinaPrepagada.ejb.SedeLogic;
import co.edu.uniandes.csw.medicinaPrepagada.entities.ConsultorioEntity;
import co.edu.uniandes.csw.medicinaPrepagada.entities.SedeEntity;
import co.edu.uniandes.csw.medicinaPrepagada.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.medicinaPrepagada.persistence.SedePersistence;
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
 * @author Simon Guzman
 */
@RunWith(Arquillian.class)
public class SedeLogicTest 
{
   
    private PodamFactory factory = new PodamFactoryImpl();

    @Inject
    private SedeLogic sedeLogic;

    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;

    private List<SedeEntity> data = new ArrayList<>();
    
    /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() 
    {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(SedeEntity.class.getPackage())
                .addPackage(SedeLogic.class.getPackage())
                .addPackage(SedePersistence.class.getPackage())
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
    private void clearData() 
    {
        em.createQuery("delete from SedeEntity").executeUpdate();
        em.createQuery("delete from ConsultorioEntity").executeUpdate();
        
    }
    
    
    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        for (int i = 0; i < 3; i++) 
        {
            SedeEntity entity = factory.manufacturePojo(SedeEntity.class);
            em.persist(entity);
            entity.setConsultorios(new ArrayList<>());
            data.add(entity);
        }
        SedeEntity sede = data.get(0);
        ConsultorioEntity entity = factory.manufacturePojo(ConsultorioEntity.class);
        //entity.setSede(sede);
        em.persist(entity);
        sede.getConsultorios().add(entity);
        em.merge(sede);
        
        SedeEntity sede2 = data.get(1);
        ConsultorioEntity entity2 = factory.manufacturePojo(ConsultorioEntity.class);
        //entity.setSede(sede2);
        em.persist(entity2);
        sede2.getConsultorios().add(entity2);
        em.merge(sede2);
        
        SedeEntity sede3 = data.get(2);
        data.get(2).setLongitud(-70.3);
        data.get(2).setLatitud(1.0);
        data.get(2).setDireccion("Calle 100 # 15 - 03");
        em.merge(sede3);

        
    }
    
    
        /**
     * Prueba para crear un Sede.
     * @throws co.edu.uniandes.csw.medicinaPrepagada.exceptions.BusinessLogicException
     */
    @Test
    public void createSedeTest() throws BusinessLogicException
    {
        SedeEntity newEntity = factory.manufacturePojo(SedeEntity.class);
        newEntity.setTelefono(876543987L);
        newEntity.setDireccion("Calle 120 # 13 - 22");
        newEntity.setTipoSede(2);
        newEntity.setLatitud(1.7);
        newEntity.setLongitud(-71.8);
        newEntity.setCorreo("pepito@gmail.com");
        SedeEntity result = sedeLogic.createSede(newEntity);
        Assert.assertNotNull(result);
        SedeEntity entity = em.find(SedeEntity.class, result.getId());
        Assert.assertEquals(newEntity.getId(), entity.getId());
        
                //Test Atributos
        Assert.assertEquals(newEntity.getNombre(), entity.getNombre());
        Assert.assertEquals(newEntity.getDireccion(), entity.getDireccion());
        Assert.assertEquals(newEntity.getTipoSede(), entity.getTipoSede());
        Assert.assertEquals(newEntity.getDescripcion(), entity.getDescripcion());
        Assert.assertEquals(newEntity.getLatitud(), entity.getLatitud(), 0);
        Assert.assertEquals(newEntity.getLongitud(), entity.getLongitud(),0);
        Assert.assertEquals(newEntity.getTelefono(), entity.getTelefono());
        Assert.assertEquals(newEntity.getCorreo(), entity.getCorreo());
        //Test relaciones
        Assert.assertEquals(newEntity.getConsultorios().size(), entity.getConsultorios().size());
        for (int i=0;i<newEntity.getConsultorios().size();i++)
        {
            Assert.assertEquals(newEntity.getConsultorios().get(i), entity.getConsultorios().get(i));
        }
    }
    
     /**
     * Prueba para crear un Sede que no tiene nombre
     * @throws co.edu.uniandes.csw.medicinaPrepagada.exceptions.BusinessLogicException
     */
    @Test (expected = BusinessLogicException.class)
    public void createSedeTestSinNombre() throws BusinessLogicException
    {
        SedeEntity newEntity = factory.manufacturePojo(SedeEntity.class);
        newEntity.setNombre("");
        newEntity.setTelefono(876543986L);
        newEntity.setDireccion("Calle 121 # 13 - 22");
        newEntity.setTipoSede(2);
        newEntity.setLatitud(1.0);
        newEntity.setLongitud(-70.3);
        newEntity.setCorreo("pepito1@gmail.com");
        SedeEntity result = sedeLogic.createSede(newEntity);
        Assert.assertNotNull(result);
        SedeEntity entity = em.find(SedeEntity.class, result.getId());
        Assert.assertEquals(newEntity.getId(), entity.getId());
    }
    /**
     * Prueba para crear un Sede con un nombre ya usado
     * @throws co.edu.uniandes.csw.medicinaPrepagada.exceptions.BusinessLogicException
     */
    @Test (expected = BusinessLogicException.class)
    public void createSedeTestNombreRepetido() throws BusinessLogicException
    {
        SedeEntity newEntity = factory.manufacturePojo(SedeEntity.class);
        newEntity.setNombre(data.get(0).getNombre());
        newEntity.setTelefono(876543986L);
        newEntity.setDireccion("Calle 121 # 13 - 22");
        newEntity.setTipoSede(2);
        newEntity.setLatitud(1.0);
        newEntity.setLongitud(-70.3);
        newEntity.setCorreo("pepito1@gmail.com");
        SedeEntity result = sedeLogic.createSede(newEntity);
        Assert.assertNotNull(result);
        SedeEntity entity = em.find(SedeEntity.class, result.getId());
        Assert.assertEquals(newEntity.getId(), entity.getId());
    }
    
    /**
     * Prueba para crear un Sede cuya direccion no cumple el formato
     * @throws co.edu.uniandes.csw.medicinaPrepagada.exceptions.BusinessLogicException
     */
    @Test (expected = BusinessLogicException.class)
    public void createSedeTestMalaDireccion() throws BusinessLogicException
    {
        SedeEntity newEntity = factory.manufacturePojo(SedeEntity.class);
        newEntity.setNombre("sede12");
        newEntity.setTelefono(876643986L);
        newEntity.setDireccion("Calle121 13 - 22");
        newEntity.setTipoSede(2);
        newEntity.setLatitud(1.0);
        newEntity.setLongitud(-70.3);
        newEntity.setCorreo("pepito10@gmail.com");
        SedeEntity result = sedeLogic.createSede(newEntity);
        Assert.assertNotNull(result);
        SedeEntity entity = em.find(SedeEntity.class, result.getId());
        Assert.assertEquals(newEntity.getId(), entity.getId());
    }
    
        /**
     * Prueba para crear un Sede con telefono de menos de 7 digitos
     * @throws co.edu.uniandes.csw.medicinaPrepagada.exceptions.BusinessLogicException
     */
    @Test (expected = BusinessLogicException.class)
    public void createSedeTestMalTelefono() throws BusinessLogicException
    {
        SedeEntity newEntity = factory.manufacturePojo(SedeEntity.class);
        newEntity.setNombre("sede12");
        newEntity.setTelefono(86L);
        newEntity.setDireccion("Calle 121 # 13 - 22");
        newEntity.setTipoSede(2);
        newEntity.setLatitud(1.0);
        newEntity.setLongitud(-70.3);
        newEntity.setCorreo("pepito10@gmail.com");
        SedeEntity result = sedeLogic.createSede(newEntity);
        Assert.assertNotNull(result);
        SedeEntity entity = em.find(SedeEntity.class, result.getId());
        Assert.assertEquals(newEntity.getId(), entity.getId());
    }
    
        
     /**
     * Prueba para crear un Sede.
     * @throws co.edu.uniandes.csw.medicinaPrepagada.exceptions.BusinessLogicException
     */
    @Test (expected = BusinessLogicException.class)
    public void createSedeDireccionRepetidaTest() throws BusinessLogicException
    {
        SedeEntity newEntity = factory.manufacturePojo(SedeEntity.class);
        newEntity.setNombre("sede12");
        newEntity.setTelefono(876543987L);
        newEntity.setDireccion("Calle 100 # 15 - 03");
        newEntity.setTipoSede(2);
        newEntity.setLatitud(1.1);
        newEntity.setLongitud(-71.3);
        newEntity.setCorreo("pepito@gmail.com");
        SedeEntity result = sedeLogic.createSede(newEntity);
        Assert.assertNotNull(result);
        SedeEntity entity = em.find(SedeEntity.class, result.getId());
        Assert.assertEquals(newEntity.getId(), entity.getId());
        
    }
            /**
     * Prueba para crear un Sede con un tipo no aceptado
     * @throws co.edu.uniandes.csw.medicinaPrepagada.exceptions.BusinessLogicException
     */
    @Test (expected = BusinessLogicException.class)
    public void createSedeMalTipoTest() throws BusinessLogicException
    {
        SedeEntity newEntity = factory.manufacturePojo(SedeEntity.class);
        newEntity.setNombre("sede12");
        newEntity.setTelefono(876543987L);
        newEntity.setDireccion("Calle 120 # 13 - 22");
        newEntity.setTipoSede(5);
        newEntity.setLatitud(1.0);
        newEntity.setLongitud(-70.3);
        newEntity.setCorreo("pepito@gmail.com");
        SedeEntity result = sedeLogic.createSede(newEntity);
        Assert.assertNotNull(result);
        SedeEntity entity = em.find(SedeEntity.class, result.getId());
        Assert.assertEquals(newEntity.getId(), entity.getId());
        
    }
    
      /**
     * Prueba para crear un Sede con mal formato de correo
     * @throws co.edu.uniandes.csw.medicinaPrepagada.exceptions.BusinessLogicException
     */
    @Test (expected = BusinessLogicException.class)
    public void createSedeMalCorreoTest() throws BusinessLogicException
    {
        SedeEntity newEntity = factory.manufacturePojo(SedeEntity.class);
        newEntity.setNombre("sede12");
        newEntity.setTelefono(876543987L);
        newEntity.setDireccion("Calle 120 # 13 - 22");
        newEntity.setTipoSede(2);
        newEntity.setLatitud(1.0);
        newEntity.setLongitud(-70.3);
        newEntity.setCorreo("pepitogmail.com");
        SedeEntity result = sedeLogic.createSede(newEntity);
        Assert.assertNotNull(result);
        SedeEntity entity = em.find(SedeEntity.class, result.getId());
        Assert.assertEquals(newEntity.getId(), entity.getId());
        
    }
    
     /**
     * Prueba para crear un Sede con una longitud fuera de colombia
     * @throws co.edu.uniandes.csw.medicinaPrepagada.exceptions.BusinessLogicException
     */
    @Test (expected = BusinessLogicException.class)
    public void createSedeMalaLongitudTest() throws BusinessLogicException
    {
        SedeEntity newEntity = factory.manufacturePojo(SedeEntity.class);
        newEntity.setNombre("sede12");
        newEntity.setTelefono(876543987L);
        newEntity.setDireccion("Calle 120 # 13 - 22");
        newEntity.setTipoSede(2);
        newEntity.setLatitud(1.0);
        newEntity.setLongitud(70.3);
        newEntity.setCorreo("pepito@gmail.com");
        SedeEntity result = sedeLogic.createSede(newEntity);
        Assert.assertNotNull(result);
        SedeEntity entity = em.find(SedeEntity.class, result.getId());
        Assert.assertEquals(newEntity.getId(), entity.getId());
        
    }
    
            /**
     * Prueba para crear un Sede con latitud fuera de colombia
     * @throws co.edu.uniandes.csw.medicinaPrepagada.exceptions.BusinessLogicException
     */
    @Test (expected = BusinessLogicException.class)
    public void createSedeMalaLatitudTest() throws BusinessLogicException
    {
        SedeEntity newEntity = factory.manufacturePojo(SedeEntity.class);
        newEntity.setNombre("sede12");
        newEntity.setTelefono(876543987L);
        newEntity.setDireccion("Calle 120 # 13 - 22");
        newEntity.setTipoSede(2);
        newEntity.setLatitud(50.0);
        newEntity.setLongitud(-70.3);
        newEntity.setCorreo("pepito@gmail.com");
        SedeEntity result = sedeLogic.createSede(newEntity);
        Assert.assertNotNull(result);
        SedeEntity entity = em.find(SedeEntity.class, result.getId());
        Assert.assertEquals(newEntity.getId(), entity.getId());       
    }
    
    /**
     * Prueba para crear un Sede con longitud y latitud existente
     * @throws co.edu.uniandes.csw.medicinaPrepagada.exceptions.BusinessLogicException
     */
    @Test (expected = BusinessLogicException.class)
    public void createSedeLongLatRepetidasTest() throws BusinessLogicException
    {

        SedeEntity newEntity = factory.manufacturePojo(SedeEntity.class);
        newEntity.setNombre("sede12");
        newEntity.setTelefono(876543987L);
        newEntity.setDireccion("Calle 120 # 13 - 22");
        newEntity.setTipoSede(2);
        newEntity.setLatitud(1.0);
        newEntity.setLongitud(-70.3);
        newEntity.setCorreo("pepito@gmail.com");
        SedeEntity result = sedeLogic.createSede(newEntity);
        Assert.assertNotNull(result);
        SedeEntity entity = em.find(SedeEntity.class, result.getId());
        Assert.assertEquals(newEntity.getId(), entity.getId());
        
    }
    
     /**
     * Prueba para consultar la lista de Sedes.
     */
    @Test
    public void getSedesTest() 
    {
        List<SedeEntity> list = sedeLogic.getSedes();
        Assert.assertEquals(data.size(), list.size());
        for (SedeEntity entity : list) 
        {
            boolean found = false;
            for (SedeEntity storedEntity : data) 
            {
                if (entity.getId().equals(storedEntity.getId()))
                {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }
    
    
    
        /**
     * Prueba para consultar un Sede.
     */
    @Test
    public void getSedeTest() 
    {
        SedeEntity entity = data.get(0);
        SedeEntity resultEntity = sedeLogic.getSede(entity.getId());
        Assert.assertNotNull(resultEntity);
        Assert.assertEquals(entity.getId(), resultEntity.getId());
        
        
        Assert.assertEquals(entity.getNombre(), resultEntity.getNombre());
        Assert.assertEquals(entity.getDireccion(), resultEntity.getDireccion());
        Assert.assertEquals(entity.getTipoSede(), resultEntity.getTipoSede());
        Assert.assertEquals(entity.getDescripcion(), resultEntity.getDescripcion());
        Assert.assertEquals(entity.getLatitud(), resultEntity.getLatitud(), 0);
        Assert.assertEquals(entity.getLongitud(), resultEntity.getLongitud(),0);
        Assert.assertEquals(entity.getTelefono(), resultEntity.getTelefono());
        Assert.assertEquals(entity.getCorreo(), resultEntity.getCorreo());
        
    }
    
    
        /**
     * Prueba para actualizar un Sede.
     * @throws co.edu.uniandes.csw.medicinaPrepagada.exceptions.BusinessLogicException
     */
    @Test
    public void updateSedeTest() throws BusinessLogicException 
     {
        SedeEntity entity = data.get(0);
        SedeEntity pojoEntity = factory.manufacturePojo(SedeEntity.class);


        pojoEntity.setId(entity.getId());
        pojoEntity.setNombre("Sede100");
        pojoEntity.setDireccion(entity.getDireccion());
        pojoEntity.setTipoSede(2);
        pojoEntity.setDescripcion("La mejor sede de sedes");
        pojoEntity.setLatitud(entity.getLatitud());
        pojoEntity.setLongitud(entity.getLongitud());
        pojoEntity.setTelefono(78987643L);
        pojoEntity.setCorreo("pepitoPerez@gmail.com");
        
        

        sedeLogic.updateSede(pojoEntity.getId(), pojoEntity);

        SedeEntity resp = em.find(SedeEntity.class, entity.getId());

        Assert.assertEquals(pojoEntity.getId(), resp.getId());

    }
        
     /**
     * Prueba para actualizar un Sede. que no existe 
     * @throws co.edu.uniandes.csw.medicinaPrepagada.exceptions.BusinessLogicException
     */
    @Test (expected = BusinessLogicException.class)
    public void updateSedeInexistenteTest() throws BusinessLogicException 
     {
        SedeEntity entity = data.get(0);
        SedeEntity pojoEntity = factory.manufacturePojo(SedeEntity.class);


        pojoEntity.setId(entity.getId());
        pojoEntity.setNombre("Sede100");
        pojoEntity.setDireccion(entity.getDireccion());
        pojoEntity.setTipoSede(2);
        pojoEntity.setDescripcion("La mejor sede de sedes");
        pojoEntity.setLatitud(entity.getLatitud());
        pojoEntity.setLongitud(entity.getLongitud());
        pojoEntity.setTelefono(78987643L);
        pojoEntity.setCorreo("pepitoPerez@gmail.com");
        
       
        sedeLogic.updateSede(999999L, pojoEntity);

        SedeEntity resp = em.find(SedeEntity.class, entity.getId());

        Assert.assertNull(resp);
    }
    
         /**
     * Prueba para actualizar un Sede cambiando su id.
     * @throws co.edu.uniandes.csw.medicinaPrepagada.exceptions.BusinessLogicException
     */
    @Test (expected = BusinessLogicException.class)
    public void updateSedeCambiaIdTest() throws BusinessLogicException 
     {
        SedeEntity entity = data.get(0);
        SedeEntity pojoEntity = factory.manufacturePojo(SedeEntity.class);


        pojoEntity.setId(entity.getId());
        pojoEntity.setNombre("Sede100");
        pojoEntity.setDireccion(entity.getDireccion());
        pojoEntity.setTipoSede(2);
        pojoEntity.setDescripcion("La mejor sede de sedes");
        pojoEntity.setLatitud(entity.getLatitud());
        pojoEntity.setLongitud(entity.getLongitud());
        pojoEntity.setTelefono(78987643L);
        pojoEntity.setCorreo("pepitoPerez@gmail.com");
        
       
        sedeLogic.updateSede(data.get(2).getId(), pojoEntity);

        SedeEntity resp = em.find(SedeEntity.class, entity.getId());

        Assert.assertNull(resp);
    }
    
             /**
     * Prueba para actualizar un Sede cambiando su Longitud.
     * @throws co.edu.uniandes.csw.medicinaPrepagada.exceptions.BusinessLogicException
     */
    @Test (expected = BusinessLogicException.class)
    public void updateSedeCambiaLongitudTest() throws BusinessLogicException 
     {
        SedeEntity entity = data.get(0);
        SedeEntity pojoEntity = factory.manufacturePojo(SedeEntity.class);


        pojoEntity.setId(entity.getId());
        pojoEntity.setNombre("Sede100");
        pojoEntity.setDireccion(entity.getDireccion());
        pojoEntity.setTipoSede(2);
        pojoEntity.setDescripcion("La mejor sede de sedes");
        pojoEntity.setLatitud(entity.getLatitud());
        pojoEntity.setLongitud(entity.getLongitud()+1);
        pojoEntity.setTelefono(78987643L);
        pojoEntity.setCorreo("pepitoPerez@gmail.com");
        
       
        sedeLogic.updateSede(data.get(0).getId(), pojoEntity);

        SedeEntity resp = em.find(SedeEntity.class, entity.getId());

        Assert.assertNull(resp);
    }
    
    
     /**
     * Prueba para actualizar un Sede cambiando su latitud.
     * @throws co.edu.uniandes.csw.medicinaPrepagada.exceptions.BusinessLogicException
     */
    @Test (expected = BusinessLogicException.class)
    public void updateSedeCambiaLatitudTest() throws BusinessLogicException 
     {
        SedeEntity entity = data.get(0);
        SedeEntity pojoEntity = factory.manufacturePojo(SedeEntity.class);


        pojoEntity.setId(entity.getId());
        pojoEntity.setNombre("Sede100");
        pojoEntity.setDireccion(entity.getDireccion());
        pojoEntity.setTipoSede(2);
        pojoEntity.setDescripcion("La mejor sede de sedes");
        pojoEntity.setLatitud(entity.getLatitud()+1);
        pojoEntity.setLongitud(entity.getLongitud());
        pojoEntity.setTelefono(78987643L);
        pojoEntity.setCorreo("pepitoPerez@gmail.com");
        
       
        sedeLogic.updateSede(data.get(0).getId(), pojoEntity);

        SedeEntity resp = em.find(SedeEntity.class, entity.getId());

        Assert.assertNull(resp);
    }
    
         /**
     * Prueba para actualizar un Sede cambiando su direccion.
     * @throws co.edu.uniandes.csw.medicinaPrepagada.exceptions.BusinessLogicException
     */
    @Test (expected = BusinessLogicException.class)
    public void updateSedeCambiaDireccionTest() throws BusinessLogicException 
     {
        SedeEntity entity = data.get(0);
        SedeEntity pojoEntity = factory.manufacturePojo(SedeEntity.class);


        pojoEntity.setId(entity.getId());
        pojoEntity.setNombre("Sede100");
        pojoEntity.setDireccion(entity.getDireccion()+ "hola");
        pojoEntity.setTipoSede(2);
        pojoEntity.setDescripcion("La mejor sede de sedes");
        pojoEntity.setLatitud(entity.getLatitud());
        pojoEntity.setLongitud(entity.getLongitud());
        pojoEntity.setTelefono(78987643L);
        pojoEntity.setCorreo("pepitoPerez@gmail.com");
        
       
        sedeLogic.updateSede(data.get(0).getId(), pojoEntity);

        SedeEntity resp = em.find(SedeEntity.class, entity.getId());

        Assert.assertNull(resp);
    }
    
    
        /**
     * Prueba para actualizar un Sede con un nombre vacio
     * @throws co.edu.uniandes.csw.medicinaPrepagada.exceptions.BusinessLogicException
     */
    @Test (expected = BusinessLogicException.class)
    public void updateSedeTestMalNombre() throws BusinessLogicException
    {
        SedeEntity entity = data.get(0);
        SedeEntity pojoEntity = factory.manufacturePojo(SedeEntity.class);


        pojoEntity.setId(entity.getId());
        pojoEntity.setNombre("");
        pojoEntity.setDireccion(entity.getDireccion());
        pojoEntity.setTipoSede(2);
        pojoEntity.setDescripcion("La mejor sede de sedes");
        pojoEntity.setLatitud(entity.getLatitud());
        pojoEntity.setLongitud(entity.getLongitud());
        pojoEntity.setTelefono(78987643L);
        pojoEntity.setCorreo("pepitoPerez@gmail.com");
        
       
        sedeLogic.updateSede(data.get(0).getId(), pojoEntity);

        SedeEntity resp = em.find(SedeEntity.class, entity.getId());

        Assert.assertNull(resp);
    }
    
    
    /**
     * Prueba para actualizar un Sede con un nombre ya usado
     * @throws co.edu.uniandes.csw.medicinaPrepagada.exceptions.BusinessLogicException
     */
    @Test (expected = BusinessLogicException.class)
    public void updateSedeTestNombreRepetido() throws BusinessLogicException
    {
        SedeEntity entity = data.get(0);
        SedeEntity pojoEntity = factory.manufacturePojo(SedeEntity.class);


        pojoEntity.setId(entity.getId());
        pojoEntity.setNombre(data.get(1).getNombre());
        pojoEntity.setDireccion(entity.getDireccion());
        pojoEntity.setTipoSede(2);
        pojoEntity.setDescripcion("La mejor sede de sedes");
        pojoEntity.setLatitud(entity.getLatitud());
        pojoEntity.setLongitud(entity.getLongitud());
        pojoEntity.setTelefono(78987643L);
        pojoEntity.setCorreo("pepitoPerez@gmail.com");
        
       
        sedeLogic.updateSede(data.get(0).getId(), pojoEntity);

        SedeEntity resp = em.find(SedeEntity.class, entity.getId());

        Assert.assertNull(resp);
    }
    
    
        /**
     * Prueba para actualizar un Sede con un telefono con menos de 7 digitos
     * @throws co.edu.uniandes.csw.medicinaPrepagada.exceptions.BusinessLogicException
     */
    @Test (expected = BusinessLogicException.class)
    public void updateSedeTestMalTelefono() throws BusinessLogicException
    {
        SedeEntity entity = data.get(0);
        SedeEntity pojoEntity = factory.manufacturePojo(SedeEntity.class);


        pojoEntity.setId(entity.getId());
        pojoEntity.setNombre("Sede 200");
        pojoEntity.setDireccion(entity.getDireccion());
        pojoEntity.setTipoSede(2);
        pojoEntity.setDescripcion("La mejor sede de sedes");
        pojoEntity.setLatitud(entity.getLatitud());
        pojoEntity.setLongitud(entity.getLongitud());
        pojoEntity.setTelefono(783L);
        pojoEntity.setCorreo("pepitoPerez@gmail.com");
        
       
        sedeLogic.updateSede(data.get(0).getId(), pojoEntity);

        SedeEntity resp = em.find(SedeEntity.class, entity.getId());

        Assert.assertNull(resp);
    }
    
    
            /**
     * Prueba para actualizar un Sede con un tipo no aceptado
     * @throws co.edu.uniandes.csw.medicinaPrepagada.exceptions.BusinessLogicException
     */
    @Test (expected = BusinessLogicException.class)
    public void updateSedeTestMalTipo() throws BusinessLogicException
    {
        SedeEntity entity = data.get(0);
        SedeEntity pojoEntity = factory.manufacturePojo(SedeEntity.class);


        pojoEntity.setId(entity.getId());
        pojoEntity.setNombre("Sede 200");
        pojoEntity.setDireccion(entity.getDireccion());
        pojoEntity.setTipoSede(5);
        pojoEntity.setDescripcion("La mejor sede de sedes");
        pojoEntity.setLatitud(entity.getLatitud());
        pojoEntity.setLongitud(entity.getLongitud());
        pojoEntity.setTelefono(78876543L);
        pojoEntity.setCorreo("pepitoPerez@gmail.com");
        
       
        sedeLogic.updateSede(data.get(0).getId(), pojoEntity);

        SedeEntity resp = em.find(SedeEntity.class, entity.getId());

        Assert.assertNull(resp);
    }
    
    
                /**
     * Prueba para actualizar un Sede con un tipo no aceptado
     * @throws co.edu.uniandes.csw.medicinaPrepagada.exceptions.BusinessLogicException
     */
    @Test (expected = BusinessLogicException.class)
    public void updateSedeTestMalCorreo() throws BusinessLogicException
    {
        SedeEntity entity = data.get(0);
        SedeEntity pojoEntity = factory.manufacturePojo(SedeEntity.class);


        pojoEntity.setId(entity.getId());
        pojoEntity.setNombre("Sede 200");
        pojoEntity.setDireccion(entity.getDireccion());
        pojoEntity.setTipoSede(2);
        pojoEntity.setDescripcion("La mejor sede de sedes");
        pojoEntity.setLatitud(entity.getLatitud());
        pojoEntity.setLongitud(entity.getLongitud());
        pojoEntity.setTelefono(78876543L);
        pojoEntity.setCorreo("pepigmail.com");
        
       
        sedeLogic.updateSede(data.get(0).getId(), pojoEntity);

        SedeEntity resp = em.find(SedeEntity.class, entity.getId());

        Assert.assertNull(resp);
    }
    

     /**
     * Prueba para eliminar un Sede
     *
     * @throws co.edu.uniandes.csw.medicinaPrepagada.exceptions.BusinessLogicException
     */
    @Test
    public void deleteSedeTest() throws BusinessLogicException 
    {
        SedeEntity entity = data.get(0);
        sedeLogic.deleteSede(entity.getId());
        SedeEntity deleted = em.find(SedeEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }
    
    
            /**
     * Prueba para eliminar un Sede que no existe
     *
     * @throws co.edu.uniandes.csw.medicinaPrepagada.exceptions.BusinessLogicException
     */
    @Test (expected = BusinessLogicException.class)
    public void deleteSedeInexistenteTest() throws BusinessLogicException 
    {
        sedeLogic.deleteSede(99999999L);
        SedeEntity deleted = em.find(SedeEntity.class, 99999999L );
        Assert.assertNull(deleted);
    }
}
