/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.medicinaPrepagada.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import uk.co.jemos.podam.common.PodamExclude;

/**
 *clase que representa un Paciente Entity
 * @author MIGUELHOYOS
 */
@Entity
public class PacienteEntity extends UsuarioEntity implements Serializable{
    
    @PodamExclude    
    @OneToMany(mappedBy = "paciente", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<CitaLaboratorioEntity> citasLaboratorio;
    
    @PodamExclude  
    @OneToMany(mappedBy = "paciente", fetch = FetchType.LAZY)
    private List<FacturaEntity> facturas;
    
    @PodamExclude
    @OneToMany(mappedBy = "paciente", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TarjetaCreditoEntity> tarjetasCredito;
    
    @PodamExclude    
    @OneToMany(mappedBy = "pacienteAAtender", fetch = FetchType.LAZY)
    private List<CitaMedicaEntity> citasMedicas;
   
    @PodamExclude    
    @OneToMany(mappedBy = "paciente", fetch = FetchType.LAZY)
    private List<HistoriaClinicaEntity> historiasClinicas;
 

    private String nombre;
    private String fechaNacimiento;
    private String direccion;
    private Long numeroContacto;
    private String mail;
    private String eps;

    /**
     * @return the citasLaboratorio
     */
    public List<CitaLaboratorioEntity> getCitasLaboratorio() {
        return citasLaboratorio;
    }

    /**
     * @param citasLaboratorio the citasLaboratorio to set
     */
    public void setCitasLaboratorio(List<CitaLaboratorioEntity> citasLaboratorio) {
        this.citasLaboratorio = citasLaboratorio;
    }

    /**
     * @return the facturas
     */
    public List<FacturaEntity> getFacturas() {
        return facturas;
    }

    /**
     * @param facturas the facturas to set
     */
    public void setFacturas(List<FacturaEntity> facturas) {
        this.facturas = facturas;
    }

    /**
     * @return the tarjetasCredito
     */
    public List<TarjetaCreditoEntity> getTarjetasCredito() {
        return tarjetasCredito;
    }

    /**
     * @param tarjetasCredito the tarjetasCredito to set
     */
    public void setTarjetasCredito(List<TarjetaCreditoEntity> tarjetasCredito) {
        this.tarjetasCredito = tarjetasCredito;
    }

    /**
     * @return the citasMedicas
     */
    public List<CitaMedicaEntity> getCitasMedicas() {
        return citasMedicas;
    }

    /**
     * @param citasMedicas the citasMedicas to set
     */
    public void setCitasMedicas(List<CitaMedicaEntity> citasMedicas) {
        this.citasMedicas = citasMedicas;
    }

    /**
     * @return the historiasClinicas
     */
    public List<HistoriaClinicaEntity> getHistoriasClinicas() {
        return historiasClinicas;
    }

    /**
     * @param historiasClinicas the historiasClinicas to set
     */
    public void setHistoriasClinicas(List<HistoriaClinicaEntity> historiasClinicas) {
        this.historiasClinicas = historiasClinicas;
    }


    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the fechaNacimiento
     */
    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    /**
     * @param fechaNacimiento the fechaNacimiento to set
     */
    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    /**
     * @return the direccion
     */
    public String getDireccion() {
        return direccion;
    }

    /**
     * @param direccion the direccion to set
     */
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    /**
     * @return the numeroContacto
     */
    public Long getNumeroContacto() {
        return numeroContacto;
    }

    /**
     * @param numeroContacto the numeroContacto to set
     */
    public void setNumeroContacto(Long numeroContacto) {
        this.numeroContacto = numeroContacto;
    }

    /**
     * @return the mail
     */
    public String getMail() {
        return mail;
    }

    /**
     * @param mail the mail to set
     */
    public void setMail(String mail) {
        this.mail = mail;
    }

    /**
     * @return the eps
     */
    public String getEps() {
        return eps;
    }

    /**
     * @param eps the eps to set
     */
    public void setEps(String eps) {
        this.eps = eps;
    }


}
