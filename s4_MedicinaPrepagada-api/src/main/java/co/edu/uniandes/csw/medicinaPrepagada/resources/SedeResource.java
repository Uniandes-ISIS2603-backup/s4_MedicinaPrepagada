/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.medicinaPrepagada.resources;

import co.edu.uniandes.csw.medicinaPrepagada.dtos.SedeDTO;
import co.edu.uniandes.csw.medicinaPrepagada.dtos.SedeDetail;
import co.edu.uniandes.csw.medicinaPrepagada.exceptions.BusinessLogicException;
import java.util.ArrayList;
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

@Path("sedes")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class SedeResource 
{
    
    
       private static final Logger LOGGER = Logger.getLogger(SedeResource.class.getName());
       
       
/**
     * Crea un nuevo  sede con la informacion que se recibe en la cuerpo de
     * la petición y se regresa un objeto identico con un id auto-generado por
     * la base de datos.
     *
     * @param pSede {@link SedeDTO} - la  sede que se desea
     * guardar.
     * @return JSON {@link SedeDTO} - la  sede guardado con la atributo
     * id autogenerado.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando ya existe la  sede.
     */   
       
    @POST
    public SedeDTO createSede(SedeDTO pSede) throws BusinessLogicException 
    {
        LOGGER.log(Level.INFO, "SedeResource createSede: input: {0}", pSede.toString());
        // Convierte la DTO (json) en un objeto Entity para ser manejado por la lógica.

        // Invoca la lógica para crear la sede  nuevo
        
        // Como debe retornar un DTO (json) se invoca la constructor del DTO con argumento la entity nuevo
        SedeDTO nuevoSedeDTO = new SedeDTO();
        LOGGER.log(Level.INFO, "SedeResource createSede: output: {0}", nuevoSedeDTO.toString());
        return nuevoSedeDTO;
    }
    
    
    
 /**
     * Busca y devuelve todos los sedes que existen en la aplicacion.
     *
     * @return JSONArray {@link SedeDTO} -  los sedes 
     * encontradas en la aplicación. Si no hay ninguna retorna una lista vacía.
     */
    @GET
    public List<SedeDTO> getSedes()
    {
        LOGGER.info("SedeResource getSedes: input: void");
        List<SedeDTO> listaSedes = listEntity2DetailDTO();
                //listEntity2DetailDTO(sedesLogic.getSedes());
        LOGGER.log(Level.INFO, "SedeResource getSedes: output: {0}", listaSedes.toString());
        return listaSedes;
    }

    
    
        /**
     * Busca la sede con la id asociado recibido en la URL y la devuelve.
     *
     * @param sedeId Identificador ded sede que se esta buscando.
     * Este debe ser una cadena de dígitos.
     * @return JSON {@link SedeDTO} - El sede buscado
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la sede.
     */
    @GET
    @Path("{sedeId: \\d+}")
    public SedeDTO getSede(@PathParam("sedeId") Long pSedeId) throws WebApplicationException 
    {
        LOGGER.log(Level.INFO, "SedeResource getSede: input: {0}", pSedeId);
        //SedeEntity sedeEntity = null;
      //  if (sedeEntity == null) }
      //  {
      //      throw new WebApplicationException("El recurso /sedes/" + pSedeId + " no existe.", 404);
      //  }
        SedeDetail detailDTO = new SedeDetail();
        LOGGER.log(Level.INFO, "SedeResource getSede: output: {0}", detailDTO.toString());
        return detailDTO;
    }
    
    
    
    
    
        /**
     * Actualiza la sede con la id recibido en la URL con la informacion
     * que se recibe en la cuerpo de la petición.
     *
     * @param sedeId Identificador del sede que se desea
     * actualizar. Este debe ser una cadena de dígitos.
     * @param sedeId {@link SedeDTO} la sede que se desea
     * guardar.
     * @return JSON {@link SedeDTO} - la sede guardada.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la sede a
     * actualizar.
     */
    @PUT
    @Path("{sedeId: \\d+}")
    public SedeDTO updateSede(@PathParam("sedeId") Long pSedeId, SedeDetail pSede) throws WebApplicationException 
    {
        LOGGER.log(Level.INFO, "SedeResource updateSede: input: id:{0} , sede: {1}", new Object[]{pSedeId, pSede.toString()});
        pSede.setId(pSedeId);
      //  if (logic.get(pSedeId) == null) {
      //      throw new WebApplicationException("El recurso /sedes/" + pSedeId + " no existe.", 404);
      //  }
        SedeDetail detailDTO = new SedeDetail();
        LOGGER.log(Level.INFO, "SedeResource updateSede: output: {0}", detailDTO.toString());
        return detailDTO;

    }
    
    
    
    
    /**
     * Borra la sede con la id asociado recibido en la URL.
     *
     * @param sedeId Identificador de la sede que se desea borrar.
     * Este debe ser una cadena de dígitos.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando no se puede eliminar la sede.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la sede.
     */
    @DELETE
    @Path("{sedeId: \\d+}")
    public void deleteSede(@PathParam("sedeId") Long sedeId) throws BusinessLogicException 
    {
        LOGGER.log(Level.INFO, "SedeResource deleteSede: input: {0}", sedeId);
    //    if (logic.get(sedeId) == null) 
    //    {
    //        throw new WebApplicationException("El recurso /sedes/" + sedeId + " no existe.", 404);
    //    }
    //    logic.delet(sedeId);
        LOGGER.info("SedeResource deleteSede: output: void");
    }
    
    

    /**
     * Convierte una lista de entidades a DTO.
     *
     * Este método convierte una lista de objetos SedesEntitya una lista de
     * objetos SedeDTO (json)
     *
     * @param entityList corresponde a la lista de sede de tipo Entity
     * que vamos a convertir a DTO.
     * @return la lista de sede en forma DTO (json)
     */
    private List<SedeDTO> listEntity2DetailDTO() 
    {
        List<SedeDTO> list = new ArrayList<>();
     //   for (SedeEntity entity : entityList) {
     //       list.add(new SedeDetail(entity));
     //   }
        return list;
    }
       
    
}
