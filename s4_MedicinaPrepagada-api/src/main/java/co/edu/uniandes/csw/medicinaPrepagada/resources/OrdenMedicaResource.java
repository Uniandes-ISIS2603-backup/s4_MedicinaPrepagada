/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.medicinaPrepagada.resources;

import co.edu.uniandes.csw.medicinaPrepagada.dtos.OrdenMedicaDTO;
import co.edu.uniandes.csw.medicinaPrepagada.dtos.OrdenMedicaDetailDTO;
import co.edu.uniandes.csw.medicinaPrepagada.ejb.OrdenMedicaLogic;
import co.edu.uniandes.csw.medicinaPrepagada.entities.OrdenMedicaEntity;
import co.edu.uniandes.csw.medicinaPrepagada.exceptions.BusinessLogicException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;

/**
 *
 * @author ni.ramirez10
 */

@Path("ordenesMedicas")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class OrdenMedicaResource 
{
    private static final Logger LOGGER = Logger.getLogger(OrdenMedicaResource.class.getName());
    
    @Inject
    private OrdenMedicaLogic ordenLogic;
    
    private final String mensaje = "no existe"; 
    
    
    /**
     * Crea una nueva orden medica con la informacion que se recibe en el cuerpo de la
     * petición y se regresa un objeto identico.
     * @param ordenMedica
     * @return 
     * @throws BusinessLogicException
     */
    
    @POST
    public OrdenMedicaDetailDTO createOrdenMedica(OrdenMedicaDTO ordenMedica) throws BusinessLogicException 
    {
        LOGGER.log(Level.INFO, "OrdenMedicaResource createOrdenMedica: input: {0}", ordenMedica);
        OrdenMedicaDetailDTO nuevaOrdenMedicaDTO = new OrdenMedicaDetailDTO(ordenLogic.createOrdenMedica(ordenMedica.toEntity()));
        LOGGER.log(Level.INFO, "OrdenMedicaResource createOrdenMedica: output: {0}", nuevaOrdenMedicaDTO);
        return nuevaOrdenMedicaDTO;
    }
    
     /**
     * Busca y devuelve todos las ordenes medicas que existen en el sistema.
     * @return List - Las ordenes medicas encontrados en la
     * aplicación. Si no hay ninguno retorna una lista vacía.
     */
    
    @GET
    public List<OrdenMedicaDetailDTO> getOrdenesMedicas() 
    {
        LOGGER.info("OrdenMedicaResource getOrdenesMedicas: input: void");
        List<OrdenMedicaDetailDTO> listaOrdenesMedicas = listEntity2DetailDTO(ordenLogic.getOrdenesMedicas());
        LOGGER.log(Level.INFO, "OrdenMedicaResource getOrdenesMedicas: output: {0}", listaOrdenesMedicas);
        return listaOrdenesMedicas;
    }
    
    
    /**
     * Busca la orden medica con el id asociado recibido en la URL y lo devuelve.
     * @param ordenMedicaid
     * @return 
     */
    
    @GET
    @Path("{ordenMedicaId: \\d+}")
    public OrdenMedicaDTO getOrdenMedica(@PathParam("ordenMedicaId") Long ordenMedicaid)
    {
        LOGGER.log(Level.INFO, "OrdenMedicaResource getOrdenMedica: input: {0}", ordenMedicaid);
        
        OrdenMedicaEntity ordenmedicaEntity = ordenLogic.getOrdenMedica(ordenMedicaid);
        
        if (ordenmedicaEntity == null) 
        {
            throw new WebApplicationException("El recurso /ordenMedica/ " + ordenMedicaid + mensaje, 404);
        }
        
        OrdenMedicaDTO ordenMedicaDetailDTO = new OrdenMedicaDTO(ordenmedicaEntity);
        LOGGER.log(Level.INFO, "OrdenMedicaResource getOrdenMedica: output: {0}", ordenMedicaDetailDTO);
        return ordenMedicaDetailDTO;
    }
    
    /**
     * Elimina la orden medica con el id asociado recibido en la URL y lo devuelve.
     * @param ordenMedicaid
     * @throws co.edu.uniandes.csw.medicinaPrepagada.exceptions.BusinessLogicException
     */
    
    @DELETE
    @Path("{ordenMedicaId: \\d+}")
    public void deleteOrdenMedica(@PathParam ("ordenMedicaId") Long ordenMedicaid) throws BusinessLogicException
    {
        LOGGER.log(Level.INFO, "OrdenMedicaResource deleteOrdenMedica: input:(0)", ordenMedicaid);
        
        if (ordenLogic.getOrdenMedica(ordenMedicaid) == null) 
        {
           throw new WebApplicationException("El recurso /ordenMedica/ " + ordenMedicaid + mensaje, 404);
        }        
        ordenLogic.deleteOrdenMedica(ordenMedicaid); 
        
        LOGGER.info("OrdenMedicaResource deleteOrdenMedica: output: void");
    }
    
    /**
     * Modifica la orden medica con el id asociado recibido en la URL y lo devuelve.
     * @param ordenMedicaid
     * @param pOrden
     * @return 
     */
    
    @PUT
    @Path("{ordenMedicaId: \\d+}")
    public OrdenMedicaDTO modificarOrdenMedica(@PathParam ("ordenMedicaId") Long ordenMedicaid, OrdenMedicaDetailDTO pOrden) throws BusinessLogicException  
    {
        LOGGER.log(Level.INFO, "OrdenMedicaResource modificarOrdenMedica: input:(0)", ordenMedicaid);
        pOrden.setId(ordenMedicaid);
        
        if (ordenLogic.getOrdenMedica(ordenMedicaid) == null)
        {
            throw new WebApplicationException("La orden medica con id " + ordenMedicaid + mensaje, 404);
        }
         
        OrdenMedicaDetailDTO modificarDetailDto = new OrdenMedicaDetailDTO(ordenLogic.updateOrdenMedica(ordenMedicaid, pOrden.toEntity()));        
        LOGGER.log(Level.INFO,"OrdenMedicaResource modificarOrdenMedica: output: (0)", modificarDetailDto);
        return modificarDetailDto;
    }
       
    
     private List<OrdenMedicaDetailDTO> listEntity2DetailDTO( List<OrdenMedicaEntity> entityList) 
    { 
        List<OrdenMedicaDetailDTO> list = new ArrayList<>();
        
        for(OrdenMedicaEntity entity : entityList)
        {
            list.add(new OrdenMedicaDetailDTO(entity)); 
        }
        
        return list;
    }
}
