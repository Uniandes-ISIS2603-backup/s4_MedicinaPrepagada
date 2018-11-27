/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.medicinaPrepagada.persistence;

import co.edu.uniandes.csw.medicinaPrepagada.entities.CitaLaboratorioEntity;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author Santiago Rojas
 */
@Stateless
public class CitaLaboratorioPersistence {
    
    private static final Logger LOGGER = Logger.getLogger(CitaLaboratorioPersistence.class.getName());
    
    @PersistenceContext(unitName = "MedisistemasPU")
    protected EntityManager em;
    
    public CitaLaboratorioEntity create(CitaLaboratorioEntity citaLabEntity) 
    {
        LOGGER.log(Level.INFO, "Creando una cita laboratorio nueva");
        em.persist(citaLabEntity);
        LOGGER.log(Level.INFO, "Cita laboratorio ha sido creada");
        return citaLabEntity;
    }
    
     public List<CitaLaboratorioEntity> findAll() 
    {
        LOGGER.log(Level.INFO, "Consultando todas las CitaLaboratorio");
        TypedQuery query = em.createQuery("select c from CitaLaboratorioEntity c", CitaLaboratorioEntity.class);
        return query.getResultList();
    }
    
   public CitaLaboratorioEntity find(Long citaLabId)
    {
        LOGGER.log(Level.INFO, "Consultando el laboratorio con id={0}", citaLabId);
        return em.find(CitaLaboratorioEntity.class, citaLabId);
    }
   
   public CitaLaboratorioEntity update(CitaLaboratorioEntity citaLabEntity)
     {
         LOGGER.log(Level.INFO,"Buscando la cita laboratorio para modificar con id={0}", citaLabEntity.getId());
         return em.merge(citaLabEntity);
     }
     
     public void delete (Long citaLabId)
     {
         LOGGER.log(Level.INFO,"Borrando la cita laboratorio con id={0}",citaLabId);
         CitaLaboratorioEntity citaLabEntity = em.find(CitaLaboratorioEntity.class,citaLabId);
         em.remove(citaLabEntity);
     }
   
    
}
