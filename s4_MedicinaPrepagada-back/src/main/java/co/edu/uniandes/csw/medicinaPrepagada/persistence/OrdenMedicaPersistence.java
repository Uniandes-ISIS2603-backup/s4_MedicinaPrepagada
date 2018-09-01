/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.medicinaPrepagada.persistence;

import co.edu.uniandes.csw.medicinaPrepagada.entities.OrdenMedicaEntity;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author ni.ramirez10
 */

@Stateless
public class OrdenMedicaPersistence 
{
    private static final Logger LOGGER = Logger.getLogger(OrdenMedicaPersistence.class.getName());
    
    @PersistenceContext(unitName = "MedisistemasPU")
    protected EntityManager em;
    
    /**
     * Método para persisitir la entidad en la base de datos.
     * @param ordenMedicaEntity objeto orden medica que se creará en la base de datos
     * @return devuelve la entidad creada con un id dado por la base de datos.
     */
    
    public OrdenMedicaEntity create(OrdenMedicaEntity ordenMedicaEntity) 
    {
        LOGGER.log(Level.INFO, "Creando una nueva orden medica");
        em.persist(ordenMedicaEntity);
        LOGGER.log(Level.INFO, "Orden medica creada");
        return ordenMedicaEntity;
    }
    
    /**
     * Devuelve todas las ordenes medicas de la base de datos.
     * @return una lista con todas las ordenes medicas que encuentre en la base de datos.
     */
    
    public List<OrdenMedicaEntity> findAll() 
    {
        LOGGER.log(Level.INFO, "Consultando todas las ordenes medicas");
        Query q = em.createQuery("select u from OrdenMedicaEntity u");
        return q.getResultList();
    }
    
    /**
     * Busca la orden medica con el id que se envía de parametro
     * @param ordenId: id correspondiente a la orden medica buscada.
     * @return una orden medica. 
     */
    
    public OrdenMedicaEntity find(Long ordenId) 
    {
        LOGGER.log(Level.INFO, "Consultando la orden medica con id={0}", ordenId);
        return em.find(OrdenMedicaEntity.class, ordenId);
    }
    
    /**
     * Actualiza una orden medica.
     * @param ordenMedicaEntity: la orden medica que viene con nueva informacion. 
     * @return una orden medica con los cambios aplicados.
     */
    
    public OrdenMedicaEntity update(OrdenMedicaEntity ordenMedicaEntity) 
    {
        LOGGER.log(Level.INFO, "Actualizando la orden medica con id={0}", ordenMedicaEntity.getId());
        return em.merge(ordenMedicaEntity);
    }
    
    /**
     * Borra una orden medicca de la base de datos con el id que entra por parametro
     * @param ordenId: id correspondiente a la orden medica a borrar.
     */
    
    public void delete(Long ordenId) 
    {
        LOGGER.log(Level.INFO, "Borrando la orden medica con id={0}", ordenId);
        OrdenMedicaEntity ordenMedicaEntity = em.find(OrdenMedicaEntity.class, ordenId);
        em.remove(ordenMedicaEntity);
    }
    
}
