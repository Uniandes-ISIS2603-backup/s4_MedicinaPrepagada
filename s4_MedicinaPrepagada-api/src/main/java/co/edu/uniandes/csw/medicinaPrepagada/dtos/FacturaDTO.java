/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.medicinaPrepagada.dtos;

import co.edu.uniandes.csw.medicinaPrepagada.entities.FacturaEntity;
import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 *
 * @author estudiante
 */
public class FacturaDTO implements Serializable{
    
    private Long idFactura;
  
    private Long idCliente;
    
    private Date fecha;
    
    private int valor;
    
    private String concepto;
    
    private boolean pagada;
    
    private PacienteDTO paciente;
    
    
    //Constructor vacio
    public FacturaDTO ()
    {
        
    }    
    
     public FacturaDTO(FacturaEntity pFacturaEntity) {
        if (pFacturaEntity != null) {
            this.idFactura = pFacturaEntity.getId();
            this.idCliente= pFacturaEntity.getIdCliente();
            this.concepto= pFacturaEntity.getConcepto();
            this.fecha=pFacturaEntity.getFecha();
            this.paciente =new PacienteDTO (pFacturaEntity.getPaciente());
            this.pagada = pFacturaEntity.getPagada();
            this.valor = pFacturaEntity.getValor();
        }

    }

    public FacturaEntity toEntity() {
        FacturaEntity facturaEntity = new FacturaEntity();

        facturaEntity.setId(this.idFactura);
        facturaEntity.setIdCliente(this.idCliente);
        facturaEntity.setConcepto(this.concepto);
        facturaEntity.setFecha(this.fecha);
        facturaEntity.setPaciente(this.paciente.toEntity());
        facturaEntity.setPagada(this.pagada);
        facturaEntity.setValor(this.valor);
      

        return facturaEntity;
    }
    public void setIdFactura ( Long pIdFactura )
    {
        this.idFactura = pIdFactura;
    }
    
    public Long getIdFactura ()
    {
        return this.idFactura;
    }
        
    public void setIdCliente ( Long pIdCliente )
    {
        this.idCliente = pIdCliente;
    }
    
    public Long getIdCliente ()
    {
        return this.idCliente;
    }
   
    public void setDate ( Date pFecha )
    {
        this.fecha = pFecha;
    }
    
    public Date getDate ()
    {
        return this.fecha;
    }
    
    public void setValor ( int pValor )
    {
        this.valor = pValor;
    }
    
    public int getValor ()
    {
        return this.valor;
    }
 
    public void setConcepto ( String pConcepto )
    {
        this.concepto = pConcepto;
    }
    
    public String getConcepto ()
    {
        return this.concepto;
    }
    
    public void setPagada(boolean pPagada)
    {
        this.pagada=pPagada;
    }
    
    public boolean getPagada ()
    {
        return pagada;
    }
      @Override
    public String toString() 
    {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
