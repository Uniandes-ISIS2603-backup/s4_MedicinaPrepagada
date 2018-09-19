/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.medicinaPrepagada.resources;
import co.edu.uniandes.csw.medicinaPrepagada.dtos.LaboratorioDTO;
import co.edu.uniandes.csw.medicinaPrepagada.dtos.LaboratorioDetailDTO;
import co.edu.uniandes.csw.medicinaPrepagada.ejb.LaboratorioLogic;
import co.edu.uniandes.csw.medicinaPrepagada.entities.LaboratorioEntity;
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
import javax.ws.rs.WebApplicationException;

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
    private LaboratorioLogic labLogic;
    
    @POST
    public LaboratorioDTO createLaboratorio (LaboratorioDTO Laboratorio) throws BusinessLogicException
    {
        LOGGER.log(Level.INFO, "LaboratorioDTO createLaboratorio: input: {0}", Laboratorio.toString());
        
        LaboratorioDTO nuevoLab = new LaboratorioDTO(labLogic.createLaboratorio(Laboratorio.toEntity()));
       
        LOGGER.log(Level.INFO, "LaboratorioDTO createLaboratorio: output: {0}", nuevoLab.toString());
        
        return nuevoLab;
    }
    
    @DELETE
    @Path("{LaboratorioId:\\d+}")
    public void deleteLaboratorio (@PathParam ("LaboratorioId") Long LaboratorioId) throws BusinessLogicException
    {
        LOGGER.log(Level.INFO, "LaboratorioDTO deleteLaboratorio: input : {0}", LaboratorioId);
        if (labLogic.getLab(LaboratorioId) == null) 
        {
            throw new WebApplicationException("El recurso /laboratorio/ que desea eliminar" + LaboratorioId + " no existe.", 404);
        }
        labLogic.deleteLaboratorio(LaboratorioId);
        
        LOGGER.info("LaboratorioDTO deleteLaboratorio: output:void");
    }
    
    @PUT
    @Path("{LaboratorioId:\\d+}")
    public LaboratorioDTO updateLaboratorio (@PathParam ("LaboratorioId") Long LaboratorioId, LaboratorioDetailDTO pLab) throws BusinessLogicException, WebApplicationException
    {
        LOGGER.log(Level.INFO, "LaboratorioResource modificarLaboratorio: input: {0}", LaboratorioId);
        pLab.setId(LaboratorioId);
        if (labLogic.getLab(LaboratorioId) == null)
        {
            throw new WebApplicationException("El recurso /laboratorio/ que quiere editar con id" + LaboratorioId + " no existe.", 404);
        }
        LaboratorioDetailDTO detailDTO = new LaboratorioDetailDTO(labLogic.updateLaboratorio(LaboratorioId, pLab.toEntity()));
        LOGGER.log(Level.INFO,"LaboratorioResource modificarLaboratorio: output:  {0}", detailDTO.toString());
        
        return detailDTO;
    }
    
    @GET
    @Path("{LaboratorioId:\\d+}")
    public LaboratorioDTO getLaboratorio (@PathParam ("LaboratorioId") Long LaboratorioId)
    {
        LOGGER.log(Level.INFO, "LaboratorioResource getLaboratorio: input:  {0}", LaboratorioId);
        LaboratorioEntity labEntity = labLogic.getLab(LaboratorioId);
        if (labEntity == null) 
        {
            throw new WebApplicationException("El recurso /laboratorio/" + LaboratorioId + " no existe .", 404);
        }
        LaboratorioDetailDTO detailDTO = new LaboratorioDetailDTO(labEntity);
        LOGGER.log(Level.INFO, "LaboratorioResource getLaboratorio: output: {0}", detailDTO.toString());
        return detailDTO;
    }
    
    @GET
    public List<LaboratorioDTO> getLaboratorios ()
    {
        LOGGER.info("LaboratorioResource getLaboratorios: input: void");
        List<LaboratorioDTO> listaLabs = listEntityDetailDTO(labLogic.getLabs()) ;
        LOGGER.log(Level.INFO, "CitaLaboratorioResource getCitasLaboratorio: output: {0}", listaLabs.toString());
        return listaLabs;
    }
    
    
     private List<LaboratorioDTO> listEntityDetailDTO(List<LaboratorioEntity> entityList)
    {
        List<LaboratorioDTO> list = new ArrayList<>();
         for (LaboratorioEntity entity : entityList) 
        {
            list.add(new LaboratorioDetailDTO(entity));
        }

        return list;
    }       
    
}
