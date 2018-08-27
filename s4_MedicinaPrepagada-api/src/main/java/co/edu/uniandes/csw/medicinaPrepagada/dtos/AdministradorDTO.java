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
 * @author ni.ramirez10
 */
public class AdministradorDTO implements Serializable 
{
    /**
     * Constructor vacio
     */
    
    public AdministradorDTO()
    {
        //Constrcutor vacio
    }
    
    /**
     * Crea un objeto AdministradorDTO a partir de un objeto AdministradorEntity.
     * @param administradorEntity Entidad AdministradorEntity desde la cual se va a crear el
     * nuevo objeto.
     */
    
    /**public AdministradorDTO(AdministradorEntity administradorEntity) 
    {
        if (ordenMedicaEntity != null) 
        {
            
        }
    }
     
    /**
    * Convierte un objeto AdministradorDTO a AdministradorEntity.
    * @return Nueva objeto AdministradorEntity.
    */
    
    /**public AdministradorEntity toEntity() 
    {
        AdministradorEntity administradorEntity = new AdministradorEntity();
        return administradorEntity;
    }
    * */
    
    
    @Override
    public String toString() 
    {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
