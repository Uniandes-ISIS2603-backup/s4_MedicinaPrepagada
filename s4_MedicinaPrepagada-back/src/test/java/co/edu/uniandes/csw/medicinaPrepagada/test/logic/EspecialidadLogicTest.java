/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.medicinaPrepagada.test.logic;

import co.edu.uniandes.csw.medicinaPrepagada.ejb.EspecialidadLogic;
import co.edu.uniandes.csw.medicinaPrepagada.entities.EspecialidadEntity;
import co.edu.uniandes.csw.medicinaPrepagada.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.medicinaPrepagada.persistence.EspecialidadPersistence;
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
 * @author Daniel Ivan Romero
 */
@RunWith(Arquillian.class)
public class EspecialidadLogicTest {
    private PodamFactory factory = new PodamFactoryImpl();

    @Inject
    private EspecialidadLogic especialidadLogic;

    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;

    private List<EspecialidadEntity> data = new ArrayList<>();
    
    /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    
    @Deployment
    public static JavaArchive createDeployment() 
    {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(EspecialidadEntity.class.getPackage())
                .addPackage(EspecialidadLogic.class.getPackage())
                .addPackage(EspecialidadPersistence.class.getPackage())
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
        em.createQuery("delete from EspecialidadEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    
    private void insertData() 
    {
        for (int i = 0; i < 3; i++) 
        {
            EspecialidadEntity entity = factory.manufacturePojo(EspecialidadEntity.class);
            entity.setNombre(String.valueOf(i));
            em.persist(entity);
            data.add(entity);
        }
    }
    
    @Test
    public void createEspecialidadTest() throws BusinessLogicException {
        EspecialidadEntity newEntity = factory.manufacturePojo(EspecialidadEntity.class);
        newEntity.setNombre("Endocrino");
        EspecialidadEntity result = especialidadLogic.createEspecialidad(newEntity);
        Assert.assertNotNull(result);
        EspecialidadEntity entity = em.find(EspecialidadEntity.class, result.getNombre());
        Assert.assertEquals(newEntity.getNombre(), entity.getNombre());
    }


    @Test(expected = BusinessLogicException.class)
    public void createEspecialidadNombreNullTest() throws BusinessLogicException {
        EspecialidadEntity newEntity = factory.manufacturePojo(EspecialidadEntity.class);
        newEntity.setNombre(null);
        EspecialidadEntity result = especialidadLogic.createEspecialidad(newEntity);
        Assert.assertNotNull(result);
        EspecialidadEntity entity = em.find(EspecialidadEntity.class, result.getNombre());
        Assert.assertEquals(newEntity.getNombre(), entity.getNombre());
    }
    
    @Test(expected = BusinessLogicException.class)
    public void createEspecialidadNombreRepetidoTest() throws BusinessLogicException {
        EspecialidadEntity newEntity = factory.manufacturePojo(EspecialidadEntity.class);
        newEntity.setNombre("1");
        EspecialidadEntity result = especialidadLogic.createEspecialidad(newEntity);
        Assert.assertNotNull(result);
        EspecialidadEntity entity = em.find(EspecialidadEntity.class, result.getNombre());
        Assert.assertEquals(newEntity.getNombre(), entity.getNombre());
    }
    /**
     * Prueba para consultar la lista de Especialidads.
     */
    @Test
    public void getEspecialidadsTest() {
        List<EspecialidadEntity> list = especialidadLogic.getEspecialidades();
        Assert.assertEquals(data.size(), list.size());
        for (EspecialidadEntity entity : list) {
            boolean found = false;
            for (EspecialidadEntity storedEntity : data) {
                if (entity.getNombre().equals(storedEntity.getNombre())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }

    /**
     * Prueba para consultar un Especialidad.
     */
    @Test
    public void getEspecialidadTest() {
        EspecialidadEntity entity = data.get(0);
        EspecialidadEntity resultEntity = especialidadLogic.getEspecialidad(entity.getNombre());
        Assert.assertNotNull(resultEntity);
        Assert.assertEquals(entity.getNombre(), resultEntity.getNombre());
    }

    /**
     * Prueba para actualizar un Especialidad.
     */
    @Test
    public void updateEspecialidadTest() throws BusinessLogicException{
        EspecialidadEntity pojoEntity = factory.manufacturePojo(EspecialidadEntity.class);

        pojoEntity.setNombre("Otorrinolaringologo");

        especialidadLogic.updateEspecialidad(pojoEntity.getNombre(), pojoEntity);

        EspecialidadEntity resp = em.find(EspecialidadEntity.class, "Otorrinolaringologo");

        Assert.assertEquals(pojoEntity.getNombre(), resp.getNombre());
    }

    
    @Test(expected = BusinessLogicException.class)
    public void updateEspecialidadNombreNullTest() throws BusinessLogicException{
        EspecialidadEntity entity = data.get(0);
        EspecialidadEntity pojoEntity = factory.manufacturePojo(EspecialidadEntity.class);

        pojoEntity.setNombre(null);

        especialidadLogic.updateEspecialidad(pojoEntity.getNombre(), pojoEntity);

        EspecialidadEntity resp = em.find(EspecialidadEntity.class, entity.getNombre());

        Assert.assertEquals(pojoEntity.getNombre(), resp.getNombre());
    }
    
    
    @Test(expected = BusinessLogicException.class)
    public void updateEspecialidadNombreRepetidoTest() throws BusinessLogicException{
        EspecialidadEntity entity = data.get(0);
        EspecialidadEntity pojoEntity = factory.manufacturePojo(EspecialidadEntity.class);

        pojoEntity.setNombre("1");

        especialidadLogic.updateEspecialidad(pojoEntity.getNombre(), pojoEntity);

        EspecialidadEntity resp = em.find(EspecialidadEntity.class, entity.getNombre());

        Assert.assertEquals(pojoEntity.getNombre(), resp.getNombre());
    }
    /**
     * Prueba para eliminar un Especialidad.
     *
     * @throws co.edu.uniandes.csw.bookstore.exceptions.BusinessLogicException
     */
    @Test
    public void deleteEspecialidadTest() throws BusinessLogicException {
        EspecialidadEntity entity = data.get(2);
        especialidadLogic.deleteEspecialidad(entity.getNombre());
        Assert.assertNull(em.find(EspecialidadEntity.class, data.get(2).getNombre()));
    }
}
