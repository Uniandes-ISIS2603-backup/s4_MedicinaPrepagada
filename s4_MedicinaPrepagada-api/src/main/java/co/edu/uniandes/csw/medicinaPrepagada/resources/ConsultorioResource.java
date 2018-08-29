/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.medicinaPrepagada.resources;

import co.edu.uniandes.csw.medicinaPrepagada.dtos.ConsultorioDTO;
import co.edu.uniandes.csw.medicinaPrepagada.dtos.ConsultorioDetailDTO;
import co.edu.uniandes.csw.medicinaPrepagada.exceptions.BusinessLogicException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
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
@Path("consultorios")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class ConsultorioResource 
{
       private static final Logger LOGGER = Logger.getLogger(ConsultorioResource.class.getName());
  
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
    public ConsultorioDTO createConsultorio(ConsultorioDTO pConsultorio) throws BusinessLogicException 
    {
        LOGGER.log(Level.INFO, "ConsultorioResource createConsultorio: input: {0}", pConsultorio.toString());
        // Convierte el DTO (json) en un objeto Entity para ser manejado por la lógica.

        // Invoca la lógica para crear el consultorio  nuevo
        
        // Como debe retornar un DTO (json) se invoca el constructor del DTO con argumento el entity nuevo
        ConsultorioDTO nuevoConsultorioDTO = new ConsultorioDTO();
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
    public List<ConsultorioDTO> getConsultorios()
    {
        LOGGER.info("ConsultorioResource getConsultorios: input: void");
        List<ConsultorioDTO> listaConsultorios = listEntity2DetailDTO();
                //listEntity2DetailDTO(consultoriosLogic.getConsultorios());
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
    public ConsultorioDTO getConsultorio(@PathParam("consultorioId") Long pConsultorioId) throws WebApplicationException 
    {
        LOGGER.log(Level.INFO, "ConsultorioResource getConsultorio: input: {0}", pConsultorioId);
        //ConsultorioEntity consultorioEntity = null;
      //  if (consultorioEntity == null) }
      //  {
      //      throw new WebApplicationException("El recurso /consultorios/" + pConsultorioId + " no existe.", 404);
      //  }
        ConsultorioDetailDTO detailDTO = new ConsultorioDetailDTO();
        LOGGER.log(Level.INFO, "ConsultorioResource getConsultorio: output: {0}", detailDTO.toString());
        return detailDTO;
    }
    
    
    
    
    
        /**
     * Actualiza el consultorio con el id recibido en la URL con la informacion
     * que se recibe en el cuerpo de la petición.
     *
     * @param consultorioId Identificador del consultorio que se desea
     * actualizar. Este debe ser una cadena de dígitos.
     * @param consultorioId {@link ConsultorioDTO} el consultorio que se desea
     * guardar.
     * @return JSON {@link ConsultorioDTO} - el consultorio guardada.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el consultorio a
     * actualizar.
     */
    @PUT
    @Path("{consultorioId: \\d+}")
    public ConsultorioDTO updateConsultorio(@PathParam("consultorioId") Long pConsultorioId, ConsultorioDetailDTO pConsultorio) throws WebApplicationException 
    {
        LOGGER.log(Level.INFO, "ConsultorioResource updateConsultorio: input: id:{0} , consultorio: {1}", new Object[]{pConsultorioId, pConsultorio.toString()});
        pConsultorio.setId(pConsultorioId);
      //  if (logic.get(pConsultorioId) == null) {
      //      throw new WebApplicationException("El recurso /consultorios/" + pConsultorioId + " no existe.", 404);
      //  }
        ConsultorioDetailDTO detailDTO = new ConsultorioDetailDTO();
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
    public void deleteConsultorio(@PathParam("consultorioId") Long consultorioId) throws BusinessLogicException 
    {
        LOGGER.log(Level.INFO, "ConsultorioResource deleteConsultorio: input: {0}", consultorioId);
    //    if (logic.get(consultorioId) == null) 
    //    {
    //        throw new WebApplicationException("El recurso /consultorios/" + consultorioId + " no existe.", 404);
    //    }
    //    logic.delet(consultorioId);
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
    private List<ConsultorioDTO> listEntity2DetailDTO() 
    {
        List<ConsultorioDTO> list = new ArrayList<>();
     //   for (ConsultorioEntity entity : entityList) {
     //       list.add(new ConsultorioDetail(entity));
     //   }
        return list;
    }
       
       
       
}