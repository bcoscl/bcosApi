/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.bcos.RF;

import cl.bcos.data.AdmRegistros;
import cl.bcos.data.Registro;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.apache.log4j.Logger;

/**
 *
 * @author aacantero
 */
public class RFEvaluaciones extends Registro {

    private static final Logger Log = Logger.getLogger(RFEvaluaciones.class);

    public static final int eva_n_id = 1;
    public static final int eva_c_titulo = 2;
    public static final int eva_c_numuser_paciente = 3;
    public static final int eva_c_paciente_name = 4;
    public static final int eva_d_eva_date = 5;
    public static final int eva_d_createdate = 6;
    public static final int eva_c_empresa = 7;
    public static final int eva_n_talla = 8;
    public static final int eva_n_peso = 9;
    public static final int eva_n_fat = 10;
    public static final int eva_n_fatv = 11;
    public static final int eva_n_musc = 12;
    public static final int eva_n_obs_evaluacion = 13;
    public static final int eva_d_ultmod_date = 14;
    public static final int eva_c_ultmod_numuser = 15;
    public static final int eva_c_ultmod_username = 16;
    public static final int eva_n_imc = 17;

    public RFEvaluaciones() {
        super(18);
    }

    public static int insertEvaluacion(Connection con,
            String eva_paciente,
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
        StringBuilder qry = new StringBuilder();

        qry.append(" INSERT INTO health_evaluaciones(");
        qry.append(" eva_n_id, ");
        //qry.append(" eva_c_titulo, ");
        qry.append(" eva_c_numuser_paciente, ");
        qry.append(" eva_d_eva_date, ");
        qry.append(" eva_d_createdate, ");
        qry.append(" eva_c_empresa, ");
        qry.append(" eva_n_talla, ");
        qry.append(" eva_n_peso,");
        qry.append(" eva_n_fat,");
        qry.append(" eva_n_fatv,");
        qry.append(" eva_n_musc,");
        qry.append(" eva_n_obs_evaluacion,");
        qry.append(" eva_d_ultmod_date,");
        qry.append(" eva_c_ultmod_numuser,");
        qry.append(" eva_c_ultmod_username,eva_n_imc )");

        qry.append(" VALUES (nextval('health_seq_evaluaciones'),'");
        qry.append(eva_paciente);
        qry.append("','");
        qry.append(eva_fecha);
        qry.append("',NOW()  at time zone (select params_n_param1 from health_params where params_n_grupo='UTC'and params_n_subgrupo='TIMEZONE' ),'");
        qry.append(empresa);
        qry.append("',");
        qry.append(eva_talla);
        qry.append(",");
        qry.append(eva_peso);
        qry.append(",");
        qry.append(eva_fat);
        qry.append(",");
        qry.append(eva_fatv);
        qry.append(",");
        qry.append(eva_musc);
        qry.append(",'");
        qry.append(eva_obs);
        qry.append("',NOW()  at time zone (select params_n_param1 from health_params where params_n_grupo='UTC'and params_n_subgrupo='TIMEZONE' ),'");
        qry.append(usuario_creador);
        qry.append("','");
        qry.append(nombre_completo);
        qry.append("',");
        qry.append(eva_imc);
        qry.append(")");

        Log.debug(qry.toString());

        PreparedStatement ps = null;

        try {

            ps = con.prepareStatement(qry.toString());

            return ps.executeUpdate();

        } catch (Exception e) {
            Log.error(e.getMessage());
            return 0;
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException x) {
                    Log.error(x.toString());
                }
            }
        }
    }

    public static AdmRegistros selectEvaluacion(Connection con) {
        Log.debug(Thread.currentThread().getStackTrace()[1].getMethodName());
        StringBuilder qry = new StringBuilder();

        qry.append(" SELECT eva_n_id,  ");
        //qry.append(" eva_c_titulo,   ");
        qry.append(" eva_c_numuser_paciente,   ");
        qry.append(" to_char(eva_d_eva_date,'DD-MM-YYYY') eva_d_eva_date,  ");
        qry.append(" eva_d_createdate,   ");
        qry.append(" eva_c_empresa,   ");
        qry.append(" eva_n_talla,   ");
        qry.append(" eva_n_peso,   ");
        qry.append(" eva_n_fat,   ");
        qry.append(" eva_n_fatv,   ");
        qry.append(" eva_n_musc,  ");
        qry.append(" eva_n_obs_evaluacion,   ");
        qry.append(" eva_d_ultmod_date,   ");
        qry.append(" eva_c_ultmod_numuser,   ");
        qry.append(" eva_c_ultmod_username,eva_n_imc  ");

        qry.append(" FROM health_evaluaciones where eva_c_numuser_paciente = ? and eva_c_empresa=? order by eva_d_createdate DESC ");
        Log.debug(qry.toString());
        AdmRegistros adm = new AdmRegistros(con,
                qry.toString(),
                15,
                new RFEvaluaciones()
        );
        adm.setColumna(1, eva_n_id);
        //adm.setColumna(2, eva_c_titulo);
        adm.setColumna(2, eva_c_numuser_paciente);
        adm.setColumna(3, eva_d_eva_date);
        adm.setColumna(4, eva_d_createdate);
        adm.setColumna(5, eva_c_empresa);
        adm.setColumna(6, eva_n_talla);
        adm.setColumna(7, eva_n_peso);
        adm.setColumna(8, eva_n_fat);
        adm.setColumna(9, eva_n_fatv);
        adm.setColumna(10, eva_n_musc);
        adm.setColumna(11, eva_n_obs_evaluacion);
        adm.setColumna(12, eva_d_ultmod_date);
        adm.setColumna(13, eva_c_ultmod_numuser);
        adm.setColumna(14, eva_c_ultmod_username);
        adm.setColumna(15, eva_n_imc);

        return adm;
    }

    public static int deleteEvaluacion(Connection con, String eva_id, String eva_empresa) {
        Log.debug(Thread.currentThread().getStackTrace()[1].getMethodName());
        StringBuilder qry = new StringBuilder();

        qry.append(" DELETE FROM health_evaluaciones ");
        qry.append(" WHERE eva_n_id=");
        qry.append(eva_id);
        qry.append(" and eva_c_empresa='");
        qry.append(eva_empresa);
        qry.append("'");

        Log.debug(qry.toString());
        PreparedStatement ps = null;

        try {
            ps = con.prepareStatement(qry.toString());
            return ps.executeUpdate();

        } catch (Exception e) {
            Log.error(e.getMessage());
            return 0;
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException x) {
                    Log.error(x.toString());
                }
            }
        }
    }

    public static int updateEvaluacion(Connection con,
            String eva_id, /*String eva_date,*/
            String eva_talla, String eva_peso, 
            String eva_fat, String eva_fatv, String eva_musc,
            String eva_obs_evaluacion, String eva_ultmod_numuser, 
            String eva_ultmod_username, String eva_empresa, String eva_imc) {

        Log.debug(Thread.currentThread().getStackTrace()[1].getMethodName());
        StringBuilder qry = new StringBuilder();

        qry.append(" UPDATE health_evaluaciones ");
        qry.append(" SET ");
        //qry.append(" eva_d_eva_date ='");
        //qry.append(eva_date);
        //qry.append("',");
        qry.append(" eva_n_talla=");
        qry.append(eva_talla);
        qry.append(",eva_n_peso=");
        qry.append(eva_peso);
        qry.append(",eva_n_fat=");
        qry.append(eva_fat);
        qry.append(",eva_n_fatv=");
        qry.append(eva_fatv);
        qry.append(",eva_n_musc=");
        qry.append(eva_musc);
        qry.append(",eva_n_obs_evaluacion='");
        qry.append(eva_obs_evaluacion);
        qry.append("',eva_d_ultmod_date= NOW()  at time zone (select params_n_param1 from health_params where params_n_grupo='UTC'and params_n_subgrupo='TIMEZONE' )");
        qry.append(",eva_c_ultmod_numuser='");
        qry.append(eva_ultmod_numuser);
        qry.append("',eva_c_ultmod_username='");
        qry.append(eva_ultmod_username);
        qry.append("' ,eva_n_imc=");
        qry.append(eva_imc);
        qry.append(" ");

        qry.append(" where eva_n_id=");
        qry.append(eva_id);
        qry.append(" and eva_c_empresa='");
        qry.append(eva_empresa);
        qry.append("'");

        Log.debug(qry.toString());

        PreparedStatement ps = null;

        try {
            ps = con.prepareStatement(qry.toString());
            return ps.executeUpdate();
        } catch (Exception e) {
            Log.error(e.getMessage());
            return 0;
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException x) {
                    Log.error(x.toString());
                }
            }
        }
    }

}
