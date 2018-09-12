/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.medicinaPrepagada.persistence;


import co.edu.uniandes.csw.medicinaPrepagada.entities.CitaMedicaEntity;
import co.edu.uniandes.csw.medicinaPrepagada.entities.MedicoEntity;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 *
 * @author Daniel Ivan Romero
 */
@Stateless
public class MedicoPersistence {
    private static final Logger LOGGER = Logger.getLogger(MedicoPersistence.class.getName());
    
    @PersistenceContext(unitName = "MedisistemasPU")
    protected EntityManager em;
    
     /**
     * Método para persisitir la entidad en la base de datos.
     * @param MedicoEntity objeto Medico que se creará en la base de datos
     * @return devuelve la entidad creada con un id dado por la base de datos.
     */
    
    public MedicoEntity create(MedicoEntity MedicoEntity) 
    {
        LOGGER.log(Level.INFO, "Creando un nuevo Medico");
        em.persist(MedicoEntity);
        LOGGER.log(Level.INFO, "Medico creado");
        return MedicoEntity;
    }
    
    /**
     * Devuelve todos los Medicoes de la base de datos.
     * @return una lista con todos los Medicoes que encuentre en la base de datos,
     */
    
    public List<MedicoEntity> findAll() 
    {
        LOGGER.log(Level.INFO, "Consultando todos los Medicoes");
        Query q = em.createQuery("select u from MedicoEntity u", MedicoEntity.class);
        return q.getResultList();
    }
    
    /**
     * Busca el Medico con el id que se envía de parametro
     * @param medicoId: id correspondiente al Medico buscado.
     * @return un Medico. 
     */
    
    public MedicoEntity find(Long medicoId) 
    {
        LOGGER.log(Level.INFO, "Consultando el Medico con id={0}", medicoId);
        return em.find(MedicoEntity.class, medicoId);
    }
    
    public MedicoEntity findByDocumento(int medicoDocumento)
    {
        LOGGER.log(Level.INFO, "Consultando medico por documento ", medicoDocumento);
        TypedQuery query = em.createQuery("Select e From MedicoEntity e where e.documentoMedico >= :medicoDocumento", MedicoEntity.class);
        query.setParameter("medicoDocumento", medicoDocumento);
        List<MedicoEntity> respuesta = query.getResultList();
        if(respuesta.isEmpty()){
            return null;
        }
        return respuesta.get(0);
    }
    /**
     * Actualiza un Medico.
     * @param medicoEntity: el Medico que viene con nueva informacion. 
     * @return un Medico con los cambios aplicados.
     */
    
    public MedicoEntity update(MedicoEntity medicoEntity) 
    {
        LOGGER.log(Level.INFO, "Actualizando el Medico con id={0}", medicoEntity.getId());
        return em.merge(medicoEntity);
    }
    
    /**
     * Borra un Medico de la base de datos con el id que entra por parametro
     * @param medicoId: id correspondiente al mediconsitrador a borrar.
     */
    
    public void delete(Long medicoId) 
    {
        LOGGER.log(Level.INFO, "Borrando el Medico con id={0}", medicoId);
        MedicoEntity medicoEntity = em.find(MedicoEntity.class, medicoId);
        em.remove(medicoEntity);
    }
    
}
