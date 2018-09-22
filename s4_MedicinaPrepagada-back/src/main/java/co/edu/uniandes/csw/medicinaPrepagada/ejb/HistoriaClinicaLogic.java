/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.medicinaPrepagada.ejb;

import co.edu.uniandes.csw.medicinaPrepagada.entities.HistoriaClinicaEntity;
import co.edu.uniandes.csw.medicinaPrepagada.entities.OrdenMedicaEntity;
import co.edu.uniandes.csw.medicinaPrepagada.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.medicinaPrepagada.persistence.HistoriaClinicaPersistence;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author ni.ramirez10
 */

@Stateless
public class HistoriaClinicaLogic 
{
     private static final Logger LOGGER = Logger.getLogger(HistoriaClinicaLogic.class.getName());

    @Inject
    private HistoriaClinicaPersistence persistence;
    
    /**
     * Guardar una nueva historia clinica
     * @param historiaEntity
     * @return La entidad luego de persistirla
     * @throws BusinessLogicException X
     */
    
    public HistoriaClinicaEntity createHistoriaClinica(HistoriaClinicaEntity historiaEntity) throws BusinessLogicException 
    {
        LOGGER.log(Level.INFO, "Inicia proceso de creación de la historia clinica");
        
        if (historiaEntity.getPeso() < 0 ) 
        {
            throw new BusinessLogicException("El peso no puede ser negativo");
        }
        
        if (historiaEntity.getEstatura() < 0 ) 
        {
            throw new BusinessLogicException("La estatura no puede ser negativa");
        }
        
        if( historiaEntity.getDescripcionDiagnostico() == null || 
            historiaEntity.getDescripcionDiagnostico().equals("")   )
        {
            throw new BusinessLogicException("La descripcion no puede ser vacia");
        }

        persistence.create(historiaEntity);
        LOGGER.log(Level.INFO, "Termina proceso de creación de la historia clinica");
        return historiaEntity;
    }
    
     /**
     * Obtener todas las historia clinicas existentes en la base de datos.
     * @return una lista de historias clinicas.
     */
    
    public List<HistoriaClinicaEntity> getHistoriasClinicas( )
    {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todas las historias clinicas");
        List<HistoriaClinicaEntity> historias = persistence.findAll();
        LOGGER.log(Level.INFO, "Termina proceso de consultar todas las historias clinicas");
        return historias;
    }
    
    /**
     * Obtener una historia clinica por medio de su id.
     * @param historiaId
     * @return la historia clinica solicitada por medio de su id.
     */
    
    public HistoriaClinicaEntity getHistoriaClinica(Long historiaId)
    {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar la historia clinica con id = {0}", historiaId);
        HistoriaClinicaEntity historiaEntity = persistence.find(historiaId);
        
        if (historiaEntity == null) 
        {
            LOGGER.log(Level.SEVERE, "La historia clinica con el id = {0} no existe", historiaId);
        }
        
        LOGGER.log(Level.INFO, "Termina proceso de consultar la historia clinica con id = {0}", historiaId);
        return historiaEntity;
    }
    
    /**
     * Actualizar una historia clinica.
     * @param historiaId
     * @param historiaEntity
     * @return la historia clinica con los cambios actualizados en la base de datos.
     */
    
    public HistoriaClinicaEntity updateHistoriaClinica(Long historiaId, HistoriaClinicaEntity historiaEntity) throws BusinessLogicException 
    {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar la historia clinica con id = {0}", historiaId);
        
        if( historiaId != historiaEntity.getId() )
        {
            throw new BusinessLogicException("No se puede modificar el id. ");
        }
        
        /**if (persistence.find(historiaId).getAlergias() != null && !persistence.find(historiaId).getAlergias().equals(historiaEntity.getAlergias()))
        {
            throw new BusinessLogicException("No se pueden modificar las alergias");
        }*/

        HistoriaClinicaEntity newEntity = persistence.update(historiaEntity);
        LOGGER.log(Level.INFO, "Termina proceso de actualizar la historia clinica con id = {0}", historiaEntity.getId());
        return newEntity;
    }
    
    /**
     * Borrar una historia clinica
     * @param historiaId
     * @throws BusinessLogicException Si la historia a eliminar tiene ordenes medicas. 
     */
    
    public void deleteHistoriaClinica(Long historiaId) throws BusinessLogicException 
    {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar la historia clinica con id = {0}", historiaId);
        
        List<OrdenMedicaEntity> ordenes = getHistoriaClinica(historiaId).getOrdenesMedicas(); 
        
        if (ordenes != null && !ordenes.isEmpty()) 
        {
            throw new BusinessLogicException("No se puede borrar la historia clinica con id = " + historiaId + " porque tiene ordenes"
                    + " medicas asociadas");
        }
        persistence.delete(historiaId);
        LOGGER.log(Level.INFO, "Termina proceso de borrar la historia clinica con id = {0}", historiaId);
    }
    
}
