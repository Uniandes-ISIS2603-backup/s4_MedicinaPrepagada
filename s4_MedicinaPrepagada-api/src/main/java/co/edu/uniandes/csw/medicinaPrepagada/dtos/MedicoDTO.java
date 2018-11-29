/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.medicinaPrepagada.dtos;

import co.edu.uniandes.csw.medicinaPrepagada.entities.MedicoEntity;
import co.edu.uniandes.csw.medicinaPrepagada.entities.MedicoEntity;
import java.io.Serializable;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 *
 * @author estudiante
 */
public class MedicoDTO extends UsuarioDTO implements Serializable{
    
    private Long idMedico;
    
    private String nombre;
    
    private Integer telefono;
    
    private String correo;
    
    private Integer documentoMedico;
    
    private String firma;
    
    private String descripcion;
    
    private EspecialidadDTO especialidad;
    
    public MedicoDTO(){
        super();
    }
    
    public MedicoDTO (MedicoEntity medicoEntity){
        super(medicoEntity);
        if (medicoEntity != null){
           this.idMedico =medicoEntity.getId();
           this.nombre = medicoEntity.getNombre();
           this.telefono = medicoEntity.getTelefono();
           this.correo = medicoEntity.getCorreo();
           this.documentoMedico = medicoEntity.getDocumentoMedico();
           this.firma = medicoEntity.getFirma();
           this.descripcion = medicoEntity.getDescripcion();
           this.especialidad = new EspecialidadDTO(medicoEntity.getEspecialidad());
        }   
    }
     
     
     /**
     * Convierte un objeto ConsultorioDTO a MedicoEntity.
     *
     * @return Nueva objeto MedicoEntity.
     *
     */
    public MedicoEntity toEntity() 
    {
        MedicoEntity medicoEntity = new MedicoEntity();
        medicoEntity.setLogin(super.toEntity().getLogin());
        medicoEntity.setContrasena(super.toEntity().getContrasena());
        medicoEntity.setTipoUsuario(super.toEntity().getTipoUsuario());
        medicoEntity.setDocumentoIdentidad(super.toEntity().getDocumentoIdentidad());
        medicoEntity.setId(this.getIdMedico());
        medicoEntity.setNombre(this.getNombre());
        medicoEntity.setTelefono(this.getTelefono());
        medicoEntity.setCorreo(this.getCorreo());
        medicoEntity.setDocumentoMedico(this.getDocumentoMedico());
        medicoEntity.setFirma(this.getFirma());
        medicoEntity.setDescripcion(this.getDescripcion());
        if(this.especialidad != null){
            medicoEntity.setEspecialidad(this.especialidad.toEntity());
        }
        return medicoEntity;
    }

    /**
     * @return the idMedico
     */
    public long getIdMedico() {
        return idMedico;
    }

    /**
     * @param idMedico the idMedico to set
     */
    public void setIdMedico(long idMedico) {
        this.idMedico = idMedico;
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
     * @return the telefono
     */
    public int getTelefono() {
        return telefono;
    }

    /**
     * @param telefono the telefono to set
     */
    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }

    /**
     * @return the correo
     */
    public String getCorreo() {
        return correo;
    }

    /**
     * @param correo the correo to set
     */
    public void setCorreo(String correo) {
        this.correo = correo;
    }
    
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

    /**
     * @return the especialidad
     */
    public EspecialidadDTO getEspecialidad() {
        return especialidad;
    }

    /**
     * @param especialidad the especialidad to set
     */
    public void setEspecialidad(EspecialidadDTO especialidad) {
        this.especialidad = especialidad;
    }

    public int getDocumentoMedico() {
        return documentoMedico;
    }

    public void setDocumentoMedico(int documentoMedico) {
        this.documentoMedico = documentoMedico;
    }

    public String getFirma() {
        return firma;
    }

    public void setFirma(String firma) {
        this.firma = firma;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    
}
