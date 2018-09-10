/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.medicinaPrepagada.ejb;

import co.edu.uniandes.csw.medicinaPrepagada.entities.SedeEntity;
import co.edu.uniandes.csw.medicinaPrepagada.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.medicinaPrepagada.persistence.SedePersistence;
import java.util.ArrayList;
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
     */
    public SedeEntity createSede(SedeEntity sedeEntity) throws BusinessLogicException
    {
        LOGGER.log(Level.INFO, "Inicia proceso de creación del sede");
        //Verifica que el nombre sea valido
        if (!validateNombre(sedeEntity.getNombre()))
           throw new BusinessLogicException ("EL nombre no puede ser vacio ");
        //Verifica que no exista otra sede con el mismo nombre
        if (persistence.findByNombre(sedeEntity.getNombre())!=null)
            throw new BusinessLogicException("Ya existe una sede con este nombre");
        //verifica que la direccion sea validad
        if (!validateDireccion(sedeEntity.getDireccion()))
            throw new BusinessLogicException("La direccion no puede ser vacia y debe respetar el formato de una direccion");
        //verifica que la direccion sea validad
        if (!validateNumero(sedeEntity.getTelefono()))
            throw new BusinessLogicException("El telefono debe tener al menos 7 digitos");
        //Verifica que no exista otra sede con la misma direccion
        if (persistence.findByDireccion(sedeEntity.getDescripcion())!= null)
            throw new BusinessLogicException("Ya existe una sede con esta direccion");
        //verifica que el tipo de la sede sea de los posibles
        if (!validateTipo(sedeEntity.getTipoSede()))
            throw new BusinessLogicException ("El tipo de sede no es de los posibles");
        //Valida que el correo tenga un formato correcto
        if (!validateCorreo(sedeEntity.getCorreo()))
            throw new BusinessLogicException("EL correo no sigue un formato de correo regular");
        //Valida que la longitud dada se encuentre en colombia
        if (!validateLongitud(sedeEntity.getLongitud()))
            throw new BusinessLogicException("La longitud dada no se encuentra en Colombia");
        //Valida que la latitud dada se encuentre en colombia
        if (!validateLatitud(sedeEntity.getLatitud()))
            throw new BusinessLogicException("La latitud dada no se encuentra en Colombia");
        //Valida que no exista otra sede con la misma longitud y latitud
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
        if (sedeEntity == null) {
            LOGGER.log(Level.SEVERE, "La editorial con el id = {0} no existe", sedesId);
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
     */
    public SedeEntity updateSede(Long sedesId, SedeEntity sedeEntity) {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar el sede con id = {0}", sedesId);
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
    public void deleteSede(Long sedesId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar el sede con id = {0}", sedesId);
  
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
