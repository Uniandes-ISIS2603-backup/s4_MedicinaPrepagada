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
 * @author Santiago Rojas
 */
public class FacturaDTO implements Serializable{
    /**
     * Id de factura
     */
    private Long idFactura;
    /**
    * id del cliente asociado a la factura
    */
    private Long idCliente;
    /**
     * Fecha de la factura
     */
    private Date fecha;
    /**
     * Valor de la factura
     */
    private int valor;
    /**
     * Concepto de la factura
     */
    private String concepto;
    /**
     * Estado de la factura, pagado o no pagado
     */
    private boolean pagada;
    
    private PacienteDTO paciente;
    
    
    //Constructor vacio
    public FacturaDTO ()
    {
        
    }    
    /**
     * Crea un objeto FacturaDTO a partir de un objetoFacturaEntity.
     *
     * @param pFacturaEntity FacturaEntity desde la cual se va a crear el
     * nuevo DTO.
     *
     */
     public FacturaDTO(FacturaEntity pFacturaEntity) {
        if (pFacturaEntity != null) {
            this.idFactura = pFacturaEntity.getId();
            this.idCliente= pFacturaEntity.getIdCliente();
            this.concepto= pFacturaEntity.getConcepto();
            this.fecha= pFacturaEntity.getFecha();
            if(pFacturaEntity.getPaciente() != null){
                this.paciente = new PacienteDTO(pFacturaEntity.getPaciente());
            }
            this.pagada = pFacturaEntity.getPagada();
            this.valor = pFacturaEntity.getValor();
        }

    }
     /**
     * Convierte un objeto FacturaDTO a FacturaEntity.
     *
     * @return Nueva objeto FacturaEntity.
     *
     */
    public FacturaEntity toEntity() {
        FacturaEntity facturaEntity = new FacturaEntity();

        facturaEntity.setId(this.idFactura);
        facturaEntity.setIdCliente(this.idCliente);
        facturaEntity.setConcepto(this.concepto);
        facturaEntity.setFecha(this.fecha);
        if(paciente!=null)
        {
            facturaEntity.setPaciente(this.paciente.toEntity());
        }
        facturaEntity.setPagada(this.pagada);
        facturaEntity.setValor(this.valor);
      

        return facturaEntity;
    }
    
    /**
     * Asigna el id de la factura
     * @param pIdFactura 
     */
    public void setIdFactura ( Long pIdFactura )
    {
        this.idFactura = pIdFactura;
    }
    /**
     * Recibe el id de la factura
     * @return 
     */
    public Long getIdFactura ()
    {
        return this.idFactura;
    }
    /**
     * Asigna el di del cliente asociado a la factura
     * @param pIdCliente 
     */
    
    public void setIdCliente ( Long pIdCliente )
    {
        this.idCliente = pIdCliente;
    }
    /**
     * Recibe el id del cliente asociado a la factura
     * @return idCliente
     */
    public Long getIdCliente ()
    {
        return this.idCliente;
    }
   /**
    * Asgina la fecha de la factura
    * @param pFecha 
    */
    public void setDate ( Date pFecha )
    {
        this.fecha = pFecha;
    }
    /**
     * Recibe La fecha de la factura
     * @return fecha
     */
    public Date getDate ()
    {
        return this.fecha;
    }
    /**
     *Asigna el valor de la factura 
     * @param pValor 
     */
    public void setValor ( int pValor )
    {
        this.valor = pValor;
    }
    /**
     * Recibe el valor de la factura
     * @return valor
     */
    public int getValor ()
    {
        return this.valor;
    }
    /**
     * Asigna el concepto de la cita
     * @param pConcepto 
     */
    public void setConcepto ( String pConcepto )
    {
        this.concepto = pConcepto;
    }
    /**
     * Recibe el concepto de la cita
     * @return concepto
     */
    public String getConcepto ()
    {
        return this.concepto;
    }
    /**
     * Asigna el estado de la factura, pagada o no pagada
     * @param pPagada 
     */
    public void setPagada(boolean pPagada)
    {
        this.pagada=pPagada;
    }
    /**
     * Recibe el estado de la factura
     * @return pagada
     */
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
