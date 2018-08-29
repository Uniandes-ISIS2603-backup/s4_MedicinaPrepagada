/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.medicinaPrepagada.resources;

import co.edu.uniandes.csw.medicinaPrepagada.dtos.MedicamentoDTO;
import co.edu.uniandes.csw.medicinaPrepagada.dtos.MedicamentoDetailDTO;
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
 * Clase que implementa el recurso "medicamentos".
 *
 * @author n.cobos
 * @version 1.0
 */
@Path("medicamentos")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class MedicamentoResource {
    
    private static final Logger LOGGER = Logger.getLogger(MedicamentoResource.class.getName());

  //  @Inject
  //  MedicamentoLogic medicamentoLogic; // Variable para acceder a la lógica de la aplicación. Es una inyección de dependencias.

    /**
     * Crea un nuevo medicamento con la informacion que se recibe en el cuerpo de
     * la petición y se regresa un objeto identico con un id auto-generado por
     * la base de datos.
     *
     * @param medicamento {@link MedicamentoDTO} - El medicamento que se desea
     * guardar.
     * @return JSON {@link MedicamentoDTO} - El medicamento guardado con el atributo
     * id autogenerado.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando ya existe el medicamento.
     */
    @POST
    public MedicamentoDTO createMedicamento(MedicamentoDTO pMedicamento) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "MedicamentoResource createMedicamento: input: {0}", pMedicamento.toString());
        // Convierte el DTO (json) en un objeto Entity para ser manejado por la lógica.
       // MedicamentoEntity medicamentoEntity = medicamento.toEntity();
        // Invoca la lógica para crear el nuevo medicamento
       // MedicamentoEntity nuevoMedicamentoEntity = medicamentoLogic.createMedicamento(medicamentoEntity);
        // Como debe retornar un DTO (json) se invoca el constructor del DTO con argumento el entity nuevo
        MedicamentoDTO nuevoMedicamentoDTO = new MedicamentoDTO();
        LOGGER.log(Level.INFO, "MedicamentoResource createMedicamento: output: {0}", nuevoMedicamentoDTO.toString());
        return pMedicamento;
    }
    
 //    @GET
 //   public MedicamentoDTO consultarMedicamento(){
 //       return new MedicamentoDTO();
 //   }
    /**
     * Busca y devuelve todos los medicamentos que existen en la aplicacion.
     *
     * @return JSONArray {@link MedicamentoDTO} - Los medicamentos encontrados en
     * la aplicación. Si no hay ninguno retorna una lista vacía.
     */
    @GET
    public List<MedicamentoDTO> getMedicamentos() {
        LOGGER.info("MedicamentoResource getMedicamentos: input: void");
        List<MedicamentoDTO> listaMedicamentos = listEntity2DetailDTO();
        LOGGER.log(Level.INFO, "MedicamentoResource getMedicamentos: output: {0}", listaMedicamentos.toString());
        return listaMedicamentos;
   }

    /**
     * Busca el medicamento con el id asociado recibido en la URL y lo devuelve.
     *
     * @param medicamentosId Identificador del medicamento que se esta buscando.
     * Este debe ser una cadena de dígitos.
     * @return JSON {@link MedicamentoDTO} - El medicamento buscado
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el medicamento.
     */
    @GET
    @Path("{medicamentosId: \\d+}")
    public MedicamentoDTO getMedicamento(@PathParam("medicamentosId") Long pMedicamentosId) throws WebApplicationException 
    {
        LOGGER.log(Level.INFO, "MedicamentoResource getMedicamento: input: {0}", pMedicamentosId);
       // MedicamentoEntity medicamentoEntity = medicamentoLogic.getMedicamento(medicamentosId);
     //   if (medicamentoEntity == null) {
   //         throw new WebApplicationException("El recurso /medicamentos/" + medicamentosId + " no existe.", 404);
 //       }
        MedicamentoDetailDTO detailDTO = new MedicamentoDetailDTO();
        LOGGER.log(Level.INFO, "MedicamentoResource getMedicamento: output: {0}", detailDTO.toString());
        return detailDTO;
    }

    /**
     * Actualiza el medicamento con el id recibido en la URL con la informacion
     * que se recibe en el cuerpo de la petición.
     *
     * @param medicamentosId Identificador del medicamento que se desea
     * actualizar. Este debe ser una cadena de dígitos.
     * @param medicamento {@link MedicamentoDTO} El medicamento que se desea guardar.
     * @return JSON {@link MedicamentoDTO} - El medicamento guardado.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el medicamento a
     * actualizar.
     */
    @PUT
    @Path("{medicamentosId: \\d+}")
   public MedicamentoDTO updateMedicamento(@PathParam("medicamentosId") Long medicamentosId, MedicamentoDetailDTO pMedicamento) throws WebApplicationException {
        LOGGER.log(Level.INFO, "MedicamentoResource updateMedicamento: input: id:{0} , medicamento: {1}", new Object[]{medicamentosId, pMedicamento.toString()});
        pMedicamento.setId(medicamentosId);
     //   if (medicamentoLogic.getMedicamento(medicamentosId) == null) {
   //         throw new WebApplicationException("El recurso /medicamentos/" + medicamentosId + " no existe.", 404);
    //    }
        MedicamentoDetailDTO detailDTO = new MedicamentoDetailDTO();
        LOGGER.log(Level.INFO, "MedicamentoResource updateMedicamento: output: {0}", detailDTO.toString());
        return detailDTO;
   }

    /**
     * Borra el medicamento con el id asociado recibido en la URL.
     *
     * @param medicamentosId Identificador del medicamento que se desea borrar.
     * Este debe ser una cadena de dígitos.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando no se puede eliminar el medicamento.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el medicamento.
     */
    @DELETE
    @Path("{medicamentosId: \\d+}")
    public void deleteMedicamento(@PathParam("medicamentosId") Long medicamentosId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "MedicamentoResource deleteMedicamento: input: {0}", medicamentosId);
      //  if (medicamentoLogic.getMedicamento(medicamentosId) == null) {
        //    throw new WebApplicationException("El recurso /medicamentos/" + medicamentosId + " no existe.", 404);
       // }
       // medicamentoLogic.deleteMedicamento(medicamentosId);
        LOGGER.info("MedicamentoResource deleteMedicamento: output: void");
    }

    
 /**
     * Convierte una lista de entidades a DTO.
     *
     * Este método convierte una lista de objetos SedesEntitya una lista de
     * objetos SedeDTO (json)
     *
     * @param entityList corresponde a la lista de sede de tipo Entity
     * que vamos a convertir a DTO.
     * @return la lista de sede en forma DTO (json)
     */
    private List<MedicamentoDTO> listEntity2DetailDTO() 
    {
        List<MedicamentoDTO> list = new ArrayList<>();
     //   for (SedeEntity entity : entityList) {
     //       list.add(new SedeDetail(entity));
     //   }
        return list;
    }
}

