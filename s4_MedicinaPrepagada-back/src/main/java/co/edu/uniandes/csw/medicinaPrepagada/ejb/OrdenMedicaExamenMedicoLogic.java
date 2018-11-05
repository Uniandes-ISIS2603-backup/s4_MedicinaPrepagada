/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.medicinaPrepagada.ejb;

import co.edu.uniandes.csw.medicinaPrepagada.entities.ExamenMedicoEntity;
import co.edu.uniandes.csw.medicinaPrepagada.entities.OrdenMedicaEntity;
import co.edu.uniandes.csw.medicinaPrepagada.persistence.ExamenMedicoPersistence;
import co.edu.uniandes.csw.medicinaPrepagada.persistence.OrdenMedicaPersistence;
import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Stateless
/**
 * Clase que implementa la conexión con la persistencia para la relación entre
 * la entidad de OrdenMedica y ExamenMedico.
 *
 * @author ncobos
 */
public class OrdenMedicaExamenMedicoLogic {
 private static final Logger LOGGER = Logger.getLogger(OrdenMedicaExamenMedicoLogic.class.getName());

    @Inject
    private OrdenMedicaPersistence ordenMedicaPersistence;

    @Inject
    private ExamenMedicoPersistence examenMedicoPersistence;

    /**
     * Asocia un ExamenMedico existente a un OrdenMedica
     *
     * @param ordenMedicasId Identificador de la instancia de OrdenMedica
     * @param examenMedicosId Identificador de la instancia de ExamenMedico
     * @return Instancia de ExamenMedicoEntity que fue asociada a OrdenMedica
     */
    public ExamenMedicoEntity addExamenMedico(Long ordenMedicasId, Long examenMedicosId) {
        LOGGER.log(Level.INFO, "Inicia proceso de asociarle una examenMedico al ordenMedica con id = {0}", ordenMedicasId);
        ExamenMedicoEntity examenMedicoEntity = examenMedicoPersistence.find(examenMedicosId);
        OrdenMedicaEntity ordenMedicaEntity = ordenMedicaPersistence.find(ordenMedicasId);
        ordenMedicaEntity.getExamenesMedicos().add(examenMedicoEntity);
        LOGGER.log(Level.INFO, "Termina proceso de asociarle una examenMedico al ordenMedica con id = {0}", ordenMedicasId);
        return examenMedicoPersistence.find(examenMedicosId);
    }

    /**
     * Obtiene una colección de instancias de ExamenMedicoEntity asociadas a una
     * instancia de OrdenMedica
     *
     * @param ordenMedicasId Identificador de la instancia de OrdenMedica
     * @return Colección de instancias de ExamenMedicoEntity asociadas a la instancia
     * de OrdenMedica
     */
    public List<ExamenMedicoEntity> getExamenesMedicos(Long ordenMedicasId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todos las examenMedicos del ordenMedica con id = {0}", ordenMedicasId);
        return ordenMedicaPersistence.find(ordenMedicasId).getExamenesMedicos();
    }

    /**
     * Obtiene una instancia de ExamenMedicoEntity asociada a una instancia de OrdenMedica
     *
     * @param ordenMedicasId Identificador de la instancia de OrdenMedica
     * @param examenMedicosId Identificador de la instancia de ExamenMedico
     * @return La entidad del Autor asociada al examenMedico
     */
    public ExamenMedicoEntity getExamenMedico(Long ordenMedicasId, Long examenMedicosId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar una examenMedico del ordenMedica con id = {0}", ordenMedicasId);
        List<ExamenMedicoEntity> examenMedicos = ordenMedicaPersistence.find(ordenMedicasId).getExamenesMedicos();
        ExamenMedicoEntity examenMedicoEntity = examenMedicoPersistence.find(examenMedicosId);
        int index = examenMedicos.indexOf(examenMedicoEntity);
        LOGGER.log(Level.INFO, "Termina proceso de consultar una examenMedico del ordenMedica con id = {0}", ordenMedicasId);
        if (index >= 0) {
            return examenMedicos.get(index);
        }
        return null;
    }

    /**
     * Remplaza las instancias de ExamenMedico asociadas a una instancia de OrdenMedica
     *
     * @param ordenMedicasId Identificador de la instancia de OrdenMedica
     * @param list Colección de instancias de ExamenMedicoEntity a asociar a instancia
     * de OrdenMedica
     * @return Nueva colección de ExamenMedicoEntity asociada a la instancia de OrdenMedica
     */
    public List<ExamenMedicoEntity> replaceExamenMedicos(Long ordenMedicasId, List<ExamenMedicoEntity> list) {
        LOGGER.log(Level.INFO, "Inicia proceso de reemplazar las examenMedicos del ordenMedica con id = {0}", ordenMedicasId);
        OrdenMedicaEntity ordenMedicaEntity = ordenMedicaPersistence.find(ordenMedicasId);
        ordenMedicaEntity.setExamenesMedicos(list);
        LOGGER.log(Level.INFO, "Termina proceso de reemplazar las examenMedicos del ordenMedica con id = {0}", ordenMedicasId);
        return ordenMedicaPersistence.find(ordenMedicasId).getExamenesMedicos();
    }

    /**
     * Desasocia un ExamenMedico existente de un OrdenMedica existente
     *
     * @param ordenMedicasId Identificador de la instancia de OrdenMedica
     * @param examenMedicosId Identificador de la instancia de ExamenMedico
     */
    public void removeExamenMedico(Long ordenMedicasId, Long examenMedicosId) {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar una examenMedico del ordenMedica con id = {0}", ordenMedicasId);
        ExamenMedicoEntity examenMedicoEntity = examenMedicoPersistence.find(examenMedicosId);
        OrdenMedicaEntity ordenMedicaEntity = ordenMedicaPersistence.find(ordenMedicasId);
        ordenMedicaEntity.getExamenesMedicos().remove(examenMedicoEntity);
        LOGGER.log(Level.INFO, "Termina proceso de borrar una examenMedico del ordenMedica con id = {0}", ordenMedicasId);
    }
       
}
