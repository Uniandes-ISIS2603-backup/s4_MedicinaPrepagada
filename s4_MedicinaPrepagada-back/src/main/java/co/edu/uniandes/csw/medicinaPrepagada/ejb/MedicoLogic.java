/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.medicinaPrepagada.ejb;

import co.edu.uniandes.csw.medicinaPrepagada.entities.MedicoEntity;
import co.edu.uniandes.csw.medicinaPrepagada.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.medicinaPrepagada.persistence.MedicoPersistence;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Daniel Ivan Romero
 */

@Stateless
public class MedicoLogic {
    private static final Logger LOGGER = Logger.getLogger(MedicoLogic.class.getName());

    @Inject
    private MedicoPersistence persistence;

    public MedicoEntity createMedico(MedicoEntity medicoEntity) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de creación del médico");
        MedicoEntity idRepetido = persistence.find(medicoEntity.getCedula());
        if(idRepetido != null){
            throw new BusinessLogicException("Ya existe un medico con el id que se quiere ingresar.");
        }
        if(medicoEntity.getNombre() == null){
            throw new BusinessLogicException("Debe ingresar el nombre del médico. ");
        }
        if(medicoEntity.getTelefono() <= 999999){
            throw new BusinessLogicException("El numero de telefono no es válido. ");
        }
        Pattern patronDeCorreo = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        String email = medicoEntity.getCorreo();
        Matcher mather = patronDeCorreo.matcher(email);
        if (mather.find() == false) {
            throw new BusinessLogicException("El correo no es un correo válido. ");
        }
        persistence.create(medicoEntity);
        LOGGER.log(Level.INFO, "Termina proceso de creación del médico satisfactoriamente");
        return medicoEntity;
    }
}
