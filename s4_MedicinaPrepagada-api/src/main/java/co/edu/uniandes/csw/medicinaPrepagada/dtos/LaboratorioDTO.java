/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.medicinaPrepagada.dtos;

import co.edu.uniandes.csw.medicinaPrepagada.entities.LaboratorioEntity;
import java.io.Serializable;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 *
 * @author SantiagoRojas
 */
public class LaboratorioDTO implements Serializable {

    private Long id;

    private String nombre;

    private Long telefono;

    private String direccion;

    private String horarioDeAtencion;

    private double latitud;

    private double longitud;
    
    
    
    /**
     * Constructor DTO vacio
     */
    public LaboratorioDTO() {

    }
         /**
     * Crea un objeto LaboratorioDTO a partir de un objeto LaboratorioEntity.
     *
     * @param pLaboratorioEntity LaboratorioEntity desde la cual se va a crear el
     * nuevo DTO.
     *
     */ 
    public LaboratorioDTO(LaboratorioEntity pLaboratorioEntity) {
        if (pLaboratorioEntity != null) {
            this.id = pLaboratorioEntity.getId();
            this.nombre = pLaboratorioEntity.getNombre();
            this.direccion = pLaboratorioEntity.getDireccion();
            this.telefono = pLaboratorioEntity.getTelefono();
            this.latitud = pLaboratorioEntity.getLatitud();
            this.longitud = pLaboratorioEntity.getLongitud();
            this.horarioDeAtencion = pLaboratorioEntity.getHorarioAtencion();
            
        }

    }
    
    /**
     * Convierte un objeto LaboratorioDTO a LaboratorioEntity.
     *
     * @return Nueva objeto LaboratorioEntity.
     *
     */
    
    public LaboratorioEntity toEntity() {
        LaboratorioEntity labEntity = new LaboratorioEntity();

        labEntity.setId(this.getId());
        labEntity.setNombre(this.nombre);
        labEntity.setDireccion(this.direccion);
        labEntity.setTelefono(this.telefono);
        labEntity.setLatitud(this.latitud);
        labEntity.setLongitud(this.longitud);
        labEntity.setHorarioAtencion(this.horarioDeAtencion);
        

        return labEntity;
    }

    public void setId(Long pId) {
        this.id = pId;
    }

    public Long getId() {
        return this.id;
    }

    public void setNombre(String pNombre) {
        this.nombre = pNombre;
    }

    public String getNombre() {
        return this.nombre;
    }

    
    public void setTelefono(Long pTelefono) {
        this.telefono = pTelefono;
    }

    public Long getTelefono() {
        return this.telefono;
    }

    public void setDireccion(String pDireccion) {
        this.direccion = pDireccion;
    }

    public String getDireccion() {
        return this.direccion;
    }

    public void setHorario(String pHorario) {
        this.horarioDeAtencion = pHorario;
    }

    public String getHorario() {
        return this.horarioDeAtencion;
    }

    public void setLatitud(double pLatitud) {
        this.latitud = pLatitud;
    }

    public double getLatitud() {
        return this.latitud;
    }

    public void setLongitud(double pLongitud) {
        this.longitud = pLongitud;
    }

    public double getLongitud() {
        return this.longitud;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

    
    
    
    
    
    
    
    
    
}
