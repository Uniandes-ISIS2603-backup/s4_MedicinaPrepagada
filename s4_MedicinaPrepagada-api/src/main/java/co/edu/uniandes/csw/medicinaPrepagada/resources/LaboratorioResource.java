/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.medicinaPrepagada.resources;
import co.edu.uniandes.csw.medicinaPrepagada.dtos.LaboratorioDTO;
import co.edu.uniandes.csw.medicinaPrepagada.exceptions.BusinessLogicException;
import java.util.logging.Level;

import java.util.logging.Logger;
import javax.ws.rs.POST;

/**
 *
 * @author Santiago Rojas
 */
public class LaboratorioResource {
 
    public static final Logger LOGGER = Logger.getLogger(LaboratorioResource.class.getName());
    
    
    @POST
    public LaboratorioDTO createLaboratorio (LaboratorioDTO Laboratorio) throws BusinessLogicException
    {
        LOGGER.log(Level.INFO, "LaboratorioDTO createLaboratorio: input: (0)", Laboratorio.toString());
        
        LaboratorioDTO nuevoLab = new LaboratorioDTO();
        
        LOGGER.log(Level.INFO, "LaboratorioDTO createLaboratorio: output: (0)", nuevoLab.toString());
        
        return nuevoLab;
    }
}
