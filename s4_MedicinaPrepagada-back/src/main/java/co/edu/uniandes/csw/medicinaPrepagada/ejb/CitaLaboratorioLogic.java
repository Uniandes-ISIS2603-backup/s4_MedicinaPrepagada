/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.medicinaPrepagada.ejb;

import co.edu.uniandes.csw.medicinaPrepagada.entities.CitaLaboratorioEntity;
import co.edu.uniandes.csw.medicinaPrepagada.entities.LaboratorioEntity;

import co.edu.uniandes.csw.medicinaPrepagada.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.medicinaPrepagada.persistence.CitaLaboratorioPersistence;

import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Santiago Rojas
 */
@Stateless
public class CitaLaboratorioLogic {

    private static final Logger LOGGER = Logger.getLogger(CitaLaboratorioLogic.class.getName());

    @Inject
    private CitaLaboratorioPersistence citaPersistence;

    /**
     * Se encarga de crear un Sede en la base de datos.
     *
     * @param citaLabEntity Objeto de CitaLaboratorioEntity con los datos nuevos
     * @return Objeto de CitaLaboratorioEntity con los datos nuevos.
     * @throws co.edu.uniandes.csw.medicinaPrepagada.exceptions.BusinessLogicException
     */
    public CitaLaboratorioEntity createCitaLaboratorio(CitaLaboratorioEntity citaLabEntity) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de creación de la cita laboratorio");

        if (!validateFecha(citaLabEntity.getFecha())) 
        {
            throw new BusinessLogicException("La fecha no puede ser vacia o anterior a la fecha actual");
        }
        
        if (!validateRecomendaciones(citaLabEntity.getRecomendaciones())) 
        {
            throw new BusinessLogicException("Recomendaciones no pueden ser vacios");
        }
        if (!validateComentarios(citaLabEntity.getComentarios())) {
            throw new BusinessLogicException("Comentarios no pueden ser vacios");
        }
       if(!validateEspecialidad(citaLabEntity.getEspecialidad()))
       {
           throw new BusinessLogicException("El formato de la especialidad no es valido");
       }
       
        CitaLaboratorioEntity newCitaLabEntity = citaPersistence.create(citaLabEntity);
        LOGGER.log(Level.INFO, "Termina proceso de creación de la cita de laboratorio");

        return newCitaLabEntity;

    }

    /**
     * Obtiene la lista de los registros de citasLab.
     *
     * @return Colección de objetos de CitaLaboratorioEntity.
     */
    public List<CitaLaboratorioEntity> getCitasLab() {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todas las citas");
        List<CitaLaboratorioEntity> lista = citaPersistence.findAll();
        LOGGER.log(Level.INFO, "Termina proceso de consultar todas las citas");
        return lista;
    }
    /**
     * Obtiene los datos de una instancia de CitaLab a partir de su ID.
     *
     * @param citaId Identificador de la instancia a consultar
     * @return Instancia de CitaLaboratorioEntity con los datos de la cita consultada.
     */ 
    public CitaLaboratorioEntity getCita(Long citaId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar el citaLaboratorio con id = {0}", citaId);
        CitaLaboratorioEntity citaLabEntity = citaPersistence.find(citaId);
        if (citaLabEntity == null) {
            LOGGER.log(Level.SEVERE, "La citaLab con el id = {0} no existe", citaId);
        }
        LOGGER.log(Level.INFO, "Termina proceso de consultar la citaLab con id = {0}", citaId);
        return citaLabEntity;
    }
    
    public LaboratorioEntity getLaboratorioFromCita (Long citaId)
    {
       return citaPersistence.find(citaId).getLaboratorio();
    }
     /**
     * Actualiza la información de una instancia de CitaLaboratorio.
     *
     * @param citaId Identificador de la instancia a actualizar
     * @param citaLabEntity Instancia de CitaLaboratorioEntity con los nuevos datos.
     * @return Instancia de CitaLaboratorioEntity con los datos actualizados.
     * @throws co.edu.uniandes.csw.medicinaPrepagada.exceptions.BusinessLogicException
     */
    public CitaLaboratorioEntity updateCitaLaboratorio (Long citaId, CitaLaboratorioEntity citaLabEntity) throws BusinessLogicException
    {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar el cita laboratorio con id = {0}", citaId);
        
        CitaLaboratorioEntity citaVieja = citaPersistence.find(citaId);
        
        if (citaVieja == null)
        {
            throw new BusinessLogicException("La cita que intenta cambiar no existe");
        }
        if (citaLabEntity.getId()!=citaId)
        {
            throw new BusinessLogicException("No puede cambiar el Id");
        }
        if (!validateFecha(citaLabEntity.getFecha()))
        {
            throw new BusinessLogicException("La fecha no puede ser vacia ni anterior a la fecha actual");
        }
         if(!validateEspecialidad(citaLabEntity.getEspecialidad()))
       {
           throw new BusinessLogicException("El formato de la especialidad no es valido");
       }
        
        CitaLaboratorioEntity newCitaEntity = citaPersistence.update(citaLabEntity);
        LOGGER.log(Level.INFO, "Termina proceso de actualizar la cita de laboratorio con id = {0}", citaId);
        return newCitaEntity;
    }
    
    /**
     * Elimina una instancia de CitaLab de la base de datos.
     *
     * @param citaId Identificador de la instancia a eliminar.
     */
    public void deleteCitaLab(Long citaId)
    {
        citaPersistence.delete(citaId);
    }
    
    /**
     * Valida la fecha 
     * @param pFecha
     * @return true para fecha valida, false de lo contrario
     */
    private boolean validateFecha(Date pFecha) 
    {
       
        Date x = new Date();
        
        return !(pFecha == null || pFecha.before(x));
    }
    
    /**
     * Valida la especialidad
     * @param pEspec
     * @return true para especialidad valida, false de lo contrario
     */ 
    private boolean validateEspecialidad (String pEspec)
    {
        return !(pEspec == null || pEspec.isEmpty());
    }

    /**
     * Valida los comentarios
     * @param pComentarios
     * @return true para comentarios validos, false de lo contrario
     */
    private boolean validateComentarios(String pComentarios) {
        return !(pComentarios.isEmpty());
    }
    
    /**
     * Valida las recomendaciones
     * @param pRecomendaciones
     * @return true para recomendaciones validas, false de lo contrario
     */
    private boolean validateRecomendaciones (String pRecomendaciones) {
        return !(pRecomendaciones.isEmpty());
    }
}
