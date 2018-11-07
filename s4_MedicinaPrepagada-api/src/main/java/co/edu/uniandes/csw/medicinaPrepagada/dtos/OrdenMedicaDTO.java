/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.medicinaPrepagada.dtos;

import co.edu.uniandes.csw.medicinaPrepagada.entities.OrdenMedicaEntity;
import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 *
 * @author ni.ramirez10
 */

public class OrdenMedicaDTO implements Serializable 
{
    private Long id; 
    private String firmaMedico; 
    private String fechaExpedicion; 
    private String comentarios; 
    private String validaHasta; 
    
    /**
     * Constructor vacio
     */
    
    public OrdenMedicaDTO()
    {
        //Constrcutor vacio
    }
    
    /**
     * Crea un objeto OrdenMedicaDTO a partir de un objeto OrdenMedicaEntity.
     * @param ordenMedicaEntity Entidad OrdenMedicaEntity desde la cual se va a crear el
     * nuevo objeto.
     */
    
    public OrdenMedicaDTO(OrdenMedicaEntity ordenMedicaEntity) 
    {
        
        if (ordenMedicaEntity != null) 
        {
            this.id = ordenMedicaEntity.getId();
            this.firmaMedico = ordenMedicaEntity.getFirmaMedico();
            this.fechaExpedicion = ordenMedicaEntity.getFechaExpedicion();
            this.comentarios = ordenMedicaEntity.getComentarios();
            this.validaHasta = ordenMedicaEntity.getValidaHasta();
        }
    }

    /**
     * Convierte un objeto OrdenMedicaDTO a OrdenMedicaEntity.
     * @return Nueva objeto OrdenMedicaEntity.
     */
    
    public OrdenMedicaEntity toEntity() 
    {
        OrdenMedicaEntity ordenMedicaEntity = new OrdenMedicaEntity();
        
        ordenMedicaEntity.setId(this.getId());
        ordenMedicaEntity.setFirmaMedico(this.getFirmaMedico());
        ordenMedicaEntity.setFechaExpedicion(this.getFechaExpedicion());
        ordenMedicaEntity.setComentarios(this.getComentarios());
        ordenMedicaEntity.setValidaHasta(this.getValidaHasta());
        
        return ordenMedicaEntity;
    }
    
    /**
     * Obtiene el atributo id.
     * @return atributo id.
     */
    
    public Long getId() 
    {
        return id;
    }

    /**
     * Establece el valor del atributo id.
     * @param id nuevo valor del atributo
     */
    
    public void setId(Long id) 
    {
        this.id = id;
    }

    /**
     * Obtiene el atributo firmaMedico.
     * @return atributo firmaMedico.
     */
    
    public String getFirmaMedico() 
    {
        return firmaMedico;
    }

    /**
     * Establece el valor del atributo firmaMedico.
     * @param pfirmaMedico nuevo valor del atributo
     */
    
    public void setFirmaMedico(String pfirmaMedico) 
    {
        this.firmaMedico = pfirmaMedico;
    }

    /**
     * Obtiene el atributo fechaExpedicion.
     * @return atributo fechaExpedicion.
     */
    
    public String getFechaExpedicion() 
    {
        return fechaExpedicion;
    }

    /**
     * Establece el valor del atributo fechaExpedicion.
     * @param pFechaExpedicion nuevo valor del atributo
     */
    
    public void setFechaExpedicion(String pFechaExpedicion) 
    {
        this.fechaExpedicion = pFechaExpedicion;
    }

    /**
     * Obtiene el atributo comentarios.
     * @return Los comentarios
     */
    
    public String getComentarios() 
    {
        return comentarios;
    }
    
     /**
     * Establece el atributo de comentarios.
     * @param pComentarios atributo a implementar
     */
    
    public void setComentarios(String pComentarios) 
    {
        this.comentarios = pComentarios;
    }

    /**
     * Ontiene el atributo de validaHasta
     * @return El validaHasta
     */
    
    public String getValidaHasta() 
    {
        return validaHasta;
    }   

    /**
     * Establece el atributo validaHasta
     * @param pValidaHasta atributo a implemnetar
     */
    
    public void setValidaHasta(String pValidaHasta) 
    {
        this.validaHasta = pValidaHasta;
    }
    
    @Override
    public String toString() 
    {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
