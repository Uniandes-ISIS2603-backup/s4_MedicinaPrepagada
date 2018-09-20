/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.medicinaPrepagada.test.logic;
import co.edu.uniandes.csw.medicinaPrepagada.ejb.LaboratorioLogic;
import co.edu.uniandes.csw.medicinaPrepagada.entities.CitaLaboratorioEntity;
import co.edu.uniandes.csw.medicinaPrepagada.entities.LaboratorioEntity;
import co.edu.uniandes.csw.medicinaPrepagada.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.medicinaPrepagada.persistence.LaboratorioPersistence;
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
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;
import org.junit.runner.RunWith;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

/**
 *
 * @author Santiago Rojas
 */
@RunWith(Arquillian.class)
public class LaboratorioLogicTest {
    
    private PodamFactory factory = new PodamFactoryImpl();

    @Inject
    private LaboratorioLogic labLogic;

    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;

    private List<LaboratorioEntity> labList = new ArrayList<>();
    
    @Deployment
    public static JavaArchive createDeployment() 
    {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(LaboratorioEntity.class.getPackage())
                .addPackage(LaboratorioLogic.class.getPackage())
                .addPackage(LaboratorioPersistence.class.getPackage())
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
        em.createQuery("delete from LaboratorioEntity").executeUpdate();
        em.createQuery("delete from CitaLaboratorioEntity").executeUpdate();
    }
     
    private void insertData() {
        for (int i = 0; i < 3; i++) {
            LaboratorioEntity entity = factory.manufacturePojo(LaboratorioEntity.class);
            em.persist(entity);
            entity.setCitasLaboratorio(new ArrayList<>());
            labList.add(entity);
        }
        
        LaboratorioEntity lab = labList.get(0);
        CitaLaboratorioEntity entity = factory.manufacturePojo(CitaLaboratorioEntity.class);
        em.persist(entity);
        lab.getCitasLaboratorio().add(entity);
        em.merge(lab);
       
    }
    
    @Test
    public void createLaboratorio () throws BusinessLogicException
    {
       LaboratorioEntity newLabEntity = factory.manufacturePojo(LaboratorioEntity.class);
       
       newLabEntity.setNombre("Laboratorio Asanar");
       newLabEntity.setDireccion("cra 12 # 12-25");
       newLabEntity.setLatitud(4.6097100);
       newLabEntity.setLongitud( -74.0817500);
       newLabEntity.setHorarioAtencion("Lunes a Sabados, 8:00am a 12:00m. Entrega resultados: 1:00pm a 5:00pm");
       newLabEntity.setTelefono(new Long(4576221));
       
       LaboratorioEntity result = labLogic.createLaboratorio(newLabEntity);
       Assert.assertNotNull(result);
       LaboratorioEntity entity = em.find(LaboratorioEntity.class, result.getId());
       Assert.assertEquals(newLabEntity.getId(), entity.getId());
       
       
    }
    
    @Test
    public void getLabsTest() {
        List<LaboratorioEntity> list = labLogic.getLabs();
        Assert.assertEquals(labList.size(), list.size());
        for (LaboratorioEntity entity : list) {
            boolean found = false;
            for (LaboratorioEntity storedEntity : labList) {
                if (entity.getId().equals(storedEntity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }
    @Test
    public void getLabTest() {
        LaboratorioEntity entity = labList.get(0);
        LaboratorioEntity resultEntity = labLogic.getLab(entity.getId());
        Assert.assertNotNull(resultEntity);
        Assert.assertEquals(entity.getId(), resultEntity.getId());
    }
    
    @Test
    public void updateLabTest ()throws BusinessLogicException
    { 
        LaboratorioEntity entity = labList.get(0);
        LaboratorioEntity pojoEntity = factory.manufacturePojo(LaboratorioEntity.class);
        
        pojoEntity.setId(entity.getId());
        pojoEntity.setNombre("Laboratorio 2039");
        pojoEntity.setDireccion(entity.getDireccion());
        pojoEntity.setLatitud(entity.getLatitud());
        pojoEntity.setLongitud(entity.getLongitud());
        pojoEntity.setTelefono(4576221L);
        pojoEntity.setHorarioAtencion(entity.getHorarioAtencion());
     
        labLogic.updateLaboratorio(pojoEntity.getId(), pojoEntity);

        LaboratorioEntity resp = em.find(LaboratorioEntity.class, entity.getId());

        Assert.assertEquals(pojoEntity.getId(), resp.getId());
    }
    
    @Test(expected = BusinessLogicException.class)
    public void updateLabConMalLatitudTest()throws BusinessLogicException
    {
        LaboratorioEntity entity = labList.get(0);
        LaboratorioEntity pojoEntity = factory.manufacturePojo(LaboratorioEntity.class);
        
        pojoEntity.setId(entity.getId());
        pojoEntity.setNombre("Laboratorio 2039");
        pojoEntity.setDireccion(entity.getDireccion());
        pojoEntity.setLatitud(456);
        pojoEntity.setLongitud(entity.getLongitud());
        pojoEntity.setTelefono(4576221L);
        pojoEntity.setHorarioAtencion(entity.getHorarioAtencion());
     
        labLogic.updateLaboratorio(labList.get(0).getId(), pojoEntity);

        LaboratorioEntity resp = em.find(LaboratorioEntity.class, entity.getId());

        Assert.assertNull(resp);
    }
    @Test(expected = BusinessLogicException.class)
    public void updateLabConMalLongitudTest()throws BusinessLogicException
    {
        LaboratorioEntity entity = labList.get(0);
        LaboratorioEntity pojoEntity = factory.manufacturePojo(LaboratorioEntity.class);
        
        pojoEntity.setId(entity.getId());
        pojoEntity.setNombre("Laboratorio 666   ");
        pojoEntity.setDireccion(entity.getDireccion());
        pojoEntity.setLatitud(entity.getLatitud());
        pojoEntity.setLongitud(456);
        pojoEntity.setTelefono(4576221L);
        pojoEntity.setHorarioAtencion(entity.getHorarioAtencion());
     
        labLogic.updateLaboratorio(labList.get(0).getId(), pojoEntity);

        LaboratorioEntity resp = em.find(LaboratorioEntity.class, entity.getId());

        Assert.assertNull(resp);
    }
     
    @Test
    public void deleteLabTest () throws BusinessLogicException
    {
        LaboratorioEntity entity = labList.get(0);
        labLogic.deleteLaboratorio(entity.getId());
        LaboratorioEntity borrado = em.find(LaboratorioEntity.class, entity.getId());
        Assert.assertNull(borrado);
    }
    
    
    
}
