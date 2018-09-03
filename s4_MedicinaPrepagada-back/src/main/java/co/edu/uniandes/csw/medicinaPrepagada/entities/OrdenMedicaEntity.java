/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.medicinaPrepagada.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import uk.co.jemos.podam.common.PodamExclude;

/**
 *
 * @author ni.ramirez10
 */

@Entity
public class OrdenMedicaEntity extends BaseEntity implements Serializable
{
    @Id
    private Long id; 
    private String firmaMedico; 
    @Temporal(TemporalType.DATE)
    private Date fechaExpedicion; 
    private String comentarios; 
    @Temporal(TemporalType.DATE)
    private Date validaHasta; 
    
    @OneToMany
    private HistoriaClinicaEntity historias; 
    
    @PodamExclude
    @ManyToOne
    private List<MedicamentoEntity> medicamentos = new ArrayList<MedicamentoEntity>();
    
    @PodamExclude
    @ManyToOne
    private List<ExamenMedicoEntity> examenesMedicos = new ArrayList<ExamenMedicoEntity>();
    
    /**
     * Obtiene el atributo id.
     * @return atributo id.
     */
    
    public Long getId() 
    {
        return id;
    }

    /**
     * Establece el valor del atributo id.
     * @param id nuevo valor del atributo
     */
    
    public void setId(Long id) 
    {
        this.id = id;
    }

    /**
     * Obtiene el atributo firmaMedico.
     * @return atributo firmaMedico.
     */
    
    public String getFirmaMedico() 
    {
        return firmaMedico;
    }

    /**
     * Establece el valor del atributo firmaMedico.
     * @param pfirmaMedico nuevo valor del atributo
     */
    
    public void setFirmaMedico(String pfirmaMedico) 
    {
        this.firmaMedico = pfirmaMedico;
    }

    /**
     * Obtiene el atributo fechaExpedicion.
     * @return atributo fechaExpedicion.
     */
    
    public Date getFechaExpedicion() 
    {
        return fechaExpedicion;
    }

    /**
     * Establece el valor del atributo fechaExpedicion.
     * @param pFechaExpedicion nuevo valor del atributo
     */
    
    public void setFechaExpedicion(Date pFechaExpedicion) 
    {
        this.fechaExpedicion = pFechaExpedicion;
    }

    /**
     * Obtiene el atributo comentarios.
     * @return Los comentarios
     */
    
    public String getComentarios() 
    {
        return comentarios;
    }
    
     /**
     * Establece el atributo de comentarios.
     * @param pComentarios atributo a implementar
     */
    
    public void setComentarios(String pComentarios) 
    {
        this.comentarios = pComentarios;
    }

    /**
     * Ontiene el atributo de validaHasta
     * @return El validaHasta
     */
    
    public Date getValidaHasta() 
    {
        return validaHasta;
    }   

    /**
     * Establece el atributo validaHasta
     * @param pValidaHasta atributo a implemnetar
     */
    
    public void setValidaHasta(Date pValidaHasta) 
    {
        this.validaHasta = pValidaHasta;
    }
    
    /**
     * Devuelve los medicamentos de la orden medica.
     * @return Los medicamentos
     */
    
    public List<MedicamentoEntity> getMedicamentos() 
    {
        return medicamentos;
    }

    /**
     * Modifica los medicamentos de la orden medica
     * @param pMedicamentos Los medicamentos a poner
     */
    
    public void setMedicamentos(List<MedicamentoEntity> pMedicamentos) 
    {
       this.medicamentos = pMedicamentos;
    }
    
    /**
     * Devuelve los examenes medicos de la orden medica.
     * @return Los examenes medicos.
     */
    
    public List<ExamenMedicoEntity> getExamenesMedicos() 
    {
        return examenesMedicos;
    }

    /**
     * Modifica los examenes medicos de la orden medica.
     * @param pExamenesMedicos Los examenes medicos a poner.
     */
    
    public void setExamenesMedicos(List<ExamenMedicoEntity> pExamenesMedicos) 
    {
        this.examenesMedicos = pExamenesMedicos;
    }
}
