/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.medicinaPrepagada.entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 *
 * @author ni.ramirez10
 */

@Entity 
public class AdministradorEntity extends UsuarioEntity implements Serializable 
{
    //@Id
    //private Long cedula;
    

    
    /**
     * Devuelve el identificador del administrador.
     * @return El id
     */
    
    //public Long getCedula() 
    //{
    //    return cedula;
    //}

    /**
     * Modifica el identificador del administrador.
     * @param pId El id a poner
     */
 
    //public void setCedula(Long pCedula) 
    //{        
    //    this.cedula = pCedula;
    //}
}
