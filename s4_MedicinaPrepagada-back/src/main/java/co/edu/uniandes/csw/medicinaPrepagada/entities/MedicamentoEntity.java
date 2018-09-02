/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.medicinaPrepagada.entities;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import uk.co.jemos.podam.common.PodamExclude;

/**
 * Clase que representa un medicamento en la persistencia y permite su
 * serialización.
 *
 * @author ncobos
 */
@Entity
public class MedicamentoEntity extends BaseEntity implements Serializable {
    
   private String nombre;
    private String cantidad;
    private String descripcion;
    private double costo;
    private String elaboradoPor;
    
    @javax.persistence.Id
    private Long id;
    
    
    
    @PodamExclude
    @OneToMany(
            mappedBy ="medicamento",     
            fetch = javax.persistence.FetchType.LAZY)
            Collection<FarmaciaEntity> farmacias;
    
    @PodamExclude
    @ManyToOne
    private OrdenMedicaEntity ordenMedica;
    
    /**
     * Devuelve las farmacias del medicamento.
     *
     * @return farmacias
     */
    public Collection<FarmaciaEntity> getFarmacias() {
        return farmacias;
    }

    /**
     * Modifica las farmacias del medicamento.
     *
     * @param pFarmacias the farmacias to set
     */
    public void setFarmacias(Collection<FarmaciaEntity> pFarmacias) {
        this.farmacias = pFarmacias;
    }
    
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
     * Devuelve el ID del medicamento.
     *
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * Modifica el ID del medicamento.
     *
     * @param pId the id to set
     */
    public void setId(Long pId) {
        this.id = pId;
    }

    /**
     * Devuelve el nombre del medicamento.
     *
     * @return the name
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Modifica el nombre del medicamento.
     *
     * @param pNombre the name to set
     */
    public void setNombre(String pNombre) {
        this.nombre = pNombre;
    }
    
    /**
     * Devuelve la cantidad del medicamento.
     *
     * @return cantidad
     */
    public String getCantidad() {
        return cantidad;
    }

    /**
     * Modifica la cantidad del medicamento.
     *
     * @param cantidad the cantidad to set
     */
    public void setCantidad(String pCantidad) {
        this.cantidad = pCantidad;
    }
    
    /**
     * Devuelve la descripcion del medicamento.
     *
     * @return descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Modifica la descripcion del medicamento.
     *
     * @param pDescripcion the descripcion to set
     */
    public void setDescripcion(String pDescripcion) {
        this.descripcion = pDescripcion;
    }
    
    
    /**
     * Devuelve el costo del medicamento.
     *
     * @return the costo
     */
    public double getCosto() {
        return costo;
    }

    /**
     * Modifica el costo del medicamento.
     *
     * @param pCosto the costo set
     */
    public void setCosto(double pCosto) {
        this.costo = pCosto;
    }
    
    /**
     * Devuelve quién hizo el medicamento.
     *
     * @return the ElaboradoPor
     */
    public String getElaboradoPor() {
        return elaboradoPor;
    }

    /**
     * Modifica el elaborado por del medicamento.
     *
     * @param pElaboradoPor the elaboradoPor set
     */
    public void setElaboradoPor(String pElaboradoPor) {
        this.elaboradoPor = pElaboradoPor;
    }
    
}
