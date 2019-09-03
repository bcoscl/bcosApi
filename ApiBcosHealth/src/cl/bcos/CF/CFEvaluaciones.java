/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.bcos.CF;

import cl.bcos.RF.RFEvaluaciones;
import cl.bcos.bd.Pool;
import cl.bcos.data.TabRegistros;
import java.sql.Connection;
import java.util.Iterator;
import org.apache.log4j.Logger;

/**
 *
 * @author aacantero
 */
public class CFEvaluaciones {

    private static final Logger Log = Logger.getLogger(CFEvaluaciones.class);
    private static final String conexionName = "ConexionBcos";

    public static int insertEvaluacion(String eva_paciente,
            String eva_fecha,
            String eva_talla,
            String eva_peso,
            String eva_fat,
            String eva_fatv,
            String eva_musc,
            String eva_obs,
            String usuario_creador,
            String nombre_completo,
            String empresa,
            String eva_imc) {
        Log.debug(Thread.currentThread().getStackTrace()[1].getMethodName());
        Connection con = null;

        try {
            con = Pool.getInstancia().getConnection(conexionName);
            return RFEvaluaciones.insertEvaluacion(con, eva_paciente,
                    eva_fecha,
                    eva_talla,
                    eva_peso,
                    eva_fat,
                    eva_fatv,
                    eva_musc,
                    eva_obs,
                    usuario_creador,
                    nombre_completo,
                    empresa,
                    eva_imc);

        } catch (Exception e) {
            Log.error(e.toString());
            return 0;
        } finally {
            Pool.getInstancia().free(con);
        }
    }

    public static Iterator selectEvaluacion(String Paciente, String empresa) {
        Log.debug(Thread.currentThread().getStackTrace()[1].getMethodName());
        Connection con = null;
        Iterator it = null;
        try {
            con = Pool.getInstancia().getConnection(conexionName);
            String[] param = {Paciente, empresa};
            TabRegistros tab = new TabRegistros();
            tab.setContext(RFEvaluaciones.selectEvaluacion(con));
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

    public static int deleteEvaluacion(String eva_id, String eva_empresa) {
        Log.debug(Thread.currentThread().getStackTrace()[1].getMethodName());
        Connection con = null;

        try {
            con = Pool.getInstancia().getConnection(conexionName);
            return RFEvaluaciones.deleteEvaluacion(con, eva_id, eva_empresa);

        } catch (Exception e) {
            Log.error(e.toString());
            return 0;
        } finally {
            Pool.getInstancia().free(con);
        }
    }

    public static int updateEvaluacion(String eva_id, /*String eva_date,*/
            String eva_talla, String eva_peso,
            String eva_fat, String eva_fatv, String eva_musc,
            String eva_obs_evaluacion, String eva_ultmod_numuser,
            String eva_ultmod_username, String eva_empresa, String eva_imc) {
        Log.debug(Thread.currentThread().getStackTrace()[1].getMethodName());
        Connection con = null;

        try {
            con = Pool.getInstancia().getConnection(conexionName);
            return RFEvaluaciones.updateEvaluacion(con, eva_id, /*eva_date,*/
                    eva_talla, eva_peso,
                    eva_fat, eva_fatv, eva_musc,
                    eva_obs_evaluacion, eva_ultmod_numuser,
                    eva_ultmod_username, eva_empresa,eva_imc);

        } catch (Exception e) {
            Log.error(e.toString());
            return 0;
        } finally {
            Pool.getInstancia().free(con);
        }
    }

}
