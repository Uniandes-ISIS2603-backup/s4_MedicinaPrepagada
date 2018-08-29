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
 * @author estudiante
 */
public class SedeDetailDTO extends SedeDTO implements Serializable
{
    
    
    private List<ConsultorioDTO> consultorios;
    
    
    
    public SedeDetailDTO ()
    {
        super();
    }
    
    
    
    public List<ConsultorioDTO> getConsultorios ()
    {
        return this.consultorios;
    }
    
    public void setConsultorios (List<ConsultorioDTO> pConsultorios)
    {
        this.consultorios = pConsultorios;
    }
    
    
    
     @Override
    public String toString() 
    {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
