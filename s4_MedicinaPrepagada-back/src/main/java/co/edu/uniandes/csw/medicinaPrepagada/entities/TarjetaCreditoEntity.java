/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.medicinaPrepagada.entities;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;


/**
 *Clase que representa una gerjeta de credito
 * @author MIGUELHOYOS
 */
@Entity
public class TarjetaCreditoEntity implements Serializable{
    
    /**
     * Paciente de la tarjeta de credito
     */
    @ManyToOne(cascade = CascadeType.PERSIST)
    private PacienteEntity paciente;
    /**
     * Id de la tarjeta de credito
     */
    @Id
    private Long numero;
    /**
     * Nombre de la tarjeta de credito
     */
    private String nombreEnTarjeta;
    /**
     * Fecha de expiracion de la tarjeta de credito
     */
    private String fechaExpiracion;
    /**
     * Franquicia de la tarjeta de credito
     */
    private String franquicia;
    /**
     * codigo de seguridad de la tarjeta de credito
     */
    private Integer codigoSeguridad;

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

    /**
     * @return the codigoSeguridad
     */
    public Integer getCodigoSeguridad() {
        return codigoSeguridad;
    }

    /**
     * @param codigoSeguridad the codigoSeguridad to set
     */
    public void setCodigoSeguridad(Integer codigoSeguridad) {
        this.codigoSeguridad = codigoSeguridad;
    }
    
     /**
     * @return the paciente
     */
    public PacienteEntity getPaciente() {
        return paciente;
    }

    /**
     * @param paciente the paciente to set
     */
    public void setPaciente(PacienteEntity paciente) {
        this.paciente = paciente;
    }


}
