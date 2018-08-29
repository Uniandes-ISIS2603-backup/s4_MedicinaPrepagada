/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.medicinaPrepagada.dtos;

import java.io.Serializable;
import java.util.List;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 *
 * @author simon Guzman
 */
public class ConsultorioDetailDTO extends ConsultorioDTO implements Serializable
{
    
    
    
    private List<HorarioAtencionDTO> horariosAtencion;
    
    
    
    public ConsultorioDetailDTO ()
    {
        super();
    }
    
    
  
    
    public List<HorarioAtencionDTO> getHorariosAtencion ()
    {
        return this.horariosAtencion;
    }
    
    public void setHorariosAtencion (List<HorarioAtencionDTO> pHorariosAtencion)
    {
        this.horariosAtencion = pHorariosAtencion;
    }
    
    @Override
    public String toString() 
    {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
