/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.medicinaPrepagada.dtos;

import java.io.Serializable;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 *
 * @author Simon Guzman
 */
public class ConsultorioDTO  implements Serializable
{
   
    private Long id;
    
    
    private String edificio;
    
    
    private int nOficina;
    
    private SedeDTO sede;
    
    private EspecialidadDTO especialidad;
    
    
    public ConsultorioDTO ( )
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
    
   
   
   
      public void getSede (SedeDTO pSede)
   {
       this.sede = pSede;
   }
   
   public SedeDTO getSede ()
   {
       return this.sede;
   }
   
   
   public void setEspecialidad (EspecialidadDTO pEspecialidad)
   {
       this.especialidad = pEspecialidad;
   }
   
   public EspecialidadDTO getEspecialidad ()
   {
       return this.especialidad;
   }
   
   
    @Override
    public String toString() 
    {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}