/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.medicinaPrepagada.dtos;

import co.edu.uniandes.csw.medicinaPrepagada.entities.ConsultorioEntity;
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
    
    
    private Integer nOficina;
    
    private SedeDTO sede;
    
    private EspecialidadDTO especialidad;
    
    
    public ConsultorioDTO ( )
    {
        
    }
    
            /**
     * Crea un objeto ConsultorioDTO a partir de un objeto ConsultorioEntity.
     *
     * @param pConsultorioEntity Entidad ConsultorioEntity desde la cual se va a crear el
     * nuevo DTO.
     *
     */   
     public ConsultorioDTO (ConsultorioEntity pConsultorioEntity) 
   {
       if (pConsultorioEntity != null)
       {
           this.id=pConsultorioEntity.getId();
           this.edificio= pConsultorioEntity.getEdificio();
           this.nOficina = pConsultorioEntity.getNOficina();
           this.especialidad = new EspecialidadDTO(pConsultorioEntity.getEspecialidad());
           this.sede = new SedeDTO (pConsultorioEntity.getSede());
           
       }
       
   }
     
     
     /**
     * Convierte un objeto ConsultorioDTO a ConsultorioEntity.
     *
     * @return Nueva objeto ConsultorioEntity.
     *
     */
    public ConsultorioEntity toEntity() 
    {
        ConsultorioEntity consultorioEntity = new ConsultorioEntity();
        
        consultorioEntity.setId(this.id);
        consultorioEntity.setEdificio(this.edificio);
        consultorioEntity.setNOficina(this.nOficina);
        consultorioEntity.setSede(this.sede.toEntity());
        if (this.especialidad != null)
        consultorioEntity.setEspecialidad(this.especialidad.toEntity());
 
        return consultorioEntity;
    }
    
    
       
   public void setId (long pId)
   {
       this.id = pId;
   }
   
   public long getId  ()
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
    
   
   
   
      public void setSede (SedeDTO pSede)
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
