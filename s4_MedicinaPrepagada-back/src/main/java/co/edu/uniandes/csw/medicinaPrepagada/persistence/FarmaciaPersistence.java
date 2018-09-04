/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.medicinaPrepagada.persistence;

import co.edu.uniandes.csw.medicinaPrepagada.entities.FarmaciaEntity;
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
public class FarmaciaPersistence {
    
    private static final Logger LOGGER = Logger.getLogger(FarmaciaPersistence.class.getName());

    @PersistenceContext(unitName = "MedisistemasPU")
    protected EntityManager em;

    /**
     * Método para persisitir la entidad en la base de datos.
     *
     * @param farmaciaEntity objeto farmacia que se creará en la base de datos
     * @return devuelve la entidad creada con un id dado por la base de datos.
     */
    public FarmaciaEntity create(FarmaciaEntity farmaciaEntity) {
        LOGGER.log(Level.INFO, "Creando una farmacia nueva");
        /* Note que hacemos uso de un método propio de EntityManager para persistir la farmacia en la base de datos.
        Es similar a "INSERT INTO table_nombre (column1, column2, column3, ...) VALUES (value1, value2, value3, ...);" en SQL.
         */
        em.persist(farmaciaEntity);
        LOGGER.log(Level.INFO, "Saliendo de crear una farmacia nueva");
        return farmaciaEntity;
    }
	
	/**
     * Devuelve todas las farmacias de la base de datos.
     *
     * @return una lista con todas las farmacias que encuentre en la base de
     * datos, "select u from FarmaciaEntity u" es como un "select * from
     * FarmaciaEntity;" - "SELECT * FROM table_nombre" en SQL.
     */
    public List<FarmaciaEntity> findAll() {
        LOGGER.log(Level.INFO, "Consultando todas las farmacias");
        // Se crea un query para buscar todas las farmacias en la base de datos.
        TypedQuery query = em.createQuery("select u from FarmaciaEntity u", FarmaciaEntity.class);
        // Note que en el query se hace uso del método getResultList() que obtiene una lista de farmacias.
        return query.getResultList();
    }
	
    /**
     * Busca si hay alguna farmacia con el id que se envía de argumento
     *
     * @param farmaciasId: id correspondiente a la farmacia buscada.
     * @return una farmacia.
     */
    public FarmaciaEntity find(Long farmaciasId) {
        LOGGER.log(Level.INFO, "Consultando farmacia con id={0}", farmaciasId);
        /* Note que se hace uso del metodo "find" propio del EntityManager, el cual recibe como argumento 
        el tipo de la clase y el objeto que nos hara el filtro en la base de datos en este caso el "id"
        Suponga que es algo similar a "select * from FarmaciaEntity where id=id;" - "SELECT * FROM table_nombre WHERE condition;" en SQL.
         */
        return em.find(FarmaciaEntity.class, farmaciasId);
    }

	 /**
     * Actualiza una farmacia.
     *
     * @param farmaciaEntity: la farmacia que viene con los nuevos cambios.
     * Por ejemplo el nombre pudo cambiar. En ese caso, se haria uso del método
     * update.
     * @return una farmacia con los cambios aplicados.
     */
    public FarmaciaEntity update(FarmaciaEntity farmaciaEntity) {
        LOGGER.log(Level.INFO, "Actualizando farmacia con id = {0}", farmaciaEntity.getId());
        /* Note que hacemos uso de un método propio del EntityManager llamado merge() que recibe como argumento
        la farmacia con los cambios, esto es similar a 
        "UPDATE table_nombre SET column1 = value1, column2 = value2, ... WHERE condition;" en SQL.
         */
        LOGGER.log(Level.INFO, "Saliendo de actualizar la farmacia con id = {0}", farmaciaEntity.getId());
        return em.merge(farmaciaEntity);
    }
	
    /**
     *
     * Borra una farmacia de la base de datos recibiendo como argumento el id
     * de la farmacia
     *
     * @param farmaciasId: id correspondiente a la farmacia a borrar.
     */
    public void delete(Long farmaciasId) {
        LOGGER.log(Level.INFO, "Borrando farmacia con id = {0}", farmaciasId);
        // Se hace uso de mismo método que esta explicado en public FarmaciaEntity find(Long id) para obtener la farmacia a borrar.
        FarmaciaEntity entity = em.find(FarmaciaEntity.class, farmaciasId);
        /* Note que una vez obtenido el objeto desde la base de datos llamado "entity", volvemos hacer uso de un método propio del
         EntityManager para eliminar de la base de datos el objeto que encontramos y queremos borrar.
         Es similar a "delete from FarmaciaEntity where id=id;" - "DELETE FROM table_nombre WHERE condition;" en SQL.*/
        em.remove(entity);
        LOGGER.log(Level.INFO, "Saliendo de borrar la farmacia con id = {0}", farmaciasId);
    }
	
    /**
     * Busca si hay alguna farmacia con el nombre que se envía de argumento
     *
     * @param nombre: Nombre de la farmacia que se está buscando
     * @return null si no existe ninguna farmacia con el nombre del argumento.
     * Si existe alguna devuelve la primera.
     */
    public FarmaciaEntity findByNombre(String nombre) {
        LOGGER.log(Level.INFO, "Consultando farmacia por nombre ", nombre);
        // Se crea un query para buscar farmacias con el nombre que recibe el método como argumento. ":nombre" es un placeholder que debe ser remplazado
        TypedQuery query = em.createQuery("Select e From FarmaciaEntity e where e.nombre = :nombre", FarmaciaEntity.class);
        // Se remplaza el placeholder ":nombre" con el valor del argumento 
        query = query.setParameter("nombre", nombre);
        // Se invoca el query se obtiene la lista resultado
        List<FarmaciaEntity> sameNombre = query.getResultList();
        FarmaciaEntity result;
        if (sameNombre == null) {
            result = null;
        } else if (sameNombre.isEmpty()) {
            result = null;
        } else {
            result = sameNombre.get(0);
        }
        LOGGER.log(Level.INFO, "Saliendo de consultar farmacia por nombre ", nombre);
        return result;
    }
}
