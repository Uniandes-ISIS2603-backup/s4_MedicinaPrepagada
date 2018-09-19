/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.medicinaPrepagada.resources;

import co.edu.uniandes.csw.medicinaPrepagada.dtos.PacienteDTO;
import co.edu.uniandes.csw.medicinaPrepagada.dtos.PacienteDetailDTO;
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
    public PacienteDTO createPaciente(PacienteDetailDTO paciente) throws BusinessLogicException
    {
       PacienteEntity entity = paciente.toEntity();
       PacienteEntity nuevaEntity = pacienteLogic.createPaciente(entity);
       return  new PacienteDTO(nuevaEntity);
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
    public PacienteDetailDTO getPaciente(@PathParam("pacientesId") Long pacientesId){
        PacienteEntity entity = pacienteLogic.getPaciente(pacientesId);
        return new PacienteDetailDTO(entity);
    }
    
    /**
     * actualiza el paciente con la información que llega en el DTO
     * @param paciente: DTO con los datos que se quiere actualizar
     * @return JSON del paciente actualizado
     */
    @PUT
    public PacienteDTO actualizarPaciente(PacienteDetailDTO paciente) throws BusinessLogicException{
        PacienteEntity entityAct = pacienteLogic.updatePaciente(paciente.toEntity());
        return new PacienteDTO(entityAct);
    }
    
    /**
     * Retorna todos los Pacientes en el sistema
     * @return Todos los Pacientes del sistema
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
       
}
