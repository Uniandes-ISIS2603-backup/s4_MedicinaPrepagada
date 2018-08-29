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
public class HorarioAtencionDetailDTO extends HorarioAtencionDTO implements Serializable
{

    
    
    
    private List<CitaMedicaDTO> citasMedicas;
    
    
    
    
    public HorarioAtencionDetailDTO() 
    {
        super();
    }
    
    
    public void setCitasMedicas(List<CitaMedicaDTO> pCitasMedicas)
    {
        this.citasMedicas = pCitasMedicas;
    }
    
    
    public List<CitaMedicaDTO> getCitasMedicas ()
    {
        return this.citasMedicas;
    }
    
    
        @Override
    public String toString() 
    {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
    
}
