/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.bcos.LF;

import cl.bcos.CF.CFUsuarios;
import cl.bcos.RF.RFUsuarios;
import cl.bcos.data.Registro;
import java.util.Iterator;
import org.apache.log4j.Logger;

/**
 *
 * @author aacantero
 */
public class LFUsuarios {

    private static final Logger Log = Logger.getLogger(LFUsuarios.class);

    public static int insertUsuario(String numuser_user, String nombre_user, String apellido_user, String email_contacto_user, String numero_telefono_user, String profesion_select, String textarea_obs, String sucursal_select, String roles_select, String password, String checkbox_activo, String usuario_creador, String nombre_completo, String empresaName, String rowUserId) {
        Log.debug(Thread.currentThread().getStackTrace()[1].getMethodName());
        return CFUsuarios.insertUsuario(numuser_user, nombre_user, apellido_user, email_contacto_user, numero_telefono_user, profesion_select, textarea_obs, sucursal_select, roles_select, password, checkbox_activo, usuario_creador, nombre_completo, empresaName,rowUserId);
    }

    /*Revisa que el registro no esta duplicado en la base de datos*/

    public static String existeRegistro(String numuser, String empresa) {
        Log.debug(Thread.currentThread().getStackTrace()[1].getMethodName());
        Iterator it = CFUsuarios.existeRegistro(numuser, empresa);
        String existeRegistro = "NO";

        while (it.hasNext()) {
            Registro reg = (Registro) it.next();
            existeRegistro = reg.get(RFUsuarios.existeRegistro);
        }
        return existeRegistro;
    }
    
    public static Iterator getUserInformation(String numuser) {
        Log.debug(Thread.currentThread().getStackTrace()[1].getMethodName());
        Iterator it = CFUsuarios.getUserInformation(numuser);
        return it;
    }

    public static String getNewUserId() {
        Log.debug(Thread.currentThread().getStackTrace()[1].getMethodName());
        String Response = CFUsuarios.getNewUserId();

        return Response;
    }
     public static void rollBack(String id, String empresa) {
        Log.debug(Thread.currentThread().getStackTrace()[1].getMethodName());
        CFUsuarios.rollBack(id, empresa);
    }

    public static Iterator selectUsuarios(String empresa) {
        Log.debug(Thread.currentThread().getStackTrace()[1].getMethodName());
        Iterator it = CFUsuarios.selectUsuarios(empresa);
        return it;
    }
    
    public static int updateEstado(String id, String checkbox_activo, String nombre_completo, String empresa) {
        return CFUsuarios.updateEstado(id,checkbox_activo, nombre_completo, empresa);
    }

}
