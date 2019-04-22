/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.bcos.LF;

import cl.bcos.CF.CFProfesiones;
import java.util.Iterator;
import org.apache.log4j.Logger;

/**
 *
 * @author aacantero
 */
public class LFProfesiones {

    private static final Logger Log = Logger.getLogger(LFProfesiones.class);

    public static int insertProfesion(String profesionName, String usuario_creador, String nombre_completo, String empresa) {
        Log.debug(Thread.currentThread().getStackTrace()[1].getMethodName());
        return CFProfesiones.insertProfesion(profesionName, nombre_completo, usuario_creador, empresa);
    }

    public static Iterator getProfesiones(String empresa) {
        Log.debug(Thread.currentThread().getStackTrace()[1].getMethodName());
        return CFProfesiones.getProfesiones(empresa);
    }

}
