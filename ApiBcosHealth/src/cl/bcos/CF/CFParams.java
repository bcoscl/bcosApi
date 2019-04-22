/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.bcos.CF;

import cl.bcos.RF.RFParams;
import cl.bcos.RF.RFSSO;
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

    public static String getParams(String grupo, String subGrupo,String param1) {
        Log.debug(Thread.currentThread().getStackTrace()[1].getMethodName());
        Connection con = null;
        String cadena = "";
        try {
            con = Pool.getInstancia().getConnection(conexionName);
            String[] param = {grupo, subGrupo,param1};
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
