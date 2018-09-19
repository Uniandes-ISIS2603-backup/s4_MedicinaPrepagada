
package co.edu.uniandes.csw.medicinaPrepagada.dtos;

import co.edu.uniandes.csw.medicinaPrepagada.entities.ExamenMedicoEntity;
import java.io.Serializable;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 *
 * @author ncobos
 */
public class ExamenMedicoDTO implements Serializable {
    
    private Long id;
    private String nombre;
    private double costo;
    private String recomendaciones;
    
    
    /**
     * Constructor por defecto
     */
    public ExamenMedicoDTO() {
    }
    
    /**
     * Conviertir Entity a DTO (Crea un nuevo DTO con los valores que recibe en
     * la entidad que viene de argumento.
     *
     * @param examenMedicoEntity: Es la entidad que se va a convertir a DTO
     */
    public ExamenMedicoDTO(ExamenMedicoEntity examenMedicoEntity) {
        if (examenMedicoEntity != null) {
            this.id = examenMedicoEntity.getId();
            this.nombre = examenMedicoEntity.getNombre();
            this.costo = examenMedicoEntity.getCosto();
            this.recomendaciones = examenMedicoEntity.getRecomendaciones();

        }
    }
    
    /**
     * Devuelve el ID del examen medico.
     *
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * Modifica el ID del examen medico.
     *
     * @param pId the id to set
     */
    public void setId(Long pId) {
        this.id = pId;
    }

    /**
     * Devuelve el nombre del examen medico.
     *
     * @return the name
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Modifica el nombre del examen medico.
     *
     * @param pNombre the name to set
     */
    public void setNombre(String pNombre) {
        this.nombre = pNombre;
    }
    
    
    
    /**
     * Devuelve el costo del examen medico.
     *
     * @return the costo
     */
    public double getCosto() {
        return costo;
    }

    /**
     * Modifica el costo del examen medico.
     *
     * @param pCosto the costo set
     */
    public void setCosto(double pCosto) {
        this.costo = pCosto;
    }
    
    /**
     * Devuelve las recomendaciones del examen medico.
     *
     * @return the Recomendaciones
     */
    public String getRecomendaciones() {
        return recomendaciones;
    }

    /**
     * Modifica las recomendaciones del examen medico.
     *
     * @param pRecomendaciones the recomendaciones set
     */
    public void setRecomendaciones(String pRecomendaciones) {
        this.recomendaciones = pRecomendaciones;
    }
    
    
    /**
     * Convertir DTO a Entity
     *
     * @return Un Entity con los valores del DTO
     */
    public ExamenMedicoEntity toEntity() {
        ExamenMedicoEntity examenMedicoEntity = new ExamenMedicoEntity();
        examenMedicoEntity.setId(this.id);
        examenMedicoEntity.setNombre(this.nombre);
        examenMedicoEntity.setCosto(this.costo);
        examenMedicoEntity.setRecomendaciones(this.recomendaciones);
        return examenMedicoEntity;
    }
     @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}