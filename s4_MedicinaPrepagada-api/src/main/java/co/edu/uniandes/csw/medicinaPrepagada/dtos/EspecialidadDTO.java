/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.medicinaPrepagada.dtos;

import co.edu.uniandes.csw.medicinaPrepagada.entities.EspecialidadEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 *
 * @author estudiante
 */
public class EspecialidadDTO {
    private String nombre;
    
    public EspecialidadDTO(){
        
    }

    public EspecialidadDTO (EspecialidadEntity especialidadEntity){
        if (especialidadEntity != null){
           this.nombre = especialidadEntity.getNombre();
        }   
    }
     
     
     /**
     * Convierte un objeto ConsultorioDTO a EspecialidadEntity.
     *
     * @return Nueva objeto EspecialidadEntity.
     *
     */
    public EspecialidadEntity toEntity() 
    {
        EspecialidadEntity especialidadEntity = new EspecialidadEntity();
        
        especialidadEntity.setNombre(this.getNombre());
 
        return especialidadEntity;
    }
    
    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
            
}
