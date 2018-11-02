/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.medicinaPrepagada.dtos;

/**
 *
 * @author ncobos
 */
import co.edu.uniandes.csw.medicinaPrepagada.entities.ExamenMedicoEntity;
import co.edu.uniandes.csw.medicinaPrepagada.entities.LaboratorioEntity;
import co.edu.uniandes.csw.medicinaPrepagada.entities.OrdenMedicaEntity;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;


public class ExamenMedicoDetailDTO extends ExamenMedicoDTO implements Serializable
{
    
    private List<LaboratorioDTO> laboratorios;
    
    // relación  cero o muchos medicamentos
    private List<OrdenMedicaDTO> ordenes;
    
    
    public ExamenMedicoDetailDTO() 
    {
       super();
    }
    
     /**
     * Constructor para transformar un Entity a un DTO
     *
     * @param examenMedicoEntity La entidad de la examenMedico para transformar a DTO.
     */
    public ExamenMedicoDetailDTO(ExamenMedicoEntity examenMedicoEntity) {
        super(examenMedicoEntity);
        if (examenMedicoEntity != null && examenMedicoEntity.getLaboratorios() != null && examenMedicoEntity.getOrdenesMedicas() != null) {
                laboratorios = new ArrayList<>();
                for (LaboratorioEntity entityLaboratorio : examenMedicoEntity.getLaboratorios()) {
                    laboratorios.add(new LaboratorioDTO(entityLaboratorio));
                }
                
                ordenes = new ArrayList<>();
                for(OrdenMedicaEntity entityOrden : examenMedicoEntity.getOrdenesMedicas()) {
                    ordenes.add(new OrdenMedicaDTO(entityOrden));
                }
            }
        }
    
    
    public void setLaboratorios(List<LaboratorioDTO> pLabs)
    {
        this.laboratorios = pLabs;
    }
    
    
    public List<LaboratorioDTO> getLaboratorios ()
    {
        return this.laboratorios;
    }
    
    public void setOrdenes(List<OrdenMedicaDTO> pOrdenes)
    {
        this.ordenes = pOrdenes;
    }
    
    
    public List<OrdenMedicaDTO> getOrdenes ()
    {
        return this.ordenes;
    }
    
    
    
        @Override
    public String toString() 
    {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }  
}
