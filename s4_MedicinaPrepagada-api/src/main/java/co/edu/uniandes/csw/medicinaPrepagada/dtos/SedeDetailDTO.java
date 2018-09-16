/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.medicinaPrepagada.dtos;

import co.edu.uniandes.csw.medicinaPrepagada.entities.ConsultorioEntity;
import co.edu.uniandes.csw.medicinaPrepagada.entities.SedeEntity;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 *
 * @author estudiante
 */
public class SedeDetailDTO extends SedeDTO implements Serializable
{
    
    /**
     * Lista de consultoriosDTO de la sedeDetailDTO
     */
    private List<ConsultorioDTO> consultorios;
    
    
    /**
     * Constructor vacio 
     */
    public SedeDetailDTO ()
    {
        super();
    }
    
     /**
     * Crea un objeto SedeDetailDTO a partir de un objeto SedeEntity.
     *
     * @param pSedeEntity Entidad SedeEntity desde la cual se va a crear el
     * nuevo DTO.
     *
     */
    
    public SedeDetailDTO (SedeEntity pSedeEntity) 
   {
       super(pSedeEntity);
   
       if (pSedeEntity != null)
       {
           consultorios = new ArrayList<>();
           for (ConsultorioEntity entityConsultorios : pSedeEntity.getConsultorios())
           {
               consultorios.add(new ConsultorioDTO(entityConsultorios));
           }
       }
   } 
    
         /**
     * Convierte un objeto SedeDTO a SedeEntity.
     *
     * @return Nueva objeto SedeEntity.
     *
     */
    @Override
    public SedeEntity toEntity() 
    {
        SedeEntity sedeEntity = super.toEntity();
        
        if (consultorios != null)
        {
            List <ConsultorioEntity> consultoriosEntity = new ArrayList<>();
            for (ConsultorioDTO dtoConsultorio : consultorios)
            {
                consultoriosEntity.add(dtoConsultorio.toEntity());
            }
            sedeEntity.setConsultorios(consultoriosEntity);
        }
        return sedeEntity;
    }
    
    
    /**
     * Recibe la lista de consultorioDTO de la sedeDetailDTO
     * @return 
     */
    public List<ConsultorioDTO> getConsultorios ()
    {
        return this.consultorios;
    }
    /**
     * ASigna la lista de consultorioDTO a la sedeDetailDTO
     * @param pConsultorios 
     */
    public void setConsultorios (List<ConsultorioDTO> pConsultorios)
    {
        this.consultorios = pConsultorios;
    }
    
    
    /**
     * Convierte la sedeDetailDTO en un string
     * @return 
     */
     @Override
    public String toString() 
    {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
