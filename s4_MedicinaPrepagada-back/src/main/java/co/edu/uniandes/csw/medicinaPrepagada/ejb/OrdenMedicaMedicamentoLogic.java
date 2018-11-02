/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.medicinaPrepagada.ejb;

import co.edu.uniandes.csw.medicinaPrepagada.entities.MedicamentoEntity;
import co.edu.uniandes.csw.medicinaPrepagada.entities.OrdenMedicaEntity;
import co.edu.uniandes.csw.medicinaPrepagada.persistence.MedicamentoPersistence;
import co.edu.uniandes.csw.medicinaPrepagada.persistence.OrdenMedicaPersistence;
import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Stateless
/**
 * Clase que implementa la conexión con la persistencia para la relación entre
 * la entidad de OrdenMedica y Medicamento.
 *
 * @author ncobos
 */
public class OrdenMedicaMedicamentoLogic {
    
    private static final Logger LOGGER = Logger.getLogger(OrdenMedicaMedicamentoLogic.class.getName());

    @Inject
    private OrdenMedicaPersistence ordenMedicaPersistence;

    @Inject
    private MedicamentoPersistence medicamentoPersistence;

    /**
     * Asocia un Medicamento existente a un OrdenMedica
     *
     * @param ordenMedicasId Identificador de la instancia de OrdenMedica
     * @param medicamentosId Identificador de la instancia de Medicamento
     * @return Instancia de MedicamentoEntity que fue asociada a OrdenMedica
     */
    public MedicamentoEntity addMedicamento(Long ordenMedicasId, Long medicamentosId) {
        LOGGER.log(Level.INFO, "Inicia proceso de asociarle una medicamento al ordenMedica con id = {0}", ordenMedicasId);
        MedicamentoEntity medicamentoEntity = medicamentoPersistence.find(medicamentosId);
        OrdenMedicaEntity ordenMedicaEntity = ordenMedicaPersistence.find(ordenMedicasId);
        ordenMedicaEntity.getMedicamentos().add(medicamentoEntity);
        LOGGER.log(Level.INFO, "Termina proceso de asociarle una medicamento al ordenMedica con id = {0}", ordenMedicasId);
        return medicamentoPersistence.find(medicamentosId);
    }

    /**
     * Obtiene una colección de instancias de MedicamentoEntity asociadas a una
     * instancia de OrdenMedica
     *
     * @param ordenMedicasId Identificador de la instancia de OrdenMedica
     * @return Colección de instancias de MedicamentoEntity asociadas a la instancia
     * de OrdenMedica
     */
    public List<MedicamentoEntity> getMedicamentos(Long ordenMedicasId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todos las medicamentos del ordenMedica con id = {0}", ordenMedicasId);
        return ordenMedicaPersistence.find(ordenMedicasId).getMedicamentos();
    }

    /**
     * Obtiene una instancia de MedicamentoEntity asociada a una instancia de OrdenMedica
     *
     * @param ordenMedicasId Identificador de la instancia de OrdenMedica
     * @param medicamentosId Identificador de la instancia de Medicamento
     * @return La entidad del Autor asociada al medicamento
     */
    public MedicamentoEntity getMedicamento(Long ordenMedicasId, Long medicamentosId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar una medicamento del ordenMedica con id = {0}", ordenMedicasId);
        List<MedicamentoEntity> medicamentos = ordenMedicaPersistence.find(ordenMedicasId).getMedicamentos();
        MedicamentoEntity medicamentoEntity = medicamentoPersistence.find(medicamentosId);
        int index = medicamentos.indexOf(medicamentoEntity);
        LOGGER.log(Level.INFO, "Termina proceso de consultar una medicamento del ordenMedica con id = {0}", ordenMedicasId);
        if (index >= 0) {
            return medicamentos.get(index);
        }
        return null;
    }

    /**
     * Remplaza las instancias de Medicamento asociadas a una instancia de OrdenMedica
     *
     * @param ordenMedicasId Identificador de la instancia de OrdenMedica
     * @param list Colección de instancias de MedicamentoEntity a asociar a instancia
     * de OrdenMedica
     * @return Nueva colección de MedicamentoEntity asociada a la instancia de OrdenMedica
     */
    public List<MedicamentoEntity> replaceMedicamentos(Long ordenMedicasId, List<MedicamentoEntity> list) {
        LOGGER.log(Level.INFO, "Inicia proceso de reemplazar las medicamentos del ordenMedica con id = {0}", ordenMedicasId);
        OrdenMedicaEntity ordenMedicaEntity = ordenMedicaPersistence.find(ordenMedicasId);
        ordenMedicaEntity.setMedicamentos(list);
        LOGGER.log(Level.INFO, "Termina proceso de reemplazar las medicamentos del ordenMedica con id = {0}", ordenMedicasId);
        return ordenMedicaPersistence.find(ordenMedicasId).getMedicamentos();
    }

    /**
     * Desasocia un Medicamento existente de un OrdenMedica existente
     *
     * @param ordenMedicasId Identificador de la instancia de OrdenMedica
     * @param medicamentosId Identificador de la instancia de Medicamento
     */
    public void removeMedicamento(Long ordenMedicasId, Long medicamentosId) {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar una medicamento del ordenMedica con id = {0}", ordenMedicasId);
        MedicamentoEntity medicamentoEntity = medicamentoPersistence.find(medicamentosId);
        OrdenMedicaEntity ordenMedicaEntity = ordenMedicaPersistence.find(ordenMedicasId);
        ordenMedicaEntity.getMedicamentos().remove(medicamentoEntity);
        LOGGER.log(Level.INFO, "Termina proceso de borrar una medicamento del ordenMedica con id = {0}", ordenMedicasId);
    }
    
}
