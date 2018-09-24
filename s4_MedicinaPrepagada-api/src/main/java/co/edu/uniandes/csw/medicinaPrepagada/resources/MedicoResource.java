/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.medicinaPrepagada.resources;
import co.edu.uniandes.csw.medicinaPrepagada.dtos.MedicoDTO;
import co.edu.uniandes.csw.medicinaPrepagada.dtos.MedicoDetailDTO;
import co.edu.uniandes.csw.medicinaPrepagada.dtos.SedeDTO;
import co.edu.uniandes.csw.medicinaPrepagada.dtos.SedeDetailDTO;
import co.edu.uniandes.csw.medicinaPrepagada.ejb.MedicoLogic;
import co.edu.uniandes.csw.medicinaPrepagada.entities.MedicoEntity;
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

@Path("medico")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class MedicoResource {
    private static final Logger LOGGER = Logger.getLogger(MedicoResource.class.getName());

    @Inject
    private MedicoLogic medicoLogic;


    /**
     * Crea una nueva medico con la informacion que se recibe en el cuerpo de
     * la petición y se regresa un objeto identico con un id auto-generado por
     * la base de datos.
     *
     * @param medico {@link MedicoDTO} - La medico que se desea
     * guardar.
     * @return JSON {@link MedicoDTO} - La medico guardada con el atributo
     * id autogenerado.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando ya existe la medico.
     */
    @POST
    public MedicoDTO createMedico(MedicoDTO medico) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "MedicoResource createMedico: input: {0}", medico.toString());
        MedicoDTO nuevoMedicoDTO = new MedicoDTO(medicoLogic.createMedico(medico.toEntity()));
        LOGGER.log(Level.INFO, "MedicoResource createMedico: output: {0}", nuevoMedicoDTO.toString());
        return nuevoMedicoDTO;
    }
    

    /**
     * Busca y devuelve todas las editoriales que existen en la aplicacion.
     *
     * @return JSONArray {@link MedicoDTO} - Las editoriales encontradas en
     * la aplicación. Si no hay ninguna retorna una lista vacía.
     */
    @GET
    public List<MedicoDTO> getMedicos() {
        LOGGER.info("MedicoResource getMedicos: input: void");
        List<MedicoDTO> listaMedicos = listEntity2DetailDTO(medicoLogic.getMedicos()); //Paramtero List<MedicoEntity> entityList
        LOGGER.log(Level.INFO, "MedicoResource getMedicos: output: {0}", listaMedicos.toString());
        return listaMedicos;
    }

    /**
     * Busca la medico con el id asociado recibido en la URL y la devuelve.
     *
     * @param medicosId Identificador de la medico que se esta buscando.
     * Este debe ser una cadena de dígitos.
     * @return JSON {@link MedicoDTO} - La medico buscada
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la medico.
     */
    @GET
    @Path("{medicosId: \\d+}")
    public MedicoDTO getMedico(@PathParam("medicosId") Long medicosId) throws WebApplicationException {
        LOGGER.log(Level.INFO, "MedicoResource getMedico: input: {0}", medicosId);
        MedicoEntity medicoEntity = medicoLogic.getMedico(medicosId);
        if (medicoEntity == null) {
            throw new WebApplicationException("El recurso /medicos/" + medicosId + " no existe.", 404);
        }
        MedicoDTO detailDTO = new MedicoDetailDTO(medicoEntity);
        LOGGER.log(Level.INFO, "MedicoResource getMedico: output: {0}", detailDTO.toString());
        return detailDTO;
    }

    /**
     * Actualiza la medico con el id recibido en la URL con la informacion
     * que se recibe en el cuerpo de la petición.
     *
     * @param medicosId Identificador de la medico que se desea
     * actualizar. Este debe ser una cadena de dígitos.
     * @param medico {@link MedicoDTO} La medico que se desea guardar.
     * @return JSON {@link MedicoDTO} - La medico guardada.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la medico a
     * actualizar.
     */
    @PUT
    @Path("{medicosId: \\d+}")
    public MedicoDTO updateMedico(@PathParam("medicosId") Long medicosId, MedicoDTO medico) throws WebApplicationException, BusinessLogicException {
        LOGGER.log(Level.INFO, "MedicoResource updateMedico: input: id:{0} , medico: {1}", new Object[]{medicosId, medico.toString()});
        medico.setIdMedico(medicosId);
        if (medicoLogic.getMedico(medicosId) == null) {
            throw new WebApplicationException("El recurso /editorials/" + medicosId + " no existe.", 404);
        }
        MedicoDTO detailDTO = new MedicoDTO(medicoLogic.updateMedico(medicosId, medico.toEntity()));
        LOGGER.log(Level.INFO, "MedicoResource updateMedico: output: {0}", detailDTO.toString());
        return detailDTO;
    }

    /**
     * Borra la medico con el id asociado recibido en la URL.
     *
     * @param medicosId Identificador de la medico que se desea borrar.
     * Este debe ser una cadena de dígitos.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando no se puede eliminar la medico.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la medico.
     */
    @DELETE
    @Path("{medicosId: \\d+}")
    public void deleteMedico(@PathParam("medicosId") Long medicosId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "MedicoResource deleteMedico: input: {0}", medicosId);
        if (medicoLogic.getMedico(medicosId) == null) {
            throw new WebApplicationException("El recurso /medico/" + medicosId + " no existe.", 404);
        }
        medicoLogic.deleteMedico(medicosId);
        LOGGER.info("MedicoResource deleteMedico: output: void");
    }

    /**
     * Convierte una lista de entidades a DTO.
     *
     * Este método convierte una lista de objetos MedicoEntity a una lista de
     * objetos MedicoDTO (json)
     *
     * @param entityList corresponde a la lista de editoriales de tipo Entity
     * que vamos a convertir a DTO.
     * @return la lista de editoriales en forma DTO (json)
     */
    private List<MedicoDTO> listEntity2DetailDTO(List<MedicoEntity> entityList){
        List<MedicoDTO> list = new ArrayList<>();
        for (MedicoEntity entity : entityList) {
            list.add(new MedicoDTO(entity));
        }
        return list;
    }
}
