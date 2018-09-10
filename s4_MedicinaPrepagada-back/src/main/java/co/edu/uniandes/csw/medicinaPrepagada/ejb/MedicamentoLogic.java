/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.medicinaPrepagada.ejb;

import co.edu.uniandes.csw.medicinaPrepagada.entities.MedicamentoEntity;
import co.edu.uniandes.csw.medicinaPrepagada.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.medicinaPrepagada.persistence.MedicamentoPersistence;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * Clase que implementa la conexion con la persistencia para la entidad de
 * Medicamento.
 *
 * @author ncobos
 */
@Stateless
public class MedicamentoLogic {
    
    private static final Logger LOGGER = Logger.getLogger(MedicamentoLogic.class.getName());

    @Inject
    private MedicamentoPersistence persistence; // Variable para acceder a la persistencia de la aplicación. Es una inyección de dependencias.

    /**
     * Crea una medicamento en la persistencia.
     *
     * @param medicamentoEntity La entidad que representa la medicamento a
     * persistir.
     * @return La entiddad de la medicamento luego de persistirla.
     * @throws BusinessLogicException Si la medicamento a persistir ya existe.
     */
    public MedicamentoEntity createMedicamento(MedicamentoEntity medicamentoEntity) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de creación de la medicamento");
        // Verifica la regla de negocio que dice que no puede haber dos medicamentos con el mismo nombre
        if (persistence.findByNombre(medicamentoEntity.getNombre()) != null) {
            throw new BusinessLogicException("Ya existe un Medicamento con el nombre \"" + medicamentoEntity.getNombre() + "\"");
        }
        // Invoca la persistencia para crear el medicamento
        persistence.create(medicamentoEntity);
        LOGGER.log(Level.INFO, "Termina proceso de creación del medicamento");
        return medicamentoEntity;
    }

    /**
     *
     * Obtener todas las medicamentos existentes en la base de datos.
     *
     * @return una lista de medicamentos.
     */
    public List<MedicamentoEntity> getMedicamentos() {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todas las medicamentos");
        // Note que, por medio de la inyección de dependencias se llama al método "findAll()" que se encuentra en la persistencia.
        List<MedicamentoEntity> medicamentos = persistence.findAll();
        LOGGER.log(Level.INFO, "Termina proceso de consultar todas las medicamentos");
        return medicamentos;
    }

    /**
     *
     * Obtener una medicamento por medio de su id.
     *
     * @param medicamentosId: id de la medicamento para ser buscada.
     * @return la medicamento solicitada por medio de su id.
     */
    public MedicamentoEntity getMedicamento(Long medicamentosId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar la medicamento con id = {0}", medicamentosId);
        // Note que, por medio de la inyección de dependencias se llama al método "find(id)" que se encuentra en la persistencia.
        MedicamentoEntity medicamentoEntity = persistence.find(medicamentosId);
        if (medicamentoEntity == null) {
            LOGGER.log(Level.SEVERE, "La medicamento con el id = {0} no existe", medicamentosId);
        }
        LOGGER.log(Level.INFO, "Termina proceso de consultar la medicamento con id = {0}", medicamentosId);
        return medicamentoEntity;
    }

    /**
     *
     * Actualizar una medicamento.
     *
     * @param medicamentosId: id de la medicamento para buscarla en la base de
     * datos.
     * @param medicamentoEntity: medicamento con los cambios para ser actualizada,
     * por ejemplo el nombre.
     * @return la medicamento con los cambios actualizados en la base de datos.
     */
    public MedicamentoEntity updateMedicamento(Long medicamentosId, MedicamentoEntity medicamentoEntity) {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar la medicamento con id = {0}", medicamentosId);
        // Note que, por medio de la inyección de dependencias se llama al método "update(entity)" que se encuentra en la persistencia.
        MedicamentoEntity newEntity = persistence.update(medicamentoEntity);
        LOGGER.log(Level.INFO, "Termina proceso de actualizar la medicamento con id = {0}", medicamentoEntity.getId());
        return newEntity;
    }

    /**
     * Borrar un medicamento
     *
     * @param medicamentosId: id de la medicamento a borrar
     * 
     */
    public void deleteMedicamento(Long medicamentosId){
        LOGGER.log(Level.INFO, "Inicia proceso de borrar la medicamento con id = {0}", medicamentosId);
        // Note que, por medio de la inyección de dependencias se llama al método "delete(id)" que se encuentra en la persistencia.
        
        persistence.delete(medicamentosId);
        LOGGER.log(Level.INFO, "Termina proceso de borrar la medicamento con id = {0}", medicamentosId);
    }
}
