/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.medicinaPrepagada.test.logic;

import co.edu.uniandes.csw.medicinaPrepagada.ejb.ExamenMedicoLogic;
import co.edu.uniandes.csw.medicinaPrepagada.entities.ExamenMedicoEntity;
import co.edu.uniandes.csw.medicinaPrepagada.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.medicinaPrepagada.persistence.ExamenMedicoPersistence;
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
 * Pruebas de logica de Examen medico
 *
 * @author ncobos
 */
@RunWith(Arquillian.class)
public class ExamenMedicoLogicTest {
    private PodamFactory factory = new PodamFactoryImpl();

    @Inject
    private ExamenMedicoLogic examenMedicoLogic;

    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;

    private List<ExamenMedicoEntity> data = new ArrayList();

    
    /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(ExamenMedicoEntity.class.getPackage())
                .addPackage(ExamenMedicoLogic.class.getPackage())
                .addPackage(ExamenMedicoPersistence.class.getPackage())
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
    private void clearData() {
        em.createQuery("delete from ExamenMedicoEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        for (int i = 0; i < 3; i++) {
            ExamenMedicoEntity entity = factory.manufacturePojo(ExamenMedicoEntity.class);
            em.persist(entity);
            data.add(entity);
        }
//        ExamenMedicoEntity examenMedico = data.get(2);
//        OrdenMedicaEntity entity = factory.manufacturePojo(OrdenMedicaEntity.class);
//        ArrayList<ExamenMedicoEntity> lista = new ArrayList();
//        lista.add(examenMedico);
//        entity.setExamenMedicos(lista);
//        em.merge(entity);
//        examenMedico.setOrdenMedica(entity);
    }
    
   /**
     * Prueba para crear un ExamenMedico.
     */
    @Test
    public void createExamenMedicoTest() throws BusinessLogicException {
        ExamenMedicoEntity newEntity = factory.manufacturePojo(ExamenMedicoEntity.class);
        newEntity.setNombre("Biopsia");
        newEntity.setRecomendaciones("Tomar mucha agua");
        newEntity.setCosto(15000);
        ExamenMedicoEntity result = examenMedicoLogic.createExamenMedico(newEntity);
        Assert.assertNotNull(result);
        ExamenMedicoEntity entity = em.find(ExamenMedicoEntity.class, result.getId());
        Assert.assertEquals(newEntity.getId(), entity.getId());
        Assert.assertEquals(newEntity.getNombre(), entity.getNombre());
        Assert.assertEquals(newEntity.getRecomendaciones(), entity.getRecomendaciones());
        Assert.assertEquals(newEntity.getCosto(), entity.getCosto(), 0);
    }
    
    /**
     * Prueba para consultar la lista de ExamenMedicos.
     */
    @Test
    public void getExamenMedicosTest() {
        List<ExamenMedicoEntity> list = examenMedicoLogic.getExamenMedicos();
        Assert.assertEquals(data.size(), list.size());
        for (ExamenMedicoEntity entity : list) {
            boolean found = false;
            for (ExamenMedicoEntity storedEntity : data) {
                if (entity.getId().equals(storedEntity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }
    
    /**
     * Prueba para consultar un ExamenMedico.
     */
    @Test
    public void getExamenMedicoTest() {
        ExamenMedicoEntity entity = data.get(0);
        ExamenMedicoEntity resultEntity = examenMedicoLogic.getExamenMedico(entity.getId());
        Assert.assertNotNull(resultEntity);
        Assert.assertEquals(entity.getId(), resultEntity.getId());
    }
    
    /**
     * Prueba para actualizar un ExamenMedico.
     */
    @Test
    public void updateExamenMedicoTest() throws BusinessLogicException {
        ExamenMedicoEntity entity = data.get(1);
        ExamenMedicoEntity pojoEntity = factory.manufacturePojo(ExamenMedicoEntity.class);

        pojoEntity.setId(entity.getId());
        pojoEntity.setNombre(entity.getNombre());
        pojoEntity.setRecomendaciones("Tomar mucha agua");
        pojoEntity.setCosto(15000);

        examenMedicoLogic.updateExamenMedico(entity.getId(), pojoEntity);

        ExamenMedicoEntity resp = em.find(ExamenMedicoEntity.class, entity.getId());

        Assert.assertEquals(pojoEntity.getId(), resp.getId());
    }
    
    /**
     * prueba para eliminar un examenMedico
     */
    @Test
    public void deleteExamenMedicoTest() throws BusinessLogicException{
        ExamenMedicoEntity entity = data.get(0);
        examenMedicoLogic.deleteExamenMedico(entity.getId());
        ExamenMedicoEntity delet = em.find(ExamenMedicoEntity.class, entity.getId());
        Assert.assertNull(delet);
    }
    
    /**
     * Prueba para crear un examenMedico con un nombre reptido
     * @throws BusinessLogicException 
     */
    @Test(expected = BusinessLogicException.class)
    public void crearExamenMedicoConNombreRepetido() throws BusinessLogicException{
        ExamenMedicoEntity newEntity = factory.manufacturePojo(ExamenMedicoEntity.class);
        newEntity.setNombre(data.get(0).getNombre());
        newEntity.setRecomendaciones("Tomar mucha agua");
        newEntity.setCosto(15000);
        
        examenMedicoLogic.createExamenMedico(newEntity);
    }

    
    /**
     * Prueba para crear un examenMedico con un costo inválido
     * @throws BusinessLogicException 
     */
    @Test(expected = BusinessLogicException.class)
    public void crearExamenMedicoConCostoInvalido() throws BusinessLogicException{
        ExamenMedicoEntity newEntity = factory.manufacturePojo(ExamenMedicoEntity.class);
        newEntity.setRecomendaciones("Tomar mucha agua");
        newEntity.setCosto(1);
        
        examenMedicoLogic.createExamenMedico(newEntity);
    }
    
    /**
     * Prueba para crear un examenMedico con una descripción invalida
     * @throws BusinessLogicException 
     */
    @Test(expected = BusinessLogicException.class)
    public void crearExamenMedicoConDescripcionInvalida() throws BusinessLogicException{
        ExamenMedicoEntity newEntity = factory.manufacturePojo(ExamenMedicoEntity.class);
        newEntity.setRecomendaciones("123456");
        newEntity.setCosto(100000);
        
        examenMedicoLogic.createExamenMedico(newEntity);
    }
    
     /**
     * Prueba para actualizar un examen medico con un costo inválido
     * @throws BusinessLogicException 
     */
    @Test(expected = BusinessLogicException.class)
    public void updateExamenMedicoConCostoInvalido() throws BusinessLogicException{
        ExamenMedicoEntity entity = data.get(1);
        ExamenMedicoEntity pojoEntity = factory.manufacturePojo(ExamenMedicoEntity.class);
        pojoEntity.setId(entity.getId());
        pojoEntity.setNombre(entity.getNombre());
        pojoEntity.setCosto(1);
        pojoEntity.setRecomendaciones("Tomar mucha agua");
        
        examenMedicoLogic.updateExamenMedico(entity.getId(), pojoEntity);
    }
    
     /**
     * Prueba para actualizar un examen medico con una descripcion invalida
     * @throws BusinessLogicException 
     */
    @Test(expected = BusinessLogicException.class)
    public void updateExamenMedicoConDescripcionInvalida() throws BusinessLogicException{
        ExamenMedicoEntity entity = data.get(1);
        ExamenMedicoEntity pojoEntity = factory.manufacturePojo(ExamenMedicoEntity.class);
        pojoEntity.setId(entity.getId());
        pojoEntity.setNombre(entity.getNombre());
        pojoEntity.setCosto(100000);
        pojoEntity.setRecomendaciones("12345");
        
        examenMedicoLogic.updateExamenMedico(entity.getId(), pojoEntity);
    }
}
