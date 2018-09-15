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
public class AdministradorDTO implements Serializable 
{
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
            this.login = administradorEntity.getLogin(); 
            this.contrasena = administradorEntity.getContrasena(); 
            this.tipoUsuario = administradorEntity.getTipoUsuario();             
        }
    }
     
    /**
    * Convierte un objeto AdministradorDTO a AdministradorEntity.
    * @return Nueva objeto AdministradorEntity.
    */
    
    public AdministradorEntity toEntity() 
    {
        AdministradorEntity administradorEntity = new AdministradorEntity();
        administradorEntity.setLogin(this.getLogin());
        administradorEntity.setContrasena(this.getContrasena());
        administradorEntity.setTipoUsuario(this.getTipoUsuario());
        
        return administradorEntity;
    }
    
    /**
     * Obtiene el atributo login.
     * @return atributo login.
     */
    
    public String getLogin() 
    {
        return login;
    }

    /**
     * Establece el valor del atributo login.
     * @param pLogin nuevo valor del atributo
     */
    
    public void setId(String pLogin)
    {
        this.login = pLogin;
    }
    
    /**
     * Obtiene el atributo contrasena.
     * @return atributo contrasena.
     */
    
    public String getContrasena() 
    {
        return contrasena;
    }

    /**
     * Establece el valor del atributo contrasena.
     * @param pContrasena nuevo valor del atributo
     */
    
    public void setContrasena(String pContrasena)
    {
        this.contrasena = pContrasena;
    }
    
    /**
     * Obtiene el atributo tipoUsuario.
     * @return atributo tipoUsuario.
     */
    
    public String getTipoUsuario() 
    {
        return tipoUsuario;
    }

    /**
     * Establece el valor del atributo tipoUsuario.
     * @param pTipoUsuario nuevo valor del atributo
     */
    
    public void setTipoUsuario(String pTipoUsuario)
    {
        this.tipoUsuario = pTipoUsuario;
    }
    
    
    
    @Override
    public String toString() 
    {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
