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
import uk.co.jemos.podam.common.PodamExclude;

/**
 * Clase que representa una farmacia en la persistencia y permite su
 * serialización.
 *
 * @author ncobos
 */
@Entity
public class FarmaciaEntity extends BaseEntity implements Serializable  {
    
    //Nombre de la famacia
    private String nombre;
    //Ubicacion de la famacia
    private String ubicacion;
    //Telefono de la famacia
    private Long telefono;
    //Latitud de la famacia
    private double latitud;
    //Longitud de la famacia
    private double longitud;
    //Correo de la famacia
    private String correo;
    
    //Medicamentos de la famacia
    @PodamExclude
    @ManyToMany(mappedBy = "farmacias")
    private List<MedicamentoEntity> medicamentos = new ArrayList<>(); 

    
    
    /**
     * Devuelve el medicamento de la farmaciaEntity.
     *
     * @return medicamento
     */
    public List<MedicamentoEntity> getMedicamentos() {
        return medicamentos;
    }

    /**
     * Modifica el medicamento de la farmaciaEntity.
     *
     * @param parametro el medicamento a asignar
     */
    public void setMedicamentos(List<MedicamentoEntity> parametro) {
        this.medicamentos = parametro;
    }

    /**
     * Devuelve el nombre de la farmaciaEntity.
     *
     * @return the name
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Modifica el nombre de la farmaciaEntity.
     *
     * @param parametro el nombre a asignar
     */
    public void setNombre(String parametro) {
        this.nombre = parametro;
    }
    
    /**
     * Devuelve la ubicacion de la farmaciaEntity.
     *
     * @return ubicacion
     */
    public String getUbicacion() {
        return ubicacion;
    }

    /**
     * Modifica la ubicacion de la farmaciaEntity.
     *
     * @param parametro la ubicacion a asignar
     */
    public void setUbicacion(String parametro) {
        this.ubicacion = parametro;
    }
    
    /**
     * Retorna el telefono de la farmaciaEntity.
     *
     * @return telefono
     */
    public Long getTelefono() {
        return telefono;
    }

    /**
     * Modifica el telefono de la farmaciaEntity.
     *
     * @param parametro el telefono a asignar
     */
    public void setTelefono(Long parametro) {
        this.telefono = parametro;
    }
    
    /**
     * Retorna el correo de la farmaciaEntity.
     *
     * @return correo
     */
    public String getCorreo(){
        return correo;
    }
   
    /**
     * Modifica el correo de la farmaciaEntity.
     *
     * @param parametro el correo a asignar
     */
    public void setCorreo(String parametro) {
        this.correo = parametro;
    }
    
    /**
     * Retorna la latitud de la farmaciaEntity.
     *
     * @return latitud
     */
    public double getLatitud() {
        return latitud;
    }

    /**
     * Modifica la latitud de la farmaciaEntity.
     *
     * @param parametro la latitud a asignar
     */
    public void setLatitud(double parametro) {
        this.latitud = parametro;
    }
    
    /**
     * Retorna la longitud de la farmaciaEntity.
     *
     * @return longitud
     */
    public double getLongitud() {
        return longitud;
    }

    /**
     * Modifica la longitud de la farmaciaEntity.
     *
     * @param parametro la longitud a asignar
     */
    public void setLongitud(double parametro) {
        this.longitud = parametro;
    }
}

