/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.medicinaPrepagada.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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
 * @author Santiago Rojas
 */
@Entity
public class LaboratorioEntity 
{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @PodamExclude
    @OneToMany(mappedBy = "laboratorio",fetch=FetchType.LAZY, cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<CitaLaboratorioEntity> citasLaboratorio = new ArrayList<>();
    
    @PodamExclude
    @OneToMany(mappedBy = "laboratorio",fetch=FetchType.LAZY, cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<ExamenMedicoEntity> examenesMedicos = new ArrayList<>();
    
    private String nombre;
    private String direccion;
    private int telefono;
    private String horarioAtencion;
    private double latitud;
    private double longitud;
    
    
    
    public Long getId ()
    {
        return this.id;
    }
    
    public void setId(Long pId)
    {
        this.id = pId;
    }
   
    public String getNombre()
    {
        return this.nombre;
    }
    
    public void setNombre (String pNombre)
    {
        this.nombre = pNombre;
    }
    
    public String getDireccion ()
    {
        return this.direccion;
    }
    
    public void setDireccion (String pDireccion)
    {
        this.direccion = pDireccion;
    }
    
    public int getTelefono ()
    {
        return this.telefono;
    }
    
    public void setTelefono (int pTelefono)
    {
        this.telefono = pTelefono;
    }
    
    public String getHorarioAtencion ()
    {
        return this.horarioAtencion;
    }
    
    public void setHorarioAtencion (String pHorarioAtencion)
    {
        this.horarioAtencion = pHorarioAtencion;
    }
    
    public double getLatitud ()
    {
        return this.latitud;
    }
    
    public void setLatitud (double pLatitud)
    {
        this.latitud = pLatitud;
    }
    
    public double getLongitud ()
    {
        return this.longitud;
    }
    
    public void setLongitud (double pLongitud)
    {
        this.longitud = pLongitud;
    }
    
    public List<CitaLaboratorioEntity> getCitasLab ()
    {
        return this.citasLaboratorio;
    }
    
    public List<ExamenMedicoEntity> getExamenes ()
    {
        return this.examenesMedicos;
    }
    
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
