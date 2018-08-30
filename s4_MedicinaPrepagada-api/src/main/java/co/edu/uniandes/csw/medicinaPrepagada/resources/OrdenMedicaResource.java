/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.medicinaPrepagada.resources;

import co.edu.uniandes.csw.medicinaPrepagada.dtos.OrdenMedicaDTO;
import co.edu.uniandes.csw.medicinaPrepagada.exceptions.BusinessLogicException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

/**
 *
 * @author ni.ramirez10
 */

@Path("ordenMedica")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class OrdenMedicaResource 
{
    private static final Logger LOGGER = Logger.getLogger(OrdenMedicaResource.class.getName());
    
    
    /**
     * Crea una nueva orden medica con la informacion que se recibe en el cuerpo de la
     * petición y se regresa un objeto identico.
     * @throws BusinessLogicException
     */
    
    @POST
    public OrdenMedicaDTO createOrdenMedica(OrdenMedicaDTO ordenMedica) throws BusinessLogicException 
    {
        LOGGER.log(Level.INFO, "OrdenMedicaResource createOrdenMedica: input: {0}", ordenMedica.toString());
        OrdenMedicaDTO nuevaOrdenMedicaDTO = new OrdenMedicaDTO();
        LOGGER.log(Level.INFO, "OrdenMedicaResource createOrdenMedica: output: {0}", nuevaOrdenMedicaDTO.toString());
        return nuevaOrdenMedicaDTO;
    }
    
     /**
     * Busca y devuelve todos las ordenes medicas que existen en el sistema.
     * @return List - Las ordenes medicas encontrados en la
     * aplicación. Si no hay ninguno retorna una lista vacía.
     */
    
    @GET
    public List<OrdenMedicaDTO> getOrdenesMedicas() 
    {
        LOGGER.info("OrdenMedicaResource getOrdenesMedicas: input: void");
        List<OrdenMedicaDTO> listaOrdenesMedicas = listEntity2DetailDTO();
        LOGGER.log(Level.INFO, "OrdenMedicaResource getOrdenesMedicas: output: {0}", listaOrdenesMedicas.toString());
        return listaOrdenesMedicas;
    }
    
    
    /**
     * Busca la orden medica con el id asociado recibido en la URL y lo devuelve.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la orden medica.
     */
    
    @GET
    @Path("{OrdenMedicaId: \\d+}")
    public OrdenMedicaDTO getOrdenMedica(@PathParam("ordenMedicaId") Long ordenMedicaid) 
    {
        LOGGER.log(Level.INFO, "OrdenMedicaResource getOrdenMedica: input: {0}", ordenMedicaid);
        /**OrdenMedicaEntity ordenmedicaEntity = ordenMedicaLogic.getOrdenMedica(ordenMedicaid);
        
        if (ordenmedicaEntity == null) 
        {
            throw new WebApplicationException("El recurso /ordenMedica/" + ordenMedicaid + " no existe.", 404);
        }*/
        OrdenMedicaDTO ordenMedicaDetailDTO = new OrdenMedicaDTO();
        LOGGER.log(Level.INFO, "OrdenMedicaResource getOrdenMedica: output: {0}", ordenMedicaDetailDTO.toString());
        return ordenMedicaDetailDTO;
    }
    
    /**
     * Elimina la orden medica con el id asociado recibido en la URL y lo devuelve.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la orden medica.
     */
    
    @DELETE
    @Path("{OrdenMedicaId: \\d+}")
    public void deleteOrdenMedica(@PathParam ("ordenMedicaId") Long ordenMedicaid) throws BusinessLogicException
    {
        LOGGER.log(Level.INFO, "OrdenMedicaResource deleteOrdenMedica: input:(0)", ordenMedicaid);
        /**if (OrdenMedicaLogic.getOrdenMedica(ordenMedicaid) == null) 
        {
           throw new WebApplicationException("El recurso /ordenMedica/" + ordenMedicaid + " no existe.", 404);
        }
        OrdenMedicaLogic.deleteOrdenMedica(ordenMedicaid); */
        LOGGER.info("OrdenMedicaResource deleteOrdenMedica: output: void");
    }
    
    /**
     * Modifica la orden medica con el id asociado recibido en la URL y lo devuelve.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la orden medica.
     */
    
    @PUT
    @Path("(OrdenMedicaId: \\d+)")
    public OrdenMedicaDTO modificarOrdenMedica(@PathParam ("ordenMedicaId") Long ordenMedicaid) throws BusinessLogicException
    {
        LOGGER.log(Level.INFO, "OrdenMedicaResource modificarOrdenMedica: input:(0)", ordenMedicaid);
        OrdenMedicaDTO modificarDetailDto = new OrdenMedicaDTO ();        
        LOGGER.log(Level.INFO,"OrdenMedicaResource modificarOrdenMedica: output: (0)", modificarDetailDto.toString());
        return modificarDetailDto;
    }
       
    
     private List<OrdenMedicaDTO> listEntity2DetailDTO() 
    { 
        List<OrdenMedicaDTO> list = new ArrayList<>();
        
        return list;
    }
}
