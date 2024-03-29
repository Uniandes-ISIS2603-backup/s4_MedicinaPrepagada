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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import uk.co.jemos.podam.common.PodamExclude;

/**
 *
 * @author ni.ramirez10
 */

@Entity
public class HistoriaClinicaEntity extends BaseEntity implements Serializable
{

    /**@Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    @PodamStrategyValue(DateStrategy.class)*/
    //Fecha de la historia clinica
    private String fecha;  
    // diagnostico de la historia clinica
    private String descripcionDiagnostico; 
    //Alergias de la historia clinica
    private String alergias;
    // Peso de la historia clinica
    private Double peso; 
    //Estatura de la historia clinica
    private Double estatura; 
    //Fuma o no 
    private boolean fuma; 
    //Bebe o no bebe
    private boolean bebe;
    //Operaciones de la historia clinica
    private String operaciones; 
    
    //Paciente de la historia clinica
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(nullable=true)
    private PacienteEntity paciente; 
    // ordenes medicas de la historia clinica
    @PodamExclude
    @OneToMany(mappedBy = "historias",fetch=FetchType.LAZY)
    private List<OrdenMedicaEntity> ordenesMedicas;
    
  
     /**
     * Obtiene la fecha
     * @return atributo fecha.
     */
    
    public String getFecha() 
    {
        return fecha;
    }

    /**
     * Establece el valor del atributo fecha.
     * @param fecha nuevo valor del atributo
     */
    
    public void setFecha(String fecha ) 
    {
        this.fecha = fecha; 
    }
    
    /**
    * Obtiene la descripcion diagnostico
    * @return atributo descripcionDiagnostico.
    */
    
    public String getDescripcionDiagnostico() 
    {
        return descripcionDiagnostico;
    }

    /**
     * Establece el valor del atributo descripcion diagnostico.
     * @param descripcionDiagnostico nuevo valor del atributo
     */
    
    public void setDescripcionDiagnostico(String descripcionDiagnostico ) 
    {
        this.descripcionDiagnostico = descripcionDiagnostico;
    }
    
    /**
     * Obtiene las alergias
     * @return atributo alergias.
     */
    
    public String getAlergias() 
    {
        return alergias;
    }

    /**
     * Establece el valor del atributo alergias.
     * @param alergias nuevo valor del atributo
     */
    
    public void setAlergias(String alergias ) 
    {
        this.alergias = alergias;
    }
    
    /**
     * Obtiene el peso 
     * @return atributo peso.
     */
    
    public Double getPeso() 
    {
        return peso;
    }

    /**
     * Establece el valor del atributo peso.
     * @param peso nuevo valor del atributo
     */
    
    public void setPeso(Double peso ) 
    {
        this.peso = peso;
    }
    
    /**
     * Obtiene la estatura 
     * @return atributo estatura.
     */
    
    public Double getEstatura() 
    {
        return estatura;
    }

    /**
     * Establece el valor del atributo estatura.
     * @param estatura nuevo valor del atributo
     */
    
    public void setEstatura(Double estatura ) 
    {
        this.estatura = estatura;
    }
    
    /**
     * Obtiene el estado de fuma 
     * @return atributo fuma.
     */
    
    public Boolean getFuma() 
    {
        return fuma;
    }

    /**
     * Establece el valor del atributo fuma.
     * @param fuma nuevo valor del atributo
     */
    
    public void setFuma(Boolean fuma ) 
    {
        this.fuma = fuma;
    }
    
    /**
     * Obtiene el estado de bebe 
     * @return atributo bebe.
     */
    
    public Boolean getBebe() 
    {
        return bebe;
    }

    /**
     * Establece el valor del atributo bebe.
     * @param bebe nuevo valor del atributo
     */
    
    public void setBebe(Boolean bebe ) 
    {
        this.bebe = bebe;
    }
    
    /**
     * Obtiene las operaciones
     * @return atributo operaciones.
     */
    
    public String getOperaciones() 
    {
        return operaciones;
    }

    /**
     * Establece el valor del atributo operaciones.
     * @param operaciones nuevo valor del atributo
     */
    
    public void setOperaciones(String operaciones ) 
    {
        this.operaciones = operaciones;
    }
    
    /**
     * Devuelve las ordenes medicas de la historia clinica. 
     * @return Lista de ordenes medicas
     */
    
    public List<OrdenMedicaEntity> getOrdenesMedicas()
    {
        return ordenesMedicas;
    }
    
    /**
     * Modifica las ordenes medicas de la historia clinica.
     * @param pOrdenesMedicas Las nuevas ordenes medicas.
     */
    
    public void setOrdenesMedicas(List<OrdenMedicaEntity> pOrdenesMedicas) 
    {
        this.ordenesMedicas = pOrdenesMedicas;
    }

    /**
     * @return the paciente
     */
    public PacienteEntity getPaciente() 
    {
        return paciente;
    }

    /**
     * @param paciente the paciente to set
     */
    public void setPaciente(PacienteEntity paciente) 
    {
        this.paciente = paciente;
    }
    
    
    
    
    
}
