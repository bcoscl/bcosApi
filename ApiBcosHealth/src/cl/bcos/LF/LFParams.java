/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.bcos.LF;

import cl.bcos.CF.CFParams;
import cl.bcos.CF.CFSSO;
import org.apache.log4j.Logger;

/**
 *
 * @author aacantero
 */
public class LFParams {

    private static final Logger Log = Logger.getLogger(LFParams.class);

    public static String getParams(String grupo, String subGrupo,String param1) {
        Log.debug(Thread.currentThread().getStackTrace()[1].getMethodName());
        String Response = CFParams.getParams( grupo,  subGrupo, param1);

        return Response;
    }

    public static String getNewParamId() {
        Log.debug(Thread.currentThread().getStackTrace()[1].getMethodName());
        String Response = CFParams.getNewParamId();

        return Response;
    }
//
//    public static int insertUserPass(String numuser_user, String password, String usuario_creador, String checkbox_activo, String nombre_completo, String rowid) {
//        Log.debug(Thread.currentThread().getStackTrace()[1].getMethodName());
//        return CFSSO.insertUserPass(numuser_user, password, usuario_creador, checkbox_activo, nombre_completo, rowid);
//    }
//    
//    public static void rollBack(String id) {
//        Log.debug(Thread.currentThread().getStackTrace()[1].getMethodName());
//        CFSSO.rollBack(id);
//    }

}
