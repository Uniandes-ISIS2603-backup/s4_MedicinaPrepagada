/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.medicinaPrepagada.entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import uk.co.jemos.podam.common.PodamExclude;

/**
 * Clase que representa una farmacia en la persistencia y permite su
 * serialización.
 *
 * @author ncobos
 */
@Entity
public class FarmaciaEntity extends BaseEntity implements Serializable  {
    
    private String nombre;
    private String ubicacion;
    private String telefono;
    private double latitud;
    private double longitud;
    private String correo;
    
    //@javax.persistence.Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    //private Long id;
    
    @PodamExclude
    @ManyToOne
    private MedicamentoEntity medicamento;
    
    
    /**
     * Devuelve el medicamento de la farmacia.
     *
     * @return the medicamento
     */
    public MedicamentoEntity getMedicamento() {
        return medicamento;
    }

    /**
     * Modifica el medicamento de la farmacia.
     *
     * @param pMedicamento the medicamento to set
     */
    public void setMedicamento(MedicamentoEntity pMedicamento) {
        this.medicamento = pMedicamento;
    }
    
     /**
     * Devuelve el ID de la farmacia.
     *
     * @return the id
     */
  //  public Long getId() {
    //    return id;
   // }

    /**
     * Modifica el ID de la farmacia.
     *
     * @param id the id to set
     */
 //   public void setId(Long id) {
   //     this.id = id;
    //}

    /**
     * Devuelve el nombre de la farmacia.
     *
     * @return the name
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Modifica el nombre de la farmacia.
     *
     * @param name the name to set
     */
    public void setNombre(String pNombre) {
        this.nombre = pNombre;
    }
    
    /**
     * Devuelve la ubicacion de la farmacia.
     *
     * @return cantidad
     */
    public String getUbicacion() {
        return ubicacion;
    }

    /**
     * Modifica la ubicacion de la farmacia.
     *
     * @param pUbicacion the cantidad to set
     */
    public void setUbicacion(String pUbicacion) {
        this.ubicacion = pUbicacion;
    }
    
    /**
     * Devuelve el telefono de la farmacia.
     *
     * @return telefono
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * Modifica el telefono de la farmacia.
     *
     * @param telefono the descripcion to set
     */
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    
    /**
     * Devuelve el correo de la farmacia.
     *
     * @return correo
     */
    public String getCorreo(){
        return correo;
    }
   
    /**
     * Modifica el telefono de la farmacia.
     *
     * @param pCorreo the descripcion to set
     */
    public void setCorreo(String pCorreo) {
        this.correo = pCorreo;
    }
    
    /**
     * Devuelve la latitud de la farmacia.
     *
     * @return latitud
     */
    public double getLatitud() {
        return latitud;
    }

    /**
     * Modifica la latitud de la farmacia.
     *
     * @param pLat the lat to set
     */
    public void setLatitud(double pLat) {
        this.latitud = pLat;
    }
    
    /**
     * Devuelve la longitud de la farmacia.
     *
     * @return longitud
     */
    public double getLongitud() {
        return longitud;
    }

    /**
     * Modifica la longitud de la farmacia.
     *
     * @param pLon the lon to set
     */
    public void setLongitud(double pLon) {
        this.longitud = pLon;
    }
}
