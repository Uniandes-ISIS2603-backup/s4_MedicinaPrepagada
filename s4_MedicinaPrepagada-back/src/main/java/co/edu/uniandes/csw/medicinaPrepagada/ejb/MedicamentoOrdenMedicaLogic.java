/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.medicinaPrepagada.ejb;

import co.edu.uniandes.csw.medicinaPrepagada.entities.MedicamentoEntity;
import co.edu.uniandes.csw.medicinaPrepagada.entities.OrdenMedicaEntity;
import co.edu.uniandes.csw.medicinaPrepagada.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.medicinaPrepagada.persistence.MedicamentoPersistence;
import co.edu.uniandes.csw.medicinaPrepagada.persistence.OrdenMedicaPersistence;
import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clase que implementa la conexion con la persistencia para la relación entre
 * la entidad de Medicamento y OrdenMedica.
 *
 * @author ncobos
 */
@Stateless
public class MedicamentoOrdenMedicaLogic {
    
    private static final Logger LOGGER = Logger.getLogger(MedicamentoOrdenMedicaLogic.class.getName());

    @Inject
    private OrdenMedicaPersistence ordenMedicaPersistence;

    @Inject
    private MedicamentoPersistence medicamentoPersistence;

    /**
     * Asocia un OrdenMedica existente a un Medicamento
     *
     * @param medicamentosId Identificador de la instancia de Medicamento
     * @param ordenMedicasId Identificador de la instancia de OrdenMedica
     * @return Instancia de OrdenMedicaEntity que fue asociada a Medicamento
     */
    public OrdenMedicaEntity addOrdenMedica(Long medicamentosId, Long ordenMedicasId) {
        LOGGER.log(Level.INFO, "Inicia proceso de asociarle un ordenMedica al autor con id = {0}", medicamentosId);
        MedicamentoEntity medicamentoEntity = medicamentoPersistence.find(medicamentosId);
        OrdenMedicaEntity ordenMedicaEntity = ordenMedicaPersistence.find(ordenMedicasId);
        ordenMedicaEntity.getMedicamentos().add(medicamentoEntity);
        LOGGER.log(Level.INFO, "Termina proceso de asociarle un ordenMedica al autor con id = {0}", medicamentosId);
        return ordenMedicaPersistence.find(ordenMedicasId);
    }

    /**
     * Obtiene una colección de instancias de OrdenMedicaEntity asociadas a una
     * instancia de Medicamento
     *
     * @param medicamentosId Identificador de la instancia de Medicamento
     * @return Colección de instancias de OrdenMedicaEntity asociadas a la instancia de
     * Medicamento
     */
    public List<OrdenMedicaEntity> getOrdenMedicas(Long medicamentosId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todos los ordenMedicas de la medicamento con id = {0}", medicamentosId);
        return medicamentoPersistence.find(medicamentosId).getOrdenesMedicas();
    }

    /**
     * Obtiene una instancia de OrdenMedicaEntity asociada a una instancia de Medicamento
     *
     * @param medicamentosId Identificador de la instancia de Medicamento
     * @param ordenMedicasId Identificador de la instancia de OrdenMedica
     * @return La entidadd de Libro de la medicamento
     * @throws BusinessLogicException Si el ordenMedica no está asociado al autor
     */
    public OrdenMedicaEntity getOrdenMedica(Long medicamentosId, Long ordenMedicasId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar el ordenMedica con id = {0} de la medicamento con id = " + medicamentosId, ordenMedicasId);
        List<OrdenMedicaEntity> ordenMedicas = medicamentoPersistence.find(medicamentosId).getOrdenesMedicas();
        OrdenMedicaEntity ordenMedicaEntity = ordenMedicaPersistence.find(ordenMedicasId);
        int index = ordenMedicas.indexOf(ordenMedicaEntity);
        LOGGER.log(Level.INFO, "Termina proceso de consultar el ordenMedica con id = {0} de la medicamento con id = " + medicamentosId, ordenMedicasId);
        if (index >= 0) {
            return ordenMedicas.get(index);
        }
        throw new BusinessLogicException("El ordenMedica no está asociado al autor");
    }

    /**
     * Remplaza las instancias de OrdenMedica asociadas a una instancia de Medicamento
     *
     * @param medicamentoId Identificador de la instancia de Medicamento
     * @param ordenMedicas Colección de instancias de OrdenMedicaEntity a asociar a instancia
     * de Medicamento
     * @return Nueva colección de OrdenMedicaEntity asociada a la instancia de Medicamento
     */
    public List<OrdenMedicaEntity> replaceOrdenMedicas(Long medicamentoId, List<OrdenMedicaEntity> ordenMedicas) {
        LOGGER.log(Level.INFO, "Inicia proceso de reemplazar los ordenMedicas asocidos a la medicamento con id = {0}", medicamentoId);
        MedicamentoEntity medicamentoEntity = medicamentoPersistence.find(medicamentoId);
        List<OrdenMedicaEntity> ordenMedicaList = ordenMedicaPersistence.findAll();
        for (OrdenMedicaEntity ordenMedica : ordenMedicaList) {
            if (ordenMedicas.contains(ordenMedica)) {
                if (!ordenMedica.getMedicamentos().contains(medicamentoEntity)) {
                    ordenMedica.getMedicamentos().add(medicamentoEntity);
                }
            } else {
                ordenMedica.getMedicamentos().remove(medicamentoEntity);
            }
        }
        medicamentoEntity.setOrdenesMedicas(ordenMedicas);
        LOGGER.log(Level.INFO, "Termina proceso de reemplazar los ordenMedicas asocidos a la medicamento con id = {0}", medicamentoId);
        return medicamentoEntity.getOrdenesMedicas();
    }

    /**
     * Desasocia un OrdenMedica existente de un Medicamento existente
     *
     * @param medicamentosId Identificador de la instancia de Medicamento
     * @param ordenMedicasId Identificador de la instancia de OrdenMedica
     */
    public void removeOrdenMedica(Long medicamentosId, Long ordenMedicasId) {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar un ordenMedica de la medicamento con id = {0}", medicamentosId);
        MedicamentoEntity medicamentoEntity = medicamentoPersistence.find(medicamentosId);
        OrdenMedicaEntity ordenMedicaEntity = ordenMedicaPersistence.find(ordenMedicasId);
        ordenMedicaEntity.getMedicamentos().remove(medicamentoEntity);
        LOGGER.log(Level.INFO, "Termina proceso de borrar un ordenMedica de la medicamento con id = {0}", medicamentosId);
    }
    
}
