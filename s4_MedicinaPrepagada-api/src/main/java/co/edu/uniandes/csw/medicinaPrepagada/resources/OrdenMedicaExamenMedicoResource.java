/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.medicinaPrepagada.resources;

import co.edu.uniandes.csw.medicinaPrepagada.dtos.ExamenMedicoDetailDTO;
import co.edu.uniandes.csw.medicinaPrepagada.ejb.ExamenMedicoLogic;
import co.edu.uniandes.csw.medicinaPrepagada.ejb.OrdenMedicaExamenMedicoLogic;
import co.edu.uniandes.csw.medicinaPrepagada.entities.ExamenMedicoEntity;
import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.WebApplicationException;

/**
 * Clase que implementa el recurso "ordenesMedicas/{id}/examenMedico".
 *
 * @author ncobos
 */
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class OrdenMedicaExamenMedicoResource {
    private static final Logger LOGGER = Logger.getLogger(OrdenMedicaExamenMedicoResource.class.getName());

    @Inject
    private OrdenMedicaExamenMedicoLogic ordenMedicaExamenMedicoLogic;

    @Inject
    private ExamenMedicoLogic examenMedicoLogic;

    /**
     * Asocia una examenMedico existente con un ordenMedica existente
     *
     * @param examenMedicosId El ID de la examenMedico que se va a asociar
     * @param ordenMedicasId El ID del ordenMedica al cual se le va a asociar la examenMedico
     * @return JSON {@link ExamenMedicoDetailDTO} - El autor asociado.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la examenMedico.
     */
    @POST
    @Path("{examenMedicosId: \\d+}")
    public ExamenMedicoDetailDTO addExamenMedico(@PathParam("ordenMedicasId") Long ordenMedicasId, @PathParam("examenMedicosId") Long examenMedicosId) {
        LOGGER.log(Level.INFO, "OrdenMedicaExamenMedicosResource addExamenMedico: input: ordenMedicasId {0} , examenMedicosId {1}", new Object[]{ordenMedicasId, examenMedicosId});
        if (examenMedicoLogic.getExamenMedico(examenMedicosId) == null) {
            throw new WebApplicationException("El recurso /examenMedicos/" + examenMedicosId + " no existe.", 404);
        }
        ExamenMedicoDetailDTO detailDTO = new ExamenMedicoDetailDTO(ordenMedicaExamenMedicoLogic.addExamenMedico(ordenMedicasId, examenMedicosId));
        LOGGER.log(Level.INFO, "OrdenMedicaExamenMedicosResource addExamenMedico: output: {0}", detailDTO.toString());
        return detailDTO;
    }

    /**
     * Busca y devuelve todos las examenMedicos que existen en un ordenMedica.
     *
     * @param ordenMedicasId El ID del ordenMedica del cual se buscan las examenMedicos
     * @return JSONArray {@link ExamenMedicoDetailDTO} - Los autores encontrados en el
     * ordenMedica. Si no hay ninguno retorna una lista vacía.
     */
    @GET
    public List<ExamenMedicoDetailDTO> getExamenMedicos(@PathParam("ordenMedicasId") Long ordenMedicasId) {
        LOGGER.log(Level.INFO, "OrdenMedicaExamenMedicosResource getExamenMedicos: input: {0}", ordenMedicasId);
        List<ExamenMedicoDetailDTO> lista = examenMedicosListEntity2DTO(ordenMedicaExamenMedicoLogic.getExamenesMedicos(ordenMedicasId));
        LOGGER.log(Level.INFO, "OrdenMedicaExamenMedicosResource getExamenMedicos: output: {0}", lista.toString());
        return lista;
    }

    /**
     * Busca y devuelve la examenMedico con el ID recibido en la URL, relativo a un
     * ordenMedica.
     *
     * @param examenMedicosId El ID de la examenMedico que se busca
     * @param ordenMedicasId El ID del ordenMedica del cual se busca la examenMedico
     * @return {@link ExamenMedicoDetailDTO} - El autor encontrado en el ordenMedica.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper}
     * Error de lógica que se genera cuando no se encuentra la examenMedico.
     */
    @GET
    @Path("{examenMedicosId: \\d+}")
    public ExamenMedicoDetailDTO getExamenMedico(@PathParam("ordenMedicasId") Long ordenMedicasId, @PathParam("examenMedicosId") Long examenMedicosId) {
        LOGGER.log(Level.INFO, "OrdenMedicaExamenMedicosResource getExamenMedico: input: ordenMedicasId {0} , examenMedicosId {1}", new Object[]{ordenMedicasId, examenMedicosId});
        if (examenMedicoLogic.getExamenMedico(examenMedicosId) == null) {
            throw new WebApplicationException("El recurso /examenMedicos/" + examenMedicosId + " no existe.", 404);
        }
        ExamenMedicoDetailDTO detailDTO = new ExamenMedicoDetailDTO(ordenMedicaExamenMedicoLogic.getExamenMedico(ordenMedicasId, examenMedicosId));
        LOGGER.log(Level.INFO, "OrdenMedicaExamenMedicosResource getExamenMedico: output: {0}", detailDTO.toString());
        return detailDTO;
    }

    /**
     * Actualiza la lista de autores de un ordenMedica con la lista que se recibe en
     * el cuerpo.
     *
     * @param ordenMedicasId El ID del ordenMedica al cual se le va a asociar la lista de
     * autores
     * @param examenMedicos JSONArray {@link ExamenMedicoDetailDTO} - La lista de autores
     * que se desea guardar.
     * @return JSONArray {@link ExamenMedicoDetailDTO} - La lista actualizada.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper}
     * Error de lógica que se genera cuando no se encuentra la examenMedico.
     */
    @PUT
    public List<ExamenMedicoDetailDTO> replaceExamenMedicos(@PathParam("ordenMedicasId") Long ordenMedicasId, List<ExamenMedicoDetailDTO> examenMedicos) {
        LOGGER.log(Level.INFO, "OrdenMedicaExamenMedicosResource replaceExamenMedicos: input: ordenMedicasId {0} , examenMedicos {1}", new Object[]{ordenMedicasId, examenMedicos.toString()});
        for (ExamenMedicoDetailDTO examenMedico : examenMedicos) {
            if (examenMedicoLogic.getExamenMedico(examenMedico.getId()) == null) {
                throw new WebApplicationException("El recurso /examenMedicos/" + examenMedico.getId() + " no existe.", 404);
            }
        }
        List<ExamenMedicoDetailDTO> lista = examenMedicosListEntity2DTO(ordenMedicaExamenMedicoLogic.replaceExamenMedicos(ordenMedicasId, examenMedicosListDTO2Entity(examenMedicos)));
        LOGGER.log(Level.INFO, "OrdenMedicaExamenMedicosResource replaceExamenMedicos: output:{0}", lista.toString());
        return lista;
    }

    /**
     * Elimina la conexión entre la examenMedico y el ordenMedica recibidos en la URL.
     *
     * @param ordenMedicasId El ID del ordenMedica al cual se le va a desasociar la examenMedico
     * @param examenMedicosId El ID de la examenMedico que se desasocia
     * @throws WebApplicationException {@link WebApplicationExceptionMapper}
     * Error de lógica que se genera cuando no se encuentra la examenMedico.
     */
    @DELETE
    @Path("{examenMedicosId: \\d+}")
    public void removeExamenMedico(@PathParam("ordenMedicasId") Long ordenMedicasId, @PathParam("examenMedicosId") Long examenMedicosId) {
        LOGGER.log(Level.INFO, "OrdenMedicaExamenMedicosResource removeExamenMedico: input: ordenMedicasId {0} , examenMedicosId {1}", new Object[]{ordenMedicasId, examenMedicosId});
        if (examenMedicoLogic.getExamenMedico(examenMedicosId) == null) {
            throw new WebApplicationException("El recurso /examenMedicos/" + examenMedicosId + " no existe.", 404);
        }
        ordenMedicaExamenMedicoLogic.removeExamenMedico(ordenMedicasId, examenMedicosId);
        LOGGER.info("OrdenMedicaExamenMedicosResource removeExamenMedico: output: void");
    }

    /**
     * Convierte una lista de ExamenMedicoEntity a una lista de ExamenMedicoDetailDTO.
     *
     * @param entityList Lista de ExamenMedicoEntity a convertir.
     * @return Lista de ExamenMedicoDetailDTO convertida.
     */
    private List<ExamenMedicoDetailDTO> examenMedicosListEntity2DTO(List<ExamenMedicoEntity> entityList) {
        List<ExamenMedicoDetailDTO> list = new ArrayList<>();
        for (ExamenMedicoEntity entity : entityList) {
            list.add(new ExamenMedicoDetailDTO(entity));
        }
        return list;
    }

    /**
     * Convierte una lista de ExamenMedicoDetailDTO a una lista de ExamenMedicoEntity.
     *
     * @param dtos Lista de ExamenMedicoDetailDTO a convertir.
     * @return Lista de ExamenMedicoEntity convertida.
     */
    private List<ExamenMedicoEntity> examenMedicosListDTO2Entity(List<ExamenMedicoDetailDTO> dtos) {
        List<ExamenMedicoEntity> list = new ArrayList<>();
        for (ExamenMedicoDetailDTO dto : dtos) {
            list.add(dto.toEntity());
        }
        return list;
    }
}
