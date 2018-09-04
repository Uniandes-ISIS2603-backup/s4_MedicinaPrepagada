/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.medicinaPrepagada.persistence;

import co.edu.uniandes.csw.medicinaPrepagada.entities.MedicamentoEntity;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author ncobos
 */
@Stateless
public class MedicamentoPersistence {
    
    private static final Logger LOGGER = Logger.getLogger(MedicamentoPersistence.class.getName());
    
   @PersistenceContext(unitName = "MedisistemasPU")
    protected EntityManager em;
   
   /**
     * Método para persisitir la entidad en la base de datos.
     *
     * @param medicamentoEntity objeto Medicamento que se creará en la base de datos
     * @return devuelve la entidad creada con un id dado por la base de datos.
     */
    public MedicamentoEntity create(MedicamentoEntity medicamentoEntity) {
        LOGGER.log(Level.INFO, "Creando un medicamento nuevo");
        /* Note que hacemos uso de un método propio de EntityManager para persistir la medicamento en la base de datos.
        Es similar a "INSERT INTO table_nombre (column1, column2, column3, ...) VALUES (value1, value2, value3, ...);" en SQL.
         */
        em.persist(medicamentoEntity);
        LOGGER.log(Level.INFO, "Saliendo de crear un Medicamento nuevo");
        return medicamentoEntity;
    }
	
	/**
     * Devuelve todas los medicamentos de la base de datos.
     *
     * @return una lista con todas los medicamentos que encuentre en la base de
     * datos, "select u from MedicamentoEntity u" es como un "select * from
     * MedicamentoEntity;" - "SELECT * FROM table_nombre" en SQL.
     */
    public List<MedicamentoEntity> findAll() {
        LOGGER.log(Level.INFO, "Consultando todas los medicamentos");
        // Se crea un query para buscar todas los medicamentos en la base de datos.
        TypedQuery query = em.createQuery("select u from MedicamentoEntity u", MedicamentoEntity.class);
        // Note que en el query se hace uso del método getResultList() que obtiene una lista de Medicamentos.
        return query.getResultList();
    }
	
    /**
     * Busca si hay algun Medicamento con el id que se envía de argumento
     *
     * @param medicamentosId: id correspondiente al Medicamento buscad.
     * @return un Medicamento.
     */
    public MedicamentoEntity find(Long medicamentosId) {
        LOGGER.log(Level.INFO, "Consultando Medicamento con id={0}", medicamentosId);
        /* Note que se hace uso del metodo "find" propio del EntityManager, el cual recibe como argumento 
        el tipo de la clase y el objeto que nos hara el filtro en la base de datos en este caso el "id"
        Suponga que es algo similar a "select * from MedicamentoEntity where id=id;" - "SELECT * FROM table_nombre WHERE condition;" en SQL.
         */
        return em.find(MedicamentoEntity.class, medicamentosId);
    }

	 /**
     * Actualiza una medicamento.
     *
     * @param medicamentoEntity:medicamento que viene con los nuevos cambios.
     * Por ejemplo el nombre pudo cambiar. En ese caso, se haria uso del método
     * update.
     * @return un medicamento con los cambios aplicados.
     */
    public MedicamentoEntity update(MedicamentoEntity medicamentoEntity) {
        LOGGER.log(Level.INFO, "Actualizando medicamento con id = {0}", medicamentoEntity.getId());
        /* Note que hacemos uso de un método propio del EntityManager llamado merge() que recibe como argumento
        la medicamento con los cambios, esto es similar a 
        "UPDATE table_nombre SET column1 = value1, column2 = value2, ... WHERE condition;" en SQL.
         */
        LOGGER.log(Level.INFO, "Saliendo de actualizar el medicamento con id = {0}", medicamentoEntity.getId());
        return em.merge(medicamentoEntity);
    }
	
    /**
     *
     * Borra una medicamento de la base de datos recibiendo como argumento el id
     * de la medicamento
     *
     * @param medicamentosId: id correspondiente a la medicamento a borrar.
     */
    public void delete(Long medicamentosId) {
        LOGGER.log(Level.INFO, "Borrando medicamento con id = {0}", medicamentosId);
        // Se hace uso de mismo método que esta explicado en public MedicamentoEntity find(Long id) para obtener el medicamento a borrar.
        MedicamentoEntity entity = em.find(MedicamentoEntity.class, medicamentosId);
        /* Note que una vez obtenido el objeto desde la base de datos llamado "entity", volvemos hacer uso de un método propio del
         EntityManager para eliminar de la base de datos el objeto que encontramos y queremos borrar.
         Es similar a "delete from MedicamentoEntity where id=id;" - "DELETE FROM table_nombre WHERE condition;" en SQL.*/
        em.remove(entity);
        LOGGER.log(Level.INFO, "Saliendo de borrar la medicamento con id = {0}", medicamentosId);
    }
	
    /**
     * Busca si hay algun medicamento con el nombre que se envía de argumento
     *
     * @param nombre: Nombre de la medicamento que se está buscando
     * @return null si no existe ninguna medicamento con el nombre del argumento.
     * Si existe alguna devuelve la primera.
     */
    public MedicamentoEntity findByNombre(String nombre) {
        LOGGER.log(Level.INFO, "Consultando medicamento por nombre ", nombre);
        // Se crea un query para buscar medicamentos con el nombre que recibe el método como argumento. ":nombre" es un placeholder que debe ser remplazado
        TypedQuery query = em.createQuery("Select e From MedicamentoEntity e where e.nombre = :nombre", MedicamentoEntity.class);
        // Se remplaza el placeholder ":nombre" con el valor del argumento 
        query = query.setParameter("nombre", nombre);
        // Se invoca el query se obtiene la lista resultado
        List<MedicamentoEntity> sameNombre = query.getResultList();
        MedicamentoEntity result;
        if (sameNombre == null) {
            result = null;
        } else if (sameNombre.isEmpty()) {
            result = null;
        } else {
            result = sameNombre.get(0);
        }
        LOGGER.log(Level.INFO, "Saliendo de consultar medicamento por nombre ", nombre);
        return result;
    }
    
}
