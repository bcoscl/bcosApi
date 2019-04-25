/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.bcos.LF;

import cl.bcos.CF.CFParams;
import java.util.Iterator;
import org.apache.log4j.Logger;

/**
 *
 * @author aacantero
 */
public class LFParams {

    private static final Logger Log = Logger.getLogger(LFParams.class);

    public static String getParams(String grupo, String subGrupo, String param1) {
        Log.debug(Thread.currentThread().getStackTrace()[1].getMethodName());
        String Response = CFParams.getParams(grupo, subGrupo, param1);

        return Response;
    }
    public static Iterator getAllParams() {
        Log.debug(Thread.currentThread().getStackTrace()[1].getMethodName());
        return CFParams.getAllParams();

        
    }

    public static int insertParam(String params_n_grupo,
            String params_n_subgrupo,
            String params_n_param1,
            String params_n_param2,
            String params_n_param3,
            String params_n_param4,
            String params_c_numuser_utlmod,
            String params_c_nombre_ultmod) {
        Log.debug(Thread.currentThread().getStackTrace()[1].getMethodName());
        return CFParams.insertParam(params_n_grupo, params_n_subgrupo, params_n_param1, params_n_param2, params_n_param3, params_n_param4,params_c_numuser_utlmod,params_c_nombre_ultmod);
       
    }

    public static String getNewParamId() {
        Log.debug(Thread.currentThread().getStackTrace()[1].getMethodName());
        String Response = CFParams.getNewParamId();

        return Response;
    }
     public static int deleteParams(String Rowid) {
        Log.debug(Thread.currentThread().getStackTrace()[1].getMethodName());
        int Response = CFParams.deleteParams(Rowid);

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
