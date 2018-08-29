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


public class ExamenMedicoDetailDTO extends ExamenMedicoDTO implements Serializable
{
    
    private List<LaboratorioDTO> laboratorios;
    
    
    
    
    public ExamenMedicoDetailDTO() 
    {
        super();
    }
    
    
    public void setLaboratorios(List<LaboratorioDTO> pLabs)
    {
        this.laboratorios = pLabs;
    }
    
    
    public List<LaboratorioDTO> getLaboratorios ()
    {
        return this.laboratorios;
    }
    
    
        @Override
    public String toString() 
    {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }  
}
