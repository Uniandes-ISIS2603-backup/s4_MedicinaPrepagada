/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.medicinaPrepagada.persistence;

import co.edu.uniandes.csw.medicinaPrepagada.entities.UsuarioEntity;
import co.edu.uniandes.csw.medicinaPrepagada.exceptions.BusinessLogicException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 *clase que manej ala persistencia de usuario
 * @author MIGUELHOYOS
 */
@Stateless
public class UsuarioPersistence {
    
    @PersistenceContext(unitName="MedisistemasPU")
    protected EntityManager em;
    
    /**
     * 
     */
    public UsuarioEntity getUsuarioByLogin(String login) throws BusinessLogicException{
        UsuarioEntity rta = null;
        TypedQuery q = em.createQuery("Select e from UsuarioEntity e where e.login = :login", UsuarioEntity.class);
        q.setParameter("login", login);
        try{
            return (UsuarioEntity) q.getSingleResult();
        }
        catch(NoResultException e2){
            throw new BusinessLogicException("El usuario no existe");
        }
    }
    
}
