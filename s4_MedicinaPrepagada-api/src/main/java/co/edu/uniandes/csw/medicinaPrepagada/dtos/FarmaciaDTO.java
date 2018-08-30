/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.medicinaPrepagada.dtos;

import java.io.Serializable;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 *
 * @author estudiante
 */
public class FarmaciaDTO implements Serializable {
    
    private Long id;
    private String name;
    private String ubicacion;
    private String telefono;
    private Long lat;
    private Long lon;
    private String correo;
    
    
    
    /**
     * Constructor por defecto
     */
    public FarmaciaDTO() {
    }
    
    /**
     * Conviertir Entity a DTO (Crea un nuevo DTO con los valores que recibe en
     * la entidad que viene de argumento.
     *
     * @param farmaciaEntity: Es la entidad que se va a convertir a DTO
     */
   // public FarmaciaDTO(FarmaciaEntity farmaciaEntity) {
     //   if (farmaciaEntity != null) {
       //     this.id = farmaciaEntity.getId();
         //   this.name = farmaciaEntity.getName();
       // }
   // }
    
    /**
     * Devuelve el ID de la farmacia.
     *
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * Modifica el ID de la farmacia.
     *
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Devuelve el nombre de la farmacia.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Modifica el nombre de la farmacia.
     *
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
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
    public Long getLat() {
        return lat;
    }

    /**
     * Modifica la latitud de la farmacia.
     *
     * @param pLat the lat to set
     */
    public void setLat(Long pLat) {
        this.lat = pLat;
    }
    
    /**
     * Devuelve la longitud de la farmacia.
     *
     * @return longitud
     */
    public Long getLon() {
        return lon;
    }

    /**
     * Modifica la longitud de la farmacia.
     *
     * @param pLon the lon to set
     */
    public void setLon(Long pLon) {
        this.lon = pLon;
    }
    
    /**
     * Convertir DTO a Entity
     *
     * @return Un Entity con los valores del DTO
     */
   // public FarmaciaEntity toEntity() {
     //   FarmaciaEntity farmaciaEntity = new FarmaciaEntity();
       // farmaciaEntity.setId(this.id);
       // farmaciaEntity.setName(this.name);
       // return farmaciaEntity;
   // }
     @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
