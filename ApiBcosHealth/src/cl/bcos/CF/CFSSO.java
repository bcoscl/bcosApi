/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.bcos.CF;

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
public class CFSSO {

    private static final Logger Log = Logger.getLogger(CFSSO.class);
    private static final String conexionName = "conexionOCT";

    public static String autenticacion(String numuser, String pass) {
        Log.debug(Thread.currentThread().getStackTrace()[1].getMethodName());
        Connection con = null;
        String ret = "";
        try {
            con = Pool.getInstancia().getConnection(conexionName);
            String[] param = {numuser, pass};
            TabRegistros tab = new TabRegistros();
            tab.setContext(RFSSO.autenticacion(con));
            tab.execute(tab.USE_RS, param);
            Iterator it = tab.getRegistros();

            while (it.hasNext()) {
                Registro reg = (Registro) it.next();
                ret = reg.get(RFSSO.usuariovalido);
            }
        } catch (Exception e) {
            Log.error(e.toString());
        } finally {
            Pool.getInstancia().free(con);
        }
        return ret;
    }

    public static String getNewUserId() {
        Log.debug(Thread.currentThread().getStackTrace()[1].getMethodName());
        Connection con = null;
        String ret = "";
        try {
            con = Pool.getInstancia().getConnection(conexionName);
            String[] param = {};
            TabRegistros tab = new TabRegistros();
            tab.setContext(RFSSO.getNewUserId(con));
            tab.execute(tab.USE_RS, param);
            Iterator it = tab.getRegistros();

            while (it.hasNext()) {
                Registro reg = (Registro) it.next();
                ret = reg.get(RFSSO.user_id);
                break;
            }
        } catch (Exception e) {
            Log.error(e.toString());
        } finally {
            Pool.getInstancia().free(con);
        }
        return ret;
    }

    public static int insertUserPass(String numuser_user, String password, String usuario_creador, String checkbox_activo, String nombre_completo, String rowid) {
        Log.debug(Thread.currentThread().getStackTrace()[1].getMethodName());
        Connection con = null;

        try {
            con = Pool.getInstancia().getConnection(conexionName);
            return RFSSO.insertUserPass(con, numuser_user, password, usuario_creador, checkbox_activo, nombre_completo, rowid);

        } catch (Exception e) {
            Log.error(e.toString());
            return 0;
        } finally {
            Pool.getInstancia().free(con);
        }
    }
    public static void rollBack(String id) {
        Log.debug(Thread.currentThread().getStackTrace()[1].getMethodName());
        Connection con = null;

        try {
            con = Pool.getInstancia().getConnection(conexionName);
            RFSSO.rollBack(con, id);

        } catch (Exception e) {
            Log.error(e.toString());
            //return 0;
        } finally {
            Pool.getInstancia().free(con);
        }
    }
}
