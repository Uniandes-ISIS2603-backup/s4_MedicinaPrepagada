/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.medicinaPrepagada.persistence;

import co.edu.uniandes.csw.medicinaPrepagada.entities.SedeEntity;
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
public class SedePersistence 
{
     private static final Logger LOGGER = Logger.getLogger(SedePersistence.class.getName());
    
   @PersistenceContext(unitName = "MedisistemasPU")
    protected EntityManager em;
     
   
    /**
     * Crea un sede en la base de datos
     *
     * @param sedeEntity objeto sede que se creará en la base de datos
     * @return devuelve la entidad creada con un id dado por la base de datos.
     */
    public SedeEntity create(SedeEntity sedeEntity) 
    {
        LOGGER.log(Level.INFO, "Creando un sede nuevo");
        em.persist(sedeEntity);
        LOGGER.log(Level.INFO, "Sede creado");
        return sedeEntity;
    }
    
    
        /**
     * Devuelve todas las sedes de la base de datos.
     *
     * @return una lista con todas las sedes que encuentre en la base de
     */
    public List<SedeEntity> findAll() 
    {
        LOGGER.log(Level.INFO, "Consultando todos los sedes");
        // Se crea un query para buscar todas las sedes en la base de datos.
        TypedQuery query = em.createQuery("select u from SedeEntity u", SedeEntity.class);
        return query.getResultList();
    }
    
    /**
     * Busca si hay alguna sede con el id que se envía de argumento
     *
     * @param sedeId: id correspondiente a la sede buscada.
     * @return un sede.
     */
    public SedeEntity find(Long sedeId)
    {
        LOGGER.log(Level.INFO, "Consultando el sede con id={0}", sedeId);
        return em.find(SedeEntity.class, sedeId);
    }
    
        public SedeEntity findByNombre(String pNombre)
    {
        LOGGER.log(Level.INFO, "Consultando el sede con nombre={0}", pNombre);
        return em.find(SedeEntity.class, pNombre);
    }
        
    public SedeEntity findByDireccion(String pDireccion)
    {
        LOGGER.log(Level.INFO, "Consultando el sede con nombre={0}", pDireccion);
        return em.find(SedeEntity.class, pDireccion);
    }
     
    public SedeEntity findByLongitud(Double pLongitud)
    {
        LOGGER.log(Level.INFO, "Consultando el sede con nombre={0}", pLongitud);
        return em.find(SedeEntity.class, pLongitud);
    }
        /**
     * Actualiza una sede.
     *
     * @param sedeEntity: la sede que viene con los nuevos cambios. Por
     * @return una sede con los cambios aplicados.
     */
    public SedeEntity update(SedeEntity sedeEntity) 
    {
        LOGGER.log(Level.INFO, "Actualizando el sede con id={0}", sedeEntity.getId());
        return em.merge(sedeEntity);
    }

    /**
     * Borra una sede de la base de datos recibiendo como argumento el id de
     * la sede
     *
     * @param sedeId: id correspondiente a la sede a borrar.
     */
    public void delete(Long sedeId) 
    {

        LOGGER.log(Level.INFO, "Borrando el sede con id={0}", sedeId);
        SedeEntity sedeEntity = em.find(SedeEntity.class, sedeId);
        em.remove(sedeEntity);
    }
    
    
}
