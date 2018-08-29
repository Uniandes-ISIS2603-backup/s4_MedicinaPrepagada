/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.medicinaPrepagada.dtos;

/**
 *
 * @author ncobos
 */
import java.io.Serializable;
import java.util.List;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;


public class MedicamentoDetailDTO extends MedicamentoDTO implements Serializable
{
    
    private List<FarmaciaDTO> farmacias;
    
    
    
    
    public MedicamentoDetailDTO() 
    {
        super();
    }
    
    
    public void setFarmacias(List<FarmaciaDTO> pFarmacias)
    {
        this.farmacias = pFarmacias;
    }
    
    
    public List<FarmaciaDTO> getFarmacias ()
    {
        return this.farmacias;
    }
    
    
        @Override
    public String toString() 
    {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }  
}
