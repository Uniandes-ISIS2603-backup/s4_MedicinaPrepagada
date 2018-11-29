/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.medicinaPrepagada.resources;

import co.edu.uniandes.csw.medicinaPrepagada.dtos.ConsultorioDTO;
import co.edu.uniandes.csw.medicinaPrepagada.dtos.ConsultorioDetailDTO;
import co.edu.uniandes.csw.medicinaPrepagada.ejb.ConsultorioLogic;
import co.edu.uniandes.csw.medicinaPrepagada.entities.ConsultorioEntity;
import co.edu.uniandes.csw.medicinaPrepagada.exceptions.BusinessLogicException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;

/**
 *
 * @author Simon Guzman
 */
@Produces("application/json")
@Consumes("application/json")
public class ConsultorioResource 
{
       private static final Logger LOGGER = Logger.getLogger(ConsultorioResource.class.getName());
       
       @Inject 
       private ConsultorioLogic consultorioLogic;
       
  
/**
     * Crea un nuevo  consultorio con la informacion que se recibe en el cuerpo de
     * la petición y se regresa un objeto identico con un id auto-generado por
     * la base de datos.
     *
     * @param pConsultorio {@link ConsultorioDTO} - el  consultorio que se desea
     * guardar.
     * @return JSON {@link ConsultorioDTO} - el  consultorio guardado con el atributo
     * id autogenerado.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando ya existe el  consultorio.
     */   
       
    @POST
    public ConsultorioDTO createConsultorio(@PathParam("sedeId") Long sedeId, ConsultorioDTO pConsultorio) throws BusinessLogicException, WebApplicationException 
    {
        LOGGER.log(Level.INFO, "ConsultorioResource createConsultorio: input: {0}", pConsultorio.toString());
        // Convierte el DTO (json) en un objeto Entity para ser manejado por la lógica.
        
        // Invoca la lógica para crear el consultorio  nuevo
        
        // Como debe retornar un DTO (json) se invoca el constructor del DTO con argumento el entity nuevo
        ConsultorioDTO nuevoConsultorioDTO = new ConsultorioDTO(consultorioLogic.createConsultorio(pConsultorio.toEntity(), sedeId));
        LOGGER.log(Level.INFO, "ConsultorioResource createConsultorio: output: {0}", nuevoConsultorioDTO.toString());
        return nuevoConsultorioDTO;
    }
    
    
    
 /**
     * Busca y devuelve todos los consultorios que existen en la aplicacion.
     *
     * @return JSONArray {@link ConsultorioDTO} -  los consultorios 
     * encontradas en la aplicación. Si no hay ninguna retorna una lista vacía.
     */
    @GET
    public List<ConsultorioDetailDTO> getConsultorios(@PathParam("sedeId") Long sedeId)
    {
        LOGGER.info("ConsultorioResource getConsultorios: input: void");
        List<ConsultorioDetailDTO> listaConsultorios = listEntity2DetailDTO(consultorioLogic.getConsultorios(sedeId));
        LOGGER.log(Level.INFO, "ConsultorioResource getConsultorios: output: {0}", listaConsultorios.toString());
        return listaConsultorios;
    }

    
    
        /**
     * Busca el consultorio con el id asociado recibido en la URL y la devuelve.
     *
     * @param consultorioId Identificador ded consultorio que se esta buscando.
     * Este debe ser una cadena de dígitos.
     * @return JSON {@link ConsultorioDTO} - El consultorio buscado
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el consultorio.
     */
    @GET
    @Path("{consultorioId: \\d+}")
    public ConsultorioDetailDTO getConsultorio(@PathParam("sedeId") Long sedeId, @PathParam("consultorioId") Long pConsultorioId) throws WebApplicationException 
    {
        LOGGER.log(Level.INFO, "ConsultorioResource getConsultorio: input: {0}", pConsultorioId);
        ConsultorioEntity consultorioEntity = consultorioLogic.getConsultorio(sedeId, pConsultorioId);
        if (consultorioEntity == null) 
        {
            throw new WebApplicationException("El recurso /consultorio/" + pConsultorioId + " no existe .", 404);
        }
        ConsultorioDetailDTO detailDTO = new ConsultorioDetailDTO(consultorioEntity);
        LOGGER.log(Level.INFO, "ConsultorioResource getConsultorio: output: {0}", detailDTO.toString());
        return detailDTO;
    }
    
    
    
    
    
        /**
     * Actualiza el consultorio con el id recibido en la URL con la informacion
     * que se recibe en el cuerpo de la petición.
     *
     * @param pConsultorioId
     * @param pConsultorio
     * @return JSON {@link ConsultorioDTO} - el consultorio guardada.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el consultorio a
     * actualizar.
     * @throws co.edu.uniandes.csw.medicinaPrepagada.exceptions.BusinessLogicException
     */
    @PUT
    @Path("{consultorioId: \\d+}")
    public ConsultorioDTO updateConsultorio(@PathParam("sedeId") Long sedeId, @PathParam("consultorioId") Long pConsultorioId, ConsultorioDetailDTO pConsultorio) throws WebApplicationException, BusinessLogicException 
    {
         LOGGER.log(Level.INFO, "Estoy en recurso "+ sedeId);
        LOGGER.log(Level.INFO, "ConsultorioResource updateConsultorio: input: id:{0} , consultorio: {1}", new Object[]{pConsultorioId, pConsultorio.toString()});
        pConsultorio.setId(pConsultorioId);
        if (consultorioLogic.getConsultorio(sedeId, pConsultorioId) == null) 
        {
            throw new WebApplicationException("El recurso /consultorio/ que desea actualizar" + pConsultorioId + " no existe.", 404);
        }
        ConsultorioDetailDTO detailDTO = new ConsultorioDetailDTO(consultorioLogic.updateConsultorio(sedeId, pConsultorio.toEntity()));
        LOGGER.log(Level.INFO, "ConsultorioResource updateConsultorio: output: {0}", detailDTO.toString());
        return detailDTO;

    }
    
    
    
    
    /**
     * Borra el consultorio con el id asociado recibido en la URL.
     *
     * @param consultorioId Identificador de el consultorio que se desea borrar.
     * Este debe ser una cadena de dígitos.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando no se puede eliminar el consultorio.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el consultorio.
     */
    @DELETE
    @Path("{consultorioId: \\d+}")
    public void deleteConsultorio(@PathParam("sedeId") Long sedeId, @PathParam("consultorioId") Long consultorioId) throws BusinessLogicException 
    {
        LOGGER.log(Level.INFO, "ConsultorioResource deleteConsultorio: input: {0}", consultorioId);
        if (consultorioLogic.getConsultorio(sedeId, consultorioId) == null) 
        {
            throw new WebApplicationException("El recurso /consultorio/ que desea eliminar" + consultorioId + " no existe.", 404);
        }
        consultorioLogic.deleteConsultorio(sedeId, consultorioId);
        LOGGER.info("ConsultorioResource deleteConsultorio: output: void");
    }
    
    

    /**
     * Convierte una lista de entidades a DTO.
     *
     * Este método convierte una lista de objetos ConsultoriosEntitya una lista de
     * objetos ConsultorioDTO (json)
     *
     * @param entityList corresponde a la lista de consultorio de tipo Entity
     * que vamos a convertir a DTO.
     * @return la lista de consultorio en forma DTO (json)
     */
    private List<ConsultorioDetailDTO> listEntity2DetailDTO(List<ConsultorioEntity> entityList) 
    {
        List<ConsultorioDetailDTO> list = new ArrayList<>();
        for (ConsultorioEntity entity : entityList) 
        {
            list.add(new ConsultorioDetailDTO(entity));
        }
        return list;
    }
       
       
       
}
