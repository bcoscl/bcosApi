/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.bcos.CF;

import cl.bcos.RF.RFEnfermedadesCronicas;
import cl.bcos.bd.Pool;
import cl.bcos.data.TabRegistros;
import java.sql.Connection;
import java.util.Iterator;
import org.apache.log4j.Logger;

/**
 *
 * @author aacantero
 */
public class CFEnfermedadesCronicas {

    private static final Logger Log = Logger.getLogger(CFEnfermedadesCronicas.class);
    private static final String conexionName = "conexionOCT";

    public static Iterator selectEnfermedadesCronicas(String Paciente, String empresa) {
        Log.debug(Thread.currentThread().getStackTrace()[1].getMethodName());
        Connection con = null;
        Iterator it = null;
        try {
            con = Pool.getInstancia().getConnection(conexionName);
            String[] param = {Paciente, empresa};
            TabRegistros tab = new TabRegistros();
            tab.setContext(RFEnfermedadesCronicas.selectEnfermedadesCronicas(con));
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

    public static int insertEnfermedadesCronicas(String cronica_c_name, String cronica_c_obs, String cronica_c_numuser_paciente, String cronica_n_ultmod_numuser, String cronica_c_ultmod_username, String empresa) {
        Log.debug(Thread.currentThread().getStackTrace()[1].getMethodName());
        Connection con = null;

        try {
            con = Pool.getInstancia().getConnection(conexionName);
            return RFEnfermedadesCronicas.insertEnfermedadesCronicas(con, cronica_c_name, cronica_c_obs, cronica_c_numuser_paciente, cronica_n_ultmod_numuser, cronica_c_ultmod_username, empresa);

        } catch (Exception e) {
            Log.error(e.toString());
            return 0;
        } finally {
            Pool.getInstancia().free(con);
        }
    }

    public static int  updateEnfermedadesCronicas(String cronica_n_id, String cronica_c_name,
            String cronica_c_obs, String cronica_n_ultmod_numuser, String cronica_c_ultmod_username, String empresa) {
        Log.debug(Thread.currentThread().getStackTrace()[1].getMethodName());
        Connection con = null;

        try {
            con = Pool.getInstancia().getConnection(conexionName);
            return RFEnfermedadesCronicas.updateEnfermedadesCronicas(con, cronica_n_id, cronica_c_name,
                    cronica_c_obs, cronica_n_ultmod_numuser, cronica_c_ultmod_username, empresa);

        } catch (Exception e) {
            Log.error(e.toString());
            return 0;
        } finally {
            Pool.getInstancia().free(con);
        }
    }

    public static int deleteEnfermedadCronica(String rowCronicaId, String empresa) {
        Log.debug(Thread.currentThread().getStackTrace()[1].getMethodName());
        Connection con = null;

        try {
            con = Pool.getInstancia().getConnection(conexionName);
            return RFEnfermedadesCronicas.deleteEnfermedadCronica(con, rowCronicaId, empresa);

        } catch (Exception e) {
            Log.error(e.toString());
            return 0;
        } finally {
            Pool.getInstancia().free(con);
        }
    }

}
