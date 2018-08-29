/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.medicinaPrepagada.dtos;

import java.io.Serializable;
import java.util.List;

/**
*Clase que extiende de {@link PacienteDTO} para manejar las relaciones entre los
 * AuthorDTO y otros DTOs. Para conocer el contenido de un Autor vaya a la
 * documentacion de {@link PacienteDTO}
 *
 * Al serializarse como JSON esta clase implementa el siguiente modelo: <br>
 * <pre>
 *   {
 *      "cedula":number,
 *      "nombre":string,
 *      "fechaNacimiento":string,
 *      "direccion":string,
 *      "numeroContacto":number,
 *      "mail":string,
 *      "eps": string,
 *      "citasLaboratorio: [{@link CitasLaboratorioDTO}],
 *      "facturas":[{@link FacturasDTO}],
 *      "tarjetascredito":[{@link TarjetaCreditoDTO}],
 *      "citasmedicas":[{@link CitasMedicaDTO}],
 *      "historiasclinicas":[{@link HistoriaClinicaDTO}]
 *  }
 *   }
 * </pre> Por ejemplo un paciente se representa asi:<br>
 *
 * <pre>
 *
 *   {
 *      "cedula":1029849123,
 *      "nombre":"Pepito Perez",
 *      "fechaNacimiento":"19/02/1982",
 *      "direccion":"Av. 19 # 97 - 49",
 *      "numeroContacto":1234567890,
 *      "mail":"pepito@mail.com",
 *      "eps": "la menos mala"},
 *      "citaslaboratorio":[],
 *      "facturas":[],
 *      "tarjetascredito":[],
 *      "citasmedicas":[],
 *      "historiasclinicas":[]
 *   }
 *
 * </pre>
 *
 * @author MIGUELHOYOS
 */
public class PacienteDetailDTO extends PacienteDTO implements Serializable{
    
//    private List<CitasLaboratorioDTO> citasLaboratorio;
//    private List<FacturaDTO> facturas;
    private List<TarjetaCreditoDTO> tarjetasCredito;
    private List<CitaMedicaDTO> citasMedicas;
//    private List<HistoriaClinicaDTO> historiasClinicas;
    
    /**
     * constructor por defecto
     */
    public PacienteDetailDTO()
    {
        super();
    }
    
//    /**
//     *  Crea un objeto PacienteDetailDTO a partir de un objeto PacienteEntity
//     * incluyendo los atributos de PacienteDTO.
//     *
//     * @param PacienteEntity Entidad PacienteEntity desde la cual se va a crear el
//     * nuevo objeto.
//     *
//     */
//    public PacienteDetailDTO(PacienteEntity pacienteEntity){
//        super(pacienteEntity);
//    }
//    
//    /**
//     * Convierte un objeto AuthorDetailDTO a AuthorEntity incluyendo los
//     * atributos de AuthorDTO.
//     *
//     * @return Nueva objeto AuthorEntity.
//     *
//     */
//    @Override
//    public PacienteEntity toEntity(){
//        
//    }

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
