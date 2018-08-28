/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.medicinaPrepagada.dtos;

import java.util.Date;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 *
 * @author estudiante
 */
public class FacturaDTO {
    
    private int idFactura;
  
    private int idCliente;
    
    private Date fecha;
    
    private int valor;
    
    private String concepto;
    
    public FacturaDTO ()
    {
        
    }    
    public void setIdFactura ( int pIdFactura )
    {
        this.idFactura = pIdFactura;
    }
    
    public int getIdFactura ()
    {
        return this.idFactura;
    }
        
    public void setIdCliente ( int pIdCliente )
    {
        this.idCliente = pIdCliente;
    }
    
    public int getIdCliente ()
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
    
      @Override
    public String toString() 
    {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
