/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iecisa.validator;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Usuario
 */
public class Test {
    public static void main(String args[]) { 
        try {
            Validator val = new Validator();
            List consulta = new ArrayList();
            //consulta.add(args[1]);
            consulta.add("rafael ricardo");
            consulta.add("cedula de ciudadania");
            int respuesta = val.validacion("C:\\Rafa\\SoportesHV\\escanear0001.pdf", consulta);
            //int respuesta = val.validacion(args[0], consulta);
            System.out.println(respuesta);
        } catch (Exception ex) {
            Logger.getLogger(Validator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
