/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.medicinaPrepagada.ejb;

import co.edu.uniandes.csw.medicinaPrepagada.entities.FarmaciaEntity;
import co.edu.uniandes.csw.medicinaPrepagada.entities.MedicamentoEntity;
import co.edu.uniandes.csw.medicinaPrepagada.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.medicinaPrepagada.persistence.FarmaciaPersistence;
import co.edu.uniandes.csw.medicinaPrepagada.persistence.MedicamentoPersistence;
import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clase que implementa la conexion con la persistencia para la relación entre
 * la entidad de Farmacia y Medicamento.
 *
 * @farmacia ncobos
 */
@Stateless
public class FarmaciaMedicamentoLogic {
    
    private static final Logger LOGGER = Logger.getLogger(FarmaciaMedicamentoLogic.class.getName());

    @Inject
    private MedicamentoPersistence medicamentoPersistence;

    @Inject
    private FarmaciaPersistence farmaciaPersistence;

    /**
     * Asocia un Medicamento existente a un Farmacia
     *
     * @param farmaciasId Identificador de la instancia de Farmacia
     * @param medicamentosId Identificador de la instancia de Medicamento
     * @return Instancia de MedicamentoEntity que fue asociada a Farmacia
     */
    public MedicamentoEntity addMedicamento(Long farmaciasId, Long medicamentosId) {
        LOGGER.log(Level.INFO, "Inicia proceso de asociarle un medicamento al autor con id = {0}", farmaciasId);
        FarmaciaEntity farmaciaEntity = farmaciaPersistence.find(farmaciasId);
        MedicamentoEntity medicamentoEntity = medicamentoPersistence.find(medicamentosId);
        medicamentoEntity.getFarmacias().add(farmaciaEntity);
        LOGGER.log(Level.INFO, "Termina proceso de asociarle un medicamento al autor con id = {0}", farmaciasId);
        return medicamentoPersistence.find(medicamentosId);
    }

    /**
     * Obtiene una colección de instancias de MedicamentoEntity asociadas a una
     * instancia de Farmacia
     *
     * @param farmaciasId Identificador de la instancia de Farmacia
     * @return Colección de instancias de MedicamentoEntity asociadas a la instancia de
     * Farmacia
     */
    public List<MedicamentoEntity> getMedicamentos(Long farmaciasId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todos los medicamentos de la farmacia con id = {0}", farmaciasId);
        return farmaciaPersistence.find(farmaciasId).getMedicamentos();
    }

    /**
     * Obtiene una instancia de MedicamentoEntity asociada a una instancia de Farmacia
     *
     * @param farmaciasId Identificador de la instancia de Farmacia
     * @param medicamentosId Identificador de la instancia de Medicamento
     * @return La entidadd de Libro de la farmacia
     * @throws BusinessLogicException Si el medicamento no está asociado al autor
     */
    public MedicamentoEntity getMedicamento(Long farmaciasId, Long medicamentosId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar el medicamento con id = {0} de la farmacia con id = " + farmaciasId, medicamentosId);
        List<MedicamentoEntity> medicamentos = farmaciaPersistence.find(farmaciasId).getMedicamentos();
        MedicamentoEntity medicamentoEntity = medicamentoPersistence.find(medicamentosId);
        int index = medicamentos.indexOf(medicamentoEntity);
        LOGGER.log(Level.INFO, "Termina proceso de consultar el medicamento con id = {0} de la farmacia con id = " + farmaciasId, medicamentosId);
        if (index >= 0) {
            return medicamentos.get(index);
        }
        throw new BusinessLogicException("El medicamento no está asociado al autor");
    }

    /**
     * Remplaza las instancias de Medicamento asociadas a una instancia de Farmacia
     *
     * @param farmaciaId Identificador de la instancia de Farmacia
     * @param medicamentos Colección de instancias de MedicamentoEntity a asociar a instancia
     * de Farmacia
     * @return Nueva colección de MedicamentoEntity asociada a la instancia de Farmacia
     */
    public List<MedicamentoEntity> replaceMedicamentos(Long farmaciaId, List<MedicamentoEntity> medicamentos) {
        LOGGER.log(Level.INFO, "Inicia proceso de reemplazar los medicamentos asocidos a la farmacia con id = {0}", farmaciaId);
        FarmaciaEntity farmaciaEntity = farmaciaPersistence.find(farmaciaId);
        List<MedicamentoEntity> medicamentoList = medicamentoPersistence.findAll();
        for (MedicamentoEntity medicamento : medicamentoList) {
            if (medicamentos.contains(medicamento)) {
                if (!medicamento.getFarmacias().contains(farmaciaEntity)) {
                    medicamento.getFarmacias().add(farmaciaEntity);
                }
            } else {
                medicamento.getFarmacias().remove(farmaciaEntity);
            }
        }
        farmaciaEntity.setMedicamentos(medicamentos);
        LOGGER.log(Level.INFO, "Termina proceso de reemplazar los medicamentos asocidos a la farmacia con id = {0}", farmaciaId);
        return farmaciaEntity.getMedicamentos();
    }

    /**
     * Desasocia un Medicamento existente de un Farmacia existente
     *
     * @param farmaciasId Identificador de la instancia de Farmacia
     * @param medicamentosId Identificador de la instancia de Medicamento
     */
    public void removeMedicamento(Long farmaciasId, Long medicamentosId) {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar un medicamento de la farmacia con id = {0}", farmaciasId);
        FarmaciaEntity farmaciaEntity = farmaciaPersistence.find(farmaciasId);
        MedicamentoEntity medicamentoEntity = medicamentoPersistence.find(medicamentosId);
        medicamentoEntity.getFarmacias().remove(farmaciaEntity);
        LOGGER.log(Level.INFO, "Termina proceso de borrar un medicamento de la farmacia con id = {0}", farmaciasId);
    }
    
}
