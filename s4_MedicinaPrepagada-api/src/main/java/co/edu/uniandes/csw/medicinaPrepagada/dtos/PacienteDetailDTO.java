/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.medicinaPrepagada.dtos;

import co.edu.uniandes.csw.medicinaPrepagada.entities.CitaLaboratorioEntity;
import co.edu.uniandes.csw.medicinaPrepagada.entities.CitaMedicaEntity;
import co.edu.uniandes.csw.medicinaPrepagada.entities.FacturaEntity;
import co.edu.uniandes.csw.medicinaPrepagada.entities.HistoriaClinicaEntity;
import co.edu.uniandes.csw.medicinaPrepagada.entities.PacienteEntity;
import co.edu.uniandes.csw.medicinaPrepagada.entities.TarjetaCreditoEntity;
import java.io.Serializable;
import java.util.LinkedList;
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
    
    private List<CitaLaboratorioDTO> citasLaboratorio;
    private List<FacturaDTO> facturas;
    private List<TarjetaCreditoDTO> tarjetasCredito;
    private List<CitaMedicaDTO> citasMedicas;
    private List<HistoriaClinicaDTO> historiasClinicas;
    
    /**
     * constructor por defecto
     */
    public PacienteDetailDTO()
    {
        super();
    }
    
    /**
     *  Crea un objeto PacienteDetailDTO a partir de un objeto PacienteEntity
     * incluyendo los atributos de PacienteDTO.
     *
     * @param pacienteEntityEntidad PacienteEntity desde la cual se va a crear el
     * nuevo objeto.
     *
     */
    public PacienteDetailDTO(PacienteEntity pacienteEntity){
        super(pacienteEntity);
        if(pacienteEntity!=null){
           if(pacienteEntity.getCitasLaboratorio() != null){
               citasLaboratorio = new LinkedList<>();
               for(CitaLaboratorioEntity citaLab : pacienteEntity.getCitasLaboratorio()){
//                   citasLaboratorio.add(new CitaLaboratorioDTO(citaLab));
               }
           }
           if(pacienteEntity.getFacturas() != null){
               facturas = new LinkedList<>();
               for(FacturaEntity fact: pacienteEntity.getFacturas()){
//                   facturas.add(new FacturaDTO(fact));
               }
           }
           if(pacienteEntity.getTarjetasCredito() != null){
               tarjetasCredito = new LinkedList<>();
               for(TarjetaCreditoEntity tarj : pacienteEntity.getTarjetasCredito()){
                   tarjetasCredito.add(new TarjetaCreditoDTO(tarj));
               }
           }
           if(pacienteEntity.getCitasMedicas() != null){
               citasMedicas = new LinkedList<>();
               for(CitaMedicaEntity med:pacienteEntity.getCitasMedicas()){
//                  citasMedicas.add(new CitaMedicaDTO(med));
               }
           }
           if(pacienteEntity.getHistoriasClinicas() != null){
               historiasClinicas = new LinkedList<>();
               for(HistoriaClinicaEntity his : pacienteEntity.getHistoriasClinicas()){
//                   historiasClinicas.add(new HistoriaClinicaDTO(his));
               }
           }
        }
    }
    
    /**
     * Convierte un objeto PacienteDetailDTO a PacienteEntity incluyendo los
     * atributos de PacienteDTO.
     *
     * @return Nueva objeto PacienteEntity.
     *
     */
    @Override
    public PacienteEntity toEntity(){
     PacienteEntity pacienteEntity = super.toEntity();
     if(citasLaboratorio != null){
         List<CitaLaboratorioEntity> citL = new LinkedList<>();
         for(CitaLaboratorioDTO cit:citasLaboratorio){
             citL.add(cit.toEntity());
         }
         pacienteEntity.setCitasLaboratorio(citL);
     }
     if(citasMedicas != null){
         List<CitaMedicaEntity> citMeds = new LinkedList<>();
         for(CitaMedicaDTO citM:citasMedicas ){
             citMeds.add(citM.toEntity());
         }
         pacienteEntity.setCitasMedicas(citMeds);
     }
     if(facturas != null){
        List<FacturaEntity> facts = new LinkedList<>();
        for(FacturaDTO fact:facturas){
            facts.add(fact.toEntity());
        }
        pacienteEntity.setFacturas(facts);
     }
     if(historiasClinicas != null){
         List<HistoriaClinicaEntity> histoClin = new LinkedList<>();
         for(HistoriaClinicaDTO hist:historiasClinicas){
             histoClin.add(hist.toEntity());
         }
         pacienteEntity.setHistoriasClinicas(histoClin);
     }
     if(tarjetasCredito != null){
         List<TarjetaCreditoEntity> tarjetas = new LinkedList<>();
         for(TarjetaCreditoDTO tarjeta:tarjetasCredito){
             tarjetas.add(tarjeta.toEntity());
         }
         pacienteEntity.setTarjetasCredito(tarjetas);
     }
     return pacienteEntity;

    }

    /**
     * Retorna las citasLaboratorio del DetailDto
     * @return the citasLaboratorio
     */
    public List<CitaLaboratorioDTO> getCitasLaboratorio() {
        return citasLaboratorio;
    }

    /**
     * Sets las citasLaboratorio del DetailDto
     * @param citasLaboratorio the citasLaboratorio to set
     */
    public void setCitasLaboratorio(List<CitaLaboratorioDTO> citasLaboratorio) {
        this.citasLaboratorio = citasLaboratorio;
    }

    /**
     * retorna las facturas del DetailDTO
     * @return the facturas
     */
    public List<FacturaDTO> getFacturas() {
        return facturas;
    }

    /**
     * sets las facturas del DetailDTO
     * @param facturas the facturas to set
     */
    public void setFacturas(List<FacturaDTO> facturas) {
        this.facturas = facturas;
    }

    /**
     * retorna las tarjetas de credito del DetailDTO
     * @return the tarjetasCredito
     */
    public List<TarjetaCreditoDTO> getTarjetasCredito() {
        return tarjetasCredito;
    }

    /**
     * sets las tarjetasCredito del DetailDTO
     * @param tarjetasCredito the tarjetasCredito to set
     */
    public void setTarjetasCredito(List<TarjetaCreditoDTO> tarjetasCredito) {
        this.tarjetasCredito = tarjetasCredito;
    }

    /**
     * retorna las citasMedicas del DetailDTO
     * @return the citasMedicas
     */
    public List<CitaMedicaDTO> getCitasMedicas() {
        return citasMedicas;
    }

    /**
     * sets las citas medicas del DetailDTO
     * @param citasMedicas the citasMedicas to set
     */
    public void setCitasMedicas(List<CitaMedicaDTO> citasMedicas) {
        this.citasMedicas = citasMedicas;
    }

    /**
     * retorna las historias clinicas del DetailDTO
     * @return the historiasClinicas
     */
    public List<HistoriaClinicaDTO> getHistoriasClinicas() {
        return historiasClinicas;
    }

    /**
     * sets las hsitorias clinicas del DetailDTO
     * @param historiasClinicas the historiasClinicas to set
     */
    public void setHistoriasClinicas(List<HistoriaClinicaDTO> historiasClinicas) {
        this.historiasClinicas = historiasClinicas;
    }
    
}
