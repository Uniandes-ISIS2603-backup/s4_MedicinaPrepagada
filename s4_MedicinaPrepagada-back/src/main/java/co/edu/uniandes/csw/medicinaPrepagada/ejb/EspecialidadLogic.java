/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.medicinaPrepagada.ejb;

import co.edu.uniandes.csw.medicinaPrepagada.entities.EspecialidadEntity;
import co.edu.uniandes.csw.medicinaPrepagada.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.medicinaPrepagada.persistence.EspecialidadPersistence;
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
public class EspecialidadLogic {
    private static final Logger LOGGER = Logger.getLogger(EspecialidadLogic.class.getName());

    @Inject
    private EspecialidadPersistence persistence;
   
    public EspecialidadEntity createEspecialidad(EspecialidadEntity especialidadoEntity) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de creación de la especialidad");
        if( especialidadoEntity.getNombre() == null){
            throw new BusinessLogicException("La especialidad requiere un nombre. ");
        }
        if(persistence.find(especialidadoEntity.getNombre())!=null){
            throw new BusinessLogicException("La especialidad ya existe. ");
        }
        persistence.create(especialidadoEntity);
        LOGGER.log(Level.INFO, "Termina proceso de creación de la especialidad satisfactoriamente");
        return especialidadoEntity;
    }
    
    /**
     * Devuelve todas las especialidads que hay en la base de datas.
     *
     * @return Lista de entidades de tipo especialidad.
     */
    public List<EspecialidadEntity> getEspecialidades() {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todas las especialidads");
        List<EspecialidadEntity> especialidads = persistence.findAll();
        LOGGER.log(Level.INFO, "Termina proceso de consultar todas las especialidads");
        return especialidads;
    }
    
    /**
     * Busca un especialidad por ID
     *
     * @param especialidadNombre La id de la especialidad a buscar
     * @return La especialidad encontrado, null si no lo encuentra.
     */
    public EspecialidadEntity getEspecialidad(String especialidadNombre) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar la especialidad con id = {0}", especialidadNombre);
        EspecialidadEntity especialidadEntity = persistence.find(especialidadNombre);
        if (especialidadEntity == null) {
            LOGGER.log(Level.SEVERE, "La especialidad con la id = {0} no existe", especialidadNombre);
        }
        LOGGER.log(Level.INFO, "Termina proceso de consultar la especialidad con id = {0}", especialidadNombre);
        return especialidadEntity;
    }

    /**
     * Actualiza la información de una instancia de Especialidad.
     *
     * @param especialidadNombre Identificador de la instancia a actualizar
     * @param especialidadEntity Instancia de EspecialidadEntity con las nuevas datas.
     * @return Instancia de EspecialidadEntity con las datas actualizadas.
     */
    public EspecialidadEntity updateEspecialidad(String especialidadNombre, EspecialidadEntity especialidadEntity) throws BusinessLogicException{
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar la especialidad con id = {0}", especialidadNombre);
        if( especialidadEntity.getNombre() == null){
            throw new BusinessLogicException("La especialidad requiere un nombre. ");
        }
        if(persistence.find(especialidadEntity.getNombre())!=null){
            throw new BusinessLogicException("La especialidad ya existe. ");
        }
        EspecialidadEntity newEspecialidadEntity = persistence.update(especialidadEntity);
        LOGGER.log(Level.INFO, "Termina proceso de actualizar la especialidad con id = {0}", especialidadNombre);
        return newEspecialidadEntity;
    }

    /**
     * Eliminar un especialidad por ID
     *
     * @param especialidadNombre La ID de la especialidad a eliminar
     * @throws BusinessLogicException si la especialidad tiene especialidads asociadas
     */
    public void deleteEspecialidad(String especialidadNombre) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar la especialidad con id = {0}", especialidadNombre);
        persistence.delete(especialidadNombre);
        LOGGER.log(Level.INFO, "Termina proceso de borrar la especialidad con id = {0}", especialidadNombre);
    }
}
