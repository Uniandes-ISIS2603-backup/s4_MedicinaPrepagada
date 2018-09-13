/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.medicinaPrepagada.dtos;

import co.edu.uniandes.csw.medicinaPrepagada.entities.UsuarioEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 ** UsuarioDTO Objeto de transferencia de datos de Usuarios. Los DTO
 * contienen las representaciones de los JSON que se transfieren entre el
 * cliente y el servidor.
 *
 * Al serializarse como JSON esta clase implementa el siguiente modelo: <br>
 * <pre>
 *   {
 *      "login":string,
 *      "contrasena":string,
 *      "tipoUsuario":string
 *   }
 * </pre> Por ejemplo una editorial se representa asi:<br>
 *
 * <pre>
 *
 *   {
 *      "login":"pepito420",
 *      "contrasena":"345",
 *      "tipoUsuario":"doctor"
 *   }
 *
 * </pre>
 * 
 * @author MIGUELHOYOS
 */
public class UsuarioDTO {
    
    private String login;
    private String contrasena;
    private String tipoUsuario;
    
    /**
     * Constructor por defecto
     */
    public UsuarioDTO(){
        
    }
    
     /**
     * Conviertir Entity a DTO (Crea un nuevo DTO con los valores que recibe en
     * la entidad que viene de argumento.
     *
     * @param usuarioEntity: Es la entidad que se va a convertir a DTO
     */
    public UsuarioDTO(UsuarioEntity usuarioEntity){
        if(usuarioEntity != null){
            this.login = usuarioEntity.getLogin();
            this.contrasena = usuarioEntity.getContrasena();
            this.tipoUsuario = usuarioEntity.getTipoUsuario();
        }
    }

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
     * Convertir DTO a Entity
     *
     * @return Un Entity con los valores del DTO
     */
    public UsuarioEntity toEntity(){
        UsuarioEntity entity = new UsuarioEntity();
        entity.setLogin(this.login);
        entity.setContrasena(this.contrasena);
        entity.setTipoUsuario(this.tipoUsuario);
        return entity;
    }
    
    @Override
    public String toString(){
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
    
}
