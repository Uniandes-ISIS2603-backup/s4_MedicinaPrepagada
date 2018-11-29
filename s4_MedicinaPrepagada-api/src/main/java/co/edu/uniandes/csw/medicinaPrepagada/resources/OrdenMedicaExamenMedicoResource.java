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
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.WebApplicationException;

/**
 * Clase que implementa el recurso "ordenesMedicas/{id}/examenMedico".
 *
 * @author ncobos
 */
@Path("/ordenesMedicas")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
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
    @Path("{ordenMedicasId: \\d+}/examenesMedicos/{examenMedicosId: \\d+}")
    public ExamenMedicoDetailDTO addExamenMedico(@PathParam("ordenMedicasId") Long ordenMedicasId, @PathParam("examenMedicosId") Long examenMedicosId) {
        LOGGER.log(Level.INFO, "OrdenMedicaExamenMedicosResource addExamenMedico: input: ordenMedicasId {0} , examenMedicosId {1}", new Object[]{ordenMedicasId, examenMedicosId});
        if (examenMedicoLogic.getExamenMedico(examenMedicosId) == null) {
            throw new WebApplicationException("El recurso /examenesMedicos/" + examenMedicosId + " no existe.", 404);
        }
        ExamenMedicoDetailDTO detailDTO = new ExamenMedicoDetailDTO(ordenMedicaExamenMedicoLogic.addExamenMedico(ordenMedicasId, examenMedicosId));
        LOGGER.log(Level.INFO, "OrdenMedicaExamenMedicosResource addExamenMedico: output: {0}", detailDTO);
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
    @Path("{ordenMedicasId: \\d+}/examenesMedicos")
    public List<ExamenMedicoDetailDTO> getExamenMedicos(@PathParam("ordenMedicasId") Long ordenMedicasId) {
        LOGGER.log(Level.INFO, "OrdenMedicaExamenMedicosResource getExamenMedicos: input: {0}", ordenMedicasId);
        List<ExamenMedicoDetailDTO> lista = examenMedicosListEntity2DTO(ordenMedicaExamenMedicoLogic.getExamenesMedicos(ordenMedicasId));
        LOGGER.log(Level.INFO, "OrdenMedicaExamenMedicosResource getExamenMedicos: output: {0}", lista);
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
    @Path("{ordenMedicasId: \\d+}/examenesMedicos/{examenMedicosId: \\d+}")
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
