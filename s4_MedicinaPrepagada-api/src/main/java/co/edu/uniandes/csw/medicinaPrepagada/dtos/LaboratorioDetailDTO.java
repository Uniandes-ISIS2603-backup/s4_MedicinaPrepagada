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
public class LaboratorioDetailDTO extends LaboratorioDTO implements Serializable
{
    public LaboratorioDetailDTO()
    {
        super();
    }
    
    private List<LaboratorioDTO> citasLaboratorio;
    
         @Override
    public String toString(){
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
    
    public List <LaboratorioDTO> getCitasLab()
    {
        return this.citasLaboratorio;
    }
    
    public void setCitas (List<LaboratorioDTO> pCitas)
    {
        this.citasLaboratorio = pCitas;
    }
    
}
