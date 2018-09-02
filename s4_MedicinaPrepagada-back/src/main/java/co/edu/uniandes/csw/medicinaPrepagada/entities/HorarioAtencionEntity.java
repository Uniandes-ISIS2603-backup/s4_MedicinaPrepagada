/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.medicinaPrepagada.entities;

import co.edu.uniandes.csw.medicinaPrepagada.podam.DateStrategy;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import uk.co.jemos.podam.common.PodamExclude;
import uk.co.jemos.podam.common.PodamStrategyValue;

/**
 *
 * @author Simon Guzman
 */
public class HorarioAtencionEntity implements Serializable
{
    
    
     @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Temporal(TemporalType.DATE)
    @PodamStrategyValue(DateStrategy.class)
    private Date fechaInicio;
    
    
     @Temporal(TemporalType.DATE)
    @PodamStrategyValue(DateStrategy.class)
    private Date fechaFin;
     
     
    @PodamExclude
    @ManyToOne
    private ConsultorioEntity consultorio;
     
    @PodamExclude
    @ManyToOne
    private MedicoEntity medico;
      
    @PodamExclude
    @OneToMany(mappedBy = "horarioAtencionAsignado",fetch=FetchType.LAZY)
    private List<CitaMedicaEntity> citasMedicas = new ArrayList<>();
     
     
    public Long getId() 
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    
    
     public void setFechaInicio (Date pFechaInicio)
   {
       this.fechaInicio = pFechaInicio;
   }
   
   public Date getFechaInicio  ()
   {
        return this.fechaInicio;
   }
   
   public void setFechaFin (Date pFechaFin)
   {
       this.fechaFin = pFechaFin;
   }
   
   public Date getFechaFin  ()
   {
        return this.fechaFin;
   }
   
   public void setConsultorio (ConsultorioEntity pConsultorio)
   {
       this.consultorio = pConsultorio;
   }
   
   public ConsultorioEntity getConsultorio ()
   {
       return this.consultorio;
   }
   
      
   public void setMedico (MedicoEntity pMedico)
   {
       this.medico = pMedico;
   }
   
   public MedicoEntity getMedico ()
   {
       return this.medico;
   }
    
    
    
    public void setCitasMedicas(List<CitaMedicaEntity> pCitasMedicas)
    {
        this.citasMedicas = pCitasMedicas;
    }
    
    
    public List<CitaMedicaEntity> getCitasMedicas ()
    {
        return this.citasMedicas;
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
        final HorarioAtencionEntity other = (HorarioAtencionEntity) obj;
        return Objects.equals(this.id, other.id);
    }

    @Override
    public int hashCode() {
        if (this.getId() != null) {
            return this.getId().hashCode();
        }
        return super.hashCode();
    }
    
    
}
