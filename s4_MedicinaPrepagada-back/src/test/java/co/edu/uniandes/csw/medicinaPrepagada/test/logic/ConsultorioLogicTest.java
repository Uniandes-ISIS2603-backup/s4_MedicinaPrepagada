/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.medicinaPrepagada.test.logic;

import co.edu.uniandes.csw.medicinaPrepagada.ejb.ConsultorioLogic;
import co.edu.uniandes.csw.medicinaPrepagada.entities.ConsultorioEntity;
import co.edu.uniandes.csw.medicinaPrepagada.entities.EspecialidadEntity;
import co.edu.uniandes.csw.medicinaPrepagada.entities.SedeEntity;
import co.edu.uniandes.csw.medicinaPrepagada.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.medicinaPrepagada.persistence.ConsultorioPersistence;
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
public class ConsultorioLogicTest 
{
    private PodamFactory factory = new PodamFactoryImpl();

    @Inject
    private ConsultorioLogic consultorioLogic;

    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;

    private List<ConsultorioEntity> data = new ArrayList<>();
    
        private List<SedeEntity> dataSede = new ArrayList<>();

    /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() 
    {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(ConsultorioEntity.class.getPackage())
                .addPackage(ConsultorioLogic.class.getPackage())
                .addPackage(ConsultorioPersistence.class.getPackage())
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

        em.createQuery("delete from ConsultorioEntity").executeUpdate();
        em.createQuery("delete from SedeEntity").executeUpdate();
        em.createQuery("delete from EspecialidadEntity").executeUpdate();

        
    }
    
    
        /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        for (int i = 0; i < 4; i++) 
        {
            ConsultorioEntity entity = factory.manufacturePojo(ConsultorioEntity.class);
            SedeEntity entitySede = factory.manufacturePojo(SedeEntity.class);
            em.persist (entitySede);
            entity.setSede(entitySede);
            entity.setHorariosAtencion(new ArrayList<>());
            em.persist(entity);
            data.add(entity);           
        }
        EspecialidadEntity esp1 = factory.manufacturePojo(EspecialidadEntity.class); 
        esp1.setNombre("Endocrinologia");
        em.merge(esp1);
        EspecialidadEntity esp2 = factory.manufacturePojo(EspecialidadEntity.class);
        esp1.setNombre("Cardiologia");
        em.merge(esp2);
        
        ConsultorioEntity consult1 = data.get(0);
        consult1.setEspecialidad(esp1);
        em.merge(consult1);
        
        ConsultorioEntity consult2 = data.get(1);
        consult2.setEspecialidad(esp1);
        em.merge(consult2);
        
        ConsultorioEntity consult3 = data.get(2);
        consult3.setEspecialidad(esp2);
        em.merge(consult3);
        
        ConsultorioEntity consult4 = data.get(3);
        consult4.setEspecialidad(esp2);
        consult4.setSede(data.get(2).getSede());
        em.merge(consult4);
        
   
    }
    
    
            /**
     * Prueba para crear un Consultorio.
     * @throws co.edu.uniandes.csw.medicinaPrepagada.exceptions.BusinessLogicException
     */
    @Test
    public void createConsultorioTest() throws BusinessLogicException
    {
        ConsultorioEntity newEntity = factory.manufacturePojo(ConsultorioEntity.class);
        newEntity.setSede(data.get(0).getSede());
        newEntity.setEspecialidad(data.get(0).getEspecialidad());
        
        ConsultorioEntity result = consultorioLogic.createConsultorio(newEntity, newEntity.getSede().getId());
        Assert.assertNotNull(result);
        ConsultorioEntity entity = em.find(ConsultorioEntity.class, result.getId());
        Assert.assertEquals(newEntity.getId(), entity.getId());
        
                //Test Atributos
        Assert.assertEquals(newEntity.getEdificio(), entity.getEdificio());
        Assert.assertEquals(newEntity.getNOficina(), entity.getNOficina());
                //Test relaciones

        Assert.assertEquals(newEntity.getEspecialidad().getNombre(), entity.getEspecialidad().getNombre());
        Assert.assertEquals(newEntity.getSede(), entity.getSede());
        
     
    }
    
    /**
     * Prueba para crear un Consultorio con edificio vacio.
     * @throws co.edu.uniandes.csw.medicinaPrepagada.exceptions.BusinessLogicException
     */
    @Test (expected = BusinessLogicException.class)
    public void createConsultorioEdificioVacioTest() throws BusinessLogicException
    {
        ConsultorioEntity newEntity = factory.manufacturePojo(ConsultorioEntity.class);
        newEntity.setSede(data.get(0).getSede());
        newEntity.setEspecialidad(data.get(0).getEspecialidad());
        newEntity.setEdificio("");
        
        ConsultorioEntity result = consultorioLogic.createConsultorio(newEntity, newEntity.getSede().getId());
        Assert.assertNotNull(result);
        ConsultorioEntity entity = em.find(ConsultorioEntity.class, result.getId());
        Assert.assertEquals(newEntity.getId(), entity.getId());    
    }
    
        /**
     * Prueba para crear un Consultorio sin sede.
     * @throws co.edu.uniandes.csw.medicinaPrepagada.exceptions.BusinessLogicException
     */
    @Test (expected = BusinessLogicException.class)
    public void createConsultorioSinSedeTest() throws BusinessLogicException
    {
        ConsultorioEntity newEntity = factory.manufacturePojo(ConsultorioEntity.class);
        newEntity.setEspecialidad(data.get(0).getEspecialidad());
        
        ConsultorioEntity result = consultorioLogic.createConsultorio(newEntity, 99999999999L);
        Assert.assertNotNull(result);
        ConsultorioEntity entity = em.find(ConsultorioEntity.class, result.getId());
        Assert.assertEquals(newEntity.getId(), entity.getId());    
    }
    
     /**
     * Prueba para crear un Consultorio con sede que no existe sede.
     * @throws co.edu.uniandes.csw.medicinaPrepagada.exceptions.BusinessLogicException
     */
    @Test (expected = BusinessLogicException.class)
    public void createConsultorioSedeInexistenteTest() throws BusinessLogicException
    {
        ConsultorioEntity newEntity = factory.manufacturePojo(ConsultorioEntity.class);
        SedeEntity newSede = factory.manufacturePojo(SedeEntity.class);
        newEntity.setEspecialidad(data.get(0).getEspecialidad());
        newEntity.setSede(newSede);
        
        ConsultorioEntity result = consultorioLogic.createConsultorio(newEntity, newEntity.getSede().getId());
        Assert.assertNotNull(result);
        ConsultorioEntity entity = em.find(ConsultorioEntity.class, result.getId());
        Assert.assertEquals(newEntity.getId(), entity.getId());    
    }
    
    /**
     * Prueba para crear un Consultorio que tiene la misma sede, edificio y numero 
     * de otro consultorio.
     * @throws co.edu.uniandes.csw.medicinaPrepagada.exceptions.BusinessLogicException
     */
    @Test (expected = BusinessLogicException.class)
    public void createConsultorioMismaSedeOficinaEdificio() throws BusinessLogicException
    {
        ConsultorioEntity newEntity = factory.manufacturePojo(ConsultorioEntity.class);
        newEntity.setEspecialidad(data.get(2).getEspecialidad());
        newEntity.setSede(data.get(1).getSede());
        newEntity.setEdificio(data.get(1).getEdificio());
        newEntity.setNOficina(data.get(1).getNOficina());
        
        
        ConsultorioEntity result = consultorioLogic.createConsultorio(newEntity, newEntity.getSede().getId());
        Assert.assertNotNull(result);
        ConsultorioEntity entity = em.find(ConsultorioEntity.class, result.getId());
        Assert.assertEquals(newEntity.getId(), entity.getId());    
    }
    
     /**
     * Prueba para crear un Consultorio con especialidad que no existe sede.
     * @throws co.edu.uniandes.csw.medicinaPrepagada.exceptions.BusinessLogicException
     */
    @Test (expected = BusinessLogicException.class)
    public void createConsultorioEspecialidadInexistenteTest() throws BusinessLogicException
    {
        ConsultorioEntity newEntity = factory.manufacturePojo(ConsultorioEntity.class);
        EspecialidadEntity newEspecialidad = factory.manufacturePojo(EspecialidadEntity.class);
        newEntity.setEspecialidad(newEspecialidad);
        newEntity.setSede(data.get(1).getSede());
        
        ConsultorioEntity result = consultorioLogic.createConsultorio(newEntity, newEntity.getSede().getId());
        Assert.assertNotNull(result);
        ConsultorioEntity entity = em.find(ConsultorioEntity.class, result.getId());
        Assert.assertEquals(newEntity.getId(), entity.getId());    
    }
    
    
         /**
     * Prueba para consultar la lista de Consultorios.
     */
    @Test
    public void getConsultoriosTest() 
    {
        Long idSede = data.get(0).getSede().getId();
        SedeEntity pSedeTemp = em.find(SedeEntity.class, idSede );
        List<ConsultorioEntity> list = consultorioLogic.getConsultorios(idSede);
        System.out.println(" ADASDSWDFSGFDGSFDSDSF sede.consultorios.size "+ pSedeTemp.getConsultorios().size() + " /n list.size " + list.size() );
        Assert.assertEquals(pSedeTemp.getConsultorios().size(), list.size());
        for (ConsultorioEntity entity : list) 
        {
            boolean found = false;
            for (ConsultorioEntity storedEntity : pSedeTemp.getConsultorios()) 
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
     * Prueba para consultar un Consultorio.
     */
    @Test
    public void getConsultorioTest() 
    {
        ConsultorioEntity entity = data.get(0);
        ConsultorioEntity resultEntity = consultorioLogic.getConsultorio(entity.getSede().getId(), entity.getId());
        Assert.assertNotNull(resultEntity);
        Assert.assertEquals(entity.getId(), resultEntity.getId());
        
                        //Test Atributos
        Assert.assertEquals(entity.getEdificio(), resultEntity.getEdificio());
        Assert.assertEquals(entity.getNOficina(), resultEntity.getNOficina());
                //Test relaciones

        Assert.assertEquals(entity.getEspecialidad().getNombre(), resultEntity.getEspecialidad().getNombre());
        Assert.assertEquals(entity.getSede(), resultEntity.getSede());
   
    }
    
    /**
     * Prueba para consultar un Consultorio que no existe.
     */
    @Test // (expected = BusinessLogicException.class)
    public void getConsultorioInexistenteTest() 
    {
        Long idSede = data.get(0).getSede().getId();
        ConsultorioEntity resultEntity = consultorioLogic.getConsultorio(idSede, 999999L);
       Assert.assertNull(resultEntity);

    } 
    
            /**
     * Prueba para actualizar un Consultorio.
     * @throws co.edu.uniandes.csw.medicinaPrepagada.exceptions.BusinessLogicException
     */
    @Test
    public void updateConsultorioTest() throws BusinessLogicException 
     {
        ConsultorioEntity entity = data.get(2);
        ConsultorioEntity pojoEntity = factory.manufacturePojo(ConsultorioEntity.class);
        
         pojoEntity.setId(entity.getId());
        pojoEntity.setSede(data.get(2).getSede());
        pojoEntity.setEspecialidad(data.get(0).getEspecialidad());

        consultorioLogic.updateConsultorio(pojoEntity.getSede().getId(), pojoEntity);

        ConsultorioEntity resp = em.find(ConsultorioEntity.class, entity.getId());

        Assert.assertEquals(pojoEntity.getId(), resp.getId());
    }
    
       /**
     * Prueba para actualizar un Consultorio que no existe.
     */
    @Test (expected = BusinessLogicException.class)
    public void updateConsultorioInexistenteTest() throws BusinessLogicException 
    {
        ConsultorioEntity entity = factory.manufacturePojo(ConsultorioEntity.class);
        consultorioLogic.updateConsultorio(999999L, entity);
    } 

    
        /**
     * Prueba para actualizar un Consultorio con edificio vacio.
     * @throws co.edu.uniandes.csw.medicinaPrepagada.exceptions.BusinessLogicException
     */
    @Test (expected = BusinessLogicException.class)
    public void updateConsultorioEdificioVacioTest() throws BusinessLogicException
    {
        ConsultorioEntity entity = data.get(2);
        ConsultorioEntity newEntity = factory.manufacturePojo(ConsultorioEntity.class);
        
         newEntity.setId(data.get(2).getId());
        newEntity.setSede(data.get(2).getSede());
        newEntity.setEspecialidad(data.get(0).getEspecialidad());
        newEntity.setEdificio("");

        ConsultorioEntity result = consultorioLogic.updateConsultorio(entity.getId(), newEntity);
        Assert.assertNotNull(result);
        ConsultorioEntity entity2 = em.find(ConsultorioEntity.class, result.getId());
        Assert.assertEquals(newEntity.getId(), entity2.getId());    
    }
    
    
      
    
    /**
     * Prueba para actualizar un Consultorio que tiene la misma sede, edificio y numero 
     * de otro consultorio.
     * @throws co.edu.uniandes.csw.medicinaPrepagada.exceptions.BusinessLogicException
     */
    @Test (expected = BusinessLogicException.class)
    public void updateConsultorioMismaSedeOficinaEdificio() throws BusinessLogicException
    {
        ConsultorioEntity newEntity = factory.manufacturePojo(ConsultorioEntity.class);
        newEntity.setId(data.get(3).getId());      
        newEntity.setEspecialidad(data.get(0).getEspecialidad());
        newEntity.setSede(data.get(3).getSede());
        newEntity.setEdificio(data.get(2).getEdificio());
        newEntity.setNOficina(data.get(2).getNOficina());
        
        
        ConsultorioEntity result = consultorioLogic.createConsultorio(newEntity, newEntity.getSede().getId());
        Assert.assertNotNull(result);
        ConsultorioEntity entity = em.find(ConsultorioEntity.class, result.getId());
        Assert.assertEquals(newEntity.getId(), entity.getId());    
    }
 
     /**
     * Prueba para eliminar un Consultorio
     *
     * @throws co.edu.uniandes.csw.medicinaPrepagada.exceptions.BusinessLogicException
     */
    @Test
    public void deleteConsultorioTest() throws BusinessLogicException 
    {
        ConsultorioEntity entity = data.get(0);
        consultorioLogic.deleteConsultorio(entity.getSede().getId(), entity.getId());
        ConsultorioEntity deleted = em.find(ConsultorioEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }
    
    /**
     * Prueba para eliminar un Consultorio que no existe
     *
     * @throws co.edu.uniandes.csw.medicinaPrepagada.exceptions.BusinessLogicException
     */
    @Test (expected = BusinessLogicException.class)
    public void deleteConsultorioInexistenteTest() throws BusinessLogicException 
    {   
        ConsultorioEntity entity = data.get(0);
        consultorioLogic.deleteConsultorio(entity.getSede().getId(),99999999L);
        ConsultorioEntity deleted = em.find(ConsultorioEntity.class, 99999999L );
        Assert.assertNull(deleted);
    }
    
    
}
