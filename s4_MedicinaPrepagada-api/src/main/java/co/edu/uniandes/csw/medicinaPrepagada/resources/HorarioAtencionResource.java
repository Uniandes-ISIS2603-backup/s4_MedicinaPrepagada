/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.medicinaPrepagada.resources;

import co.edu.uniandes.csw.medicinaPrepagada.dtos.HorarioAtencionDTO;
import co.edu.uniandes.csw.medicinaPrepagada.dtos.HorarioAtencionDetailDTO;
import co.edu.uniandes.csw.medicinaPrepagada.ejb.HorarioAtencionLogic;
import co.edu.uniandes.csw.medicinaPrepagada.entities.HorarioAtencionEntity;
import co.edu.uniandes.csw.medicinaPrepagada.entities.SedeEntity;
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
 * @author Simon Guzman
 */
@Path("horariosAtencion")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class HorarioAtencionResource
{

       private static final Logger LOGGER = Logger.getLogger(HorarioAtencionResource.class.getName());
       @Inject
       private HorarioAtencionLogic horarioAtencionLogic;
       
       
       
     /**
     * Crea un nuevo  horarios de atencion con la informacion que se recibe en el cuerpo de
     * la petición y se regresa un objeto identico con un id auto-generado por
     * la base de datos.
     *
     * @param pHorarioAtencion {@link HorarioAtencionDTO} - el  horarios de atencion que se desea
     * guardar.
     * @param pSede
     * @return JSON {@link HorarioAtencionDTO} - el  horarios de atencion guardado con el atributo
     * id autogenerado.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando ya existe el  horario de atencion.
     */   
       
    @POST
    public HorarioAtencionDTO createHorarioAtencion(HorarioAtencionDTO pHorarioAtencion) throws BusinessLogicException //, SedeDTO pSede
    {
        LOGGER.log(Level.INFO, "HorarioAtencionResource createHorarioAtencion: input: {0}", pHorarioAtencion.toString());
        // Convierte el DTO (json) en un objeto Entity para ser manejado por la lógica.

        // Invoca la lógica para crear el horario de atencion  nuevo
        HorarioAtencionEntity pHorEntityt = pHorarioAtencion.toEntity();
        SedeEntity pSedeEntity = pHorarioAtencion.getConsultorio().getSede().toEntity();
        LOGGER.log(Level.INFO, "Las entidades 0909090" + pHorEntityt.toString() + "SEDE" + pSedeEntity.toString() );
        HorarioAtencionDTO  nuevoHorarioAtencionDTO = new HorarioAtencionDTO (horarioAtencionLogic.createHorarioAtencion(pHorEntityt, pSedeEntity));
        // Como debe retornar un DTO (json) se invoca el constructor del DTO con argumento el entity nuevo
        LOGGER.log(Level.INFO, "HorarioAtencionResource createHorarioAtencion: output: {0}", nuevoHorarioAtencionDTO.toString());
        return nuevoHorarioAtencionDTO;
    }
    
    
    
 /**
     * Busca y devuelve todos los horarios de atencion que existen en la aplicacion.
     *
     * @return JSONArray {@link HorarioAtencionDTO} -  los horarios de atencion 
     * encontradas en la aplicación. Si no hay ninguna retorna una lista vacía.
     */
    @GET
    public List<HorarioAtencionDetailDTO> getHorariosAtencion()
    {
        LOGGER.info("HorarioAtencionResource getHorariosAtencion: input: void");
        List<HorarioAtencionDetailDTO> listaHorariosAtencion = listEntity2DetailDTO(horarioAtencionLogic.getHorarioAtencions());
        LOGGER.log(Level.INFO, "HorarioAtencionResource getHorariosAtencion: output: {0}", listaHorariosAtencion.toString());
        return listaHorariosAtencion;
    }

    
    
        /**
     * Busca el horario de atencion con el id asociado recibido en la URL y la devuelve.
     *
     * @param horarioAtencionId Identificador ded horario de atencion que se esta buscando.
     * Este debe ser una cadena de dígitos.
     * @return JSON {@link HorarioAtencionDTO} - El horario de atencion buscado
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el horario de atencion.
     */
    @GET
    @Path("{horarioAtencionId: \\d+}")
    public HorarioAtencionDetailDTO getHorarioAtencion(@PathParam("horarioAtencionId") Long pHorarioAtencionId) throws WebApplicationException 
    {
        LOGGER.log(Level.INFO, "HorarioAtencionResource getHorarioAtencion: input: {0}", pHorarioAtencionId);
        HorarioAtencionEntity horarioAtencionEntity = horarioAtencionLogic.getHorarioAtencion(pHorarioAtencionId);
        if (horarioAtencionEntity == null) 
        {
            throw new WebApplicationException("El recurso /horariosAtencion/ con el id " + pHorarioAtencionId + " no existe.", 404);
        }
        HorarioAtencionDetailDTO detailDTO = new HorarioAtencionDetailDTO(horarioAtencionEntity);
        LOGGER.log(Level.INFO, "HorarioAtencionResource getHorarioAtencion: output: {0}", detailDTO.toString());
        return detailDTO;
    }

        /**
     * Actualiza el horario de atencion con el id recibido en la URL con la informacion
     * que se recibe en el cuerpo de la petición.
     *
     * @param pHorarioAtencionId
     * @param pHorarioAtencion
     * @return JSON {@link HorarioAtencionDTO} - el horario de atencion guardada.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el horario de atencion a
     * actualizar.
     * @throws co.edu.uniandes.csw.medicinaPrepagada.exceptions.BusinessLogicException
     */
    @PUT
    @Path("{horarioAtencionId: \\d+}")
    public HorarioAtencionDTO updateHorarioAtencion(@PathParam("horarioAtencionId") Long pHorarioAtencionId, HorarioAtencionDetailDTO pHorarioAtencion) throws WebApplicationException, BusinessLogicException 
    {
        LOGGER.log(Level.INFO, "HorarioAtencionResource updateHorarioAtencion: input: id:{0} , horarioAtencion: {1}", new Object[]{pHorarioAtencionId, pHorarioAtencion.toString()});
        pHorarioAtencion.setId(pHorarioAtencionId);
        if (horarioAtencionLogic.getHorarioAtencion(pHorarioAtencionId) == null) 
        {
            throw new WebApplicationException("El recurso /horariosAtencion/ que quiere editar con id " + pHorarioAtencionId + " no existe.", 404);
        }
        HorarioAtencionDetailDTO detailDTO = new HorarioAtencionDetailDTO(horarioAtencionLogic.updateHorarioAtencion(pHorarioAtencionId, pHorarioAtencion.toEntity()));
        LOGGER.log(Level.INFO, "HorarioAtencionResource updateHorarioAtencion: output: {0}", detailDTO.toString());
        return detailDTO;

    }

    /**
     * Borra el horario de atencion con el id asociado recibido en la URL.
     *
     * @param horarioAtencionId Identificador de el horario de atencion que se desea borrar.
     * Este debe ser una cadena de dígitos.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando no se puede eliminar el horario de atencion.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el horario de atencion.
     */
    @DELETE
    @Path("{horarioAtencionId: \\d+}")
    public void deleteHorarioAtencion(@PathParam("horarioAtencionId") Long horarioAtencionId) throws BusinessLogicException 
    {
        LOGGER.log(Level.INFO, "HorarioAtencionResource deleteHorarioAtencion: input: {0}", horarioAtencionId);
        if (horarioAtencionLogic.getHorarioAtencion(horarioAtencionId) == null) 
        {
            throw new WebApplicationException("El recurso /horariosAtencion/ que intenta eliminar con id" + horarioAtencionId + " no existe.", 404);
        }
        horarioAtencionLogic.deleteHorarioAtencion(horarioAtencionId);
        LOGGER.info("HorarioAtencionResource deleteHorarioAtencion: output: void");
    }
    
    

    /**
     * Convierte una lista de entidades a DTO.
     *
     * Este método convierte una lista de objetos HorariosAtencionEntity a una lista de
     * objetos HorarioAtencionDTO (json)
     *
     * @param entityList corresponde a la lista de horarios de atencion de tipo Entity
     * que vamos a convertir a DTO.
     * @return la lista de horarios de atencion en forma DTO (json)
     */
    private List<HorarioAtencionDetailDTO> listEntity2DetailDTO(List<HorarioAtencionEntity> entityList) 
    {
        List<HorarioAtencionDetailDTO> list = new ArrayList<>();
        for (HorarioAtencionEntity entity : entityList) 
        {
            list.add(new HorarioAtencionDetailDTO(entity));
        }
        return list;
    }
}
