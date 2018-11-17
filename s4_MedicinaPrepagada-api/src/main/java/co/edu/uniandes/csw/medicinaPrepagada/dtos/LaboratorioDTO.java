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

    /**
     * Id del laboratorio
     */
    private Long id;
    /**
     * Nombre del laboratorio
     */
    private String nombre;
    /**
     * Telefono del laboratorio
     */
    private Long telefono;
    /**
     * Direccion del laboratorio
     */
    private String direccion;
    /**
     * Horario de atencion en el que trabaja/atiende el laboratorio
     */
    private String horarioDeAtencion;
    /**
     * Latitud del laboratorio
     */
    private double latitud;
    /**
     * Longitud del laboratorio
     */
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

    /**
     * Asigna el id del laboratorio
     * @param pId 
     */
    public void setId(Long pId) {
        this.id = pId;
    }

    /**
     * Retorna el id del laboratorio
     * @return nombre
     */
    public Long getId() {
        return this.id;
    }
    /**
     * Asigna el nombre del laboratorio
     * @param pNombre 
     */
    public void setNombre(String pNombre) {
        this.nombre = pNombre;
    }
    /**
     * Retorna el nombre del laboratorio
     * @return nombre
     */
    public String getNombre() {
        return this.nombre;
    }

    /**
     * Asigna el telefono del laboratorio
     * @param pTelefono 
     */
    public void setTelefono(Long pTelefono) {
        this.telefono = pTelefono;
    }
    
    /**
     * Retorna el telefono del laboratorio
     * @return telefono
     */
    public Long getTelefono() {
        return this.telefono;
    }
    /**
     * Asigna la direccion del laboratorio
     * @param pDireccion 
     */
    public void setDireccion(String pDireccion) {
        this.direccion = pDireccion;
    }
    /**
     * Retorna la direccion del laboratorio
     * @return direccion
     */
    public String getDireccion() {
        return this.direccion;
    }
    /**
     * Asigna el horario de atencion
     * @param pHorario 
     */
    public void setHorario(String pHorario) {
        this.horarioDeAtencion = pHorario;
    }
    /**
     * Retorna el horario de atencion del laboratorio
     * @return horarioDeAtencion
     */
    public String getHorario() {
        return this.horarioDeAtencion;
    }
    /**
     * Asigna la latitud del laboratorio
     * @param pLatitud 
     */
    public void setLatitud(double pLatitud) {
        this.latitud = pLatitud;
    }
    /**
     * Obtiene la latitud del laboratorio
     * @return latitud
     */
    public double getLatitud() {
        return this.latitud;
    }
    /**
     * Asigna la longitud del laboratorio
     * @param pLongitud 
     */
    public void setLongitud(double pLongitud) {
        this.longitud = pLongitud;
    }
    /**
     * Retorna la longitud del laboratorio
     * @return longitud
     */
    public double getLongitud() {
        return this.longitud;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

    
    
    
    
    
    
    
    
    
}
