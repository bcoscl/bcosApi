/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.bcos.LF;


import org.apache.log4j.Logger;
import cl.bcos.CF.*;
import java.util.Iterator;

/**
 *
 * @author aacantero
 * LOGIC FILE LF
 */
public class LFPlanes {

    private static final Logger Log = Logger.getLogger(LFPlanes.class);

   
    public static int insertPlan(String nombre_plan, String numero_maximo, String usuario_creador, String nombreUsuario) {
        Log.debug(Thread.currentThread().getStackTrace()[1].getMethodName());
              
        return CFPlanes.insertPlan(nombre_plan,numero_maximo,usuario_creador,nombreUsuario);
    }

    public static Iterator selectPlanes() {
        Log.debug(Thread.currentThread().getStackTrace()[1].getMethodName());
        return CFPlanes.selectPlanes();
    }

    

    
    
}
