/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.medicinaPrepagada.entities;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 *
 * @author Daniel Ivan Romero
 */
@Entity
public class EspecialidadEntity {
    
    @Id
    private String nombre;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    
}
