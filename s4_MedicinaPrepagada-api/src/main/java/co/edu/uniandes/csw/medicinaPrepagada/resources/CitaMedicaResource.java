/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.medicinaPrepagada.resources;

import co.edu.uniandes.csw.medicinaPrepagada.dtos.CitaMedicaDTO;
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
 *Clase que implementa el recurso CitaMedica
 * @author Daniel Ivan Romero
 */


@Path("citaMedica")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class CitaMedicaResource 
{
    private static final Logger LOGGER = Logger.getLogger(CitaMedicaResource.class.getName());


    /**
     * Crea una nueva CitaMedica con la informacion que se recibe en el cuerpo de
     * la petición y se regresa un objeto identico con un id auto-generado por
     * la base de datos.
     *
     * @param CitaMedica {@link CitaMedicaDTO} - La CitaMedica que se desea
     * guardar.
     * @return JSON {@link CitaMedicaDTO} - La CitaMedica guardada con el atributo
     * id autogenerado.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando ya existe la CitaMedica.
     */
    @POST
    public CitaMedicaDTO createCitaMedica(CitaMedicaDTO CitaMedica) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "CitaMedicaResource createCitaMedica: input: {0}", CitaMedica.toString());
        // Convierte el DTO (json) en un objeto Entity para ser manejado por la lógica.
        //CitaMedicaEntity editorialEntity = CitaMedica.toEntity();
        // Invoca la lógica para crear la CitaMedica nueva
        //CitaMedicaEntity nuevoCitaMedicaEntity = CitaMedicaLogic.createCitaMedica(editorialEntity);
        // Como debe retornar un DTO (json) se invoca el constructor del DTO con argumento el entity nuevo
        CitaMedicaDTO nuevoCitaMedicaDTO = new CitaMedicaDTO();
        LOGGER.log(Level.INFO, "CitaMedicaResource createCitaMedica: output: {0}", nuevoCitaMedicaDTO.toString());
        return nuevoCitaMedicaDTO;
    }
    

    /**
     * Busca y devuelve todas las editoriales que existen en la aplicacion.
     *
     * @return JSONArray {@link CitaMedicaDTO} - Las editoriales encontradas en
     * la aplicación. Si no hay ninguna retorna una lista vacía.
     */
    @GET
    public List<CitaMedicaDTO> getCitaMedicas() {
        LOGGER.info("CitaMedicaResource getCitaMedicas: input: void");
        List<CitaMedicaDTO> listaCitaMedicas = listEntity2DetailDTO(); //Paramtero List<CitaMedicaEntity> entityList
        LOGGER.log(Level.INFO, "CitaMedicaResource getCitaMedicas: output: {0}", listaCitaMedicas.toString());
        return listaCitaMedicas;
    }

    /**
     * Busca la CitaMedica con el id asociado recibido en la URL y la devuelve.
     *
     * @param CitaMedicasId Identificador de la CitaMedica que se esta buscando.
     * Este debe ser una cadena de dígitos.
     * @return JSON {@link CitaMedicaDTO} - La CitaMedica buscada
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la CitaMedica.
     */
    @GET
    @Path("{CitaMedicasId: \\d+}")
    public CitaMedicaDTO getCitaMedica(@PathParam("CitaMedicasId") Long CitaMedicasId) throws WebApplicationException {
        LOGGER.log(Level.INFO, "CitaMedicaResource getCitaMedica: input: {0}", CitaMedicasId);
//        CitaMedicaEntity editorialEntity = CitaMedicaLogic.getCitaMedica(CitaMedicasId);
//        if (CitaMedicaEntity == null) {
//            throw new WebApplicationException("El recurso /CitaMedicas/" + CitaMedicasId + " no existe.", 404);
//        }
        CitaMedicaDTO detailDTO = new CitaMedicaDTO();
        LOGGER.log(Level.INFO, "CitaMedicaResource getCitaMedica: output: {0}", detailDTO.toString());
        return detailDTO;
    }

    /**
     * Actualiza la CitaMedica con el id recibido en la URL con la informacion
     * que se recibe en el cuerpo de la petición.
     *
     * @param CitaMedicasId Identificador de la CitaMedica que se desea
     * actualizar. Este debe ser una cadena de dígitos.
     * @param CitaMedica {@link CitaMedicaDTO} La CitaMedica que se desea guardar.
     * @return JSON {@link CitaMedicaDTO} - La CitaMedica guardada.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la CitaMedica a
     * actualizar.
     */
    @PUT
    @Path("{CitaMedicasId: \\d+}")
    public CitaMedicaDTO updateCitaMedica(@PathParam("CitaMedicasId") Long CitaMedicasId, CitaMedicaDTO CitaMedica) throws WebApplicationException {
        LOGGER.log(Level.INFO, "CitaMedicaResource updateCitaMedica: input: id:{0} , CitaMedica: {1}", new Object[]{CitaMedicasId, CitaMedica.toString()});
//        CitaMedica.setId(CitaMedicasId);
//        if (CitaMedicaLogic.getCitaMedica(CitaMedicasId) == null) {
//            throw new WebApplicationException("El recurso /editorials/" + CitaMedicasId + " no existe.", 404);
//        }
        CitaMedicaDTO detailDTO = new CitaMedicaDTO(); //Parametro = CitaMedicaLogic.updateCitaMedica(CitaMedicasId, CitaMedica.toEntity())
        LOGGER.log(Level.INFO, "CitaMedicaResource updateCitaMedica: output: {0}", detailDTO.toString());
        return detailDTO;
    }

    /**
     * Borra la CitaMedica con el id asociado recibido en la URL.
     *
     * @param CitaMedicasId Identificador de la CitaMedica que se desea borrar.
     * Este debe ser una cadena de dígitos.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando no se puede eliminar la CitaMedica.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la CitaMedica.
     */
    @DELETE
    @Path("{CitaMedicasId: \\d+}")
    public void deleteCitaMedica(@PathParam("CitaMedicasId") Long CitaMedicasId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "CitaMedicaResource deleteCitaMedica: input: {0}", CitaMedicasId);
//        if (CitaMedicaLogic.getCitaMedica(CitaMedicasId) == null) {
//            throw new WebApplicationException("El recurso /editorials/" + CitaMedicasId + " no existe.", 404);
//        }
//        CitaMedicaLogic.deleteCitaMedica(CitaMedicasId);
        LOGGER.info("CitaMedicaResource deleteCitaMedica: output: void");
    }

    /**
     * Convierte una lista de entidades a DTO.
     *
     * Este método convierte una lista de objetos CitaMedicaEntity a una lista de
     * objetos CitaMedicaDTO (json)
     *
     * @param entityList corresponde a la lista de editoriales de tipo Entity
     * que vamos a convertir a DTO.
     * @return la lista de editoriales en forma DTO (json)
     */
    private List<CitaMedicaDTO> listEntity2DetailDTO() { //paramtero: List<CitaMedicaEntity> entityList
        List<CitaMedicaDTO> list = new ArrayList<>();
//        for (CitaMedicaEntity entity : entityList) {
//            list.add(new CitaMedicaDTO(entity));
//        }
        return list;
    }
}
