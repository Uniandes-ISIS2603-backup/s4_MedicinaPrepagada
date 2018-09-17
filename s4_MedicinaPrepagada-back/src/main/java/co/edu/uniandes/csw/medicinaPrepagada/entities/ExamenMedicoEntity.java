/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.medicinaPrepagada.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
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
    
    
    @PodamExclude
    @OneToMany(
            mappedBy ="examenMedico",     
            fetch = javax.persistence.FetchType.LAZY, cascade = CascadeType.PERSIST)
           private List<LaboratorioEntity> laboratorios = new ArrayList<>();
    
    @PodamExclude
    @ManyToOne
    private OrdenMedicaEntity ordenMedica;
    
    /**
     * Devuelve los laboratorios del examenMedicoEntity.
     *
     * @return laboratorios
     */
    public List<LaboratorioEntity> getLaboratorios() {
        return laboratorios;
    }

    /**
     * Modifica los laboratorios del examenMedicoEntity.
     *
     * @param pLaboratorios the laboratorios to set
     */
    public void setLaboratorios(List<LaboratorioEntity> pLaboratorios) {
        this.laboratorios = pLaboratorios;
    }
    
     /**
     * Devuelve la orden medica del examenMedicoEntity.
     *
     * @return ordenMedica
     */
    public OrdenMedicaEntity getOrdenMedica() {
        return ordenMedica;
    }

    /**
     * Modifica la orden medica del examenMedicoEntity.
     *
     * @param pOrden the orden medica to set
     */
    public void setOrdenMedica(OrdenMedicaEntity pOrden) {
        this.ordenMedica = pOrden;
    }

    /**
     * Devuelve el nombre del examenMedicoEntity.
     *
     * @return the name
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Modifica el nombre del examenMedicoEntity.
     *
     * @param pNombre the name to set
     */
    public void setNombre(String pNombre) {
        this.nombre = pNombre;
    }
    
    
    
    /**
     * Devuelve el costo del examenMedicoEntity.
     *
     * @return the costo
     */
    public double getCosto() {
        return costo;
    }

    /**
     * Modifica el costo del examenMedicoEntity.
     *
     * @param pCosto the costo set
     */
    public void setCosto(double pCosto) {
        this.costo = pCosto;
    }
    
    /**
     * Devuelve las recomendaciones del examenMedicoEntity.
     *
     * @return the Recomendaciones
     */
    public String getRecomendaciones() {
        return recomendaciones;
    }

    /**
     * Modifica las recomendaciones del examenMedicoEntity.
     *
     * @param pRecomendaciones the recomendaciones set
     */
    public void setRecomendaciones(String pRecomendaciones) {
        this.recomendaciones = pRecomendaciones;
    }
}
