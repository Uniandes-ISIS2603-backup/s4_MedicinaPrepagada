/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.medicinaPrepagada.persistence;

import co.edu.uniandes.csw.medicinaPrepagada.entities.AdministradorEntity;
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
public class AdministradorPersistence 
{
    private static final Logger LOGGER = Logger.getLogger(AdministradorPersistence.class.getName());
    
    @PersistenceContext(unitName = "AdministradorPU")
    protected EntityManager em;
    
     /**
     * Método para persisitir la entidad en la base de datos.
     * @param administradorEntity objeto administrador que se creará en la base de datos
     * @return devuelve la entidad creada con un id dado por la base de datos.
     */
    
    public AdministradorEntity create(AdministradorEntity administradorEntity) 
    {
        LOGGER.log(Level.INFO, "Creando un nuevo administrador");
        em.persist(administradorEntity);
        LOGGER.log(Level.INFO, "Administrador creado");
        return administradorEntity;
    }
    
    /**
     * Devuelve todos los administradores de la base de datos.
     * @return una lista con todos los administradores que encuentre en la base de datos,
     */
    
    public List<AdministradorEntity> findAll() 
    {
        LOGGER.log(Level.INFO, "Consultando todos los administradores");
        Query q = em.createQuery("select u from AdministradorEntity u");
        return q.getResultList();
    }
    
    /**
     * Busca el administrador con el id que se envía de parametro
     * @param admiId: id correspondiente al administrador buscado.
     * @return un administrador. 
     */
    
    public AdministradorEntity find(Long admiId) 
    {
        LOGGER.log(Level.INFO, "Consultando el administrador con id={0}", admiId);
        return em.find(AdministradorEntity.class, admiId);
    }
    
    /**
     * Actualiza un administrador.
     * @param admiEntity: el administrador que viene con nueva informacion. 
     * @return un administrador con los cambios aplicados.
     */
    
    public AdministradorEntity update(AdministradorEntity admiEntity) 
    {
        LOGGER.log(Level.INFO, "Actualizando el administrador con id={0}", admiEntity.getId());
        return em.merge(admiEntity);
    }
    
    /**
     * Borra un administrador de la base de datos con el id que entra por parametro
     * @param admiId: id correspondiente al adminsitrador a borrar.
     */
    
    public void delete(Long admiId) 
    {
        LOGGER.log(Level.INFO, "Borrando el administrador con id={0}", admiId);
        AdministradorEntity admiEntity = em.find(AdministradorEntity.class, admiId);
        em.remove(admiEntity);
    }
    
    
}
