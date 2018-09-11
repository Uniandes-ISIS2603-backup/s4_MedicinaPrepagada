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
import javax.ejb.Stateless;
import javax.inject.Inject;

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
           throw new BusinessLogicException ("EL nombre no puede ser vacio ");
        //Verifica que no exista otra farmacia con el mismo nombre
        if (persistence.findByNombre(farmaciaEntity.getNombre())!=null)
            throw new BusinessLogicException("Ya existe una farmacia con este nombre");
        //verifica que la direccion sea validad
        if (!validateDireccion(farmaciaEntity.getUbicacion()))
            throw new BusinessLogicException("La ubicación no puede ser vacia y debe respetar el formato de una direccion");
        //verifica que el telefono sea validad
        if (!validateNumero(farmaciaEntity.getTelefono()))
            throw new BusinessLogicException("El telefono debe tener más de 6 digitos");
        //Valida que el correo tenga un formato correcto
        if (!validateCorreo(farmaciaEntity.getCorreo()))
            throw new BusinessLogicException("El correo no sigue un formato correcto");
        //Valida que la longitud dada se encuentre en colombia
        if (!validateLongitud(farmaciaEntity.getLongitud()))
            throw new BusinessLogicException("La longitud dada no se encuentra en Colombia");
        //Valida que la latitud dada se encuentre en colombia
        if (!validateLatitud(farmaciaEntity.getLatitud()))
            throw new BusinessLogicException("La latitud dada no se encuentra en Colombia");
        //Valida que no exista otra farmacia con la misma longitud y latitud
        if (!validateLongAndLat(farmaciaEntity.getLongitud(), farmaciaEntity.getLatitud()))
            throw new BusinessLogicException("Ya existe una farmacia con esta longitud y latitud");
        
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
        if (farmaciaEntity.getUbicacion() != pFarmaciaOld.getUbicacion())
            throw new BusinessLogicException("No se puede cambiar la direccion de una farmacia");      
         //Verifica que el nombre sea valido
        if (!validateNombre(farmaciaEntity.getNombre()))
           throw new BusinessLogicException ("El nombre no puede ser vacío.");
        //Verifica que no exista otra farmacia con el mismo nombre
        if (persistence.findByNombre(farmaciaEntity.getNombre())!=null)
            throw new BusinessLogicException("Ya existe una farmacia con este nombre");
        //verifica que el telefono sea validad
        if (!validateNumero(farmaciaEntity.getTelefono()))
            throw new BusinessLogicException("El telefono debe tener al menos 7 digitos");
     
        //Valida que el correo tenga un formato correcto
        if (!validateCorreo(farmaciaEntity.getCorreo()))
            throw new BusinessLogicException("EL correo no sigue un formato correcto.");    
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
       private boolean validateDireccion (String pDireccion)
    {
        return !(pDireccion == null || pDireccion.isEmpty() || !pDireccion.contains("#") );
    }
    
    
       private boolean validateNumero (Long pNumero)
    {
        return !(pNumero == null || pNumero<1000000);
    }
    
    private boolean validateLongAndLat (Double pLong, Double pLat )
    {
        
        List <FarmaciaEntity> pFarmacias= persistence.findAll();
        for (int i = 0 ; i< pFarmacias.size(); i++)
        {
            FarmaciaEntity temp =pFarmacias.get(i);
            if (temp.getLongitud() == pLong && temp.getLatitud() == pLat)
            {
                return false;
            }
        }
        
        return true;
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
        return (pCorreo.contains("@")&& pCorreo != null);
    }
       
    
}
