/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.medicinaPrepagada.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import uk.co.jemos.podam.common.PodamExclude;

/**
 *Reppresentacion en la base de datos de los laboratorios
 * @author Santiago Rojas
 */
@Entity
public class LaboratorioEntity implements Serializable
{
    // Id del laboratorio 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    // citas de laboratio del del laboratorio 
    @PodamExclude
    @OneToMany(mappedBy = "laboratorio",fetch=FetchType.LAZY, cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<CitaLaboratorioEntity> citasLaboratorio = new ArrayList<>();
    // Examenes medicos del laboratorio 
    @PodamExclude
    @ManyToMany(mappedBy = "laboratorios")
    private List<ExamenMedicoEntity> examenes = new ArrayList<>(); 
    //Nombre del laboratorio 
    private String nombre;
    // Direccion del laboratorio 
    private String direccion;
    //Telefono del laboratorio
    private Long telefono;
    //Horario de atencion del laboratorio
    private String horarioAtencion;
    // latitud del laboratorio
    private double latitud;
    //Longitud del laboratorio
    private double longitud;
    
    
    /**
     * Retorna el id del laboratorio
     * @return 
     */
    public Long getId ()
    {
        return this.id;
    }
    /**
     * Edita el id del laboratorio
     * @param pId 
     */
    public void setId(Long pId)
    {
        this.id = pId;
    }
   /** Retorna el nombre del laboratorio
    * 
    * @return 
    */
    public String getNombre()
    {
        return this.nombre;
    }
    /**
     * Edita el nombre del laboratorio
     * @param pNombre 
     */
    public void setNombre (String pNombre)
    {
        this.nombre = pNombre;
    }
    /**
     * Retorna la direccion del laboratorio
     * @return 
     */
    public String getDireccion ()
    {
        return this.direccion;
    }
    /**
     * Edita la direccion del laboratorio
     * @param pDireccion 
     */
    public void setDireccion (String pDireccion)
    {
        this.direccion = pDireccion;
    }
    /**
     * Edita el telefono del laboratorio
     * @param pTelefono 
     */
    public void setTelefono (Long pTelefono)
    {
        this.telefono = pTelefono;
    }
    /**
     * Retorna el telefono del laboratorio
     * @return 
     */
    public Long getTelefono ()
    {
        return this.telefono;
    }
    
    /**
     * Retorna el horario de atencon del laboratorio
     * @return 
     */
    public String getHorarioAtencion ()
    {
        return this.horarioAtencion;
    }
    /**
     * Edita el horario de atencion del laboratorio
     * @param pHorarioAtencion 
     */
    public void setHorarioAtencion (String pHorarioAtencion)
    {
        this.horarioAtencion = pHorarioAtencion;
    }
    /**
     * Retorna la latitud del laboratorio
     * @return 
     */
    public double getLatitud ()
    {
        return this.latitud;
    }
    /**
     * Edita la latitud del laboratorio
     * @param pLatitud 
     */
    public void setLatitud (double pLatitud)
    {
        this.latitud = pLatitud;
    }
    /**
     * Retorna la longitud del laboratorio
     * @return 
     */
    public double getLongitud ()
    {
        return this.longitud;
    }
    /**
     * Edita la longityd del laboratorio
     * @param pLongitud 
     */
    public void setLongitud (double pLongitud)
    {
        this.longitud = pLongitud;
    }
    /**
     * Retorna las citas del laboratorio
     * @return 
     */
    public List<CitaLaboratorioEntity> getCitasLaboratorio() {
        return citasLaboratorio;
    }
    /**
     * Edita las citas de laboratorio del laboratorio
     * @param citasLaboratorio 
     */
    public void setCitasLaboratorio(List<CitaLaboratorioEntity> citasLaboratorio) {
        this.citasLaboratorio = citasLaboratorio;
    }
    /**
     * Retorna los examenes medicos del laboratorio
     * @return 
     */
    public List<ExamenMedicoEntity> getExamens() {
        return examenes;
    }
    /**
     * Eduta los examens medicos vdel laboratorio
     * @param examenMedico 
     */
    public void setExamens(List<ExamenMedicoEntity> examenMedico) {
        this.examenes = examenMedico;
    }
    
    /**
     * Compara dos laboratorios
     * @param obj
     * @return true si son iguales
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final LaboratorioEntity other = (LaboratorioEntity) obj;
        return Objects.equals(this.id, other.id);
    }

    @Override
    public int hashCode() 
    {
        if (this.getId() != null) {
            return this.getId().hashCode();
        }
        return super.hashCode();
    }
}
