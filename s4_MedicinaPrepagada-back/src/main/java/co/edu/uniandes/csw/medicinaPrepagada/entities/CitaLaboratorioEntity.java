/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.medicinaPrepagada.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import uk.co.jemos.podam.common.PodamExclude;

/**
 *
 * @author Santiago Rojas
 */
@Entity
public class CitaLaboratorioEntity  implements Serializable
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @PodamExclude
    @ManyToOne (cascade = CascadeType.PERSIST)
    private LaboratorioEntity laboratorio;
    
    @PodamExclude
    @ManyToOne (cascade = CascadeType.PERSIST)
    private PacienteEntity paciente;
    
    
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fecha;
    
    private String especialidad;
    private String comentarios;
    private String recomendaciones;
    
    
    /**
     *  Asigna el paciente a la cita
     * @param pPaciente 
     */
    public void setPaciente (PacienteEntity pPaciente)
    {
        this.paciente = pPaciente;
    }
    
    /**
     * Retorna el paciente de la cita
     * @return Paciente
     */
    public PacienteEntity getPaciente ()
    {
        return this.paciente;
    }
    
    /**
     * 
     * @param pId 
     */
    public void setId (Long pId)
    {
        this.id= pId;
    }
    
    /**
     * 
     * @return 
     */
    public Long getId ()
    {
        return this.id;
    }
    
    /**
     * 
     * @param pLab 
     */
    public void setLaboratorio (LaboratorioEntity pLab)
    {
        this.laboratorio = pLab;
    }
    
    /**
     * 
     * @return 
     */
    public LaboratorioEntity getLaboratorio ()
    {
        return this.laboratorio;
    }
    
    /**
     * 
     * @param pDate 
     */
    public void setFecha (Date pDate)
    {
        this.fecha = pDate;
    }
    
    /**
     * 
     * @return 
     */
    public Date getFecha()
    {
        return this.fecha;
    }
    
    /**
     * 
     * @param pEspecialidad 
     */
    public void setEspecialidad (String pEspecialidad)
    {
        this.especialidad = pEspecialidad;
    }
    
    /**
     * 
     * @return 
     */
    public String getEspecialidad()
    {
        return this.especialidad;
    }
    
    /**
     * 
     * @param pComentarios 
     */
    public void setComentarios (String pComentarios)
    {
        this.comentarios = pComentarios;
    }
    
    /**
     * 
     * @return 
     */
    public String getComentarios()
    {
        return this.comentarios;
    }
    
    /**
     * 
     * @param pRecomendaciones 
     */
    public void setRecomendaciones (String pRecomendaciones)
    {
        this.recomendaciones = pRecomendaciones;
    }
    
    /**
     * 
     * @return 
     */
    public String getRecomendaciones()
    {
        return this.recomendaciones;
    }
    
    
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final CitaLaboratorioEntity other = (CitaLaboratorioEntity) obj;
        return Objects.equals(this.id, other.id);
    }

    @Override
    public int hashCode() 
    {
        if (this.getId() != null) {
            return this.getId().hashCode();
        }
        return super.hashCode();
    }
}
