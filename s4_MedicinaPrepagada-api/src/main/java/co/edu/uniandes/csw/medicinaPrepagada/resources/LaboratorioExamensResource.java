/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.medicinaPrepagada.resources;

import co.edu.uniandes.csw.medicinaPrepagada.dtos.ExamenMedicoDetailDTO;
import co.edu.uniandes.csw.medicinaPrepagada.ejb.LaboratorioExamenLogic;
import co.edu.uniandes.csw.medicinaPrepagada.ejb.ExamenMedicoLogic;
import co.edu.uniandes.csw.medicinaPrepagada.entities.ExamenMedicoEntity;
import co.edu.uniandes.csw.medicinaPrepagada.exceptions.BusinessLogicException;
import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.WebApplicationException;

/**
 * Clase que implementa el recurso "laboratorios/{id}/examens".
 *
 * @laboratorio ISIS2603
 * @version 1.0
 */
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("/laboratorios")
public class LaboratorioExamensResource {

    private static final Logger LOGGER = Logger.getLogger(LaboratorioExamensResource.class.getName());

    @Inject
    private LaboratorioExamenLogic laboratorioExamenLogic;

    @Inject
    private ExamenMedicoLogic examenLogic; // Variable para acceder a la lógica de la aplicación. Es una inyección de dependencias.

    /**
     * Asocia un examen existente con una laboratorio existente
     *
     * @param laboratoriosId El ID de la laboratorio al cual se le va a asociar el examen
     * @param examensId El ID del examen que se asocia
     * @return JSON {@link ExamenMedicoDetailDTO} - El examen asociado.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el examen.
     */
    @POST
    @Path("{laboratoriosId: \\d+}/examenesMedicos/{examensId: \\d+}")
    public ExamenMedicoDetailDTO addExamen(@PathParam("laboratoriosId") Long laboratoriosId, @PathParam("examensId") Long examensId) {
        LOGGER.log(Level.INFO, "LaboratorioExamensResource addExamen: input: laboratoriosId {0} , examensId {1}", new Object[]{laboratoriosId, examensId});
        if (examenLogic.getExamenMedico(examensId) == null) {
            throw new WebApplicationException("El recurso /examens/" + examensId + " no existe.", 404);
        }
        ExamenMedicoDetailDTO detailDTO = new ExamenMedicoDetailDTO(laboratorioExamenLogic.addExamen(laboratoriosId, examensId));
        LOGGER.log(Level.INFO, "LaboratorioExamensResource addExamen: output: {0}", detailDTO);
        return detailDTO;
    }

    /**
     * Busca y devuelve todos los examens que existen en una laboratorio.
     *
     * @param laboratoriosId El ID de la laboratorio del cual se buscan los examens
     * @return JSONArray {@link ExamenMedicoDetailDTO} - Los examens encontrados en el
     * autor. Si no hay ninguno retorna una lista vacía.
     */
    @GET
        @Path("{laboratoriosId: \\d+}/examenesMedicos")

    public List<ExamenMedicoDetailDTO> getExamens(@PathParam("laboratoriosId") Long laboratoriosId) {
        LOGGER.log(Level.INFO, "LaboratorioExamensResource getExamens: input: {0}", laboratoriosId);
        List<ExamenMedicoDetailDTO> lista = examensListEntity2DTO(laboratorioExamenLogic.getExamens(laboratoriosId));
        LOGGER.log(Level.INFO, "LaboratorioExamensResource getExamens: output: {0}", lista.toString());
        return lista;
    }

    /**
     * Busca y devuelve el examen con el ID recibido en la URL, relativo a un
     * autor.
     *
     * @param laboratoriosId El ID de la laboratorio del cual se busca el examen
     * @param examensId El ID del examen que se busca
     * @return {@link ExamenMedicoDetailDTO} - El examen encontrado en la laboratorio.
     * @throws co.edu.uniandes.csw.examenstore.exceptions.BusinessLogicException
     * si el examen no está asociado al autor
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el examen.
     */
    @GET
    @Path("{laboratoriosId: \\d+}/examenesMedicos/{examensId: \\d+}")
    public ExamenMedicoDetailDTO getExamen(@PathParam("laboratoriosId") Long laboratoriosId, @PathParam("examensId") Long examensId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "LaboratorioExamensResource getExamen: input: laboratoriosId {0} , examensId {1}", new Object[]{laboratoriosId, examensId});
        if (examenLogic.getExamenMedico(examensId) == null) {
            throw new WebApplicationException("El recurso /examens/" + examensId + " no existe.", 404);
        }
        ExamenMedicoDetailDTO detailDTO = new ExamenMedicoDetailDTO(laboratorioExamenLogic.getExamen(laboratoriosId, examensId));
        LOGGER.log(Level.INFO, "LaboratorioExamensResource getExamen: output: {0}", detailDTO);
        return detailDTO;
    }
    
    /**
     * Elimina la conexión entre el examen y e autor recibidos en la URL.
     *
     * @param laboratoriosId El ID de la laboratorio al cual se le va a desasociar el examen
     * @param examensId El ID del examen que se desasocia
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el examen.
     */
    @DELETE
    @Path("{laboratoriosId: \\d+}/examenesMedicos/{examensId: \\d+}")
    public void removeExamen(@PathParam("laboratoriosId") Long laboratoriosId, @PathParam("examensId") Long examensId) {
        LOGGER.log(Level.INFO, "LaboratorioExamensResource deleteExamen: input: laboratoriosId {0} , examensId {1}", new Object[]{laboratoriosId, examensId});
        if (examenLogic.getExamenMedico(examensId) == null) {
            throw new WebApplicationException("El recurso /examens/" + examensId + " no existe.", 404);
        }
        laboratorioExamenLogic.removeExamen(laboratoriosId, examensId);
        LOGGER.info("LaboratorioExamensResource deleteExamen: output: void");
    }

    /**
     * Convierte una lista de ExamenEntity a una lista de ExamenMedicoDetailDTO.
     *
     * @param entityList Lista de ExamenEntity a convertir.
     * @return Lista de ExamenMedicoDetailDTO convertida.
     */
    private List<ExamenMedicoDetailDTO> examensListEntity2DTO(List<ExamenMedicoEntity> entityList) {
        List<ExamenMedicoDetailDTO> list = new ArrayList<>();
        for (ExamenMedicoEntity entity : entityList) {
            list.add(new ExamenMedicoDetailDTO(entity));
        }
        return list;
    }

    /**
     * Convierte una lista de ExamenMedicoDetailDTO a una lista de ExamenEntity.
     *
     * @param dtos Lista de ExamenMedicoDetailDTO a convertir.
     * @return Lista de ExamenEntity convertida.
     */
    private List<ExamenMedicoEntity> examensListDTO2Entity(List<ExamenMedicoDetailDTO> dtos) {
        List<ExamenMedicoEntity> list = new ArrayList<>();
        for (ExamenMedicoDetailDTO dto : dtos) {
            list.add(dto.toEntity());
        }
        return list;
    }
    
}
