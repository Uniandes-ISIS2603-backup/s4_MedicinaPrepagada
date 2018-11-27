/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.medicinaPrepagada.dtos;

import co.edu.uniandes.csw.medicinaPrepagada.entities.CitaLaboratorioEntity;
import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 *
 * @author Santiago Rojas
 */
public class CitaLaboratorioDTO implements Serializable {
    
    /**
     * Id de la cita
     */
    private Long id;
    /**
     * fecha de la cita
     */
    private Date fecha;
    /**
     * especialidad de la cita
     */
    private String especialidad;
    /**
     * comentarios de la cita
     */
    private String comentarios;
    /**
     *Recomendaciones para la cita
     */
    private String recomendaciones;
    /**
     * El paciente que pidio la cita
     */
    private PacienteDTO paciente;
    /**
     * Laboratorio donde la cita se llevara acabo
     */
    private LaboratorioDTO laboratorio;
    
    // Constructor de clase vacio
    public CitaLaboratorioDTO ()
    {
        
    }
     /**
     * Crea un objeto CitaLaboratorioDTO a partir de un objeto CitaLaboratorioEntity.
     *
     * @param pCitaLaboratorioEntity CitaLaboratorioEntity desde la cual se va a crear el
     * nuevo DTO.
     *
     */ 
    public CitaLaboratorioDTO(CitaLaboratorioEntity pCitaLaboratorioEntity) {
        if (pCitaLaboratorioEntity != null) {
            this.id = pCitaLaboratorioEntity.getId();
            this.comentarios = pCitaLaboratorioEntity.getComentarios();
            this.especialidad= pCitaLaboratorioEntity.getEspecialidad();
            this.fecha=pCitaLaboratorioEntity.getFecha();
            this.recomendaciones=pCitaLaboratorioEntity.getRecomendaciones();
            if(pCitaLaboratorioEntity.getPaciente() != null){
                this.paciente = new PacienteDTO(pCitaLaboratorioEntity.getPaciente());
            }
            if(pCitaLaboratorioEntity.getLaboratorio() != null){
                this.laboratorio = new LaboratorioDTO(pCitaLaboratorioEntity.getLaboratorio());
            }
        }

    }
    /**
     * Convierte un objeto CitaLaboratorioDTO a CitaLaboratorioEntity.
     *
     * @return Nueva objeto CitaLaboratorioEntity.
     *
     */
    public CitaLaboratorioEntity toEntity() 
    {
        CitaLaboratorioEntity citaLabEntity = new CitaLaboratorioEntity();

        citaLabEntity.setId(this.getId());
        citaLabEntity.setComentarios(this.comentarios);
        citaLabEntity.setFecha(this.fecha);
        citaLabEntity.setRecomendaciones(this.recomendaciones);
        citaLabEntity.setEspecialidad(this.especialidad);
        if(this.paciente != null){
            citaLabEntity.setPaciente(this.paciente.toEntity());
        }
        if(this.laboratorio != null){
           citaLabEntity.setLaboratorio(this.laboratorio.toEntity()); 
        }

        return citaLabEntity;
    }
    
    /**
     * Asigna el id de la cita
     * @param pId 
     */
    public void setId (Long pId)
    {
        this.id = pId;
    }
    /**
     * Recibe el id de la cita
     * @return citaId
     */
    public Long getId ()
    {
        return this.id;
    }
    /**
     * Asigna la fecha de la cita
     * @param pFecha 
     */
    public void setFecha (Date pFecha)
    {
        this.fecha = pFecha;
    }
    /**
     * Recibe la fecha de la cita
     * @return fecha
     */
    public Date getFecha ()
    {
         return this.fecha;
    }
    /**
     * Asigna la especialidad de la fecha
     * @param pEspecialidad 
     */
    public void setEspecialidad (String pEspecialidad)
    {
        this.especialidad = pEspecialidad;
    }
    /**
     * Recibe la especialidad de la cita
     * @return especialidad
     */
    public String getEspecialidad ()
    {
        return this.especialidad;
    }
    /**
     * Asigna los comentarios de la cita
     * @param pComentarios 
     */
    public void setComentarios (String pComentarios)
    {
        this.comentarios = pComentarios;
    }
    /**
     * Recibe los comentarios de la cita
     * @return comentarios
     */
    
    public String getComentarios ()
    {
        return this.comentarios;
    }
    /**
     * Asigna las recomendaciones de la cita
     * @param pRecomendaciones 
     */
    public void setRecomendaciones (String pRecomendaciones)
    {
        this.recomendaciones = pRecomendaciones;
    }
    /**
     * Recibe las recomendaciones de la cita
     * @return recomendaciones
     */
    public String getRecomendaciones ()
    {
        return this.recomendaciones;
    }
    /**
     * Asigna el paciente de la cita 
     * @param pPaciente 
     */
    public void setPaciente(PacienteDTO pPaciente)
    {
        this.paciente=pPaciente;
    }
    /**
     * Recibe el paciente de la cita
     * @return paciente
     */
    public PacienteDTO getPaciente()
    {
        return this.paciente;
    }
    /**
     * Asigna el laboratorio donde se va a llevar a cabo la cita
     * @param pLab 
     */
    public void setLaboratorio(LaboratorioDTO pLab)
    {
        this.laboratorio=pLab;
    }
    /**
     * Recibe el laboratorio donde se llevarà a cabo la cita
     * @return laboratorio
     */
    public LaboratorioDTO getLaboratorio()
    {
        return this.laboratorio;
    }
    
      @Override
    public String toString() 
    {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
    
    
    
    
}
