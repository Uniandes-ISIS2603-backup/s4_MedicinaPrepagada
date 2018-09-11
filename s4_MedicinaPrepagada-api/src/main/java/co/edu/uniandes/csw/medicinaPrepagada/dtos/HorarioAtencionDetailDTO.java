/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.medicinaPrepagada.dtos;

import co.edu.uniandes.csw.medicinaPrepagada.entities.HorarioAtencionEntity;
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
    
    
     /**
     * Crea un objeto HorarioAtencionDetailDTO a partir de un objeto HorarioAtencionEntity.
     *
     * @param pHorarioAtencionEntity Entidad HorarioAtencionEntity desde la cual se va a crear el
     * nuevo DTO.
     *
     */
    
    public HorarioAtencionDetailDTO (HorarioAtencionEntity pHorarioAtencionEntity) 
   {
       super(pHorarioAtencionEntity);
   
       if (pHorarioAtencionEntity != null)
       {
          // completar
       }
   } 
    
         /**
     * Convierte un objeto HorarioAtencionDTO a HorarioAtencionEntity.
     *
     * @return Nueva objeto HorarioAtencionEntity.
     *
     */
    @Override
    public HorarioAtencionEntity toEntity() 
    {
        HorarioAtencionEntity horarioAtencionEntity = super.toEntity();

        //COmpletar
        return horarioAtencionEntity;
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
