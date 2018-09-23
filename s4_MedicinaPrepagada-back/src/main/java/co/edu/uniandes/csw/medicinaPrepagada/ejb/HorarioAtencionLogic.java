/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.medicinaPrepagada.ejb;

import co.edu.uniandes.csw.medicinaPrepagada.entities.ConsultorioEntity;
import co.edu.uniandes.csw.medicinaPrepagada.entities.EspecialidadEntity;
import co.edu.uniandes.csw.medicinaPrepagada.entities.HorarioAtencionEntity;
import co.edu.uniandes.csw.medicinaPrepagada.entities.MedicoEntity;
import co.edu.uniandes.csw.medicinaPrepagada.entities.SedeEntity;
import co.edu.uniandes.csw.medicinaPrepagada.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.medicinaPrepagada.persistence.HorarioAtencionPersistence;
import co.edu.uniandes.csw.medicinaPrepagada.persistence.MedicoPersistence;
import co.edu.uniandes.csw.medicinaPrepagada.persistence.SedePersistence;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Simon Guzman
 */
@Stateless
public class HorarioAtencionLogic 
{
    
   private static final Logger LOGGER = Logger.getLogger(HorarioAtencionLogic.class.getName());

    @Inject
    private HorarioAtencionPersistence persistence; 
    
    @Inject
    private MedicoPersistence medicoPersistence;
    
    @Inject 
    private SedePersistence sedePersistence;
    
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
    
    
         /**
     * Se encarga de crear un HorarioAtencion en la base de datos.
     *
     * @param horarioAtencionEntity Objeto de HorarioAtencionEntity con los datos nuevos
     * @param sedeEntity
     * @return Objeto de HorarioAtencionEntity con los datos nuevos y su ID.
     * @throws co.edu.uniandes.csw.medicinaPrepagada.exceptions.BusinessLogicException
     */
    public HorarioAtencionEntity createHorarioAtencion(HorarioAtencionEntity horarioAtencionEntity, SedeEntity sedeEntity) throws BusinessLogicException
    {
        LOGGER.log(Level.INFO, "Inicia proceso de creación del horarioAtencion" + horarioAtencionEntity.toString());
        Long idMedico = horarioAtencionEntity.getMedico().getId();
        
        //Valida que la fecha sea despues de la actual
        if (!validatePostActual(horarioAtencionEntity.getFechaInicio()))
            throw new BusinessLogicException("La fecha de inicio debe ser despues a la fecha actual");
        //Valida que este entre los horarios de trabajo de la empresa
        if (!validateHorarioTrabajo(horarioAtencionEntity.getFechaInicio(), horarioAtencionEntity.getFechaFin()))
            throw new BusinessLogicException("Los horarios de antencion solo pueden estar entre las 6 am y las 6 pm");
        //Valida que los horarios no sobrepasen on mes de la fecha de cracion
        if (!validateLessThanMonth(horarioAtencionEntity.getFechaFin()))
            throw new BusinessLogicException("Solo puede crear horarios hasta 1 mes despues de la fecha actual");
        //Valida que el horario de atencion sea en el mismo dia
        if (!validateOneDay(horarioAtencionEntity.getFechaInicio(), horarioAtencionEntity.getFechaFin()))
            throw new BusinessLogicException("Su horario de atencion debe empezar y terminar en el mismo dia");
        //Valida que los horarios empiecen y terminen en 00, 20 o 40
        if (!validateMinutos(horarioAtencionEntity.getFechaInicio(), horarioAtencionEntity.getFechaFin()))
            throw new BusinessLogicException("Sus horarios de atencion deben empezar y acabar en xx:00, xx:20 o xx:40");
        //Valida que el horario que se intenta crear no genere problema con los horarios actuales del medico
        if (!validateHorMedico(horarioAtencionEntity.getFechaInicio(), horarioAtencionEntity.getFechaFin(), idMedico))
            throw new BusinessLogicException("No puede crear este horario ya que se cruza con otro horario que ya tiene");
        // Busca que exista un consultorio al que se le pueda asignar el horario
        ConsultorioEntity pConsult = findConsultorio(sedeEntity.getId(), idMedico, horarioAtencionEntity.getFechaInicio(), horarioAtencionEntity.getFechaFin());
        if (pConsult != null)
        {
            horarioAtencionEntity.setConsultorio(pConsult);
            horarioAtencionEntity.setMedico(medicoPersistence.find(idMedico));
        }
        else
        {
            throw new BusinessLogicException("No es posible crear su horario ya que no ahi un consultorio disponible en la sede. Pruebe una sede diferente o un horario");
        }
        HorarioAtencionEntity newHorarioAtencionEntity = persistence.create(horarioAtencionEntity);
        LOGGER.log(Level.INFO, "Termina proceso de creación del horarioAtencion");
        return newHorarioAtencionEntity;
    }
    
    
    
    /**
     * Obtiene la lista de los registros de HorarioAtencion.
     *
     * @return Colección de objetos de HorarioAtencionEntity.
     */
    public List<HorarioAtencionEntity> getHorarioAtencions() 
    {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todos los horarioAtenciones");
        List<HorarioAtencionEntity> lista = persistence.findAll();
        LOGGER.log(Level.INFO, "Termina proceso de consultar todos los horarioAtenciones");
        return lista;
    }

    /**
     * Obtiene los datos de una instancia de HorarioAtencion a partir de su ID.
     *
     * @param horarioAtencionsId Identificador de la instancia a consultar
     * @return Instancia de HorarioAtencionEntity con los datos del HorarioAtencion consultado.
     */
    public HorarioAtencionEntity getHorarioAtencion(Long horarioAtencionsId) 
    {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar el horarioAtencion con id = {0}", horarioAtencionsId);
        HorarioAtencionEntity horarioAtencionEntity = persistence.find(horarioAtencionsId);
        if (horarioAtencionEntity == null)
        {
            LOGGER.log(Level.SEVERE, "La horarioAtencion con el id = {0} no existe", horarioAtencionsId);
        }
        LOGGER.log(Level.INFO, "Termina proceso de consultar el horarioAtencion con id = {0}", horarioAtencionsId);
        return horarioAtencionEntity;
    }
    
    
    
        /**
     * Actualiza la información de una instancia de HorarioAtencion.
     *
     * @param horarioAtencionsId Identificador de la instancia a actualizar
     * @param horarioAtencionEntity Instancia de HorarioAtencionEntity con los nuevos datos.
     * @return Instancia de HorarioAtencionEntity con los datos actualizados.
     */
    public HorarioAtencionEntity updateHorarioAtencion(Long horarioAtencionsId, HorarioAtencionEntity horarioAtencionEntity) throws BusinessLogicException
    {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar el horarioAtencion con id = {0}", horarioAtencionsId);
        
        HorarioAtencionEntity pHorarioAtencionOld = persistence.find(horarioAtencionsId);
        //Verifica que la horarioAtencion que se intenta modificar exista
        if (pHorarioAtencionOld == null)
            throw new BusinessLogicException ("La horarioAtencion que intenta modificar no existe");
        //Verifica que no se intente cambiar el Id
        if (horarioAtencionEntity.getId() != horarioAtencionsId)
            throw new BusinessLogicException("No se puede cambiar el id de la horarioAtencion");
       //Valida que la fecha sea despues de la actual
        if (!validatePostActual(horarioAtencionEntity.getFechaInicio()))
            throw new BusinessLogicException("La fecha de inicio debe ser despues a la fecha actual");
        //Valida que este entre los horarios de trabajo de la empresa
        if (!validateHorarioTrabajo(horarioAtencionEntity.getFechaInicio(), horarioAtencionEntity.getFechaFin()))
            throw new BusinessLogicException("Los horarios de antencion solo pueden estar entre las 6 am y las 6 pm");
        //Valida que los horarios no sobrepasen on mes de la fecha de cracion
        if (!validateLessThanMonth(horarioAtencionEntity.getFechaFin()))
            throw new BusinessLogicException("Solo puede actualizar horarios hasta 1 mes despues de la fecha actual");
        //Valida que el horario de atencion sea en el mismo dia
        if (!validateOneDay(horarioAtencionEntity.getFechaInicio(), horarioAtencionEntity.getFechaFin()))
            throw new BusinessLogicException("Su horario de atencion debe empezar y terminar en el mismo dia");
        //Valida que los horarios empiecen y terminen en 00, 20 o 40
        if (!validateMinutos(horarioAtencionEntity.getFechaInicio(), horarioAtencionEntity.getFechaFin()))
            throw new BusinessLogicException("Sus horarios de atencion deben empezar y acabar en xx:00, xx:20 o xx:40");
       //Valida que el horario editado no genere problema con los horarios del medico 
        if (!validateHorMedicoUpdate(horarioAtencionEntity))
            throw new BusinessLogicException("No puede cambiar este horario ya que se cruza con otro horario que ya tiene");
        // Busca que exista un consultorio al que se le pueda asignar el horario
        ConsultorioEntity pConsult = findConsultorio(horarioAtencionEntity.getConsultorio().getSede().getId(), 
                horarioAtencionEntity.getMedico().getId(), horarioAtencionEntity.getFechaInicio(), horarioAtencionEntity.getFechaFin());
        if (pConsult != null)
        {
            horarioAtencionEntity.setConsultorio(pConsult);
            horarioAtencionEntity.setMedico(medicoPersistence.find(horarioAtencionEntity.getMedico().getId()));
        }
        else
        {
            throw new BusinessLogicException("No es posible cambiar su horario ya que no ahi un consultorio disponible en la sede. Pruebe una sede diferente o un horario");
        }
        
        

        HorarioAtencionEntity newHorarioAtencionEntity = persistence.update(horarioAtencionEntity);
        LOGGER.log(Level.INFO, "Termina proceso de actualizar el horarioAtencion con id = {0}", horarioAtencionsId);
        return newHorarioAtencionEntity;
    }

    /**
     * Elimina una instancia de HorarioAtencion de la base de datos.
     *
     * @param horarioAtencionsId Identificador de la instancia a eliminar.
     * @throws BusinessLogicException si el horarioAtencion tiene libros asociados.
     */
    public void deleteHorarioAtencion(Long horarioAtencionsId) throws BusinessLogicException 
    {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar el horarioAtencion con id = {0}", horarioAtencionsId);
  
        
        //Verifica que la horarioAtencion que se intenta modificar exista
        if (persistence.find(horarioAtencionsId) == null)
            throw new BusinessLogicException ("La horarioAtencion que intenta modificar no existe");
        //Verifica que el horario no tenga citas medicas
        if (!persistence.find(horarioAtencionsId).getCitasMedicas().isEmpty())
            throw new BusinessLogicException("No puede eliminar el horario por que tiene citas medicas asignadas");
        
        persistence.delete(horarioAtencionsId);
        LOGGER.log(Level.INFO, "Termina proceso de borrar el horarioAtencion con id = {0}", horarioAtencionsId);
    }
    
    
    /**
     * Verifica que el horario que intenta crear este en el horario de trabajo de la empresa
     * @param pHoraInicio
     * @param pHoraFin
     * @return True si el las horas estan entre las 6 am y las 6 pm (18)
     */
    public boolean validateHorarioTrabajo (Date pHoraInicio, Date pHoraFin)
    {
        
        String inicio = dateFormat.format( pHoraInicio); 
        String fin = dateFormat.format( pHoraFin); 
        
        //Particion de inicio
        String[] partsInicio = inicio.split(" ");
        String[] partsInicio2 = partsInicio[1].split(":");
        int horaInicio =Integer.parseInt(partsInicio2[0]);
        
        //Particion de fin
        String[] partsFin = fin.split(" ");
        String[] partsFin2 = partsFin[1].split(":");
        int horaFin =Integer.parseInt(partsFin2[0]);
        
         
        return (horaInicio >= 6 && horaFin <=18);
    }
    
    /**
     * Verifica que los horarios empiecen y terminen en xx:00, xx:20 o xx:40
     * @param pHoraInicio
     * @param pHoraFin
     * @return true si se cumple la regla
     */ 
    public boolean validateMinutos (Date pHoraInicio, Date pHoraFin)
    {
        String inicio = dateFormat.format( pHoraInicio); 
        String fin = dateFormat.format( pHoraFin); 
        
        //Particion de inicio
        String[] partsInicio = inicio.split(" ");
        String[] partsInicio2 = partsInicio[1].split(":");
        int horaInicio =Integer.parseInt(partsInicio2[1]);
        
        //Particion de fin
        String[] partsFin = fin.split(" ");
        String[] partsFin2 = partsFin[1].split(":");
        int horaFin =Integer.parseInt(partsFin2[1]);
        
        return ((horaInicio == 0 || horaInicio ==20 || horaInicio == 40) && 
                (horaFin == 0 || horaFin ==20 || horaFin == 40));
        
        
    }
    
    /**
     * Compara que el horario de atencion empiece y acabe en el mismo dia
     * @param pHoraInicio
     * @param pHoraFin
     * @return true si estan en el mismo dia
     */
    public boolean validateOneDay (Date pHoraInicio, Date pHoraFin)
    {
        
        String inicio = dateFormat.format( pHoraInicio); 
        String fin = dateFormat.format( pHoraFin); 
        
        //Particion de inicio
        String[] partsInicio = inicio.split(" ");
        String[] partsInicio2 = partsInicio[0].split("-");
        //int horaInicio =Integer.parseInt(partsInicio2[1]);
        
        //Particion de fin
        String[] partsFin = fin.split(" ");
        String[] partsFin2 = partsFin[0].split("-");
        //int horaFin =Integer.parseInt(partsFin2[1]); 
        
        int cont=0;
        while (cont <3)
        {
            
            if (partsInicio2[cont].compareTo(partsFin2[cont])!= 0)
                return false;
            
            cont++;
        }
        
        return true;
    }
    
    /**
     * Valida que la fecha de inicio sea mayor que la fecha actual
     * @param pHoraInicio
     * @return true si la fecha es despues de la actual
     */
    public boolean validatePostActual (Date pHoraInicio)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR_OF_DAY, 0);
        Date dateActual =  calendar.getTime();
        
        return (pHoraInicio.after(dateActual));
        
        
    }
    
     /**
     * Valida que la fecha final no sobrepase un mes de lafecha actual
     * @param pHoraFin
     * @return 
     */
    public boolean validateLessThanMonth (Date pHoraFin)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, +1);
        calendar.add(Calendar.HOUR_OF_DAY, 0);
        Date dateMax =  calendar.getTime();  
        
        return (pHoraFin.before(dateMax));
        
        
    }
    
    /**
     * Verifica que el horario no genere conflicto con los horariosactuales del medico en create
     * @param pHoraInicio
     * @param pHoraFin
     * @param id
     * @return true si no se genera ningun problema con los horarios actuales del medico
     */
    public boolean validateHorMedico (Date pHoraInicio, Date pHoraFin, Long id)
    {
        MedicoEntity medico = medicoPersistence.find(id);
        List <HorarioAtencionEntity> horarios = medico.getHorariosAtencion();
        for (int i = 0; i < horarios.size();i++)
        {
            if ((pHoraInicio.after(horarios.get(i).getFechaInicio()) && pHoraInicio.before(horarios.get(i).getFechaFin()))
                    || (pHoraFin.after(horarios.get(i).getFechaInicio()) && pHoraFin.before(horarios.get(i).getFechaFin())) )
            {
                return false;
            }
        }
        
        return true;
    }
    /**
     * Verifica que el horario no genere conflicto con los horariosactuales del medico en update
     * @param horEntity
     * @return true si no se genera ningun problema con los horarios actuales del medico
     */
    public boolean validateHorMedicoUpdate (HorarioAtencionEntity horEntity)
    {
        Date pHoraInicio = horEntity.getFechaInicio();
        Date pHoraFin = horEntity.getFechaFin();
        
        MedicoEntity medico = medicoPersistence.find(horEntity.getMedico().getId());
        List <HorarioAtencionEntity> horarios = medico.getHorariosAtencion();
        for (int i = 0; i < horarios.size();i++)
        {
            if (horarios.get(i).getId() != horEntity.getId() &&
                    ((pHoraInicio.after(horarios.get(i).getFechaInicio()) && pHoraInicio.before(horarios.get(i).getFechaFin()))
                    || (pHoraFin.after(horarios.get(i).getFechaInicio()) && pHoraFin.before(horarios.get(i).getFechaFin()))) )
            {
                return false;
            }
        }
        
        return true;
    }
    /**
     * Retorna un consultorio disponible para el horario
     * @param idSede
     * @param idMedico
     * @param pHoraInicio
     * @param pHoraFin
     * @return Retorna un consultorio entity si encuentra uno adecuado, de lo contrario retorna null
     */
    public ConsultorioEntity findConsultorio (Long idSede, Long idMedico, Date pHoraInicio, Date pHoraFin)
    {
        SedeEntity sede = sedePersistence.find(idSede);
        EspecialidadEntity especialidad =  medicoPersistence.find(idMedico).getEspecialidad();
        List <ConsultorioEntity> consultorios = sede.getConsultorios();
        
        //Recorro los consultorios
        for (int i = 0; i < consultorios.size();i++)
        {
            //Entra solo si la especialida del medico es la misma del consultorio
            if (especialidad.equals(consultorios.get(i).getEspecialidad()))
            {
                //Lista de horarios de atencion del consultorio
              List <HorarioAtencionEntity> horarios = consultorios.get(i).getHorariosAtencion();
              //Si no se encuentra ningun problema se mantendra en true
                Boolean sirve = true;
                 for (int j = 0; j < horarios.size();j++)
                 {
                        
                     if ((pHoraInicio.after(horarios.get(i).getFechaInicio()) && pHoraInicio.before(horarios.get(i).getFechaFin()))
                             || (pHoraFin.after(horarios.get(i).getFechaInicio()) && pHoraFin.before(horarios.get(i).getFechaFin())) )
                     {
                         sirve = false;
                     }
                 }
                 if (sirve)
                     return consultorios.get(i);
            }   
        }
        return null;
    }
    
}
