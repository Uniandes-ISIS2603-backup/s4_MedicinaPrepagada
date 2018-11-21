/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.medicinaPrepagada.ejb;


import co.edu.uniandes.csw.medicinaPrepagada.entities.LaboratorioEntity;
import co.edu.uniandes.csw.medicinaPrepagada.entities.ExamenMedicoEntity;
import co.edu.uniandes.csw.medicinaPrepagada.persistence.LaboratorioPersistence;
import co.edu.uniandes.csw.medicinaPrepagada.persistence.ExamenMedicoPersistence;
import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Stateless
/**
 * Clase que implementa la conexión con la persistencia para la relación entre
 * la entidad de Examen y Laboratorio.
 *
 * @author ncobos
 */
public class ExamenLaboratorioLogic {
    
    private static final Logger LOGGER = Logger.getLogger(ExamenLaboratorioLogic.class.getName());

    @Inject
    private ExamenMedicoPersistence examenPersistence;

    @Inject
    private LaboratorioPersistence laboratorioPersistence;

    /**
     * Asocia un Laboratorio existente a un Examen
     *
     * @param examensId Identificador de la instancia de Examen
     * @param laboratoriosId Identificador de la instancia de Laboratorio
     * @return Instancia de LaboratorioEntity que fue asociada a Examen
     */
    public LaboratorioEntity addLaboratorio(Long examensId, Long laboratoriosId) {
        LOGGER.log(Level.INFO, "Inicia proceso de asociarle una laboratorio al examen con id = {0}", examensId);
        LaboratorioEntity laboratorioEntity = laboratorioPersistence.find(laboratoriosId);
        ExamenMedicoEntity examenEntity = examenPersistence.find(examensId);
        examenEntity.getLaboratorios().add(laboratorioEntity);
        LOGGER.log(Level.INFO, "Termina proceso de asociarle una laboratorio al examen con id = {0}", examensId);
        return laboratorioPersistence.find(laboratoriosId);
    }

    /**
     * Obtiene una colección de instancias de LaboratorioEntity asociadas a una
     * instancia de Examen
     *
     * @param examensId Identificador de la instancia de Examen
     * @return Colección de instancias de LaboratorioEntity asociadas a la instancia
     * de Examen
     */
    public List<LaboratorioEntity> getLaboratorios(Long examensId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todos las laboratorios del examen con id = {0}", examensId);
        return examenPersistence.find(examensId).getLaboratorios();
    }

    /**
     * Obtiene una instancia de LaboratorioEntity asociada a una instancia de Examen
     *
     * @param examensId Identificador de la instancia de Examen
     * @param laboratoriosId Identificador de la instancia de Laboratorio
     * @return La entidad del Autor asociada al laboratorio
     */
    public LaboratorioEntity getLaboratorio(Long examensId, Long laboratoriosId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar una laboratorio del examen con id = {0}", examensId);
        List<LaboratorioEntity> laboratorios = examenPersistence.find(examensId).getLaboratorios();
        LaboratorioEntity laboratorioEntity = laboratorioPersistence.find(laboratoriosId);
        int index = laboratorios.indexOf(laboratorioEntity);
        LOGGER.log(Level.INFO, "Termina proceso de consultar una laboratorio del examen con id = {0}", examensId);
        if (index >= 0) {
            return laboratorios.get(index);
        }
        return null;
    }

    /**
     * Remplaza las instancias de Laboratorio asociadas a una instancia de Examen
     *
     * @param examensId Identificador de la instancia de Examen
     * @param list Colección de instancias de LaboratorioEntity a asociar a instancia
     * de Examen
     * @return Nueva colección de LaboratorioEntity asociada a la instancia de Examen
     */
    public List<LaboratorioEntity> replaceLaboratorios(Long examensId, List<LaboratorioEntity> list) {
        LOGGER.log(Level.INFO, "Inicia proceso de reemplazar las laboratorios del examen con id = {0}", examensId);
        ExamenMedicoEntity examenEntity = examenPersistence.find(examensId);
        examenEntity.setLaboratorios(list);
        LOGGER.log(Level.INFO, "Termina proceso de reemplazar las laboratorios del examen con id = {0}", examensId);
        return examenPersistence.find(examensId).getLaboratorios();
    }

    /**
     * Desasocia un Laboratorio existente de un Examen existente
     *
     * @param examensId Identificador de la instancia de Examen
     * @param laboratoriosId Identificador de la instancia de Laboratorio
     */
    public void removeLaboratorio(Long examensId, Long laboratoriosId) {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar una laboratorio del examen con id = {0}", examensId);
        LaboratorioEntity laboratorioEntity = laboratorioPersistence.find(laboratoriosId);
        ExamenMedicoEntity examenEntity = examenPersistence.find(examensId);
        examenEntity.getLaboratorios().remove(laboratorioEntity);
        LOGGER.log(Level.INFO, "Termina proceso de borrar un laboratorio del examen con id = {0}", examensId);
    }
    
}


