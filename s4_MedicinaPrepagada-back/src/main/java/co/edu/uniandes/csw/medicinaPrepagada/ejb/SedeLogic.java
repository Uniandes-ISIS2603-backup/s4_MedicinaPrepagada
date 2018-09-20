/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.medicinaPrepagada.ejb;

import co.edu.uniandes.csw.medicinaPrepagada.entities.SedeEntity;
import co.edu.uniandes.csw.medicinaPrepagada.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.medicinaPrepagada.persistence.SedePersistence;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Simon Guzman
 */
@Stateless
public class SedeLogic 
{
  
    private static final Logger LOGGER = Logger.getLogger(SedeLogic.class.getName());

    @Inject
    private SedePersistence persistence;
  
    
     /**
     * Se encarga de crear un Sede en la base de datos.
     *
     * @param sedeEntity Objeto de SedeEntity con los datos nuevos
     * @return Objeto de SedeEntity con los datos nuevos y su ID.
     * @throws co.edu.uniandes.csw.medicinaPrepagada.exceptions.BusinessLogicException
     */
    public SedeEntity createSede(SedeEntity sedeEntity) throws BusinessLogicException
    {
        LOGGER.log(Level.INFO, "Inicia proceso de creación del sede");
        //Verifica que el nombre sea valido al momento de crear
        if (!validateNombre(sedeEntity.getNombre()))
           throw new BusinessLogicException ("EL nombre no puede ser vacio al crear una sede ");
        //Verifica que no exista otra sede con el mismo nombre al crear
        if (persistence.findByNombre(sedeEntity.getNombre())!=null)
            throw new BusinessLogicException("Ya existe una sede con este nombre, cambie el nombre para crearla");
        //verifica que la direccion sea validad al crar la sede
        if (!validateDireccion(sedeEntity.getDireccion()))
            throw new BusinessLogicException("La direccion no puede ser vacia y debe respetar el formato de una direccion");
        //verifica que el telefono sea valido al crear la sede
        if (!validateNumero(sedeEntity.getTelefono()))
            throw new BusinessLogicException("El telefono debe tener al menos 7 digitos");
        //Verifica que no exista otra sede con la misma direccion para crearla
        if (persistence.findByDireccion(sedeEntity.getDireccion())!= null)
            throw new BusinessLogicException("Ya existe una sede con esta direccion");
        //verifica que el tipo de la sede sea de los posibles al crearla
        if (!validateTipo(sedeEntity.getTipoSede()))
            throw new BusinessLogicException ("El tipo de sede no es de los posibles");
        //Valida que el correo tenga un formato correcto al crearla
        if (!validateCorreo(sedeEntity.getCorreo()))
            throw new BusinessLogicException("EL correo no sigue un formato de correo regular");
        //Valida que la longitud dada se encuentre en colombia al crearla
        if (!validateLongitud(sedeEntity.getLongitud()))
            throw new BusinessLogicException("La longitud dada no se encuentra en Colombia");
        //Valida que la latitud dada se encuentre en colombia al crearla
        if (!validateLatitud(sedeEntity.getLatitud()))
            throw new BusinessLogicException("La latitud dada no se encuentra en Colombia");
        //Valida que no exista otra sede con la misma longitud y latitud al crearla
        if (!validateLongAndLat(sedeEntity.getLongitud(), sedeEntity.getLatitud()))
            throw new BusinessLogicException("Ya existe una sede con esta longitud y latitud");
        
        SedeEntity newSedeEntity = persistence.create(sedeEntity);
        LOGGER.log(Level.INFO, "Termina proceso de creación del sede");
        return newSedeEntity;
    }
    
    
    
    /**
     * Obtiene la lista de los registros de Sede.
     *
     * @return Colección de objetos de SedeEntity.
     */
    public List<SedeEntity> getSedes() 
    {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todos los sedees");
        List<SedeEntity> lista = persistence.findAll();
        LOGGER.log(Level.INFO, "Termina proceso de consultar todos los sedees");
        return lista;
    }

    /**
     * Obtiene los datos de una instancia de Sede a partir de su ID.
     *
     * @param sedesId Identificador de la instancia a consultar
     * @return Instancia de SedeEntity con los datos del Sede consultado.
     */
    public SedeEntity getSede(Long sedesId) 
    {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar el sede con id = {0}", sedesId);
        SedeEntity sedeEntity = persistence.find(sedesId);
        if (sedeEntity == null)
        {
            LOGGER.log(Level.SEVERE, "La sede con el id = {0} no existe", sedesId);
        }
        LOGGER.log(Level.INFO, "Termina proceso de consultar el sede con id = {0}", sedesId);
        return sedeEntity;
    }
    
    
    
        /**
     * Actualiza la información de una instancia de Sede.
     *
     * @param sedesId Identificador de la instancia a actualizar
     * @param sedeEntity Instancia de SedeEntity con los nuevos datos.
     * @return Instancia de SedeEntity con los datos actualizados.
     * @throws co.edu.uniandes.csw.medicinaPrepagada.exceptions.BusinessLogicException
     */
    public SedeEntity updateSede(Long sedesId, SedeEntity sedeEntity) throws BusinessLogicException
    {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar el sede con id = {0}", sedesId);
        
        SedeEntity pSedeOld = persistence.find(sedesId);
        //Verifica que la sede que se intenta modificar exista
        if (pSedeOld == null)
            throw new BusinessLogicException ("La sede que intenta modificar no existe");
        //Verifica que no se intente cambiar el Id
        if (sedeEntity.getId() != sedesId)
            throw new BusinessLogicException("No se puede cambiar el id de la sede");
        //Verifica que no se intente cambiar la longitud de la sede
        if (sedeEntity.getLongitud() != pSedeOld.getLongitud())
            throw new BusinessLogicException("No se puede cambiar la longitud de una Sede");
        //Verifica que no se intente cambia la latitud de la sede
        if (sedeEntity.getLatitud() != pSedeOld.getLatitud())
            throw new BusinessLogicException ("No se puede cambiar la latitud de una sede");
        //Verifica que no se intente cambiar la direccion de la sede
        if (!sedeEntity.getDireccion().equals(pSedeOld.getDireccion()))
            throw new BusinessLogicException("No se puede cambiar la direccion de una sede");      
         //Verifica que el nombre sea valido al editarlo
        if (!validateNombre(sedeEntity.getNombre()))
           throw new BusinessLogicException ("EL nombre no puede ser vacio ");
        //Verifica que no exista otra sede con el mismo nombre al editarlo
        if (persistence.findByNombre(sedeEntity.getNombre())!=null)
            throw new BusinessLogicException("Ya existe una sede con este nombre");
        //verifica que el telefono sea valido al editarlo
        if (!validateNumero(sedeEntity.getTelefono()))
            throw new BusinessLogicException("El telefono debe tener al menos 7 digitos");
        //verifica que el tipo de la sede sea de los posibles al editarla
        if (!validateTipo(sedeEntity.getTipoSede()))
            throw new BusinessLogicException ("El tipo de sede no es de los posibles");
        //Valida que el correo tenga un formato correcto al editarlo
        if (!validateCorreo(sedeEntity.getCorreo()))
            throw new BusinessLogicException("EL correo no sigue un formato de correo regular");        
        
        SedeEntity newSedeEntity = persistence.update(sedeEntity);
        LOGGER.log(Level.INFO, "Termina proceso de actualizar el sede con id = {0}", sedesId);
        return newSedeEntity;
    }

    /**
     * Elimina una instancia de Sede de la base de datos.
     *
     * @param sedesId Identificador de la instancia a eliminar.
     * @throws BusinessLogicException si el sede tiene libros asociados.
     */
    public void deleteSede(Long sedesId) throws BusinessLogicException 
    {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar el sede con id = {0}", sedesId);
  
        
        //Verifica que la sede que se intenta modificar exista
        if (persistence.find(sedesId) == null)
            throw new BusinessLogicException ("La sede que intenta eliminar no existe");
        
        persistence.delete(sedesId);
        LOGGER.log(Level.INFO, "Termina proceso de borrar el sede con id = {0}", sedesId);
    }

    
     private boolean validateNombre (String pNombre)
    {
        return !(pNombre == null || pNombre.isEmpty());
    }
       private boolean validateDireccion (String pDireccion)
    {
        return !(pDireccion == null || pDireccion.isEmpty() || !pDireccion.contains("#") );
    }
    private boolean validateTipo (int pTipo)
    {
        return (pTipo == 1 || pTipo == 2  );
    }
    
       private boolean validateNumero (Long pNumero)
    {
        return !(pNumero == null || pNumero<1000000);
    }
    
    private boolean validateLongAndLat (Double pLong, Double pLat )
    {
        
        List <SedeEntity> pSedes= persistence.findAll();
        for (int i = 0 ; i< pSedes.size(); i++)
        {
            SedeEntity temp =pSedes.get(i);
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
