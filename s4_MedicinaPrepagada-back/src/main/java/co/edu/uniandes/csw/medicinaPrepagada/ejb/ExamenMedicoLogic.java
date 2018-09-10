/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.medicinaPrepagada.ejb;

import co.edu.uniandes.csw.medicinaPrepagada.entities.ExamenMedicoEntity;
import co.edu.uniandes.csw.medicinaPrepagada.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.medicinaPrepagada.persistence.ExamenMedicoPersistence;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * Clase que implementa la conexion con la persistencia para la entidad de farmacia.
 * @author ncobos
 */
@Stateless
public class ExamenMedicoLogic {
    private static final Logger LOGGER = Logger.getLogger(ExamenMedicoLogic.class.getName());

    @Inject
    private ExamenMedicoPersistence persistence; // Variable para acceder a la persistencia de la aplicación. Es una inyección de dependencias.

    /**
     * Crea una examenMedico en la persistencia.
     *
     * @param examenMedicoEntity La entidad que representa la examenMedico a
     * persistir.
     * @return La entiddad de la examenMedico luego de persistirla.
     * @throws BusinessLogicException Si la examenMedico a persistir ya existe.
     */
    public ExamenMedicoEntity createExamenMedico(ExamenMedicoEntity examenMedicoEntity) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de creación de la examenMedico");
        // Verifica la regla de negocio que dice que no puede haber dos examenMedicoes con el mismo nombre
        if (persistence.findByNombre(examenMedicoEntity.getNombre()) != null) {
            throw new BusinessLogicException("Ya existe una ExamenMedico con el nombre \"" + examenMedicoEntity.getNombre() + "\"");
        }
        // Invoca la persistencia para crear la examenMedico
        persistence.create(examenMedicoEntity);
        LOGGER.log(Level.INFO, "Termina proceso de creación de la examenMedico");
        return examenMedicoEntity;
    }

    /**
     *
     * Obtener todas las examenMedicoes existentes en la base de datos.
     *
     * @return una lista de examenMedicoes.
     */
    public List<ExamenMedicoEntity> getExamenMedicos() {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todas las examenMedicoes");
        // Note que, por medio de la inyección de dependencias se llama al método "findAll()" que se encuentra en la persistencia.
        List<ExamenMedicoEntity> examenMedicos = persistence.findAll();
        LOGGER.log(Level.INFO, "Termina proceso de consultar todas las examenMedicoes");
        return examenMedicos;
    }

    /**
     *
     * Obtener una examenMedico por medio de su id.
     *
     * @param examenMedicosId: id de la examenMedico para ser buscada.
     * @return la examenMedico solicitada por medio de su id.
     */
    public ExamenMedicoEntity getExamenMedico(Long examenMedicosId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar la examenMedico con id = {0}", examenMedicosId);
        // Note que, por medio de la inyección de dependencias se llama al método "find(id)" que se encuentra en la persistencia.
        ExamenMedicoEntity examenMedicoEntity = persistence.find(examenMedicosId);
        if (examenMedicoEntity == null) {
            LOGGER.log(Level.SEVERE, "La examenMedico con el id = {0} no existe", examenMedicosId);
        }
        LOGGER.log(Level.INFO, "Termina proceso de consultar la examenMedico con id = {0}", examenMedicosId);
        return examenMedicoEntity;
    }

    /**
     *
     * Actualizar una examenMedico.
     *
     * @param examenMedicosId: id de la examenMedico para buscarla en la base de
     * datos.
     * @param examenMedicoEntity: examenMedico con los cambios para ser actualizada,
     * por ejemplo el nombre.
     * @return la examenMedico con los cambios actualizados en la base de datos.
     */
    public ExamenMedicoEntity updateExamenMedico(Long examenMedicosId, ExamenMedicoEntity examenMedicoEntity) {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar la examenMedico con id = {0}", examenMedicosId);
        // Note que, por medio de la inyección de dependencias se llama al método "update(entity)" que se encuentra en la persistencia.
        ExamenMedicoEntity newEntity = persistence.update(examenMedicoEntity);
        LOGGER.log(Level.INFO, "Termina proceso de actualizar la examenMedico con id = {0}", examenMedicoEntity.getId());
        return newEntity;
    }

    /**
     * Borrar un examenMedico
     *
     * @param examenMedicosId: id de la examenMedico a borrar
     * @throws BusinessLogicException Si la examenMedico a eliminar tiene libros.
     */
    public void deleteExamenMedico(Long examenMedicosId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar la examenMedico con id = {0}", examenMedicosId);
        // Note que, por medio de la inyección de dependencias se llama al método "delete(id)" que se encuentra en la persistencia.
        
        persistence.delete(examenMedicosId);
        LOGGER.log(Level.INFO, "Termina proceso de borrar la examenMedico con id = {0}", examenMedicosId);
    }
    
    
}
