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
     * Busca y devuelve todos los ordenMedicas que existen en una medicamento.
     *
     * @param medicamentosId El ID de la medicamento del cual se buscan los ordenMedicas
     * @return JSONArray {@link OrdenMedicaDetailDTO} - Los ordenMedicas encontrados en el
     * autor. Si no hay ninguno retorna una lista vacía.
     */
    @GET
    public List<OrdenMedicaDetailDTO> getOrdenMedicas(@PathParam("medicamentosId") Long medicamentosId) {
        LOGGER.log(Level.INFO, "MedicamentoOrdenMedicasResource getOrdenMedicas: input: {0}", medicamentosId);
        List<OrdenMedicaDetailDTO> lista = ordenMedicasListEntity2DTO(medicamentoOrdenMedicaLogic.getOrdenMedicas(medicamentosId));
        LOGGER.log(Level.INFO, "MedicamentoOrdenMedicasResource getOrdenMedicas: output: {0}", lista.toString());
        return lista;
    }

    /**
     * Busca y devuelve el ordenMedica con el ID recibido en la URL, relativo a un
     * autor.
     *
     * @param medicamentosId El ID de la medicamento del cual se busca el ordenMedica
     * @param ordenMedicasId El ID del ordenMedica que se busca
     * @return {@link OrdenMedicaDetailDTO} - El ordenMedica encontrado en la medicamento.
     * @throws co.edu.uniandes.csw.ordenMedicastore.exceptions.BusinessLogicException
     * si el ordenMedica no está asociado al autor
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el ordenMedica.
     */
    @GET
    @Path("{ordenMedicasId: \\d+}")
    public OrdenMedicaDetailDTO getOrdenMedica(@PathParam("medicamentosId") Long medicamentosId, @PathParam("ordenMedicasId") Long ordenMedicasId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "MedicamentoOrdenMedicasResource getOrdenMedica: input: medicamentosId {0} , ordenMedicasId {1}", new Object[]{medicamentosId, ordenMedicasId});
        if (ordenMedicaLogic.getOrdenMedica(ordenMedicasId) == null) {
            throw new WebApplicationException("El recurso /ordenMedicas/" + ordenMedicasId + " no existe.", 404);
        }
        OrdenMedicaDetailDTO detailDTO = new OrdenMedicaDetailDTO(medicamentoOrdenMedicaLogic.getOrdenMedica(medicamentosId, ordenMedicasId));
        LOGGER.log(Level.INFO, "MedicamentoOrdenMedicasResource getOrdenMedica: output: {0}", detailDTO);
        return detailDTO;
    }

    /**
     * Actualiza la lista de ordenMedicas de una medicamento con la lista que se recibe en el
     * cuerpo
     *
     * @param medicamentosId El ID de la medicamento al cual se le va a asociar el ordenMedica
     * @param ordenMedicas JSONArray {@link OrdenMedicaDetailDTO} - La lista de ordenMedicas que se
     * desea guardar.
     * @return JSONArray {@link OrdenMedicaDetailDTO} - La lista actualizada.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el ordenMedica.
     */
    @PUT
    public List<OrdenMedicaDetailDTO> replaceOrdenMedicas(@PathParam("medicamentosId") Long medicamentosId, List<OrdenMedicaDetailDTO> ordenMedicas) {
        LOGGER.log(Level.INFO, "MedicamentoOrdenMedicasResource replaceOrdenMedicas: input: medicamentosId {0} , ordenMedicas {1}", new Object[]{medicamentosId, ordenMedicas.toString()});
        for (OrdenMedicaDetailDTO ordenMedica : ordenMedicas) {
            if (ordenMedicaLogic.getOrdenMedica(ordenMedica.getId()) == null) {
                throw new WebApplicationException("El recurso /ordenMedicas/" + ordenMedica.getId() + " no existe.", 404);
            }
        }
        List<OrdenMedicaDetailDTO> lista = ordenMedicasListEntity2DTO(medicamentoOrdenMedicaLogic.replaceOrdenMedicas(medicamentosId, ordenMedicasListDTO2Entity(ordenMedicas)));
        LOGGER.log(Level.INFO, "MedicamentoOrdenMedicasResource replaceOrdenMedicas: output: {0}", lista.toString());
        return lista;
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
    @Path("{ordenMedicasId: \\d+}")
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
