/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.medicinaPrepagada.persistence;

import co.edu.uniandes.csw.medicinaPrepagada.entities.FacturaEntity;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author Santiago Rojas
 */
@Stateless
public class FacturaPersistence {
    
    private static final Logger LOGGER = Logger.getLogger(FacturaPersistence.class.getName());
    
    @PersistenceContext(unitName = "MedisistemasPU")
    protected EntityManager em;
    
    public FacturaEntity create (FacturaEntity facturaEntity)
    {
        LOGGER.log(Level.INFO, "Creando una factura nueva");
        em.persist(facturaEntity);
        LOGGER.log(Level.INFO, "Factura ha sido creada");
        return facturaEntity;
    }
    
    public FacturaEntity find (Long facturaId)
    {
     LOGGER.log(Level.INFO,"Buscando la factura con el id=(0)",facturaId); 
     return em.find(FacturaEntity.class, facturaId);
    }
    
    public List<FacturaEntity> findAll()
    {
        LOGGER.log(Level.INFO,"Buscando todas las facturas");
        TypedQuery query = em.createQuery("select f from FacturaEntity f", FacturaEntity.class);
         
         return query.getResultList();
    }
    
}
