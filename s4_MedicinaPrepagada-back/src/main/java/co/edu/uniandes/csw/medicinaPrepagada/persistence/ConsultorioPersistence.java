/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.medicinaPrepagada.persistence;

import co.edu.uniandes.csw.medicinaPrepagada.entities.ConsultorioEntity;
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
public class ConsultorioPersistence
{
    
    
   private static final Logger LOGGER = Logger.getLogger(ConsultorioPersistence.class.getName());
    
   @PersistenceContext(unitName = "MedisistemasPU")
    protected EntityManager em;
   
   
    /**
     * Crea un consultorio en la base de datos
     *
     * @param consultorioEntity objeto consultorio que se creará en la base de datos
     * @return devuelve la entidad creada con un id dado por la base de datos.
     */
    public ConsultorioEntity create(ConsultorioEntity consultorioEntity) 
    {
        LOGGER.log(Level.INFO, "Creando un consultorio nuevo");
        em.persist(consultorioEntity);
        LOGGER.log(Level.INFO, "Consultorio creado");
        return consultorioEntity;
    }
    
    
        /**
     * Devuelve todas las consultorios de la base de datos.
     *
     * @return una lista con todas las consultorios que encuentre en la base de
     */
    public List<ConsultorioEntity> findAll() 
    {
        LOGGER.log(Level.INFO, "Consultando todos los consultorios");
        // Se crea un query para buscar todas las consultorios en la base de datos.
        TypedQuery query = em.createQuery("select u from ConsultorioEntity u", ConsultorioEntity.class);
        return query.getResultList();
    }
    
    /**
     * Busca si hay alguna consultorio con el id que se envía de argumento
     *
     * @param consultorioId: id correspondiente a la consultorio buscada.
     * @return un consultorio.
     */
    public ConsultorioEntity find(Long consultorioId)
    {
        LOGGER.log(Level.INFO, "Consultando el consultorio con id={0}", consultorioId);
        return em.find(ConsultorioEntity.class, consultorioId);
    }
     
        /**
     * Actualiza una consultorio.
     *
     * @param consultorioEntity: la consultorio que viene con los nuevos cambios. Por
     * @return una consultorio con los cambios aplicados.
     */
    public ConsultorioEntity update(ConsultorioEntity consultorioEntity) 
    {
        LOGGER.log(Level.INFO, "Actualizando el consultorio con id={0}", consultorioEntity.getId());
        return em.merge(consultorioEntity);
    }

    /**
     * Borra una consultorio de la base de datos recibiendo como argumento el id de
     * la consultorio
     *
     * @param consultorioId: id correspondiente a la consultorio a borrar.
     */
    public void delete(Long consultorioId) 
    {

        LOGGER.log(Level.INFO, "Borrando el consultorio con id={0}", consultorioId);
        ConsultorioEntity consultorioEntity = em.find(ConsultorioEntity.class, consultorioId);
        em.remove(consultorioEntity);
    }
}
