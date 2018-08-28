/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.medicinaPrepagada.dtos;

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
    
    
       
   public void setId (long pId)
   {
       this.id = pId;
   }
   
   public Long getId  ()
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
