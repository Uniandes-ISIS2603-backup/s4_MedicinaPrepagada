/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.medicinaPrepagada.ejb;

import co.edu.uniandes.csw.medicinaPrepagada.entities.ExamenMedicoEntity;
import co.edu.uniandes.csw.medicinaPrepagada.entities.OrdenMedicaEntity;
import co.edu.uniandes.csw.medicinaPrepagada.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.medicinaPrepagada.persistence.OrdenMedicaPersistence;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.WebApplicationException;

/**
 *
 * @author ni.ramirez10
 */

@Stateless
public class OrdenMedicaLogic 
{
    private static final Logger LOGGER = Logger.getLogger(OrdenMedicaLogic.class.getName());

    @Inject
    private OrdenMedicaPersistence persistence; 
    
     /**
     * Crea una orden medica en la persistencia.
     * @param ordenEntity
     * @return La entiddad de la orden medica luego de persistirla.
     * @throws BusinessLogicException X
     */
    
    public OrdenMedicaEntity createOrdenMedica(OrdenMedicaEntity ordenEntity) throws BusinessLogicException 
    {
        LOGGER.log(Level.INFO, "Inicia proceso de creación de la orden medica");
        
        Date d1 = Date.from(Instant.now());        
        Date fechaValidoHasta = ordenEntity.getValidaHasta() ;
        
         /**if(persistence.find(ordenEntity.getId()) != null)
         {
              throw new WebApplicationException("Ya existe una orden con ese id");
         }*/
         
        try
        {
            if(fechaValidoHasta.compareTo(d1) < 0)
            {
                throw new BusinessLogicException("La validez de la orden es antes de la fecha actual");
            }
        }
        catch( Exception e)
                {
                
                }
        
        if( ordenEntity.getFirmaMedico() == null ||
            ordenEntity.getFirmaMedico().equals("")   )
        {
            throw new BusinessLogicException("La firma del medico no puede ser vacia");
        }

        persistence.create(ordenEntity);
        LOGGER.log(Level.INFO, "Termina proceso de creación de la orden medica");
        return ordenEntity;
    }
    
    /**
     * Obtener todas las ordenes medicas existentes en la base de datos.
     * @return una lista de ordenes medicas.
     */
    
    public List<OrdenMedicaEntity> getOrdenesMedicas( )
    {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todas las ordenes medicas");
        List<OrdenMedicaEntity> ordenes = persistence.findAll();
        LOGGER.log(Level.INFO, "Termina proceso de consultar todas las ordenes medicas");
        return ordenes;
    }
    
     /**
     * Obtener una orden medica por medio de su id.
     * @param ordenId
     * @return la orden medica solicitada por medio de su id.
     */
    
    public OrdenMedicaEntity getOrdenMedica(Long ordenId)
    {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar la orden medica con id = {0}", ordenId);
    
        OrdenMedicaEntity ordenEntity = persistence.find(ordenId);
        
        if (ordenEntity == null) 
        {
            LOGGER.log(Level.SEVERE, "La orden medica con el id = {0} no existe", ordenId);
        }
        
        LOGGER.log(Level.INFO, "Termina proceso de consultar la orden medica con id = {0}", ordenId);
        return ordenEntity;
    }
    
    /**
     * Actualizar una orden medica.
     * @param ordenId
     * @param ordenEntity
     * @return la orden medica con los cambios actualizados en la base de datos.
     * @throws co.edu.uniandes.csw.medicinaPrepagada.exceptions.BusinessLogicException
     */
    
    public OrdenMedicaEntity updateOrdenMedica(Long ordenId, OrdenMedicaEntity ordenEntity) throws BusinessLogicException
    {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar la orden medica con id = {0}", ordenId);
        
        /**if (!persistence.find(ordenId).getFirmaMedico().equals(ordenEntity.getFirmaMedico()))
        {
            throw new BusinessLogicException("No se puede modificar la firma del medico");
        }*/
        
        if (!persistence.find(ordenId).getFechaExpedicion().equals(ordenEntity.getFechaExpedicion()))
        {
            throw new BusinessLogicException("No se puede modificar la fecha de expedicion");
        }
        
        if( ordenEntity.getId() != ordenId )
        {
            throw new BusinessLogicException("No se puede modificar el id. ");
        }
        
        OrdenMedicaEntity newEntity = persistence.update(ordenEntity);
        LOGGER.log(Level.INFO, "Termina proceso de actualizar la orden medica con id = {0}", ordenEntity.getId());
        return newEntity;
    }
    
    /**
     * Borrar una orden medica
     * @param ordenId
     * @throws BusinessLogicException X
     */
    
    public void deleteOrdenMedica(Long ordenId) throws BusinessLogicException 
    {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar la orden medica con id = {0}", ordenId);
        
        List<ExamenMedicoEntity> examenes = getOrdenMedica(ordenId).getExamenesMedicos(); 
        
        if (examenes != null && !examenes.isEmpty()) 
        {
            throw new BusinessLogicException("No se puede borrar la orden medica con id = " + ordenId + " porque tiene examenes medicos asociados");
        }
        persistence.delete(ordenId);
        LOGGER.log(Level.INFO, "Termina proceso de borrar la orden medica con id = {0}", ordenId);
    }

    
}
