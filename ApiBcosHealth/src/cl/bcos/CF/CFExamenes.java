/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.bcos.CF;

import cl.bcos.RF.RFExamenes;
import cl.bcos.bd.Pool;
import cl.bcos.data.TabRegistros;
import java.sql.Connection;
import java.util.Iterator;
import org.apache.log4j.Logger;

/**
 *
 * @author aacantero
 */
public class CFExamenes {

    private static final Logger Log = Logger.getLogger(CFConsultas.class);
    private static final String conexionName = "conexionOCT";

    public static Iterator selectExamenesbyPaciente(String Paciente, String empresa) {
        Log.debug(Thread.currentThread().getStackTrace()[1].getMethodName());
        Connection con = null;
        Iterator it = null;
        try {
            con = Pool.getInstancia().getConnection(conexionName);
            String[] param = {Paciente,empresa};
            TabRegistros tab = new TabRegistros();
            tab.setContext(RFExamenes.selectExamenesbyPaciente(con));
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
    public static Iterator selectExamenesAll(String empresa) {
        Log.debug(Thread.currentThread().getStackTrace()[1].getMethodName());
        Connection con = null;
        Iterator it = null;
        try {
            con = Pool.getInstancia().getConnection(conexionName);
            String[] param = {empresa};
            TabRegistros tab = new TabRegistros();
            tab.setContext(RFExamenes.selectExamenesAll(con));
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

    public static int insertExamenes(String exa_c_name, String exa_c_obs, String exa_c_numuser_paciente, String exa_n_ultmod_numuser, String exa_c_ultmod_username, String exa_c_url, String nombre, String empresa) {
        Log.debug(Thread.currentThread().getStackTrace()[1].getMethodName());
        Connection con = null;

        try {
            con = Pool.getInstancia().getConnection(conexionName);
            return RFExamenes.insertExamenes(con, exa_c_name, exa_c_obs, exa_c_numuser_paciente, exa_n_ultmod_numuser, exa_c_ultmod_username, exa_c_url, nombre, empresa);

        } catch (Exception e) {
            Log.error(e.toString());
            return 0;
        } finally {
            Pool.getInstancia().free(con);
        }
    }

    public static int deleteExamenes(String exa_n_id, String empresa) {
        Log.debug(Thread.currentThread().getStackTrace()[1].getMethodName());
        Connection con = null;

        try {
            con = Pool.getInstancia().getConnection(conexionName);
            return RFExamenes.deleteExamenes(con, exa_n_id, empresa);

        } catch (Exception e) {
            Log.error(e.toString());
            return 0;
        } finally {
            Pool.getInstancia().free(con);
        }
    }

    public static int updateExamenes(String exa_n_id, String exa_c_name, String exa_c_obs, String exa_n_ultmod_numuser, String exa_c_ultmod_username, String exa_c_url, String empresa) {
        Log.debug(Thread.currentThread().getStackTrace()[1].getMethodName());
        Connection con = null;

        try {
            con = Pool.getInstancia().getConnection(conexionName);
            return RFExamenes.updateExamenes(con, exa_n_id, exa_c_name, exa_c_obs, exa_n_ultmod_numuser, exa_c_ultmod_username, exa_c_url, empresa);

        } catch (Exception e) {
            Log.error(e.toString());
            return 0;
        } finally {
            Pool.getInstancia().free(con);
        }
    }

}
