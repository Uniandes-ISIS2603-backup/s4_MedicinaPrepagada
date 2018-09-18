/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.medicinaPrepagada.resources;

import co.edu.uniandes.csw.medicinaPrepagada.dtos.ExamenMedicoDTO;
import co.edu.uniandes.csw.medicinaPrepagada.dtos.ExamenMedicoDetailDTO;
import co.edu.uniandes.csw.medicinaPrepagada.ejb.ExamenMedicoLogic;
import co.edu.uniandes.csw.medicinaPrepagada.entities.ExamenMedicoEntity;
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
import javax.ws.rs.PathParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;

/**
 * Clase que implementa el recurso "examenesMedicos".
 *
 * @author ISIS2603
 * @version 1.0
 */
@Path("examenesMedicos")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class ExamenMedicoResource {
    
    private static final Logger LOGGER = Logger.getLogger(ExamenMedicoResource.class.getName());

    @Inject
    ExamenMedicoLogic examenMedicoLogic; // Variable para acceder a la lógica de la aplicación. Es una inyección de dependencias.

    /**
     * Crea un nuevo examen medico con la informacion que se recibe en el cuerpo de
     * la petición y se regresa un objeto identico con un id auto-generado por
     * la base de datos.
     *
     * @param examenMedico {@link ExamenMedicoDTO} - El ExamenMedico que se desea
     * guardar.
     * @return JSON {@link ExamenMedicoDTO} - El ExamenMedico guardado con el atributo
     * id autogenerado.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando ya existe el ExamenMedico.
     */
   @POST
    public ExamenMedicoDTO createExamenMedico(ExamenMedicoDTO examenMedico) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "ExamenMedicoResource createExamenMedico: input: {0}", examenMedico.toString());
        // Convierte el DTO (json) en un objeto Entity para ser manejado por la lógica.
        ExamenMedicoEntity examenMedicoEntity = examenMedico.toEntity();
        // Invoca la lógica para crear la examenMedico nueva
        ExamenMedicoEntity nuevoExamenMedicoEntity = examenMedicoLogic.createExamenMedico(examenMedicoEntity);
        // Como debe retornar un DTO (json) se invoca el constructor del DTO con argumento el entity nuevo
        ExamenMedicoDTO nuevoExamenMedicoDTO = new ExamenMedicoDTO(nuevoExamenMedicoEntity);
        LOGGER.log(Level.INFO, "ExamenMedicoResource createExamenMedico: output: {0}", nuevoExamenMedicoDTO.toString());
        return nuevoExamenMedicoDTO;
    }

    /**
     * Busca y devuelve todas las examenMedicoes que existen en la aplicacion.
     *
     * @return JSONArray {@link ExamenMedicoDetailDTO} - Las examenMedicoes
     * encontradas en la aplicación. Si no hay ninguna retorna una lista vacía.
     */
    @GET
    public List<ExamenMedicoDetailDTO> getExamenMedicos() {
        LOGGER.info("ExamenMedicoResource getExamenMedicos: input: void");
        List<ExamenMedicoDetailDTO> listaExamenesMedicos = listEntity2DetailDTO(examenMedicoLogic.getExamenMedicos());
        LOGGER.log(Level.INFO, "ExamenMedicoResource getExamenMedicos: output: {0}", listaExamenesMedicos.toString());
        return listaExamenesMedicos;
    }

    /**
     * Busca la examenMedico con el id asociado recibido en la URL y la devuelve.
     *
     * @param examenMedicosId Identificador de la examenMedico que se esta buscando.
     * Este debe ser una cadena de dígitos.
     * @return JSON {@link ExamenMedicoDetailDTO} - La examenMedico buscada
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la examenMedico.
     */
    @GET
    @Path("{examenMedicosId: \\d+}")
    public ExamenMedicoDetailDTO getExamenMedico(@PathParam("examenMedicosId") Long examenMedicosId) throws WebApplicationException {
        LOGGER.log(Level.INFO, "ExamenMedicoResource getExamenMedico: input: {0}", examenMedicosId);
        ExamenMedicoEntity examenMedicoEntity = examenMedicoLogic.getExamenMedico(examenMedicosId);
        if (examenMedicoEntity == null) {
            throw new WebApplicationException("El recurso /examenMedicos/" + examenMedicosId + " no existe.", 404);
        }
        ExamenMedicoDetailDTO detailDTO = new ExamenMedicoDetailDTO(examenMedicoEntity);
        LOGGER.log(Level.INFO, "ExamenMedicoResource getExamenMedico: output: {0}", detailDTO.toString());
        return detailDTO;
    }

    /**
     * Actualiza la examenMedico con el id recibido en la URL con la informacion
     * que se recibe en el cuerpo de la petición.
     *
     * @param examenMedicosId Identificador de la examenMedico que se desea
     * actualizar. Este debe ser una cadena de dígitos.
     * @param examenMedico {@link ExamenMedicoDetailDTO} La examenMedico que se desea
     * guardar.
     * @return JSON {@link ExamenMedicoDetailDTO} - La examenMedico guardada.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la examenMedico a
     * actualizar.
     */
    @PUT
    @Path("{examenMedicosId: \\d+}")
    public ExamenMedicoDetailDTO updateExamenMedico(@PathParam("examenMedicosId") Long examenMedicosId, ExamenMedicoDetailDTO examenMedico) throws WebApplicationException, BusinessLogicException {
        LOGGER.log(Level.INFO, "ExamenMedicoResource updateExamenMedico: input: id:{0} , examenMedico: {1}", new Object[]{examenMedicosId, examenMedico.toString()});
        examenMedico.setId(examenMedicosId);
        if (examenMedicoLogic.getExamenMedico(examenMedicosId) == null) {
            throw new WebApplicationException("El recurso /examenMedicos/" + examenMedicosId + " no existe.", 404);
        }
        ExamenMedicoDetailDTO detailDTO = new ExamenMedicoDetailDTO(examenMedicoLogic.updateExamenMedico(examenMedicosId, examenMedico.toEntity()));
        LOGGER.log(Level.INFO, "ExamenMedicoResource updateExamenMedico: output: {0}", detailDTO.toString());
        return detailDTO;

    }

    /**
     * Borra la examenMedico con el id asociado recibido en la URL.
     *
     * @param examenMedicosId Identificador de la examenMedico que se desea borrar.
     * Este debe ser una cadena de dígitos.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando no se puede eliminar la examenMedico.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la examenMedico.
     */
    @DELETE
    @Path("{examenMedicosId: \\d+}")
    public void deleteExamenMedico(@PathParam("examenMedicosId") Long examenMedicosId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "ExamenMedicoResource deleteExamenMedico: input: {0}", examenMedicosId);
        if (examenMedicoLogic.getExamenMedico(examenMedicosId) == null) {
            throw new WebApplicationException("El recurso /examenMedicos/" + examenMedicosId + " no existe.", 404);
        }
        examenMedicoLogic.deleteExamenMedico(examenMedicosId);
        LOGGER.info("ExamenMedicoResource deleteExamenMedico: output: void");
    }

    /**
     * Convierte una lista de entidades a DTO.
     *
     * Este método convierte una lista de objetos ExamenMedicoEntity a una lista de
     * objetos ExamenMedicoDetailDTO (json)
     *
     * @param entityList corresponde a la lista de examenMedicoes de tipo Entity
     * que vamos a convertir a DTO.
     * @return la lista de examenMedicoes en forma DTO (json)
     */
    private List<ExamenMedicoDetailDTO> listEntity2DetailDTO(List<ExamenMedicoEntity> entityList) {
        List<ExamenMedicoDetailDTO> list = new ArrayList<>();
        for (ExamenMedicoEntity entity : entityList) {
            list.add(new ExamenMedicoDetailDTO(entity));
        }
        return list;
    }
}
