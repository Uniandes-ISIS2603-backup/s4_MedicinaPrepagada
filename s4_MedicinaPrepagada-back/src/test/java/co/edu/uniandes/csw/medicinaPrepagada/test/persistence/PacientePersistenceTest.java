/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.medicinaPrepagada.test.persistence;



import co.edu.uniandes.csw.medicinaPrepagada.entities.PacienteEntity;
import co.edu.uniandes.csw.medicinaPrepagada.persistence.PacientePersistence;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import org.junit.Assert;
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
 *Pruebas persistencia Paciente
 * @author MIGUELHOYOS
 */
@RunWith(Arquillian.class)
public class PacientePersistenceTest {
    
    @Inject
    private PacientePersistence pacientePersistence;
    
    @PersistenceContext
    private EntityManager em;
    
    @Inject
    UserTransaction utx;
    
    private List<PacienteEntity> data = new ArrayList<>();
    
    /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive cerateDeployment(){
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(PacienteEntity.class.getPackage())
                .addPackage(PacientePersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }
    
    /**
     * Configuración inicial de la prueba
     */
    @Before
    public void configTest(){
         try {
            utx.begin();
            em.joinTransaction();
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
    private void clearData(){
        em.createQuery("delete from PacienteEntity").executeUpdate();
    }
    
    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData(){
        PodamFactory factory = new PodamFactoryImpl();
        int i = 0;
        while(i<3){
            PacienteEntity entity = factory.manufacturePojo(PacienteEntity.class);
            em.persist(entity);
            data.add(entity);
            i++;
        }
    }
    
    /**
     * Prueba para crear un Paciente.
     */
    @Test
    public void crearPacienteTest(){
      PodamFactory factory = new PodamFactoryImpl();
      PacienteEntity newEntity = factory.manufacturePojo(PacienteEntity.class);
      PacienteEntity result = pacientePersistence.create(newEntity);
      
      Assert.assertNotNull(result);
      
      PacienteEntity entity = em.find(PacienteEntity.class, result.getId());
      
      Assert.assertEquals(newEntity.getNombre(), entity.getNombre());
    }
    
    /**
     * Prueba para consultar la lista de Pacienets
     */
    @Test
    public void getPacientesTest(){
        List<PacienteEntity> list = pacientePersistence.findAll();
        Assert.assertEquals(list.size(), data.size());
        for(PacienteEntity ent : list){
            boolean found = false;
            for(PacienteEntity ent2 : data){
                if(ent.getId().equals(ent2.getId())){
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }
    
    /**
     * prueba para consultar un Paciente
     */
    @Test
    public void getPacienteTest(){
        PacienteEntity entity = data.get(0);
        PacienteEntity newEntity = pacientePersistence.find(entity.getId());
        Assert.assertNotNull(newEntity);
        Assert.assertEquals(entity.getNombre(), newEntity.getNombre());
        Assert.assertEquals(entity.getDireccion(), newEntity.getDireccion());
    }
    
    /**
     * prueba para actualizar un Paciente
     */
    @Test
    public void updatePacienteTest(){
        PacienteEntity entity = data.get(0);
        PodamFactory factory = new PodamFactoryImpl();
        PacienteEntity newEntity = factory.manufacturePojo(PacienteEntity.class);
        
        newEntity.setId(entity.getId());
        
        pacientePersistence.update(newEntity);
        
        PacienteEntity resp = em.find(PacienteEntity.class, entity.getId());
        
        Assert.assertEquals(newEntity.getNombre(), newEntity.getNombre());
    }
    
    /**
     * prueba para eliminar un Paciente
     */
    @Test
    public void deletePacienteTest(){
        PacienteEntity entity = data.get(0);
        pacientePersistence.delete(entity.getId());
        PacienteEntity deleted = em.find(PacienteEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }
    
    /**
     * prueba para encontrar un paciente mediante el login
     */
    @Test
    public void getPacienteByLoginTest(){
        PacienteEntity entity = data.get(0);
        PacienteEntity newEntity = pacientePersistence.getPacienteByLogin(entity.getLogin());
        Assert.assertNotNull(newEntity);
        Assert.assertEquals(entity.getId(), newEntity.getId());
    }
    
}


