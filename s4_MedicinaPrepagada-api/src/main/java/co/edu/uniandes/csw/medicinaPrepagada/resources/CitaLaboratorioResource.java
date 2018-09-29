/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.medicinaPrepagada.resources;

import co.edu.uniandes.csw.medicinaPrepagada.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.medicinaPrepagada.dtos.CitaLaboratorioDTO;
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
    
    @POST
    public CitaLaboratorioDTO createCitaLaboratorio (CitaLaboratorioDTO pCitaLaboratorio) throws BusinessLogicException
    {
        LOGGER.log(Level.INFO, "CitaLaboratorioResource createCitaLaboratorio: input: {0}", pCitaLaboratorio.toString());
        
        CitaLaboratorioDTO nuevoCitaLabDTO = new CitaLaboratorioDTO(citaLogic.createCitaLaboratorio(pCitaLaboratorio.toEntity()));
        
        LOGGER.log(Level.INFO, "CitaLaboratorioResource createCitaLaboratorio: output: {0}", nuevoCitaLabDTO.toString());
        
        return nuevoCitaLabDTO;
    }
    
    
    @GET
    @Path("{CitaLaboratorioId: \\d+}")
    public CitaLaboratorioDTO getCitaLaboratorio (@PathParam ("CitaLaboratorioId") Long CitaLaboratorioId ) throws WebApplicationException
    {
        LOGGER.log(Level.INFO, "CitaLaboratorioResource getCitaLaboratorio: input: {0}", CitaLaboratorioId);
        CitaLaboratorioEntity entity = citaLogic.getCita(CitaLaboratorioId);
        if(entity == null)
        {
            throw new WebApplicationException("El recurso /citaLaboratorio/" + CitaLaboratorioId + " no existe .", 404);
        }
        
        CitaLaboratorioDTO citaLabDTO = new CitaLaboratorioDTO(entity);
        LOGGER.log(Level.INFO, "CitaLaboratorioResource getCitaLaboratorio: output: {0}", citaLabDTO.toString());
        return citaLabDTO;
    }
    
    @GET
    
    public List <CitaLaboratorioDTO> getCitasLaboratorio ()
    {
        LOGGER.info("CitaLaboratorioResource getCitasLaboratorio: input: void");
        List<CitaLaboratorioDTO> listaCitasLab = listEntityDTO(citaLogic.getCitasLab()) ;
        LOGGER.log(Level.INFO, "CitaLaboratorioResource getCitasLaboratorio: output: {0}", listaCitasLab.toString());
        return listaCitasLab;
    }

    @DELETE
    @Path("{CitaLaboratorioId:\\d+}")
    public void deleteCitaLaboratorio (@PathParam ("CitaLaboratorioId") Long CitaLaboratorioId) throws BusinessLogicException
    {
        LOGGER.log(Level.INFO, "CitaLaboratorioDTO deleteCitaLaboratorio:input: {0}", CitaLaboratorioId);
        if (citaLogic.getCita(CitaLaboratorioId) == null) 
        {
            throw new WebApplicationException("El recurso /citaLaboratorio/ que desea eliminar" + CitaLaboratorioId + " no existe.", 404);
        }
        citaLogic.deleteCitaLab(CitaLaboratorioId);
        LOGGER.info("CitaLaboratorioDTO deleteCitaLaboratorio: output: void");
    }
    
    @PUT
    @Path("{CitaLaboratorioId:\\d+}")
    public CitaLaboratorioDTO updateCitaLaboratorio (@PathParam ("CitaLaboratorioId") Long CitaLaboratorioId, CitaLaboratorioDTO pCitaD) throws BusinessLogicException,WebApplicationException
    {
        LOGGER.log(Level.INFO, "CitaLaboratorioResource modificarCitaLaboratorio: input: {0}, citaLaboratorio {1}", new Object[]{CitaLaboratorioId, pCitaD.toString()});
        pCitaD.setId(CitaLaboratorioId);
        if (citaLogic.getCita(CitaLaboratorioId) == null)
        {
            throw new WebApplicationException("El recurso /citaLaboratorio/ que quiere editar con id" + CitaLaboratorioId + " no existe.", 404);
        }
        CitaLaboratorioDTO nuevoDTO = new CitaLaboratorioDTO(citaLogic.updateCitaLaboratorio(CitaLaboratorioId, pCitaD.toEntity()));
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
