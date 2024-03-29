/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.medicinaPrepagada.dtos;

import co.edu.uniandes.csw.medicinaPrepagada.entities.CitaLaboratorioEntity;
import co.edu.uniandes.csw.medicinaPrepagada.entities.LaboratorioEntity;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 *
 * @author estudiante
 */
public class LaboratorioDetailDTO extends LaboratorioDTO implements Serializable
{
    
    private List<CitaLaboratorioDTO> citasLab;
    public LaboratorioDetailDTO()
    {
        super();
    }
    
    
    public LaboratorioDetailDTO (LaboratorioEntity pLaboratorioEntity) 
   {
       super(pLaboratorioEntity);
   
       if (pLaboratorioEntity != null)
       {
           citasLab = new ArrayList<>();
           
           for (CitaLaboratorioEntity entityLaboratorios : pLaboratorioEntity.getCitasLaboratorio())
           {
               citasLab.add(new CitaLaboratorioDTO(entityLaboratorios));
           }
       }
   } 
    
       
    @Override
    public LaboratorioEntity toEntity() 
    {
        LaboratorioEntity labEntity = super.toEntity();
        
        if (citasLab != null)
        {
            List <CitaLaboratorioEntity> citasEntity = new ArrayList<>();
            for (CitaLaboratorioDTO citasLabDto : citasLab)
            {
                citasEntity.add(citasLabDto.toEntity());
            }
            labEntity.setCitasLaboratorio(citasEntity);
        }
        
        
        
        return labEntity;
    }
    
    
    
    
         @Override
    public String toString(){
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
    
    public List <CitaLaboratorioDTO> getCitasLab()
    {
        return this.citasLab;
    }
    
    
    public void setCitas (List<CitaLaboratorioDTO> pCitas)
    {
        this.citasLab = pCitas;
    }
    
}
