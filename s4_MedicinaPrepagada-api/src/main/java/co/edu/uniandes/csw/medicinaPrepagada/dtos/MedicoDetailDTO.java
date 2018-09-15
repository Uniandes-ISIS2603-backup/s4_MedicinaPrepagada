/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.medicinaPrepagada.dtos;

import co.edu.uniandes.csw.medicinaPrepagada.entities.ConsultorioEntity;
import co.edu.uniandes.csw.medicinaPrepagada.entities.HorarioAtencionEntity;
import co.edu.uniandes.csw.medicinaPrepagada.entities.MedicoEntity;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 *
 * @author estudiante
 */
public class MedicoDetailDTO extends MedicoDTO implements Serializable{
    
    private List<HorarioAtencionDTO> horariosAtencion;
    
    public MedicoDetailDTO (){
        super();
    }
    
    public MedicoDetailDTO (MedicoEntity pMedicoEntity) 
   {
       super(pMedicoEntity);
       if (pMedicoEntity != null)
       {
           horariosAtencion = new ArrayList<>();
           for(HorarioAtencionEntity entityHorariosAtencion : pMedicoEntity.getHorariosAtencion())
           {
               horariosAtencion.add(new HorarioAtencionDTO(entityHorariosAtencion));
           }
       }
   } 
    
         /**
     * Convierte un objeto SedeDTO a MedicoEntity.
     *
     * @return Nueva objeto MedicoEntity.
     *
     */
    @Override
    public MedicoEntity toEntity() 
    {
        MedicoEntity medicoEntity = super.toEntity();
        
        if (horariosAtencion != null)
        {
            List<HorarioAtencionEntity> horariosAtencionEntity = new ArrayList<>();
            for(HorarioAtencionDTO dtoHorarioAtencion : horariosAtencion)
            {
                horariosAtencionEntity.add(dtoHorarioAtencion.toEntity());
            }
            medicoEntity.setHorariosAtencion(horariosAtencionEntity);
        }
        return medicoEntity;
    }
    
     @Override
    public String toString(){
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

    /**
     * @return the horariosAtencion
     */
    public List<HorarioAtencionDTO> getHorariosReservados() {
        return horariosAtencion;
    }

    /**
     * @param horariosAtencion the horariosAtencion to set
     */
    public void setHorariosReservados(List<HorarioAtencionDTO> horariosAtencion) {
        this.horariosAtencion = horariosAtencion;
    }
}
