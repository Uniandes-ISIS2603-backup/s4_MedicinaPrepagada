/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.medicinaPrepagada.resources;

import co.edu.uniandes.csw.medicinaPrepagada.dtos.EspecialidadDTO;
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
 * @author estudiante
 */

@Path("especialidad")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class EspecialidadResource {
    private static final Logger LOGGER = Logger.getLogger(EspecialidadResource.class.getName());


    /**
     * Crea una nueva Especialidad con la informacion que se recibe en el cuerpo de
     * la petición y se regresa un objeto identico con un id auto-generado por
     * la base de datos.
     *
     * @param Especialidad {@link EspecialidadDTO} - La Especialidad que se desea
     * guardar.
     * @return JSON {@link EspecialidadDTO} - La Especialidad guardada con el atributo
     * id autogenerado.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando ya existe la Especialidad.
     */
    @POST
    public EspecialidadDTO createEspecialidad(EspecialidadDTO Especialidad) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "EspecialidadResource createEspecialidad: input: {0}", Especialidad.toString());
        // Convierte el DTO (json) en un objeto Entity para ser manejado por la lógica.
        //EspecialidadEntity editorialEntity = Especialidad.toEntity();
        // Invoca la lógica para crear la Especialidad nueva
        //EspecialidadEntity nuevoEspecialidadEntity = EspecialidadLogic.createEspecialidad(editorialEntity);
        // Como debe retornar un DTO (json) se invoca el constructor del DTO con argumento el entity nuevo
        EspecialidadDTO nuevoEspecialidadDTO = new EspecialidadDTO();
        LOGGER.log(Level.INFO, "EspecialidadResource createEspecialidad: output: {0}", nuevoEspecialidadDTO.toString());
        return nuevoEspecialidadDTO;
    }
    

    /**
     * Busca y devuelve todas las editoriales que existen en la aplicacion.
     *
     * @return JSONArray {@link EspecialidadDTO} - Las editoriales encontradas en
     * la aplicación. Si no hay ninguna retorna una lista vacía.
     */
    @GET
    public List<EspecialidadDTO> getEspecialidads() {
        LOGGER.info("EspecialidadResource getEspecialidads: input: void");
        List<EspecialidadDTO> listaEspecialidads = listEntity2DetailDTO(); //Paramtero List<EspecialidadEntity> entityList
        LOGGER.log(Level.INFO, "EspecialidadResource getEspecialidads: output: {0}", listaEspecialidads.toString());
        return listaEspecialidads;
    }

    /**
     * Busca la Especialidad con el id asociado recibido en la URL y la devuelve.
     *
     * @param EspecialidadsId Identificador de la Especialidad que se esta buscando.
     * Este debe ser una cadena de dígitos.
     * @return JSON {@link EspecialidadDTO} - La Especialidad buscada
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la Especialidad.
     */
    @GET
    @Path("{EspecialidadsId: \\d+}")
    public EspecialidadDTO getEspecialidad(@PathParam("EspecialidadsId") Long EspecialidadsId) throws WebApplicationException {
        LOGGER.log(Level.INFO, "EspecialidadResource getEspecialidad: input: {0}", EspecialidadsId);
//        EspecialidadEntity editorialEntity = EspecialidadLogic.getEspecialidad(EspecialidadsId);
//        if (EspecialidadEntity == null) {
//            throw new WebApplicationException("El recurso /Especialidads/" + EspecialidadsId + " no existe.", 404);
//        }
        EspecialidadDTO detailDTO = new EspecialidadDTO();
        LOGGER.log(Level.INFO, "EspecialidadResource getEspecialidad: output: {0}", detailDTO.toString());
        return detailDTO;
    }

    /**
     * Actualiza la Especialidad con el id recibido en la URL con la informacion
     * que se recibe en el cuerpo de la petición.
     *
     * @param EspecialidadsId Identificador de la Especialidad que se desea
     * actualizar. Este debe ser una cadena de dígitos.
     * @param Especialidad {@link EspecialidadDTO} La Especialidad que se desea guardar.
     * @return JSON {@link EspecialidadDTO} - La Especialidad guardada.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la Especialidad a
     * actualizar.
     */
    @PUT
    @Path("{EspecialidadsId: \\d+}")
    public EspecialidadDTO updateEspecialidad(@PathParam("EspecialidadsId") Long EspecialidadsId, EspecialidadDTO Especialidad) throws WebApplicationException {
        LOGGER.log(Level.INFO, "EspecialidadResource updateEspecialidad: input: id:{0} , Especialidad: {1}", new Object[]{EspecialidadsId, Especialidad.toString()});
//        Especialidad.setId(EspecialidadsId);
//        if (EspecialidadLogic.getEspecialidad(EspecialidadsId) == null) {
//            throw new WebApplicationException("El recurso /editorials/" + EspecialidadsId + " no existe.", 404);
//        }
        EspecialidadDTO detailDTO = new EspecialidadDTO(); //Parametro = EspecialidadLogic.updateEspecialidad(EspecialidadsId, Especialidad.toEntity())
        LOGGER.log(Level.INFO, "EspecialidadResource updateEspecialidad: output: {0}", detailDTO.toString());
        return detailDTO;
    }

    /**
     * Borra la Especialidad con el id asociado recibido en la URL.
     *
     * @param EspecialidadsId Identificador de la Especialidad que se desea borrar.
     * Este debe ser una cadena de dígitos.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando no se puede eliminar la Especialidad.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la Especialidad.
     */
    @DELETE
    @Path("{EspecialidadsId: \\d+}")
    public void deleteEspecialidad(@PathParam("EspecialidadsId") Long EspecialidadsId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "EspecialidadResource deleteEspecialidad: input: {0}", EspecialidadsId);
//        if (EspecialidadLogic.getEspecialidad(EspecialidadsId) == null) {
//            throw new WebApplicationException("El recurso /editorials/" + EspecialidadsId + " no existe.", 404);
//        }
//        EspecialidadLogic.deleteEspecialidad(EspecialidadsId);
        LOGGER.info("EspecialidadResource deleteEspecialidad: output: void");
    }

    /**
     * Convierte una lista de entidades a DTO.
     *
     * Este método convierte una lista de objetos EspecialidadEntity a una lista de
     * objetos EspecialidadDTO (json)
     *
     * @param entityList corresponde a la lista de editoriales de tipo Entity
     * que vamos a convertir a DTO.
     * @return la lista de editoriales en forma DTO (json)
     */
    private List<EspecialidadDTO> listEntity2DetailDTO() { //paramtero: List<EspecialidadEntity> entityList
        List<EspecialidadDTO> list = new ArrayList<>();
//        for (EspecialidadEntity entity : entityList) {
//            list.add(new EspecialidadDTO(entity));
//        }
        return list;
    }
}
