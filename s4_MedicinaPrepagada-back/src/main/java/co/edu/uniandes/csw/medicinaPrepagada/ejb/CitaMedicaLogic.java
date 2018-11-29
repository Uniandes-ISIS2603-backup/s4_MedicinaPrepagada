/*
 * To change this license header, choase License Headers in Project Properties.
 * To change this template file, choase Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.medicinaPrepagada.ejb;

import co.edu.uniandes.csw.medicinaPrepagada.entities.CitaMedicaEntity;
import co.edu.uniandes.csw.medicinaPrepagada.entities.HorarioAtencionEntity;
import co.edu.uniandes.csw.medicinaPrepagada.entities.PacienteEntity;
import co.edu.uniandes.csw.medicinaPrepagada.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.medicinaPrepagada.persistence.CitaMedicaPersistence;
import co.edu.uniandes.csw.medicinaPrepagada.persistence.HorarioAtencionPersistence;
import co.edu.uniandes.csw.medicinaPrepagada.persistence.PacientePersistence;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Daniel Ivan Romero
 */

@Stateless
public class CitaMedicaLogic {
    private static final Logger LOGGER = Logger.getLogger(CitaMedicaLogic.class.getName());

    @Inject
    private CitaMedicaPersistence persistence;
    
    @Inject
    private PacientePersistence pacienetePersistence;
    
    @Inject
    private HorarioAtencionPersistence horarioPersistence;
   
    public CitaMedicaEntity createCitaMedica(CitaMedicaEntity citaMedicaEntity) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de creación de la cita medica");
        if(citaMedicaEntity.getFecha() == null){
            throw new BusinessLogicException("Debe ingresarse una fecha. ");
        }
        Date fechaInicio = citaMedicaEntity.getFecha();
        Date fechaFin = mas20Minutos(fechaInicio);
        if(!Objects.equals(persistence.findByFechaYConsultorio(fechaInicio, fechaFin, citaMedicaEntity.getHorarioAtencionAsignado().getConsultorio().getId()),null)){
            throw new BusinessLogicException("Ya existe una cita en el consultorio a una hora que impide esta reserva. ");
        }
        if(!Objects.equals(persistence.findByFechaYMedico(fechaInicio, fechaFin, citaMedicaEntity.getHorarioAtencionAsignado().getMedico().getId()),null)){
            throw new BusinessLogicException("Ya existe una cita con el médico a una hora que impide esta reserva. ");
        }
        if(persistence.findByLimitesFechaInicioFechaFinSedeYMedico(fechaInicio, fechaFin, citaMedicaEntity.getHorarioAtencionAsignado().getConsultorio().getId(), citaMedicaEntity.getHorarioAtencionAsignado().getMedico().getId())==null){
            throw new BusinessLogicException("No existe un horario de atención disponible para esta nueva cita. ");
        }
        citaMedicaEntity.setHorarioAtencionAsignado(persistence.findByLimitesFechaInicioFechaFinSedeYMedico(fechaInicio, fechaFin, citaMedicaEntity.getHorarioAtencionAsignado().getConsultorio().getId(), citaMedicaEntity.getHorarioAtencionAsignado().getMedico().getId()));
        if(fechaInicio.compareTo(new Date())<0){
            throw new BusinessLogicException("En la fecha no es posible agendar una cita ");
        }
        persistence.create(citaMedicaEntity);
        LOGGER.log(Level.INFO, "Termina proceso de creación de la médico satisfactoriamente");
        return citaMedicaEntity;
    }
    
    /**
     * Devuelve todas las citaMedicas que hay en la base de datas.
     *
     * @return Lista de entidades de tipo citaMedica.
     */
    public List<CitaMedicaEntity> getCitasMedicas() {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todas las citaMedicas");
        List<CitaMedicaEntity> citaMedicas = persistence.findAll();
        LOGGER.log(Level.INFO, "Termina proceso de consultar todas las citaMedicas");
        return citaMedicas;
    }
    
    public CitaMedicaEntity crearCita(CitaMedicaEntity citaMedicaEntity, Long idPaciente , Long idHorario)throws BusinessLogicException{
        PacienteEntity pac = pacienetePersistence.find(idPaciente);
        if(pac == null){
            throw new BusinessLogicException("No hay Paciente");
        }
        citaMedicaEntity.setPacienteAAtender(pac);
        HorarioAtencionEntity horario = horarioPersistence.find(idHorario);
        if(horario == null){
            throw new BusinessLogicException("No hay Horario");
        }
        citaMedicaEntity.setHorarioAtencionAsignado(horario);
        LOGGER.log(Level.INFO, "Inicia proceso de creación de la cita medica");
        if(citaMedicaEntity.getFecha() == null){
            throw new BusinessLogicException("Debe ingresarse una fecha. ");
        }
        Date fechaInicio = citaMedicaEntity.getFecha();
        Date fechaFin = mas20Minutos(fechaInicio);
        if(!Objects.equals(persistence.findByFechaYConsultorio(fechaInicio, fechaFin, citaMedicaEntity.getHorarioAtencionAsignado().getConsultorio().getId()),null)){
            throw new BusinessLogicException("Ya existe una cita en el consultorio a una hora que impide esta reserva. ");
        }
        if(!Objects.equals(persistence.findByFechaYMedico(fechaInicio, fechaFin, citaMedicaEntity.getHorarioAtencionAsignado().getMedico().getId()),null)){
            throw new BusinessLogicException("Ya existe una cita con el médico a una hora que impide esta reserva. ");
        }
        if(persistence.findByLimitesFechaInicioFechaFinSedeYMedico(fechaInicio, fechaFin, citaMedicaEntity.getHorarioAtencionAsignado().getConsultorio().getId(), citaMedicaEntity.getHorarioAtencionAsignado().getMedico().getId())==null){
            throw new BusinessLogicException("No existe un horario de atención disponible para esta nueva cita. ");
        }
        citaMedicaEntity.setHorarioAtencionAsignado(persistence.findByLimitesFechaInicioFechaFinSedeYMedico(fechaInicio, fechaFin, citaMedicaEntity.getHorarioAtencionAsignado().getConsultorio().getId(), citaMedicaEntity.getHorarioAtencionAsignado().getMedico().getId()));
        if(fechaInicio.compareTo(new Date())<0){
            throw new BusinessLogicException("En la fecha no es posible agendar una cita ");
        }
        persistence.create(citaMedicaEntity);
        return citaMedicaEntity;
    }
    
    /**
     * Busca un citaMedica por ID
     *
     * @param citaMedicasId La id de la citaMedica a buscar
     * @return La citaMedica encontrado, null si no lo encuentra.
     */
    public CitaMedicaEntity getCitaMedica(Long citaMedicasId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar la citaMedica con id = {0}", citaMedicasId);
        CitaMedicaEntity citaMedicaEntity = persistence.find(citaMedicasId);
        if (citaMedicaEntity == null) {
            LOGGER.log(Level.SEVERE, "La citaMedica con la id = {0} no existe", citaMedicasId);
        }
        LOGGER.log(Level.INFO, "Termina proceso de consultar la citaMedica con id = {0}", citaMedicasId);
        return citaMedicaEntity;
    }

    /**
     * Actualiza la información de una instancia de CitaMedica.
     *
     * @param citaMedicasId Identificador de la instancia a actualizar
     * @param citaMedicaEntity Instancia de CitaMedicaEntity con las nuevas datas.
     * @return Instancia de CitaMedicaEntity con las datas actualizadas.
     */
    public CitaMedicaEntity updateCitaMedica(Long citaMedicasId, CitaMedicaEntity citaMedicaEntity) throws BusinessLogicException{
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar la citaMedica con id = {0}", citaMedicasId);
        CitaMedicaEntity citaExistente = persistence.find(citaMedicasId);
        if(citaExistente == null){
            throw new BusinessLogicException("La cita no existe. ");
        }
        PacienteEntity pacienteAnterior = persistence.find(citaMedicasId).getPacienteAAtender();
        PacienteEntity pacienteNuevo = citaMedicaEntity.getPacienteAAtender();
        if(citaMedicaEntity.getFecha() == null){
            throw new BusinessLogicException("Debe ingresarse una fecha. ");
        }
        if(!citaMedicasId.equals(citaMedicaEntity.getId())){
            throw new BusinessLogicException("No se puede modificar el id. ");
        }
        if(pacienteNuevo==null){
            throw new BusinessLogicException("No existe el paciente para asignar la cita. ");
        }
        if(!pacienteAnterior.getId().equals(pacienteNuevo.getId())){
            throw new BusinessLogicException("No se puede modificar el paciente. ");
        }
        Date fechaInicio = citaMedicaEntity.getFecha();
        Date fechaFin = mas20Minutos(fechaInicio);
        if(fechaInicio.compareTo(masUnaHora(new Date()))<0){
            throw new BusinessLogicException("No se puede modificar la cita con menos de una hora de anticipación. ");
        }
        if(persistence.findByFechaYConsultorio(fechaInicio, fechaFin, citaMedicaEntity.getHorarioAtencionAsignado().getConsultorio().getId())!=null && !persistence.findByFechaYConsultorio(fechaInicio, fechaFin, citaMedicaEntity.getHorarioAtencionAsignado().getConsultorio().getId()).getId().equals(citaMedicaEntity.getId())){
            throw new BusinessLogicException("Ya existe una cita en el consultorio a una hora que impide esta reserva. ");
        }
        if(persistence.findByFechaYMedico(fechaInicio, fechaFin, citaMedicaEntity.getHorarioAtencionAsignado().getMedico().getId())!=null && !persistence.findByFechaYMedico(fechaInicio, fechaFin, citaMedicaEntity.getHorarioAtencionAsignado().getMedico().getId()).getId().equals(citaMedicaEntity.getId())){
            throw new BusinessLogicException("Ya existe una cita con el médico a una hora que impide esta reserva. ");
        }
        if(persistence.findByLimitesFechaInicioFechaFinSedeYMedico(fechaInicio, fechaFin, citaMedicaEntity.getHorarioAtencionAsignado().getConsultorio().getId(), citaMedicaEntity.getHorarioAtencionAsignado().getMedico().getId())==null){
            throw new BusinessLogicException("No existe un horario de atención disponible para esta nueva cita. ");
        }
        if(fechaInicio.compareTo(new Date())<0){
            throw new BusinessLogicException("En la fecha no es posible agendar una cita ");
        }
        CitaMedicaEntity newCitaMedicaEntity = persistence.update(citaMedicaEntity);
        LOGGER.log(Level.INFO, "Termina proceso de actualizar la citaMedica con id = {0}", citaMedicasId);
        return newCitaMedicaEntity;
    }

    /**
     * Eliminar un citaMedica por ID
     *
     * @param citaMedicasId La ID de la citaMedica a eliminar
     * @throws BusinessLogicException si la citaMedica tiene citaMedicas asociadas
     */
    public void deleteCitaMedica(Long citaMedicasId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar la citaMedica con id = {0}", citaMedicasId);
        CitaMedicaEntity cita = persistence.find(citaMedicasId);
        if(cita == null){
            throw new BusinessLogicException("No se puede eliminar una cita inexistente. ");
        }
        Date fechaInicio = cita.getFecha();
        if(fechaInicio.compareTo(masUnaHora(new Date()))<0){
            throw new BusinessLogicException("No se puede eliminar la cita con menos de una hora de anticipación. ");
        }
        persistence.delete(citaMedicasId);
        LOGGER.log(Level.INFO, "Termina proceso de borrar la citaMedica con id = {0}", citaMedicasId);
    }
    
    public Date mas20Minutos(Date fechaInicial){
        long constanteMinutos = 1200000;
        
        long ahora = fechaInicial.getTime();
        Date despues = new Date(ahora+constanteMinutos);
        return despues;
    }
    
    public Date masUnaHora(Date fechaInicial){
        long constanteMinutos = 3600000;
        
        long ahora = fechaInicial.getTime();
        Date despues = new Date(ahora+constanteMinutos);
        return despues;
    }
    
}
