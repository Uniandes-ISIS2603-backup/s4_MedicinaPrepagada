/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.medicinaPrepagada.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
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
    private Double costo;
    private String elaboradoPor;
    
    @PodamExclude
    @ManyToMany 
    private List<FarmaciaEntity> farmacias = new ArrayList<FarmaciaEntity>();
    
    @PodamExclude
    @ManyToMany(mappedBy = "medicamentos")
    private List<OrdenMedicaEntity> ordenes = new ArrayList<>(); 
    
    /**
     * Devuelve las farmacias del medicamentoEntity.
     *
     * @return farmacias
     */
    public List<FarmaciaEntity> getFarmacias() {
        return farmacias;
    }

    /**
     * Modifica las farmacias del medicamentoEntity.
     *
     * @param pFarmacias the farmacias to set
     */
    public void setFarmacias(List<FarmaciaEntity> pFarmacias) {
        this.farmacias = pFarmacias;
    }
    
     /**
     * Devuelve la ordenes medicas del medicamentoEntity.
     *
     * @return ordenMedica
     */
    public List<OrdenMedicaEntity> getOrdenesMedicas() {
        return ordenes;
    }

    /**
     * Modifica las ordenes medicas del medicamentoEntity.
     *
     * @param pOrden the orden medica to set
     */
    public void setOrdenesMedicas(List<OrdenMedicaEntity> pOrden) {
        this.ordenes = pOrden;
    }

    /**
     * Devuelve el nombre del medicamentoEntity.
     *
     * @return the name
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Modifica el nombre del medicamentoEntity.
     *
     * @param pNombre the name to set
     */
    public void setNombre(String pNombre) {
        this.nombre = pNombre;
    }
    
    /**
     * Devuelve la cantidad del medicamentoEntity.
     *
     * @return cantidad
     */
    public String getCantidad() {
        return cantidad;
    }

    /**
     * Modifica la cantidad del medicamentoEntity.
     *
     * @param pCantidad the cantidad to set
     */
    public void setCantidad(String pCantidad) {
        this.cantidad = pCantidad;
    }
    
    /**
     * Devuelve la descripcion del medicamentoEntity.
     *
     * @return descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Modifica la descripcion del medicamentoEntity.
     *
     * @param pDescripcion the descripcion to set
     */
    public void setDescripcion(String pDescripcion) {
        this.descripcion = pDescripcion;
    }
    
    
    /**
     * Devuelve el costo del medicamentoEntity.
     *
     * @return the costo
     */
    public double getCosto() {
        return costo;
    }

    /**
     * Modifica el costo del medicamentoEntity.
     *
     * @param pCosto the costo set
     */
    public void setCosto(double pCosto) {
        this.costo = pCosto;
    }
    
    /**
     * Devuelve quién hizo el medicamentoEntity.
     *
     * @return the ElaboradoPor
     */
    public String getElaboradoPor() {
        return elaboradoPor;
    }

    /**
     * Modifica el elaborado por del medicamentoEntity.
     *
     * @param pElaboradoPor the elaboradoPor set
     */
    public void setElaboradoPor(String pElaboradoPor) {
        this.elaboradoPor = pElaboradoPor;
    }
}
