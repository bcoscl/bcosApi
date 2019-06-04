/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.bcos.CF;

import cl.bcos.RF.RFConsultas;
import cl.bcos.bd.Pool;
import cl.bcos.data.TabRegistros;
import java.sql.Connection;
import java.util.Iterator;
import org.apache.log4j.Logger;

/**
 *
 * @author aacantero
 */
public class CFConsultas {

    private static final Logger Log = Logger.getLogger(CFConsultas.class);
    private static final String conexionName = "ConexionBcos";

    public static Iterator selectConsultas(String Paciente, String empresa) {
        Log.debug(Thread.currentThread().getStackTrace()[1].getMethodName());
        Connection con = null;
        Iterator it = null;
        try {
            con = Pool.getInstancia().getConnection(conexionName);
            String[] param = {Paciente, empresa};
            TabRegistros tab = new TabRegistros();
            tab.setContext(RFConsultas.selectConsultas(con));
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

    public static int insertConsultas(String consult_c_titulo, String consult_c_obs_consulta, String consult_c_tipoconsulta, String consult_c_numuser_paciente, String consult_c_paciente_name, String consult_c_ultmod_numuser, String consult_c_ultmod_username, String consult_c_createdate, String empresa) {
        Log.debug(Thread.currentThread().getStackTrace()[1].getMethodName());
        Connection con = null;

        try {
            con = Pool.getInstancia().getConnection(conexionName);
            return RFConsultas.insertConsultas(con, consult_c_titulo, consult_c_obs_consulta, consult_c_tipoconsulta, consult_c_numuser_paciente, consult_c_paciente_name, consult_c_ultmod_numuser, consult_c_ultmod_username,consult_c_createdate, empresa);

        } catch (Exception e) {
            Log.error(e.toString());
            return 0;
        } finally {
            Pool.getInstancia().free(con);
        }
    }

    public static int deleteConsultas(String consult_n_id, String empresa) {
        Log.debug(Thread.currentThread().getStackTrace()[1].getMethodName());
        Connection con = null;

        try {
            con = Pool.getInstancia().getConnection(conexionName);
            return RFConsultas.deleteConsultas(con, consult_n_id, empresa);

        } catch (Exception e) {
            Log.error(e.toString());
            return 0;
        } finally {
            Pool.getInstancia().free(con);
        }
    }

    public static int updateConsultas(String consult_n_id, String consult_c_titulo, String consult_c_obs_consulta,      String consult_c_ultmod_numuser, String consult_c_ultmod_username, String empresa) {
        Log.debug(Thread.currentThread().getStackTrace()[1].getMethodName());
        Connection con = null;

        try {
            con = Pool.getInstancia().getConnection(conexionName);
            return RFConsultas.updateConsultas(con, consult_n_id, consult_c_titulo, consult_c_obs_consulta,    consult_c_ultmod_numuser, consult_c_ultmod_username, empresa);

        } catch (Exception e) {
            Log.error(e.toString());
            return 0;
        } finally {
            Pool.getInstancia().free(con);
        }
    }

}
