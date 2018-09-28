/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.medicinaPrepagada.dtos;

import co.edu.uniandes.csw.medicinaPrepagada.entities.HorarioAtencionEntity;
import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 *
 * @author Simon guzman
 */
public class HorarioAtencionDTO implements Serializable
{
    
    private Long id;
    
    
    private Date fechaInicio;
    
    
    private Date fechaFin;
    
    private ConsultorioDTO consultorio;
    
    private MedicoDTO medico;
    
    
    
    public HorarioAtencionDTO ()
    {
        
    }
    
           /**
     * Crea un objeto HorarioAtencionDTO a partir de un objeto HorarioAtencionEntity.
     *
     * @param pHorarioAtencionEntity Entidad HorarioAtencionEntity desde la cual se va a crear el
     * nuevo DTO.
     *
     */
    
    public HorarioAtencionDTO (HorarioAtencionEntity pHorarioAtencionEntity) 
   {
   
       if (pHorarioAtencionEntity != null)
       {
          this.id =pHorarioAtencionEntity.getId();
          this.fechaInicio = pHorarioAtencionEntity.getFechaInicio();
          this.fechaFin = pHorarioAtencionEntity.getFechaFin();
          this.consultorio = new ConsultorioDTO(pHorarioAtencionEntity.getConsultorio());
          this.medico = new MedicoDTO(pHorarioAtencionEntity.getMedico());
       }
   } 
    
         /**
     * Convierte un objeto HorarioAtencionDTO a HorarioAtencionEntity.
     *
     * @return Nueva objeto HorarioAtencionEntity.
     *
     */
    public HorarioAtencionEntity toEntity() 
    {
        HorarioAtencionEntity horarioAtencionEntity = new HorarioAtencionEntity();

        horarioAtencionEntity.setId(this.id);
        horarioAtencionEntity.setFechaInicio(this.fechaInicio);
        horarioAtencionEntity.setFechaFin(this.fechaFin);
        if (this.consultorio != null)
        horarioAtencionEntity.setConsultorio(this.consultorio.toEntity());
        if (this.medico != null)
       horarioAtencionEntity.setMedico(this.medico.toEntity());
        return horarioAtencionEntity;
    }
    
    
       
   public void setId (long pId)
   {
       this.id = pId;
   }
   
   public long getId  ()
   {
        return this.id;
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
   
   public void setConsultorio (ConsultorioDTO pConsultorio)
   {
       this.consultorio = pConsultorio;
   }
   
   public ConsultorioDTO getConsultorio ()
   {
       return this.consultorio;
   }
   
   
   
   public void setMedico (MedicoDTO pMedico)
   {
       this.medico = pMedico;
   }
   
   public MedicoDTO getMedico ()
   {
       return this.medico;
   }
   
      
    @Override
    public String toString() 
    {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
    
}
