/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.medicinaPrepagada.test.logic;

import co.edu.uniandes.csw.medicinaPrepagada.ejb.CitaMedicaLogic;
import co.edu.uniandes.csw.medicinaPrepagada.entities.CitaMedicaEntity;
import co.edu.uniandes.csw.medicinaPrepagada.entities.ConsultorioEntity;
import co.edu.uniandes.csw.medicinaPrepagada.entities.HorarioAtencionEntity;
import co.edu.uniandes.csw.medicinaPrepagada.entities.MedicoEntity;
import co.edu.uniandes.csw.medicinaPrepagada.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.medicinaPrepagada.persistence.MedicoPersistence;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
 * @author Daniel Ivan Romero
 */
//@RunWith(Arquillian.class)
//public class CitaMedicaLogicTest {
//    private PodamFactory factory = new PodamFactoryImpl();
//
//    @Inject
//    private CitaMedicaLogic citaMedicaLogic;
//
//    @PersistenceContext
//    private EntityManager em;
//
//    @Inject
//    private UserTransaction utx;
//
//    private List<CitaMedicaEntity> data = new ArrayList<>();
//    
//    private HorarioAtencionEntity horario;
//    
//    /**
//     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
//     * El jar contiene las clases, el descriptor de la base de datos y el
//     * archivo beans.xml para resolver la inyección de dependencias.
//     */
//    
//    @Deployment
//    public static JavaArchive createDeployment() 
//    {
//        return ShrinkWrap.create(JavaArchive.class)
//                .addPackage(CitaMedicaEntity.class.getPackage())
//                .addPackage(CitaMedicaLogic.class.getPackage())
//                .addPackage(MedicoPersistence.class.getPackage())
//                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
//                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
//    }
//    
//    /**
//     * Configuración inicial de la prueba.
//     */
//    @Before
//    public void configTest() 
//    {
//        try 
//        {
//            utx.begin();
//            clearData();
//            insertData();
//            utx.commit();
//        } 
//        catch (Exception e) 
//        {
//            e.printStackTrace();
//            
//            try 
//            {
//                utx.rollback();
//            } 
//            catch (Exception e1) 
//            {
//                e1.printStackTrace();
//            }
//        }
//    }
//
//    /**
//     * Limpia las tablas que están implicadas en la prueba.
//     */
//    
//    private void clearData() 
//    {
//        em.createQuery("delete from CitaMedicaEntity").executeUpdate();
//    }
//
//    /**
//     * Inserta los datos iniciales para el correcto funcionamiento de las
//     * pruebas.
//     */
//    
//    private void insertData() 
//    {
//        try{  
//            
//            horario = factory.manufacturePojo(HorarioAtencionEntity.class);
//            ConsultorioEntity pConsultorio = factory.manufacturePojo(ConsultorioEntity.class);
//            MedicoEntity medico = factory.manufacturePojo(MedicoEntity.class);
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            String stringFechaConHora = "2018-01-01 00:00:00";
//            Date fechaConHora = sdf.parse(stringFechaConHora);
//            horario.setFechaInicio(fechaConHora);
//            stringFechaConHora = "2020-01-01 00:00:00";
//            fechaConHora = sdf.parse(stringFechaConHora);
//            horario.setFechaFin(fechaConHora);
//            horario.setConsultorio(pConsultorio);
//            horario.setMedico(medico);
//            em.persist(horario);
//            CitaMedicaEntity ent = factory.manufacturePojo(CitaMedicaEntity.class);
//            
//            
//            CitaMedicaEntity entity = factory.manufacturePojo(CitaMedicaEntity.class);
//            stringFechaConHora = "2019-01-01 00:00:00";
//            fechaConHora = sdf.parse(stringFechaConHora);
//            entity.setFecha(fechaConHora);
//            entity.setHorarioAtencionAsignado(horario);
//            em.persist(entity);
//            data.add(entity);
//            
//            CitaMedicaEntity entit = factory.manufacturePojo(CitaMedicaEntity.class);
//            stringFechaConHora = "2019-01-01 00:00:00";
//            fechaConHora = sdf.parse(stringFechaConHora);
//            entit.setFecha(fechaConHora);
//            entit.setHorarioAtencionAsignado(horario);
//            em.persist(entit);
//            data.add(entit);
//            
//            CitaMedicaEntity enti = factory.manufacturePojo(CitaMedicaEntity.class);
//            stringFechaConHora = "2019-01-01 00:00:00";
//            fechaConHora = sdf.parse(stringFechaConHora);
//            enti.setFecha(fechaConHora);
//            enti.setHorarioAtencionAsignado(horario);
//            em.persist(enti);
//            data.add(enti);
//            
//        }catch(ParseException e){
//            e.printStackTrace();
//        }
//    }
    
//    @Test
//    public void createCitaMedicaTest() throws BusinessLogicException 
//    {
//        try{   
//            CitaMedicaEntity newEntity = factory.manufacturePojo(CitaMedicaEntity.class);
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            String stringFechaConHora = "2019-01-02 00:00:00";
//            Date fechaConHora = sdf.parse(stringFechaConHora);
//            newEntity.setFecha(fechaConHora);
//            newEntity.setHorarioAtencionAsignado(horario);
//            CitaMedicaEntity result = citaMedicaLogic.createCitaMedica(newEntity);
//            Assert.assertNotNull(result);
//        
//            CitaMedicaEntity entity = em.find(CitaMedicaEntity.class, result.getId());
//            Assert.assertEquals(newEntity.getId(), entity.getId());
//        }catch(ParseException e){
//            e.printStackTrace();
//        }
//        
//        
//        
//        
//    }
//}
