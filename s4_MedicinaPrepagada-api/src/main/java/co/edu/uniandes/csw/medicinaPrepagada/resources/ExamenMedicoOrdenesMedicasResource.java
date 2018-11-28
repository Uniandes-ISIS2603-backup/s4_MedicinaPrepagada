/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.medicinaPrepagada.resources;

import co.edu.uniandes.csw.medicinaPrepagada.dtos.OrdenMedicaDetailDTO;
import co.edu.uniandes.csw.medicinaPrepagada.ejb.ExamenMedicoOrdenMedicaLogic;
import co.edu.uniandes.csw.medicinaPrepagada.ejb.OrdenMedicaLogic;
import co.edu.uniandes.csw.medicinaPrepagada.entities.OrdenMedicaEntity;
import co.edu.uniandes.csw.medicinaPrepagada.exceptions.BusinessLogicException;
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
 * 
 *
 * @version 1.0
 *
 *
 * @author ncobos
 */
@Path("examenesMedicos")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class ExamenMedicoOrdenesMedicasResource {
    
    
    private static final Logger LOGGER = Logger.getLogger(ExamenMedicoOrdenesMedicasResource.class.getName());

    @Inject
    private ExamenMedicoOrdenMedicaLogic examenMedicoOrdenMedicaLogic;

    @Inject
    private OrdenMedicaLogic ordenMedicaLogic; // Variable para acceder a la lógica de la aplicación. Es una inyección de dependencias.

    /**
     * Asocia un ordenMedica existente con una examenMedico existente
     *
     * @param examenMedicosId El ID de la examenMedico al cual se le va a asociar el ordenMedica
     * @param ordenMedicasId El ID del ordenMedica que se asocia
     * @return JSON {@link OrdenMedicaDetailDTO} - El ordenMedica asociado.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el ordenMedica.
     */
    @POST
    @Path("{ordenMedicasId: \\d+}")
    public OrdenMedicaDetailDTO addOrdenMedica(@PathParam("examenMedicosId") Long examenMedicosId, @PathParam("ordenMedicasId") Long ordenMedicasId) {
        LOGGER.log(Level.INFO, "ExamenMedicoOrdenMedicasResource addOrdenMedica: input: examenMedicosId {0} , ordenMedicasId {1}", new Object[]{examenMedicosId, ordenMedicasId});
        if (ordenMedicaLogic.getOrdenMedica(ordenMedicasId) == null) {
            throw new WebApplicationException("El recurso /ordenMedicas/" + ordenMedicasId + " no existe.", 404);
        }
        OrdenMedicaDetailDTO detailDTO = new OrdenMedicaDetailDTO(examenMedicoOrdenMedicaLogic.addOrdenMedica(examenMedicosId, ordenMedicasId));
        LOGGER.log(Level.INFO, "ExamenMedicoOrdenMedicasResource addOrdenMedica: output: {0}", detailDTO);
        return detailDTO;
    }

    /**
     * Elimina la conexión entre el ordenMedica y e autor recibidos en la URL.
     *
     * @param examenMedicosId El ID de la examenMedico al cual se le va a desasociar el ordenMedica
     * @param ordenMedicasId El ID del ordenMedica que se desasocia
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el ordenMedica.
     */
    @DELETE
    @Path("{examenMedicosId: \\d+}/ordenesMedicas/{ordenMedicasId: \\d+}")
    public void removeOrdenMedica(@PathParam("examenMedicosId") Long examenMedicosId, @PathParam("ordenMedicasId") Long ordenMedicasId) {
        LOGGER.log(Level.INFO, "ExamenMedicoOrdenMedicasResource deleteOrdenMedica: input: examenMedicosId {0} , ordenMedicasId {1}", new Object[]{examenMedicosId, ordenMedicasId});
        if (ordenMedicaLogic.getOrdenMedica(ordenMedicasId) == null) {
            throw new WebApplicationException("El recurso /ordenMedicas/" + ordenMedicasId + " no existe.", 404);
        }
        examenMedicoOrdenMedicaLogic.removeOrdenMedica(examenMedicosId, ordenMedicasId);
        LOGGER.info("ExamenMedicoOrdenMedicasResource deleteOrdenMedica: output: void");
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
    
    
