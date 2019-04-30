/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.bcos.CF;

import cl.bcos.RF.RFParams;
import cl.bcos.bd.Pool;
import cl.bcos.data.Registro;
import cl.bcos.data.TabRegistros;
import java.sql.Connection;
import java.util.Iterator;
import org.apache.log4j.Logger;

/**
 *
 * @author aacantero
 */
public class CFParams {

    private static final Logger Log = Logger.getLogger(CFParams.class);
    private static final String conexionName = "conexionOCT";

    public static String getParams(String grupo, String subGrupo, String param1) {
        Log.debug(Thread.currentThread().getStackTrace()[1].getMethodName());
        Connection con = null;
        String cadena = "";
        try {
            con = Pool.getInstancia().getConnection(conexionName);
            String[] param = {grupo, subGrupo, param1};
            TabRegistros tab = new TabRegistros();
            tab.setContext(RFParams.getParams(con));
            tab.execute(tab.USE_RS, param);
            Iterator it = tab.getRegistros();

            while (it.hasNext()) {
                Registro reg = (Registro) it.next();
                cadena = reg.get(RFParams.params_n_param2);
            }
        } catch (Exception e) {
            Log.error(e.toString());
        } finally {
            Pool.getInstancia().free(con);
        }
        return cadena;
    }

    public static Iterator getAllParams() {
        Log.debug(Thread.currentThread().getStackTrace()[1].getMethodName());
        Connection con = null;
        
        try {
            con = Pool.getInstancia().getConnection(conexionName);
            String[] param = {};
            TabRegistros tab = new TabRegistros();
            tab.setContext(RFParams.getAllParams(con));
            tab.execute(tab.USE_RS, param);
            Iterator it = tab.getRegistros();
            return it;

        } catch (Exception e) {
            Log.error(e.toString());
            return null;
        } finally {
            Pool.getInstancia().free(con);
        }

    }

    public static String getNewParamId() {
        Log.debug(Thread.currentThread().getStackTrace()[1].getMethodName());
        Connection con = null;
        String ret = "";
        try {
            con = Pool.getInstancia().getConnection(conexionName);
            String[] param = {};
            TabRegistros tab = new TabRegistros();
            tab.setContext(RFParams.getNewParamId(con));
            tab.execute(tab.USE_RS, param);
            Iterator it = tab.getRegistros();

            while (it.hasNext()) {
                Registro reg = (Registro) it.next();
                ret = reg.get(RFParams.params_n_id);
            }
        } catch (Exception e) {
            Log.error(e.toString());
        } finally {
            Pool.getInstancia().free(con);
        }
        return ret;
    }
    public static String getsysdate() {
        Log.debug(Thread.currentThread().getStackTrace()[1].getMethodName());
        Connection con = null;
        String ret = "";
        try {
            con = Pool.getInstancia().getConnection(conexionName);
            String[] param = {};
            TabRegistros tab = new TabRegistros();
            tab.setContext(RFParams.getsysdate(con));
            tab.execute(tab.USE_RS, param);
            Iterator it = tab.getRegistros();

            while (it.hasNext()) {
                Registro reg = (Registro) it.next();
                ret = reg.get(RFParams.params_now);
            }
        } catch (Exception e) {
            Log.error(e.toString());
        } finally {
            Pool.getInstancia().free(con);
        }
        return ret;
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
        Connection con = null;

        try {
            con = Pool.getInstancia().getConnection(conexionName);
            return RFParams.insertParam(con, params_n_grupo,
                    params_n_subgrupo,
                    params_n_param1,
                    params_n_param2,
                    params_n_param3,
                    params_n_param4,
                    params_c_numuser_utlmod,
                    params_c_nombre_ultmod);

        } catch (Exception e) {
            Log.error(e.toString());
            return 0;
        } finally {
            Pool.getInstancia().free(con);
        }
    }
    
     public static int deleteParams(String rowParamsId) {
        Log.debug(Thread.currentThread().getStackTrace()[1].getMethodName());
        Connection con = null;

        try {
            con = Pool.getInstancia().getConnection(conexionName);
            return RFParams.deleteParams(con, rowParamsId);

        } catch (Exception e) {
            Log.error(e.toString());
            return 0;
        } finally {
            Pool.getInstancia().free(con);
        }
    }


//
//    public static int insertUserPass(String numuser_user, String password, String usuario_creador, String checkbox_activo, String nombre_completo, String rowid) {
//        Log.debug(Thread.currentThread().getStackTrace()[1].getMethodName());
//        Connection con = null;
//
//        try {
//            con = Pool.getInstancia().getConnection(conexionName);
//            return RFSSO.insertUserPass(con, numuser_user, password, usuario_creador, checkbox_activo, nombre_completo, rowid);
//
//        } catch (Exception e) {
//            Log.error(e.toString());
//            return 0;
//        } finally {
//            Pool.getInstancia().free(con);
//        }
//    }
//    public static void rollBack(String id) {
//        Log.debug(Thread.currentThread().getStackTrace()[1].getMethodName());
//        Connection con = null;
//
//        try {
//            con = Pool.getInstancia().getConnection(conexionName);
//            RFSSO.rollBack(con, id);
//
//        } catch (Exception e) {
//            Log.error(e.toString());
//            //return 0;
//        } finally {
//            Pool.getInstancia().free(con);
//        }
//    }
}
