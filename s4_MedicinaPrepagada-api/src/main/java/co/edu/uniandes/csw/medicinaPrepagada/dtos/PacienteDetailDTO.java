/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.medicinaPrepagada.dtos;

import java.util.List;

/**
 *
 * @author MIGUELHOYOS
 */
public class PacienteDetailDTO extends PacienteDTO{
    
//    private List<CitasLaboratorioDTO> citasLaboratorio;
//    private List<FacturaDTO> facturas;
    private List<TarjetaCreditoDTO> tarjetasCredito;
    private List<CitaMedicaDTO> citasMedicas;
//    private List<HistoriaClinicaDTO> historiasClinicas;

    /**
     * @return the citasLaboratorio
     */
//    public List<CitasLaboratorioDTO> getCitasLaboratorio() {
//        return citasLaboratorio;
//    }

    /**
     * @param citasLaboratorio the citasLaboratorio to set
     */
//    public void setCitasLaboratorio(List<CitasLaboratorioDTO> citasLaboratorio) {
//        this.citasLaboratorio = citasLaboratorio;
//    }

    /**
     * @return the facturas
     */
//    public List<FacturaDTO> getFacturas() {
//        return facturas;
//    }

    /**
     * @param facturas the facturas to set
     */
//    public void setFacturas(List<FacturaDTO> facturas) {
//        this.facturas = facturas;
//    }

    /**
     * @return the tarjetasCredito
     */
    public List<TarjetaCreditoDTO> getTarjetasCredito() {
        return tarjetasCredito;
    }

    /**
     * @param tarjetasCredito the tarjetasCredito to set
     */
    public void setTarjetasCredito(List<TarjetaCreditoDTO> tarjetasCredito) {
        this.tarjetasCredito = tarjetasCredito;
    }

    /**
     * @return the citasMedicas
     */
    public List<CitaMedicaDTO> getCitasMedicas() {
        return citasMedicas;
    }

    /**
     * @param citasMedicas the citasMedicas to set
     */
    public void setCitasMedicas(List<CitaMedicaDTO> citasMedicas) {
        this.citasMedicas = citasMedicas;
    }

    /**
     * @return the historiasClinicas
     */
//    public List<HistoriaClinicaDTO> getHistoriasClinicas() {
//        return historiasClinicas;
//    }

    /**
     * @param historiasClinicas the historiasClinicas to set
     */
//    public void setHistoriasClinicas(List<HistoriaClinicaDTO> historiasClinicas) {
//        this.historiasClinicas = historiasClinicas;
//    }
    
}
