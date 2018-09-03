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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import uk.co.jemos.podam.common.PodamExclude;

/**
 *
 * @author Daniel Ivan Romero
 */

@Entity
public class MedicoEntity extends BaseEntity implements Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idMedico;
    private String nombre;
    private int telefono;
    private String correo;
    
    private EspecialidadEntity especialidad;
    
    @PodamExclude
    @OneToMany(mappedBy = "medico", cascade = CascadeType.PERSIST, orphanRemoval = true, fetch=FetchType.LAZY)
    private List<HorarioAtencionEntity> horariosAtencion;

    public EspecialidadEntity getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(EspecialidadEntity especialidad) {
        this.especialidad = especialidad;
    }
    
    public long getIdMedico() {
        return idMedico;
    }

    public void setIdMedico(long idMedico) {
        this.idMedico = idMedico;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getTelefono() {
        return telefono;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public List<HorarioAtencionEntity> getHorariosAtencion() {
        return horariosAtencion;
    }

    public void setHorariosAtencion(List<HorarioAtencionEntity> horariosAtencion) {
        this.horariosAtencion = horariosAtencion;
    }
    
    
}
