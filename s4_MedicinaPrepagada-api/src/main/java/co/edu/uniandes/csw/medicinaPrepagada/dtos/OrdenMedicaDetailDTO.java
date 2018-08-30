/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.medicinaPrepagada.dtos;

import java.io.Serializable;
import java.util.List;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 *
 * @author ni.ramirez10
 */
public class OrdenMedicaDetailDTO extends OrdenMedicaDTO implements Serializable 
{
    private List<ExamenMedicoDTO> examenesMedicos; 
    
    private List<MedicamentoDTO> medicamentos;
    
    /**
     * constructor por defecto
     */
    
    public OrdenMedicaDetailDTO()
    {
        super();
    }
    
    /**
     * Obtiene la lista de examenes medicos. 
     */
    
    public List<ExamenMedicoDTO> getExamenesMedicos() 
    {
       return examenesMedicos;
    }
    
    /**
     * Declara una la lista de examenes medicos
     */
    
    public void setExamenesMedicos(List<ExamenMedicoDTO> pExamenesMedicos)
    {
        this.examenesMedicos = pExamenesMedicos;
    }
    
    /**
     * Obtiene la lista de medicamentos. 
     */
    
    public List<MedicamentoDTO> getMedicamentos() 
    {
       return medicamentos;
    }
    
    /**
     * Declara una la lista de medicamentos
     */
    
    public void seMedicamentos(List<MedicamentoDTO> pMedicamentos)
    {
        this.medicamentos = pMedicamentos;
    }
    
    @Override
    public String toString() 
    {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
