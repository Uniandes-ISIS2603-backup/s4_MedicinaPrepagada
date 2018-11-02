/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.medicinaPrepagada.ejb;

import co.edu.uniandes.csw.medicinaPrepagada.entities.ExamenMedicoEntity;
import co.edu.uniandes.csw.medicinaPrepagada.entities.OrdenMedicaEntity;
import co.edu.uniandes.csw.medicinaPrepagada.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.medicinaPrepagada.persistence.ExamenMedicoPersistence;
import co.edu.uniandes.csw.medicinaPrepagada.persistence.OrdenMedicaPersistence;
import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clase que implementa la conexion con la persistencia para la relación entre
 * la entidad de ExamenMedico y OrdenMedica.
 *
 * @author ncobos
 */
@Stateless
public class ExamenMedicoOrdenMedicaLogic {
    private static final Logger LOGGER = Logger.getLogger(ExamenMedicoOrdenMedicaLogic.class.getName());

    @Inject
    private OrdenMedicaPersistence ordenMedicaPersistence;

    @Inject
    private ExamenMedicoPersistence examenMedicoPersistence;

    /**
     * Asocia un OrdenMedica existente a un ExamenMedico
     *
     * @param examenMedicosId Identificador de la instancia de ExamenMedico
     * @param ordenMedicasId Identificador de la instancia de OrdenMedica
     * @return Instancia de OrdenMedicaEntity que fue asociada a ExamenMedico
     */
    public OrdenMedicaEntity addOrdenMedica(Long examenMedicosId, Long ordenMedicasId) {
        LOGGER.log(Level.INFO, "Inicia proceso de asociarle un ordenMedica al autor con id = {0}", examenMedicosId);
        ExamenMedicoEntity examenMedicoEntity = examenMedicoPersistence.find(examenMedicosId);
        OrdenMedicaEntity ordenMedicaEntity = ordenMedicaPersistence.find(ordenMedicasId);
        ordenMedicaEntity.getExamenesMedicos().add(examenMedicoEntity);
        LOGGER.log(Level.INFO, "Termina proceso de asociarle un ordenMedica al autor con id = {0}", examenMedicosId);
        return ordenMedicaPersistence.find(ordenMedicasId);
    }

    /**
     * Obtiene una colección de instancias de OrdenMedicaEntity asociadas a una
     * instancia de ExamenMedico
     *
     * @param examenMedicosId Identificador de la instancia de ExamenMedico
     * @return Colección de instancias de OrdenMedicaEntity asociadas a la instancia de
     * ExamenMedico
     */
    public List<OrdenMedicaEntity> getOrdenMedicas(Long examenMedicosId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todos los ordenMedicas de la examenMedico con id = {0}", examenMedicosId);
        return examenMedicoPersistence.find(examenMedicosId).getOrdenesMedicas();
    }

    /**
     * Obtiene una instancia de OrdenMedicaEntity asociada a una instancia de ExamenMedico
     *
     * @param examenMedicosId Identificador de la instancia de ExamenMedico
     * @param ordenMedicasId Identificador de la instancia de OrdenMedica
     * @return La entidadd de Libro de la examenMedico
     * @throws BusinessLogicException Si el ordenMedica no está asociado al autor
     */
    public OrdenMedicaEntity getOrdenMedica(Long examenMedicosId, Long ordenMedicasId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar el ordenMedica con id = {0} de la examenMedico con id = " + examenMedicosId, ordenMedicasId);
        List<OrdenMedicaEntity> ordenMedicas = examenMedicoPersistence.find(examenMedicosId).getOrdenesMedicas();
        OrdenMedicaEntity ordenMedicaEntity = ordenMedicaPersistence.find(ordenMedicasId);
        int index = ordenMedicas.indexOf(ordenMedicaEntity);
        LOGGER.log(Level.INFO, "Termina proceso de consultar el ordenMedica con id = {0} de la examenMedico con id = " + examenMedicosId, ordenMedicasId);
        if (index >= 0) {
            return ordenMedicas.get(index);
        }
        throw new BusinessLogicException("El ordenMedica no está asociado al autor");
    }

    /**
     * Remplaza las instancias de OrdenMedica asociadas a una instancia de ExamenMedico
     *
     * @param examenMedicoId Identificador de la instancia de ExamenMedico
     * @param ordenMedicas Colección de instancias de OrdenMedicaEntity a asociar a instancia
     * de ExamenMedico
     * @return Nueva colección de OrdenMedicaEntity asociada a la instancia de ExamenMedico
     */
    public List<OrdenMedicaEntity> replaceOrdenMedicas(Long examenMedicoId, List<OrdenMedicaEntity> ordenMedicas) {
        LOGGER.log(Level.INFO, "Inicia proceso de reemplazar los ordenMedicas asocidos a la examenMedico con id = {0}", examenMedicoId);
        ExamenMedicoEntity examenMedicoEntity = examenMedicoPersistence.find(examenMedicoId);
        List<OrdenMedicaEntity> ordenMedicaList = ordenMedicaPersistence.findAll();
        for (OrdenMedicaEntity ordenMedica : ordenMedicaList) {
            if (ordenMedicas.contains(ordenMedica)) {
                if (!ordenMedica.getExamenesMedicos().contains(examenMedicoEntity)) {
                    ordenMedica.getExamenesMedicos().add(examenMedicoEntity);
                }
            } else {
                ordenMedica.getExamenesMedicos().remove(examenMedicoEntity);
            }
        }
        examenMedicoEntity.setOrdenesMedicas(ordenMedicas);
        LOGGER.log(Level.INFO, "Termina proceso de reemplazar los ordenMedicas asocidos a la examenMedico con id = {0}", examenMedicoId);
        return examenMedicoEntity.getOrdenesMedicas();
    }

    /**
     * Desasocia un OrdenMedica existente de un ExamenMedico existente
     *
     * @param examenMedicosId Identificador de la instancia de ExamenMedico
     * @param ordenMedicasId Identificador de la instancia de OrdenMedica
     */
    public void removeOrdenMedica(Long examenMedicosId, Long ordenMedicasId) {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar un ordenMedica de la examenMedico con id = {0}", examenMedicosId);
        ExamenMedicoEntity examenMedicoEntity = examenMedicoPersistence.find(examenMedicosId);
        OrdenMedicaEntity ordenMedicaEntity = ordenMedicaPersistence.find(ordenMedicasId);
        ordenMedicaEntity.getExamenesMedicos().remove(examenMedicoEntity);
        LOGGER.log(Level.INFO, "Termina proceso de borrar un ordenMedica de la examenMedico con id = {0}", examenMedicosId);
    }
    
}
