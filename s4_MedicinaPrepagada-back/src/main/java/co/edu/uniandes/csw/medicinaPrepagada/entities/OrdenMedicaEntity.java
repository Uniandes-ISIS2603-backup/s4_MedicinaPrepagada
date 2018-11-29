/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.medicinaPrepagada.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import uk.co.jemos.podam.common.PodamExclude;

/**
 *
 * @author ni.ramirez10
 */

@Entity
public class OrdenMedicaEntity extends BaseEntity implements Serializable
{
    //Firma del medico de la orden medica
    private String firmaMedico; 
    //Fecha de expecidicon de la orden medica
    private String fechaExpedicion; 
    //Comentarios de la orden medica
    private String comentarios; 
    //Fecha hasta la que es valida de la orden medica
    private String validaHasta; 
    //Historia clinica de la orden medica
    @ManyToOne(cascade = CascadeType.PERSIST)
    private HistoriaClinicaEntity historias; 
    
 //   @PodamExclude
 //   @OneToMany
 //   private List<MedicamentoEntity> medicamentos = new ArrayList<MedicamentoEntity>();
    
    //Medicamentos de la orden medica
    @PodamExclude
    @ManyToMany 
    private List<MedicamentoEntity> medicamentos = new ArrayList<MedicamentoEntity>();
    
    //Examenes medicos de la orden medica
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
