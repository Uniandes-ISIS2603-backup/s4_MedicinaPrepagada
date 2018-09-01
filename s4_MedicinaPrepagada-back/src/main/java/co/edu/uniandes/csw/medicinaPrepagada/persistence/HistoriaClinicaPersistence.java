/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.medicinaPrepagada.persistence;

import co.edu.uniandes.csw.medicinaPrepagada.entities.HistoriaClinicaEntity;
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
public class HistoriaClinicaPersistence 
{
    private static final Logger LOGGER = Logger.getLogger(HistoriaClinicaPersistence.class.getName());
    
    @PersistenceContext(unitName = "MedisistemasPU")
    protected EntityManager em;
    
    /**
     * Método para persisitir la entidad en la base de datos.
     * @param historiaClinicaEntity objeto historia clinica que se creará en la base de datos
     * @return devuelve la entidad creada con un id dado por la base de datos.
     */
    
    public HistoriaClinicaEntity create(HistoriaClinicaEntity historiaClinicaEntity) 
    {
        LOGGER.log(Level.INFO, "Creando una nueva historia clinica");
        em.persist(historiaClinicaEntity);
        LOGGER.log(Level.INFO, "Historia clinica creada");
        return historiaClinicaEntity;
    }
    
    /**
     * Devuelve todas las historias clinicas de la base de datos.
     * @return una lista con todas las historias clinicas que encuentre en la base de datos.
     */
    
    public List<HistoriaClinicaEntity> findAll() 
    {
        LOGGER.log(Level.INFO, "Consultando todas las historias clinicas");
        Query q = em.createQuery("select u from HistoriaClinicaEntity u");
        return q.getResultList();
    }
    
    /**
     * Busca la historia clinica con el id que se envía de parametro
     * @param historiaId: id correspondiente a la historia clinica buscada.
     * @return una historia clinica.
     */
    
    public HistoriaClinicaEntity find(Long historiaId) 
    {
        LOGGER.log(Level.INFO, "Consultando la historia clinica con id={0}", historiaId);
        return em.find(HistoriaClinicaEntity.class, historiaId);
    }
    
    /**
     * Actualiza una historia clinica.
     * @param historiaClinicaEntity: la historia clinica que viene con nueva informacion. 
     * @return una historia clinica con los cambios aplicados.
     */
    
    public HistoriaClinicaEntity update(HistoriaClinicaEntity historiaClinicaEntity) 
    {
        LOGGER.log(Level.INFO, "Actualizando la historia clinica con id={0}", historiaClinicaEntity.getId());
        return em.merge(historiaClinicaEntity);
    }
    
    /**
     * Borra una historia clinica de la base de datos con el id que entra por parametro
     * @param historiaId: id correspondiente a la historia clinica a borrar.
     */
    
    public void delete(Long historiaId) 
    {
        LOGGER.log(Level.INFO, "Borrando la historia clinica con id={0}", historiaId);
        HistoriaClinicaEntity historiaCkinicaEntity = em.find(HistoriaClinicaEntity.class, historiaId);
        em.remove(historiaCkinicaEntity);
    }
    
    
}
