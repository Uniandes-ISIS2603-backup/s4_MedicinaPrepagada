/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.medicinaPrepagada.dtos;

import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 *
 * @author estudiante
 */
public class CitaLaboratorioDTO implements Serializable {
    
    private Long id;
    
    private Date fecha;
    
    private String especialidad;
    
    private String comentarios;
    
    private String recomendaciones;
    
    // Constructor de clase vacio
    public CitaLaboratorioDTO ()
    {
        
    }
    
    public void setId (Long pId)
    {
        this.id = pId;
    }
    
    public Long getId ()
    {
        return this.id;
    }
    
    public void setDate (Date pFecha)
    {
        this.fecha = pFecha;
    }
            
    public Date getDate ()
    {
         return this.fecha;
    }
    
    public void setEspecialidad (String pEspecialidad)
    {
        this.especialidad = pEspecialidad;
    }
    
    public String getEspecialidad ()
    {
        return this.especialidad;
    }
    
    public void setComentarios (String pComentarios)
    {
        this.comentarios = pComentarios;
    }
    
    public String getComentarios ()
    {
        return this.comentarios;
    }
    
    public void setRecomendaciones (String pRecomendaciones)
    {
        this.recomendaciones = pRecomendaciones;
    }
    
    public String getRecomendaciones ()
    {
        return this.recomendaciones;
    }
    
      @Override
    public String toString() 
    {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
    
    
    
    
}
