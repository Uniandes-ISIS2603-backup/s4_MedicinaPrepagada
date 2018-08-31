/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.medicinaPrepagada.dtos;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 *
 * @author estudiante
 */
public class MedicoDTO extends UsuarioDTO{
    
    private long idMedico;
    
    private String nombre;
    
    private int telefono;
    
    private String correo;
    
    private EspecialidadDTO especialidad;
    
    public MedicoDTO(){
        super();
    }

    /**
     * @return the idMedico
     */
    public long getIdMedico() {
        return idMedico;
    }

    /**
     * @param idMedico the idMedico to set
     */
    public void setIdMedico(long idMedico) {
        this.idMedico = idMedico;
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the telefono
     */
    public int getTelefono() {
        return telefono;
    }

    /**
     * @param telefono the telefono to set
     */
    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }

    /**
     * @return the correo
     */
    public String getCorreo() {
        return correo;
    }

    /**
     * @param correo the correo to set
     */
    public void setCorreo(String correo) {
        this.correo = correo;
    }
    
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

    /**
     * @return the especialidad
     */
    public EspecialidadDTO getEspecialidad() {
        return especialidad;
    }

    /**
     * @param especialidad the especialidad to set
     */
    public void setEspecialidad(EspecialidadDTO especialidad) {
        this.especialidad = especialidad;
    }
    
    
}
