/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.medicinaPrepagada.resources;

import co.edu.uniandes.csw.medicinaPrepagada.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.medicinaPrepagada.dtos.CitaLaboratorioDTO;
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
   
    @POST
    public CitaLaboratorioDTO createCitaLaboratorio (CitaLaboratorioDTO CitaLaboratorio) throws BusinessLogicException
    {
        LOGGER.log(Level.INFO, "CitaLaboratorioResource CreateCitaLaboratorio: input: (0)", CitaLaboratorio.toString());
        
        CitaLaboratorioDTO nuevoCitaLabDTO = new CitaLaboratorioDTO();
        
        LOGGER.log(Level.INFO, "CitaLaboratorioResource CreateCitaLaboratorio: output: (0)", nuevoCitaLabDTO.toString());
        
        return nuevoCitaLabDTO;
    }
    
    
    @GET
    @Path("{CitaLaboratorioId: \\d+}")
    public CitaLaboratorioDTO getCitaLaboratorio (@PathParam ("CitaLaboratorioId") Long CitaLaboratorioId ) throws WebApplicationException
    {
        LOGGER.log(Level.INFO, "CitaLaboratorioResource getCitaLaboratorio: input: (0)", CitaLaboratorioId);
        
        CitaLaboratorioDTO nuevoDetailDTO = new CitaLaboratorioDTO();
        
        LOGGER.log(Level.INFO, "CitaLaboratorioResource getCitaLaboratorio: output: {0}", nuevoDetailDTO.toString());
        return nuevoDetailDTO;
    }
    
    @GET
    
    public List <CitaLaboratorioDTO> getCitasLaboratorio ()
    {
        LOGGER.info("CitaLaboratorioResource getCitasLaboratorio: input: void");
        List<CitaLaboratorioDTO> listaCitasLab = listEntityDetailDTO() ;
        LOGGER.log(Level.INFO, "CitaLaboratorioResource getCitasLaboratorio: output: {0}", listaCitasLab.toString());
        return listaCitasLab;
    }

    @DELETE
    @Path("{CitaLaboratorioId: \\d+}")
    public void deleteCitaLaboratorio (@PathParam ("CitaLaboratorioId") Long CitaLaboratorioId) throws BusinessLogicException
    {
        LOGGER.log(Level.INFO, "CitaLaboratorioResource deleteCitaLaboratorio: input:(0)", CitaLaboratorioId);
        LOGGER.info("CitaLaboratorioResource deleteCitaLaboratorio: output: void");
    }
    
    @PUT
    @Path("(CitaLaboratorioId:\\d+)")
    public CitaLaboratorioDTO modificarCitaLaboratorio (@PathParam ("CitaLaboratorioId") Long CitaLaboratorioId) throws BusinessLogicException
    {
        LOGGER.log(Level.INFO, "CitaLaboratorioResource modificarCitaLaboratorio: input:(0)", CitaLaboratorioId);
        CitaLaboratorioDTO modificadoDetailDTO = new CitaLaboratorioDTO ();
        
        LOGGER.log(Level.INFO,"CitaLaboratorioResource modificarCitaLaboratorio: output: (0)", modificadoDetailDTO.toString());
        
        return modificadoDetailDTO;
    }
    
    
            
    private List<CitaLaboratorioDTO> listEntityDetailDTO()
    {
        List<CitaLaboratorioDTO> list = new ArrayList<>();

        return list;
    }       
    
}
