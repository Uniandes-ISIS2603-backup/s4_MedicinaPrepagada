/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.medicinaPrepagada.ejb;

import co.edu.uniandes.csw.medicinaPrepagada.entities.FarmaciaEntity;
import co.edu.uniandes.csw.medicinaPrepagada.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.medicinaPrepagada.persistence.FarmaciaPersistence;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

/**
 *
 * Clase que implementa la conexion con la persistencia para la entidad de farmacia.
 * @author ncobos
 */
@Stateless
public class FarmaciaLogic {
    private static final Logger LOGGER = Logger.getLogger(FarmaciaLogic.class.getName());

    @Inject
    private FarmaciaPersistence persistence; // Variable para acceder a la persistencia de la aplicación. Es una inyección de dependencias.

    /**
     * Crea una farmacia en la persistencia.
     *
     * @param farmaciaEntity La entidad que representa la farmacia a
     * persistir.
     * @return La entiddad de la farmacia luego de persistirla.
     * @throws BusinessLogicException Si la farmacia a persistir ya existe.
     */
    public FarmaciaEntity createFarmacia(FarmaciaEntity farmaciaEntity) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de creación de la farmacia");
        // Verifica la regla de negocio que dice que no puede haber dos farmacias con el mismo nombre
        if (persistence.findByNombre(farmaciaEntity.getNombre()) != null) {
            throw new BusinessLogicException("Ya existe una Farmacia con el nombre \"" + farmaciaEntity.getNombre() + "\"");
        }
        if (!validateNombre(farmaciaEntity.getNombre()))
           throw new BusinessLogicException ("El nombre no puede ser vacio ");
        
     
        //Verifica que no exista otra farmacia con el mismo nombre
        if (persistence.findByNombre(farmaciaEntity.getNombre())!=null)
            throw new BusinessLogicException("Ya existe una farmacia con este nombre");
        //Verifica que la ubicación sea valida
        if (!validateDireccion(farmaciaEntity))
           throw new BusinessLogicException ("La dirección no cumple con el formato.");
        //Verifica que la latitud sea valida
        if (!validateLatitud(farmaciaEntity.getLatitud()))
           throw new BusinessLogicException ("La latitud no corresponde a valores de Colombia.");
        
        //Verifica que la longitud sea valida
        if (!validateLongitud(farmaciaEntity.getLongitud()))
           throw new BusinessLogicException ("La longitud no corresponde a valores de Colombia.");
        validarCondiciones(farmaciaEntity);
        
        // Invoca la persistencia para crear la farmacia
        persistence.create(farmaciaEntity);
        LOGGER.log(Level.INFO, "Termina proceso de creación de la farmacia");
        return farmaciaEntity;
    }

    /**
     *
     * Obtener todas las farmacias existentes en la base de datos.
     *
     * @return una lista de farmacias.
     */
    public List<FarmaciaEntity> getFarmacias() {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todas las farmacias");
        // Note que, por medio de la inyección de dependencias se llama al método "findAll()" que se encuentra en la persistencia.
        List<FarmaciaEntity> farmacias = persistence.findAll();
        LOGGER.log(Level.INFO, "Termina proceso de consultar todas las farmacias");
        return farmacias;
    }

    /**
     *
     * Obtener una farmacia por medio de su id.
     *
     * @param farmaciasId: id de la farmacia para ser buscada.
     * @return la farmacia solicitada por medio de su id.
     */
    public FarmaciaEntity getFarmacia(Long farmaciasId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar la farmacia con id = {0}", farmaciasId);
        // Note que, por medio de la inyección de dependencias se llama al método "find(id)" que se encuentra en la persistencia.
        FarmaciaEntity farmaciaEntity = persistence.find(farmaciasId);
        if (farmaciaEntity == null) {
            LOGGER.log(Level.SEVERE, "La farmacia con el id = {0} no existe", farmaciasId);
        }
        LOGGER.log(Level.INFO, "Termina proceso de consultar la farmacia con id = {0}", farmaciasId);
        return farmaciaEntity;
    }

    /**
     *
     * Actualizar una farmacia.
     *
     * @param farmaciasId: id de la farmacia para buscarla en la base de
     * datos.
     * @param farmaciaEntity: farmacia con los cambios para ser actualizada,
     * por ejemplo el nombre.
     * @return la farmacia con los cambios actualizados en la base de datos.
     */
    public FarmaciaEntity updateFarmacia(Long farmaciasId, FarmaciaEntity farmaciaEntity) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar la farmacia con id = {0}", farmaciasId);
        // Note que, por medio de la inyección de dependencias se llama al método "update(entity)" que se encuentra en la persistencia.
        
        FarmaciaEntity pFarmaciaOld = persistence.find(farmaciasId);
        //Verifica que la farmacia que se intenta modificar exista
        if (pFarmaciaOld == null)
            throw new BusinessLogicException ("La farmacia que intenta modificar no existe");
        //Verifica que no se intente cambiar el Id
        if (farmaciaEntity.getId() != farmaciasId)
            throw new BusinessLogicException("No se puede cambiar el id de la farmacia");
        //Verifica que no se intente cambiar la longitud de la farmacia
        if (farmaciaEntity.getLongitud() != pFarmaciaOld.getLongitud())
            throw new BusinessLogicException("No se puede cambiar la longitud de una Farmacia");
        //Verifica que no se intente cambia la latitud de la farmacia
        if (farmaciaEntity.getLatitud() != pFarmaciaOld.getLatitud())
            throw new BusinessLogicException ("No se puede cambiar la latitud de una farmacia");
        //Verifica que no se intente cambiar la direccion de la farmacia
        if (!farmaciaEntity.getUbicacion().equals(pFarmaciaOld.getUbicacion()))
            throw new BusinessLogicException("No se puede cambiar la direccion de una farmacia");      
         //Verifica que el nombre sea valido
        if (!validateNombre(farmaciaEntity.getNombre()))
           throw new BusinessLogicException ("El nombre no puede ser vacío.");
        //Verifica que no exista otra farmacia con el mismo nombre
        if (persistence.findByNombre(farmaciaEntity.getNombre())!=null)
            throw new BusinessLogicException("Ya existe una farmacia con este nombre");
       
        validarCondiciones(farmaciaEntity);
        FarmaciaEntity newEntity = persistence.update(farmaciaEntity);
        LOGGER.log(Level.INFO, "Termina proceso de actualizar la farmacia con id = {0}", farmaciaEntity.getId());
        return newEntity;
    }

    /**
     * Borrar un farmacia
     *
     * @param farmaciasId: id de la farmacia a borrar
     * @throws BusinessLogicException Si la farmacia a eliminar tiene libros.
     */
    public void deleteFarmacia(Long farmaciasId) {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar la farmacia con id = {0}", farmaciasId);
        // Note que, por medio de la inyección de dependencias se llama al método "delete(id)" que se encuentra en la persistencia.
       
        persistence.delete(farmaciasId);
        LOGGER.log(Level.INFO, "Termina proceso de borrar la farmacia con id = {0}", farmaciasId);
    }
    
    private boolean validateNombre (String pNombre)
    {
        return !(pNombre == null || pNombre.isEmpty());
    }
       private boolean validateDireccion (FarmaciaEntity entity)
    {
        return entity.getUbicacion().contains("#");
    }
    private boolean validateTelefono(Long pTelefono)
    {
        return(pTelefono>999999);
    }
    
    private boolean validateLongitud (Double pLongitud)
    {
        return (pLongitud !=null && pLongitud>= -79.374594 && pLongitud <= -66.853233 );
    }
    
     
    private boolean validateLatitud (Double pLatitud)
    {
        return (pLatitud !=null && pLatitud>= -4.223596 && pLatitud <= 12.514801 );
    }
    
    private boolean  validateCorreo (String pCorreo) 
    {
        Boolean rta = true;
        String mailValidationPattern = "[a-z]+@[a-z]+.[a-z]+";
        Pattern patternMail = Pattern.compile(mailValidationPattern);
        Matcher matchMail = patternMail.matcher(pCorreo);
        if(!matchMail.matches()){
           rta= false;
        }
        return rta;
    }
     
    public static boolean isValidEmailAddress(String email) {
   boolean result = true;
   try {
       InternetAddress emailAddr = new InternetAddress(email);
      emailAddr.validate();
   } catch (AddressException ex) {
      result = false;
   }
   return result;
}
    public void validarCondiciones (FarmaciaEntity entity) throws BusinessLogicException
    {
        //Verifica que el correo sea valido
        if (!validateCorreo(entity.getCorreo()))
           throw new BusinessLogicException ("El correo no sigue el formato.");
        
        //Verifica que el telefono sea valido
        if (!validateTelefono(entity.getTelefono()))
           throw new BusinessLogicException ("El telefono no cumple con el formato.");
        
    }
    
}
