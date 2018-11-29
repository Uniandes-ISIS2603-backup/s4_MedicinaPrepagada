/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.medicinaPrepagada.resources;

import co.edu.uniandes.csw.medicinaPrepagada.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.medicinaPrepagada.dtos.CitaLaboratorioDTO;
import co.edu.uniandes.csw.medicinaPrepagada.dtos.LaboratorioDTO;
import co.edu.uniandes.csw.medicinaPrepagada.ejb.CitaLaboratorioLogic;
import co.edu.uniandes.csw.medicinaPrepagada.entities.CitaLaboratorioEntity;
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
 * @author Santiago Rojas
 */
@Path ("/citaLaboratorio")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped

public class CitaLaboratorioResource {
    
    
    private static final Logger LOGGER = Logger.getLogger(CitaLaboratorioResource.class.getName());
    
    @Inject
    private CitaLaboratorioLogic citaLogic;
    
    /**
     * Crea una nueva CitaLaboratorio con la informacion que se recibe en el cuerpo de
     * la petición y se regresa un objeto identico con un id auto-generado por
     * la base de datos.
     *
     * @param CitaLaboratorio {@link CitaLaboratorioDTO} - La CitaLaboratorio que se desea
     * guardar.
     * @return JSON {@link CitaMedicaDTO} - La CitaMedica guardada con el atributo
     * id autogenerado.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando ya existe la CitaMedica.
     */
    @POST
    public CitaLaboratorioDTO createCitaLaboratorio (CitaLaboratorioDTO pCitaLaboratorio) throws BusinessLogicException
    {
        LOGGER.log(Level.INFO, "CitaLaboratorioResource createCitaLaboratorio: input: {0}", pCitaLaboratorio.toString());
        
        CitaLaboratorioDTO nuevoCitaLabDTO = new CitaLaboratorioDTO(citaLogic.createCitaLaboratorio(pCitaLaboratorio.toEntity()));
        
        LOGGER.log(Level.INFO, "CitaLaboratorioResource createCitaLaboratorio: output: {0}", nuevoCitaLabDTO.toString());
        
        return nuevoCitaLabDTO;
    }
    
     /**
     * Busca la CitaLaboratorio con el id asociado recibido en la URL y la devuelve.
     *
     * @param pCitaLaboratorioId Identificador de la CitaLaboratorio que se esta buscando.
     * Este debe ser una cadena de dígitos.
     * @return JSON {@link CitaLaboratorioDTO} - La CitaLabroatorio buscada
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la CitaLaboratorio.
     */
    
    @GET
    @Path("{CitaLaboratorioId: \\d+}")
    public CitaLaboratorioDTO getCitaLaboratorio (@PathParam ("CitaLaboratorioId") Long pCitaLaboratorioId ) 
    {
        LOGGER.log(Level.INFO, "CitaLaboratorioResource getCitaLaboratorio: input: {0}", pCitaLaboratorioId);
        CitaLaboratorioEntity entity = citaLogic.getCita(pCitaLaboratorioId);
        if(entity == null)
        {
            throw new WebApplicationException("El recurso /citaLaboratorio/" + pCitaLaboratorioId + " no existe .", 404);
        }
        
        CitaLaboratorioDTO citaLabDTO = new CitaLaboratorioDTO(entity);
        LOGGER.log(Level.INFO, "CitaLaboratorioResource getCitaLaboratorio: output: {0}", citaLabDTO);
        return citaLabDTO;
    }
    
    
    /**
     * Busca y devuelve todas las citas de laboratorio que existen en la aplicacion.
     *
     * @return JSONArray {@link CitaLaboratorioDTO} - Las citas laboratorio encontradas en
     * la aplicación. Si no hay ninguna retorna una lista vacía.
     */
    @GET
    
    public List <CitaLaboratorioDTO> getCitasLaboratorio ()
    {
        LOGGER.info("CitaLaboratorioResource getCitasLaboratorio: input: void");
        List<CitaLaboratorioDTO> listaCitasLab = listEntityDTO(citaLogic.getCitasLab()) ;
        LOGGER.log(Level.INFO, "CitaLaboratorioResource getCitasLaboratorio: output: {0}", listaCitasLab.toString());
        return listaCitasLab;
    }

   @GET
   @Path ("{CitaLaboratorioId:\\d+}/laboratorio")
   public LaboratorioDTO getLaboratorioFromCita (@PathParam ("CitaLaboratorioId") Long pCitaLaboratorioId) throws BusinessLogicException
   {

       return new LaboratorioDTO(citaLogic.getLaboratorioFromCita(pCitaLaboratorioId)); 
   }
    @DELETE
    @Path("{CitaLaboratorioId:\\d+}")
    public void deleteCitaLaboratorio (@PathParam ("CitaLaboratorioId") Long pCitaLaboratorioId) throws BusinessLogicException
    {
        LOGGER.log(Level.INFO, "CitaLaboratorioDTO deleteCitaLaboratorio:input: {0}", pCitaLaboratorioId);
        if (citaLogic.getCita(pCitaLaboratorioId) == null) 
        {
            throw new WebApplicationException("El recurso /citaLaboratorio/ que desea eliminar" + pCitaLaboratorioId + " no existe.", 404);
        }
        citaLogic.deleteCitaLab(pCitaLaboratorioId);
        LOGGER.info("CitaLaboratorioDTO deleteCitaLaboratorio: output: void");
    }
    
    @PUT
    @Path("{CitaLaboratorioId:\\d+}")
    public CitaLaboratorioDTO updateCitaLaboratorio (@PathParam ("CitaLaboratorioId") Long pCitaLaboratorioId, CitaLaboratorioDTO pCitaD) throws BusinessLogicException,WebApplicationException
    {
        LOGGER.log(Level.INFO, "CitaLaboratorioResource modificarCitaLaboratorio: input: {0}, citaLaboratorio {1}", new Object[]{pCitaLaboratorioId, pCitaD.toString()});
        pCitaD.setId(pCitaLaboratorioId);
        if (citaLogic.getCita(pCitaLaboratorioId) == null)
        {
            throw new WebApplicationException("El recurso /citaLaboratorio/ que quiere editar con id" + pCitaLaboratorioId + " no existe.", 404);
        }
        CitaLaboratorioDTO nuevoDTO = new CitaLaboratorioDTO(citaLogic.updateCitaLaboratorio(pCitaLaboratorioId, pCitaD.toEntity()));
        LOGGER.log(Level.INFO,"CitaLaboratorioResource modificarCitaLaboratorio: output: {0}", nuevoDTO.toString());
        
        return nuevoDTO;
    }
    
    
            
    private List<CitaLaboratorioDTO> listEntityDTO(List<CitaLaboratorioEntity> entityList) 
    {
        List<CitaLaboratorioDTO> list = new ArrayList<>();
        for (CitaLaboratorioEntity entity : entityList) 
        {
            list.add(new CitaLaboratorioDTO(entity));
        }
        return list;
    }      
    
}
