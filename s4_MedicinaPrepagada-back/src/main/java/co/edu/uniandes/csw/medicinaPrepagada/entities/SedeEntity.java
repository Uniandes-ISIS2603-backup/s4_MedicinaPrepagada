/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.medicinaPrepagada.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import uk.co.jemos.podam.common.PodamExclude;

/**
 *
 * @author Simon Guzman
 */
@Entity
public class SedeEntity implements Serializable
{
    
    /**
     * Id autogenerado de la sedeEntity
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    
    
    
    /**
     * Lista de consultorio de las entidades
     */
    @PodamExclude
    @OneToMany(mappedBy = "sede",fetch=FetchType.LAZY, cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<ConsultorioEntity> consultorios = new ArrayList<>();
    
    /**
     * nombre de la sedeEntity
     */
    private String nombre;
    /**
     * Direccion de la sedeEntity
     */
    private String direccion;
    /**
     * tipo de la sedeEntity
     */
    private Integer tipoSede;
    /**
     * Descripcion de la sedeEntity
     */
    private String descripcion;
    /**
     * latitud de la sedeEntity
     */
    private Double latitud;
    /**
     * longitud de la sedeEntity
     */
    private Double longitud;
    /**
     * Telefono de la sedeEntity
     */
    private Long telefono;
    /**
     * correo de la sedeEntity
     */
    private String correo;

    
  
    /**
     * Obtiene el id de la sede
     * @return 
     */
    public Long getId() 
    {
        return id;
    }
    /**
     * Pone el id de la sedeEntity
     * @param id 
     */
    public void setId(Long id) 
    {
        this.id = id;
    }
    /**
     * Pone el nombre de la sedeEntity
     * @param pNombre 
     */
    public void setNombre (String pNombre)
   {
       this.nombre = pNombre;
   }
   
    /**
     * Recibe el nombre del sedeEntity
     * @return string del nombre
     */
   public String getNombre  ()
   {
       return this.nombre;
   }
   
   /**
    * Pone la direccion de la sedeEntity
    * @param pDirreccion 
    */
   public void setDireccion (String pDirreccion)
   {
       this.direccion = pDirreccion;
   }
   /**
    * Recibe la direccion de la sedeEntity
    * @return la direccion
    */
   public String getDireccion  ()
   {
       return this.direccion;
   }
    /**
     * Modifica el tipo de sedeEntity
     * @param pTipoSede 
     */
    public void setTipoSede (Integer pTipoSede)
   {
       this.tipoSede=pTipoSede;
   }
    /**
     * Obtiene el tipo de sedeEntity
     * @return 
     */
   public int getTipoSede  ()
   {
       return this.tipoSede;
   }
   
   /**
    * Modifica la descripcion de la sedeEntity
     * @param pDescripcion
    */
   public void setDescripcion (String pDescripcion)
   {
       this.descripcion = pDescripcion;
   }
   /**
    * Obtiene la Descripcion de la sedeEntity
    * @return 
    */
   public String getDescripcion  ()
   {
       return this.descripcion;
   }
   /**
    * Modifica la latitud de la sedeentity
    * @param pLatitud 
    */
   public void setLatitud (Double pLatitud)
   {
       this.latitud = pLatitud;
   }
   /**
    * Obtiene la latitud de la sedeEntity
    * @return de la entidad
    */
   public double getLatitud  ()
   {
       return this.latitud;
   }
   /**
    * Modifica la longitud de la sedeEntity
    * @param pLongitud nueva
    */
   public void setLongitud (Double pLongitud)
   {
       this.longitud = pLongitud;
   }
   /**
    * Obtiene la longitud de la entidad
    * @return 
    */
   public double getLongitud  ()
   {
       return this.longitud;
   }
    
   /**
    * Modifica el numero de telefono de la sedeEntoty
    * @param pTelefono 
    */
   public void setTelefono (Long pTelefono)
   {
       this.telefono = pTelefono;
   }
   /**
    * Obtiene el telefono de la sedeEntity
    * @return 
    */
   public Long getTelefono  ()
   {
        return this.telefono;
   }
   /**
    * Modifica el correo de la sede entity
    * @param pCorreo 
    */
   public void setCorreo (String pCorreo)
   {
       this.correo = pCorreo;
   }
   /**
    * Obtiene el correo de la entidad
    * @return 
    */
   public String getCorreo  ()
   {
       return this.correo;
   }
    
    

    /**
     * Obtiene los consultorios de la sedeEntity
     * @return 
     */
    public List<ConsultorioEntity> getConsultorios ()
    {
        return this.consultorios;
    }
    /**
     * Modifica los consultorios de la sede entity
     * @param pConsultorios 
     */
    public void setConsultorios (List<ConsultorioEntity> pConsultorios)
    {
        this.consultorios = pConsultorios;
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final SedeEntity other = (SedeEntity) obj;
        return Objects.equals(this.id, other.id);
    }

    @Override
    public int hashCode() 
    {
        if (this.getId() != null) {
            return this.getId().hashCode();
        }
        return super.hashCode();
    }
    
    
    
}
