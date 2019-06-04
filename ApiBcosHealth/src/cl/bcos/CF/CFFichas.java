/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.bcos.CF;

import cl.bcos.RF.RFFichas;
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
public class CFFichas {

    private static final Logger Log = Logger.getLogger(CFFichas.class);
    private static final String conexionName = "ConexionBcos";

    public static Iterator selectFichas(String empresa) {
        Log.debug(Thread.currentThread().getStackTrace()[1].getMethodName());
        Connection con = null;
        Iterator it = null;
        try {
            con = Pool.getInstancia().getConnection(conexionName);
            String[] param = {empresa};
            TabRegistros tab = new TabRegistros();
            tab.setContext(RFFichas.selectFichas(con));
            tab.execute(tab.USE_RS, param);
            it = tab.getRegistros();
            return it;
        } catch (Exception e) {
            Log.error(e.toString());
        } finally {
            Pool.getInstancia().free(con);
        }
        return it;
    }

    public static String getNewFichaId() {
        Log.debug(Thread.currentThread().getStackTrace()[1].getMethodName());
        Connection con = null;
        String ret = "";
        try {
            con = Pool.getInstancia().getConnection(conexionName);
            String[] param = {};
            TabRegistros tab = new TabRegistros();
            tab.setContext(RFFichas.getNewFichaId(con));
            tab.execute(tab.USE_RS, param);
            Iterator it = tab.getRegistros();

            while (it.hasNext()) {
                Registro reg = (Registro) it.next();
                ret = reg.get(RFFichas.ficha_n_id);
            }
        } catch (Exception e) {
            Log.error(e.toString());
        } finally {
            Pool.getInstancia().free(con);
        }
        return ret;
    }

    public static Iterator existeRegistro(String numuser, String empresa) {
        Log.debug(Thread.currentThread().getStackTrace()[1].getMethodName());
        Connection con = null;
        Iterator it = null;
        try {
            con = Pool.getInstancia().getConnection(conexionName);
            String[] param = {};
            TabRegistros tab = new TabRegistros();
            tab.setContext(RFFichas.existeRegistro(con, numuser, empresa));
            tab.execute(tab.USE_RS, param);
            it = tab.getRegistros();
            return it;
        } catch (Exception e) {
            Log.error(e.toString());
        } finally {
            Pool.getInstancia().free(con);
        }
        return it;
    }

    public static int insertFicha(String rowFichaId, String numuser_paciente, String nombre_paciente,
            String apellido_paciente, String nombre_completo, String Roles,
            String usuario_creador, String createUsername, String empresaName) {
        Log.debug(Thread.currentThread().getStackTrace()[1].getMethodName());
        Connection con = null;

        try {
            con = Pool.getInstancia().getConnection(conexionName);
            return RFFichas.insertFicha(con, rowFichaId, numuser_paciente, nombre_paciente, apellido_paciente, nombre_completo, Roles, usuario_creador, createUsername, empresaName);

        } catch (Exception e) {
            Log.error(e.toString());
            return 0;
        } finally {
            Pool.getInstancia().free(con);
        }
    }

    public static void rollBack(String rowFichaId, String empresa) {
        Log.debug(Thread.currentThread().getStackTrace()[1].getMethodName());
        Connection con = null;

        try {
            con = Pool.getInstancia().getConnection(conexionName);
            RFFichas.rollBack(con, rowFichaId, empresa);

        } catch (Exception e) {
            Log.error(e.toString());
            //return 0;
        } finally {
            Pool.getInstancia().free(con);
        }
    }

}
