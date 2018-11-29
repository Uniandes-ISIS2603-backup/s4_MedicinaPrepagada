/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.medicinaPrepagada.resources;

import co.edu.uniandes.csw.medicinaPrepagada.dtos.OrdenMedicaDetailDTO;
import co.edu.uniandes.csw.medicinaPrepagada.ejb.MedicamentoOrdenMedicaLogic;
import co.edu.uniandes.csw.medicinaPrepagada.ejb.OrdenMedicaLogic;
import co.edu.uniandes.csw.medicinaPrepagada.entities.OrdenMedicaEntity;
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
 * 
 *
 * @version 1.0
 *
 *
 * @author ncobos
 */

@Path("/medicamentos")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class MedicamentoOrdenesMedicasResource {
    
    private static final Logger LOGGER = Logger.getLogger(MedicamentoOrdenesMedicasResource.class.getName());

    @Inject
    private MedicamentoOrdenMedicaLogic medicamentoOrdenMedicaLogic;

    @Inject
    private OrdenMedicaLogic ordenMedicaLogic; // Variable para acceder a la lógica de la aplicación. Es una inyección de dependencias.

    /**
     * Asocia un ordenMedica existente con una medicamento existente
     *
     * @param medicamentosId El ID de la medicamento al cual se le va a asociar el ordenMedica
     * @param ordenMedicasId El ID del ordenMedica que se asocia
     * @return JSON {@link OrdenMedicaDetailDTO} - El ordenMedica asociado.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el ordenMedica.
     */
    @POST
    @Path("{medicamentosId: \\d+}/ordenesMedicas/{ordenMedicasId: \\d+}")
    public OrdenMedicaDetailDTO addOrdenMedica(@PathParam("medicamentosId") Long medicamentosId, @PathParam("ordenMedicasId") Long ordenMedicasId) {
        LOGGER.log(Level.INFO, "MedicamentoOrdenMedicasResource addOrdenMedica: input: medicamentosId {0} , ordenMedicasId {1}", new Object[]{medicamentosId, ordenMedicasId});
        if (ordenMedicaLogic.getOrdenMedica(ordenMedicasId) == null) {
            throw new WebApplicationException("El recurso /ordenMedicas/" + ordenMedicasId + " no existe.", 404);
        }
        OrdenMedicaDetailDTO detailDTO = new OrdenMedicaDetailDTO(medicamentoOrdenMedicaLogic.addOrdenMedica(medicamentosId, ordenMedicasId));
        LOGGER.log(Level.INFO, "MedicamentoOrdenMedicasResource addOrdenMedica: output: {0}", detailDTO);
        return detailDTO;
    }

    
    /**
     * Elimina la conexión entre el ordenMedica y e autor recibidos en la URL.
     *
     * @param medicamentosId El ID de la medicamento al cual se le va a desasociar el ordenMedica
     * @param ordenMedicasId El ID del ordenMedica que se desasocia
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el ordenMedica.
     */
    @DELETE
    @Path("{medicamentosId: \\d+}/ordenesMedicas/{ordenMedicasId: \\d+}")
    public void removeOrdenMedica(@PathParam("medicamentosId") Long medicamentosId, @PathParam("ordenMedicasId") Long ordenMedicasId) {
        LOGGER.log(Level.INFO, "MedicamentoOrdenMedicasResource deleteOrdenMedica: input: medicamentosId {0} , ordenMedicasId {1}", new Object[]{medicamentosId, ordenMedicasId});
        if (ordenMedicaLogic.getOrdenMedica(ordenMedicasId) == null) {
            throw new WebApplicationException("El recurso /ordenMedicas/" + ordenMedicasId + " no existe.", 404);
        }
        medicamentoOrdenMedicaLogic.removeOrdenMedica(medicamentosId, ordenMedicasId);
        LOGGER.info("MedicamentoOrdenMedicasResource deleteOrdenMedica: output: void");
    }

    /**
     * Convierte una lista de OrdenMedicaEntity a una lista de OrdenMedicaDetailDTO.
     *
     * @param entityList Lista de OrdenMedicaEntity a convertir.
     * @return Lista de OrdenMedicaDetailDTO convertida.
     */
    private List<OrdenMedicaDetailDTO> ordenMedicasListEntity2DTO(List<OrdenMedicaEntity> entityList) {
        List<OrdenMedicaDetailDTO> list = new ArrayList<>();
        for (OrdenMedicaEntity entity : entityList) {
            list.add(new OrdenMedicaDetailDTO(entity));
        }
        return list;
    }

    /**
     * Convierte una lista de OrdenMedicaDetailDTO a una lista de OrdenMedicaEntity.
     *
     * @param dtos Lista de OrdenMedicaDetailDTO a convertir.
     * @return Lista de OrdenMedicaEntity convertida.
     */
    private List<OrdenMedicaEntity> ordenMedicasListDTO2Entity(List<OrdenMedicaDetailDTO> dtos) {
        List<OrdenMedicaEntity> list = new ArrayList<>();
        for (OrdenMedicaDetailDTO dto : dtos) {
            list.add(dto.toEntity());
        }
        return list;
    }
    
    
}
