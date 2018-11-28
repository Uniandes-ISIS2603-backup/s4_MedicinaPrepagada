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
import java.util.Objects;
import java.util.logging.Logger;
import java.util.logging.Level;
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

    /**
     * Se encarga de crear un Sede en la base de datos.
     *
     * @param facturaEntity Objeto de FacturaEntity con los datos nuevos
     * @return Objeto de FacturaEntity con los datos nuevos.
     * @throws co.edu.uniandes.csw.medicinaPrepagada.exceptions.BusinessLogicException
     */
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
        
        if(!validateConcepto(facturaEntity.getConcepto()))
        {
            throw new BusinessLogicException("El concepto no puede ser vacio");
        }
        if (!validarIdCliente(facturaEntity.getIdCliente())) {
            throw new BusinessLogicException("El id del cliente no es valido");
        }
        FacturaEntity newFacturaEntity = facturaPersistence.create(facturaEntity);
        LOGGER.log(Level.INFO, "Termina proceso de creación de una factura");

        return newFacturaEntity;

    }
    
    /**
     * retorna una lista con todas las facturas
     * @return todas las facturas
     */
    public List<FacturaEntity> getFacturas() {
        return facturaPersistence.findAll();
    }

    /**
     * Busca un factura por el id
     * @param id: el id a buscar
     * @return: una factura con el id dado por param
     */
    public FacturaEntity getFactura(Long id){
        return facturaPersistence.find(id);
    }
    
    /**
     * Actualiza la información de una instancia de Laboratorio.
     *
     * @param FacturaId Identificador de la instancia a actualizar
     * @param facturaEntity Instancia de FacturaEntity con los nuevos datos.
     * @return Instancia de FacturaEntity con los datos actualizados.
     * @throws co.edu.uniandes.csw.medicinaPrepagada.exceptions.BusinessLogicException
     */
    public FacturaEntity updateFactura (Long FacturaId,FacturaEntity facturaEntity) throws BusinessLogicException
    {
        
        FacturaEntity facVieja = facturaPersistence.find(facturaEntity.getId());
        
        if (facVieja == null)
        {
            throw new BusinessLogicException("La factura que intenta cambiar no existe");
        }
        if (!facturaEntity.getConcepto().equals(facVieja.getConcepto()))
        {
            throw new BusinessLogicException("No se puede cambiar el concepto");
        }
        if(!Objects.equals(facturaEntity.getIdCliente(), facVieja.getIdCliente()))
        {
            throw new BusinessLogicException("No se puede cambiar el id cliente");
        }
        if(!facturaEntity.getFecha().equals(facVieja.getFecha()))
        {
            throw new BusinessLogicException("No se puede cambiar la fecha");
        }
        if(facturaEntity.getValor()!=(facVieja.getValor()))
        {
            throw new BusinessLogicException("No se puede cambiar el valor");
        }
        
        
        FacturaEntity newFacturaEntity = facturaPersistence.updateFactura(facturaEntity);
        LOGGER.log(Level.INFO, "Termina proceso de actualizar la cita de laboratorio con id = {0}", FacturaId);
        return newFacturaEntity;
    }
    
    
    
    private boolean validateFecha(Date pFecha) {

        Date x = new Date();
        return !(pFecha == null || pFecha.before(x));
    }
    private boolean validateValor(int pValor) {

        
        return !(pValor<0 || pValor>10000000);
    }
    private boolean validarIdCliente (Long pIdCliente)
    {
        
        return !(pIdCliente == null&& pIdCliente>0);
    }
     private boolean validateConcepto (String pConcepto)
    {
        return !(pConcepto == null || pConcepto.isEmpty());
    }
}
