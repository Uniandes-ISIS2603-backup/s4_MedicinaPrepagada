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
    // id de la sede DTO
    private Long id;
    // Nombre de la sedeDTO
    private String nombre;
    // Direccion de la sedeDTO
    private String direccion;
    //Tipo de la sedeDTO
    private Integer tipoSede;
    //Descripcion de la sedeDTO
    private String descripcion;
    //latitud de la sedeDTO
    private Double latitud;
    //longitud de la sedeDTO
    private Double longitud;
    //telefono de la sedeDTO
    private Long telefono;
    //Correo de la sedeDTO
    private String correo;
    //Imagen de la sedeDTO
    private String imagen;
    
  /**
   * Constructor vacio de la sedeDTO
   */
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
           this.imagen = pSedeEntity.getImagen();
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
        sedeEntity.setId(this.id);
        sedeEntity.setNombre(this.nombre);
        sedeEntity.setDireccion(this.direccion);
        sedeEntity.setTipoSede(this.tipoSede);
        sedeEntity.setDescripcion(this.descripcion);
        sedeEntity.setLatitud(this.latitud);
        sedeEntity.setLongitud(this.longitud);
        sedeEntity.setTelefono(this.telefono);
        sedeEntity.setCorreo(this.correo);
        sedeEntity.setImagen(this.imagen);
        
        return sedeEntity;
    }

    //Getters and setter
    
    
   /**
    * ASigna el Id de la sedeDTO
    * @param pId 
    */
   public void setId (long pId)
   {
       this.id = pId;
   }
 
   /**
    * Recibe el id de la sedeDTO
    * @return idSedeDTO
    */
   public long getId  ()
   {
        return this.id;
   }
    
   /**
    * Asigna el nombre de la sedeDTO
    * @param pNombre 
    */
   public void setNombre (String pNombre)
   {
       this.nombre = pNombre;
   }
   /**
    * Recibe el nombre de la sedeDTO
    * @return 
    */
   
   public String getNombre  ()
   {
       return this.nombre;
   }
   /**
    * Asigna la direccion de la sedeDTO
    * @param pDirreccion 
    */
   public void setDireccion (String pDirreccion)
   {
       this.direccion = pDirreccion;
   }
   /**
    * Recibe la direccion de la sedeDTO
    * @return 
    */
   public String getDireccion  ()
   {
       return this.direccion;
   }
   /**
    * ASigna el tipo de la sedeDTO
    * @param pTipoSede 
    */ 
    public void setTipoSede (int pTipoSede)
   {
       this.tipoSede=pTipoSede;
   }
   /**
    * Recibe el tipo de la sedeDTO
    * @return 
    */
   public int getTipoSede  ()
   {
       return this.tipoSede;
   }
   /**
    * ASigna la descripcion de la sedeDTO
    * @param pDirreccion 
    */
      public void setDescripcion (String pDirreccion)
   {
       this.descripcion = pDirreccion;
   }
   /**
    * Recibe la descripcion de la sedeDTO
    * @return 
    */
   
   public String getDescripcion  ()
   {
       return this.descripcion;
   }
   /**
    * Asigna la latitud de la sedeDTO
    * @param pLatitud del DTO
    */
   public void setLatitud (double pLatitud)
   {
       this.latitud = pLatitud;
   }
   /**
    * Recibe la latitud de la sedeDTO
    * @return 
    */
   public double getLatitud  ()
   {
       return this.latitud;
   }
   /**
    * Asigna la longitud de la sedeDTO
    * @param pLongitud 
    */
   public void setLongitud (double pLongitud)
   {
       this.longitud = pLongitud;
   }
   /**
    * Recibe la longitud de la sedeDTO
    * @return 
    */
   public double getLongitud  ()
   {
       return this.longitud;
   }
   /**
    * Asigna el telefono de la sedeDTO
    * @param pTelefono 
    */
   
   public void setTelefono (Long pTelefono)
   {
       this.telefono = pTelefono;
   }
   /**
    * Recibe el telefono de la sedeDTO
    * @return 
    */
   public Long getTelefono  ()
   {
        return this.telefono;
   }
   /**
    * Asigna el correo de la sedeDTO
    * @param pCorreo 
    */
   public void setCorreo (String pCorreo)
   {
       this.correo = pCorreo;
   }
   /**
    * Recibe el correo de la sedeDTO
    * @return 
    */
   public String getCorreo  ()
   {
       return this.correo;
   }
   
      /**
     * Pone la imagen de la sedeEntity
     * @param pImagen 
     */
    public void setImagen (String pImagen)
   {
       this.imagen = pImagen;
   }
   
    /**
     * Recibe el nombre del sedeEntity
     * @return string del nombre
     */
   public String getImagen  ()
   {
       return this.imagen;
   }
   
   /**
    * Convierle la sedeDTO en string
    * @return 
    */
    @Override
    public String toString() 
    {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
