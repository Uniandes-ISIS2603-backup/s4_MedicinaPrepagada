/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.medicinaPrepagada.test.logic;

import co.edu.uniandes.csw.medicinaPrepagada.ejb.FacturaLogic;
import co.edu.uniandes.csw.medicinaPrepagada.entities.FacturaEntity;
import co.edu.uniandes.csw.medicinaPrepagada.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.medicinaPrepagada.persistence.FacturaPersistence;
import java.time.Instant;
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
import org.junit.Before;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;
/**
 *
 * @author Santiago Rojas
 */
@RunWith(Arquillian.class)
public class FacturaLogicTest {
     private PodamFactory factory = new PodamFactoryImpl();

    @Inject
    private FacturaLogic facturaLogic;

    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;

    private List<FacturaEntity> factList = new ArrayList<>();
    
    @Deployment
    public static JavaArchive createDeployment() 
    {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(FacturaEntity.class.getPackage())
                .addPackage(FacturaLogic.class.getPackage())
                .addPackage(FacturaPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }
    
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
    
    private void clearData() {
        em.createQuery("delete from FacturaEntity").executeUpdate();
    }
     
    private void insertData() {
        for (int i = 0; i < 3; i++) {
            FacturaEntity entity = factory.manufacturePojo(FacturaEntity.class);
            em.persist(entity);
            factList.add(entity);
        }
       
    }
    
    @Test
    public void createFactura () throws BusinessLogicException
    {
       Date nueva = new Date(System.currentTimeMillis()+24*60*60*1000);
       FacturaEntity newEntity = factory.manufacturePojo(FacturaEntity.class);
       newEntity.setValor(1000);
       newEntity.setFecha(nueva);
       FacturaEntity result = facturaLogic.createFactura(newEntity);
       
       Assert.assertNotNull(result);
       FacturaEntity entity = em.find(FacturaEntity.class, result.getId());

    }
    
    @Test
    public void getAllFacturasTest() throws BusinessLogicException
    {
        List<FacturaEntity> list = facturaLogic.getFacturas();
        Assert.assertEquals(factList.size(), list.size());
        for (FacturaEntity entity : list) {
            boolean found = false;
            for (FacturaEntity storedEntity : factList) {
                if (entity.getId().equals(storedEntity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }
    
    @Test
    public void getFacturaTest() {
        FacturaEntity entity = factList.get(0);
        FacturaEntity resultEntity = facturaLogic.getFactura(entity.getId());
        Assert.assertNotNull(resultEntity);
        Assert.assertEquals(entity.getId(), resultEntity.getId());
    }
    /*
    @Test
    public void updateFaturaTest() throws BusinessLogicException
    {
       FacturaEntity entity = factList.get(1);
       FacturaEntity newEntity = factory.manufacturePojo(FacturaEntity.class);
       
       
       newEntity.setCitaLab(entity.getCitaLab());
       
       newEntity.setConcepto(entity.getConcepto());
       
       newEntity.setFecha(entity.getFecha());
       
       newEntity.setId(entity.getId());
       
       newEntity.setIdCliente(entity.getIdCliente());
       newEntity.setPaciente(entity.getPaciente());
       newEntity.setValor(entity.getValor());
        
       newEntity.setPagada(entity.getPagada());
       
       
       facturaLogic.updateFactura(newEntity);
       
       FacturaEntity resp = em.find(FacturaEntity.class, entity.getId());
       Assert.assertEquals(newEntity.getId(), resp.getId());
       
    }
    
   */ 
}
