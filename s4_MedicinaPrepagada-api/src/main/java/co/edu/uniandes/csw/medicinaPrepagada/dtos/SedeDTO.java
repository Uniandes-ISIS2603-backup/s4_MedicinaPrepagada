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
 * @author Simon Guzman
 */
public class SedeDTO implements Serializable
{
    
    private Long id;
    
    private String nombre;
    
    private String direccion;
    
    private int tipoSede;
    
    private String descripcion;
    
    private double latitud;
    
    private double longitud;
    
    private Long telefono;
    
    private String correo;
    
    
   public SedeDTO () 
   {
       
   }
    
   
   public void setId (long pId)
   {
       this.id = pId;
   }
   
   public Long getId  ()
   {
        return this.id;
   }
    
   
   
   public void setNombre (String pNombre)
   {
       this.nombre = pNombre;
   }
   
   public String getNombre  ()
   {
       return this.nombre;
   }
   
   public void setDireccion (String pDirreccion)
   {
       this.direccion = pDirreccion;
   }
   
   public String getDireccion  ()
   {
       return this.direccion;
   }
    
    public void setTipoSede (int pTipoSede)
   {
       this.tipoSede=pTipoSede;
   }
   
   public int getTipoSede  ()
   {
       return this.tipoSede;
   }
   
      public void setDescripcion (String pDirreccion)
   {
       this.descripcion = pDirreccion;
   }
   
   public String getDescripcion  ()
   {
       return this.descripcion;
   }
   
   public void setLatitud (double pLatitud)
   {
       this.latitud = pLatitud;
   }
   
   public double getLatitud  ()
   {
       return this.latitud;
   }
   
   public void setLongitud (double pLongitud)
   {
       this.longitud = pLongitud;
   }
   
   public double getLongitud  ()
   {
       return this.longitud;
   }
    
   
   public void setTelefono (long pTelefono)
   {
       this.telefono = pTelefono;
   }
   
   public Long getTelefono  ()
   {
        return this.telefono;
   }
   
   public void setCorreo (String pCorreo)
   {
       this.correo = pCorreo;
   }
   
   public String getCorreo  ()
   {
       return this.correo;
   }
   
   

   
   
   
       @Override
    public String toString() 
    {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
