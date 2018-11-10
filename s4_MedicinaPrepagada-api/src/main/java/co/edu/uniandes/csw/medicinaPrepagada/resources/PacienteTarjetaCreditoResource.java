/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.medicinaPrepagada.resources;

import co.edu.uniandes.csw.medicinaPrepagada.dtos.TarjetaCreditoDTO;
import co.edu.uniandes.csw.medicinaPrepagada.ejb.PacienteLogic;
import co.edu.uniandes.csw.medicinaPrepagada.entities.TarjetaCreditoEntity;
import java.util.LinkedList;
import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author MIGUELHOYOS
 */
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("/pacientes")
public class PacienteTarjetaCreditoResource {
    
    @Inject
    private PacienteLogic pacienteLogic;
    
    /**
     * da todas las tarjetas de credito de un paciente
     * @param pacienteId: id del paciente
     * @return ina lista con todas las tarjetas de credito
     */
    @GET
    @Path("{pacienteId: \\d+}/tarjetascredito")
    public List<TarjetaCreditoDTO> getTarjetasCredito(@PathParam("pacienteId") Long pacienteId){
        List<TarjetaCreditoDTO> rta = new LinkedList<>();
        List<TarjetaCreditoEntity> lista = pacienteLogic.getTarjetasCreditoPaciente(pacienteId);
        for(TarjetaCreditoEntity ent: lista){
            rta.add(new TarjetaCreditoDTO(ent));
        }
        return rta;
    }
    

    
}
