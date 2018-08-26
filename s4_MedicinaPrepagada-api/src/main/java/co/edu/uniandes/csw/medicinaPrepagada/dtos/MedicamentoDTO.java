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
public class MedicamentoDTO implements Serializable {
    
    private Long id;
    private String name;
    private String cantidad;
    private String descripcion;
    private String costo;
    private String elaboradoPor;
    
    
    /**
     * Constructor por defecto
     */
    public MedicamentoDTO() {
    }
    
    /**
     * Conviertir Entity a DTO (Crea un nuevo DTO con los valores que recibe en
     * la entidad que viene de argumento.
     *
     * @param medicamentoEntity: Es la entidad que se va a convertir a DTO
     */
   // public MedicamentoDTO(MedicamentoEntity medicamentoEntity) {
     //   if (medicamentoEntity != null) {
       //     this.id = medicamentoEntity.getId();
         //   this.name = medicamentoEntity.getName();
       // }
   // }
    
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
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Devuelve el nombre del medicamento.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Modifica el nombre del medicamento.
     *
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
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
    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
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
     * @param descripcion the descripcion to set
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    
    /**
     * Devuelve el costo del medicamento.
     *
     * @return the costo
     */
    public String getCosto() {
        return costo;
    }

    /**
     * Modifica el costo del medicamento.
     *
     * @param costo the costo set
     */
    public void setCosto(String costo) {
        this.costo = costo;
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
     * @param elaboradoPor the elaboradoPor set
     */
    public void setElaboradoPor(String elaboradoPor) {
        this.elaboradoPor = elaboradoPor;
    }
    
    
    /**
     * Convertir DTO a Entity
     *
     * @return Un Entity con los valores del DTO
     */
   // public MedicamentoEntity toEntity() {
     //   MedicamentoEntity medicamentoEntity = new MedicamentoEntity();
       // medicamentoEntity.setId(this.id);
       // medicamentoEntity.setName(this.name);
       // return medicamentoEntity;
   // }
}
