/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.medicinaPrepagada.ejb;

import co.edu.uniandes.csw.medicinaPrepagada.entities.CitaLaboratorioEntity;

import co.edu.uniandes.csw.medicinaPrepagada.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.medicinaPrepagada.persistence.CitaLaboratorioPersistence;

import java.time.Instant;
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

    public CitaLaboratorioEntity createCitaLaboratorio(CitaLaboratorioEntity citaLabEntity) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de creación de la cita laboratorio");

        if (!validateFecha(citaLabEntity.getDate())) 
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

    public List<CitaLaboratorioEntity> getCitasLab() {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todas las citas");
        List<CitaLaboratorioEntity> lista = citaPersistence.findAll();
        LOGGER.log(Level.INFO, "Termina proceso de consultar todas las citas");
        return lista;
    }

    public CitaLaboratorioEntity getCita(Long citaId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar el citaLaboratorio con id = {0}", citaId);
        CitaLaboratorioEntity citaLabEntity = citaPersistence.find(citaId);
        if (citaLabEntity == null) {
            LOGGER.log(Level.SEVERE, "La citaLab con el id = {0} no existe", citaId);
        }
        LOGGER.log(Level.INFO, "Termina proceso de consultar la citaLab con id = {0}", citaId);
        return citaLabEntity;
    }
    
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
        if (!validateFecha(citaLabEntity.getDate()))
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
    
    public void deleteCitaLab(Long citaId)
    {
        citaPersistence.delete(citaId);
    }

    private boolean validateFecha(Date pFecha) 
    {
       
        Date x = new Date().from(Instant.now());
        
        return !(pFecha == null || pFecha.before(x));
    }
    
     private boolean validateEspecialidad (String pEspec)
    {
        return !(pEspec == null || pEspec.isEmpty());
    }

    private boolean validateComentarios(String pComentarios) {
        return !(pComentarios.isEmpty());
    }
    private boolean validateRecomendaciones (String pRecomendaciones) {
        return !(pRecomendaciones.isEmpty());
    }
}
