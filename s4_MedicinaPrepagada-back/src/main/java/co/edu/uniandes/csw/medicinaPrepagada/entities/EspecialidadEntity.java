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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import uk.co.jemos.podam.common.PodamExclude;

/**
 *
 * @author Daniel Ivan Romero
 */
@Entity
public class EspecialidadEntity implements Serializable{
    
    @Id
    private String nombre;
    
    @PodamExclude
    @OneToMany(mappedBy = "especialidad", fetch=FetchType.LAZY)
    private List<ConsultorioEntity> consultorios;
    
    @PodamExclude
    @OneToMany(mappedBy = "especialidad", fetch=FetchType.LAZY)
    private List<MedicoEntity> medicos;
    
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<ConsultorioEntity> getConsultorios() {
        return consultorios;
    }

    public void setConsultorios(List<ConsultorioEntity> consultorios) {
        this.consultorios = consultorios;
    }

    public List<MedicoEntity> getMedicos() {
        return medicos;
    }

    public void setMedicos(List<MedicoEntity> medicos) {
        this.medicos = medicos;
    }
    
    
}
