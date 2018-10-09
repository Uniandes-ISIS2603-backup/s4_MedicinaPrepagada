
package co.edu.uniandes.csw.medicinaPrepagada.dtos;

import co.edu.uniandes.csw.medicinaPrepagada.entities.FarmaciaEntity;
import co.edu.uniandes.csw.medicinaPrepagada.entities.MedicamentoEntity;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;


/**
 *
 * /**
 * Clase que extiende de {@link FarmaciaDTO} para manejar las relaciones entre los
 * FarmaciaDTO y otros DTOs. Para conocer el contenido de una Farmacia vaya a la
 * documentacion de {@link FarmaciaDTO}
 *
 *
 * @farmacia ncobos
 */

public class FarmaciaDetailDTO extends FarmaciaDTO implements Serializable {

    // relación  cero o muchos medicamentos
    private List<MedicamentoDTO> medicamentos;

    

    public FarmaciaDetailDTO() {
        super();
    }

    /**
     * Crea un objeto FarmaciaDetailDTO a partir de un objeto FarmaciaEntity
     * incluyendo los atributos de FarmaciaDTO.
     *
     * @param farmaciaEntity Entidad FarmaciaEntity desde la cual se va a crear el
     * nuevo objeto.
     *
     */
    public FarmaciaDetailDTO(FarmaciaEntity farmaciaEntity) {
        super(farmaciaEntity);
        if (farmaciaEntity != null) {
            medicamentos = new ArrayList<>();
            for (MedicamentoEntity entityMedicamentos : farmaciaEntity.getMedicamentos()) {
                medicamentos.add(new MedicamentoDTO(entityMedicamentos));
            }
        }
    }

    /**
     * Convierte un objeto FarmaciaDetailDTO a FarmaciaEntity incluyendo los
     * atributos de FarmaciaDTO.
     *
     * @return Nueva objeto FarmaciaEntity.
     *
     */
    @Override
    public FarmaciaEntity toEntity() {
        FarmaciaEntity farmaciaEntity = super.toEntity();
        if (medicamentos != null) {
            List<MedicamentoEntity> medicamentosEntity = new ArrayList<>();
            for (MedicamentoDTO dtoMedicamento : medicamentos) {
                medicamentosEntity.add(dtoMedicamento.toEntity());
            }
            farmaciaEntity.setMedicamentos(medicamentosEntity);
        }
        
        return farmaciaEntity;
    }

    /**
     * Obtiene la lista de libros del autor
     *
     * @return the medicamentos
     */
    public List<MedicamentoDTO> getMedicamentos() {
        return medicamentos;
    }

    /**
     * Modifica la lista de libros para el autor
     *
     * @param medicamentos the medicamentos to set
     */
    public void setMedicamentos(List<MedicamentoDTO> medicamentos) {
        this.medicamentos = medicamentos;
    }
    
    
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}


