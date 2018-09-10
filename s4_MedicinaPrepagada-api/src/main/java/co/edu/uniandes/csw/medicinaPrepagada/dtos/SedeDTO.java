/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.medicinaPrepagada.dtos;

import co.edu.uniandes.csw.medicinaPrepagada.entities.SedeEntity;
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
    
    private Double latitud;
    
    private Double longitud;
    
    private Long telefono;
    
    private String correo;
    
    
    //Constructor vacio
   public SedeDTO () 
   {
       
   }
   
        /**
     * Crea un objeto SedeDTO a partir de un objeto SedeEntity.
     *
     * @param pSedeEntity Entidad SedeEntity desde la cual se va a crear el
     * nuevo DTO.
     *
     */   
     public SedeDTO (SedeEntity pSedeEntity) 
   {
       if (pSedeEntity != null)
       {
           this.id=pSedeEntity.getId();
           this.nombre = pSedeEntity.getNombre();
           this.direccion = pSedeEntity.getDireccion();
           this.tipoSede = pSedeEntity.getTipoSede();
           this.descripcion = pSedeEntity.getDescripcion();
           this.latitud = pSedeEntity.getLatitud();
           this.longitud = pSedeEntity.getLongitud();
           this.telefono = pSedeEntity.getTelefono();
           this.correo = pSedeEntity.getCorreo();
       }
       
   }
     
     
     
     
     /**
     * Convierte un objeto SedeDTO a SedeEntity.
     *
     * @return Nueva objeto SedeEntity.
     *
     */
    public SedeEntity toEntity() 
    {
        SedeEntity sedeEntity = new SedeEntity();
        sedeEntity.setId(this.getId());
        sedeEntity.setNombre(this.nombre);
        sedeEntity.setDireccion(this.direccion);
        sedeEntity.setTipoSede(this.tipoSede);
        sedeEntity.setDescripcion(this.descripcion);
        sedeEntity.setLatitud(this.latitud);
        sedeEntity.setLongitud(this.longitud);
        sedeEntity.setTelefono(this.telefono);
        sedeEntity.setCorreo(this.correo);
        
        return sedeEntity;
    }
   
   
    
   
   public void setId (Long pId)
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
    
    public void setTipoSede (Integer pTipoSede)
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
   
   public void setLatitud (Double pLatitud)
   {
       this.latitud = pLatitud;
   }
   
   public double getLatitud  ()
   {
       return this.latitud;
   }
   
   public void setLongitud (Double pLongitud)
   {
       this.longitud = pLongitud;
   }
   
   public double getLongitud  ()
   {
       return this.longitud;
   }
    
   
   public void setTelefono (Long pTelefono)
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
