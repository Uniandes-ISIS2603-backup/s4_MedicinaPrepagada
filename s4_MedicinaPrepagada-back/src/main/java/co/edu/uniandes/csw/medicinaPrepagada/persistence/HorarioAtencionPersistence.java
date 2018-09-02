/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.medicinaPrepagada.persistence;

import co.edu.uniandes.csw.medicinaPrepagada.entities.HorarioAtencionEntity;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author Simon Guzman
 */
@Stateless

public class HorarioAtencionPersistence 
{
    
   private static final Logger LOGGER = Logger.getLogger(HorarioAtencionPersistence.class.getName());
    
   @PersistenceContext(unitName = "MedisistemasPU")
    protected EntityManager em;
    
    
   
    /**
     * Crea un horarioAtencion en la base de datos
     *
     * @param horarioAtencionEntity objeto horarioAtencion que se creará en la base de datos
     * @return devuelve la entidad creada con un id dado por la base de datos.
     */
    public HorarioAtencionEntity create(HorarioAtencionEntity horarioAtencionEntity) 
    {
        LOGGER.log(Level.INFO, "Creando un horarioAtencion nuevo");
        em.persist(horarioAtencionEntity);
        LOGGER.log(Level.INFO, "HorarioAtencion creado");
        return horarioAtencionEntity;
    }
    
    
        /**
     * Devuelve todas las horarioAtencions de la base de datos.
     *
     * @return una lista con todas las horarioAtencions que encuentre en la base de
     */
    public List<HorarioAtencionEntity> findAll() 
    {
        LOGGER.log(Level.INFO, "Consultando todos los horarioAtencions");
        // Se crea un query para buscar todas las horarioAtencions en la base de datos.
        TypedQuery query = em.createQuery("select u from HorarioAtencionEntity u", HorarioAtencionEntity.class);
        return query.getResultList();
    }
    
    /**
     * Busca si hay alguna horarioAtencion con el id que se envía de argumento
     *
     * @param horarioAtencionId: id correspondiente a la horarioAtencion buscada.
     * @return un horarioAtencion.
     */
    public HorarioAtencionEntity find(Long horarioAtencionId)
    {
        LOGGER.log(Level.INFO, "Consultando el horarioAtencion con id={0}", horarioAtencionId);
        return em.find(HorarioAtencionEntity.class, horarioAtencionId);
    }
     
        /**
     * Actualiza una horarioAtencion.
     *
     * @param horarioAtencionEntity: la horarioAtencion que viene con los nuevos cambios. Por
     * @return una horarioAtencion con los cambios aplicados.
     */
    public HorarioAtencionEntity update(HorarioAtencionEntity horarioAtencionEntity) 
    {
        LOGGER.log(Level.INFO, "Actualizando el horarioAtencion con id={0}", horarioAtencionEntity.getId());
        return em.merge(horarioAtencionEntity);
    }

    /**
     * Borra una horarioAtencion de la base de datos recibiendo como argumento el id de
     * la horarioAtencion
     *
     * @param horarioAtencionId: id correspondiente a la horarioAtencion a borrar.
     */
    public void delete(Long horarioAtencionId) 
    {

        LOGGER.log(Level.INFO, "Borrando el horarioAtencion con id={0}", horarioAtencionId);
        HorarioAtencionEntity horarioAtencionEntity = em.find(HorarioAtencionEntity.class, horarioAtencionId);
        em.remove(horarioAtencionEntity);
    }
    
    
   
   
}
