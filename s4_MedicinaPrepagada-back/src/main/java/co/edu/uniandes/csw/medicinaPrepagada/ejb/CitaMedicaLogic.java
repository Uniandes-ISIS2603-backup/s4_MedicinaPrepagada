/*
 * To change this license header, choase License Headers in Project Properties.
 * To change this template file, choase Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.medicinaPrepagada.ejb;

import co.edu.uniandes.csw.medicinaPrepagada.entities.CitaMedicaEntity;
import co.edu.uniandes.csw.medicinaPrepagada.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.medicinaPrepagada.persistence.CitaMedicaPersistence;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Daniel Ivan Romero
 */

@Stateless
public class CitaMedicaLogic {
    private static final Logger LOGGER = Logger.getLogger(CitaMedicaLogic.class.getName());

    @Inject
    private CitaMedicaPersistence persistence;
   
    public CitaMedicaEntity createCitaMedica(CitaMedicaEntity citaMedicaoEntity) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de creación de la médico");
        persistence.create(citaMedicaoEntity);
        LOGGER.log(Level.INFO, "Termina proceso de creación de la médico satisfactoriamente");
        return citaMedicaoEntity;
    }
    
    /**
     * Devuelve todas las citaMedicas que hay en la base de datas.
     *
     * @return Lista de entidades de tipo citaMedica.
     */
    public List<CitaMedicaEntity> getCitasMedicas() {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todas las citaMedicas");
        List<CitaMedicaEntity> citaMedicas = persistence.findAll();
        LOGGER.log(Level.INFO, "Termina proceso de consultar todas las citaMedicas");
        return citaMedicas;
    }
    
    /**
     * Busca un citaMedica por ID
     *
     * @param citaMedicasId La id de la citaMedica a buscar
     * @return La citaMedica encontrado, null si no lo encuentra.
     */
    public CitaMedicaEntity getCitaMedica(Long citaMedicasId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar la citaMedica con id = {0}", citaMedicasId);
        CitaMedicaEntity citaMedicaEntity = persistence.find(citaMedicasId);
        if (citaMedicaEntity == null) {
            LOGGER.log(Level.SEVERE, "La citaMedica con la id = {0} no existe", citaMedicasId);
        }
        LOGGER.log(Level.INFO, "Termina proceso de consultar la citaMedica con id = {0}", citaMedicasId);
        return citaMedicaEntity;
    }

    /**
     * Actualiza la información de una instancia de CitaMedica.
     *
     * @param citaMedicasId Identificador de la instancia a actualizar
     * @param citaMedicaEntity Instancia de CitaMedicaEntity con las nuevas datas.
     * @return Instancia de CitaMedicaEntity con las datas actualizadas.
     */
    public CitaMedicaEntity updateCitaMedica(Long citaMedicasId, CitaMedicaEntity citaMedicaEntity) {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar la citaMedica con id = {0}", citaMedicasId);
        CitaMedicaEntity newCitaMedicaEntity = persistence.update(citaMedicaEntity);
        LOGGER.log(Level.INFO, "Termina proceso de actualizar la citaMedica con id = {0}", citaMedicasId);
        return newCitaMedicaEntity;
    }

    /**
     * Eliminar un citaMedica por ID
     *
     * @param citaMedicasId La ID de la citaMedica a eliminar
     * @throws BusinessLogicException si la citaMedica tiene citaMedicas asociadas
     */
    public void deleteCitaMedica(Long citaMedicasId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar la citaMedica con id = {0}", citaMedicasId);
        persistence.delete(citaMedicasId);
        LOGGER.log(Level.INFO, "Termina proceso de borrar la citaMedica con id = {0}", citaMedicasId);
    }
}
