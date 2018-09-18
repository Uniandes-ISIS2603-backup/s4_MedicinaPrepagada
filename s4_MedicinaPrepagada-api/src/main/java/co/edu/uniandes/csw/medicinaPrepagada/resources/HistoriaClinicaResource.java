/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.medicinaPrepagada.resources;

import co.edu.uniandes.csw.medicinaPrepagada.dtos.HistoriaClinicaDTO;
import co.edu.uniandes.csw.medicinaPrepagada.dtos.HistoriaClinicaDetailDTO;
import co.edu.uniandes.csw.medicinaPrepagada.ejb.HistoriaClinicaLogic;
import co.edu.uniandes.csw.medicinaPrepagada.entities.HistoriaClinicaEntity;
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

@Path("historiaClinica")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class HistoriaClinicaResource 
{
    private static final Logger LOGGER = Logger.getLogger(HistoriaClinicaResource.class.getName());
    
     @Inject
    private HistoriaClinicaLogic histLogic;
    
    /**
     * Crea una nueva historia clinica con la informacion que se recibe en el cuerpo de la
     * petición y se regresa un objeto identico.
     * @param histClinica
     * @return 
     * @throws BusinessLogicException
     */
    
    @POST
    public HistoriaClinicaDTO createHistoriaClinica(HistoriaClinicaDTO histClinica) throws BusinessLogicException 
    {
        LOGGER.log(Level.INFO, "HistoriaClinicaResource createHistoriaClinica: input: {0}", histClinica.toString());
        HistoriaClinicaDTO nuevaHistClinicaDTO = new HistoriaClinicaDTO(histLogic.createHistoriaClinica(histClinica.toEntity()));
        LOGGER.log(Level.INFO, "HistoriaClinicaResource createHistoriaClinica: output: {0}", nuevaHistClinicaDTO.toString());
        return nuevaHistClinicaDTO;
    }
    
    /**
     * Busca y devuelve todos las historias clinicas que existen en el sistema.
     * @return List - Las historias clinicas encontrados en la
     * aplicación. Si no hay ninguno retorna una lista vacía.
     */
    
    @GET
    public List<HistoriaClinicaDTO> getHistoriasClinicas() 
    {
        LOGGER.info("HistoriaClinicaResource getHistoriasClinicas: input: void");
        List<HistoriaClinicaDTO> listaHistoriasClinicas = listEntity2DetailDTO(histLogic.getHistoriasClinicas());
        LOGGER.log(Level.INFO, "HistoriaClinicaResource getHistoriasClinicas: output: {0}", listaHistoriasClinicas.toString());
        return listaHistoriasClinicas;
    }
    
    /**
     * Busca la historia clinica con el id asociado recibido en la URL y lo devuelve.
     * @param histClinicaId
     * @return 
     */
    
    @GET
    @Path("{HistoriaClinicaId: \\d+}")
    public HistoriaClinicaDTO getHistoriaClinica(@PathParam("historiaClinicaId") Long histClinicaId) throws WebApplicationException
    {
        LOGGER.log(Level.INFO, "HistoriaClinicaResource getHistoriaClinica: input: {0}", histClinicaId);
        HistoriaClinicaEntity histClinEntity = histLogic.getHistoriaClinica(histClinicaId);
        
        if (histClinEntity == null) 
        {
            throw new WebApplicationException("El recurso /historiClinica/" + histClinicaId + " no existe.", 404);
        }
        
        HistoriaClinicaDTO histClinDetailDTO = new HistoriaClinicaDTO(histClinEntity);
        LOGGER.log(Level.INFO, "HistoriaClinicaResource getHistoriaClinica: output: {0}", histClinDetailDTO.toString());
        return histClinDetailDTO;
    }
    
    /**
     * Elimina la historia clinica con el id asociado recibido en la URL y lo devuelve.
     * @param histClinicaId
     * @throws WebApplicationException 
     * @throws co.edu.uniandes.csw.medicinaPrepagada.exceptions.BusinessLogicException 
     */
    
    @DELETE
    @Path("{HistoriaClinicaId: \\d+}")
    public void deleteHistoriaClinica(@PathParam("historiaClinicaId") Long histClinicaId) throws BusinessLogicException
    {
        LOGGER.log(Level.INFO, "HistoriaClinicaResource deleteHistoriaClinica: input:(0)", histClinicaId);
        
        if (histLogic.getHistoriaClinica(histClinicaId) == null) 
        {
           throw new WebApplicationException("El recurso /historiaClinica/" + histClinicaId + " no existe.", 404);
        }
        
        histLogic.deleteHistoriaClinica(histClinicaId); 
        LOGGER.info("HistoriaClinicaResource deleteHistoriaClinica: output: void");
    }
    
     /**
     * Modifica la historia clinica con el id asociado recibido en la URL y lo devuelve.
     * @param histClinicaId
     * @param pHistoria
     * @return 
     * @throws co.edu.uniandes.csw.medicinaPrepagada.exceptions.BusinessLogicException 
     */
    
    @PUT
    @Path("(HistoriaClinicaId: \\d+)")
    public HistoriaClinicaDTO updateHistoriaClinica(@PathParam ("historiaClinicaId") Long histClinicaId, HistoriaClinicaDetailDTO pHistoria) throws BusinessLogicException
    {
        LOGGER.log(Level.INFO, "HistoriaClinicaResource modificarHistoriaClinica: input:(0)", histClinicaId);
        pHistoria.setId(histClinicaId);
        
        if (histLogic.getHistoriaClinica(histClinicaId) == null) 
        {
            throw new WebApplicationException("La orden medica con ese id" + histClinicaId + " no existe.", 404);
        }
        
        HistoriaClinicaDetailDTO modificarDetailDto = new HistoriaClinicaDetailDTO( histLogic.updateHistoriaClinica(histClinicaId, pHistoria.toEntity()));        
        LOGGER.log(Level.INFO,"HistoriaClinicaResource modificarHistoriaClinica: output: (0)", modificarDetailDto.toString());
        return modificarDetailDto;
    }
       
    /**
     * Convierte una lista de AuthorEntity a una lista de AuthorDetailDTO.
     *
     * @param entityList Lista de AuthorEntity a convertir.
     * @return Lista de AuthorDetailDTO convertida.
     */
    
    private List<HistoriaClinicaDTO> listEntity2DetailDTO(List<HistoriaClinicaEntity> entityList) 
    { 
        List<HistoriaClinicaDTO> list = new ArrayList<>();
        
        for(HistoriaClinicaEntity entity : entityList)
        {
            list.add(new HistoriaClinicaDetailDTO(entity)); 
        }
        
        return list;
    }
        
}
