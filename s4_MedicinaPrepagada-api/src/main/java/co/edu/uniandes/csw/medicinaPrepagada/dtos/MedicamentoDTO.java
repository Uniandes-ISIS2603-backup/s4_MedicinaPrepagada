
package co.edu.uniandes.csw.medicinaPrepagada.dtos;

import co.edu.uniandes.csw.medicinaPrepagada.entities.MedicamentoEntity;
import java.io.Serializable;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 *
 * @author ncobos
 */
public class MedicamentoDTO implements Serializable {
    
    private Long id;
    private String nombre;
    private String cantidad;
    private String descripcion;
    private Double costo;
    private String elaboradoPor;
    
    
    /**
     * Constructor por defecto
     */
    public MedicamentoDTO() {
    }
    
    /**
     * Conviertir Entity a DTO (Crea un nuevo DTO con los valores que recibe en
     * la entidad que viene de argumento.
     *
     * @param medicamentoEntity: Es la entidad que se va a convertir a DTO
     */
    public MedicamentoDTO(MedicamentoEntity medicamentoEntity) {
        if (medicamentoEntity != null) {
            this.id = medicamentoEntity.getId();
            this.nombre = medicamentoEntity.getNombre();
            this.cantidad = medicamentoEntity.getCantidad();
            this.descripcion = medicamentoEntity.getDescripcion();
            this.costo = medicamentoEntity.getCosto();
            this.elaboradoPor = medicamentoEntity.getElaboradoPor();
        }
    }
    
    /**
     * Devuelve el ID del medicamento.
     *
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * Modifica el ID del medicamento.
     *
     * @param pId the id to set
     */
    public void setId(Long pId) {
        this.id = pId;
    }

    /**
     * Devuelve el nombre del medicamento.
     *
     * @return the name
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Modifica el nombre del medicamento.
     *
     * @param pNombre the name to set
     */
    public void setNombre(String pNombre) {
        this.nombre = pNombre;
    }
    
    /**
     * Devuelve la cantidad del medicamento.
     *
     * @return cantidad
     */
    public String getCantidad() {
        return cantidad;
    }

    /**
     * Modifica la cantidad del medicamento.
     *
     * @param pCantidad the cantidad to set
     */
    public void setCantidad(String pCantidad) {
        this.cantidad = pCantidad;
    }
    
    /**
     * Devuelve la descripcion del medicamento.
     *
     * @return descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Modifica la descripcion del medicamento.
     *
     * @param pDescripcion the descripcion to set
     */
    public void setDescripcion(String pDescripcion) {
        this.descripcion = pDescripcion;
    }
    
    
    /**
     * Devuelve el costo del medicamento.
     *
     * @return the costo
     */
    public double getCosto() {
        return costo;
    }

    /**
     * Modifica el costo del medicamento.
     *
     * @param pCosto the costo set
     */
    public void setCosto(double pCosto) {
        this.costo = pCosto;
    }
    
    /**
     * Devuelve quién hizo el medicamento.
     *
     * @return the ElaboradoPor
     */
    public String getElaboradoPor() {
        return elaboradoPor;
    }

    /**
     * Modifica el elaborado por del medicamento.
     *
     * @param pElaboradoPor the elaboradoPor set
     */
    public void setElaboradoPor(String pElaboradoPor) {
        this.elaboradoPor = pElaboradoPor;
    }
    
    
    /**
     * Convertir DTO a Entity
     *
     * @return Un Entity con los valores del DTO
     */
    public MedicamentoEntity toEntity() {
        MedicamentoEntity medicamentoEntity = new MedicamentoEntity();
        medicamentoEntity.setId(this.id);
        medicamentoEntity.setNombre(this.nombre);
        medicamentoEntity.setCantidad(this.cantidad );
        medicamentoEntity.setDescripcion(this.descripcion);
        medicamentoEntity.setCosto(this.costo);
        medicamentoEntity.setElaboradoPor(this.elaboradoPor);
        
        return medicamentoEntity;
    }
     @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}

