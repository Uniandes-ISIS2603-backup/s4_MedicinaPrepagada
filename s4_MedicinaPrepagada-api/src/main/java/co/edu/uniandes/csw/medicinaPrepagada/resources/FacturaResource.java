/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.medicinaPrepagada.resources;

import co.edu.uniandes.csw.medicinaPrepagada.dtos.FacturaDTO;
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
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

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
    
    @POST
     public FacturaDTO createFactura (FacturaDTO Factura) throws BusinessLogicException
    {
        LOGGER.log(Level.INFO, "FacturaDTO createFactura: input:(0)", Factura.toString());
        
        FacturaDTO nuevaFactura = new FacturaDTO();
        
        LOGGER.log(Level.INFO, "FacturaDTO createFactura: output:(0)", nuevaFactura.toString());
        
        return nuevaFactura;
    }
    
    @GET
    @Path("(FacturaId:\\d+)")
    public FacturaDTO getFactura (@PathParam ("FacturaId") int FacturaId)
    {
         LOGGER.log(Level.INFO, "FacturaResource getFactura: input: (0)", FacturaId);
        
        FacturaDTO nuevoDetailDTO = new FacturaDTO();
        
        LOGGER.log(Level.INFO, "FacturaResource getFactura: output: {0}", nuevoDetailDTO.toString());
        return nuevoDetailDTO;
    }
    
    @GET
    public List<FacturaDTO> getFacturas ()
    {
        LOGGER.info("FacturaResource getFactura: input: void");
        List<FacturaDTO> listaFacturas = listEntityDetailDTO() ;
        LOGGER.log(Level.INFO, "FacturaResource getFactura: output: {0}", listaFacturas.toString());
        return listaFacturas;
    }
    
    
    private List<FacturaDTO> listEntityDetailDTO()
    {
        List<FacturaDTO> list = new ArrayList<>();

        return list;
    }       
    
}
