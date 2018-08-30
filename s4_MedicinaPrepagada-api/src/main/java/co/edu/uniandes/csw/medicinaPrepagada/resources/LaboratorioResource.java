/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.medicinaPrepagada.resources;
import co.edu.uniandes.csw.medicinaPrepagada.dtos.LaboratorioDTO;
import co.edu.uniandes.csw.medicinaPrepagada.dtos.LaboratorioDetailDTO;
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
 * @author Santiago Rojas
 */

@Path("/laboratorios")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped

public class LaboratorioResource {
 
    
    private static final Logger LOGGER = Logger.getLogger(LaboratorioResource.class.getName());
    
    
    @POST
    public LaboratorioDTO createLaboratorio (LaboratorioDTO Laboratorio) throws BusinessLogicException
    {
        LOGGER.log(Level.INFO, "LaboratorioDTO createLaboratorio: input: (0)", Laboratorio.toString());
        
        LaboratorioDTO nuevoLab = new LaboratorioDTO();
       
        LOGGER.log(Level.INFO, "LaboratorioDTO createLaboratorio: output: (0)", nuevoLab.toString());
        
        return nuevoLab;
    }
    
    @DELETE
    @Path("(LaboratorioId:\\d+)")
    public void deleteLaboratorio (@PathParam ("LaboratorioId") Long LaboratorioId) throws BusinessLogicException
    {
        LOGGER.log(Level.INFO, "LaboratorioDTO deleteLaboratorio: input : (0)", LaboratorioId);
        LOGGER.info("LaboratorioDTO deleteLaboratorio: output:void");
    }
    
    @PUT
    @Path("(LaboratorioId:\\d+)")
    public LaboratorioDTO modificarLaboratorio (@PathParam ("LaboratorioId") Long LaboratorioId)
    {
        LOGGER.log(Level.INFO, "LaboratorioResource modificarLaboratorio: input:(0)", LaboratorioId);
        LaboratorioDTO modificadoDetailDTO = new LaboratorioDTO ();
        
        LOGGER.log(Level.INFO,"LaboratorioResource modificarLaboratorio: output: (0)", modificadoDetailDTO.toString());
        
        return modificadoDetailDTO;
    }
    
    @GET
    @Path("(LaboratorioId:\\d+)")
    public LaboratorioDTO getLaboratorio (@PathParam ("LaboratorioId") Long LaboratorioId)
    {
         LOGGER.log(Level.INFO, "LaboratorioResource getLaboratorio: input: (0)", LaboratorioId);
        
        LaboratorioDetailDTO nuevoDetailDTO = new LaboratorioDetailDTO();
        
        LOGGER.log(Level.INFO, "LaboratorioResource getLaboratorio: output: {0}", nuevoDetailDTO.toString());
        return nuevoDetailDTO;
    }
    
    @GET
    public List<LaboratorioDTO> getLaboratorios ()
    {
        LOGGER.info("LaboratorioResource getLaboratorios: input: void");
        List<LaboratorioDTO> listaLabs = listEntityDetailDTO() ;
        LOGGER.log(Level.INFO, "CitaLaboratorioResource getCitasLaboratorio: output: {0}", listaLabs.toString());
        return listaLabs;
    }
    
    
     private List<LaboratorioDTO> listEntityDetailDTO()
    {
        List<LaboratorioDTO> list = new ArrayList<>();

        return list;
    }       
    
}
