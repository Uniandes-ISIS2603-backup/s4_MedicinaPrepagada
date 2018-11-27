package co.edu.uniandes.csw.medicinaPrepagada.ejb;

import co.edu.uniandes.csw.medicinaPrepagada.entities.FarmaciaEntity;
import co.edu.uniandes.csw.medicinaPrepagada.entities.MedicamentoEntity;
import co.edu.uniandes.csw.medicinaPrepagada.persistence.FarmaciaPersistence;
import co.edu.uniandes.csw.medicinaPrepagada.persistence.MedicamentoPersistence;
import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Stateless
/**
 * Clase que implementa la conexión con la persistencia para la relación entre
 * la entidad de Medicamento y Farmacia.
 *
 * @author ncobos
 */
public class MedicamentoFarmaciaLogic {
    
    private static final Logger LOGGER = Logger.getLogger(MedicamentoFarmaciaLogic.class.getName());

    @Inject
    private MedicamentoPersistence medicamentoPersistence;

    @Inject
    private FarmaciaPersistence farmaciaPersistence;

    /**
     * Asocia un Farmacia existente a un Medicamento
     *
     * @param medicamentosId Identificador de la instancia de Medicamento
     * @param farmaciasId Identificador de la instancia de Farmacia
     * @return Instancia de FarmaciaEntity que fue asociada a Medicamento
     */
    public FarmaciaEntity addFarmacia(Long medicamentosId, Long farmaciasId) {
        LOGGER.log(Level.INFO, "Inicia proceso de asociarle una farmacia al medicamento con id = {0}", medicamentosId);
        FarmaciaEntity farmaciaEntity = farmaciaPersistence.find(farmaciasId);
        MedicamentoEntity medicamentoEntity = medicamentoPersistence.find(medicamentosId);
        medicamentoEntity.getFarmacias().add(farmaciaEntity);
        LOGGER.log(Level.INFO, "Termina proceso de asociarle una farmacia al medicamento con id = {0}", medicamentosId);
        return farmaciaPersistence.find(farmaciasId);
    }

    /**
     * Obtiene una colección de instancias de FarmaciaEntity asociadas a una
     * instancia de Medicamento
     *
     * @param medicamentosId Identificador de la instancia de Medicamento
     * @return Colección de instancias de FarmaciaEntity asociadas a la instancia
     * de Medicamento
     */
    public List<FarmaciaEntity> getFarmacias(Long medicamentosId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todos las farmacias del medicamento con id = {0}", medicamentosId);
        return medicamentoPersistence.find(medicamentosId).getFarmacias();
    }

    /**
     * Obtiene una instancia de FarmaciaEntity asociada a una instancia de Medicamento
     *
     * @param medicamentosId Identificador de la instancia de Medicamento
     * @param farmaciasId Identificador de la instancia de Farmacia
     * @return La entidad del Autor asociada al farmacia
     */
    public FarmaciaEntity getFarmacia(Long medicamentosId, Long farmaciasId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar una farmacia del medicamento con id = {0}", medicamentosId);
        List<FarmaciaEntity> farmacias = medicamentoPersistence.find(medicamentosId).getFarmacias();
        FarmaciaEntity farmaciaEntity = farmaciaPersistence.find(farmaciasId);
        int index = farmacias.indexOf(farmaciaEntity);
        LOGGER.log(Level.INFO, "Termina proceso de consultar una farmacia del medicamento con id = {0}", medicamentosId);
        if (index >= 0) {
            return farmacias.get(index);
        }
        return null;
    }

    
    /**
     * Desasocia un Farmacia existente de un Medicamento existente
     *
     * @param medicamentosId Identificador de la instancia de Medicamento
     * @param farmaciasId Identificador de la instancia de Farmacia
     */
    public void removeFarmacia(Long medicamentosId, Long farmaciasId) {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar una farmacia del medicamento con id = {0}", medicamentosId);
        FarmaciaEntity farmaciaEntity = farmaciaPersistence.find(farmaciasId);
        MedicamentoEntity medicamentoEntity = medicamentoPersistence.find(medicamentosId);
        medicamentoEntity.getFarmacias().remove(farmaciaEntity);
        LOGGER.log(Level.INFO, "Termina proceso de borrar una farmacia del medicamento con id = {0}", medicamentosId);
    }
    
}
