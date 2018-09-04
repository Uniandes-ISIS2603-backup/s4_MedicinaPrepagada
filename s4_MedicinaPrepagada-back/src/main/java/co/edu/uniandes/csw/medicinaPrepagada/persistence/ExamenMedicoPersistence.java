/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.medicinaPrepagada.persistence;

import co.edu.uniandes.csw.medicinaPrepagada.entities.ExamenMedicoEntity;
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
public class ExamenMedicoPersistence {
    private static final Logger LOGGER = Logger.getLogger(ExamenMedicoPersistence.class.getName());

    @PersistenceContext(unitName = "MedisistemasPU")
    protected EntityManager em;
    

    /**
     * Método para persisitir la entidad en la base de datos.
     *
     * @param examenMedicoEntity objeto examenMedico que se creará en la base de datos
     * @return devuelve la entidad creada con un id dado por la base de datos.
     */
    public ExamenMedicoEntity create(ExamenMedicoEntity examenMedicoEntity) {
        LOGGER.log(Level.INFO, "Creando una examenMedico nueva");
        /* Note que hacemos uso de un método propio de EntityManager para persistir la examenMedico en la base de datos.
        Es similar a "INSERT INTO table_nombre (column1, column2, column3, ...) VALUES (value1, value2, value3, ...);" en SQL.
         */
        em.persist(examenMedicoEntity);
        LOGGER.log(Level.INFO, "Saliendo de crear una examenMedico nueva");
        return examenMedicoEntity;
    }
	
	/**
     * Devuelve todas las examenMedicoes de la base de datos.
     *
     * @return una lista con todas las examenesMedicos que encuentre en la base de
     * datos, "select u from ExamenMedicoEntity u" es como un "select * from
     * ExamenMedicoEntity;" - "SELECT * FROM table_nombre" en SQL.
     */
    public List<ExamenMedicoEntity> findAll() {
        LOGGER.log(Level.INFO, "Consultando todas los examenes medicos");
        // Se crea un query para buscar todas los examenes medicos en la base de datos.
        TypedQuery query = em.createQuery("select u from ExamenMedicoEntity u", ExamenMedicoEntity.class);
        // Note que en el query se hace uso del método getResultList() que obtiene una lista de examenes medicos.
        return query.getResultList();
    }
	
    /**
     * Busca si hay alguna examenMedico con el id que se envía de argumento
     *
     * @param examenMedicosId: id correspondiente a la examenMedico buscada.
     * @return una examenMedico.
     */
    public ExamenMedicoEntity find(Long examenMedicosId) {
        LOGGER.log(Level.INFO, "Consultando examenMedico con id={0}", examenMedicosId);
        /* Note que se hace uso del metodo "find" propio del EntityManager, el cual recibe como argumento 
        el tipo de la clase y el objeto que nos hara el filtro en la base de datos en este caso el "id"
        Suponga que es algo similar a "select * from ExamenMedicoEntity where id=id;" - "SELECT * FROM table_nombre WHERE condition;" en SQL.
         */
        return em.find(ExamenMedicoEntity.class, examenMedicosId);
    }

	 /**
     * Actualiza una examenMedico.
     *
     * @param examenMedicoEntity: la examenMedico que viene con los nuevos cambios.
     * Por ejemplo el nombre pudo cambiar. En ese caso, se haria uso del método
     * update.
     * @return una examenMedico con los cambios aplicados.
     */
    public ExamenMedicoEntity update(ExamenMedicoEntity examenMedicoEntity) {
        LOGGER.log(Level.INFO, "Actualizando examenMedico con id = {0}", examenMedicoEntity.getId());
        /* Note que hacemos uso de un método propio del EntityManager llamado merge() que recibe como argumento
        la examenMedico con los cambios, esto es similar a 
        "UPDATE table_nombre SET column1 = value1, column2 = value2, ... WHERE condition;" en SQL.
         */
        LOGGER.log(Level.INFO, "Saliendo de actualizar la examenMedico con id = {0}", examenMedicoEntity.getId());
        return em.merge(examenMedicoEntity);
    }
	
    /**
     *
     * Borra una examenMedico de la base de datos recibiendo como argumento el id
     * de la examenMedico
     *
     * @param examenMedicosId: id correspondiente a la examenMedico a borrar.
     */
    public void delete(Long examenMedicosId) {
        LOGGER.log(Level.INFO, "Borrando examenMedico con id = {0}", examenMedicosId);
        // Se hace uso de mismo método que esta explicado en public ExamenMedicoEntity find(Long id) para obtener la examenMedico a borrar.
        ExamenMedicoEntity entity = em.find(ExamenMedicoEntity.class, examenMedicosId);
        /* Note que una vez obtenido el objeto desde la base de datos llamado "entity", volvemos hacer uso de un método propio del
         EntityManager para eliminar de la base de datos el objeto que encontramos y queremos borrar.
         Es similar a "delete from ExamenMedicoEntity where id=id;" - "DELETE FROM table_nombre WHERE condition;" en SQL.*/
        em.remove(entity);
        LOGGER.log(Level.INFO, "Saliendo de borrar el examen medico con id = {0}", examenMedicosId);
    }
	
    /**
     * Busca si hay algun examenMedico con el nombre que se envía de argumento
     *
     * @param nombre: Nombre del examenMedico que se está buscando
     * @return null si no existe ninguna examenMedico con el nombre del argumento.
     * Si existe alguna devuelve la primera.
     */
    public ExamenMedicoEntity findByNombre(String nombre) {
        LOGGER.log(Level.INFO, "Consultando examenMedico por nombre ", nombre);
        // Se crea un query para buscar examenMedicoes con el nombre que recibe el método como argumento. ":nombre" es un placeholder que debe ser remplazado
        TypedQuery query = em.createQuery("Select e From ExamenMedicoEntity e where e.nombre = :nombre", ExamenMedicoEntity.class);
        // Se remplaza el placeholder ":nombre" con el valor del argumento 
        query = query.setParameter("nombre", nombre);
        // Se invoca el query se obtiene la lista resultado
        List<ExamenMedicoEntity> sameNombre = query.getResultList();
        ExamenMedicoEntity result;
        if (sameNombre == null) {
            result = null;
        } else if (sameNombre.isEmpty()) {
            result = null;
        } else {
            result = sameNombre.get(0);
        }
        LOGGER.log(Level.INFO, "Saliendo de consultar examenMedico por nombre ", nombre);
        return result;
    }
}
