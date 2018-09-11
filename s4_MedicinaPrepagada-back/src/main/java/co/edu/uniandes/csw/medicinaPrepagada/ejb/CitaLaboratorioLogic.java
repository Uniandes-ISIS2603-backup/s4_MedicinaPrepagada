/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.medicinaPrepagada.ejb;

import co.edu.uniandes.csw.medicinaPrepagada.persistence.CitaLaboratorioPersistence;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Santiago Rojas
 */
@Stateless
public class CitaLaboratorioLogic {
    
    private static final Logger LOGGER = Logger.getLogger(CitaLaboratorioLogic.class.getName());
    
    
    @Inject
    private CitaLaboratorioPersistence citaPersistence;
    
    
    
    
    
}
