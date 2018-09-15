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
public class HistoriaClinicaDetailDTO extends HistoriaClinicaDTO implements Serializable  
{
     // relación  uno o muchos 
    private List<OrdenMedicaDTO> ordenesMedicas;
    
    public HistoriaClinicaDetailDTO() 
    {
        super();
    }
    
     /**
     * Obtiene la lista de ordenes medicas
     * @return 
     */
    
    public List<OrdenMedicaDTO> getOrdenesMedicas() 
    {
        return ordenesMedicas;
    }
    
    /**
     * Declara una la lista de ordenes medicas
     * @param pOrdenesMedicas
     */
    
    public void setOrdenesMedicas (List<OrdenMedicaDTO> pOrdenesMedicas)
    {
        this.ordenesMedicas = pOrdenesMedicas;
    }
    
    @Override
    public String toString() 
    {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
    
}
