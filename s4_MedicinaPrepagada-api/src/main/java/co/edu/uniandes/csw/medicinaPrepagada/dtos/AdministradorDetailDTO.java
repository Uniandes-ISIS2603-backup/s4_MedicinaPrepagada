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
 * @author ni.ramirez10
 */
public class AdministradorDetailDTO extends AdministradorDTO implements Serializable 
{
    
    private Long id; 
    
    /**
     * constructor por defecto
     */
    
    public AdministradorDetailDTO()
    {
        super();
    }
    
    /**
     * Obtiene el atributo id.
     * @return atributo id.
     */
    
    public Long getId() 
    {
        return id;
    }

    /**
     * Establece el valor del atributo id.
     * @param id nuevo valor del atributo
     */
    
    public void setId(Long id) 
    {
        this.id = id;
    }
    
     @Override
    public String toString(){
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
