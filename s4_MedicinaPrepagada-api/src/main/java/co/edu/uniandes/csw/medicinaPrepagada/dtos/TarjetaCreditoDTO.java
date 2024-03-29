/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.medicinaPrepagada.dtos;

import co.edu.uniandes.csw.medicinaPrepagada.entities.TarjetaCreditoEntity;
import java.io.Serializable;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 *TarjetaCreditoDTO Objeto de transferencia de datos de Editoriales. Los DTO
 * contienen las representaciones de los JSON que se transfieren entre el
 * cliente y el servidor.
 * 
 * Al serializarse como JSON esta clase implementa el siguiente modelo: <br>
 * <pre>
 *  {
 *      "numero":number,
*       "fechaExpiracion":string,
*       "nombreEnTarjeta":string,
*       "franquicia":string,
*       "codigoSeguridad":number
*   }
* </pre> por ejemplo una TarejetaCredito se representa asi;
* <pre>
* {
 *      "numero":7890696966688978,
*       "fechaExpiracion":"23/10/2023",
*       "nombreEnTarjeta":"PEPITO PEREX R",
*       "franquicia":"VISA",
*       "codigoSeguridad":666
*   }
*</pre>
 * @author MIGUELHOYOS
 */
public class TarjetaCreditoDTO implements Serializable{
    
    private Long numero;
    private String fechaExpiracion;
    private String nombreEnTarjeta;
    private String franquicia;
    private Integer codigoSeguridad;
    
    /**
     * Constructor por defecto
     */
    public TarjetaCreditoDTO(){
        
    }
    
    public TarjetaCreditoDTO(TarjetaCreditoEntity tarjetaCreditoEntity){
        if(tarjetaCreditoEntity != null)
        {
            this.numero = tarjetaCreditoEntity.getNumero();
            this.fechaExpiracion = tarjetaCreditoEntity.getFechaExpiracion();
            this.nombreEnTarjeta = tarjetaCreditoEntity.getNombreEnTarjeta();
            this.franquicia = tarjetaCreditoEntity.getFranquicia();
            this.codigoSeguridad = tarjetaCreditoEntity.getCodigoSeguridad();
        }
    }

    /**
     * retorna el numero de DTO
     * @return the numero
     */
    public Long getNumero() {
        return numero;
    }

    /**
     * sets el numero del DTO
     * @param numero the numero to set
     */
    public void setNumero(Long numero) {
        this.numero = numero;
    }

    /**
     * retorna la fecha de expiracion del DTO
     * @return the fechaExpiracion
     */
    public String getFechaExpiracion() {
        return fechaExpiracion;
    }

    /**
     * sets la fecha de expiracion del DTO
     * @param fechaExpiracion the fechaExpiracion to set
     */
    public void setFechaExpiracion(String fechaExpiracion) {
        this.fechaExpiracion = fechaExpiracion;
    }

    /**
     * retorna el nombre en la tarjeta del DTO
     * @return the nombreEnTarjeta
     */
    public String getNombreEnTarjeta() {
        return nombreEnTarjeta;
    }

    /**
     * sets el nombre de la tarjeta del DTO
     * @param nombreEnTarjeta the nombreEnTarjeta to set
     */
    public void setNombreEnTarjeta(String nombreEnTarjeta) {
        this.nombreEnTarjeta = nombreEnTarjeta;
    }

    /**
     * retorna la franquicia del DTO
     * @return the franquicia
     */
    public String getFranquicia() {
        return franquicia;
    }

    /**
     * sets la franquicia del DTO
     * @param franquicia the franquicia to set
     */
    public void setFranquicia(String franquicia) {
        this.franquicia = franquicia;
    }

    /**
     * retorna el codigo de seguridad del DTO
     * @return the codigoSeguridad
     */
    public Integer getCodigoSeguridad() {
        return codigoSeguridad;
    }

    /**
     * sets el codigo de seguridad del DTO
     * @param codigoSeguridad the codigoSeguridad to set
     */
    public void setCodigoSeguridad(Integer codigoSeguridad) {
        this.codigoSeguridad = codigoSeguridad;
    }
    
    /**
     * convertir DTO a Entity
     * @return Un entity con los valores del DTO
     */
    public TarjetaCreditoEntity toEntity(){
        TarjetaCreditoEntity entity = new TarjetaCreditoEntity();
        entity.setNumero(this.numero);
        entity.setFechaExpiracion(this.fechaExpiracion);
        entity.setNombreEnTarjeta(this.nombreEnTarjeta);
        entity.setFranquicia(this.franquicia);
        entity.setCodigoSeguridad(this.codigoSeguridad);
        return entity;
    }
    
    @Override
    public String toString(){
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
