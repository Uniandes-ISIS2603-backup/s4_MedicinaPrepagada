/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.medicinaPrepagada.dtos;

import co.edu.uniandes.csw.medicinaPrepagada.entities.HorarioAtencionEntity;
import java.io.Serializable;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 *
 * @author Simon Guzman
 */
public class HorarioAtencionSedeDTO extends HorarioAtencionDTO implements Serializable 
{
    
  
    private SedeDTO sede;
    
    public HorarioAtencionSedeDTO ()
    {
        super();
    }
    
    public HorarioAtencionSedeDTO (HorarioAtencionEntity pHorEntity)
    {
        super(pHorEntity);
    }
  
    @Override
    public HorarioAtencionEntity toEntity()
    {
       return super.toEntity();
    }
       
   public void setSede (SedeDTO pSede)
   {
       this.sede = pSede;
   }
   
   public SedeDTO getSede ()
   {
       return this.sede;
   }
    
    @Override
    public String toString() 
    {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
    
}
