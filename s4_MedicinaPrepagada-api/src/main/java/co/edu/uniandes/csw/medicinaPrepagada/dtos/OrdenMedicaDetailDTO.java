/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.medicinaPrepagada.dtos;

import co.edu.uniandes.csw.medicinaPrepagada.entities.ExamenMedicoEntity;
import co.edu.uniandes.csw.medicinaPrepagada.entities.MedicamentoEntity;
import co.edu.uniandes.csw.medicinaPrepagada.entities.OrdenMedicaEntity;
import java.io.Serializable;
import java.util.ArrayList;
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
     * Crea un objeto OrdenMedicaDetailDto a partir de un objeto OrdenMedicaEntity
     */
    
    public OrdenMedicaDetailDTO(OrdenMedicaEntity ordenEntity) 
    {
        super(ordenEntity);
        
        if (ordenEntity != null) 
        {
            examenesMedicos = new ArrayList<>();
            
            for (ExamenMedicoEntity entityExamenesMedicos : ordenEntity.getExamenesMedicos())
            {
                examenesMedicos.add(new ExamenMedicoDTO(entityExamenesMedicos));
            }
            
            medicamentos = new ArrayList<>(); 
            
            for (MedicamentoEntity entityMedicamentos : ordenEntity.getMedicamentos())
            {
                medicamentos.add(new MedicamentoDTO(entityMedicamentos));
            }
            
        }
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
    
    @Override
    public OrdenMedicaEntity toEntity() 
    {
        OrdenMedicaEntity ordenesEntity = super.toEntity();
        
        if (examenesMedicos != null)
        {
            List <ExamenMedicoEntity> examenesEntity = new ArrayList<>();
            
            for (ExamenMedicoDTO examenesDto : examenesMedicos)
            {
                examenesEntity.add(examenesDto.toEntity());
            }
            
            ordenesEntity.setExamenesMedicos(examenesEntity);
        }
        
        if (medicamentos != null)
        {
            List <MedicamentoEntity> medicamentosEntity = new ArrayList<>();
            
            for (MedicamentoDTO medicamwntoDto : medicamentos)
            {
                medicamentosEntity.add(medicamwntoDto.toEntity());
            }
            
            ordenesEntity.setMedicamentos(medicamentosEntity);
        }
        return ordenesEntity;
    }
}
