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
    
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    
    
    
    
    @PodamExclude
    @OneToMany(mappedBy = "sede",fetch=FetchType.LAZY, cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<ConsultorioEntity> consultorios = new ArrayList<>();
    
    private String nombre;
    
    private String direccion;
    
    private Integer tipoSede;
    
    private String descripcion;
    
    private Double latitud;
    
    private Double longitud;
    
    private Long telefono;
    
    private String correo;

    
  
    
    public Long getId() 
    {
        return id;
    }

    public void setId(Long id) 
    {
        this.id = id;
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
    
    

    
    public List<ConsultorioEntity> getConsultorios ()
    {
        return this.consultorios;
    }
    
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
