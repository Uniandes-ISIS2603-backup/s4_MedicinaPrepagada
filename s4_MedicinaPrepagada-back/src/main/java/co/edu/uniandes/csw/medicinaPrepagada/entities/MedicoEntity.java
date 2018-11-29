/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.medicinaPrepagada.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import uk.co.jemos.podam.common.PodamExclude;

/**
 *
 * @author Daniel Ivan Romero
 */

@Entity
public class MedicoEntity extends UsuarioEntity implements Serializable{
    
    //Nombre del medico 
    private String nombre;
    // Telefono del medico 
    private int telefono;
    //Correo del medico 
    private String correo;
    //DOcuemto medico del medico 
    private int documentoMedico;
    //Firma del medico 
    private String firma;
    //Descripcion del medico 
    private String descripcion;
    
    //Especialidad del del medico 
    @PodamExclude
    @ManyToOne
    private EspecialidadEntity especialidad;
    // Horarios de atencion del medico 
    @PodamExclude
    @OneToMany(mappedBy = "medico", fetch=FetchType.LAZY)// , cascade = CascadeType.ALL, orphanRemoval = true)
    private List<HorarioAtencionEntity> horariosAtencion;
    
    /**
     * Retorna la especialidad del medico 
     * @return 
     */
    public EspecialidadEntity getEspecialidad() {
        return especialidad;
    }
    /**
     * Edita la especialidad del medico 
     * @param especialidad 
     */
    public void setEspecialidad(EspecialidadEntity especialidad) {
        this.especialidad = especialidad;
    }
    
    /**
     * Retorna el nombre del medico
     * @return 
     */
    public String getNombre() {
        return nombre;
    }
    /**
     * Edita el nombre del medico 
     * @param nombre 
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    /**
     * Retorna el telefono del medico 
     * @return 
     */
    public int getTelefono() {
        return telefono;
    }
    /**
     * Edita el telefono del medico 
     * @param telefono 
     */
    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }
    /**
     * Retorna el correo del medico 
     * @return 
     */
    public String getCorreo() {
        return correo;
    }
    /**
     * Edita el correo del medico 
     * @param correo 
     */
    public void setCorreo(String correo) {
        this.correo = correo;
    }
    /**
     * Retorna los horarios de atencion del medico
     * @return 
     */
    public List<HorarioAtencionEntity> getHorariosAtencion() {
        return horariosAtencion;
    }
    /**
     * Edita los horarios de atencion del medico
     * @param horariosAtencion 
     */
    public void setHorariosAtencion(List<HorarioAtencionEntity> horariosAtencion) {
        this.horariosAtencion = horariosAtencion;
    }
    
    /**
     * Retorna el documento medico del medico 
     * @return 
     */
    public int getDocumentoMedico() {
        return documentoMedico;
    }
    /**
     * Edita el documento medico del medico 
     * @param documentoMedico 
     */
    public void setDocumentoMedico(int documentoMedico) {
        this.documentoMedico = documentoMedico;
    }
    /**
     * Retorna la firma del medico 
     * @return 
     */
    public String getFirma() {
        return firma;
    }
    /**
     * Edita la firma del medico 
     * @param firma 
     */
    public void setFirma(String firma) {
        this.firma = firma;
    }
    /**
     * Retorna la descripcion del medico 
     * @return 
     */
    public String getDescripcion() {
        return descripcion;
    }
    /**
     * Edita la descripcion del medico 
     * @param descripcion 
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    
    
}
