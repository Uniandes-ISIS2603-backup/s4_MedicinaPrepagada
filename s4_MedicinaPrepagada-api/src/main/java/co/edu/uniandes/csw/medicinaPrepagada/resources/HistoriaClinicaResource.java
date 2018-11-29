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

@Path("historiasClinicas")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class HistoriaClinicaResource 
{
    private static final Logger LOGGER = Logger.getLogger(HistoriaClinicaResource.class.getName());
    
     @Inject
    private HistoriaClinicaLogic histLogic;
     
     private String mensaje = "no existe"; 
    
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
        LOGGER.log(Level.INFO, "HistoriaClinicaResource createHistoriaClinica: input: {0}", histClinica);
        HistoriaClinicaDTO nuevaHistClinicaDTO = new HistoriaClinicaDTO(histLogic.createHistoriaClinica(histClinica.toEntity()));
        LOGGER.log(Level.INFO, "HistoriaClinicaResource createHistoriaClinica: output: {0}", nuevaHistClinicaDTO);
        return nuevaHistClinicaDTO;
    }
    
    /**
     * Busca y devuelve todos las historias clinicas que existen en el sistema.
     * @return List - Las historias clinicas encontrados en la
     * aplicación. Si no hay ninguno retorna una lista vacía.
     */
    
    @GET
    public List<HistoriaClinicaDetailDTO> getHistoriasClinicas() 
    {
        LOGGER.info("HistoriaClinicaResource getHistoriasClinicas: input: void");
        List<HistoriaClinicaDetailDTO> listaHistoriasClinicas = listEntity2DetailDTO(histLogic.getHistoriasClinicas());
        LOGGER.log(Level.INFO, "HistoriaClinicaResource getHistoriasClinicas: output: {0}", listaHistoriasClinicas);
        return listaHistoriasClinicas;
    }
    
    /**
     * Busca la historia clinica con el id asociado recibido en la URL y lo devuelve.
     * @param histClinicaId
     * @return 
     */
    
    @GET
    @Path("{historiaClinicaId: \\d+}")
    public HistoriaClinicaDetailDTO getHistoriaClinica(@PathParam("historiaClinicaId") Long histClinicaId) throws WebApplicationException
    {
        LOGGER.log(Level.INFO, "HistoriaClinicaResource getHistoriaClinica: input: {0}", histClinicaId);
        HistoriaClinicaEntity histClinEntity = histLogic.getHistoriaClinica(histClinicaId);
        
        if (histClinEntity == null) 
        {
            throw new WebApplicationException("El recurso /historiClinica/" + histClinicaId + mensaje, 404);
        }
        
        HistoriaClinicaDetailDTO histClinDetailDTO = new HistoriaClinicaDetailDTO(histClinEntity);
        LOGGER.log(Level.INFO, "HistoriaClinicaResource getHistoriaClinica: output: {0}", histClinDetailDTO);
        return histClinDetailDTO;
    }
    
    /**
     * Elimina la historia clinica con el id asociado recibido en la URL y lo devuelve.
     * @param histClinicaId
     * @throws WebApplicationException 
     * @throws co.edu.uniandes.csw.medicinaPrepagada.exceptions.BusinessLogicException 
     */
    
    @DELETE
    @Path("{historiaClinicaId: \\d+}")
    public void deleteHistoriaClinica(@PathParam("historiaClinicaId") Long histClinicaId) throws BusinessLogicException
    {
        LOGGER.log(Level.INFO, "HistoriaClinicaResource deleteHistoriaClinica: input:(0)", histClinicaId);
        
        if (histLogic.getHistoriaClinica(histClinicaId) == null) 
        {
           throw new WebApplicationException("El recurso /historiaClinica/" + histClinicaId + mensaje, 404);
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
    @Path("{historiaClinicaId: \\d+}")
    public HistoriaClinicaDTO updateHistoriaClinica(@PathParam ("historiaClinicaId") Long histClinicaId, HistoriaClinicaDetailDTO pHistoria) throws BusinessLogicException
    {
        LOGGER.log(Level.INFO, "HistoriaClinicaResource modificarHistoriaClinica: input:(0)", histClinicaId);
        HistoriaClinicaEntity hist = histLogic.getHistoriaClinica(histClinicaId);
        if(hist == null){
            throw new WebApplicationException("No existe la historia clinica", 404);
        }
        pHistoria.setId(histClinicaId);
        HistoriaClinicaDetailDTO modificarDetailDto = new HistoriaClinicaDetailDTO( histLogic.updateHistoriaClinica(histClinicaId, pHistoria.toEntity()));        
        LOGGER.log(Level.INFO,"HistoriaClinicaResource modificarHistoriaClinica: output: (0)", modificarDetailDto);
        return modificarDetailDto;
    }
       
    /**
     * Convierte una lista de AuthorEntity a una lista de AuthorDetailDTO.
     *
     * @param entityList Lista de AuthorEntity a convertir.
     * @return Lista de AuthorDetailDTO convertida.
     */
    
    private List<HistoriaClinicaDetailDTO> listEntity2DetailDTO(List<HistoriaClinicaEntity> entityList) 
    { 
        List<HistoriaClinicaDetailDTO> list = new ArrayList<>();
        
        for(HistoriaClinicaEntity entity : entityList)
        {
            list.add(new HistoriaClinicaDetailDTO(entity)); 
        }
        
        return list;
    }
        
}
