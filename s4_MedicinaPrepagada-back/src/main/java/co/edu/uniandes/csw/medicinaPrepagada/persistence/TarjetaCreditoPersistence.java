/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.medicinaPrepagada.persistence;


import co.edu.uniandes.csw.medicinaPrepagada.entities.TarjetaCreditoEntity;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *Clase que maneja la persistencia para TarjetaCredito. Se conecta a traves del
 * Entity Manager de javax.persistence con la base de datos SQL
 * @author MIGUELHOYOS
 */
@Stateless
public class TarjetaCreditoPersistence {
 
    @PersistenceContext(unitName = "MedisistemasPU")
    protected EntityManager em;
    
    /**
     * Crea una Tarjeta de credito en la base de datos
     * 
     * @param tarjetaCreditoEntity: objeto tipo TarjetaCredito que se desea crear en la base de datos
     * @return devuelve la entidad creada
     */
    public TarjetaCreditoEntity create(TarjetaCreditoEntity tarjetaCreditoEntity){
        em.persist(tarjetaCreditoEntity);
        return tarjetaCreditoEntity;
    }
    
    /**
     * Devuelve todas las tarjetas de la base de datos
     * 
     * @return una lista con todas las tarjetas de credito en la base de datos
     */
    public List<TarjetaCreditoEntity> findAll(){
        Query q = em.createQuery("select u from TarjetaCreditoEntity u");
        return q.getResultList();
    }
    
    /**
     * Busca una atrjeta de credito con el id que llega por parametro
     * @param tarjetaCreditoId: id correspondiente a la tarjeta de credito buscada
     * @return una tarjeta de credito
     */
    public TarjetaCreditoEntity find(Long tarjetaCreditoId){
        return em.find(TarjetaCreditoEntity.class, tarjetaCreditoId);
    }
    
    /**
     * Actualiza una tarjeta de credito
     * @param tarjetaCreditoEntity: la tarjeta de credito que que viene con los cambios.
     * @return una tarjeta de cerdito con los cambios aplicados
     */
    public TarjetaCreditoEntity update(TarjetaCreditoEntity tarjetaCreditoEntity){
       return em.merge(tarjetaCreditoEntity);
    }
    
    /**
     * Borra una tarjeta de cerdito de la base de datos recibiendo como argumento el id de la tarjeta de credito
     * @param tarjetaCreditoId : id correspondiente a la tarjeta de credito a borar
     */
    public void delete(Long tarjetaCreditoId){
        TarjetaCreditoEntity entity = em.find(TarjetaCreditoEntity.class, tarjetaCreditoId);
        em.remove(entity);
    }
    
    /**
     * le asocia un paciente a una tarjeta de credito
     * @param pacienteId: id del paciente
     * @param numero: numero de la tarjeta
     */
    public void setPacienteToTarjeta(Long pacienteId, Long numero){
        Query q = em.createQuery("UPDATE TARJETACREDITOENTITY SET PACIENTE_ID = pid WHERE NUMERO = tnum");
        q.setParameter("pid", pacienteId);
        q.setParameter("tnum", numero);
        q.executeUpdate();
    }
    
}
