/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.medicinaPrepagada.resources;

import co.edu.uniandes.csw.medicinaPrepagada.dtos.LaboratorioDetailDTO;
import co.edu.uniandes.csw.medicinaPrepagada.ejb.LaboratorioLogic;
import co.edu.uniandes.csw.medicinaPrepagada.ejb.ExamenLaboratorioLogic;
import co.edu.uniandes.csw.medicinaPrepagada.entities.LaboratorioEntity;
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
 * Clase que implementa el recurso "examens/{id}/laboratorios".
 *
 * @author ncobos
 */
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("/examenesMedicos")
public class ExamenLaboratoriosResource {

    private static final Logger LOGGER = Logger.getLogger(ExamenLaboratoriosResource.class.getName());

    @Inject
    private ExamenLaboratorioLogic examenLaboratorioLogic;

    @Inject
    private LaboratorioLogic laboratorioLogic;

    /**
     * Asocia una laboratorio existente con un examen existente
     *
     * @param laboratoriosId El ID de la laboratorio que se va a asociar
     * @param examensId El ID del examen al cual se le va a asociar la laboratorio
     * @return JSON {@link LaboratorioDetailDTO} - El autor asociado.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la laboratorio.
     */
    @POST
    @Path("{examensId: \\d+}/laboratorios/{laboratoriosId: \\d+}")
    public LaboratorioDetailDTO addLaboratorio(@PathParam("examensId") Long examensId, @PathParam("laboratoriosId") Long laboratoriosId) {
        LOGGER.log(Level.INFO, "ExamenLaboratoriosResource addLaboratorio: input: examensId {0} , laboratoriosId {1}", new Object[]{examensId, laboratoriosId});
        if (laboratorioLogic.getLab(laboratoriosId) == null) {
            throw new WebApplicationException("El recurso /laboratorios/" + laboratoriosId + " no existe.", 404);
        }
        LaboratorioDetailDTO detailDTO = new LaboratorioDetailDTO(examenLaboratorioLogic.addLaboratorio(examensId, laboratoriosId));
        LOGGER.log(Level.INFO, "ExamenLaboratoriosResource addLaboratorio: output: {0}", detailDTO);
        return detailDTO;
    }

    /**
     * Busca y devuelve todos las laboratorios que existen en un examen.
     *
     * @param examensId El ID del examen del cual se buscan las laboratorios
     * @return JSONArray {@link LaboratorioDetailDTO} - Los autores encontrados en el
     * examen. Si no hay ninguno retorna una lista vacía.
     */
    @GET
    @Path("{examensId: \\d+}/laboratorios")
    public List<LaboratorioDetailDTO> getLaboratorios(@PathParam("examensId") Long examensId) {
        LOGGER.log(Level.INFO, "ExamenLaboratoriosResource getLaboratorios: input: {0}", examensId);
        List<LaboratorioDetailDTO> lista;
        try{
            lista= laboratoriosListEntity2DTO(examenLaboratorioLogic.getLaboratorios(examensId));
        }
        catch(Exception e){
            throw new WebApplicationException(e.getMessage() + "404");
        }
        LOGGER.log(Level.INFO, "ExamenLaboratoriosResource getLaboratorios: output: {0}", lista);
        return lista;
    }

    /**
     * Busca y devuelve la laboratorio con el ID recibido en la URL, relativo a un
     * examen.
     *
     * @param laboratoriosId El ID de la laboratorio que se busca
     * @param examensId El ID del examen del cual se busca la laboratorio
     * @return {@link LaboratorioDetailDTO} - El autor encontrado en el examen.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper}
     * Error de lógica que se genera cuando no se encuentra la laboratorio.
     */
    @GET
    @Path("{examensId: \\d+}/laboratorios/{laboratoriosId: \\d+}")
    public LaboratorioDetailDTO getLaboratorio(@PathParam("examensId") Long examensId, @PathParam("laboratoriosId") Long laboratoriosId) {
        LOGGER.log(Level.INFO, "ExamenLaboratoriosResource getLaboratorio: input: examensId {0} , laboratoriosId {1}", new Object[]{examensId, laboratoriosId});
        if (laboratorioLogic.getLab(laboratoriosId) == null) {
            throw new WebApplicationException("El recurso /laboratorios/" + laboratoriosId + " no existe.", 404);
        }
        LaboratorioDetailDTO detailDTO = new LaboratorioDetailDTO(examenLaboratorioLogic.getLaboratorio(examensId, laboratoriosId));
        LOGGER.log(Level.INFO, "ExamenLaboratoriosResource getLaboratorio: output: {0}", detailDTO);
        return detailDTO;
    }

   

    /**
     * Elimina la conexión entre la laboratorio y el examen recibidos en la URL.
     *
     * @param examensId El ID del examen al cual se le va a desasociar la laboratorio
     * @param laboratoriosId El ID de la laboratorio que se desasocia
     * @throws WebApplicationException {@link WebApplicationExceptionMapper}
     * Error de lógica que se genera cuando no se encuentra la laboratorio.
     */
    @DELETE
    @Path("{examensId: \\d+}/laboratorios/{laboratoriosId: \\d+}")
    public void removeLaboratorio(@PathParam("examensId") Long examensId, @PathParam("laboratoriosId") Long laboratoriosId) {
        LOGGER.log(Level.INFO, "ExamenLaboratoriosResource removeLaboratorio: input: examensId {0} , laboratoriosId {1}", new Object[]{examensId, laboratoriosId});
        if (laboratorioLogic.getLab(laboratoriosId) == null) {
            throw new WebApplicationException("El recurso /laboratorios/" + laboratoriosId + " no existe.", 404);
        }
        examenLaboratorioLogic.removeLaboratorio(examensId, laboratoriosId);
        LOGGER.info("ExamenLaboratoriosResource removeLaboratorio: output: void");
    }

    /**
     * Convierte una lista de LaboratorioEntity a una lista de LaboratorioDetailDTO.
     *
     * @param entityList Lista de LaboratorioEntity a convertir.
     * @return Lista de LaboratorioDetailDTO convertida.   
     */
    private List<LaboratorioDetailDTO> laboratoriosListEntity2DTO(List<LaboratorioEntity> entityList) {
        List<LaboratorioDetailDTO> list = new ArrayList<>();
        for (LaboratorioEntity entity : entityList) {
            list.add(new LaboratorioDetailDTO(entity));
        }
        return list;
    }

    /**
     * Convierte una lista de LaboratorioDetailDTO a una lista de LaboratorioEntity.
     *
     * @param dtos Lista de LaboratorioDetailDTO a convertir.
     * @return Lista de LaboratorioEntity convertida.
     */
    private List<LaboratorioEntity> laboratoriosListDTO2Entity(List<LaboratorioDetailDTO> dtos) {
        List<LaboratorioEntity> list = new ArrayList<>();
        for (LaboratorioDetailDTO dto : dtos) {
            list.add(dto.toEntity());
        }
        return list;
    }
}
