/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.medicinaPrepagada.test.persistence;

import co.edu.uniandes.csw.medicinaPrepagada.entities.TarjetaCreditoEntity;
import co.edu.uniandes.csw.medicinaPrepagada.persistence.TarjetaCreditoPersistence;
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
 * @author MIGUELHOYOS
 */
@RunWith(Arquillian.class)
public class TarjetaCreditoPersistenceTest {
    
    @Inject
    private TarjetaCreditoPersistence tarjetaCreditoPersistence;
    
    @PersistenceContext
    private EntityManager em;
    
    @Inject
    UserTransaction utx;
    
    private List<TarjetaCreditoEntity> data = new ArrayList<>();
    
     /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive cerateDeployment(){
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(TarjetaCreditoEntity.class.getPackage())
                .addPackage(TarjetaCreditoPersistence.class.getPackage())
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
        em.createQuery("delete from TarjetaCreditoEntity").executeUpdate();
    }
    
    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData(){
        PodamFactory factory = new PodamFactoryImpl();
        int i = 0;
        while(i<3){
            TarjetaCreditoEntity entity = factory.manufacturePojo(TarjetaCreditoEntity.class);
            em.persist(entity);
            data.add(entity);
            i++;
        }
    }
    
     /**
     * Prueba para crear una TarjetaCredito.
     */
    @Test
    public void crearTarjetaCreditoTest(){
      PodamFactory factory = new PodamFactoryImpl();
      TarjetaCreditoEntity newEntity = factory.manufacturePojo(TarjetaCreditoEntity.class);
      TarjetaCreditoEntity result = tarjetaCreditoPersistence.create(newEntity);
      
      Assert.assertNotNull(result);
      
      TarjetaCreditoEntity entity = em.find(TarjetaCreditoEntity.class, result.getNumero());
      
      Assert.assertEquals(newEntity.getNombreEnTarjeta(), entity.getNombreEnTarjeta());
    }
    
    /**
     * Prueba para consultar la lista de TarjetasCredito
     */
    @Test
    public void getTarjetasCreditoTest(){
        List<TarjetaCreditoEntity> list = tarjetaCreditoPersistence.findAll();
        Assert.assertEquals(list.size(), data.size());
        for(TarjetaCreditoEntity ent : list){
            boolean found = false;
            for(TarjetaCreditoEntity ent2 : data){
                if(ent.getNumero().equals(ent2.getNumero())){
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }
    
    /**
     * prueba para consultar una TarjetaCredito
     */
    @Test
    public void getTarjetaCreditoTest(){
        TarjetaCreditoEntity entity = data.get(0);
        
        TarjetaCreditoEntity newEntity = tarjetaCreditoPersistence.find(entity.getNumero());
        Assert.assertNotNull(newEntity);
        Assert.assertEquals(entity.getNombreEnTarjeta(), newEntity.getNombreEnTarjeta());
        Assert.assertEquals(entity.getFranquicia(), newEntity.getFranquicia());
    }
    
        /**
     * prueba para actualizar una TarjetaCredito
     */
    @Test
    public void updatePacienteTest(){
        TarjetaCreditoEntity entity = data.get(0);
        PodamFactory factory = new PodamFactoryImpl();
        TarjetaCreditoEntity newEntity = factory.manufacturePojo(TarjetaCreditoEntity.class);
        
        newEntity.setNumero(entity.getNumero());
        
        tarjetaCreditoPersistence.update(newEntity);
        
        TarjetaCreditoEntity resp = em.find(TarjetaCreditoEntity.class, entity.getNumero());
        
        Assert.assertEquals(newEntity.getNombreEnTarjeta(), newEntity.getNombreEnTarjeta());
    }
    
    /**
     * prueba para eliminar una TarjetaCredito
     */
    @Test
    public void DeletePacienteTest(){
        TarjetaCreditoEntity entity = data.get(0);
        tarjetaCreditoPersistence.delete(entity.getNumero());
        TarjetaCreditoEntity deleted = em.find(TarjetaCreditoEntity.class, entity.getNumero());
        Assert.assertNull(deleted);
    }
}
