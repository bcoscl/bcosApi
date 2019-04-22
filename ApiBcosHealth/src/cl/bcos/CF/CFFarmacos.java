/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.bcos.CF;

import cl.bcos.RF.RFFarmacos;
import cl.bcos.bd.Pool;
import cl.bcos.data.TabRegistros;
import java.sql.Connection;
import java.util.Iterator;
import org.apache.log4j.Logger;

/**
 *
 * @author aacantero
 */
public class CFFarmacos {

    private static final Logger Log = Logger.getLogger(CFFarmacos.class);
    private static final String conexionName = "conexionOCT";

    public static Iterator selectFarmacos(String Paciente, String empresa) {
        Log.debug(Thread.currentThread().getStackTrace()[1].getMethodName());
        Connection con = null;
        Iterator it = null;
        try {
            con = Pool.getInstancia().getConnection(conexionName);
            String[] param = {Paciente, empresa};
            TabRegistros tab = new TabRegistros();
            tab.setContext(RFFarmacos.selectFarmacos(con));
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

    public static int insertFarmacos(String farmaco_c_name, String farmaco_c_obs, String farmaco_numuser_paciente, String farmaco_n_ultmod_numuser, String farmaco_c_ultmod_username, String empresa) {
        Log.debug(Thread.currentThread().getStackTrace()[1].getMethodName());
        Connection con = null;

        try {
            con = Pool.getInstancia().getConnection(conexionName);
            return RFFarmacos.insertFarmacos(con, farmaco_c_name, farmaco_c_obs, farmaco_numuser_paciente, farmaco_n_ultmod_numuser, farmaco_c_ultmod_username, empresa);

        } catch (Exception e) {
            Log.error(e.toString());
            return 0;
        } finally {
            Pool.getInstancia().free(con);
        }
    }

    public static int deleteFarmacos(String rowFarmacoId, String empresa) {
        Log.debug(Thread.currentThread().getStackTrace()[1].getMethodName());
        Connection con = null;

        try {
            con = Pool.getInstancia().getConnection(conexionName);
            return RFFarmacos.deleteFarmacos(con, rowFarmacoId, empresa);

        } catch (Exception e) {
            Log.error(e.toString());
            return 0;
        } finally {
            Pool.getInstancia().free(con);
        }
    }

    public static int updateFarmacos(String farmaco_n_id, String farmaco_c_name, String farmaco_c_obs, String farmaco_n_ultmod_numuser, String farmaco_c_ultmod_username, String empresa) {

        Log.debug(Thread.currentThread().getStackTrace()[1].getMethodName());
        Connection con = null;

        try {
            con = Pool.getInstancia().getConnection(conexionName);
            return RFFarmacos.updateFarmacos(con, farmaco_n_id, farmaco_c_name,
                    farmaco_c_obs, farmaco_n_ultmod_numuser, farmaco_c_ultmod_username, empresa);

        } catch (Exception e) {
            Log.error(e.toString());
            return 0;
        } finally {
            Pool.getInstancia().free(con);
        }
    }

}
