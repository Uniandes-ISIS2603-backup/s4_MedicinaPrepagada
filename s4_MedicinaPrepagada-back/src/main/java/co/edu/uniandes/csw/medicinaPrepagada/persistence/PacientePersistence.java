/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.medicinaPrepagada.persistence;

import co.edu.uniandes.csw.medicinaPrepagada.entities.PacienteEntity;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *Clase que maneja la persistencia para Paciente. Se conecta a traves del
 * Entity Manager de javax.persistence con la base de datos SQL
 * @author MIGUELHOYOS
 */
@Stateless
public class PacientePersistence {
    
    @PersistenceContext(unitName="MedisistemasPU")
    protected EntityManager em;
    
    /**
     * Método para persisitir la entidad en la base de datos.
     * @param pacienteEntity: objeto Paciente que se creará en la base de datos
     * @return devuelve la entidad creada con un id dado por la base de datos.
     */
    public PacienteEntity create(PacienteEntity pacienteEntity){
        em.persist(pacienteEntity);
        return pacienteEntity;
    }
    
    /**
     * Devuelve todos los pacientes en la base de datos
     * @return una lista con todos los pacientes en la base de datod
     */
    public List<PacienteEntity> findAll(){
        Query q = em.createQuery("select u from PacienteEntity u");
        return q.getResultList();
    }
    
    /**
     * Busca el paciente con el id que se envia por paramentro
     * @param pacienteId: el id correspondiente al paciente buscado
     * @return un paciente
     */
    public PacienteEntity find(Long pacienteId){
        return em.find(PacienteEntity.class, pacienteId);
    }
    
    /**
     * actualiza un Paciente
     * 
     * @param pacienteEntity: la entidad con la informacion actualizada
     * @return un Paciente con los cambios realizados
     */
    public PacienteEntity update(PacienteEntity pacienteEntity){
        return em.merge(pacienteEntity);
    }
    
    public void delete(Long pacienteId){
        PacienteEntity pacienteEntity = em.find(PacienteEntity.class, pacienteId);
        em.remove(pacienteEntity);
    }
}
