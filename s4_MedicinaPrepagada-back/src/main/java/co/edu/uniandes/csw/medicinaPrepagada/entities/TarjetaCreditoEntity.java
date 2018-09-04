/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.medicinaPrepagada.entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;


/**
 *Clase que representa una gerjeta de credito
 * @author MIGUELHOYOS
 */
@Entity
public class TarjetaCreditoEntity implements Serializable{

    @ManyToOne()
    private PacienteEntity paciente;
    
    @Id
    private Long numero;
    private String nombreEnTarjeta;
    private String fechaExpiracion;
    private String franquicia;

    /**
     * @return the numero
     */
    public Long getNumero() {
        return numero;
    }

    /**
     * @param numero the numero to set
     */
    public void setNumero(Long numero) {
        this.numero = numero;
    }

    /**
     * @return the nombreEnTarjeta
     */
    public String getNombreEnTarjeta() {
        return nombreEnTarjeta;
    }

    /**
     * @param nombreEnTarjeta the nombreEnTarjeta to set
     */
    public void setNombreEnTarjeta(String nombreEnTarjeta) {
        this.nombreEnTarjeta = nombreEnTarjeta;
    }

    /**
     * @return the fechaExpiracion
     */
    public String getFechaExpiracion() {
        return fechaExpiracion;
    }

    /**
     * @param fechaExpiracion the fechaExpiracion to set
     */
    public void setFechaExpiracion(String fechaExpiracion) {
        this.fechaExpiracion = fechaExpiracion;
    }

    /**
     * @return the franquicia
     */
    public String getFranquicia() {
        return franquicia;
    }

    /**
     * @param franquicia the franquicia to set
     */
    public void setFranquicia(String franquicia) {
        this.franquicia = franquicia;
    }

}
