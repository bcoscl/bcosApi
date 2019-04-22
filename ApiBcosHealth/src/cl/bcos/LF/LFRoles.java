/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.bcos.LF;

import cl.bcos.CF.CFRoles;
import cl.bcos.CF.CFSuscripcion;
import java.util.Iterator;
import org.apache.log4j.Logger;

/**
 *
 * @author aacantero
 */
public class LFRoles {
    
    private static final Logger Log = Logger.getLogger(LFRoles.class);

    public static int insertRole(String RoleName, String usuario_creador, String nombre_completo) {
       Log.debug(Thread.currentThread().getStackTrace()[1].getMethodName());
        return CFRoles.insertRoles(RoleName, nombre_completo, usuario_creador);
    }
     public static Iterator getRoles() {
        Log.debug(Thread.currentThread().getStackTrace()[1].getMethodName());
        return CFRoles.getRoles();
    }
    
}
