/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.medicinaPrepagada.resources;

import co.edu.uniandes.csw.medicinaPrepagada.dtos.TarjetaCreditoDTO;
import co.edu.uniandes.csw.medicinaPrepagada.ejb.TarjetaCreditoLogic;
import co.edu.uniandes.csw.medicinaPrepagada.entities.TarjetaCreditoEntity;
import co.edu.uniandes.csw.medicinaPrepagada.exceptions.BusinessLogicException;
import java.util.LinkedList;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;

/**
 *Clase que implementa el recurso "tarjetascredito"
 * 
 * @author MIGUELHOYOS
 */
@Path("tarjetascredito")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequestScoped
public class TarjetaCreditoResource {
    
    @Inject
    TarjetaCreditoLogic tarjetaCreditoLogic;// Variable para acceder a la lógica de la aplicación. Es una inyección de dependencias.

    /**
     * Crea una nueva tarjeta de credito con la informacion que se recibe en el cuerpo de
     * la petición y se regresa un objeto identico con un id auto-generado por
     * la base de datos.
     *
     * @param tarjetaCredito {@link TarjetaCreditoDTO} - El pacciente que se desea
     * guardar.
     * @return JSON {@link TarjetaCreditoDTO} - La tarjeta de credito guardada con el atributo
     * id autogenerado.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando ya existe el paciente.
     */
    @POST
    public TarjetaCreditoDTO createTarjetaCredito(TarjetaCreditoDTO tarjetaCredito) throws BusinessLogicException{
        TarjetaCreditoEntity entity = tarjetaCredito.toEntity();
        TarjetaCreditoEntity nuevaEntity = tarjetaCreditoLogic.createTarjetaCredito(entity);
        return new TarjetaCreditoDTO(nuevaEntity);
    }
    
    /**
     * Borra la tarjetadecredito con el id asociado recibido en la URL.
     *
     * @param tarjetascreditoId Identificador del paciente que se desea borrar.
     * Este debe ser una cadena de dígitos.
     */
    @DELETE
    @Path("{tarjetascreditoId: \\d+}")
    public void deleteTarjetaCredito(@PathParam("tarjetascreditoId") Long tarjetascreditoId)throws WebApplicationException{
        try {
            tarjetaCreditoLogic.deleteTarjetaCredito(tarjetascreditoId);
        } catch (BusinessLogicException ex) {
            throw new WebApplicationException(ex.getMessage() + "404");
        }
    }
    
     /**
     * Encuentra la tarjeta de credito con el id asociado recibido por la URL
     * @param tarjetascreditoId Identificador del paciente que se desea encontrar
     * @return JSON del paciente buscado
     */
    @GET
    @Path("{tarjetascreditoId: \\d+}")
    public TarjetaCreditoDTO getTarjetaCredito(@PathParam("tarjetascreditoId") Long tarjetascreditoId) throws WebApplicationException {
        try {
            TarjetaCreditoEntity entity = tarjetaCreditoLogic.getTarjetaCredito(tarjetascreditoId);
            return new TarjetaCreditoDTO(entity);
        } catch (BusinessLogicException ex) {
            throw new WebApplicationException(ex.getMessage() + "404");
        }
    }
    
     /**
     * Encuentra todas las tarjetas de credito
     * @return JSON del paciente buscado
     */
    @GET
    public List<TarjetaCreditoDTO> getTarjetaCredito(){
        LinkedList<TarjetaCreditoDTO> rta = new LinkedList<>();
        List<TarjetaCreditoEntity> lista = tarjetaCreditoLogic.getTarjetasDeCredito();
        for(TarjetaCreditoEntity ent : lista){
            rta.add(new TarjetaCreditoDTO(ent));
        }
        return rta;
    }

}
