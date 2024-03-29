/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.medicinaPrepagada.persistence;

import co.edu.uniandes.csw.medicinaPrepagada.entities.LaboratorioEntity;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author Santiago Rojas
 */

@Stateless
public class LaboratorioPersistence {
    
    private static final Logger LOGGER = Logger.getLogger(LaboratorioPersistence.class.getName());
    
    @PersistenceContext(unitName = "MedisistemasPU")
    protected EntityManager em;
    
     /**
     * Crea un sede en la base de datos
     *
     * @param LaboratorioEntity objeto laboratorio que se creará en la base de datos
     * @return devuelve la entidad creada con un id dado por la base de datos.
     */
    
    public LaboratorioEntity create(LaboratorioEntity labEntity) 
    {
        LOGGER.log(Level.INFO, "Creando un laboratorio nuevo");
        em.persist(labEntity);
        LOGGER.log(Level.INFO, "Laboratorio ha sido creado");
        return labEntity;
    }
    /**
     * Encuentra un laboratorio en la base de datos
     *
     * @param labId Id del laboratorio que se buscara en la base de datos
     * @return devuelve la entidad encontrada con un id dado.
     */
     public LaboratorioEntity find(Long labId)
    {
        LOGGER.log(Level.INFO, "Consultando el laboratorio con id={0}", labId);
        return em.find(LaboratorioEntity.class, labId);
    }
     
     public List<LaboratorioEntity> findAll()
     {
         LOGGER.log(Level.INFO, "Buscando todos los laboratorios");
         TypedQuery query = em.createQuery("select u from LaboratorioEntity u", LaboratorioEntity.class);
         
         return query.getResultList();
     }
     public LaboratorioEntity findByNombre(String pNombre)
    {
        LOGGER.log(Level.INFO, "Consultando el lab con nombre={0}", pNombre);
        
        
       TypedQuery q = em.createQuery("Select e from LaboratorioEntity e where e.nombre = :nombre", LaboratorioEntity.class);
        q = q.setParameter("nombre", pNombre);       
        try 
        {
            return (LaboratorioEntity) q.getSingleResult();
        }
        catch (NoResultException excp)
        {
            return null;
        }
    }
     
     public LaboratorioEntity update (LaboratorioEntity labEntity)
     {
         LOGGER.log(Level.INFO,"Buscando el laboratorio para modificar con id={0}", labEntity.getId());
         return em.merge(labEntity);
     }
     

        
    public LaboratorioEntity findByDireccion(String pDireccion)
    {
        LOGGER.log(Level.INFO, "Consultando el lab con direccion={0}", pDireccion);
        
       TypedQuery q = em.createQuery("Select e from LaboratorioEntity e where e.direccion = :direccion", LaboratorioEntity.class);
        q = q.setParameter("direccion", pDireccion);       
        try 
        {
            return (LaboratorioEntity) q.getSingleResult();
        }
        catch (NoResultException excp)
        {
            return null;
        }
    }
     
     public void delete (Long labId)
     {
         LOGGER.log(Level.INFO,"Borrando laboratorio con id={0}",labId);
         LaboratorioEntity labEntity = em.find(LaboratorioEntity.class,labId);
         em.remove(labEntity);
     }
}

