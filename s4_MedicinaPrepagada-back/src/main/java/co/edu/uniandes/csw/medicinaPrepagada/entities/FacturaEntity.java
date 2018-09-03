/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.medicinaPrepagada.entities;

import java.util.Date;
import java.util.Objects;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

/**
 *
 * @author Santiago Rojas
 */
public class FacturaEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    private PacienteEntity paciente;
    
    @OneToOne //(mappedBy = "factura", fetch=FetchType.LAZY, cascade = CascadeType.PERSIST, orphanRemoval = true)
    private CitaLaboratorioEntity citaLaboratorio;
    
    private Date fecha;
    private int valor;
    private String concepto;
    private boolean pagada;
    private int idCliente;
    
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
    
    public int getIdCliente ()
    {
        return this.idCliente;
    }
    
    public void setIdCliente (int pIdCliente)
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
