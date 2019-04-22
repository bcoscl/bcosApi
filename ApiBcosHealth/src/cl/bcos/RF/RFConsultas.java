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
public class RFConsultas extends Registro {

    private static final Logger Log = Logger.getLogger(RFConsultas.class);

    public static int consult_n_id = 1;
    public static int consult_c_titulo = 2;
    public static int consult_c_obs_consulta = 3;
    public static int consult_c_tipoconsulta = 4;
    public static int consult_c_numuser_paciente = 5;
    public static int consult_c_paciente_name = 6;
    public static int consult_d_ultmod_date = 7;
    public static int consult_c_ultmod_numuser = 8;
    public static int consult_c_ultmod_username = 9;
    public static int consult_d_createdate = 10;
    public static int consult_c_empresa = 11;

    public RFConsultas() {
        super(12);
    }

    public static AdmRegistros selectConsultas(Connection con) {
        Log.debug(Thread.currentThread().getStackTrace()[1].getMethodName());
        StringBuilder qry = new StringBuilder();

        qry.append(" SELECT consult_n_id,  ");
        qry.append(" consult_c_titulo,  ");
        qry.append(" consult_c_obs_consulta,  ");
        qry.append(" consult_c_tipoconsulta,  ");
        qry.append(" consult_c_numuser_paciente,  ");
        qry.append(" consult_c_paciente_name,  ");
        qry.append(" to_char(consult_d_ultmod_date, 'yyyy-mm-dd HH24:MI:SS'),  ");

        qry.append(" consult_c_ultmod_numuser, ");
        qry.append(" consult_c_ultmod_username, ");
        qry.append(" to_char(consult_d_createdate, 'Day, dd Month yyyy - HH24:MI:SS'),consult_c_empresa ");
        qry.append(" FROM health_consultas where consult_c_numuser_paciente = ? and consult_c_empresa=? order by consult_n_id DESC ");
        Log.debug(qry.toString());
        AdmRegistros adm = new AdmRegistros(con,
                qry.toString(),
                11,
                new RFConsultas()
        );
        adm.setColumna(1, consult_n_id);
        adm.setColumna(2, consult_c_titulo);
        adm.setColumna(3, consult_c_obs_consulta);
        adm.setColumna(4, consult_c_tipoconsulta);
        adm.setColumna(5, consult_c_numuser_paciente);
        adm.setColumna(6, consult_c_paciente_name);
        adm.setColumna(7, consult_d_ultmod_date);
        adm.setColumna(8, consult_c_ultmod_numuser);
        adm.setColumna(9, consult_c_ultmod_username);
        adm.setColumna(10, consult_d_createdate);
        adm.setColumna(11, consult_c_empresa);

        return adm;
    }

    public static int insertConsultas(Connection con,
            String consult_c_titulo, String consult_c_obs_consulta,
            String consult_c_tipoconsulta, String consult_c_numuser_paciente,
            String consult_c_paciente_name, String consult_c_ultmod_numuser,
            String consult_c_ultmod_username, String consult_d_createdate, String empresa) {

        Log.debug(Thread.currentThread().getStackTrace()[1].getMethodName());
        StringBuilder qry = new StringBuilder();

        qry.append(" INSERT INTO health_consultas(");
        qry.append(" consult_n_id, ");
        qry.append(" consult_c_titulo, ");
        qry.append(" consult_c_obs_consulta, ");
        qry.append(" consult_c_tipoconsulta, ");
        qry.append(" consult_c_numuser_paciente, ");
        qry.append(" consult_c_paciente_name, ");
        qry.append(" consult_d_ultmod_date, ");
        qry.append(" consult_c_ultmod_numuser, ");
        qry.append(" consult_c_ultmod_username,consult_d_createdate,consult_c_empresa) ");

        qry.append(" VALUES (nextval('health_seq_consultas'),'");
        qry.append(consult_c_titulo);
        qry.append("','");
        qry.append(consult_c_obs_consulta);
        qry.append("','");
        qry.append(consult_c_tipoconsulta);
        qry.append("','");
        qry.append(consult_c_numuser_paciente);
        qry.append("','");
        qry.append(consult_c_paciente_name);
        qry.append("',NOW(),'");
        qry.append(consult_c_ultmod_numuser);
        qry.append("','");
        qry.append(consult_c_ultmod_username);
        if (consult_d_createdate == null || consult_d_createdate.isEmpty()) {
            qry.append("',NOW(),");
        } else {
            qry.append("','");
            qry.append(consult_d_createdate);
            qry.append("',");
        }
        qry.append("'");
        qry.append(empresa);
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

    public static int deleteConsultas(Connection con, String consult_n_id, String empresa) {
        Log.debug(Thread.currentThread().getStackTrace()[1].getMethodName());
        StringBuilder qry = new StringBuilder();

        qry.append(" DELETE FROM health_consultas ");
        qry.append(" WHERE consult_n_id=");
        qry.append(consult_n_id);
        qry.append(" and consult_c_empresa='");
        qry.append(empresa);
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

    public static int updateConsultas(Connection con,
            String consult_n_id, String consult_c_titulo,
            String consult_c_obs_consulta,
            String consult_c_ultmod_numuser, String consult_c_ultmod_username, String empresa) {

        Log.debug(Thread.currentThread().getStackTrace()[1].getMethodName());
        StringBuilder qry = new StringBuilder();

        qry.append(" UPDATE health_consultas ");
        qry.append(" SET consult_c_titulo ='");
        qry.append(consult_c_titulo);
        qry.append("',consult_c_obs_consulta='");
        qry.append(consult_c_obs_consulta);
        //qry.append("',consult_c_tipoconsulta='");
        //qry.append(consult_c_tipoconsulta);
        qry.append("',consult_c_ultmod_numuser='");
        qry.append(consult_c_ultmod_numuser);
        qry.append("',consult_c_ultmod_username='");
        qry.append(consult_c_ultmod_username);
        qry.append("',consult_d_ultmod_date=NOW()");

        qry.append(" where consult_n_id=");
        qry.append(consult_n_id);
        qry.append(" and consult_c_empresa='");
        qry.append(empresa);
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
