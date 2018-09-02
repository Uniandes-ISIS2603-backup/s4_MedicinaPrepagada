/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.medicinaPrepagada.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import uk.co.jemos.podam.common.PodamExclude;

/**
 *
 * @author Simon Guzman
 */
public class ConsultorioEntity implements Serializable
{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    
    @PodamExclude
    @OneToMany(mappedBy = "consultorio",fetch=FetchType.LAZY)
    private List<HorarioAtencionEntity> horariosAtencion = new ArrayList<>();
     
    @PodamExclude
    @ManyToOne
    private SedeEntity sede;
     
    @PodamExclude
    @ManyToOne
    private EspecialidadEntity especialidad; 
     
    private String edificio;
    
    
    private int nOficina;
     
     
     
     
     
     
     
    public Long getId() 
    {
        return id;
    }

    public void setId(Long id) 
    {
        this.id = id;
    }

    
    
   public void setEdificio (String pEdificio)
   {
       this.edificio = pEdificio;
   }
   
   public String getEdificio  ()
   {
       return this.edificio;
   }
   
   
    public void setNOficina (int pNOficina)
   {
       this.nOficina=pNOficina;
   }
   
   public int getNOficina  ()
   {
       return this.nOficina;
   }
    
    public SedeEntity getSede()   
    {
        return this.sede;
    }

    public void setSede(SedeEntity pSede) 
    {
        this.sede = pSede;
    }
    
    
       
   public void setEspecialidad (EspecialidadEntity pEspecialidad)
   {
       this.especialidad = pEspecialidad;
   }
   
   public EspecialidadEntity getEspecialidad ()
   {
       return this.especialidad;
   }
    
    
    public List<HorarioAtencionEntity> getHorariosAtencion ()
    {
        return this.horariosAtencion;
    }
    
    public void setHorariosAtencion (List<HorarioAtencionEntity> pHorariosAtencion)
    {
        this.horariosAtencion = pHorariosAtencion;
    }
    
    
    @Override
    public boolean equals(Object obj) 
    {
        if (this == obj) 
        {
            return true;
        }
        if (obj == null) 
        {
            return false;
        }
        if (getClass() != obj.getClass()) 
        {
            return false;
        }
        final ConsultorioEntity other = (ConsultorioEntity) obj;
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
