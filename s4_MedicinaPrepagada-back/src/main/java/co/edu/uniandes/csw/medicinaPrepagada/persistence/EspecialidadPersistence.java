/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.medicinaPrepagada.persistence;

import co.edu.uniandes.csw.medicinaPrepagada.entities.EspecialidadEntity;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @medico Daniel Ivan Romero
 */
@Stateless
public class EspecialidadPersistence {
    private static final Logger LOGGER = Logger.getLogger(EspecialidadPersistence.class.getName());
    
    @PersistenceContext(unitName = "MedisistemasPU")
    protected EntityManager em;
    
     /**
     * Método para persisitir la entidad en la base de datos.
     * @param EspecialidadEntity objeto Especialidad que se creará en la base de datos
     * @return devuelve la entidad creada con un id dado por la base de datos.
     */
    
    public EspecialidadEntity create(EspecialidadEntity especialidadEntity) 
    {
        LOGGER.log(Level.INFO, "Creando una nueva Especialidad");
        em.persist(especialidadEntity);
        LOGGER.log(Level.INFO, "Especialidad creada");
        return especialidadEntity;
    }
    
    /**
     * Devuelve todas las Especialidades de la base de datos.
     * @return una lista con todos los Especialidades que encuentre en la base de datos,
     */
    
    public List<EspecialidadEntity> findAll() 
    {
        LOGGER.log(Level.INFO, "Consultando todas las especialides");
        Query q = em.createQuery("select u from EspecialidadEntity u");
        return q.getResultList();
    }
    
    /**
     * Busca la Especialidad con el id que se envía de parametro
     * @param especialidadId: id correspondiente al Especialidad buscado.
     * @return un Especialidad. 
     */
    
    public EspecialidadEntity find(String especialidadId) 
    {
        LOGGER.log(Level.INFO, "Consultando la Especialidad con id={0}", especialidadId);
        return em.find(EspecialidadEntity.class, especialidadId);
    }
    
    /**
     * Actualiza una Especialidad.
     * @param especialidadEntity: el Especialidad que viene con nueva informacion. 
     * @return una Especialidad con los cambios aplicados.
     */
    
    public EspecialidadEntity update(EspecialidadEntity especialidadEntity) 
    {
        LOGGER.log(Level.INFO, "Actualizando la Especialidad con id={0}", especialidadEntity.getNombre());
        return em.merge(especialidadEntity);
    }
    
    /**
     * Borra una Especialidad de la base de datos con el id que entra por parametro
     * @param especialidadId: id correspondiente al especialidadnsitrador a borrar.
     */
    
    public void delete(String especialidadId) 
    {
        LOGGER.log(Level.INFO, "Borrando la Especialidad con id={0}", especialidadId);
        EspecialidadEntity especialidadEntity = em.find(EspecialidadEntity.class, especialidadId);
        em.remove(especialidadEntity);
    }
}
