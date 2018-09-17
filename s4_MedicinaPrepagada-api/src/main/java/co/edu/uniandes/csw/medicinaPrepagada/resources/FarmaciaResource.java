package co.edu.uniandes.csw.medicinaPrepagada.resources;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import co.edu.uniandes.csw.medicinaPrepagada.dtos.FarmaciaDTO;
import co.edu.uniandes.csw.medicinaPrepagada.ejb.FarmaciaLogic;
import co.edu.uniandes.csw.medicinaPrepagada.entities.FarmaciaEntity;
import co.edu.uniandes.csw.medicinaPrepagada.exceptions.BusinessLogicException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.WebApplicationException;
/**
 * Clase que implementa el recurso "farmacias".
 *
 * @author ISIS2603
 * @version 1.0
 */
@Path("farmacias")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class FarmaciaResource {
    private static final Logger LOGGER = Logger.getLogger(FarmaciaResource.class.getName());

    @Inject
    FarmaciaLogic farmaciaLogic; // Variable para acceder a la lógica de la aplicación. Es una inyección de dependencias.

    /**
     * Crea una nueva farmacia con la informacion que se recibe en el cuerpo de
     * la petición y se regresa un objeto identico con un id auto-generado por
     * la base de datos.
     *
     * @param pFarmacia {@link FarmaciaDTO} - La farmacia que se desea
     * guardar.
     * @return JSON {@link FarmaciaDTO} - La farmacia guardada con el atributo
     * id autogenerado.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando ya existe la farmacia.
     */
    @POST
    public FarmaciaDTO createFarmacia(FarmaciaDTO pFarmacia) throws BusinessLogicException {
         LOGGER.log(Level.INFO, "FarmaciaResource createFarmacia: input: {0}", pFarmacia.toString());
        // Convierte el DTO (json) en un objeto Entity para ser manejado por la lógica.
        FarmaciaEntity farmaciaEntity = pFarmacia.toEntity();
        // Invoca la lógica para crear la farmacia nueva
        FarmaciaEntity nuevoFarmaciaEntity = farmaciaLogic.createFarmacia(farmaciaEntity);
        // Como debe retornar un DTO (json) se invoca el constructor del DTO con argumento el entity nuevo
        FarmaciaDTO nuevoFarmaciaDTO = new FarmaciaDTO(nuevoFarmaciaEntity);
        LOGGER.log(Level.INFO, "FarmaciaResource createFarmacia: output: {0}", nuevoFarmaciaDTO.toString());
        return nuevoFarmaciaDTO;
    }

   /**
     * Busca y devuelve todas las farmacias que existen en la aplicacion.
     *
     * @return JSONArray {@link FarmaciaDTO} - Las farmacias
     * encontradas en la aplicación. Si no hay ninguna retorna una lista vacía.
     */
    @GET
    public List<FarmaciaDTO> getFarmacias() {
        LOGGER.info("FarmaciaResource getFarmacias: input: void");
        List<FarmaciaDTO> listaFarmacias = listEntity2DetailDTO(farmaciaLogic.getFarmacias());
        LOGGER.log(Level.INFO, "FarmaciaResource getFarmacias: output: {0}", listaFarmacias.toString());
        return listaFarmacias;
    }

    /**
     * Busca la farmacia con el id asociado recibido en la URL y la devuelve.
     *
     * @param farmaciasId Identificador de la farmacia que se esta buscando.
     * Este debe ser una cadena de dígitos.
     * @return JSON {@link FarmaciaDTO} - La farmacia buscada
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la farmacia.
     */
    @GET
    @Path("{farmaciasId: \\d+}")
    public FarmaciaDTO getFarmacia(@PathParam("farmaciasId") Long farmaciasId) throws WebApplicationException {
        LOGGER.log(Level.INFO, "FarmaciaResource getFarmacia: input: {0}", farmaciasId);
        FarmaciaEntity farmaciaEntity = farmaciaLogic.getFarmacia(farmaciasId);
        if (farmaciaEntity == null) {
            throw new WebApplicationException("El recurso /farmacias/" + farmaciasId + " no existe.", 404);
        }
        FarmaciaDTO detailDTO = new FarmaciaDTO(farmaciaEntity);
        LOGGER.log(Level.INFO, "FarmaciaResource getFarmacia: output: {0}", detailDTO.toString());
        return detailDTO;
    }

    /**
     * Actualiza la farmacia con el id recibido en la URL con la informacion
     * que se recibe en el cuerpo de la petición.
     *
     * @param farmaciasId Identificador de la farmacia que se desea
     * actualizar. Este debe ser una cadena de dígitos.
     * @param farmacia {@link FarmaciaDTO} La farmacia que se desea
     * guardar.
     * @return JSON {@link FarmaciaDTO} - La farmacia guardada.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la farmacia a
     * actualizar.
     */
    @PUT
    @Path("{farmaciasId: \\d+}")
    public FarmaciaDTO updateFarmacia(@PathParam("farmaciasId") Long farmaciasId, FarmaciaDTO farmacia) throws WebApplicationException, BusinessLogicException {
        LOGGER.log(Level.INFO, "FarmaciaResource updateFarmacia: input: id:{0} , farmacia: {1}", new Object[]{farmaciasId, farmacia.toString()});
        farmacia.setId(farmaciasId);
        if (farmaciaLogic.getFarmacia(farmaciasId) == null) {
            throw new WebApplicationException("El recurso /farmacias/" + farmaciasId + " no existe.", 404);
        }
        FarmaciaDTO detailDTO = new FarmaciaDTO(farmaciaLogic.updateFarmacia(farmaciasId, farmacia.toEntity()));
        LOGGER.log(Level.INFO, "FarmaciaResource updateFarmacia: output: {0}", detailDTO.toString());
        return detailDTO;

    }

    /**
     * Borra la farmacia con el id asociado recibido en la URL.
     *
     * @param farmaciasId Identificador de la farmacia que se desea borrar.
     * Este debe ser una cadena de dígitos.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando no se puede eliminar la farmacia.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la farmacia.
     */
    @DELETE
    @Path("{farmaciasId: \\d+}")
    public void deleteFarmacia(@PathParam("farmaciasId") Long farmaciasId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "FarmaciaResource deleteFarmacia: input: {0}", farmaciasId);
        if (farmaciaLogic.getFarmacia(farmaciasId) == null) {
            throw new WebApplicationException("El recurso /farmacias/" + farmaciasId + " no existe.", 404);
        }
        farmaciaLogic.deleteFarmacia(farmaciasId);
        LOGGER.info("FarmaciaResource deleteFarmacia: output: void");
    }

    /**
     * Convierte una lista de entidades a DTO.
     *
     * Este método convierte una lista de objetos FarmaciaEntity a una lista de
     * objetos FarmaciaDTO (json)
     *
     * @param entityList corresponde a la lista de farmacias de tipo Entity
     * que vamos a convertir a DTO.
     * @return la lista de farmacias en forma DTO (json)
     */
    private List<FarmaciaDTO> listEntity2DetailDTO(List<FarmaciaEntity> entityList) {
        List<FarmaciaDTO> list = new ArrayList<>();
        for (FarmaciaEntity entity : entityList) {
            list.add(new FarmaciaDTO(entity));
        }
        return list;
    }
    
}
