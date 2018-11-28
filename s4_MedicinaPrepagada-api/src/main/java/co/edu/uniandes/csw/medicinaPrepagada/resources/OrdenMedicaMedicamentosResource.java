/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.medicinaPrepagada.resources;

import co.edu.uniandes.csw.medicinaPrepagada.dtos.MedicamentoDetailDTO;
import co.edu.uniandes.csw.medicinaPrepagada.ejb.MedicamentoLogic;
import co.edu.uniandes.csw.medicinaPrepagada.ejb.OrdenMedicaMedicamentoLogic;
import co.edu.uniandes.csw.medicinaPrepagada.entities.MedicamentoEntity;
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
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.WebApplicationException;

/**
 * Clase que implementa el recurso "ordenesMedicas/{id}/medicamento".
 *
 * @author ncobos
 */
@Path("/ordenesMedicas")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@RequestScoped
public class OrdenMedicaMedicamentosResource {
    
 private static final Logger LOGGER = Logger.getLogger(OrdenMedicaMedicamentosResource.class.getName());

    @Inject
    private OrdenMedicaMedicamentoLogic ordenMedicaMedicamentoLogic;

    @Inject
    private MedicamentoLogic medicamentoLogic;

    /**
     * Asocia una medicamento existente con un ordenMedica existente
     *
     * @param medicamentosId El ID de la medicamento que se va a asociar
     * @param ordenMedicasId El ID del ordenMedica al cual se le va a asociar la medicamento
     * @return JSON {@link MedicamentoDetailDTO} - El autor asociado.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la medicamento.
     */
    @POST
    @Path("{ordenMedicasId: \\d+}/medicamentos/{medicamentosId: \\d+}")
    public MedicamentoDetailDTO addMedicamento(@PathParam("ordenMedicasId") Long ordenMedicasId, @PathParam("medicamentosId") Long medicamentosId) {
        LOGGER.log(Level.INFO, "OrdenMedicaMedicamentosResource addMedicamento: input: ordenMedicasId {0} , medicamentosId {1}", new Object[]{ordenMedicasId, medicamentosId});
        if (medicamentoLogic.getMedicamento(medicamentosId) == null) {
            throw new WebApplicationException("El recurso /medicamentos/" + medicamentosId + " no existe.", 404);
        }
        MedicamentoDetailDTO detailDTO = new MedicamentoDetailDTO(ordenMedicaMedicamentoLogic.addMedicamento(ordenMedicasId, medicamentosId));
        LOGGER.log(Level.INFO, "OrdenMedicaMedicamentosResource addMedicamento: output: {0}", detailDTO.toString());
        return detailDTO;
    }

    /**
     * Busca y devuelve todos las medicamentos que existen en un ordenMedica.
     *
     * @param ordenMedicasId El ID del ordenMedica del cual se buscan las medicamentos
     * @return JSONArray {@link MedicamentoDetailDTO} - Los autores encontrados en el
     * ordenMedica. Si no hay ninguno retorna una lista vacía.
     */
    @GET
    @Path("{ordenMedicasId: \\d+}/medicamentos")
    public List<MedicamentoDetailDTO> getMedicamentos(@PathParam("ordenMedicasId") Long ordenMedicasId) {
        LOGGER.log(Level.INFO, "OrdenMedicaMedicamentosResource getMedicamentos: input: {0}", ordenMedicasId);
        List<MedicamentoDetailDTO> lista = medicamentosListEntity2DTO(ordenMedicaMedicamentoLogic.getMedicamentos(ordenMedicasId));
        LOGGER.log(Level.INFO, "OrdenMedicaMedicamentosResource getMedicamentos: output: {0}", lista.toString());
        return lista;
    }

    /**
     * Elimina la conexión entre la medicamento y el ordenMedica recibidos en la URL.
     *
     * @param ordenMedicasId El ID del ordenMedica al cual se le va a desasociar la medicamento
     * @param medicamentosId El ID de la medicamento que se desasocia
     * @throws WebApplicationException {@link WebApplicationExceptionMapper}
     * Error de lógica que se genera cuando no se encuentra la medicamento.
     */
    @DELETE
    @Path("{ordenMedicasId: \\d+}/medicamentos/{medicamentosId: \\d+}")
    public void removeMedicamento(@PathParam("ordenMedicasId") Long ordenMedicasId, @PathParam("medicamentosId") Long medicamentosId) {
        LOGGER.log(Level.INFO, "OrdenMedicaMedicamentosResource removeMedicamento: input: ordenMedicasId {0} , medicamentosId {1}", new Object[]{ordenMedicasId, medicamentosId});
        if (medicamentoLogic.getMedicamento(medicamentosId) == null) {
            throw new WebApplicationException("El recurso /medicamentos/" + medicamentosId + " no existe.", 404);
        }
        ordenMedicaMedicamentoLogic.removeMedicamento(ordenMedicasId, medicamentosId);
        LOGGER.info("OrdenMedicaMedicamentosResource removeMedicamento: output: void");
    }

    /**
     * Convierte una lista de MedicamentoEntity a una lista de MedicamentoDetailDTO.
     *
     * @param entityList Lista de MedicamentoEntity a convertir.
     * @return Lista de MedicamentoDetailDTO convertida.
     */
    private List<MedicamentoDetailDTO> medicamentosListEntity2DTO(List<MedicamentoEntity> entityList) {
        List<MedicamentoDetailDTO> list = new ArrayList<>();
        for (MedicamentoEntity entity : entityList) {
            list.add(new MedicamentoDetailDTO(entity));
        }
        return list;
    }

    /**
     * Convierte una lista de MedicamentoDetailDTO a una lista de MedicamentoEntity.
     *
     * @param dtos Lista de MedicamentoDetailDTO a convertir.
     * @return Lista de MedicamentoEntity convertida.
     */
    private List<MedicamentoEntity> medicamentosListDTO2Entity(List<MedicamentoDetailDTO> dtos) {
        List<MedicamentoEntity> list = new ArrayList<>();
        for (MedicamentoDetailDTO dto : dtos) {
            list.add(dto.toEntity());
        }
        return list;
    }
}
