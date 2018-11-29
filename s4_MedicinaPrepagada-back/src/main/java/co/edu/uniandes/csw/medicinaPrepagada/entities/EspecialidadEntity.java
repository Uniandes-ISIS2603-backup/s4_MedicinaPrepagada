/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.medicinaPrepagada.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import uk.co.jemos.podam.common.PodamExclude;

/**
 *
 * @author Daniel Ivan Romero
 */
@Entity
public class EspecialidadEntity implements Serializable{
    // Id de la especialidad
    @Id
    private String nombre;
    //Consultorios de la especialidad
    @PodamExclude
    @OneToMany(mappedBy = "especialidad", fetch=FetchType.LAZY)
    private List<ConsultorioEntity> consultorios;
    //Medicos de la especialidad
    @PodamExclude
    @OneToMany(mappedBy = "especialidad", fetch=FetchType.LAZY, cascade = CascadeType.PERSIST)
    private List<MedicoEntity> medicos;
    
    
    /**
     * Retorna el nombre de la especialidad
     * @return 
     */
    public String getNombre() {
        return nombre;
    }
    /**
     * Edita el nombre de la especialidad
     * @param nombre 
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    /**
     * Retorna los consultorios de la especialidad
     * @return 
     */
    public List<ConsultorioEntity> getConsultorios() {
        return consultorios;
    }
    /**
     * Edita los consultorios de la especialidad
     * @param consultorios 
     */
    public void setConsultorios(List<ConsultorioEntity> consultorios) {
        this.consultorios = consultorios;
    }
    /**
     * Retorna los medicos de la especialidad
     * @return 
     */
    public List<MedicoEntity> getMedicos() {
        return medicos;
    }
    /**
     * Edita los medicos de la especialidad
     * @param medicos 
     */
    public void setMedicos(List<MedicoEntity> medicos) {
        this.medicos = medicos;
    }
    
    
}
