/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.medicinaPrepagada.resources;

import co.edu.uniandes.csw.medicinaPrepagada.dtos.AdministradorDTO;
import co.edu.uniandes.csw.medicinaPrepagada.dtos.AdministradorDetailDTO;
import co.edu.uniandes.csw.medicinaPrepagada.ejb.AdministradorLogic;
import co.edu.uniandes.csw.medicinaPrepagada.entities.AdministradorEntity;
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

@Path("administradores")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class AdministradorResource 
{
    private static final Logger LOGGER = Logger.getLogger(AdministradorResource.class.getName());
    
    @Inject
    private AdministradorLogic admiLogic;
    
    private final String mensaje = "no existe"; 
    
    /**
     * Crea un nuevo administrador con la informacion que se recibe en el cuerpo de la
     * petición y se regresa un objeto identico.
     * @param admi
     * @return 
     * @throws BusinessLogicException
     */
    
    @POST
    public AdministradorDTO createAdministrador(AdministradorDTO admi) throws BusinessLogicException 
    {
        LOGGER.log(Level.INFO, "AdministradorResource createAdministrador: input: {0}", admi);
        AdministradorDTO nuevaAdmiDTO = new AdministradorDTO(admiLogic.createAdministrador(admi.toEntity())); 
        LOGGER.log(Level.INFO, "AdministradorResource createAdministrador: output: {0}", nuevaAdmiDTO);
        return nuevaAdmiDTO;
    }
    
    /**
     * Busca y devuelve todos los adminsitradores que existen en el sistema.
     * @return List - Los administradores encontrados en la
     * aplicación. Si no hay ninguno retorna una lista vacía.
     */
    
    @GET
    public List<AdministradorDTO> getAdministradores() 
    {
        LOGGER.info("AdministradorResource getAdministradores: input: void");
        List<AdministradorDTO> listaAdministradores = listEntity2DetailDTO(admiLogic.getAdministradores());
        LOGGER.log(Level.INFO, "AdministradorResource getAdministradores: output: {0}", listaAdministradores);
        return listaAdministradores;
    }
    
    /**
     * Busca el administrador con el id asociado recibido en la URL y lo devuelve.
     * @param administradorId
     * @return 
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el adminsitrador.
     */
    
    @GET
    @Path("{administradorId: \\d+}")
    public AdministradorDetailDTO getAdministrador(@PathParam("administradorId") Long administradorId) 
    {
        LOGGER.log(Level.INFO, "AdministradorResource getAdministrador: input: {0}", administradorId);
        AdministradorEntity admiEntity = admiLogic.getAdministrador(administradorId);
        
        if (admiEntity == null) 
        {
            throw new WebApplicationException("El recurso /administrador/" + administradorId + mensaje, 404);
        }
        
        LOGGER.log(Level.INFO, "AdministradorResource getAdministrador: output: {0}", administradorId);
        return new AdministradorDetailDTO(admiEntity);
    }
    
    /**
     * Elimina el administrador con el id asociado recibido en la URL y lo devuelve.
     * @param administradorId
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el adminsitrador.
     */
    
    @DELETE
    @Path("{administradorId: \\d+}")
    public void deleteAdministrador(@PathParam ("administradorId") Long administradorId)
    {
        LOGGER.log(Level.INFO, "AdministradorResource deleteAdministrador: input:(0)", administradorId);
        
        if (admiLogic.getAdministrador(administradorId) == null) 
        {
           throw new WebApplicationException("El recurso /administradores/" + administradorId + mensaje, 404);
        }
        
        admiLogic.deleteAdministrador(administradorId); 
        LOGGER.info("AdministradorResource deleteAdministrador: output: void");
    }
    
    /**
     * Modifica el administrador con el id asociado recibido en la URL y lo devuelve.
     * @param administradorId
     * @param pAdmi
     * @return 
     * @throws co.edu.uniandes.csw.medicinaPrepagada.exceptions.BusinessLogicException 
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el administrador.
     */
    
    @PUT
    @Path("{administradorId: \\d+}")
    public AdministradorDTO updateAdministrador(@PathParam ("administradorId") Long administradorId, AdministradorDTO pAdmi) throws BusinessLogicException
    {
        LOGGER.log(Level.INFO, "AdministradorResource modificarAdministrador: input:(0),  administrador: {1}", new Object[]{administradorId, pAdmi.toString()});
        pAdmi.setId(administradorId);
        
        if (admiLogic.getAdministrador(administradorId) == null)
        {
            throw new WebApplicationException("El administrador con id " + administradorId + mensaje, 404);
        }

        AdministradorDTO detailDTO = new AdministradorDTO(admiLogic.updateAdministrador(administradorId, pAdmi.toEntity()));
        LOGGER.log(Level.INFO, "AdministradorResource updateAdministrador: output: {0}", detailDTO);
        return detailDTO;

    }
    
    private List<AdministradorDTO> listEntity2DetailDTO(List<AdministradorEntity> entityList) 
    { 
        List<AdministradorDTO> list = new ArrayList<>();
        
        for(AdministradorEntity entity : entityList) 
        {
            list.add(new AdministradorDetailDTO(entity));
        }
        
        return list;
    }
}
