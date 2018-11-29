/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.medicinaPrepagada.resources;

import co.edu.uniandes.csw.medicinaPrepagada.dtos.FacturaDTO;
import co.edu.uniandes.csw.medicinaPrepagada.ejb.FacturaLogic;
import co.edu.uniandes.csw.medicinaPrepagada.entities.FacturaEntity;
import co.edu.uniandes.csw.medicinaPrepagada.exceptions.BusinessLogicException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
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
@Path("/facturas")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class FacturaResource {
    
    
    private static final Logger LOGGER = Logger.getLogger(FacturaResource.class.getName());
    @Inject
    private FacturaLogic facturaLogic;
    @POST
     public FacturaDTO createFactura (FacturaDTO pFactura) throws BusinessLogicException
    {
        LOGGER.log(Level.INFO, "FacturaDTO createFactura: input: {0}", pFactura.toString());
        
        FacturaDTO nuevaFactura = new FacturaDTO(facturaLogic.createFactura(pFactura.toEntity()));
        
        LOGGER.log(Level.INFO, "FacturaDTO createFactura: output: {0}", nuevaFactura.toString());
        
        return nuevaFactura;
    }
    
    @GET
    @Path("{FacturaId:\\d+}")
    public FacturaDTO getFactura (@PathParam ("FacturaId") Long pFacturaId) throws WebApplicationException
    {
         LOGGER.log(Level.INFO, "FacturaResource getFactura: input: {0}", pFacturaId);
        FacturaEntity entity = facturaLogic.getFactura(pFacturaId);
        if(entity == null)
        {
            throw new WebApplicationException("El recurso /factura/" + pFacturaId + " no existe .", 404);
        }
        
        FacturaDTO factDTO = new FacturaDTO(entity);
        LOGGER.log(Level.INFO, "FacturaResource getFactura: output: {0}", factDTO.toString());
        return factDTO;
    }
    
    @GET
    public List<FacturaDTO> getFacturas ()
    {
        LOGGER.info("FacturaResource getFactura: input: void");
        List<FacturaDTO> listaFacturas = listEntityDTO(facturaLogic.getFacturas()) ;
        LOGGER.log(Level.INFO, "FacturaResource getFactura: output: {0}", listaFacturas.toString());
        return listaFacturas;
    }
    
    @PUT
    @Path("{FacturaId:\\d+}")
    public FacturaDTO updateFactura(FacturaDTO factura) throws BusinessLogicException, WebApplicationException
    {
        
        FacturaEntity entityAct = facturaLogic.updateFactura(factura.toEntity().getId(),factura.toEntity());
        return new FacturaDTO(entityAct);
    }
    
    
    private List<FacturaDTO> listEntityDTO(List<FacturaEntity> eList)
    {
         List<FacturaDTO> list = new ArrayList<>();
        for (FacturaEntity entity : eList) 
        {
            list.add(new FacturaDTO(entity));
        }
        return list;
    }       
    
}
