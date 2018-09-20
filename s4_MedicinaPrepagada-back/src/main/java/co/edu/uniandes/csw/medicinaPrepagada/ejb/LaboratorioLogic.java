/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.medicinaPrepagada.ejb;

import co.edu.uniandes.csw.medicinaPrepagada.entities.LaboratorioEntity;
import co.edu.uniandes.csw.medicinaPrepagada.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.medicinaPrepagada.persistence.LaboratorioPersistence;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Santiago Rojas
 */
@Stateless
public class LaboratorioLogic {
    
    private static final Logger LOGGER = Logger.getLogger(LaboratorioLogic.class.getName());
    
    @Inject
    private LaboratorioPersistence labPersistence;
    
    public LaboratorioEntity createLaboratorio (LaboratorioEntity labEntity) throws BusinessLogicException
    {
        LOGGER.log(Level.INFO, "Inicia proceso de creación del  laboratorio");
        
        if (!validateNombre(labEntity.getNombre()))
           throw new BusinessLogicException ("EL nombre no puede ser vacio ");
       
        if(!validateNumeroTelefono(labEntity.getTelefono()))
        {
            throw new BusinessLogicException("El telefono no puede ser vacio o debe ser mayor a 6 digitos.");
        }
        if(!validateLatitud(labEntity.getLatitud()))
        {
            throw new BusinessLogicException("La latitud dada no se encuentra en Colombia");
        }
        if(!validateLongitud(labEntity.getLongitud()))
        {
            throw new BusinessLogicException("La longitud dada no se encuentra en Colombia");
        }
        
        LaboratorioEntity newLabEntity = labPersistence.create(labEntity);
        LOGGER.log(Level.INFO, "Termina proceso de creación del laboratorio");

        return newLabEntity;
    }
    
    public List<LaboratorioEntity> getLabs() {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todos los laboratorios");
        List<LaboratorioEntity> lista = labPersistence.findAll();
        LOGGER.log(Level.INFO, "Termina proceso de consultar todas los laboratorios");
        return lista;
    }
    
    public LaboratorioEntity getLab(Long citaId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar el laboratorio con id = {0}", citaId);
        LaboratorioEntity labEntity = labPersistence.find(citaId);
        if (labEntity == null) {
            LOGGER.log(Level.SEVERE, "La citaLab con el id = {0} no existe", citaId);
        }
        LOGGER.log(Level.INFO, "Termina proceso de consultar la citaLab con id = {0}", citaId);
        return labEntity;
    }
    
    public LaboratorioEntity updateLaboratorio (Long labId, LaboratorioEntity labEntity) throws BusinessLogicException
    {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar el laboratorio con id = {0}", labId);
        
        LaboratorioEntity labViejo = labPersistence.find(labId);
        
        if (labViejo == null)
        {
            throw new BusinessLogicException("El laboratorio que intenta cambiar no existe");
        }
        if (labEntity.getId()!=labId)
        {
            throw new BusinessLogicException("No puede cambiar el Id");
        }
        if (labEntity.getLongitud() != labViejo.getLongitud())
            throw new BusinessLogicException("No se puede cambiar la longitud de un laboratorio");
        
        if (labEntity.getLatitud() != labViejo.getLatitud())
            throw new BusinessLogicException ("No se puede cambiar la latitud de un laboratorio");
        if (!validateNumeroTelefono(labEntity.getTelefono()))
        {
            throw new BusinessLogicException("El numero no puede ser vacio ni menor a 6 digitos");
        }
        
        if (!validateNombre(labEntity.getNombre()))
           throw new BusinessLogicException ("EL nombre no puede ser vacio");
        
        
        LaboratorioEntity newLabEntity = labPersistence.update(labEntity);
        LOGGER.log(Level.INFO, "Termina proceso de actualizar la cita de laboratorio con id = {0}", labId);
        return newLabEntity;
    }
    
    public void deleteLaboratorio(Long labId)
    {
        labPersistence.delete(labId);
    }
    private boolean validateNumeroTelefono(Long pNumero)
    {
        return !(pNumero == null || pNumero<1000000 );
    }
    
         private boolean validateNombre (String pNombre)
    {
        return !(pNombre == null || pNombre.isEmpty());
    }
    
    private boolean validateLongitud (Double pLongitud)
    {
        return (pLongitud !=null && pLongitud>= -79.374594 && pLongitud <= -66.853233 );
    }
    
     
    private boolean validateLatitud (Double pLatitud)
    {
        return (pLatitud !=null && pLatitud>= -4.223596 && pLatitud <= 12.514801 );
    }
}
