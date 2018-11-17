/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.medicinaPrepagada.ejb;

import co.edu.uniandes.csw.medicinaPrepagada.entities.LaboratorioEntity;
import co.edu.uniandes.csw.medicinaPrepagada.entities.ExamenMedicoEntity;
import co.edu.uniandes.csw.medicinaPrepagada.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.medicinaPrepagada.persistence.LaboratorioPersistence;
import co.edu.uniandes.csw.medicinaPrepagada.persistence.ExamenMedicoPersistence;
import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clase que implementa la conexion con la persistencia para la relación entre
 * la entidad de Laboratorio y Examen.
 *
 * @author ncobos
 */
@Stateless
public class LaboratorioExamenLogic {
    
    private static final Logger LOGGER = Logger.getLogger(LaboratorioExamenLogic.class.getName());

    @Inject
    private ExamenMedicoPersistence examenPersistence;

    @Inject
    private LaboratorioPersistence laboratorioPersistence;

    /**
     * Asocia un Examen existente a un Laboratorio
     *
     * @param laboratoriosId Identificador de la instancia de Laboratorio
     * @param examensId Identificador de la instancia de Examen
     * @return Instancia de ExamenMedicoEntity que fue asociada a Laboratorio
     */
    public ExamenMedicoEntity addExamen(Long laboratoriosId, Long examensId) {
        LOGGER.log(Level.INFO, "Inicia proceso de asociarle un examen al autor con id = {0}", laboratoriosId);
        LaboratorioEntity laboratorioEntity = laboratorioPersistence.find(laboratoriosId);
        ExamenMedicoEntity examenEntity = examenPersistence.find(examensId);
        examenEntity.getLaboratorios().add(laboratorioEntity);
        LOGGER.log(Level.INFO, "Termina proceso de asociarle un examen al autor con id = {0}", laboratoriosId);
        return examenPersistence.find(examensId);
    }

    /**
     * Obtiene una colección de instancias de ExamenMedicoEntity asociadas a una
     * instancia de Laboratorio
     *
     * @param laboratoriosId Identificador de la instancia de Laboratorio
     * @return Colección de instancias de ExamenMedicoEntity asociadas a la instancia de
     * Laboratorio
     */
    public List<ExamenMedicoEntity> getExamens(Long laboratoriosId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todos los examens de la laboratorio con id = {0}", laboratoriosId);
        return laboratorioPersistence.find(laboratoriosId).getExamens();
    }

    /**
     * Obtiene una instancia de ExamenMedicoEntity asociada a una instancia de Laboratorio
     *
     * @param laboratoriosId Identificador de la instancia de Laboratorio
     * @param examensId Identificador de la instancia de Examen
     * @return La entidadd de Libro de la laboratorio
     * @throws BusinessLogicException Si el examen no está asociado al autor
     */
    public ExamenMedicoEntity getExamen(Long laboratoriosId, Long examensId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar el examen con id = {0} de la laboratorio con id = " + laboratoriosId, examensId);
        List<ExamenMedicoEntity> examens = laboratorioPersistence.find(laboratoriosId).getExamens();
        ExamenMedicoEntity examenEntity = examenPersistence.find(examensId);
        int index = examens.indexOf(examenEntity);
        LOGGER.log(Level.INFO, "Termina proceso de consultar el examen con id = {0} de la laboratorio con id = " + laboratoriosId, examensId);
        if (index >= 0) {
            return examens.get(index);
        }
        throw new BusinessLogicException("El examen no está asociado al autor");
    }

    /**
     * Remplaza las instancias de Examen asociadas a una instancia de Laboratorio
     *
     * @param laboratorioId Identificador de la instancia de Laboratorio
     * @param examens Colección de instancias de ExamenMedicoEntity a asociar a instancia
     * de Laboratorio
     * @return Nueva colección de ExamenMedicoEntity asociada a la instancia de Laboratorio
     */
    public List<ExamenMedicoEntity> replaceExamens(Long laboratorioId, List<ExamenMedicoEntity> examens) {
        LOGGER.log(Level.INFO, "Inicia proceso de reemplazar los examens asocidos a la laboratorio con id = {0}", laboratorioId);
        LaboratorioEntity laboratorioEntity = laboratorioPersistence.find(laboratorioId);
        List<ExamenMedicoEntity> examenList = examenPersistence.findAll();
        for (ExamenMedicoEntity examen : examenList) {
            if (examens.contains(examen)) {
                if (!examen.getLaboratorios().contains(laboratorioEntity)) {
                    examen.getLaboratorios().add(laboratorioEntity);
                }
            } else {
                examen.getLaboratorios().remove(laboratorioEntity);
            }
        }
        laboratorioEntity.setExamens(examens);
        LOGGER.log(Level.INFO, "Termina proceso de reemplazar los examens asocidos a la laboratorio con id = {0}", laboratorioId);
        return laboratorioEntity.getExamens();
    }

    /**
     * Desasocia un Examen existente de un Laboratorio existente
     *
     * @param laboratoriosId Identificador de la instancia de Laboratorio
     * @param examensId Identificador de la instancia de Examen
     */
    public void removeExamen(Long laboratoriosId, Long examensId) {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar un examen de la laboratorio con id = {0}", laboratoriosId);
        LaboratorioEntity laboratorioEntity = laboratorioPersistence.find(laboratoriosId);
        ExamenMedicoEntity examenEntity = examenPersistence.find(examensId);
        examenEntity.getLaboratorios().remove(laboratorioEntity);
        LOGGER.log(Level.INFO, "Termina proceso de borrar un examen de la laboratorio con id = {0}", laboratoriosId);
    }
    
}

