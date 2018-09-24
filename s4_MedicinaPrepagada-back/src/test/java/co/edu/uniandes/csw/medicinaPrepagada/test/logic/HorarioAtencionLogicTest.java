/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.medicinaPrepagada.test.logic;

import co.edu.uniandes.csw.medicinaPrepagada.ejb.HorarioAtencionLogic;
import co.edu.uniandes.csw.medicinaPrepagada.entities.ConsultorioEntity;
import co.edu.uniandes.csw.medicinaPrepagada.entities.EspecialidadEntity;
import co.edu.uniandes.csw.medicinaPrepagada.entities.HorarioAtencionEntity;
import co.edu.uniandes.csw.medicinaPrepagada.entities.MedicoEntity;
import co.edu.uniandes.csw.medicinaPrepagada.entities.SedeEntity;
import co.edu.uniandes.csw.medicinaPrepagada.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.medicinaPrepagada.persistence.HorarioAtencionPersistence;
import java.util.ArrayList;
import java.util.Date;
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
public class HorarioAtencionLogicTest 
{
    
    private PodamFactory factory = new PodamFactoryImpl();

    @Inject
    private HorarioAtencionLogic horarioAtencionLogic;

    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;

    private List<HorarioAtencionEntity> data = new ArrayList<>();
    
        
    /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() 
    {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(HorarioAtencionEntity.class.getPackage())
                .addPackage(HorarioAtencionLogic.class.getPackage())
                .addPackage(HorarioAtencionPersistence.class.getPackage())
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
        em.createQuery("delete from HorarioAtencionEntity").executeUpdate();
        em.createQuery("delete from ConsultorioEntity").executeUpdate();
        em.createQuery("delete from SedeEntity").executeUpdate();
      
        em.createQuery("delete from MedicoEntity").executeUpdate(); 
        em.createQuery("delete from EspecialidadEntity").executeUpdate();

    }
    
            /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() 
    {
         EspecialidadEntity esp1 = factory.manufacturePojo(EspecialidadEntity.class); 
        esp1.setNombre("Endocrinologia");
        em.merge(esp1);
        
        SedeEntity entitySede = factory.manufacturePojo(SedeEntity.class);
        entitySede.setId(1L);
        em.persist (entitySede);
        

        for (int i = 0; i < 3; i++) 
        {
            HorarioAtencionEntity entity = factory.manufacturePojo(HorarioAtencionEntity.class);
            ConsultorioEntity entityConsultorio = factory.manufacturePojo(ConsultorioEntity.class);
            MedicoEntity entityMedico = factory.manufacturePojo(MedicoEntity.class);
            em.persist (entityConsultorio);
            em.persist (entityMedico);
            
            entityConsultorio.setEspecialidad(esp1);
            entityConsultorio.setSede(entitySede);
            em.merge(entityConsultorio);
            
            entityMedico.setEspecialidad(esp1);
            em.merge(entityMedico);
            
            entity.setConsultorio(entityConsultorio);
            entity.setMedico(entityMedico);
            entity.setCitasMedicas(new ArrayList<>());
            
            entity.setFechaInicio(new Date (1539608400000L));
            entity.setFechaFin(new Date(1539615600000L));
            em.persist (entity);
            data.add(entity);           
        } 
        
        
        EspecialidadEntity esp2 = factory.manufacturePojo(EspecialidadEntity.class); 
        esp2.setNombre("Cardiologia");
        em.merge(esp2);
        SedeEntity entitySede2 = factory.manufacturePojo(SedeEntity.class);
        entitySede2.setId(2L);
        em.persist (entitySede2);
        
        
       ConsultorioEntity entityConsultorio2 = factory.manufacturePojo(ConsultorioEntity.class);
       MedicoEntity entityMedico2 = factory.manufacturePojo(MedicoEntity.class);
       
       em.persist (entityConsultorio2);
       em.persist (entityMedico2);
       
       entityConsultorio2.setEspecialidad(esp2);
       entityConsultorio2.setSede(entitySede2);
       em.merge(entityConsultorio2);
            
       entityMedico2.setEspecialidad(esp2);
       em.merge(entityMedico2);
      
      HorarioAtencionEntity horario2 = factory.manufacturePojo(HorarioAtencionEntity.class); 
         
            horario2.setConsultorio(entityConsultorio2);
            horario2.setMedico(entityMedico2);
            horario2.setCitasMedicas(new ArrayList<>());
            
            horario2.setFechaInicio(new Date (1539608400000L));
            horario2.setFechaFin(new Date(1539615600000L));
            em.persist (horario2);
            data.add(horario2);         
        
    }
    
    
    
    
    
        
     /**
     * Prueba para crear un HorarioAtencion.
     * @throws co.edu.uniandes.csw.medicinaPrepagada.exceptions.BusinessLogicException
     */
    @Test
    public void createHorarioAtencionTest() throws BusinessLogicException
    {
        HorarioAtencionEntity newEntity = factory.manufacturePojo(HorarioAtencionEntity.class);
        newEntity.setConsultorio(data.get(0).getConsultorio());
        newEntity.setMedico(data.get(0).getMedico());
        
       Date incio = new Date (1538312400000L);
       Date fin = new Date (1538319600000L);
       newEntity.setFechaInicio(incio);
       newEntity.setFechaFin(fin);
        
        HorarioAtencionEntity result = horarioAtencionLogic.createHorarioAtencion(newEntity, newEntity.getConsultorio().getSede());
        Assert.assertNotNull(result);
        HorarioAtencionEntity entity = em.find(HorarioAtencionEntity.class, result.getId());
        Assert.assertEquals(newEntity.getId(), entity.getId());
        
                //Test Atributos
        Assert.assertEquals(newEntity.getFechaInicio(), entity.getFechaInicio());
        Assert.assertEquals(newEntity.getFechaFin(), entity.getFechaFin());
                //Test relaciones

        Assert.assertEquals(newEntity.getMedico(), entity.getMedico());
        Assert.assertEquals(newEntity.getConsultorio(), entity.getConsultorio());
        
     
    }
    
    
    
         /**
     * Prueba para crear un HorarioAtencion con fecha anterior a la actual.
     * @throws co.edu.uniandes.csw.medicinaPrepagada.exceptions.BusinessLogicException
     */
    @Test (expected = BusinessLogicException.class)
    public void createHorarioAtencionFechaAnteriorTest() throws BusinessLogicException
    {
        HorarioAtencionEntity newEntity = factory.manufacturePojo(HorarioAtencionEntity.class);
        newEntity.setConsultorio(data.get(1).getConsultorio());
        newEntity.setMedico(data.get(1).getMedico());
        
       Date incio = new Date (1537448400000L);
       Date fin = new Date (1537455600000L);
       newEntity.setFechaInicio(incio);
       newEntity.setFechaFin(fin);
        
        HorarioAtencionEntity result = horarioAtencionLogic.createHorarioAtencion(newEntity, newEntity.getConsultorio().getSede());
        Assert.assertNotNull(result);
        HorarioAtencionEntity entity = em.find(HorarioAtencionEntity.class, result.getId());
        Assert.assertEquals(newEntity.getId(), entity.getId());

    }
    
             /**
     * Prueba para crear un HorarioAtencion con fecha de iniciofuera del horario laboral.
     * @throws co.edu.uniandes.csw.medicinaPrepagada.exceptions.BusinessLogicException
     */
    @Test (expected = BusinessLogicException.class)
    public void createHorarioAtencionFechaFueraInicioDeRangoTest() throws BusinessLogicException
    {
        HorarioAtencionEntity newEntity = factory.manufacturePojo(HorarioAtencionEntity.class);
        newEntity.setConsultorio(data.get(1).getConsultorio());
        newEntity.setMedico(data.get(1).getMedico());
        
       Date incio = new Date (1538352000000L);
       Date fin = new Date (1538355600000L);
       newEntity.setFechaInicio(incio);
       newEntity.setFechaFin(fin);
        
        HorarioAtencionEntity result = horarioAtencionLogic.createHorarioAtencion(newEntity, newEntity.getConsultorio().getSede());
        Assert.assertNotNull(result);
        HorarioAtencionEntity entity = em.find(HorarioAtencionEntity.class, result.getId());
        Assert.assertEquals(newEntity.getId(), entity.getId());

    }
    
    
        
     /**
     * Prueba para crear un HorarioAtencion con fecha de fin fuera del horario laboral.
     * @throws co.edu.uniandes.csw.medicinaPrepagada.exceptions.BusinessLogicException
     */
    @Test (expected = BusinessLogicException.class)
    public void createHorarioAtencionFechaFueraFinDeRangoTest() throws BusinessLogicException
    {
        HorarioAtencionEntity newEntity = factory.manufacturePojo(HorarioAtencionEntity.class);
        newEntity.setConsultorio(data.get(1).getConsultorio());
        newEntity.setMedico(data.get(1).getMedico());
        
       Date incio = new Date (1538337600000L);
       Date fin = new Date (1538355600000L);
       newEntity.setFechaInicio(incio);
       newEntity.setFechaFin(fin);
        
        HorarioAtencionEntity result = horarioAtencionLogic.createHorarioAtencion(newEntity, newEntity.getConsultorio().getSede());
        Assert.assertNotNull(result);
        HorarioAtencionEntity entity = em.find(HorarioAtencionEntity.class, result.getId());
        Assert.assertEquals(newEntity.getId(), entity.getId());

    }
    
    
    
         /**
     * Prueba para crear un HorarioAtencion con fecha posterior a un mes de la actual.
     * @throws co.edu.uniandes.csw.medicinaPrepagada.exceptions.BusinessLogicException
     */
    @Test (expected = BusinessLogicException.class)
    public void createHorarioAtencionFechaFueraDeRangoMesTest() throws BusinessLogicException
    {
        HorarioAtencionEntity newEntity = factory.manufacturePojo(HorarioAtencionEntity.class);
        newEntity.setConsultorio(data.get(1).getConsultorio());
        newEntity.setMedico(data.get(1).getMedico());
        
       Date incio = new Date (1569848400000L);
       Date fin = new Date (1569855600000L);
       newEntity.setFechaInicio(incio);
       newEntity.setFechaFin(fin);
        
        HorarioAtencionEntity result = horarioAtencionLogic.createHorarioAtencion(newEntity, newEntity.getConsultorio().getSede());
        Assert.assertNotNull(result);
        HorarioAtencionEntity entity = em.find(HorarioAtencionEntity.class, result.getId());
        Assert.assertEquals(newEntity.getId(), entity.getId());

    }
    
             /**
     * Prueba para crear un HorarioAtencion con fecha de inicio y fin en diferentes dias.
     * @throws co.edu.uniandes.csw.medicinaPrepagada.exceptions.BusinessLogicException
     */
    @Test (expected = BusinessLogicException.class)
    public void createHorarioAtencionIniFinDiferenteDiaTest() throws BusinessLogicException
    {
        HorarioAtencionEntity newEntity = factory.manufacturePojo(HorarioAtencionEntity.class);
        newEntity.setConsultorio(data.get(1).getConsultorio());
        newEntity.setMedico(data.get(1).getMedico());
        
       Date incio = new Date (1538226000000L);
       Date fin = new Date (1538312400000L);
       newEntity.setFechaInicio(incio);
       newEntity.setFechaFin(fin);
        
        HorarioAtencionEntity result = horarioAtencionLogic.createHorarioAtencion(newEntity, newEntity.getConsultorio().getSede());
        Assert.assertNotNull(result);
        HorarioAtencionEntity entity = em.find(HorarioAtencionEntity.class, result.getId());
        Assert.assertEquals(newEntity.getId(), entity.getId());

    }
    
                 /**
     * Prueba para crear un HorarioAtencion con fecha de inicio y fin que no terminan en 00, 20 o 40.
     * @throws co.edu.uniandes.csw.medicinaPrepagada.exceptions.BusinessLogicException
     */
    @Test (expected = BusinessLogicException.class)
    public void createHorarioAtencionIniMalFormatoTest() throws BusinessLogicException
    {
        HorarioAtencionEntity newEntity = factory.manufacturePojo(HorarioAtencionEntity.class);
        newEntity.setConsultorio(data.get(2).getConsultorio());
        newEntity.setMedico(data.get(2).getMedico());
        
       Date incio = new Date (1538313000000L);
       Date fin = new Date (1538320200000L);
       newEntity.setFechaInicio(incio);
       newEntity.setFechaFin(fin);
        
        HorarioAtencionEntity result = horarioAtencionLogic.createHorarioAtencion(newEntity, newEntity.getConsultorio().getSede());
        Assert.assertNotNull(result);
        HorarioAtencionEntity entity = em.find(HorarioAtencionEntity.class, result.getId());
        Assert.assertEquals(newEntity.getId(), entity.getId());

    }
    
                 /**
     * Prueba para crear un HorarioAtencion que se cruza con otro existente .
     * @throws co.edu.uniandes.csw.medicinaPrepagada.exceptions.BusinessLogicException
     */
    @Test (expected = BusinessLogicException.class)
    public void createHorarioAtencionQueSeCruzaTest() throws BusinessLogicException
    {
        HorarioAtencionEntity newEntity = factory.manufacturePojo(HorarioAtencionEntity.class);
        newEntity.setConsultorio(data.get(0).getConsultorio());
        newEntity.setMedico(data.get(0).getMedico());
        
       Date incio = new Date (1539610800000L);
       Date fin = new Date (1539614400000L);
       newEntity.setFechaInicio(incio);
       newEntity.setFechaFin(fin);
        
        HorarioAtencionEntity result = horarioAtencionLogic.createHorarioAtencion(newEntity, newEntity.getConsultorio().getSede());
        Assert.assertNotNull(result);
        HorarioAtencionEntity entity = em.find(HorarioAtencionEntity.class, result.getId());
        Assert.assertEquals(newEntity.getId(), entity.getId());

    }
    
    /**
     * Prueba para crear un HorarioAtencion que y no encuentra consultorio .
     * @throws co.edu.uniandes.csw.medicinaPrepagada.exceptions.BusinessLogicException
     */
    @Test  (expected = BusinessLogicException.class)
    public void createHorarioAtencionSinConsultorioDispoTest() throws BusinessLogicException
    {
        HorarioAtencionEntity newEntity = factory.manufacturePojo(HorarioAtencionEntity.class);
        newEntity.setConsultorio(data.get(0).getConsultorio());
        newEntity.setMedico(data.get(0).getMedico());
        
         
        
       Date incio = new Date (1539176400000L);
       Date fin = new Date (1539183600000L);
       newEntity.setFechaInicio(incio);
       newEntity.setFechaFin(fin);
        
       SedeEntity entitySde = data.get(3).getConsultorio().getSede();
        HorarioAtencionEntity result = horarioAtencionLogic.createHorarioAtencion(newEntity,entitySde );
        Assert.assertNotNull(result);
        HorarioAtencionEntity entity = em.find(HorarioAtencionEntity.class, result.getId());
        Assert.assertEquals(newEntity.getId(), entity.getId());

    }
     /**
     * Prueba para consultar la lista de HorarioAtencions.
     */
    @Test
    public void getHorariosAtencionsTest() 
    {
        List<HorarioAtencionEntity> list = horarioAtencionLogic.getHorarioAtencions();
        Assert.assertEquals(data.size(), list.size());
        for (HorarioAtencionEntity entity : list) 
        {
            boolean found = false;
            for (HorarioAtencionEntity storedEntity : data) 
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
     * Prueba para consultar un HorarioAtencion que no existe.
     */
    @Test // (expected = BusinessLogicException.class)
    public void getHorarioAtencionInexistenteTest() 
    {
        HorarioAtencionEntity resultEntity = horarioAtencionLogic.getHorarioAtencion(999999L);
       Assert.assertNull(resultEntity);

    } 
    
    
                /**
     * Prueba para consultar un HorarioAtencion.
     */
    @Test
    public void getHorarioAtencionTest() 
    {
        HorarioAtencionEntity entity = data.get(0);
        HorarioAtencionEntity resultEntity = horarioAtencionLogic.getHorarioAtencion(entity.getId());
        Assert.assertNotNull(resultEntity);
        Assert.assertEquals(entity.getId(), resultEntity.getId());
        
                        //Test Atributos
        Assert.assertEquals(entity.getFechaFin(), resultEntity.getFechaFin());
        Assert.assertEquals(entity.getFechaInicio(), resultEntity.getFechaInicio());
                //Test relaciones

        Assert.assertEquals(entity.getMedico(), resultEntity.getMedico());
        Assert.assertEquals(entity.getConsultorio(), resultEntity.getConsultorio());
   
    }
    
    
    
    
         /**
     * Prueba para eliminar un HorarioAtencion
     *
     * @throws co.edu.uniandes.csw.medicinaPrepagada.exceptions.BusinessLogicException
     */
    @Test
    public void deleteHorarioAtencionTest() throws BusinessLogicException 
    {
        HorarioAtencionEntity entity = data.get(0);
        horarioAtencionLogic.deleteHorarioAtencion(entity.getId());
        HorarioAtencionEntity deleted = em.find(HorarioAtencionEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }
    
    
        
    /**
     * Prueba para eliminar un HorarioAtencion que no existe
     *
     * @throws co.edu.uniandes.csw.medicinaPrepagada.exceptions.BusinessLogicException
     */
    @Test (expected = BusinessLogicException.class)
    public void deleteHorarioAtencionInexistenteTest() throws BusinessLogicException 
    {
        horarioAtencionLogic.deleteHorarioAtencion(99999999L);
        HorarioAtencionEntity deleted = em.find(HorarioAtencionEntity.class, 99999999L );
        Assert.assertNull(deleted);
    }
    
}
