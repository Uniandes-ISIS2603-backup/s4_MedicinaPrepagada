/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.medicinaPrepagada.ejb;

import co.edu.uniandes.csw.medicinaPrepagada.entities.FacturaEntity;
import co.edu.uniandes.csw.medicinaPrepagada.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.medicinaPrepagada.persistence.FacturaPersistence;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Santiago Rojas
 */
@Stateless
public class FacturaLogic {
    
    private static final Logger LOGGER = Logger.getLogger(FacturaLogic.class.getName());

    @Inject
    private FacturaPersistence facturaPersistence;

    public FacturaEntity createFactura(FacturaEntity facturaEntity) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de creación de una factura");

        if (!validateFecha(facturaEntity.getFecha())) 
        {
            throw new BusinessLogicException("La fecha no puede ser vacia o anterior a la fecha actual");
        }
        
        if (!validateValor(facturaEntity.getValor())) 
        {
            throw new BusinessLogicException("El valor no puede ser menor a 0");
        }
        
        String nameValidationPattern = "([A-Z][a-z]+ ){2,3}([A-Z][a-z]+)";
        Pattern patternName = Pattern.compile(nameValidationPattern);
        Matcher matchName = patternName.matcher(facturaEntity.getConcepto());
        if (!matchName.matches()) {
            throw new BusinessLogicException("El formato de concepto no es valida.");
        }
        if (!validarIdCliente(facturaEntity.getIdCliente())) {
            throw new BusinessLogicException("El id del cliente no es valido");
        }
        FacturaEntity newFacturaEntity = facturaPersistence.create(facturaEntity);
        LOGGER.log(Level.INFO, "Termina proceso de creación de una factura");

        return newFacturaEntity;

    }
    
    public List<FacturaEntity> getFacturas() {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todas las facturas");
        List<FacturaEntity> lista = facturaPersistence.findAll();
        LOGGER.log(Level.INFO, "Termina proceso de consultar todas las facturas");
        return lista;
    }

    public FacturaEntity getFactura(Long facturaId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar la factura con id = {0}", facturaId);
        FacturaEntity facturaEntity = facturaPersistence.find(facturaId);
        if (facturaEntity == null) {
            LOGGER.log(Level.SEVERE, "La factura con el id = {0} no existe", facturaId);
        }
        LOGGER.log(Level.INFO, "Termina proceso de consultar la citaLab con id = {0}", facturaId);
        return facturaEntity;
    }
    
    public FacturaEntity updateFactura (Long facturaId, FacturaEntity facturaEntity) throws BusinessLogicException
    {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar la factura con id = {0}", facturaId);
        
        FacturaEntity facVieja = facturaPersistence.find(facturaId);
        
        if (facVieja == null)
        {
            throw new BusinessLogicException("La factura que intenta cambiar no existe");
        }
        if (facturaEntity.getId()!=facturaId)
        {
            throw new BusinessLogicException("No puede cambiar el Id");
        }
        if (facturaEntity.getConcepto()!=facVieja.getConcepto())
        {
            throw new BusinessLogicException("No se puede cambiar el concepto");
        }
        if(facturaEntity.getIdCliente()!=facVieja.getIdCliente())
        {
            throw new BusinessLogicException("No se puede cambiar el id cliente");
        }
        if(facturaEntity.getFecha()!=facVieja.getFecha())
        {
            throw new BusinessLogicException("No se puede cambiar la fecha");
        }
        
        
        FacturaEntity newFacturaEntity = facturaPersistence.updateFactura(facturaEntity);
        LOGGER.log(Level.INFO, "Termina proceso de actualizar la cita de laboratorio con id = {0}", facturaId);
        return newFacturaEntity;
    }
    
    
    
    private boolean validateFecha(Date pFecha) {

        Date x = new Date();
        return !(pFecha == null || pFecha.before(x));
    }
    private boolean validateValor(int pValor) {

        
        return !(pValor<0);
    }
    private boolean validarIdCliente (Long pIdCliente)
    {
        return !(pIdCliente == null&& pIdCliente>0);
    }
}
