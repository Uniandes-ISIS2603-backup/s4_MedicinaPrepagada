/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.medicinaPrepagada.ejb;

import co.edu.uniandes.csw.medicinaPrepagada.entities.MedicamentoEntity;
import co.edu.uniandes.csw.medicinaPrepagada.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.medicinaPrepagada.persistence.MedicamentoPersistence;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * Clase que implementa la conexion con la persistencia para la entidad de
 * Medicamento.
 *
 * @author ncobos
 */
@Stateless
public class MedicamentoLogic {
    
    private static final Logger LOGGER = Logger.getLogger(MedicamentoLogic.class.getName());

    @Inject
    private MedicamentoPersistence persistence; // Variable para acceder a la persistencia de la aplicación. Es una inyección de dependencias.

    /**
     * Crea una medicamento en la persistencia.
     *
     * @param medicamentoEntity La entidad que representa la medicamento a
     * persistir.
     * @return La entiddad de la medicamento luego de persistirla.
     * @throws BusinessLogicException Si la medicamento a persistir ya existe.
     */
    public MedicamentoEntity createMedicamento(MedicamentoEntity medicamentoEntity) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de creación de la medicamento");
        // Verifica la regla de negocio que dice que no puede haber dos medicamentos con el mismo nombre
        if (persistence.findByNombre(medicamentoEntity.getNombre()) != null) {
            throw new BusinessLogicException("Ya existe un Medicamento con el nombre \"" + medicamentoEntity.getNombre() + "\"");
        }
        validarCondiciones(medicamentoEntity);
         //Verifica que el nombre sea valido
        if (!validateString(medicamentoEntity.getNombre()))
           throw new BusinessLogicException ("El nombre debe tener letras.");
        
        // Invoca la persistencia para crear el medicamento
        persistence.create(medicamentoEntity);
        LOGGER.log(Level.INFO, "Termina proceso de creación del medicamento");
        return medicamentoEntity;
    }

    /**
     *
     * Obtener todas las medicamentos existentes en la base de datos.
     *
     * @return una lista de medicamentos.
     */
    public List<MedicamentoEntity> getMedicamentos() {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todas las medicamentos");
        // Note que, por medio de la inyección de dependencias se llama al método "findAll()" que se encuentra en la persistencia.
        List<MedicamentoEntity> medicamentos = persistence.findAll();
        LOGGER.log(Level.INFO, "Termina proceso de consultar todas las medicamentos");
        return medicamentos;
    }

    /**
     *
     * Obtener una medicamento por medio de su id.
     *
     * @param medicamentosId: id de la medicamento para ser buscada.
     * @return la medicamento solicitada por medio de su id.
     */
    public MedicamentoEntity getMedicamento(Long medicamentosId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar la medicamento con id = {0}", medicamentosId);
        // Note que, por medio de la inyección de dependencias se llama al método "find(id)" que se encuentra en la persistencia.
        MedicamentoEntity medicamentoEntity = persistence.find(medicamentosId);
        if (medicamentoEntity == null) {
            LOGGER.log(Level.SEVERE, "La medicamento con el id = {0} no existe", medicamentosId);
        }
        LOGGER.log(Level.INFO, "Termina proceso de consultar la medicamento con id = {0}", medicamentosId);
        return medicamentoEntity;
    }

    /**
     *
     * Actualizar una medicamento.
     *
     * @param medicamentosId: id de la medicamento para buscarla en la base de
     * datos.
     * @param medicamentoEntity: medicamento con los cambios para ser actualizada,
     * por ejemplo el nombre.
     * @return la medicamento con los cambios actualizados en la base de datos.
     */
    public MedicamentoEntity updateMedicamento(Long medicamentosId, MedicamentoEntity medicamentoEntity) throws BusinessLogicException{
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar la medicamento con id = {0}", medicamentosId);
        
        MedicamentoEntity pMedicamentoOld = persistence.find(medicamentosId);
        //Verifica que la medicamento que se intenta modificar exista
        if (pMedicamentoOld == null)
             throw new BusinessLogicException ("El medicamento que intenta modificar no existe");
         if (medicamentoEntity.getId()!= medicamentosId)
            throw new BusinessLogicException("No se puede cambiar el id de la medicamento");
        //Verifica que no se intente cambiar el nombre del medicamento
        if (!medicamentoEntity.getNombre().equals(pMedicamentoOld.getNombre()))
            throw new BusinessLogicException("No se puede cambiar el nombre de una medicamento");
       
        validarCondiciones(medicamentoEntity);
        
        // Note que, por medio de la inyección de dependencias se llama al método "update(entity)" que se encuentra en la persistencia.
        MedicamentoEntity newEntity = persistence.update(medicamentoEntity);
        LOGGER.log(Level.INFO, "Termina proceso de actualizar la medicamento con id = {0}", medicamentoEntity.getId());
        return newEntity;
    }

    /**
     * Borrar un medicamento
     *
     * @param medicamentosId: id de la medicamento a borrar
     * 
     */
    public void deleteMedicamento(Long medicamentosId) throws BusinessLogicException{
        LOGGER.log(Level.INFO, "Inicia proceso de borrar la medicamento con id = {0}", medicamentosId);
        // Note que, por medio de la inyección de dependencias se llama al método "delete(id)" que se encuentra en la persistencia.
        //Verifica que la medicamento que se intenta modificar exista
         MedicamentoEntity pMedicamentoOld = persistence.find(medicamentosId);
        if (pMedicamentoOld == null)
             throw new BusinessLogicException ("El medicamento que intenta modificar no existe");
        
        persistence.delete(medicamentosId);
        LOGGER.log(Level.INFO, "Termina proceso de borrar la medicamento con id = {0}", medicamentosId);
    }
    
    private boolean validateString (String pString) throws BusinessLogicException
    {
        String validationPattern = "([A-Za-z]*|([A-Za-z]+\\s))+[A-Za-z]*";
        Pattern patternNombre = Pattern.compile(validationPattern);
        Matcher matchNombre = patternNombre.matcher(pString);
        return matchNombre.matches();
    }
    
    private boolean validateCantidad(String pCantidad)
    {
        return (pCantidad.endsWith("mg")||pCantidad.endsWith("g")||pCantidad.endsWith("ml"));
    }
       
    private boolean validateCosto (Double pCosto)
    {
        return !(pCosto == null || pCosto<1000 || pCosto>10000000);
    }
    
    private void validarCondiciones (MedicamentoEntity medicamentoEntity) throws BusinessLogicException
    {
        //Verifica que el elaboradoPor sea valido
        if (!validateString(medicamentoEntity.getElaboradoPor()))
           throw new BusinessLogicException ("El elaboradoPor debe tener letras.");
        
        //Verifica que la descripción sea valida
        if (!validateString(medicamentoEntity.getDescripcion()))
           throw new BusinessLogicException ("La descripción debe tener letras.");
        
        //Verifica que la cantidad sea valida
        if (!validateCantidad(medicamentoEntity.getCantidad()))
           throw new BusinessLogicException ("La cantidad no cumple con el formato");
        
        //Verifica que el costo sea valido
        if (!validateCosto(medicamentoEntity.getCosto()))
           throw new BusinessLogicException ("El costo no puede ser vacío o tiene que estar dentro de los límites.");
    }
}
