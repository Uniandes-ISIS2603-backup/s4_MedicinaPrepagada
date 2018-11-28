/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.medicinaPrepagada.resources;

import co.edu.uniandes.csw.medicinaPrepagada.dtos.UsuarioDTO;
import co.edu.uniandes.csw.medicinaPrepagada.ejb.UsuarioLogic;
import co.edu.uniandes.csw.medicinaPrepagada.exceptions.BusinessLogicException;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;

/**
 *clase que implementa el recurso de usuarios
 * @author MIGUELHOYOS
 */
@Path("usuarios")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequestScoped
public class UsuarioResource {
    
    @Inject
    UsuarioLogic usuarioLogic;
    
    @GET
    @Path("{usuarioLogin}")
    public UsuarioDTO getUsuarioByLogin(@PathParam("usuarioLogin") String login){
        try {
            return new UsuarioDTO(usuarioLogic.getUsuarioByLogin(login));
        } catch (BusinessLogicException ex) {
            throw new WebApplicationException(ex.getMessage());
        }
    }
    
}
