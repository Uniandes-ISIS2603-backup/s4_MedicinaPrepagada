/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.medicinaPrepagada.ejb;

import co.edu.uniandes.csw.medicinaPrepagada.entities.TarjetaCreditoEntity;
import co.edu.uniandes.csw.medicinaPrepagada.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.medicinaPrepagada.persistence.TarjetaCreditoPersistence;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * Clase que implementa la conexion con la persistencia para la entidad de
 * TarjetaCredito
 * 
 * @author MIGUELHOYOS
 */
@Stateless
public class TarjetaCreditoLogic {
    
    @Inject
    private TarjetaCreditoPersistence persistence;
    
    /**
     * Guarda una arjeta de credito
     * @param tarjetaCreditoEntity, la entidad a guardar
     * @return la tarjeta guardada
     * @throws BusinessLogicException, si no se cumple alguna regla de negocio 
     */
    public TarjetaCreditoEntity createTarjetaCredito(TarjetaCreditoEntity tarjetaCreditoEntity)throws BusinessLogicException{
        if(!validarNumero(tarjetaCreditoEntity.getNumero())){
            throw new BusinessLogicException("El numero de la tarjeta no es valido");
        }
        if(!validarNumeroConFranquicia(tarjetaCreditoEntity.getNumero(), tarjetaCreditoEntity.getFranquicia())){
            throw new BusinessLogicException("En numero no coincide con la franquicia");
        }
        SimpleDateFormat format = new SimpleDateFormat("MM/yyyy");
        Calendar nowCal = Calendar.getInstance();
        Date now = nowCal.getTime();
        nowCal.add(Calendar.YEAR, 20);
        Date veinteYrs = nowCal.getTime(); 
        try {
            String[] fechaForm = tarjetaCreditoEntity.getFechaExpiracion().split("/");
            String fechaCompleta = fechaForm[0]+"/20"+fechaForm[1];
            Date fechaExpiracionEntidad = format.parse(fechaCompleta);
            if(fechaExpiracionEntidad.compareTo(now) < 0){
                throw new BusinessLogicException("La tarjeta de credito ya esta vencida");
            }
            if(fechaExpiracionEntidad.compareTo(veinteYrs) > 0){
                throw new BusinessLogicException("la fecha de expiracion es 20 anos superior a la actual, lo que la hace no valida");
            }
        } catch (ParseException ex) {
            throw new BusinessLogicException("La fecha de expiracion no cumple el formato");
        }
        String ccvValidationPattern = "[0-9]{3}";
        Pattern patternCcv = Pattern.compile(ccvValidationPattern);
        String ccvEnString = tarjetaCreditoEntity.getCodigoSeguridad().toString();
        Matcher matchCcv = patternCcv.matcher(ccvEnString);
        if(!matchCcv.matches()){
            throw new BusinessLogicException("El codigo de seguridad no sigue el formato, solo puede ser tres digitos numericos");
        }
        String nombreValidartionPattern = "([A-Z]+\\s){2,3}[A-Z]+";
        Pattern patternNombre = Pattern.compile(nombreValidartionPattern);
        Matcher matchNombre = patternNombre.matcher(tarjetaCreditoEntity.getNombreEnTarjeta());
        if(!matchNombre.matches()){
            throw new BusinessLogicException("El nombre en la tarjeta no sigue un formatoValido");
        }
        return persistence.create(tarjetaCreditoEntity);
        
    }
    
    /**
     * Retorna una tarjetaDeCredito con el id dado pro param
     * @param id: el id corespondiente a la tarjetaDeCredito
     * @return la tarjeta de credito buscada
     */
    public TarjetaCreditoEntity getTarjetaCredito(Long id){
        return persistence.find(id);
    }
    
    /**
     * Borra una tarjeta de credito
     * @param id de la tarjeta que se desea eliminar
     */
    public void deleteTarjetaCredito(Long id){
        persistence.delete(id);
    }
    
    /**
     * valida el numero
     * @param numero: numero de la tarjeta
     * @return si el numero es valido o no
     */
    public boolean validarNumero(Long numero){
        int sumaPares = 0;
        int sumaInpares = 0;
        
        int i = 1;
        while(numero>0){
            int num = (int) (numero%10);
            if(i%2 == 0)
            {
                int doblePares = 2*num;
                int sumaDigitos = 0;
                if(doblePares/10 >= 1)
                {
                    sumaDigitos = 1 + doblePares%10;
                }
                else{
                    sumaDigitos = doblePares;
                }
                sumaPares += sumaDigitos;
            }
            else{
                sumaInpares += num;
            }
            numero = numero/10;
            i++;
        }
        return (sumaInpares+sumaPares)%10 == 0;
    }
    
    /**
     * valida que el numero y la franquicia coincidan
     * @param numero, numero de tarjeta
     * @param franquicia
     * @return si coinciden
     */
    public boolean validarNumeroConFranquicia(Long numero, String franquicia){
        Long primerNum = numero;
        while(primerNum>9){
           primerNum = primerNum/10; 
        }
        if(primerNum == 3 && (franquicia.equals("American Express") || franquicia.equals("Diners Club"))){
            return true;
        }
        if(primerNum == 4 && franquicia.equals("Visa")){
            return true;
        }
        return primerNum == 5 && franquicia.equals("MasterCard");
    }
}
