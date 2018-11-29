/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.medicinaPrepagada.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import uk.co.jemos.podam.common.PodamExclude;

/**
 *Representacion en la base de datos de una cita medica
 * @author Daniel Ivan Romero
 */
@Entity
public class CitaMedicaEntity implements Serializable{
    
    //id de la cita medica
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    // Fecha de la cita medica
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    //Comentarios de la cita medica
    private String comentarios;
    //Paciente de la cita medica
    @PodamExclude
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(nullable=true)
    private PacienteEntity pacienteAAtender;
    //Horario de atencion de la cita medica
    @PodamExclude
    @ManyToOne(cascade = CascadeType.PERSIST)//(mappedBy = "citaMedica", fetch=FetchType.EAGER)
    private HorarioAtencionEntity horarioAtencionAsignado;

    /**
     * Retorna la fecha de la cita medica
     * @return 
     */
    public Date getFecha() {
        return fecha;
    }
    /**
     * Edita la fecha de la cita medica
     * @param fecha 
     */
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
    /**
     * Retorna los comentarios de la cita medica
     * @return 
     */
    public String getComentarios() {
        return comentarios;
    }
    /**
     * edita los comentarios de la cita medica
     * @param comentarios 
     */
    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }
    /**
     * Retorna el paciente de la cita medica
     * @return 
     */
    public PacienteEntity getPacienteAAtender() {
        return pacienteAAtender;
    }
    /**
     * Edita el paciente de la cita medica
     * @param pacienteAAtender 
     */
    public void setPacienteAAtender(PacienteEntity pacienteAAtender) {
        this.pacienteAAtender = pacienteAAtender;
    }
    /**
     * retorna el horario de atencion de la cita medica
     * @return 
     */
    public HorarioAtencionEntity getHorarioAtencionAsignado() {
        return horarioAtencionAsignado;
    }
    /**
     * Edita el horario de atencion de la cita medica
     * @param horarioAtencionAsignado 
     */
    public void setHorarioAtencionAsignado(HorarioAtencionEntity horarioAtencionAsignado) {
        this.horarioAtencionAsignado = horarioAtencionAsignado;
    }
    /**
     * Retorna el id de la cita medica
     * @return 
     */
    public Long getId() {
        return id;
    }
    /**
     * Edita el id de la cita medica
     * @param id 
     */
    public void setId(Long id) {
        this.id = id;
    }
    
    
    
}
