/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.medicinaPrepagada.dtos;

import co.edu.uniandes.csw.medicinaPrepagada.entities.CitaMedicaEntity;
import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 *
 * @author estudiante
 */
public class CitaMedicaDTO implements Serializable
{
    private long idCitaMedica;
    private Date fecha;
    private String comentarios;
    private HorarioAtencionDTO horarioAtencionAsignado;
    private PacienteDTO pacienteAAtender;
    
    public CitaMedicaDTO (CitaMedicaEntity citaMedicaEntity){
        if (citaMedicaEntity != null){
           this.idCitaMedica =citaMedicaEntity.getId();
           this.fecha = citaMedicaEntity.getFecha();
           this.comentarios = citaMedicaEntity.getComentarios();
           this.horarioAtencionAsignado = new HorarioAtencionDTO(citaMedicaEntity.getHorarioAtencionAsignado());
           this.pacienteAAtender = new PacienteDTO(citaMedicaEntity.getPacienteAAtender());
        }   
    }
     
     
     /**
     * Convierte un objeto ConsultorioDTO a CitaMedicaEntity.
     *
     * @return Nueva objeto CitaMedicaEntity.
     *
     */
    public CitaMedicaEntity toEntity() 
    {
        CitaMedicaEntity citaMedicaEntity = new CitaMedicaEntity();
        
        citaMedicaEntity.setId(this.getIdCitaMedica());
        citaMedicaEntity.setFecha(this.getFecha());
        citaMedicaEntity.setComentarios(this.getComentarios());
        if(this.horarioAtencionAsignado != null){
            citaMedicaEntity.setHorarioAtencionAsignado(this.horarioAtencionAsignado.toEntity());
        }
        if(this.pacienteAAtender != null){
            citaMedicaEntity.setPacienteAAtender(this.pacienteAAtender.toEntity());
        }
        
 
        return citaMedicaEntity;
    }

    
    public CitaMedicaDTO(){
    
    }

    public long getIdCitaMedica() {
        return idCitaMedica;
    }

    public void setIdCitaMedica(long idCitaMedica) {
        this.idCitaMedica = idCitaMedica;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    /**
     * @return the horarioAtencionAsignado
     */
    public HorarioAtencionDTO getHorarioAtencionAsignado() {
        return horarioAtencionAsignado;
    }

    /**
     * @param horarioAtencionAsignado the horarioAtencionAsignado to set
     */
    public void setHorarioAtencionAsignado(HorarioAtencionDTO horarioAtencionAsignado) {
        this.horarioAtencionAsignado = horarioAtencionAsignado;
    }

    /**
     * @return the pacienteAAtender
     */
    public PacienteDTO getPacienteAAtender() {
        return pacienteAAtender;
    }

    /**
     * @param pacienteAAtender the pacienteAAtender to set
     */
    public void setPacienteAAtender(PacienteDTO pacienteAAtender) {
        this.pacienteAAtender = pacienteAAtender;
    }
    
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
    
}
