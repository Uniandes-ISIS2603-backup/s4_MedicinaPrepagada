/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.medicinaPrepagada.dtos;

import java.util.Date;

/**
 *
 * @author estudiante
 */
public class CitaMedicaDTO
{
    private long idCitaMedica;
    private Date fecha;
    private String comentarios;
    
    public CitaMedicaDTO(){
    
    }

    public long getIdCitaMedica() {
        return idCitaMedica;
    }

    public void setIdCitaMedica(long idCitaMedica) {
        this.idCitaMedica = idCitaMedica;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }
    
    
}
