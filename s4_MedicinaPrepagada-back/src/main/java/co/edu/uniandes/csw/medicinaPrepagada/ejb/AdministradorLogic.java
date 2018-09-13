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
        if (persistence.findByLogin(admiEntity.getLogin() ) != null) 
        {
            throw new BusinessLogicException("El administrador con ese Login ya existe");
        }
        
        //Regla de negocio 2: El login no puede ser vacio
        if( admiEntity.getLogin().isEmpty() )
        {
            throw new BusinessLogicException("El Login no puede ser vacio");
        }
        
        //Regla de negocio 3: La contraseña no puede ser null
        if( admiEntity.getContrasena().isEmpty() )
        {
            throw new BusinessLogicException("La contraseña no puede ser vacia");
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
        LOGGER.log(Level.INFO, "Inicia proceso de consultar el administrador con id = {0}", admiId);
        AdministradorEntity admiEntity = persistence.find(admiId);
        
        if (admiEntity == null) 
        {
            LOGGER.log(Level.SEVERE, "El administrador con el id = {0} no existe", admiId);
        }
        LOGGER.log(Level.INFO, "Termina proceso de consultar el administrador con id = {0}", admiId);
        return admiEntity;
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
     * @throws co.edu.uniandes.csw.medicinaPrepagada.exceptions.BusinessLogicException
     */
    
    public void validarFormatoContrasena( AdministradorEntity admiEntity ) throws BusinessLogicException
    {
        String contrasena = admiEntity.getContrasena(); 
                
        String pas1 = contrasena.trim();
        
        if(pas1.matches("[A-Za-z][0-9]{10}"))
        {
            char clave;
            byte contLetra = 0; 
            byte conNumero = 0;
            
            for(byte i = 0; i <= pas1.length(); i++)
            {
                clave = pas1.charAt(i);
                String pas2 = String.valueOf(clave);
                
                if(pas2.matches("[a-zA-Z]"))
                {
                    contLetra++;
                }
                else if(pas2.matches("[0-9]"))
                {
                    conNumero++;
                }
            }
            
            int sumaLetrasYnumeros = conNumero + contLetra; 
                        
            if(conNumero == 0 || conNumero == sumaLetrasYnumeros || contLetra == 0 ||
               contLetra == sumaLetrasYnumeros || sumaLetrasYnumeros < 8 || sumaLetrasYnumeros > 20   )
              {
                    throw new BusinessLogicException("La contraseña no cumple con los lineamientos de longitud"
                        + " y composición"); 
              } 
        }
        else 
        {
            throw new BusinessLogicException("La contraseña no puede tener caracteres especiales como %-&-$.");
        }
    }
}
