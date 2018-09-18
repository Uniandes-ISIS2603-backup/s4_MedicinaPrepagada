/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.medicinaPrepagada.resources;

import co.edu.uniandes.csw.medicinaPrepagada.dtos.PacienteDTO;
import co.edu.uniandes.csw.medicinaPrepagada.dtos.PacienteDetailDTO;
import co.edu.uniandes.csw.medicinaPrepagada.dtos.TarjetaCreditoDTO;
import co.edu.uniandes.csw.medicinaPrepagada.ejb.PacienteLogic;
import co.edu.uniandes.csw.medicinaPrepagada.entities.PacienteEntity;
import co.edu.uniandes.csw.medicinaPrepagada.exceptions.BusinessLogicException;
import java.util.LinkedList;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Produces;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

/**
 *Clase que implementa el recurso "pacientes"
 * @author MIGUELHOYOS
 */
@Path("pacientes")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class PacienteResource {
    
    @Inject
    PacienteLogic pacienteLogic;// Variable para acceder a la lógica de la aplicación. Es una inyección de dependencias.
    
    /**
     * Crea una nuevo paciente con la informacion que se recibe en el cuerpo de
     * la petición y se regresa un objeto identico con un id auto-generado por
     * la base de datos.
     *
     * @param paciente {@link PacienteDTO} - El pacciente que se desea
     * guardar.
     * @return JSON {@link PacienteDTO} - El paciente guardado con el atributo
     * id autogenerado.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando ya existe el paciente.
     */
    @POST
    public PacienteDTO createPaciente(PacienteDTO paciente) throws BusinessLogicException
    {
       PacienteEntity entity = paciente.toEntity();
       PacienteEntity nuevaEntity = pacienteLogic.createPaciente(entity);
       PacienteDTO nuevoDTO = new PacienteDTO(nuevaEntity);
       return nuevoDTO;
    }
    
    /**
     * Borra el paciente con el id asociado recibido en la URL.
     *
     * @param pacientessId Identificador del paciente que se desea borrar.
     * Este debe ser una cadena de dígitos.
     */
    @DELETE
    @Path("{pacientesId: \\d+}")
    public void deletePaciente(@PathParam("pacientesId") Long pacientesId){
        pacienteLogic.deletePaciente(pacientesId);
    }
    
    /**
     * Encuentra el paciante con el id asociado recibido por la URL
     * @param pacientesId Identificador del paciente que se desea encontrar
     * @return JSON del paciente buscado
     */
    @GET
    @Path("{pacientesId: \\d+}")
    public PacienteDTO getPaciente(@PathParam("pacientesId") Long pacientesId){
        PacienteEntity entity = pacienteLogic.getPaciente(pacientesId);
        return new PacienteDTO(entity);
    }
    
    /**
     * actualiza el paciente con la información que llega en el DTO
     * @param paciente: DTO con los datos que se quiere actualizar
     * @return JSON del paciente actualizado
     */
    @PUT
    public PacienteDTO actualizarPaciente(PacienteDTO paciente) throws BusinessLogicException{
        PacienteEntity entityAct = pacienteLogic.updatePaciente(paciente.toEntity());
        PacienteDTO nuevoDTO = new PacienteDTO(entityAct);
        return nuevoDTO;
    }
    
    /**
     * Retorna todos los Pacientes en el sistema
     * 
     */
    @GET
    public List<PacienteDetailDTO> getAll(){
        List<PacienteEntity> listaEntity = pacienteLogic.getPacientes();
        List<PacienteDetailDTO> rta = new LinkedList<>();
        for(PacienteEntity ent:listaEntity){
            PacienteDetailDTO  dto = new PacienteDetailDTO(ent);
            rta.add(dto);
        }
        return rta;
    }
    
//    /**
//     * Crea una tarjeta de credito al paciente con el id dado por param
//     * @param tarjetaCredito el dto de la tarjeta de credito que se quiere crear
//     * @return la tarjeta de credito creada
//     */
//    @POST
//    @Path("{pacientesId: \\d+}")
//    public TarjetaCreditoDTO crearTarjetaCredito(TarjetaCreditoDTO tarjetaCredito){
//        return tarjetaCredito;
//    }
//    
//    /**
//     * obtiene todas las tarjetas de credito de un paciente
//     * @return una lista con todos los DTOs tipo tarjetaCredito del paciente
//     */
//    @GET
//    @Path("{pacientesId: \\d+}")
//    public List<TarjetaCreditoDTO> getTarjetasCredito(@PathParam("PacientesId") Long pacientesId){
//        return new LinkedList<>();
//    }
//    
//    /**
//     * retorna una tarjeta de credito de un paciente
//     * @param pacientesId id del paciente propietario de la tarjeta
//     * @param TarjetaId id de la tarjeta buscada
//     * @return tarjeta de credito buscada
//     */
//    @GET
//    @Path("{pacientesId: \\d+}/tarjetascredito/{tarjetaId: \\d+}")
//    public TarjetaCreditoDTO getTarjetaCredito(@PathParam("PacientesId") Long pacientesId, @PathParam("tarjetaId") Long TarjetaId){
//        return new TarjetaCreditoDTO();
//    }
//    
//    /**
//     * elimina tarjeta credito de un paciente
//     * @param pacientesId id del paciente propietario de la tarjeta
//     * @param TarjetaId id de la tarjeta que se desa eliminar
//     */
//    @DELETE
//    @Path("{pacientesId: \\d+}/tarjetascredito/{tarjetaId: \\d+}")
//    public void eliminarTarjetaCredito(@PathParam("PacientesId") Long pacientesId, @PathParam("tarjetaId") Long TarjetaId){
//        
//    }
//    
}
