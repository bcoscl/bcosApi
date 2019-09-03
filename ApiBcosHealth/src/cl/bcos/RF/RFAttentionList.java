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
public class RFAttentionList extends Registro {

    private static final Logger Log = Logger.getLogger(RFAttentionList.class);

    public static final int at_n_id = 1;
    public static final int at_c_numuser_paciente = 2;
    public static final int at_c_pacientename = 3;
    public static final int at_c_mediconame = 4;
    public static final int at_c_numuser_medico = 5;
    public static final int at_c_obs = 6;
    public static final int at_d_fechamod = 7;
    public static final int at_c_empresa = 8;
    public static final int at_d_fechacita = 9;

    public RFAttentionList() {
        super(10);
    }

    /*Inserta en la lista de atencion del doctor*/
    public static int insertAttentionList(Connection con,
            String at_c_numuser_paciente,
            String at_c_pacientename, String at_c_mediconame,
            String at_c_numuser_medico, String motivo, String empresa, String hora) {

        Log.debug(Thread.currentThread().getStackTrace()[1].getMethodName());
        StringBuilder qry = new StringBuilder();

        qry.append(" INSERT INTO health_attention_list( ");
        qry.append(" at_n_id,  ");
        qry.append(" at_c_numuser_paciente,  ");
        qry.append(" at_c_pacientename, ");
        qry.append(" at_c_mediconame,  ");
        qry.append(" at_c_numuser_medico, at_c_obs, at_d_fechamod,at_c_empresa,at_d_fechacita) ");
        qry.append(" VALUES ( nextval('health_seq_attetion_list'),'");
        qry.append(at_c_numuser_paciente);
        qry.append("','");
        qry.append(at_c_pacientename);
        qry.append("','");
        qry.append(at_c_mediconame);
        qry.append("','");
        qry.append(at_c_numuser_medico);
        qry.append("','");
        qry.append(motivo);
        qry.append("',NOW()  at time zone (select params_n_param1 from health_params where params_n_grupo='UTC'and params_n_subgrupo='TIMEZONE' ),'");
        qry.append(empresa);
        qry.append("',CURRENT_DATE  at time zone (select params_n_param1 from health_params where params_n_grupo='UTC'and params_n_subgrupo='TIMEZONE' ) + time '");
        qry.append(hora);
        qry.append("')");

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

    /*mover al final de la lista*/
    public static int moverAlfinal(Connection con,
            String at_n_id) {

        Log.debug(Thread.currentThread().getStackTrace()[1].getMethodName());
        StringBuilder qry = new StringBuilder();

        qry.append(" INSERT INTO health_attention_list(");
        qry.append(" at_n_id, at_c_numuser_paciente, at_c_pacientename, at_c_mediconame, at_c_numuser_medico,at_c_obs,at_d_fechamod,at_c_empresa)");
        qry.append(" (select nextval('health_seq_attetion_list'),a.at_c_numuser_paciente,a.at_c_pacientename,");
        qry.append(" a.at_c_mediconame,a.at_c_numuser_medico,a.at_c_obs, NOW()  at time zone (select params_n_param1 from health_params where params_n_grupo='UTC'and params_n_subgrupo='TIMEZONE' ),a.at_c_empresa from health_attention_list a where  a.at_n_id=");
        qry.append(at_n_id);
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

    /*Obtener lista de atencion by Doc*/
    public static AdmRegistros selectAttentionList(Connection con) {
        Log.debug(Thread.currentThread().getStackTrace()[1].getMethodName());
        StringBuilder qry = new StringBuilder();

        qry.append(" SELECT at_n_id, ");
        qry.append(" at_c_numuser_paciente, ");
        qry.append(" at_c_pacientename, ");
        qry.append(" at_c_mediconame, ");
        qry.append(" at_c_numuser_medico,at_c_obs ,to_char(at_d_fechamod, 'yyyy-mm-dd HH24:MI:SS'),at_c_empresa,to_char(at_d_fechacita, 'yyyy-mm-dd HH24:MI:SS')");
        qry.append(" FROM health_attention_list");
        qry.append(" where at_c_numuser_medico =? and at_c_empresa = ? order by at_n_id ASC ");
        Log.debug(qry.toString());
        AdmRegistros adm = new AdmRegistros(con,
                qry.toString(),
                9,
                new RFAttentionList()
        );
        adm.setColumna(1, at_n_id);
        adm.setColumna(2, at_c_numuser_paciente);
        adm.setColumna(3, at_c_pacientename);
        adm.setColumna(4, at_c_mediconame);
        adm.setColumna(5, at_c_numuser_medico);
        adm.setColumna(6, at_c_obs);
        adm.setColumna(7, at_d_fechamod);
        adm.setColumna(8, at_c_empresa);
        adm.setColumna(9, at_d_fechacita);

        return adm;
    }

    /*quitar de la lista de atencion*/
    public static void deleteFromAttentionList(Connection con, String rowAt, String empresa) {

        Log.debug(Thread.currentThread().getStackTrace()[1].getMethodName());
        StringBuilder qry = new StringBuilder();

        qry.append(" DELETE FROM health_attention_list ");
        qry.append(" WHERE at_n_id=");
        qry.append(rowAt);
        qry.append(" and  at_c_empresa='");
        qry.append(empresa);
        qry.append("'");
        
        
        Log.debug(qry.toString());
        PreparedStatement ps = null;

        try {
            ps = con.prepareStatement(qry.toString());
            ps.executeUpdate();

        } catch (Exception e) {
            Log.error(e.getMessage());
            //return 0;
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
