/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.medicinaPrepagada.test.logic;

import co.edu.uniandes.csw.medicinaPrepagada.ejb.MedicamentoLogic;
import co.edu.uniandes.csw.medicinaPrepagada.entities.MedicamentoEntity;
import co.edu.uniandes.csw.medicinaPrepagada.entities.OrdenMedicaEntity;
import co.edu.uniandes.csw.medicinaPrepagada.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.medicinaPrepagada.persistence.MedicamentoPersistence;
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
 * Pruebas de logica de Medicamento
 *
 * @author ncobos
 */
@RunWith(Arquillian.class)
public class MedicamentoLogicTest {
    private PodamFactory factory = new PodamFactoryImpl();

    @Inject
    private MedicamentoLogic medicamentoLogic;

    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;

    private List<MedicamentoEntity> data = new ArrayList();

   // private List<BookEntity> booksData = new ArrayList();
    
    /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(MedicamentoEntity.class.getPackage())
                .addPackage(MedicamentoLogic.class.getPackage())
                .addPackage(MedicamentoPersistence.class.getPackage())
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
        em.createQuery("delete from MedicamentoEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        for (int i = 0; i < 3; i++) {
            MedicamentoEntity entity = factory.manufacturePojo(MedicamentoEntity.class);
            em.persist(entity);
            data.add(entity);
        }
        MedicamentoEntity medicamento = data.get(2);
        OrdenMedicaEntity entity = factory.manufacturePojo(OrdenMedicaEntity.class);
        entity.getMedicamentos().add(medicamento);
        em.persist(entity);
        medicamento.setOrdenMedica(entity);
    }
    
   /**
     * Prueba para crear un Medicamento.
     */
    @Test
    public void createMedicamentoTest() throws BusinessLogicException {
        MedicamentoEntity newEntity = factory.manufacturePojo(MedicamentoEntity.class);
         newEntity.setElaboradoPor("Glaxo Smith Kline");
        newEntity.setDescripcion("Tomar mucha agua");
        newEntity.setCosto(15000);
        newEntity.setCantidad("20 mg");
        MedicamentoEntity result = medicamentoLogic.createMedicamento(newEntity);
        Assert.assertNotNull(result);
        MedicamentoEntity entity = em.find(MedicamentoEntity.class, result.getId());
        Assert.assertEquals(newEntity.getId(), entity.getId());
        Assert.assertEquals(newEntity.getNombre(), entity.getNombre());
        Assert.assertEquals(newEntity.getElaboradoPor(), entity.getElaboradoPor());
        Assert.assertEquals(newEntity.getCantidad(), entity.getCantidad());
        Assert.assertEquals(newEntity.getDescripcion(), entity.getDescripcion());
        Assert.assertEquals(newEntity.getCosto(), entity.getCosto(), 0);
    }
    
    /**
     * Prueba para consultar la lista de Medicamentos.
     */
//    @Test
  //  public void getMedicamentosTest() {
    //    List<MedicamentoEntity> list = medicamentoLogic.getMedicamentos();
      //  Assert.assertEquals(data.size(), list.size());
        //for (MedicamentoEntity entity : list) {
//            boolean found = false;
  //          for (MedicamentoEntity storedEntity : data) {
    //            if (entity.getId().equals(storedEntity.getId())) {
      //              found = true;
        //        }
          //  }
            //Assert.assertTrue(found);
//        }
  //  }
    
    /**
     * Prueba para consultar un Medicamento.
     */
//    @Test
  //  public void getMedicamentoTest() {
    //    MedicamentoEntity entity = data.get(0);
      //  MedicamentoEntity resultEntity = medicamentoLogic.getMedicamento(entity.getId());
        //Assert.assertNotNull(resultEntity);
//        Assert.assertEquals(entity.getId(), resultEntity.getId());
  //  }
    
    /**
     * Prueba para actualizar un Medicamento.
     */
//    @Test
  //  public void updateMedicamentoTest() throws BusinessLogicException {
    //    MedicamentoEntity entity = data.get(1);
      //  MedicamentoEntity pojoEntity = factory.manufacturePojo(MedicamentoEntity.class);

//        pojoEntity.setElaboradoPor("Glaxo Smith Kline");
  //      pojoEntity.setDescripcion("Tomar mucha agua");
    //    pojoEntity.setCosto(15000);
      //  pojoEntity.setCantidad("20 mg");

        //medicamentoLogic.updateMedicamento(entity.getId(), pojoEntity);

//        MedicamentoEntity resp = em.find(MedicamentoEntity.class, entity.getId());

//        Assert.assertEquals(pojoEntity.getId(), resp.getId());
  //  }
    
    /**
     * prueba para eliminar un medicamento
     */
//    @Test
  //  public void deleteMedicamentoTest() throws BusinessLogicException{
    //    MedicamentoEntity entity = data.get(0);
      //  medicamentoLogic.deleteMedicamento(entity.getId());
        //MedicamentoEntity delet = em.find(MedicamentoEntity.class, entity.getId());
//        Assert.assertNull(delet);
  //  }
    
    /**
     * Prueba para crear un medicamento con un nombre reptido
     * @throws BusinessLogicException 
     */
//    @Test(expected = BusinessLogicException.class)
  //  public void crearMedicamentoConNombreRepetido() throws BusinessLogicException{
    //    MedicamentoEntity newEntity = factory.manufacturePojo(MedicamentoEntity.class);
      //  newEntity.setNombre(data.get(0).getNombre());
        //newEntity.setElaboradoPor("Glaxo Smith Kline");
//        newEntity.setDescripcion("Tomar mucha agua");
  //      newEntity.setCosto(15000);
    //    newEntity.setCantidad("20 mg");
        
      //  medicamentoLogic.createMedicamento(newEntity);
//    }

    
    /**
     * Prueba para crear un medicamento con un costo inválido
     * @throws BusinessLogicException 
     */
    @Test(expected = BusinessLogicException.class)
    public void crearMedicamentoConCostoInvalido() throws BusinessLogicException{
        MedicamentoEntity newEntity = factory.manufacturePojo(MedicamentoEntity.class);
        newEntity.setElaboradoPor("Glaxo Smith Kline");
        newEntity.setDescripcion("Tomar mucha agua");
        newEntity.setCosto(1);
        newEntity.setCantidad("20 mg");
        
        medicamentoLogic.createMedicamento(newEntity);
    }

}
