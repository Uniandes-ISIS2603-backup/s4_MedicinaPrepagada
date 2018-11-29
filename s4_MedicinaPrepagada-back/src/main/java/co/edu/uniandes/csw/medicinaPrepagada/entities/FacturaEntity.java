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
    // Id de la factura
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //Paciente de la factura
    @PodamExclude
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(nullable=true)
    private PacienteEntity paciente;
    // cita laboratorio de la factura
    @PodamExclude
    @OneToOne //(mappedBy = "factura", fetch=FetchType.LAZY, cascade = CascadeType.PERSIST, orphanRemoval = true)
    private CitaLaboratorioEntity citaLaboratorio;
    //Fecha de la factura
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @PodamStrategyValue(DateStrategy.class)
    private Date fecha;
    // Valor de la factura
    private int valor;
    //Concepto de la factura
    private String concepto;
    //Estado de pago dde la factura
    private boolean pagada;
    // Id del cliente de la factura
    private Long idCliente;
    
    /**
     * Retorna el id de la factura
     * @return 
     */
    public Long getId()
    {
        return this.id;
    }
    /**
     * Edita el id de la factura
     * @param pId 
     */
    public void setId (Long pId)
    {
        this.id=pId;
    }
    /**
     * Retorna el paciente de la factura
     * @return 
     */
    public PacienteEntity getPaciente ()
    {
        return this.paciente;
    }
    /**
     * Edita el paciente de la factura
     * @param pPaciente 
     */
    public void setPaciente (PacienteEntity pPaciente)
    {
        this.paciente = pPaciente;
    }
    /**
     * Retorna la cita de laboratorio de la factura
     * @return 
     */
    public CitaLaboratorioEntity getCitaLab ()
    {
        return this.citaLaboratorio;
    }
    /**
     * Edita la cita de laboratoio de la factura
     * @param pCita 
     */
    public void setCitaLab (CitaLaboratorioEntity pCita)
    {
        this.citaLaboratorio = pCita;
    }
    /**
     * Retorna la fecha de la factura
     * @return 
     */
    public Date getFecha()
    {
        return this.fecha;
    }
    /**
     * Edita la fecha de de la factura
     * @param pFecha 
     */
    public void setFecha(Date pFecha)
    {
        this.fecha = pFecha;
    }
    /**
     * Retorna el valor de la factura
     * @return 
     */
    public int getValor ()
    {
        return this.valor;
    }
    /**
     * Edita el valor de la factura
     * @param pValor 
     */
    public void setValor (int pValor)
    {
        this.valor = pValor;
    }
    /**
     * Retorna el concepto de la factura
     * @return 
     */
    public String getConcepto ()
    {
        return this.concepto;
    }
    /**
     * Edita el concepto de la factura
     * @param pConcepto 
     */
    public void setConcepto (String pConcepto)
    {
        this.concepto = pConcepto;
    }
    /**
     * Retorna el estado de pago de la factura
     * @return 
     */
    public boolean getPagada ()
    {
        return this.pagada;
    }
    /**
     * Edita el estado de pago de la factura
     * @param pPagada 
     */
    public void setPagada (boolean pPagada)
    {
        this.pagada = pPagada;
    }
    /**
     * Retorna el id del cliente de la factura
     * @return 
     */
    public Long getIdCliente ()
    {
        return this.idCliente;
    }
    /**
     * -Edita el id del cliente de la factura
     * @param pIdCliente 
     */
    public void setIdCliente (Long pIdCliente)
    {
        this.idCliente = pIdCliente;
    }
    /**
     * Se usa para comparar si dos factura son iguales
     * @param obj
     * @return True si son iguales
     */
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
