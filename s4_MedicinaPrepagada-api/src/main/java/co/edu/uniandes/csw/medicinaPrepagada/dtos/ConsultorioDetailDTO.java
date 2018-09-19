/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.medicinaPrepagada.dtos;

import co.edu.uniandes.csw.medicinaPrepagada.entities.ConsultorioEntity;
import co.edu.uniandes.csw.medicinaPrepagada.entities.HorarioAtencionEntity;
import java.io.Serializable;
import java.util.ArrayList;
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
    
    /**
     * Crea un objeto ConsultorioDetailDTO a partir de un objeto ConsultorioEntity.
     *
     * @param pConsultorioEntity Entidad ConsultorioEntity desde la cual se va a crear el
     * nuevo DTO.
     *
     */
    
    public ConsultorioDetailDTO (ConsultorioEntity pConsultorioEntity) 
   {
       super(pConsultorioEntity);
   
       if (pConsultorioEntity != null)
       {
          horariosAtencion = new ArrayList<>();
          for (HorarioAtencionEntity entityHorario : pConsultorioEntity.getHorariosAtencion())
          {
              horariosAtencion.add(new HorarioAtencionDTO(entityHorario));
          }
       }
   } 
    
     /**
     * Convierte un objeto ConsultorioDTO a ConsultorioEntity.
     *
     * @return Nueva objeto ConsultorioEntity.
     *
     */
    @Override
    public ConsultorioEntity toEntity() 
    {
        ConsultorioEntity consultorioEntity = super.toEntity();

        if (horariosAtencion != null)
        {
            List <HorarioAtencionEntity> horariosEntity = new ArrayList<>();
            for (HorarioAtencionDTO dtoHorario : horariosAtencion)
            {
                horariosEntity.add(dtoHorario.toEntity());
            }
            consultorioEntity.setHorariosAtencion(horariosEntity);
        }
        return consultorioEntity;
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
