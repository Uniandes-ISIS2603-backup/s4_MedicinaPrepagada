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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import uk.co.jemos.podam.common.PodamExclude;

/**
 *
 * @author Simon Guzman
 */
@Entity
public class ConsultorioEntity implements Serializable
{
    // Id del consultorio
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    //Lista de horarios de atencion del consultorio
    @PodamExclude
    @OneToMany(mappedBy = "consultorio",fetch=FetchType.LAZY)
    private List<HorarioAtencionEntity> horariosAtencion = new ArrayList<>();
     //Sede del consultorio
    @PodamExclude
    @ManyToOne (cascade = CascadeType.PERSIST)
    private SedeEntity sede;
     //especialidad del consultorio
    @PodamExclude
    @ManyToOne
    private EspecialidadEntity especialidad; 
     //Edificio del consultorio
    private String edificio;
    
    //numero de oficina del consultorio
    private Integer nOficina;
     
     
     
     
     
     
     /**
      * Retorna el id del consultorio
      * @return 
      */
    public Long getId() 
    {
        return id;
    }
    /**
     * Edita el id del consultorio
     * @param id 
     */
    public void setId(Long id) 
    {
        this.id = id;
    }

    
    /**
     * Edita el edificio del consultorio
     * @param pEdificio 
     */
   public void setEdificio (String pEdificio)
   {
       this.edificio = pEdificio;
   }
   /**
    * Retorna el edificio del consultorio
    * @return 
    */
   public String getEdificio  ()
   {
       return this.edificio;
   }
   
   /**
    * Edita el numero de oficina del consultorio
    * @param pNOficina 
    */
    public void setNOficina (Integer pNOficina)
   {
       this.nOficina=pNOficina;
   }
   /**
    * Retorna el numero de oficina del consultorio
    * @return 
    */
   public int getNOficina  ()
   {
       return this.nOficina;
   }
    /**
     * Retirna la sede del consultorio
     * @return 
     */
    public SedeEntity getSede()   
    {
        return this.sede;
    }
    /**
     * Edita la sede del consultorio
     * @param pSede 
     */
    public void setSede(SedeEntity pSede) 
    {
        this.sede = pSede;
    }
    
    
     /**
      * Edita la especialidad del consultorio
      * @param pEspecialidad 
      */  
   public void setEspecialidad (EspecialidadEntity pEspecialidad)
   {
       this.especialidad = pEspecialidad;
   }
   /**
    * Retorna la especialidad del consultorio
    * @return 
    */
   public EspecialidadEntity getEspecialidad ()
   {
       return this.especialidad;
   }
    
    /**
     * Retorna los horarios de atencion del consultorio
     * @return 
     */
    public List<HorarioAtencionEntity> getHorariosAtencion ()
    {
        return this.horariosAtencion;
    }
    /**
     * Edita los horarios de atencion del consultorio
     * @param pHorariosAtencion 
     */
    public void setHorariosAtencion (List<HorarioAtencionEntity> pHorariosAtencion)
    {
        this.horariosAtencion = pHorariosAtencion;
    }
    
    /**
     * Se usa para comparar dos consultorios
     * @param obj
     * @return true si son iguales
     */
    @Override
    public boolean equals(Object obj) 
    {
        if (this == obj) 
        {
            return true;
        }
        if (obj == null) 
        {
            return false;
        }
        if (getClass() != obj.getClass()) 
        {
            return false;
        }
        final ConsultorioEntity other = (ConsultorioEntity) obj;
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
