/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.medicinaPrepagada.dtos;

import co.edu.uniandes.csw.medicinaPrepagada.entities.PacienteEntity;
import java.io.Serializable;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 *
 * PacienteDTO Objeto de transferencia de datos de Pacientes. Los DTO
 * contienen las representaciones de los JSON que se transfieren entre el
 * cliente y el servidor.
 *
 * Al serializarse como JSON esta clase implementa el siguiente modelo: <br>
 * <pre>
 *   {
 *      "cedula":number,
 *      "nombre":string,
 *      "fechaNacimiento":string,
 *      "direccion":string,
 *      "numeroContacto":number,
 *      "mail":string,
 *      "eps": string}
 *   }
 * </pre> Por ejemplo un paciente se representa asi:<br>
 *
 * <pre>
 *
 *   {
 *      "cedula":1029849123,
 *      "nombre":"Pepito Perez",
 *      "fechaNacimiento":"19/02/1982",
 *      "direccion":"Av. 19 # 97 - 49",
 *      "numeroContacto":1234567890,
 *      "mail":"pepito@mail.com",
 *      "eps": "la menos mala"}
 *   }
 *
 * </pre>
 *
 * @author MIGUELHOYOS
 */
public class PacienteDTO extends UsuarioDTO implements Serializable{
    
    private Long cedula;
    private String nombre;
    private String fechaNacimiento;
    private String direccion;
    private Long numeroContacto;
    private String mail;
    private String eps;
    
    /**
     * constructor por defecto
     */
    public PacienteDTO(){
        
    }
    
    /**
     *  Conviertir Entity a DTO (Crea un nuevo DTO con los valores que recibe en
     * la entidad que viene de argumento.
     * 
     * @param pacienteEntity: es la entidad para convertir en DTO
     */
    public PacienteDTO(PacienteEntity pacienteEntity){
        if(pacienteEntity != null){
            this.cedula = pacienteEntity.getCedula();
            this.nombre = pacienteEntity.getNombre();
            this.fechaNacimiento = pacienteEntity.getFechaNacimiento();
            this.direccion = pacienteEntity.getDireccion();
            this.numeroContacto = pacienteEntity.getNumeroContacto();
            this.mail = pacienteEntity.getMail();
            this.eps = pacienteEntity.getEps();
        }
    }

    /**
     * @return the cedula
     */
    public Long getCedula() {
        return cedula;
    }

    /**
     * @param cedula the cedula to set
     */
    public void setCedula(Long cedula) {
        this.cedula = cedula;
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the fechaNacimiento
     */
    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    /**
     * @param fechaNacimiento the fechaNacimiento to set
     */
    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    /**
     * @return the direccion
     */
    public String getDireccion() {
        return direccion;
    }

    /**
     * @param direccion the direccion to set
     */
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    /**
     * @return the numeroContacto
     */
    public Long getNumeroContacto() {
        return numeroContacto;
    }

    /**
     * @param numeroContacto the numeroContacto to set
     */
    public void setNumeroContacto(Long numeroContacto) {
        this.numeroContacto = numeroContacto;
    }

    /**
     * @return the mail
     */
    public String getMail() {
        return mail;
    }

    /**
     * @param mail the mail to set
     */
    public void setMail(String mail) {
        this.mail = mail;
    }

    /**
     * @return the eps
     */
    public String getEps() {
        return eps;
    }

    /**
     * @param eps the eps to set
     */
    public void setEps(String eps) {
        this.eps = eps;
    }
    
    /**
     * convierte DTO a entity
     * @return un entity con los valores del dto
     */
    public PacienteEntity toEntity(){
        PacienteEntity pacienteEntity = new PacienteEntity();
        pacienteEntity.setCedula(this.cedula);
        pacienteEntity.setNombre(this.nombre);
        pacienteEntity.setFechaNacimiento(this.fechaNacimiento);
        pacienteEntity.setMail(this.mail);
        pacienteEntity.setDireccion(this.direccion);
        pacienteEntity.setNumeroContacto(this.numeroContacto);
        pacienteEntity.setEps(this.eps);
        return pacienteEntity;
    }
    
    @Override
    public String toString(){
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
