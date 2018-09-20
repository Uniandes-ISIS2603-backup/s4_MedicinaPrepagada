/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.medicinaPrepagada.test.logic;

import co.edu.uniandes.csw.medicinaPrepagada.ejb.HorarioAtencionLogic;
import co.edu.uniandes.csw.medicinaPrepagada.entities.ConsultorioEntity;
import co.edu.uniandes.csw.medicinaPrepagada.entities.HorarioAtencionEntity;
import co.edu.uniandes.csw.medicinaPrepagada.entities.MedicoEntity;
import co.edu.uniandes.csw.medicinaPrepagada.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.medicinaPrepagada.persistence.HorarioAtencionPersistence;
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
        em.createQuery("delete from ConsultorioEntity").executeUpdate();
        em.createQuery("delete from SedeEntity").executeUpdate();
        em.createQuery("delete from HorarioAtencionEntity").executeUpdate();
        em.createQuery("delete from MedicoEntity").executeUpdate();     
    }
    
            /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        for (int i = 0; i < 4; i++) 
        {
            HorarioAtencionEntity entity = factory.manufacturePojo(HorarioAtencionEntity.class);
            ConsultorioEntity entityConsultorio = factory.manufacturePojo(ConsultorioEntity.class);
            MedicoEntity entityMedico = factory.manufacturePojo(MedicoEntity.class);
            em.persist (entityConsultorio);
            em.persist (entityMedico);
            
            entity.setConsultorio(entityConsultorio);
            entity.setMedico(entityMedico);
            entity.setCitasMedicas(new ArrayList<>());
            em.persist (entity);
            data.add(entity);           
        }

        
        
    }
    
    
//             /**
//     * Prueba para consultar la lista de HorarioAtencions.
//     */
//    @Test
//    public void getHorariosAtencionsTest() 
//    {
//        List<HorarioAtencionEntity> list = horarioAtencionLogic.getHorarioAtencions();
//        Assert.assertEquals(data.size(), list.size());
//        for (HorarioAtencionEntity entity : list) 
//        {
//            boolean found = false;
//            for (HorarioAtencionEntity storedEntity : data) 
//            {
//                if (entity.getId().equals(storedEntity.getId()))
//                {
//                    found = true;
//                }
//            }
//            Assert.assertTrue(found);
//        }
//    }
    
    
    
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
    
//    
//    
//    
//         /**
//     * Prueba para eliminar un HorarioAtencion
//     *
//     * @throws co.edu.uniandes.csw.medicinaPrepagada.exceptions.BusinessLogicException
//     */
//    @Test
//    public void deleteHorarioAtencionTest() throws BusinessLogicException 
//    {
//        HorarioAtencionEntity entity = data.get(0);
//        horarioAtencionLogic.deleteHorarioAtencion(entity.getId());
//        HorarioAtencionEntity deleted = em.find(HorarioAtencionEntity.class, entity.getId());
//        Assert.assertNull(deleted);
//    }
    
    
        
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
