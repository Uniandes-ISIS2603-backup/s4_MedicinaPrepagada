/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.medicinaPrepagada.entities;

import java.util.Date;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import uk.co.jemos.podam.common.PodamExclude;

/**
 *
 * @author Santiago Rojas
 */
public class CitaLaboratorioEntity 
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @OneToOne //(mappedBy = "citaLaboratorio",fetch=FetchType.LAZY, cascade = CascadeType.PERSIST, orphanRemoval = true)
    private LaboratorioEntity laboratorio;
    
    @PodamExclude
    @ManyToOne
    private PacienteEntity paciente;
    
    private Date fecha;
    private String especialidad;
    private String comentarios;
    private String recomendaciones;
    
    
    public PacienteEntity getPaciente ()
    {
        return this.paciente;
    }
    public void setPaciente (PacienteEntity pPaciente)
    {
        this.paciente = pPaciente;
    }
    public Long getId ()
    {
        return this.id;
    }
    public void setId (Long pId)
    {
        this.id= pId;
    }
    
    public LaboratorioEntity getLaboratorio ()
    {
        return this.laboratorio;
    }
    
    public void setLaboratorio (LaboratorioEntity pLab)
    {
        this.laboratorio = pLab;
    }
    
    public Date getDate()
    {
        return this.fecha;
    }
    
    public void setDate (Date pDate)
    {
        this.fecha = pDate;
    }
    
    public String getEspecialidad()
    {
        return this.especialidad;
    }
    
    public void setEspecialidad (String pEspecialidad)
    {
        this.especialidad = pEspecialidad;
    }
    
    public String getComentarios()
    {
        return this.comentarios;
    }
    
    public void setComentarios (String pComentarios)
    {
        this.comentarios = pComentarios;
    }
    
    public String getRecomendaciones()
    {
        return this.recomendaciones;
    }
    
    public void setRecomendaciones (String pRecomendaciones)
    {
        this.recomendaciones = pRecomendaciones;
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
