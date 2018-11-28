package co.edu.uniandes.csw.medicinaPrepagada.resources;

import co.edu.uniandes.csw.medicinaPrepagada.dtos.MedicamentoDetailDTO;
import co.edu.uniandes.csw.medicinaPrepagada.ejb.FarmaciaMedicamentoLogic;
import co.edu.uniandes.csw.medicinaPrepagada.ejb.MedicamentoLogic;
import co.edu.uniandes.csw.medicinaPrepagada.entities.MedicamentoEntity;
import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
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
 * Clase que implementa el recurso "farmacias/{id}/medicamentos".
 *
 * @farmacia ISIS2603
 * @version 1.0
 */

@Path("/farmacias")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class FarmaciaMedicamentosResource {

    private static final Logger LOGGER = Logger.getLogger(FarmaciaMedicamentosResource.class.getName());

    @Inject
    private FarmaciaMedicamentoLogic farmaciaMedicamentoLogic;

    @Inject
    private MedicamentoLogic medicamentoLogic; // Variable para acceder a la lógica de la aplicación. Es una inyección de dependencias.

    /**
     * Asocia un medicamento existente con una farmacia existente
     *
     * @param farmaciasId El ID de la farmacia al cual se le va a asociar el medicamento
     * @param medicamentosId El ID del medicamento que se asocia
     * @return JSON {@link MedicamentoDetailDTO} - El medicamento asociado.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el medicamento.
     */
    @POST
    @Path("{farmaciasId: \\d+}/medicamentos/{medicamentosId: \\d+}")
    public MedicamentoDetailDTO addMedicamento(@PathParam("farmaciasId") Long farmaciasId, @PathParam("medicamentosId") Long medicamentosId) {
        LOGGER.log(Level.INFO, "FarmaciaMedicamentosResource addMedicamento: input: farmaciasId {0} , medicamentosId {1}", new Object[]{farmaciasId, medicamentosId});
        if (medicamentoLogic.getMedicamento(medicamentosId) == null) {
            throw new WebApplicationException("El recurso /medicamentos/" + medicamentosId + " no existe.", 404);
        }
        MedicamentoDetailDTO detailDTO = new MedicamentoDetailDTO(farmaciaMedicamentoLogic.addMedicamento(farmaciasId, medicamentosId));
        LOGGER.log(Level.INFO, "FarmaciaMedicamentosResource addMedicamento: output: {0}", detailDTO);
        return detailDTO;
    }

    /**
     * Elimina la conexión entre el medicamento y e autor recibidos en la URL.
     *
     * @param farmaciasId El ID de la farmacia al cual se le va a desasociar el medicamento
     * @param medicamentosId El ID del medicamento que se desasocia
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el medicamento.
     */
    @DELETE
    @Path("{farmaciasId: \\d+}/medicamentos/{medicamentosId: \\d+}")
    public void removeMedicamento(@PathParam("farmaciasId") Long farmaciasId, @PathParam("medicamentosId") Long medicamentosId) {
        LOGGER.log(Level.INFO, "FarmaciaMedicamentosResource deleteMedicamento: input: farmaciasId {0} , medicamentosId {1}", new Object[]{farmaciasId, medicamentosId});
        if (medicamentoLogic.getMedicamento(medicamentosId) == null) {
            throw new WebApplicationException("El recurso /medicamentos/" + medicamentosId + " no existe.", 404);
        }
        farmaciaMedicamentoLogic.removeMedicamento(farmaciasId, medicamentosId);
        LOGGER.info("FarmaciaMedicamentosResource deleteMedicamento: output: void");
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
