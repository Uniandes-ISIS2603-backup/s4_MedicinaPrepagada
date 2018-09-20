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
 *      "tipoUsuario":string,
 *      "documentoIdentidad":number
 *   }
 * </pre> Por ejemplo una editorial se representa asi:<br>
 *
 * <pre>
 *
 *   {
 *      "login":"pepito420",
 *      "contrasena":"345",
 *      "tipoUsuario":"doctor",
 *      "documentoIdentidad":1020829469
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
    private Long documentoIdentidad;
    
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
    public UsuarioDTO(UsuarioEntity usuarioEntity)
    {
        if(usuarioEntity != null){
            this.login = usuarioEntity.getLogin();
            this.contrasena = usuarioEntity.getContrasena();
            this.tipoUsuario = usuarioEntity.getTipoUsuario();
            this.documentoIdentidad = usuarioEntity.getDocumentoIdentidad();
        }
    }

    /**
     * retorana el login del DTO
     * @return the login
     */
    public String getLogin() {
        return login;
    }

    /**
     * sets el login del DTO
     * @param login the login to set
     */
    public void setLogin(String login) {
        this.login = login;
    }

    /**
     * retorna la contrasena del DTO
     * @return the contrasena
     */
    public String getContrasena() {
        return contrasena;
    }

    /**
     * sets la contrasena del DTO
     * @param contrasena the contrasena to set
     */
    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    /**
     * retorna el tipoUsuario del DTO
     * @return the tipoUsuario
     */
    public String getTipoUsuario() {
        return tipoUsuario;
    }

    /**
     * sets el tipo usario del DTO
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
        entity.setDocumentoIdentidad(this.documentoIdentidad);
        return entity;
    }
    
    @Override
    public String toString(){
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

    /**
     * retorna el documento de indentidad del DTO
     * @return the documentoIdentidad
     */
    public Long getDocumentoIdentidad() {
        return documentoIdentidad;
    }

    /**
     * sets el documento de identidad del DTO
     * @param documentoIdentidad the documentoIdentidad to set
     */
    public void setDocumentoIdentidad(Long documentoIdentidad) {
        this.documentoIdentidad = documentoIdentidad;
    }
    
}
