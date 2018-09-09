/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.medicinaPrepagada.ejb;

import co.edu.uniandes.csw.medicinaPrepagada.entities.MedicoEntity;
import co.edu.uniandes.csw.medicinaPrepagada.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.medicinaPrepagada.persistence.EspecialidadPersistence;
import co.edu.uniandes.csw.medicinaPrepagada.persistence.MedicoPersistence;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @medico Daniel Ivan Romero
 */

@Stateless
public class MedicoLogic {
    private static final Logger LOGGER = Logger.getLogger(MedicoLogic.class.getName());

    @Inject
    private MedicoPersistence persistence;
    
    @Inject
    private EspecialidadPersistence especialidadPersistence;

    public MedicoEntity createMedico(MedicoEntity medicooEntity) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de creación del médico");
        MedicoEntity idRepetido = persistence.find(medicooEntity.getCedula());
        if(idRepetido != null){
            throw new BusinessLogicException("Ya existe un medicoo con el id que se quiere ingresar.");
        }
        if(medicooEntity.getNombre() == null){
            throw new BusinessLogicException("Debe ingresar el nombre del médico. ");
        }
        if(medicooEntity.getTelefono() <= 999999){
            throw new BusinessLogicException("El numero de telefono no es válido. ");
        }
        Pattern patronDeCorreo = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        String email = medicooEntity.getCorreo();
        Matcher mather = patronDeCorreo.matcher(email);
        if (mather.find() == false) {
            throw new BusinessLogicException("El correo no es un correo válido. ");
        }
        persistence.create(medicooEntity);
        LOGGER.log(Level.INFO, "Termina proceso de creación del médico satisfactoriamente");
        return medicooEntity;
    }
    
    /**
     * Devuelve todos los medicos que hay en la base de datos.
     *
     * @return Lista de entidades de tipo medico.
     */
    public List<MedicoEntity> getMedicos() {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todos los medicos");
        List<MedicoEntity> medicos = persistence.findAll();
        LOGGER.log(Level.INFO, "Termina proceso de consultar todos los medicos");
        return medicos;
    }
    
    /**
     * Busca un medico por ID
     *
     * @param medicosId El id del medico a buscar
     * @return El medico encontrado, null si no lo encuentra.
     */
    public MedicoEntity getMedico(Long medicosId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar el medico con id = {0}", medicosId);
        MedicoEntity medicoEntity = persistence.find(medicosId);
        if (medicoEntity == null) {
            LOGGER.log(Level.SEVERE, "El medico con el id = {0} no existe", medicosId);
        }
        LOGGER.log(Level.INFO, "Termina proceso de consultar el medico con id = {0}", medicosId);
        return medicoEntity;
    }

    /**
     * Actualiza la información de una instancia de Medico.
     *
     * @param medicosId Identificador de la instancia a actualizar
     * @param medicoEntity Instancia de MedicoEntity con los nuevos datos.
     * @return Instancia de MedicoEntity con los datos actualizados.
     */
    public MedicoEntity updateAuthor(Long medicosId, MedicoEntity medicoEntity) {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar el medico con id = {0}", medicosId);
        MedicoEntity newMedicoEntity = persistence.update(medicoEntity);
        LOGGER.log(Level.INFO, "Termina proceso de actualizar el medico con id = {0}", medicosId);
        return newMedicoEntity;
    }

    /**
     * Eliminar un medico por ID
     *
     * @param medicosId El ID del medico a eliminar
     * @throws BusinessLogicException si el medico tiene medicoes asociados
     */
    public void deleteMedico(Long medicosId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar el medico con id = {0}", medicosId);
//        List<AuthorEntity> medicos = getMedico(medicosId).getAuthors();
//        if (medicos != null && !medicos.isEmpty()) {
//            throw new BusinessLogicException("No se puede borrar el medico con id = " + medicosId + " porque tiene medicoes asociados");
//        }
        persistence.delete(medicosId);
        LOGGER.log(Level.INFO, "Termina proceso de borrar el medico con id = {0}", medicosId);
    }
}
