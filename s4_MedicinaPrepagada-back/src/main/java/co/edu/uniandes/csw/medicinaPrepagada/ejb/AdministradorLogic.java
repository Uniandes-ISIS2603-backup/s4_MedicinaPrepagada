/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.medicinaPrepagada.ejb;

import co.edu.uniandes.csw.medicinaPrepagada.entities.AdministradorEntity;
import co.edu.uniandes.csw.medicinaPrepagada.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.medicinaPrepagada.persistence.AdministradorPersistence;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author ni.ramirez10
 */

@Stateless
public class AdministradorLogic 
{
    private static final Logger LOGGER = Logger.getLogger(AdministradorLogic.class.getName());

    @Inject
    private AdministradorPersistence persistence;
    
    /**
     * Crea un nuevo administrador
     * @param admiEntity
     * @return La entidad luego de persistirla
     * @throws BusinessLogicException X
     */
    
    public AdministradorEntity createAdministrador(AdministradorEntity admiEntity) throws BusinessLogicException 
    {
        LOGGER.log(Level.INFO, "Inicia proceso de creación del administrador");
        
        //Regla de negocio 1: No puede haber un login igual en la base de datos
        if(persistence.findByLogin(admiEntity.getLogin()))
        {
            throw new BusinessLogicException("El administrador con ese Login ya existe");
        }
        
        //Regla de negocio 2: El login no puede ser vacio
        if( admiEntity.getLogin() == null || admiEntity.getLogin().equals("") )
        {
            throw new BusinessLogicException("El Login no puede ser vacio");
        }
        
        //Regla de negocio 3: La contraseña no puede ser null
        if( admiEntity.getContrasena() == null || admiEntity.getContrasena().equals("") )
        {
            throw new BusinessLogicException("La contraseña no puede ser vacia");
        }
        
        //Regla de negocio 4: El tipo de usuario no puede ser null
        if( admiEntity.getTipoUsuario() == null || admiEntity.getTipoUsuario().equals("") )
        {
            throw new BusinessLogicException("El tipo de usuario no puede ser vacia");
        }
     
        validarFormatoContrasena(admiEntity);
       
        persistence.create(admiEntity);
        LOGGER.log(Level.INFO, "Termina proceso de creación del administrador");
        return admiEntity;
    }
    
    /**
     * Devuelve todos los administradores que hay en la base de datos.
     * @return Lista de entidades de tipo administrador.
     */
    
    public List<AdministradorEntity> getAdministradores( ) 
    {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todos los administradores");
        List<AdministradorEntity> admis = persistence.findAll();
        LOGGER.log(Level.INFO, "Termina proceso de consultar todos los administradores");
        return admis;
    }
    
    /**
     * Busca un administrador por el id
     * @param admiId
     * @return El administrador encontrado, null si no lo encuentra.
     */
    
    public AdministradorEntity getAdministrador(Long admiId) 
    {
        return persistence.find(admiId); 
    
        /**LOGGER.log(Level.INFO, "Inicia proceso de consultar el administrador con id = {0}", admiId);
        AdministradorEntity admiEntity = persistence.find(admiId);
        
        if (admiEntity == null) 
        {
            LOGGER.log(Level.SEVERE, "El administrador con el id = {0} no existe", admiId);
        }
        LOGGER.log(Level.INFO, "Termina proceso de consultar el administrador con id = {0}", admiId);
        return admiEntity;*/
    }
    
    /**
     * Actualizar un administrador por ID
     * @param admiId
     * @param admiEntity
     * @return La entidad del administrador luego de actualizarla
     * @throws co.edu.uniandes.csw.medicinaPrepagada.exceptions.BusinessLogicException
     */
    
    public AdministradorEntity updateAdministrador(Long admiId, AdministradorEntity admiEntity) throws BusinessLogicException
    {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar el administrador con id = {0}", admiId);
        
        //Regla de negocio 1: La contraseña no puede ser null
        if( admiEntity.getContrasena().isEmpty() )
        {
            throw new BusinessLogicException("La contraseña no puede ser vacia");
        }

        validarFormatoContrasena(admiEntity);

        AdministradorEntity newEntity = persistence.update(admiEntity);
        LOGGER.log(Level.INFO, "Termina proceso de actualizar el administrador con id = {0}", admiEntity.getId());
        return newEntity;
    }
    
     /**
     * Eliminar un administrador por ID
     * @param admiId
     */
    
    public void deleteAdministrador(Long admiId) 
    {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar el administrador con id = {0}", admiId);
        persistence.delete(admiId);
        LOGGER.log(Level.INFO, "Termina proceso de borrar el administrador con id = {0}", admiId);
    }
    
    /**
     * Valida que la contraseña cumpla con el formato
     * @param admiEntity
     * @return 
     * @throws co.edu.uniandes.csw.medicinaPrepagada.exceptions.BusinessLogicException
     */
    
    public boolean validarFormatoContrasena( AdministradorEntity admiEntity ) throws BusinessLogicException
    {
        String contrasena = admiEntity.getContrasena(); 
        boolean validada = false; 
        
        if(contrasena.matches("^[A-Za-z0-9]{8,20}$"))
        {
            validada = true; 
        }
        else 
        {
            throw new BusinessLogicException("La contraseña no puede tener caracteres especiales como %-&-$.");
        }
        
        return validada; 
    }
}
