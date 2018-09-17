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
import co.edu.uniandes.csw.medicinaPrepagada.entities.FarmaciaEntity;
import co.edu.uniandes.csw.medicinaPrepagada.entities.MedicamentoEntity;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;


public class MedicamentoDetailDTO extends MedicamentoDTO implements Serializable
{
    
    private List<FarmaciaDTO> farmacias;
    
    
    
    
    public MedicamentoDetailDTO() 
    {
        super();
    }
    
     /**
     * Constructor para transformar un Entity a un DTO
     *
     * @param medicamentoEntity La entidad de la medicamento para transformar a DTO.
     */
    public MedicamentoDetailDTO(MedicamentoEntity medicamentoEntity) {
        super(medicamentoEntity);
        if (medicamentoEntity != null) {
            if (medicamentoEntity.getFarmacias() != null) {
                farmacias = new ArrayList<>();
                for (FarmaciaEntity entityFarmacia : medicamentoEntity.getFarmacias()) {
                    farmacias.add(new FarmaciaDTO(entityFarmacia));
                }
            }
        }
    }

    /**
     * Transformar un DTO a un Entity
     *
     * @return El DTO de la medicamento para transformar a Entity
     */
    @Override
    public MedicamentoEntity toEntity() {
        MedicamentoEntity medicamentoEntity = super.toEntity();
        if (farmacias != null) {
            List<FarmaciaEntity> farmaciasEntity = new ArrayList<>();
            for (FarmaciaDTO dtoFarmacia : farmacias) {
                farmaciasEntity.add(dtoFarmacia.toEntity());
            }
            medicamentoEntity.setFarmacias(farmaciasEntity);
        }
        return medicamentoEntity;
    }

    
    public void setFarmacias(List<FarmaciaDTO> pFarmacias)
    {
        this.farmacias = pFarmacias;
    }
    
    
    public List<FarmaciaDTO> getFarmacias ()
    {
        return this.farmacias;
    }
    
    
        @Override
    public String toString() 
    {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }  
}
