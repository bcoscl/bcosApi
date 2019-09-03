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
public class RFExamenes extends Registro {

    private static final Logger Log = Logger.getLogger(RFExamenes.class);

    public static int exa_n_id = 1;
    public static int exa_c_name = 2;
    public static int exa_c_obs = 3;
    public static int exa_c_numuser_paciente = 4;
    public static int exa_n_ultmod_numuser = 5;
    public static int exa_d_ultmod_date = 6;
    public static int exa_c_ultmod_username = 7;
    public static int exa_c_url = 8;
    public static int exa_c_paciente_name = 9;
    public static int exa_c_empresa = 10;

   

   

    public RFExamenes() {
        super(11);
    }

    public static AdmRegistros selectExamenesbyPaciente(Connection con) {
        Log.debug(Thread.currentThread().getStackTrace()[1].getMethodName());
        StringBuilder qry = new StringBuilder();

        qry.append(" SELECT exa_n_id,  ");
        qry.append(" exa_c_name,  ");
        qry.append(" exa_c_obs,  ");
        qry.append(" exa_c_numuser_paciente,  ");
        qry.append(" exa_n_ultmod_numuser,  ");

        qry.append(" to_char(exa_d_ultmod_date, 'yyyy-mm-dd HH24:MI:SS'),  ");
        qry.append(" exa_c_ultmod_username, ");
        qry.append(" exa_c_url,exa_c_paciente_name,exa_c_empresa ");
        qry.append(" FROM health_examenes where exa_c_numuser_paciente = ? and exa_c_empresa = ? order by exa_n_id DESC ");
        Log.debug(qry.toString());
        AdmRegistros adm = new AdmRegistros(con,
                qry.toString(),
                10,
                new RFExamenes()
        );
        adm.setColumna(1, exa_n_id);
        adm.setColumna(2, exa_c_name);
        adm.setColumna(3, exa_c_obs);
        adm.setColumna(4, exa_c_numuser_paciente);
        adm.setColumna(5, exa_n_ultmod_numuser);
        adm.setColumna(6, exa_d_ultmod_date);
        adm.setColumna(7, exa_c_ultmod_username);
        adm.setColumna(8, exa_c_url);
        adm.setColumna(9, exa_c_paciente_name);
        adm.setColumna(10, exa_c_empresa);

        return adm;
    }
    public static AdmRegistros selectExamenesbyData(Connection con) {
        Log.debug(Thread.currentThread().getStackTrace()[1].getMethodName());
        StringBuilder qry = new StringBuilder();

        qry.append(" SELECT exa_n_id  ");      
        qry.append(" FROM health_examenes where exa_c_name = ? and exa_c_numuser_paciente=? and exa_c_empresa = ? ");
        
        
        Log.debug(qry.toString());
        AdmRegistros adm = new AdmRegistros(con,
                qry.toString(),
                1,
                new RFExamenes()
        );
        adm.setColumna(1, exa_n_id);
       

        return adm;
    }
    public static AdmRegistros selectExamenesAll(Connection con) {
        Log.debug(Thread.currentThread().getStackTrace()[1].getMethodName());
        StringBuilder qry = new StringBuilder();

        qry.append(" SELECT exa_n_id,  ");
        qry.append(" exa_c_name,  ");
        qry.append(" exa_c_obs,  ");
        qry.append(" exa_c_numuser_paciente,  ");
        qry.append(" exa_n_ultmod_numuser,  ");

        qry.append(" to_char(exa_d_ultmod_date, 'yyyy-mm-dd HH24:MI:SS'),  ");
        qry.append(" exa_c_ultmod_username, ");
        qry.append(" exa_c_url,exa_c_paciente_name, exa_c_empresa");
        qry.append(" FROM health_examenes where exa_c_empresa = ? order by exa_n_id DESC ");
        Log.debug(qry.toString()); 
        AdmRegistros adm = new AdmRegistros(con,
                qry.toString(),
                10,
                new RFExamenes()
        );
        adm.setColumna(1, exa_n_id);
        adm.setColumna(2, exa_c_name);
        adm.setColumna(3, exa_c_obs);
        adm.setColumna(4, exa_c_numuser_paciente);
        adm.setColumna(5, exa_n_ultmod_numuser);
        adm.setColumna(6, exa_d_ultmod_date);
        adm.setColumna(7, exa_c_ultmod_username);
        adm.setColumna(8, exa_c_url);
        adm.setColumna(9, exa_c_paciente_name);
        adm.setColumna(10, exa_c_empresa);

        return adm;
    }

    public static int insertExamenes(Connection con, String exa_c_name, String exa_c_obs, String exa_c_numuser_paciente, String exa_n_ultmod_numuser, String exa_c_ultmod_username, String exa_c_url, String nombre, String empresa) {
        Log.debug(Thread.currentThread().getStackTrace()[1].getMethodName());
        StringBuilder qry = new StringBuilder();

        qry.append(" INSERT INTO health_examenes(");
        qry.append(" exa_n_id, ");
        qry.append(" exa_c_name, ");
        qry.append(" exa_c_obs, ");
        qry.append(" exa_c_numuser_paciente, ");
        qry.append(" exa_n_ultmod_numuser, ");
        qry.append(" exa_d_ultmod_date, ");
        qry.append(" exa_c_ultmod_username, ");
        qry.append(" exa_c_url,exa_c_paciente_name,exa_c_empresa) ");

        qry.append(" VALUES (nextval('health_seq_examenes'),'");
        qry.append(exa_c_name);
        qry.append("','");
        qry.append(exa_c_obs);
        qry.append("','");
        qry.append(exa_c_numuser_paciente);
        qry.append("','");
        qry.append(exa_n_ultmod_numuser);
        qry.append("',NOW()  at time zone (select params_n_param1 from health_params where params_n_grupo='UTC'and params_n_subgrupo='TIMEZONE' ),'");
        qry.append(exa_c_ultmod_username);
        qry.append("','");
        qry.append(exa_c_url);
        qry.append("','");
        qry.append(nombre);
        qry.append("','");
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

    public static int deleteExamenes(Connection con, String exa_n_id, String empresa) {
        Log.debug(Thread.currentThread().getStackTrace()[1].getMethodName());
        StringBuilder qry = new StringBuilder();

        qry.append(" DELETE FROM health_examenes ");
        qry.append(" WHERE exa_n_id=");
        qry.append(exa_n_id);
        qry.append(" and  exa_c_empresa='");
        qry.append(empresa);
        qry.append("' ");
        
        
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

    public static int updateExamenes(Connection con,
            String exa_n_id, String exa_c_name,
            String exa_c_obs, String exa_n_ultmod_numuser,
            String exa_c_ultmod_username, String empresa) {
        Log.debug(Thread.currentThread().getStackTrace()[1].getMethodName());
        StringBuilder qry = new StringBuilder();

        qry.append(" UPDATE health_examenes ");
        qry.append(" SET exa_c_name ='");
        qry.append(exa_c_name);
        qry.append("',exa_c_obs='");
        qry.append(exa_c_obs);
        qry.append("',exa_n_ultmod_numuser='");
        qry.append(exa_n_ultmod_numuser);
        qry.append("',exa_c_ultmod_username='");
        qry.append(exa_c_ultmod_username);
//        qry.append("',exa_c_url='");
//        qry.append(exa_c_url);
        qry.append("',exa_d_ultmod_date=NOW()  at time zone (select params_n_param1 from health_params where params_n_grupo='UTC'and params_n_subgrupo='TIMEZONE' )");

        qry.append(" where exa_n_id=");
        qry.append(exa_n_id);
        qry.append(" and  exa_c_empresa='");
        qry.append(empresa);
        qry.append("' ");

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
    public static void updateExamenUrlFile(Connection con, String id, String examenUrl, String empresa) {
       
        Log.debug(Thread.currentThread().getStackTrace()[1].getMethodName());
        StringBuilder qry = new StringBuilder();

        qry.append(" UPDATE health_examenes ");
        qry.append(" SET exa_c_url ='");
        qry.append(examenUrl);
        qry.append("'");
        qry.append(" where exa_n_id=");
        qry.append(id);
        qry.append(" and  exa_c_empresa='");
        qry.append(empresa);
        qry.append("' ");

        Log.debug(qry.toString());

        PreparedStatement ps = null;

        try {
            ps = con.prepareStatement(qry.toString());
             ps.executeUpdate();
        } catch (Exception e) {
            Log.error(e.getMessage());
           
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
