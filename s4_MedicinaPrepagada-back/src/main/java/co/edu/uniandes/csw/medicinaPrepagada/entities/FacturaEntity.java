/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.medicinaPrepagada.entities;

import co.edu.uniandes.csw.medicinaPrepagada.podam.DateStrategy;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import org.springframework.format.annotation.DateTimeFormat;
import uk.co.jemos.podam.common.PodamExclude;
import uk.co.jemos.podam.common.PodamStrategyValue;

/**
 *
 * @author Santiago Rojas
 */
@Entity
public class FacturaEntity implements Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @PodamExclude
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(nullable=true)
    private PacienteEntity paciente;
    
    @PodamExclude
    @OneToOne //(mappedBy = "factura", fetch=FetchType.LAZY, cascade = CascadeType.PERSIST, orphanRemoval = true)
    private CitaLaboratorioEntity citaLaboratorio;
    
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @PodamStrategyValue(DateStrategy.class)
    private Date fecha;
    private int valor;
    private String concepto;
    private boolean pagada;
    private Long idCliente;
    
    public Long getId()
    {
        return this.id;
    }
    
    public void setId (Long pId)
    {
        this.id=pId;
    }
    
    public PacienteEntity getPaciente ()
    {
        return this.paciente;
    }
    
    public void setPaciente (PacienteEntity pPaciente)
    {
        this.paciente = pPaciente;
    }
    
    public CitaLaboratorioEntity getCitaLab ()
    {
        return this.citaLaboratorio;
    }
    
    public void setCitaLab (CitaLaboratorioEntity pCita)
    {
        this.citaLaboratorio = pCita;
    }
    
    public Date getFecha()
    {
        return this.fecha;
    }
    
    public void setFecha(Date pFecha)
    {
        this.fecha = pFecha;
    }
    
    public int getValor ()
    {
        return this.valor;
    }
    
    public void setValor (int pValor)
    {
        this.valor = pValor;
    }
    
    public String getConcepto ()
    {
        return this.concepto;
    }
    
    public void setConcepto (String pConcepto)
    {
        this.concepto = pConcepto;
    }
    
    public boolean getPagada ()
    {
        return this.pagada;
    }
    
    public void setPagada (boolean pPagada)
    {
        this.pagada = pPagada;
    }
    
    public Long getIdCliente ()
    {
        return this.idCliente;
    }
    
    public void setIdCliente (Long pIdCliente)
    {
        this.idCliente = pIdCliente;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final FacturaEntity other = (FacturaEntity) obj;
        return Objects.equals(this.id, other.id);
    }

    @Override
    public int hashCode() 
    {
        if (this.getId() != null) {
            return this.getId().hashCode();
        }
        return super.hashCode();
    }
    
}
