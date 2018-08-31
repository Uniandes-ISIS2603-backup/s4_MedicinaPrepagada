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
public class ExamenMedicoDTO implements Serializable {
    
    private Long id;
    private String nombre;
    private double costo;
    private String recomendaciones;
    
    
    /**
     * Constructor por defecto
     */
    public ExamenMedicoDTO() {
    }
    
    /**
     * Conviertir Entity a DTO (Crea un nuevo DTO con los valores que recibe en
     * la entidad que viene de argumento.
     *
     * @param examenMedicoEntity: Es la entidad que se va a convertir a DTO
     */
   // public ExamenMedicoDTO(ExamenMedicoEntity examenMedicoEntity) {
     //   if (examenMedicoEntity != null) {
       //     this.id = examenMedicontity.getId();
         //   this.name = examenMedicoEntity.getName();
       // }
   // }
    
    /**
     * Devuelve el ID del examen medico.
     *
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * Modifica el ID del examen medico.
     *
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Devuelve el nombre del examen medico.
     *
     * @return the name
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Modifica el nombre del examen medico.
     *
     * @param name the name to set
     */
    public void setNombre(String pNombre) {
        this.nombre = pNombre;
    }
    
    
    
    /**
     * Devuelve el costo del examen medico.
     *
     * @return the costo
     */
    public double getCosto() {
        return costo;
    }

    /**
     * Modifica el costo del examen medico.
     *
     * @param costo the costo set
     */
    public void setCosto(double pCosto) {
        this.costo = costo;
    }
    
    /**
     * Devuelve las recomendaciones del examen medico.
     *
     * @return the Recomendaciones
     */
    public String getRecomendaciones() {
        return recomendaciones;
    }

    /**
     * Modifica las recomendaciones del examen medico.
     *
     * @param recomendaciones the recomendaciones set
     */
    public void setRecomendaciones(String recomendaciones) {
        this.recomendaciones = recomendaciones;
    }
    
    
    /**
     * Convertir DTO a Entity
     *
     * @return Un Entity con los valores del DTO
     */
   // public ExamenMedicoEntity toEntity() {
     //   ExamenMedicoEntity examenMedicoEntity = new ExamenMedicoEntity();
       // examenMedicoEntity.setId(this.id);
       // examenMedicoEntity.setName(this.name);
       // return examenMedicoEntity;
   // }
     @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}