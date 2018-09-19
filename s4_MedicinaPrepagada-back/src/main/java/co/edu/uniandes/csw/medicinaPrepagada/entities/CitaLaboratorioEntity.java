/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.medicinaPrepagada.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import uk.co.jemos.podam.common.PodamExclude;

/**
 *
 * @author Santiago Rojas
 */
@Entity
public class CitaLaboratorioEntity implements Serializable
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @PodamExclude
    @OneToOne 
   
    private LaboratorioEntity laboratorio;
    
    @PodamExclude
    @ManyToOne
    private PacienteEntity paciente;
    
    
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fecha;
    
    private String especialidad;
    private String comentarios;
    private String recomendaciones;
    
    
    public void setPaciente (PacienteEntity pPaciente)
    {
        this.paciente = pPaciente;
    }
    
    public PacienteEntity getPaciente ()
    {
        return this.paciente;
    }
    
    
    public void setId (Long pId)
    {
        this.id= pId;
    }
    
    public Long getId ()
    {
        return this.id;
    }
    
     public void setLaboratorio (LaboratorioEntity pLab)
    {
        this.laboratorio = pLab;
    }
    public LaboratorioEntity getLaboratorio ()
    {
        return this.laboratorio;
    }
    
   
    
    public void setDate (Date pDate)
    {
        this.fecha = pDate;
    }
    
    public Date getDate()
    {
        return this.fecha;
    }
    
     public void setEspecialidad (String pEspecialidad)
    {
        this.especialidad = pEspecialidad;
    }
    
    public String getEspecialidad()
    {
        return this.especialidad;
    }
    
     public void setComentarios (String pComentarios)
    {
        this.comentarios = pComentarios;
    }
    
    public String getComentarios()
    {
        return this.comentarios;
    }
    
    public void setRecomendaciones (String pRecomendaciones)
    {
        this.recomendaciones = pRecomendaciones;
    }
    
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
