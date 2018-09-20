/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.medicinaPrepagada.ejb;

import co.edu.uniandes.csw.medicinaPrepagada.entities.ConsultorioEntity;
import co.edu.uniandes.csw.medicinaPrepagada.entities.SedeEntity;
import co.edu.uniandes.csw.medicinaPrepagada.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.medicinaPrepagada.persistence.ConsultorioPersistence;
import co.edu.uniandes.csw.medicinaPrepagada.persistence.EspecialidadPersistence;
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
public class ConsultorioLogic 
{
   
    private static final Logger LOGGER = Logger.getLogger(ConsultorioLogic.class.getName());

    @Inject
    private ConsultorioPersistence persistence;
    
    @Inject
    private SedePersistence persistenceSede;
    
    @Inject
    private EspecialidadPersistence persistenceEspecialidad;
    
    
         /**
     * Se encarga de crear un Consultorio en la base de datos.
     *
     * @param consultorioEntity Objeto de ConsultorioEntity con los datos nuevos
     * @return Objeto de ConsultorioEntity con los datos nuevos y su ID.
     */
    public ConsultorioEntity createConsultorio(ConsultorioEntity consultorioEntity) throws BusinessLogicException
    {
        LOGGER.log(Level.INFO, "Inicia proceso de creación del consultorio");
        //Verifica que el edificio sea valido
        if (!validateEdificio(consultorioEntity.getEdificio()))
           throw new BusinessLogicException ("EL edificio no puede ser vacio ");
        //Verifica que tenga una sede
        if (!validateSede(consultorioEntity.getSede()))
            throw new BusinessLogicException("Un consultorio debe tener una sede");
        //Verifica que la sede exista
        if (persistenceSede.find(consultorioEntity.getSede().getId())== null)
            throw new BusinessLogicException("La sede del consultorio no existe");
        //Verifica que la sede, edificio y oficna no sena la misma
        if (!validateSedeEdificioOficina(consultorioEntity))
            throw new BusinessLogicException("Un consultorio no puede tener la misma Sde, edificio y numero de oficina que otro consultorio");
        //Verifica que la especialidad si exista
        if (persistenceEspecialidad.find(consultorioEntity.getEspecialidad().getNombre())== null)
            throw new BusinessLogicException("La especialidad que intenta asignar no existe");
        
        ConsultorioEntity newConsultorioEntity = persistence.create(consultorioEntity);
        LOGGER.log(Level.INFO, "Termina proceso de creación del consultorio");
        return newConsultorioEntity;
    }
    
    
     /**
     * Obtiene la lista de los registros de Consultorio.
     *
     * @return Colección de objetos de ConsultorioEntity.
     */
    public List<ConsultorioEntity> getConsultorios() 
    {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todos los consultorioes");
        List<ConsultorioEntity> lista = persistence.findAll();
        LOGGER.log(Level.INFO, "Termina proceso de consultar todos los consultorioes");
        return lista;
    }

    /**
     * Obtiene los datos de una instancia de Consultorio a partir de su ID.
     *
     * @param consultoriosId Identificador de la instancia a consultar
     * @return Instancia de ConsultorioEntity con los datos del Consultorio consultado.
     */
    public ConsultorioEntity getConsultorio(Long consultoriosId) 
    {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar el consultorio con id = {0}", consultoriosId);
        ConsultorioEntity consultorioEntity = persistence.find(consultoriosId);
        if (consultorioEntity == null)
        {
            LOGGER.log(Level.SEVERE, "La consultorio con el id = {0} no existe", consultoriosId);
        }
        LOGGER.log(Level.INFO, "Termina proceso de consultar el consultorio con id = {0}", consultoriosId);
        return consultorioEntity;
    }
    
    
            /**
     * Actualiza la información de una instancia de Consultorio.
     *
     * @param consultoriosId Identificador de la instancia a actualizar
     * @param consultorioEntity Instancia de ConsultorioEntity con los nuevos datos.
     * @return Instancia de ConsultorioEntity con los datos actualizados.
     */
    public ConsultorioEntity updateConsultorio(Long consultoriosId, ConsultorioEntity consultorioEntity) throws BusinessLogicException
    {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar el consultorio con id = {0}", consultoriosId);
        
        ConsultorioEntity pConsultorioOld = persistence.find(consultoriosId);
        //Verifica que la consultorio que se intenta modificar exista
        if (pConsultorioOld == null)
            throw new BusinessLogicException ("El consultorio que intenta modificar no existe");
        //Verifica que no se intente cambiar el Id
        if (consultorioEntity.getId() != consultoriosId)
            throw new BusinessLogicException("No se puede cambiar el id de la consultorio");
         //Verifica que el edificio sea valido
        if (!validateEdificio(consultorioEntity.getEdificio()))
           throw new BusinessLogicException ("EL edificio no puede ser vacio ");
         //Verifica que la sede no cambie
        if (!persistence.find(consultoriosId).getSede().equals(consultorioEntity.getSede()))
            throw new BusinessLogicException("Un consultorio no puede cambiar de sede");
         //Verifica que la sede, edificio y oficna no sena la misma
        if (!validateSedeEdificioOficina(consultorioEntity))
            throw new BusinessLogicException("Un consultorio no puede tener la misma Sde, edificio y numero de oficina");
        // validador de horarios?
        
        //Verifica que la especialidad si exista
        if (persistenceEspecialidad.find(consultorioEntity.getEspecialidad().getNombre())== null)
            throw new BusinessLogicException("La especialidad que intenta asignar no existe ");
        
  
        ConsultorioEntity newConsultorioEntity = persistence.update(consultorioEntity);
        LOGGER.log(Level.INFO, "Termina proceso de actualizar el consultorio con id = {0}", consultoriosId);
        return newConsultorioEntity;
    }

    /**
     * Elimina una instancia de Consultorio de la base de datos.
     *
     * @param consultoriosId Identificador de la instancia a eliminar.
     * @throws BusinessLogicException si el consultorio tiene libros asociados.
     */
    public void deleteConsultorio(Long consultoriosId) throws BusinessLogicException 
    {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar el consultorio con id = {0}", consultoriosId);
  
        
        //Verifica que la consultorio que se intenta modificar exista
        if (persistence.find(consultoriosId) == null)
            throw new BusinessLogicException ("El consultorio que intenta eliminar no existe");
        
        persistence.delete(consultoriosId);
        LOGGER.log(Level.INFO, "Termina proceso de borrar el consultorio con id = {0}", consultoriosId);
    }
    
    
    
    private boolean validateEdificio (String pNombre)
    {
        return !(pNombre == null || pNombre.isEmpty());
    }
    
    
        private boolean validateSedeEdificioOficina (ConsultorioEntity pConsultorio)
    {
        List <ConsultorioEntity> pConsultorios = persistence.findAll();
        for (int i=0; i<pConsultorios.size();i++)
        {
            ConsultorioEntity temp = pConsultorios.get(i);
            if (temp.getSede().equals(pConsultorio.getSede())&& temp.getEdificio().equals(pConsultorio.getEdificio())
                    && temp.getNOficina() == pConsultorio.getNOficina())
                    return false;
        }
        return true;
    }
        
    private boolean validateSede (SedeEntity pSede)
    {
        return !(pSede == null);
    }
    
}
