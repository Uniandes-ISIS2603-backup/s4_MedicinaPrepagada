/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.medicinaPrepagada.dtos;

import java.io.Serializable;

/**
 *
 * @author estudiante
 */
public class FarmaciaDTO implements Serializable {
    
    private Long id;
    private String name;
    private String ubicacion;
    private String telefono;
    
    
    
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
}