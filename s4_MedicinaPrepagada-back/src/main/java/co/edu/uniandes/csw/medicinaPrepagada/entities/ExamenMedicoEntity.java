/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.medicinaPrepagada.entities;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import uk.co.jemos.podam.common.PodamExclude;

/**
 * Clase que representa un examen medico en la persistencia y permite su
 * serialización.
 *
 * @author ncobos
 */
@Entity
public class ExamenMedicoEntity extends BaseEntity implements Serializable  {
    
    
    private String nombre;
    private double costo;
    private String recomendaciones;
    
    //@javax.persistence.Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)    
    //private Long id;
    
    
    @PodamExclude
    @OneToMany(
            mappedBy ="examenMedico",     
            fetch = javax.persistence.FetchType.LAZY, cascade = CascadeType.PERSIST)
            Collection<LaboratorioEntity> laboratorios;
    
    @PodamExclude
    @ManyToOne
    private OrdenMedicaEntity ordenMedica;
    
    /**
     * Devuelve los laboratorios del examen medico.
     *
     * @return laboratorios
     */
    //public Collection<LaboratorioEntity> getLaboratorios() {
      //  return laboratorios;
    //}

    /**
     * Modifica los laboratorios del examen medico.
     *
     * @param pLaboratorios the laboratorios to set
     */
    //public void setLaboratorios(Collection<LaboratorioEntity> pLaboratorios) {
      //  this.laboratorios = pLaboratorios;
    //}
    
     /**
     * Devuelve la orden medica del medicamento.
     *
     * @return ordenMedica
     */
    public OrdenMedicaEntity getOrdenMedica() {
        return ordenMedica;
    }

    /**
     * Modifica la orden medica del medicamento.
     *
     * @param pOrden the orden medica to set
     */
    public void setOrdenMedica(OrdenMedicaEntity pOrden) {
        this.ordenMedica = pOrden;
    }
    
    
      /**
     * Devuelve el ID del examen medico.
     *
     * @return the id
     */
//    public Long getId() {
  //      return id;
   // }

    /**
     * Modifica el ID del examen medico.
     *
     * @param id the id to set
     */
    //public void setId(Long id) {
      //  this.id = id;
    //}

    /**
     * Devuelve el nombre del examen medico.
     *
     * @return the name
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Modifica el nombre del examen medico.
     *
     * @param pNombre the name to set
     */
    public void setNombre(String pNombre) {
        this.nombre = pNombre;
    }
    
    
    
    /**
     * Devuelve el costo del examen medico.
     *
     * @return the costo
     */
    public double getCosto() {
        return costo;
    }

    /**
     * Modifica el costo del examen medico.
     *
     * @param pCosto the costo set
     */
    public void setCosto(double pCosto) {
        this.costo = pCosto;
    }
    
    /**
     * Devuelve las recomendaciones del examen medico.
     *
     * @return the Recomendaciones
     */
    public String getRecomendaciones() {
        return recomendaciones;
    }

    /**
     * Modifica las recomendaciones del examen medico.
     *
     * @param pRecomendaciones the recomendaciones set
     */
    public void setRecomendaciones(String pRecomendaciones) {
        this.recomendaciones = pRecomendaciones;
    }
}
