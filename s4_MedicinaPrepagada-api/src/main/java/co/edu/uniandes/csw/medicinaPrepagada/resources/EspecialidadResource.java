/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.medicinaPrepagada.resources;

import co.edu.uniandes.csw.medicinaPrepagada.dtos.EspecialidadDTO;
import co.edu.uniandes.csw.medicinaPrepagada.dtos.SedeDTO;
import co.edu.uniandes.csw.medicinaPrepagada.ejb.EspecialidadLogic;
import co.edu.uniandes.csw.medicinaPrepagada.entities.EspecialidadEntity;
import co.edu.uniandes.csw.medicinaPrepagada.exceptions.BusinessLogicException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
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
 * @author Daniel Ivan Romero
 */

@Path("especialidad")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class EspecialidadResource {
    private static final Logger LOGGER = Logger.getLogger(EspecialidadResource.class.getName());

    @Inject
    private EspecialidadLogic especialidadLogic;
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
    public EspecialidadDTO createEspecialidad(EspecialidadDTO especialidad) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "EspecialidadResource createEspecialidad: input: {0}", especialidad.toString());
        EspecialidadDTO especialidadDTO = new EspecialidadDTO(especialidadLogic.createEspecialidad(especialidad.toEntity()));
        LOGGER.log(Level.INFO, "EspecialidadResource createEspecialidad: output: {0}", especialidadDTO.toString());
        return especialidadDTO;
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
        List<EspecialidadDTO> listaEspecialidads = listEntity2DetailDTO(especialidadLogic.getEspecialidades()); 
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
    @Path("{especialidadsId: [a-zA-Z][a-zA-Z]*}")
    public EspecialidadDTO getEspecialidad(@PathParam("EspecialidadsId") String EspecialidadsId) throws WebApplicationException {
        LOGGER.log(Level.INFO, "EspecialidadResource getEspecialidad: input: {0}", EspecialidadsId);
        EspecialidadEntity especialidadEntity = especialidadLogic.getEspecialidad(EspecialidadsId);
        if (especialidadEntity == null) {
            throw new WebApplicationException("El recurso /Especialidads/" + EspecialidadsId + " no existe.", 404);
        }
        EspecialidadDTO detailDTO = new EspecialidadDTO(especialidadEntity);
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
    @Path("{especialidadsId: [a-zA-Z][a-zA-Z]*}")
    public EspecialidadDTO updateEspecialidad(@PathParam("especialidadsId") String especialidadsId, EspecialidadDTO especialidad) throws WebApplicationException, BusinessLogicException {
        LOGGER.log(Level.INFO, "EspecialidadResource updateEspecialidad: input: id:{0} , Especialidad: {1}", new Object[]{especialidadsId, especialidad.toString()});
        especialidad.setNombre(especialidadsId);
        if (especialidadLogic.getEspecialidad(especialidadsId) == null) {
            throw new WebApplicationException("El recurso /editorials/" + especialidadsId + " no existe.", 404);
        }
        EspecialidadDTO detailDTO = new EspecialidadDTO(especialidadLogic.updateEspecialidad(especialidadsId, especialidad.toEntity()));
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
    @Path("{especialidadsId: [a-zA-Z][a-zA-Z]*}")
    public void deleteEspecialidad(@PathParam("EspecialidadsId") String especialidadsId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "EspecialidadResource deleteEspecialidad: input: {0}", especialidadsId);
        if (especialidadLogic.getEspecialidad(especialidadsId) == null) {
            throw new WebApplicationException("El recurso /editorials/" + especialidadsId + " no existe.", 404);
        }
        especialidadLogic.deleteEspecialidad(especialidadsId);
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
    private List<EspecialidadDTO> listEntity2DetailDTO(List<EspecialidadEntity> entityList){
        List<EspecialidadDTO> list = new ArrayList<>();
        for (EspecialidadEntity entity : entityList) {
            list.add(new EspecialidadDTO(entity));
        }
        return list;
    }
}
