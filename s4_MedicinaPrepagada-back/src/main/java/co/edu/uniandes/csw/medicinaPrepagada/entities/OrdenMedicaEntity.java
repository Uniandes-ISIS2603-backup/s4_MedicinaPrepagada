/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.medicinaPrepagada.entities;

import co.edu.uniandes.csw.medicinaPrepagada.podam.DateStrategy;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.springframework.format.annotation.DateTimeFormat;
import uk.co.jemos.podam.common.PodamExclude;
import uk.co.jemos.podam.common.PodamStrategyValue;

/**
 *
 * @author ni.ramirez10
 */

@Entity
public class OrdenMedicaEntity extends BaseEntity implements Serializable
{
    private String firmaMedico; 
    private String fechaExpedicion; 
    private String comentarios; 
    private String validaHasta; 
    
    @ManyToOne(cascade = CascadeType.PERSIST)
    private HistoriaClinicaEntity historias; 
    
 //   @PodamExclude
 //   @OneToMany
 //   private List<MedicamentoEntity> medicamentos = new ArrayList<MedicamentoEntity>();
    
    @PodamExclude
    @ManyToMany 
    private List<MedicamentoEntity> medicamentos = new ArrayList<MedicamentoEntity>();
    
    @PodamExclude
    @ManyToMany
    private List<ExamenMedicoEntity> examenesMedicos = new ArrayList<ExamenMedicoEntity>();
    
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
    
    public String getFechaExpedicion() 
    {
        return fechaExpedicion;
    }

    /**
     * Establece el valor del atributo fechaExpedicion.
     * @param pFechaExpedicion nuevo valor del atributo
     */
    
    public void setFechaExpedicion(String pFechaExpedicion) 
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
    
    public String getValidaHasta() 
    {
        return validaHasta;
    }   

    /**
     * Establece el atributo validaHasta
     * @param pValidaHasta atributo a implemnetar
     */
    
    public void setValidaHasta(String pValidaHasta) 
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

    /**
     * @return the historias
     */
    public HistoriaClinicaEntity getHistorias() {
        return historias;
    }

    /**
     * @param historias the historias to set
     */
    public void setHistorias(HistoriaClinicaEntity historias) {
        this.historias = historias;
    }
}
