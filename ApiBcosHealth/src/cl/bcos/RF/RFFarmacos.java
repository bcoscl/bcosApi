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
public class RFFarmacos extends Registro {

    private static final Logger Log = Logger.getLogger(RFFarmacos.class);

    public static int farmaco_n_id = 1;
    public static int farmaco_c_name = 2;
    public static int farmaco_c_obs = 3;
    public static int farmaco_numuser_paciente = 4;
    public static int farmaco_n_ultmod_numuser = 5;
    public static int farmaco_d_ultmod_date = 6;
    public static int farmaco_c_ultmod_username = 7;
    public static int farmaco_c_empresa = 8;

    public RFFarmacos() {
        super(10);
    }

    public static AdmRegistros selectFarmacos(Connection con) {
        Log.debug(Thread.currentThread().getStackTrace()[1].getMethodName());
        StringBuilder qry = new StringBuilder();

        qry.append(" SELECT farmaco_n_id,  ");
        qry.append(" farmaco_c_name,  ");
        qry.append(" farmaco_c_obs,  ");
        qry.append(" farmaco_numuser_paciente,  ");
        qry.append(" farmaco_n_ultmod_numuser,  ");
        qry.append(" to_char(farmaco_d_ultmod_date, 'yyyy-mm-dd HH24:MI:SS'),  ");
        qry.append(" farmaco_c_ultmod_username , farmaco_c_empresa");
        qry.append(" FROM health_farmaco where farmaco_numuser_paciente = ? and farmaco_c_empresa =? order by farmaco_d_ultmod_date ASC ");
        Log.debug(qry.toString());
        AdmRegistros adm = new AdmRegistros(con,
                qry.toString(),
                8,
                new RFFarmacos()
        );
        adm.setColumna(1, farmaco_n_id);
        adm.setColumna(2, farmaco_c_name);
        adm.setColumna(3, farmaco_c_obs);
        adm.setColumna(4, farmaco_numuser_paciente);
        adm.setColumna(5, farmaco_n_ultmod_numuser);
        adm.setColumna(6, farmaco_d_ultmod_date);
        adm.setColumna(7, farmaco_c_ultmod_username);
        adm.setColumna(8, farmaco_c_empresa);

        return adm;
    }

    public static int insertFarmacos(Connection con, String farmaco_c_name,
            String farmaco_c_obs, String farmaco_numuser_paciente, String farmaco_n_ultmod_numuser, String farmaco_c_ultmod_username, String empresa) {
        Log.debug(Thread.currentThread().getStackTrace()[1].getMethodName());
        StringBuilder qry = new StringBuilder();

        qry.append(" INSERT INTO health_farmaco(");
        qry.append(" farmaco_n_id, ");
        qry.append(" farmaco_c_name, ");
        qry.append(" farmaco_c_obs, ");
        qry.append(" farmaco_numuser_paciente, ");
        qry.append(" farmaco_n_ultmod_numuser, ");
        qry.append(" farmaco_d_ultmod_date, ");
        qry.append(" farmaco_c_ultmod_username,farmaco_c_empresa) ");

        qry.append(" VALUES (nextval('health_seq_farmaco'),'");
        qry.append(farmaco_c_name);
        qry.append("','");
        qry.append(farmaco_c_obs);
        qry.append("','");
        qry.append(farmaco_numuser_paciente);
        qry.append("','");
        qry.append(farmaco_n_ultmod_numuser);
        qry.append("',NOW(),'");
        qry.append(farmaco_c_ultmod_username);
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

    public static int deleteFarmacos(Connection con, String rowFarmacoId, String empresa) {

        Log.debug(Thread.currentThread().getStackTrace()[1].getMethodName());
        StringBuilder qry = new StringBuilder();

        qry.append(" DELETE FROM health_farmaco ");
        qry.append(" WHERE farmaco_n_id=");
        qry.append(rowFarmacoId);
        qry.append(" and  farmaco_c_empresa='");
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

    public static int updateFarmacos(Connection con, String farmaco_n_id, String farmaco_c_name,
            String farmaco_c_obs, String farmaco_n_ultmod_numuser, String farmaco_c_ultmod_username, String empresa) {
        Log.debug(Thread.currentThread().getStackTrace()[1].getMethodName());
        StringBuilder qry = new StringBuilder();

        qry.append(" UPDATE health_farmaco ");
        qry.append(" SET farmaco_c_obs ='");
        qry.append(farmaco_c_obs);
        qry.append("',farmaco_c_name='");
        qry.append(farmaco_c_name);
        qry.append("',farmaco_n_ultmod_numuser='");
        qry.append(farmaco_n_ultmod_numuser);
        qry.append("',farmaco_d_ultmod_date=NOW(),farmaco_c_ultmod_username='");
        qry.append(farmaco_c_ultmod_username);
        qry.append("' where farmaco_n_id=");
        qry.append(farmaco_n_id);
        qry.append(" and  farmaco_c_empresa='");
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
