/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.medicinaPrepagada.test.persistence;

import co.edu.uniandes.csw.medicinaPrepagada.entities.ConsultorioEntity;
import co.edu.uniandes.csw.medicinaPrepagada.entities.HorarioAtencionEntity;
import co.edu.uniandes.csw.medicinaPrepagada.persistence.ConsultorioPersistence;
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
public class HorarioAtencionTest 
{
    
    
      
    @Inject
    private HorarioAtencionPersistence horarioAtencionPersistence;

    @PersistenceContext
    private EntityManager em;

    @Inject
    UserTransaction utx;

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
                .addPackage(HorarioAtencionPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }
    
    
    /**
     * Configuración inicial de la prueba.
     */
    @Before
    public void configTest() 
    {
        try 
        {
            utx.begin();
            em.joinTransaction();
            clearData();
            insertData();
            utx.commit();
        } 
        catch (Exception e)
        {
            e.printStackTrace();
            try 
            {
                utx.rollback();
            } 
            catch (Exception e1) {
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
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() 
    {
        PodamFactory factory = new PodamFactoryImpl();
        for (int i = 0; i < 3; i++) 
        {
            HorarioAtencionEntity entity = factory.manufacturePojo(HorarioAtencionEntity.class);

            em.persist(entity);
            data.add(entity);
        }
    }
    
    //TESTS
  
    
    
    
    
    
     /**
     * Prueba para crear una HorarioAtencion
     */
    @Test
    public void createHorarioAtencionTest() 
    {
        PodamFactory factory = new PodamFactoryImpl();
        HorarioAtencionEntity newEntity = factory.manufacturePojo(HorarioAtencionEntity.class);
        HorarioAtencionEntity result = horarioAtencionPersistence.create(newEntity);

        Assert.assertNotNull(result);

        HorarioAtencionEntity entity = em.find(HorarioAtencionEntity.class, result.getId());
        //Test Atributos
        Assert.assertEquals(newEntity.getFechaInicio(), entity.getFechaInicio());
        Assert.assertEquals(newEntity.getFechaFin(), entity.getFechaFin());
        
        //Test relaciones
        Assert.assertEquals(newEntity.getMedico(), entity.getMedico());
        Assert.assertEquals(newEntity.getConsultorio(), entity.getConsultorio());
        Assert.assertEquals(newEntity.getCitasMedicas().size(), entity.getCitasMedicas().size());
        for (int i=0;i<newEntity.getCitasMedicas().size();i++)
        {
            Assert.assertEquals(newEntity.getCitasMedicas().get(i), entity.getCitasMedicas().get(i));
        }
    
    }
    
    
    
    
    /**
     * Prueba para consultar la lista de HorarioAtencions.
     */
    @Test
    public void getHorarioAtencionsTest() 
    {
        List<HorarioAtencionEntity> list = horarioAtencionPersistence.findAll();
        Assert.assertEquals(data.size(), list.size());
        for (HorarioAtencionEntity ent : list)
        {
            boolean found = false;
            for (HorarioAtencionEntity entity : data) 
            {
                if (ent.getId().equals(entity.getId())) 
                
                {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }
    
    
      
    
     /**
     * Prueba para consultar un HorarioAtencion.
     */
    @Test
    public void getHorarioAtencionTest()
    {
        HorarioAtencionEntity entity = data.get(0);
        HorarioAtencionEntity newEntity = horarioAtencionPersistence.find(entity.getId());
        Assert.assertNotNull(newEntity);
 
          //Test Atributos
        Assert.assertEquals(newEntity.getFechaInicio(), entity.getFechaInicio());
        Assert.assertEquals(newEntity.getFechaFin(), entity.getFechaFin());
        
        //Test relaciones
        Assert.assertEquals(newEntity.getMedico(), entity.getMedico());
        Assert.assertEquals(newEntity.getConsultorio(), entity.getConsultorio());
        Assert.assertEquals(newEntity.getCitasMedicas().size(), entity.getCitasMedicas().size());
        for (int i=0;i<newEntity.getCitasMedicas().size();i++)
        {
            Assert.assertEquals(newEntity.getCitasMedicas().get(i), entity.getCitasMedicas().get(i));
        }

    }
    
    
           /**
     * Prueba para actualizar un HorarioAtencion.
     */
    @Test
    public void updateHorarioAtencionTest()
    {
        HorarioAtencionEntity entity = data.get(0);
        PodamFactory factory = new PodamFactoryImpl();
        HorarioAtencionEntity newEntity = factory.manufacturePojo(HorarioAtencionEntity.class);
        newEntity.setId(entity.getId());
        horarioAtencionPersistence.update(newEntity);
        HorarioAtencionEntity resp = em.find(HorarioAtencionEntity.class, entity.getId());
        
       
          //Test Atributos
        Assert.assertEquals(newEntity.getFechaInicio(), resp.getFechaInicio());
        Assert.assertEquals(newEntity.getFechaFin(), resp.getFechaFin());
        
        //Test relaciones
        Assert.assertEquals(newEntity.getMedico(), resp.getMedico());
        Assert.assertEquals(newEntity.getConsultorio(), resp.getConsultorio());
        Assert.assertEquals(newEntity.getCitasMedicas().size(), resp.getCitasMedicas().size());
        for (int i=0;i<newEntity.getCitasMedicas().size();i++)
        {
            Assert.assertEquals(newEntity.getCitasMedicas().get(i), resp.getCitasMedicas().get(i));
        }

        
    }
    
    
    
    
    
    
    
    
         /**
     * Prueba para eliminar un HorarioAtencion.
     */
    @Test
    public void deleteHorarioAtencionTest() 
    {
        HorarioAtencionEntity entity = data.get(0);
        horarioAtencionPersistence.delete(entity.getId());
        HorarioAtencionEntity deleted = em.find(HorarioAtencionEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }
    
    
    
    
}
