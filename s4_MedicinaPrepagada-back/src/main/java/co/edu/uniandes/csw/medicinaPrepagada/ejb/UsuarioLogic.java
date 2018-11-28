/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.medicinaPrepagada.ejb;

import co.edu.uniandes.csw.medicinaPrepagada.entities.UsuarioEntity;
import co.edu.uniandes.csw.medicinaPrepagada.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.medicinaPrepagada.persistence.UsuarioPersistence;
import javax.inject.Inject;

/**
 *Logica de la entidad usuario
 * @author MIGUELHOYOS
 */
public class UsuarioLogic {
    
    @Inject
    private UsuarioPersistence usuarioPersistence;
    
    /**
     * Da el usuario con el login que llega por param
     * @param login : el login del usuario
     * @return el usuario con el login que llega por param
     * @throws BusinessLogicException si el usuario no existe
     */
    public UsuarioEntity getUsuarioByLogin(String login) throws BusinessLogicException{
        return usuarioPersistence.getUsuarioByLogin(login);
    }
}
