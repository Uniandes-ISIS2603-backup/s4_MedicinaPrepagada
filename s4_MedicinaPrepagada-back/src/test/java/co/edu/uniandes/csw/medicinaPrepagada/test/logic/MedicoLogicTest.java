/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.medicinaPrepagada.test.logic;

import co.edu.uniandes.csw.medicinaPrepagada.ejb.MedicoLogic;
import co.edu.uniandes.csw.medicinaPrepagada.entities.MedicoEntity;
import co.edu.uniandes.csw.medicinaPrepagada.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.medicinaPrepagada.persistence.MedicoPersistence;
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
 * @medico Daniel Ivan Romero
 */
@RunWith(Arquillian.class)
public class MedicoLogicTest {
    private PodamFactory factory = new PodamFactoryImpl();

    @Inject
    private MedicoLogic medicoLogic;

    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;

    private List<MedicoEntity> data = new ArrayList<>();
    
    /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    
    @Deployment
    public static JavaArchive createDeployment() 
    {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(MedicoEntity.class.getPackage())
                .addPackage(MedicoLogic.class.getPackage())
                .addPackage(MedicoPersistence.class.getPackage())
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
            catch (Exception e1) 
            {
                e1.printStackTrace();
            }
        }
    }

    /**
     * Limpia las tablas que están implicadas en la prueba.
     */
    
    private void clearData() 
    {
        em.createQuery("delete from MedicoEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    
    private void insertData() 
    {
        for (int i = 0; i < 3; i++) 
        {
            MedicoEntity entity = factory.manufacturePojo(MedicoEntity.class);
            entity.setDocumentoMedico(i);
            em.persist(entity);
            data.add(entity);
        }
    }
    
    /**
     * Prueba para crear una Historia Clinica.
     * @throws co.edu.uniandes.csw.medicinaPrepagada.exceptions.BusinessLogicException
     */
    
    @Test
    public void createMedicoTest() throws BusinessLogicException 
    {
        MedicoEntity newEntity = factory.manufacturePojo(MedicoEntity.class);
        newEntity.setNombre("Alberto Rodriguez");
        newEntity.setTelefono(1234567);
        newEntity.setCorreo("alberto@uniandes.com");
        newEntity.setDocumentoMedico(5);
        newEntity.setFirma("firma del medico");
        newEntity.setContrasena("abcdefgh1");
        MedicoEntity result = medicoLogic.createMedico(newEntity);
        Assert.assertNotNull(result);
        
        MedicoEntity entity = em.find(MedicoEntity.class, result.getId());
        Assert.assertEquals(newEntity.getId(), entity.getId());
    }
    
    @Test(expected = BusinessLogicException.class)
    public void createMedicoNombreNullTest() throws BusinessLogicException 
    {
        MedicoEntity newEntity = factory.manufacturePojo(MedicoEntity.class);
        newEntity.setNombre(null);
        newEntity.setTelefono(1234567);
        newEntity.setCorreo("alberto@uniandes.com");
        newEntity.setDocumentoMedico(6);
        newEntity.setFirma("firma del medico");
        newEntity.setContrasena("abcdefgh1");
        MedicoEntity result = medicoLogic.createMedico(newEntity);
        Assert.assertNotNull(result);
        
        MedicoEntity entity = em.find(MedicoEntity.class, result.getId());
        Assert.assertEquals(newEntity.getId(), entity.getId());
    }
    
    @Test(expected = BusinessLogicException.class)
    public void createMedicoTelefonoMalTest() throws BusinessLogicException 
    {
        MedicoEntity newEntity = factory.manufacturePojo(MedicoEntity.class);
        newEntity.setNombre("Alberto Rodriguez");
        newEntity.setTelefono(123456);
        newEntity.setCorreo("alberto@uniandes.com");
        newEntity.setDocumentoMedico(7);
        newEntity.setFirma("firma del medico");
        newEntity.setContrasena("abcdefgh1");
        MedicoEntity result = medicoLogic.createMedico(newEntity);
        Assert.assertNotNull(result);
        
        MedicoEntity entity = em.find(MedicoEntity.class, result.getId());
        Assert.assertEquals(newEntity.getId(), entity.getId());
    }
    
    @Test(expected = BusinessLogicException.class)
    public void createMedicoDocumentoRepetidoTest() throws BusinessLogicException 
    {
        MedicoEntity newEntity = factory.manufacturePojo(MedicoEntity.class);
        newEntity.setNombre("Alberto Rodriguez");
        newEntity.setTelefono(1234567);
        newEntity.setCorreo("alberto@uniandes.com");
        newEntity.setDocumentoMedico(1);
        newEntity.setFirma("firma del medico");
//        newEntity.setContrasena("abcdefgh1");
        MedicoEntity result = medicoLogic.createMedico(newEntity);
        
        Assert.assertNotNull(result);
        
        MedicoEntity entity = em.find(MedicoEntity.class, result.getId());
        Assert.assertEquals(newEntity.getId(), entity.getId());
        
    }
    
    @Test(expected = BusinessLogicException.class)
    public void createMedicoFirmaNullTest() throws BusinessLogicException 
    {
        MedicoEntity newEntity = factory.manufacturePojo(MedicoEntity.class);
        newEntity.setNombre("Alberto Rodriguez");
        newEntity.setTelefono(1234567);
        newEntity.setCorreo("alberto@uniandes.com");
        newEntity.setDocumentoMedico(9);
        newEntity.setFirma(null);
        newEntity.setContrasena("abcdefgh1");
        MedicoEntity result = medicoLogic.createMedico(newEntity);
        Assert.assertNotNull(result);
        
        MedicoEntity entity = em.find(MedicoEntity.class, result.getId());
        Assert.assertEquals(newEntity.getId(), entity.getId());
    }
    
//    @Test(expected = BusinessLogicException.class)
//    public void createMedicoContrasenaNullTest() throws BusinessLogicException 
//    {
//        MedicoEntity newEntity = factory.manufacturePojo(MedicoEntity.class);
//        newEntity.setNombre("Alberto Rodriguez");
//        newEntity.setTelefono(1234567);
//        newEntity.setCorreo("alberto@uniandes.com");
//        newEntity.setDocumentoMedico(1);
//        newEntity.setFirma("firma del medico");
//        newEntity.setContrasena(null);
//        MedicoEntity result = medicoLogic.createMedico(newEntity);
//        Assert.assertNotNull(result);
//        
//        MedicoEntity entity = em.find(MedicoEntity.class, result.getId());
//        Assert.assertEquals(newEntity.getId(), entity.getId());
//    }
    
    @Test(expected = BusinessLogicException.class)
    public void createMedicoCorreoNoValidoTest() throws BusinessLogicException 
    {
        MedicoEntity newEntity = factory.manufacturePojo(MedicoEntity.class);
        newEntity.setNombre("Alberto Rodriguez");
        newEntity.setTelefono(1234567);
        newEntity.setCorreo("albertouniandes.com");
        newEntity.setDocumentoMedico(10);
        newEntity.setFirma("firma del medico");
        newEntity.setContrasena("abcdefgh1");
        MedicoEntity result = medicoLogic.createMedico(newEntity);
        Assert.assertNotNull(result);
        
        MedicoEntity entity = em.find(MedicoEntity.class, result.getId());
        Assert.assertEquals(newEntity.getId(), entity.getId());
    }
    /**
     * Prueba para consultar la lista de medicos clinicas.
     */
    
    @Test
    public void getHistoriasClinicasTest()
    {
        List<MedicoEntity> list = medicoLogic.getMedicos();
        Assert.assertEquals(data.size(), list.size());
        
        for (MedicoEntity entity : list) 
        {
            boolean found = false;
            
            for (MedicoEntity storedEntity : data) 
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
     * Prueba para consultar una medico clinica.
     */
    
    @Test
    public void getMedicoTest() 
    {
        MedicoEntity entity = data.get(0);
        MedicoEntity resultEntity = medicoLogic.getMedico(entity.getId());
        Assert.assertNotNull(resultEntity);
        
    }
    
    /**
     * Prueba para actualizar una medico clinica.
     */
    
    @Test
    public void updateMedicoTest() throws BusinessLogicException
    {
        MedicoEntity entity = data.get(0);
        MedicoEntity pojoEntity = factory.manufacturePojo(MedicoEntity.class);
        pojoEntity.setNombre("Alberto Rodriguez");
        pojoEntity.setTelefono(1234567);
        pojoEntity.setCorreo("alberto@uniandes.com");
        pojoEntity.setDocumentoMedico(5);
        pojoEntity.setFirma("firma del medico");
        pojoEntity.setContrasena("abcdefgh1");
        pojoEntity.setId(entity.getId());

        medicoLogic.updateMedico(pojoEntity.getId(), pojoEntity);

        MedicoEntity resp = em.find(MedicoEntity.class, entity.getId());

    }
    
    /**
     * Prueba para eliminar una medico clinica
     * @throws co.edu.uniandes.csw.medicinaPrepagada.exceptions.BusinessLogicException
     */
    
    @Test
    public void deleteMedicoTest() throws BusinessLogicException 
    {
        MedicoEntity entity = data.get(0);
        medicoLogic.deleteMedico(entity.getId());
        MedicoEntity deleted = em.find(MedicoEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }
}
