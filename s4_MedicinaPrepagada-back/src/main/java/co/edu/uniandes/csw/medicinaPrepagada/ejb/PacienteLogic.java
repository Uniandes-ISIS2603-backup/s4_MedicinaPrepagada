/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.medicinaPrepagada.ejb;

import co.edu.uniandes.csw.medicinaPrepagada.entities.CitaLaboratorioEntity;
import co.edu.uniandes.csw.medicinaPrepagada.entities.CitaMedicaEntity;
import co.edu.uniandes.csw.medicinaPrepagada.entities.FacturaEntity;
import co.edu.uniandes.csw.medicinaPrepagada.entities.HistoriaClinicaEntity;
import co.edu.uniandes.csw.medicinaPrepagada.entities.PacienteEntity;
import co.edu.uniandes.csw.medicinaPrepagada.entities.TarjetaCreditoEntity;
import co.edu.uniandes.csw.medicinaPrepagada.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.medicinaPrepagada.persistence.PacientePersistence;
import co.edu.uniandes.csw.medicinaPrepagada.persistence.TarjetaCreditoPersistence;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *Clase que implementa la conexion con la persistencia para la entidad de
 * Paciente
 * 
 * 
 * @author MIGUELHOYOS
 */
@Stateless
public class PacienteLogic {
    
    @Inject
    private PacientePersistence persistence;
    
    @Inject
    private TarjetaCreditoPersistence tarjetaPersistence;
     
    /**
     * Guardar un nuevo Paciente
     * @param pacienteEntity: el paciente a guardar
     * @return el paciente guardado
     * @throws BusinessLogicException: se lanza cada vez que una regla de negocio no se cumpla
     */
    public PacienteEntity createPaciente(PacienteEntity pacienteEntity) throws BusinessLogicException{
        if(persistence.existePacienteByLogin(pacienteEntity.getLogin())){
            throw new BusinessLogicException("El login dado ya existe");
        }

        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        Date now = Date.from(Instant.now());
        try {
            Date fechaNacimientoEntidad = format.parse(pacienteEntity.getFechaNacimiento());
            if(fechaNacimientoEntidad.compareTo(now) > 0){
                throw new BusinessLogicException("La fecha de nacimiento es superior a la actual");
            }
        } catch (ParseException ex) {
            throw new BusinessLogicException("La fecha de nacimiento no cumple el formato");
        }
        
        validarReglasComunes(pacienteEntity);
        return persistence.create(pacienteEntity);
                
    }
    
    /**
     * Busca un paciente por el id
     * @param id: el id a buscar
     * @return: un Paciente con el id dado por param
     */
    public PacienteEntity getPaciente(Long id) throws BusinessLogicException{
        PacienteEntity pac = persistence.find(id);
        if(pac == null){
            throw new BusinessLogicException("El paciente con el id dado no existe");
        }
        return pac;
    }
    
    /**
     * retorna una lista con todos los pacientes
     * @return todos los pacientes
     */
    public List<PacienteEntity> getPacientes(){
        return persistence.findAll();
    }
    
    /**
     * Actualiza un paciente
     * @param pacienteEntity: el pacienteactualizado
     * @return: el paciente actualizado
     * @throws BusinessLogicException si no se cumple alguna regla de negocio
     */
    public PacienteEntity updatePaciente(PacienteEntity pacienteEntity) throws BusinessLogicException{
        PacienteEntity oldEntity = getPaciente(pacienteEntity.getId());
        if(!pacienteEntity.getFechaNacimiento().equals(oldEntity.getFechaNacimiento())){
            throw new BusinessLogicException("No se puede actualizar la fecha de nacimiento");
        }
        if(!pacienteEntity.getLogin().equals(oldEntity.getLogin())){
            throw new BusinessLogicException("No se puede actualizar el login");
        }
        if(pacienteEntity.getTarjetasCredito() == null || pacienteEntity.getTarjetasCredito().isEmpty()){
            pacienteEntity.setTarjetasCredito(oldEntity.getTarjetasCredito());
        }
        
        
        List<CitaMedicaEntity> oldCitasMedicas = oldEntity.getCitasMedicas();
        List<CitaMedicaEntity> newCitasMedicas = pacienteEntity.getCitasMedicas();
        if(!newCitasMedicas.isEmpty()){
            for (CitaMedicaEntity ent : oldCitasMedicas) {
            for (CitaMedicaEntity ent2 : newCitasMedicas) {
                if(ent.getId() != ent2.getId() && ent.getFecha().compareTo(ent2.getFecha()) == 0){
                    throw  new BusinessLogicException("No se puede tener dos citas medicas al tiempo");
                }
            }
        }
        }
        
        validarReglasComunes(pacienteEntity);
        return persistence.update(pacienteEntity);
        
    }
    
    /**
     * elimina un paciente con el id dado por parametro
     * @param id: el id del paciente que se desea eliminar
     */
    public void deletePaciente(Long id) throws BusinessLogicException{
        PacienteEntity pac = persistence.find(id);
        if(pac == null){
            throw new BusinessLogicException("El paciente con el id dado no existe");
        }
        List<HistoriaClinicaEntity> historiasClinicas = pac.getHistoriasClinicas();
        for(HistoriaClinicaEntity hist: historiasClinicas){
            hist.setPaciente(null);
        }
        List<CitaMedicaEntity> citasMed = pac.getCitasMedicas();
        for(CitaMedicaEntity citMed: citasMed){
            citMed.setPacienteAAtender(null);
        }
        List<FacturaEntity> facturas = pac.getFacturas();
        for(FacturaEntity fac : facturas){
            fac.setPaciente(null);
        }
        persistence.delete(id);
    }
    
    /**
     * Retorna las tarjetas de credito de un paciente
     * @param idPaciente: id del paciente
     * @return lista de tarjetas de credito del paciente
     */
    public List<TarjetaCreditoEntity> getTarjetasCreditoPaciente(Long idPaciente) throws BusinessLogicException{
        PacienteEntity pac = persistence.find(idPaciente);
        if(pac == null){
            throw new BusinessLogicException("El paciente con el id dado no existe");
        }
        return pac.getTarjetasCredito();
    }
    
    /**
     * agrega una tarjeta de credito al paciente
     * @param idPaciente: id del paciente al que se le agrega la tarjeta
     * @param tarjeta: entity de la tarjeta que se le asocia al paciente
     * @return tarjeta creada
     */
    public TarjetaCreditoEntity agregarTarjetaCreditoAPaciente(Long idPaciente, TarjetaCreditoEntity tarjeta) throws BusinessLogicException{
        PacienteEntity ent = persistence.find(idPaciente);
        if(ent == null){
            throw new BusinessLogicException("No existe el paciente");
        }
        ent.getTarjetasCredito().add(tarjeta);
        persistence.update(ent);
        tarjeta.setPaciente(ent);
        tarjetaPersistence.update(tarjeta);
        return tarjeta;
    }
    
    /**
     * da todas las citas de laboratorio de un paciente
     * @param idPaciente: id del paciente
     * @return 
     * @return: lista con todas las citas laboratorio de un paciente
     */
    public List<CitaLaboratorioEntity> darCitasLaboratorio(Long idPaciente) throws BusinessLogicException{
        PacienteEntity pac = persistence.find(idPaciente);
        if(pac == null){
            throw new BusinessLogicException("El paciente con el id dado no existe");
        }
        return pac.getCitasLaboratorio();
    }
    
    /**
     * da todas las facturas de un paciente
     * @param idPaciente: id del paciente
     * @return lista de todas las facturas de un paciente
     */
    public List<FacturaEntity> darFacturasPaciente(Long idPaciente) throws BusinessLogicException{
        PacienteEntity pac = persistence.find(idPaciente);
        if(pac == null){
            throw new BusinessLogicException("El paciente no existe");
        }
        return pac.getFacturas();
    }
    
    /**
     * da todas las citas medicas de un paciente
     * @param idPaciente: id del paciente
     * @return una lista con todas las citas medicas
     */
    public List<CitaMedicaEntity> darCitasMedicas(Long idPaciente) throws BusinessLogicException{
        PacienteEntity pac =  persistence.find(idPaciente);
        if(pac == null){
            throw new BusinessLogicException("El paciente con el id dado no existe");
        }
        return pac.getCitasMedicas();
    }
    
    /**
     * da todas las historias clinicas de un paciente
     * @param idPaciente: id del paciente
     * @return una lista con todas las historias clinicas del paciente
     */
    public List<HistoriaClinicaEntity> darHistoriasClinicas(Long idPaciente) throws BusinessLogicException{
        PacienteEntity pac = persistence.find(idPaciente);
        if(pac == null){
            throw new BusinessLogicException("El paciente no existe");
        }
        return pac.getHistoriasClinicas();
    }
    

    
    public boolean validarReglasComunes(PacienteEntity pacienteEntity)throws BusinessLogicException{


        if(pacienteEntity.getLogin() == null || pacienteEntity.getLogin().equals("")){
            throw new BusinessLogicException("El login no puede ser vacio o nulo");
        }
        
        String nameValidationPattern = "([A-Z][a-z]+\\s){2,3}[A-Z][a-z]+";
        Pattern patternName = Pattern.compile(nameValidationPattern);
        Matcher matchName = patternName.matcher(pacienteEntity.getNombre());
        if(!matchName.matches()){
            throw new BusinessLogicException("El formato del nombre no es valido: Nombre Apellidos");
        }
        String mailValidationPattern = "[a-z]+@[a-z]+\\.[a-z]+";
        Pattern patternMail = Pattern.compile(mailValidationPattern);
        Matcher matchMail = patternMail.matcher(pacienteEntity.getMail());
        if(!matchMail.matches()){
            throw new BusinessLogicException("El mail no sigue el formato de un mail");
        }
        // falta revidar casa o apto, solo recibe la direccion sin la parte especifica
        
        String direccionValidationPattern = "[A-Z][a-z]{1,2}\\s([A-Z][a-z]+|[0-9]{1,3})#([0-9]{1,3})[ABC]*-[0-9]{1,2}";
        Pattern patternDirecccion = Pattern.compile(direccionValidationPattern);
        Matcher matchDireccion = patternDirecccion.matcher(pacienteEntity.getDireccion());
        if(!matchDireccion.matches()){
            throw new BusinessLogicException("La direccion dada no sigue el formato");
        }
        
        String epsValidationPattern = "^Nueva\\sEps$|^Colsanitas$|^Sura$|^Aliansalud$|^Compensar$|^Salud\\sTotal$|^Coomeva$|^Famisanar$|^Cruz\\sBlanca$|^Cafesalud";
        Pattern patternEps = Pattern.compile(epsValidationPattern);
        Matcher matchEps = patternEps.matcher(pacienteEntity.getEps());
        if(!matchEps.matches()){
            throw new BusinessLogicException("La eps no existe");
        }
        
        String numeroContactoString = pacienteEntity.getNumeroContacto().toString();
        String numeroContactoValidationPattern = "([0-9]{10}|[0-9]{7})";
        Pattern patternNumeroContacto = Pattern.compile(numeroContactoValidationPattern);
        Matcher matchNumeroContacto = patternNumeroContacto.matcher(numeroContactoString);
        if(!matchNumeroContacto.matches()){
            throw new BusinessLogicException("El numero de contacto no es valido");
        }
        if(pacienteEntity.getDocumentoIdentidad() == 0 || pacienteEntity.getDocumentoIdentidad() == null){
            throw new BusinessLogicException("el documento de identidad ingresado no es valido");
        }
        return true;
         
    }
    
    
}
