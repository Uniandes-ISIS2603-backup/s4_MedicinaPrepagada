/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.medicinaPrepagada.dtos;

/**
 *
 * @author estudiante
 */
public class LaboratorioDTO {
    
    
    private String nombre;
    
    private int telefono;
    
    private String direccion;
    
    private String horarioDeAtencion;
    
    public LaboratorioDTO()
    {
        
    }
    
    public void setNombre (String pNombre)
    {
        this.nombre = pNombre;
    }
    
    public String getNombre ()
    {
        return this.nombre;
    }
    
    public void setTelefono (int pTelefono)
    {
        this.telefono = pTelefono;
    }
    
    public int getTelefono ()
    {
        return this.telefono;
    }
    
    public void setDireccion (String pDireccion)
    {
        this.direccion = pDireccion;
    }
    
    public String getDireccion()
    {
        return this.direccion;
    }
    
    public void setHorario(String pHorario)
    {
        this.horarioDeAtencion = pHorario;
    }
    
    public String getHorario ()
    {
        return this.horarioDeAtencion;
    }
            
    
    
    
    
    
    
    
    
    
    
    
}
