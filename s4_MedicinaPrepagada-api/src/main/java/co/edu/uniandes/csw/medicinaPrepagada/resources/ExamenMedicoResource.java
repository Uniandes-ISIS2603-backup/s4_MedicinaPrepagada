/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.medicinaPrepagada.resources;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import co.edu.uniandes.csw.medicinaPrepagada.dtos.ExamenMedicoDTO;
import co.edu.uniandes.csw.medicinaPrepagada.dtos.MedicamentoDTO;
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
import javax.ws.rs.PathParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;

/**
 * Clase que implementa el recurso "examenesMedicos".
 *
 * @author ISIS2603
 * @version 1.0
 */
@Path("examenesMedicos")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class ExamenMedicoResource {
    private static final Logger LOGGER = Logger.getLogger(ExamenMedicoResource.class.getName());

    @Inject
  //  ExamenMedicoLogic examenMedicoLogic; // Variable para acceder a la lógica de la aplicación. Es una inyección de dependencias.

    /**
     * Crea un nuevo examen medico con la informacion que se recibe en el cuerpo de
     * la petición y se regresa un objeto identico con un id auto-generado por
     * la base de datos.
     *
     * @param examenMedico {@link ExamenMedicoDTO} - El ExamenMedico que se desea
     * guardar.
     * @return JSON {@link ExamenMedicoDTO} - El ExamenMedico guardado con el atributo
     * id autogenerado.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando ya existe el ExamenMedico.
     */
    @POST
    public ExamenMedicoDTO createExamenMedicoDTO(ExamenMedicoDTO examenMedico) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "ExamenMedicoResource createExamenMedico: input: {0}", examenMedico.toString());
        // Convierte el DTO (json) en un objeto Entity para ser manejado por la lógica.
       // ExamenMedicoEntity examenMedicoEntity = examenMedico.toEntity();
        // Invoca la lógica para crear el nuevo ExamenMedico
       // ExamenMedicoEntity nuevoExamenMedicoEntity = examenMedicoLogic.createExamenMedico(examenMedicoEntity);
        // Como debe retornar un DTO (json) se invoca el constructor del DTO con argumento el entity nuevo
       // ExamenMedicoDTO nuevoExamenMedicoDTO = new ExamenMedicoDTO(nuevoExamenMedicoEntity);
     //   LOGGER.log(Level.INFO, "ExamenMedicoResource createExamenMedico: output: {0}", nuevoExamenMedicoDTO.toString());
      //  return nuevoExamenMedicoDTO;
      return new ExamenMedicoDTO();
    }
    
     @GET
    public ExamenMedicoDTO consultarExamenMedico(){
        return new ExamenMedicoDTO();
    }
    /**
     * Busca y devuelve todos los examenes medicos que existen en la aplicacion.
     *
     * @return JSONArray {@link ExamenMedicoDTO} - Los examenes medicos encontrados en
     * la aplicación. Si no hay ninguno retorna una lista vacía.
     */
  //  @GET
  //  public List<ExamenMedicoDTO> getExamenesMedicos() {
    //    LOGGER.info("ExamenMedicoResource getExamenesMedicos: input: void");
      //  List<ExamenMedicoDTO> listaExamenesMedicos = listEntity2DetailDTO(examenMedicoLogic.getExamenesMedicos());
     //   LOGGER.log(Level.INFO, "ExamenMedicoResource getExamenesMedicos: output: {0}", listaExamenesMedicos.toString());
      //  return listaExamenesMedicos;
   // }

    /**
     * Busca el ExamenMedico con el id asociado recibido en la URL y lo devuelve.
     *
     * @param examenesMedicosId Identificador del ExamenMedico que se esta buscando.
     * Este debe ser una cadena de dígitos.
     * @return JSON {@link ExamenMedicoDTO} - El ExamenMedico buscado
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el ExamenMedico.
     */
    //@GET
   // @Path("{examenesMedicosId: \\d+}")
  //  public ExamenMedicoDTO getExamenMedico(@PathParam("examenesMedicosId") Long examenesMedicosId) throws WebApplicationException {
     //   LOGGER.log(Level.INFO, "ExamenMedicoResource getExamenMedico: input: {0}", examenesMedicosId);
       // ExamenMedicoEntity examenMedicoEntity = examenMedicoLogic.getExamenMedico(examenesMedicosId);
     //   if (examenMedicoEntity == null) {
   //         throw new WebApplicationException("El recurso /examenesMedicos/" + examenesMedicosId + " no existe.", 404);
 //       }
    //    ExamenMedicoDTO detailDTO = new ExamenMedicoDTO(examenMedicoEntity);
   //     LOGGER.log(Level.INFO, "ExamenMedicoResource getExamenMedico: output: {0}", detailDTO.toString());
    //    return detailDTO;
   // }

    /**
     * Actualiza el ExamenMedico con el id recibido en la URL con la informacion
     * que se recibe en el cuerpo de la petición.
     *
     * @param examenesMedicosId Identificador del ExamenMedico que se desea
     * actualizar. Este debe ser una cadena de dígitos.
     * @param examenMedico {@link ExamenMedicoDTO} El ExamenMedico que se desea guardar.
     * @return JSON {@link ExamenMedicoDTO} - El ExamenMedico guardado.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el ExamenMedico a
     * actualizar.
     */
    //@PUT
   // @Path("{examenesMedicosId: \\d+}")
 //   public ExamenMedicoDTO updateExamenMedico(@PathParam("examenesMedicosId") Long examenesMedicosId, ExamenMedicoDTO examenMedico) throws WebApplicationException {
      //  LOGGER.log(Level.INFO, "ExamenMedicoResource updateExamenMedico: input: id:{0} , editorial: {1}", new Object[]{examenesMedicosId, examenMedico.toString()});
     //   examenMedico.setId(examenesMedicosId);
     //   if (examenMedicoLogic.getExamenMedico(examenesMedicosId) == null) {
   //         throw new WebApplicationException("El recurso /examenesMedicos/" + examenesMedicosId + " no existe.", 404);
    //    }
    //    ExamenMedicoDTO detailDTO = new ExamenMedicoDTO(examenMedicoLogic.updateExamenMedico(examenesMedicosId, examenMedico.toEntity()));
    //    LOGGER.log(Level.INFO, "ExamenMedicoResource updateExamenMedico: output: {0}", detailDTO.toString());
   //     return detailDTO;
    }

    /**
     * Borra el ExamenMedico con el id asociado recibido en la URL.
     *
     * @param examenesMedicosId Identificador del ExamenMedico que se desea borrar.
     * Este debe ser una cadena de dígitos.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando no se puede eliminar el ExamenMedico.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el ExamenMedico.
     */
   // @DELETE
   // @Path("{examenesMedicosId: \\d+}")
   // public void deleteExamenMedico(@PathParam("examenesMedicosId") Long examenesMedicosId) throws BusinessLogicException {
     //   LOGGER.log(Level.INFO, "ExamenMedicoResource deleteExamenMedico: input: {0}", examenesMedicosId);
      //  if (examenMedicoLogic.getExamenMedico(examenesMedicosId) == null) {
        //    throw new WebApplicationException("El recurso /examenesMedicos/" + examenesMedicosId + " no existe.", 404);
       // }
       // examenMedicoLogic.deleteExamenMedico(examenesMedicosId);
       // LOGGER.info("ExamenMedicoResource deleteExamenMedico: output: void");
   // }

    /**
     * Convierte una lista de entidades a DTO.
     *
     * Este método convierte una lista de objetos EditorialEntity a una lista de
     * objetos EditorialDTO (json)
     *
     * @param entityList corresponde a la lista de editoriales de tipo Entity
     * que vamos a convertir a DTO.
     * @return la lista de editoriales en forma DTO (json)
     */
    //private List<EditorialDTO> listEntity2DetailDTO(List<EditorialEntity> entityList) {
      //  List<EditorialDTO> list = new ArrayList<>();
       // for (EditorialEntity entity : entityList) {
         //   list.add(new EditorialDTO(entity));
        //}
        //return list;
    //}


