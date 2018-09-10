/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.medicinaPrepagada.dtos;

import java.io.Serializable;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 *
 * @author estudiante
 */
public class LaboratorioDTO implements Serializable
{
    
    private Long id;
    
    private String nombre;
    
    private int telefono;
    
    private String direccion;
    
    private String horarioDeAtencion;
    
    private double latitud;
    
    private double longitud;
    
    public LaboratorioDTO()
    {
        
    }
    public void setId(Long pId)
    {
        this.id = pId;
    }
    
    public Long getId ()
    {
        return this.id;
    }
    
    public void setNombre (String pNombre)
    {
        this.nombre = pNombre;
    }
    
    public String getNombre ()
    {
        return this.nombre;
    }
    
    public void setTelefono (int pTelefono)
    {
        this.telefono = pTelefono;
    }
    
    public int getTelefono ()
    {
        return this.telefono;
    }
    
    public void setDireccion (String pDireccion)
    {
        this.direccion = pDireccion;
    }
    
    public String getDireccion()
    {
        return this.direccion;
    }
    
    public void setHorario(String pHorario)
    {
        this.horarioDeAtencion = pHorario;
    }
    
    public String getHorario ()
    {
        return this.horarioDeAtencion;
    }
    
        public void setLatitud(double pLatitud)
    {
        this.latitud = pLatitud;
    }
    
    public double getLatitud ()
    {
        return this.latitud;
    }
    
    public void setLongitud(double pLongitud)
    {
        this.longitud = pLongitud;
    }
    
    public double getLongitud ()
    {
        return this.longitud;
    }
            
    @Override
    public String toString() 
    {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
    
    
    
    
    
    
    
    
    
    
}
