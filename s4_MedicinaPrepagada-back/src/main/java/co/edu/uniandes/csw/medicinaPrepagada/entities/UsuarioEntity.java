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
 *clase que representa un usuario
 * @author MIGUELHOYOS
 */
@Entity
public class UsuarioEntity extends BaseEntity implements Serializable{


    
    
    private Long documentoIdentidad;
    private String login;
    private String contrasena;
    private String tipoUsuario;
    


    /**
     * @return the login
     */
    public String getLogin() {
        return login;
    }

    /**
     * @param login the login to set
     */
    public void setLogin(String login) {
        this.login = login;
    }

    /**
     * @return the contrasena
     */
    public String getContrasena() {
        return contrasena;
    }

    /**
     * @param contrasena the contrasena to set
     */
    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    /**
     * @return the tipoUsuario
     */
    public String getTipoUsuario() {
        return tipoUsuario;
    }

    /**
     * @param tipoUsuario the tipoUsuario to set
     */
    public void setTipoUsuario(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    /**
     * @return the documentoIdentidad
     */
    public Long getDocumentoIdentidad() {
        return documentoIdentidad;
    }

    /**
     * @param documentoIdentidad the documentoIdentidad to set
     */
    public void setDocumentoIdentidad(Long documentoIdentidad) {
        this.documentoIdentidad = documentoIdentidad;
    }
}
