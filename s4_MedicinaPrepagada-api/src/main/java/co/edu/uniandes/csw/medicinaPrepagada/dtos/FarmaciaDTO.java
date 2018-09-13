
package co.edu.uniandes.csw.medicinaPrepagada.dtos;

import co.edu.uniandes.csw.medicinaPrepagada.entities.FarmaciaEntity;
import java.io.Serializable;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 *
 * @author estudiante
 */
public class FarmaciaDTO implements Serializable {
    
    private Long id;
    private String nombre;
    private String ubicacion;
    private Long telefono;
    private double latitud;
    private double longitud;
    private String correo;
    
    
    
    /**
     * Constructor por defecto
     */
    public FarmaciaDTO() {
    }
    
    /**
     * Conviertir Entity a DTO (Crea un nuevo DTO con los valores que recibe en
     * la entidad que viene de argumento.
     *
     * @param farmaciaEntity: Es la entidad que se va a convertir a DTO
     */
    public FarmaciaDTO(FarmaciaEntity farmaciaEntity) {
        if (farmaciaEntity != null) {
            this.id = farmaciaEntity.getId();
            this.nombre = farmaciaEntity.getNombre();
            this.ubicacion = farmaciaEntity.getUbicacion();
            this.telefono = farmaciaEntity.getTelefono();
            this.latitud = farmaciaEntity.getLatitud();
            this.longitud = farmaciaEntity.getLongitud();
            this.correo = farmaciaEntity.getCorreo();
        }
    }
    
    /**
     * Devuelve el ID de la farmacia.
     *
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * Modifica el ID de la farmacia.
     *
     * @param pId the id to set
     */
    public void setId(Long pId) {
        this.id = pId;
    }

    /**
     * Devuelve el nombre de la farmacia.
     *
     * @return the name
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Modifica el nombre de la farmacia.
     *
     * @param pNombre the name to set
     */
    public void setNombre(String pNombre) {
        this.nombre = pNombre;
    }
    
    /**
     * Devuelve la ubicacion de la farmacia.
     *
     * @return ubicacion
     */
    public String getUbicacion() {
        return ubicacion;
    }

    /**
     * Modifica la ubicacion de la farmacia.
     *
     * @param pUbicacion the ubicacion to set
     */
    public void setUbicacion(String pUbicacion) {
        this.ubicacion = pUbicacion;
    }
    
    /**
     * Devuelve el telefono de la farmacia.
     *
     * @return telefono
     */
    public Long getTelefono() {
        return telefono;
    }

    /**
     * Modifica el telefono de la farmacia.
     *
     * @param telefono the telefono to set
     */
    public void setTelefono(Long telefono) {
        this.telefono = telefono;
    }
    
    /**
     * Devuelve el correo de la farmacia.
     *
     * @return correo
     */
    public String getCorreo(){
        return correo;
    }
   
    /**
     * Modifica el telefono de la farmacia.
     *
     * @param pCorreo the correo to set
     */
    public void setCorreo(String pCorreo) {
        this.correo = pCorreo;
    }
    
    /**
     * Devuelve la latitud de la farmacia.
     *
     * @return latitud
     */
    public double getLatitud() {
        return latitud;
    }

    /**
     * Modifica la latitud de la farmacia.
     *
     * @param pLat the latitud to set
     */
    public void setLatitud(double pLat) {
        this.latitud = pLat;
    }
    
    /**
     * Devuelve la longitud de la farmacia.
     *
     * @return longitud
     */
    public double getLongitud() {
        return longitud;
    }

    /**
     * Modifica la longitud de la farmacia.
     *
     * @param pLon the longitud to set
     */
    public void setLongitud(double pLon) {
        this.longitud = pLon;
    }
    
    /**
     * Convertir DTO a Entity
     *
     * @return Un Entity con los valores del DTO
     */
    public FarmaciaEntity toEntity() {
        FarmaciaEntity farmaciaEntity = new FarmaciaEntity();
        farmaciaEntity.setId(this.id);
        farmaciaEntity.setNombre(this.nombre);
        farmaciaEntity.setUbicacion(this.ubicacion);
        farmaciaEntity.setTelefono(this.telefono);
        farmaciaEntity.setLatitud(this.latitud);
        farmaciaEntity.setLongitud(this.longitud);
        farmaciaEntity.setCorreo(this.correo);

        return farmaciaEntity;
    }
     @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
