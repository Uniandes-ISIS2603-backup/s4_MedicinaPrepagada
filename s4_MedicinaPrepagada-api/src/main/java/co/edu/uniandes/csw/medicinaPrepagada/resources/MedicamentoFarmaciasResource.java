/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.medicinaPrepagada.resources;

import co.edu.uniandes.csw.medicinaPrepagada.dtos.FarmaciaDetailDTO;
import co.edu.uniandes.csw.medicinaPrepagada.ejb.FarmaciaLogic;
import co.edu.uniandes.csw.medicinaPrepagada.ejb.MedicamentoFarmaciaLogic;
import co.edu.uniandes.csw.medicinaPrepagada.entities.FarmaciaEntity;
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
 * Clase que implementa el recurso "medicamentos/{id}/farmacias".
 *
 * @author ncobos
 */
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class MedicamentoFarmaciasResource {

    private static final Logger LOGGER = Logger.getLogger(MedicamentoFarmaciasResource.class.getName());

    @Inject
    private MedicamentoFarmaciaLogic medicamentoFarmaciaLogic;

    @Inject
    private FarmaciaLogic farmaciaLogic;

    /**
     * Asocia una farmacia existente con un medicamento existente
     *
     * @param farmaciasId El ID de la farmacia que se va a asociar
     * @param medicamentosId El ID del medicamento al cual se le va a asociar la farmacia
     * @return JSON {@link FarmaciaDetailDTO} - El autor asociado.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la farmacia.
     */
    @POST
    @Path("{farmaciasId: \\d+}")
    public FarmaciaDetailDTO addFarmacia(@PathParam("medicamentosId") Long medicamentosId, @PathParam("farmaciasId") Long farmaciasId) {
        LOGGER.log(Level.INFO, "MedicamentoFarmaciasResource addFarmacia: input: medicamentosId {0} , farmaciasId {1}", new Object[]{medicamentosId, farmaciasId});
        if (farmaciaLogic.getFarmacia(farmaciasId) == null) {
            throw new WebApplicationException("El recurso /farmacias/" + farmaciasId + " no existe.", 404);
        }
        FarmaciaDetailDTO detailDTO = new FarmaciaDetailDTO(medicamentoFarmaciaLogic.addFarmacia(medicamentosId, farmaciasId));
        LOGGER.log(Level.INFO, "MedicamentoFarmaciasResource addFarmacia: output: {0}", detailDTO.toString());
        return detailDTO;
    }

    /**
     * Busca y devuelve todos las farmacias que existen en un medicamento.
     *
     * @param medicamentosId El ID del medicamento del cual se buscan las farmacias
     * @return JSONArray {@link FarmaciaDetailDTO} - Los autores encontrados en el
     * medicamento. Si no hay ninguno retorna una lista vacía.
     */
    @GET
    public List<FarmaciaDetailDTO> getFarmacias(@PathParam("medicamentosId") Long medicamentosId) {
        LOGGER.log(Level.INFO, "MedicamentoFarmaciasResource getFarmacias: input: {0}", medicamentosId);
        List<FarmaciaDetailDTO> lista = farmaciasListEntity2DTO(medicamentoFarmaciaLogic.getFarmacias(medicamentosId));
        LOGGER.log(Level.INFO, "MedicamentoFarmaciasResource getFarmacias: output: {0}", lista.toString());
        return lista;
    }

    /**
     * Busca y devuelve la farmacia con el ID recibido en la URL, relativo a un
     * medicamento.
     *
     * @param farmaciasId El ID de la farmacia que se busca
     * @param medicamentosId El ID del medicamento del cual se busca la farmacia
     * @return {@link FarmaciaDetailDTO} - El autor encontrado en el medicamento.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper}
     * Error de lógica que se genera cuando no se encuentra la farmacia.
     */
    @GET
    @Path("{farmaciasId: \\d+}")
    public FarmaciaDetailDTO getFarmacia(@PathParam("medicamentosId") Long medicamentosId, @PathParam("farmaciasId") Long farmaciasId) {
        LOGGER.log(Level.INFO, "MedicamentoFarmaciasResource getFarmacia: input: medicamentosId {0} , farmaciasId {1}", new Object[]{medicamentosId, farmaciasId});
        if (farmaciaLogic.getFarmacia(farmaciasId) == null) {
            throw new WebApplicationException("El recurso /farmacias/" + farmaciasId + " no existe.", 404);
        }
        FarmaciaDetailDTO detailDTO = new FarmaciaDetailDTO(medicamentoFarmaciaLogic.getFarmacia(medicamentosId, farmaciasId));
        LOGGER.log(Level.INFO, "MedicamentoFarmaciasResource getFarmacia: output: {0}", detailDTO.toString());
        return detailDTO;
    }

    /**
     * Actualiza la lista de autores de un medicamento con la lista que se recibe en
     * el cuerpo.
     *
     * @param medicamentosId El ID del medicamento al cual se le va a asociar la lista de
     * autores
     * @param farmacias JSONArray {@link FarmaciaDetailDTO} - La lista de autores
     * que se desea guardar.
     * @return JSONArray {@link FarmaciaDetailDTO} - La lista actualizada.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper}
     * Error de lógica que se genera cuando no se encuentra la farmacia.
     */
    @PUT
    public List<FarmaciaDetailDTO> replaceFarmacias(@PathParam("medicamentosId") Long medicamentosId, List<FarmaciaDetailDTO> farmacias) {
        LOGGER.log(Level.INFO, "MedicamentoFarmaciasResource replaceFarmacias: input: medicamentosId {0} , farmacias {1}", new Object[]{medicamentosId, farmacias.toString()});
        for (FarmaciaDetailDTO farmacia : farmacias) {
            if (farmaciaLogic.getFarmacia(farmacia.getId()) == null) {
                throw new WebApplicationException("El recurso /farmacias/" + farmacia.getId() + " no existe.", 404);
            }
        }
        List<FarmaciaDetailDTO> lista = farmaciasListEntity2DTO(medicamentoFarmaciaLogic.replaceFarmacias(medicamentosId, farmaciasListDTO2Entity(farmacias)));
        LOGGER.log(Level.INFO, "MedicamentoFarmaciasResource replaceFarmacias: output:{0}", lista.toString());
        return lista;
    }

    /**
     * Elimina la conexión entre la farmacia y el medicamento recibidos en la URL.
     *
     * @param medicamentosId El ID del medicamento al cual se le va a desasociar la farmacia
     * @param farmaciasId El ID de la farmacia que se desasocia
     * @throws WebApplicationException {@link WebApplicationExceptionMapper}
     * Error de lógica que se genera cuando no se encuentra la farmacia.
     */
    @DELETE
    @Path("{farmaciasId: \\d+}")
    public void removeFarmacia(@PathParam("medicamentosId") Long medicamentosId, @PathParam("farmaciasId") Long farmaciasId) {
        LOGGER.log(Level.INFO, "MedicamentoFarmaciasResource removeFarmacia: input: medicamentosId {0} , farmaciasId {1}", new Object[]{medicamentosId, farmaciasId});
        if (farmaciaLogic.getFarmacia(farmaciasId) == null) {
            throw new WebApplicationException("El recurso /farmacias/" + farmaciasId + " no existe.", 404);
        }
        medicamentoFarmaciaLogic.removeFarmacia(medicamentosId, farmaciasId);
        LOGGER.info("MedicamentoFarmaciasResource removeFarmacia: output: void");
    }

    /**
     * Convierte una lista de FarmaciaEntity a una lista de FarmaciaDetailDTO.
     *
     * @param entityList Lista de FarmaciaEntity a convertir.
     * @return Lista de FarmaciaDetailDTO convertida.
     */
    private List<FarmaciaDetailDTO> farmaciasListEntity2DTO(List<FarmaciaEntity> entityList) {
        List<FarmaciaDetailDTO> list = new ArrayList<>();
        for (FarmaciaEntity entity : entityList) {
            list.add(new FarmaciaDetailDTO(entity));
        }
        return list;
    }

    /**
     * Convierte una lista de FarmaciaDetailDTO a una lista de FarmaciaEntity.
     *
     * @param dtos Lista de FarmaciaDetailDTO a convertir.
     * @return Lista de FarmaciaEntity convertida.
     */
    private List<FarmaciaEntity> farmaciasListDTO2Entity(List<FarmaciaDetailDTO> dtos) {
        List<FarmaciaEntity> list = new ArrayList<>();
        for (FarmaciaDetailDTO dto : dtos) {
            list.add(dto.toEntity());
        }
        return list;
    }
}
