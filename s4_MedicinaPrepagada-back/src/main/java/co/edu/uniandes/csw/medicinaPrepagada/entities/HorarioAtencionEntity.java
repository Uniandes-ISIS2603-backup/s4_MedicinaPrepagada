/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.medicinaPrepagada.entities;

import co.edu.uniandes.csw.medicinaPrepagada.podam.DateStrategy;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.springframework.format.annotation.DateTimeFormat;
import uk.co.jemos.podam.common.PodamExclude;
import uk.co.jemos.podam.common.PodamStrategyValue;

/**
 *
 * @author Simon Guzman
 */
@Entity
public class HorarioAtencionEntity implements Serializable
{
    
    /**
     * Id del horario de atencion
     */
    
     @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
     
     /**
      * fecha de inicio del horario de atencion
      */
     //En logic "yyyy/MM/dd HH:mm:ss"
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @PodamStrategyValue(DateStrategy.class)
    private Date fechaInicio;
    
    /**
     * Fecha de fin del horario de atencion
     */
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @PodamStrategyValue(DateStrategy.class)
    private Date fechaFin;
     
     /**
      * El consultorio de horario de atencion
      */
    @PodamExclude
    @ManyToOne
    private ConsultorioEntity consultorio;
     /**
      * El medico del horario de atencion
      */
    @PodamExclude
    @ManyToOne
    private MedicoEntity medico;
     /**
      * La lista de citas medicas del horario de atencion
      */ 
    @PodamExclude
    @OneToMany(mappedBy = "horarioAtencionAsignado",fetch=FetchType.LAZY)
    private List<CitaMedicaEntity> citasMedicas = new ArrayList<>();
     
     /**
      * Retorna el id del horario de atencion
      * @return 
      */
    public Long getId() 
    {
        return id;
    }
    /**
     * Para editar el id del horario de atencion
     * @param id 
     */
    public void setId(Long id)
    {
        this.id = id;
    }

    
    /**
     * para editar la fecha de inicio del horario de atencion
     * @param pFechaInicio 
     */
     public void setFechaInicio (Date pFechaInicio)
   {
       this.fechaInicio = pFechaInicio;
   }
   /**
    * Retorna la fecha de inicio del horario de atencion
    * @return 
    */
   public Date getFechaInicio  ()
   {
        return this.fechaInicio;
   }
   /**
    * Para editar la fecha fin del horario de atencion
    * @param pFechaFin 
    */
   public void setFechaFin (Date pFechaFin)
   {
       this.fechaFin = pFechaFin;
   }
   /**
    * Retorna la fecha de fin del horario de atencion
    * @return 
    */
   public Date getFechaFin  ()
   {
        return this.fechaFin;
   }
   /**
    * Edita el consultorio del horario de atencion
    * @param pConsultorio 
    */
   public void setConsultorio (ConsultorioEntity pConsultorio)
   {
       this.consultorio = pConsultorio;
   }
   /**
    * Retorna el consultorio del horario de atencion
    * @return 
    */
   public ConsultorioEntity getConsultorio ()
   {
       return this.consultorio;
   }
   
      /**
       * Edita el medico del horario de atencion
       * @param pMedico 
       */
   public void setMedico (MedicoEntity pMedico)
   {
       this.medico = pMedico;
   }
   /**
    * retorna el medico del horario de atencion
    * @return 
    */
   public MedicoEntity getMedico ()
   {
       return this.medico;
   }
    
    
    /**
     * Edita la lista de citas medicas del horario de atencion
     * @param pCitasMedicas 
     */
    public void setCitasMedicas(List<CitaMedicaEntity> pCitasMedicas)
    {
        this.citasMedicas = pCitasMedicas;
    }
    
    /**
     * Retorna la lista de citas medicas del horario de atencion
     * @return 
     */
    public List<CitaMedicaEntity> getCitasMedicas ()
    {
        return this.citasMedicas;
    }
    
    /**
     * Metodo para comparar dos horarios de atencion
     * @param obj
     * @return true si son iguales
     */
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
        final HorarioAtencionEntity other = (HorarioAtencionEntity) obj;
        return Objects.equals(this.id, other.id);
    }

    @Override
    public int hashCode() {
        if (this.getId() != null) {
            return this.getId().hashCode();
        }
        return super.hashCode();
    }
    
    
}
