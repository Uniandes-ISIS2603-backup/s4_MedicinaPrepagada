/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.medicinaPrepagada.dtos;

import co.edu.uniandes.csw.medicinaPrepagada.entities.AdministradorEntity;
import java.io.Serializable;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 *
 * @author ni.ramirez10
 */
public class AdministradorDTO extends UsuarioDTO implements Serializable 
{
    private Long id; 
    private String login;
    private String contrasena;
    private String tipoUsuario;
    
    /**
     * Constructor vacio
     */
    
    public AdministradorDTO()
    {
        //Constrcutor vacio
    }
    
    /**
     * Crea un objeto AdministradorDTO a partir de un objeto AdministradorEntity.
     * @param administradorEntity Entidad AdministradorEntity desde la cual se va a crear el
     * nuevo objeto.
     */
    
    public AdministradorDTO(AdministradorEntity administradorEntity) 
    {
        if (administradorEntity != null) 
        {
            this.id = administradorEntity.getId(); 
            this.login = administradorEntity.getLogin(); 
            this.contrasena = administradorEntity.getContrasena(); 
            this.tipoUsuario = administradorEntity.getTipoUsuario();             
        }
    }
     
    /**
    * Convierte un objeto AdministradorDTO a AdministradorEntity.
    * @return Nueva objeto AdministradorEntity.
    */
    
    @Override
    public AdministradorEntity toEntity() 
    {
        AdministradorEntity administradorEntity = new AdministradorEntity();
        
        administradorEntity.setId(this.getId());
        administradorEntity.setLogin(this.getLogin());
        administradorEntity.setContrasena(this.getContrasena());
        administradorEntity.setTipoUsuario(this.getTipoUsuario());
        
        return administradorEntity;
    }
    
    /**
     * Obtiene el atributo id.
     * @return atributo id.
     */
    
    public Long getId() 
    {
        return id;
    }

    /**
     * Establece el valor del atributo id.
     * @param id nuevo valor del atributo
     */
    
    public void setId(Long id) 
    {
        this.id = id;
    }
    
    /**
     * retorana el login del DTO
     * @return the login
     */
    @Override
    public String getLogin() {
        return login;
    }

    /**
     * sets el login del DTO
     * @param login the login to set
     */
    @Override
    public void setLogin(String login) {
        this.login = login;
    }

    /**
     * retorna la contrasena del DTO
     * @return the contrasena
     */
    @Override
    public String getContrasena() {
        return contrasena;
    }

    /**
     * sets la contrasena del DTO
     * @param contrasena the contrasena to set
     */
    @Override
    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    /**
     * retorna el tipoUsuario del DTO
     * @return the tipoUsuario
     */
    @Override
    public String getTipoUsuario() {
        return tipoUsuario;
    }

    /**
     * sets el tipo usario del DTO
     * @param tipoUsuario the tipoUsuario to set
     */
    @Override
    public void setTipoUsuario(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }
    
    
    @Override
    public String toString() 
    {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
