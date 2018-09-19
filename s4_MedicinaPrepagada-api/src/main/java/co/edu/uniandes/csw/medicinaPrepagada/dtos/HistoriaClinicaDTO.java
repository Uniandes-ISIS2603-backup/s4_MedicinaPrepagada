/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.medicinaPrepagada.dtos;

import co.edu.uniandes.csw.medicinaPrepagada.entities.HistoriaClinicaEntity;
import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 *
 * @author ni.ramirez10
 */

public class HistoriaClinicaDTO implements Serializable  
{
    private Long id; 
    private Date fecha; 
    private String descripcionDiagnostico; 
    private String alergias; 
    private Double peso; 
    private Double estatura; 
    private boolean fuma; 
    private boolean bebe; 
    private String operaciones; 
    
    
    /**
     * Constructor vacio
     */
    
    public HistoriaClinicaDTO()
    {
        //Constrcutor vacio
    }
    
     /**
     * Crea un objeto HistoriaClinicaDTO a partir de un objeto HistoriaClinicaEntity.
     * @param historiaClinicaEntity Entidad HistoriaClinicaEntity desde la cual se va a crear el
     * nuevo objeto.
     */
    
    public HistoriaClinicaDTO(HistoriaClinicaEntity historiaClinicaEntity) 
    {
        if (historiaClinicaEntity != null) 
        {
            this.id = historiaClinicaEntity.getId();
            this.fecha = historiaClinicaEntity.getFecha();
            this.descripcionDiagnostico = historiaClinicaEntity.getDescripcionDiagnostico();
            this.alergias = historiaClinicaEntity.getAlergias();
            this.peso = historiaClinicaEntity.getPeso();
            this.estatura = historiaClinicaEntity.getEstatura();
            this.fuma = historiaClinicaEntity.getFuma();
            this.bebe = historiaClinicaEntity.getBebe();
            this.operaciones = historiaClinicaEntity.getOperaciones();
        }
    }
    
    
    /**
     * Convierte un objeto HistoriaClinicaDTO a HistoriaClinicaEntity.
     * @return Nuevo objeto HistoriaClinicaEntity.
     */
    
    public HistoriaClinicaEntity toEntity() 
    {
        HistoriaClinicaEntity historiaClinicaEntity = new HistoriaClinicaEntity();
        historiaClinicaEntity.setId(this.getId());
        historiaClinicaEntity.setFecha(this.getFecha());
        historiaClinicaEntity.setDescripcionDiagnostico(this.getDescripcionDiagnostico());
        historiaClinicaEntity.setAlergias(this.getAlergias());
        historiaClinicaEntity.setPeso(this.getPeso());
        historiaClinicaEntity.setEstatura(this.getEstatura());
        historiaClinicaEntity.setFuma(this.getFuma());
        historiaClinicaEntity.setBebe(this.getBebe());
        historiaClinicaEntity.setOperaciones(this.getOperaciones());
        
        return historiaClinicaEntity;
    }
    
    /**
     * Obtiene el atributo id.
     * @return atributo id.
     */
    
    public Long getId() 
    {
        return id;
    }

    /**
     * Establece el valor del atributo id.
     * @param id nuevo valor del atributo
     */
    
    public void setId(Long id) 
    {
        this.id = id;
    }
    
     /**
     * Obtiene la fecha
     * @return atributo fecha.
     */
    
    public Date getFecha() 
    {
        return fecha;
    }

    /**
     * Establece el valor del atributo fecha.
     * @param fecha nuevo valor del atributo
     */
    
    public void setFecha(Date fecha ) 
    {
        this.fecha = fecha;
    }
    
    /**
    * Obtiene la descripcion diagnostico
    * @return atributo descripcionDiagnostico.
    */
    
    public String getDescripcionDiagnostico() 
    {
        return descripcionDiagnostico;
    }

    /**
     * Establece el valor del atributo descripcion diagnostico.
     * @param descripcionDiagnostico nuevo valor del atributo
     */
    
    public void setDescripcionDiagnostico(String descripcionDiagnostico ) 
    {
        this.descripcionDiagnostico = descripcionDiagnostico;
    }
    
    /**
     * Obtiene las alergias
     * @return atributo alergias.
     */
    
    public String getAlergias() 
    {
        return alergias;
    }

    /**
     * Establece el valor del atributo alergias.
     * @param alergias nuevo valor del atributo
     */
    
    public void setAlergias(String alergias ) 
    {
        this.alergias = alergias;
    }
    
    /**
     * Obtiene el peso 
     * @return atributo peso.
     */
    
    public Double getPeso() 
    {
        return peso;
    }

    /**
     * Establece el valor del atributo peso.
     * @param peso nuevo valor del atributo
     */
    
    public void setPeso(Double peso ) 
    {
        this.peso = peso;
    }
    
    /**
     * Obtiene la estatura 
     * @return atributo estatura.
     */
    
    public Double getEstatura() 
    {
        return estatura;
    }

    /**
     * Establece el valor del atributo estatura.
     * @param estatura nuevo valor del atributo
     */
    
    public void setEstatura(Double estatura ) 
    {
        this.estatura = estatura;
    }
    
    /**
     * Obtiene el estado de fuma 
     * @return atributo fuma.
     */
    
    public Boolean getFuma() 
    {
        return fuma;
    }

    /**
     * Establece el valor del atributo fuma.
     * @param fuma nuevo valor del atributo
     */
    
    public void setFuma(Boolean fuma ) 
    {
        this.fuma = fuma;
    }
    
    /**
     * Obtiene el estado de bebe 
     * @return atributo bebe.
     */
    
    public Boolean getBebe() 
    {
        return bebe;
    }

    /**
     * Establece el valor del atributo bebe.
     * @param bebe nuevo valor del atributo
     */
    
    public void setBebe(Boolean bebe ) 
    {
        this.bebe = bebe;
    }
    
    /**
     * Obtiene las operaciones
     * @return atributo operaciones.
     */
    
    public String getOperaciones() 
    {
        return operaciones;
    }

    /**
     * Establece el valor del atributo operaciones.
     * @param operaciones nuevo valor del atributo
     */
    
    public void setOperaciones(String operaciones ) 
    {
        this.operaciones = operaciones;
    }
    
      @Override
    public String toString() 
    {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
