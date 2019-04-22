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
public class RFEnfermedadesCronicas extends Registro {

    private static final Logger Log = Logger.getLogger(RFEnfermedadesCronicas.class);

    public static int cronica_c_name = 1;
    public static int cronica_c_numuser_paciente = 2;
    public static int cronica_c_obs = 3;
    public static int cronica_d_ultmod_date = 4;
    public static int cronica_n_id = 5;
    public static int cronica_n_ultmod_numuser = 6;
    public static int cronica_c_ultmod_username = 7;
    public static int cronica_c_empresa = 8;

    public RFEnfermedadesCronicas() {
        super(10);
    }

    public static int insertEnfermedadesCronicas(Connection con, String cronica_c_name, String cronica_c_obs,
            String cronica_c_numuser_paciente, String cronica_n_ultmod_numuser, String cronica_c_ultmod_username, String empresa) {
        Log.debug(Thread.currentThread().getStackTrace()[1].getMethodName());
        StringBuilder qry = new StringBuilder();

        qry.append(" INSERT INTO health_ecronicas(");
        qry.append(" cronica_n_id, ");
        qry.append(" cronica_c_name, ");
        qry.append(" cronica_c_obs, ");
        qry.append(" cronica_c_numuser_paciente, ");
        qry.append(" cronica_n_ultmod_numuser, ");
        qry.append(" cronica_d_ultmod_date, ");
        qry.append(" cronica_c_ultmod_username,cronica_c_empresa) ");

        qry.append(" VALUES (nextval('health_seq_ecronica'),'");
        qry.append(cronica_c_name);
        qry.append("','");
        qry.append(cronica_c_obs);
        qry.append("','");
        qry.append(cronica_c_numuser_paciente);
        qry.append("','");
        qry.append(cronica_n_ultmod_numuser);
        qry.append("',NOW(),'");
        qry.append(cronica_c_ultmod_username);
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

    public static int updateEnfermedadesCronicas(Connection con, String cronica_n_id, String cronica_c_name,
            String cronica_c_obs, String cronica_n_ultmod_numuser, String cronica_c_ultmod_username, String empresa) {
        Log.debug(Thread.currentThread().getStackTrace()[1].getMethodName());
        StringBuilder qry = new StringBuilder();

        qry.append(" UPDATE health_ecronicas ");
        qry.append(" SET cronica_c_obs ='");
        qry.append(cronica_c_obs);
        qry.append("',cronica_c_name='");
        qry.append(cronica_c_name);
        qry.append("',cronica_n_ultmod_numuser='");
        qry.append(cronica_n_ultmod_numuser);
        qry.append("',cronica_d_ultmod_date=NOW(),cronica_c_ultmod_username='");
        qry.append(cronica_c_ultmod_username);
        qry.append("' where cronica_n_id=");
        qry.append(cronica_n_id);
        qry.append(" and cronica_c_empresa='");
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

    public static AdmRegistros selectEnfermedadesCronicas(Connection con) {
        Log.debug(Thread.currentThread().getStackTrace()[1].getMethodName());
        StringBuilder qry = new StringBuilder();

        qry.append(" SELECT cronica_n_id,  ");
        qry.append(" cronica_c_name,  ");
        qry.append(" cronica_c_obs,  ");
        qry.append(" cronica_c_numuser_paciente,  ");
        qry.append(" cronica_n_ultmod_numuser,  ");
        qry.append(" to_char(cronica_d_ultmod_date, 'yyyy-mm-dd HH24:MI:SS'),  ");
        qry.append(" cronica_c_ultmod_username,cronica_c_empresa ");
        qry.append(" FROM health_ecronicas where cronica_c_numuser_paciente = ? and cronica_c_empresa =? order by cronica_d_ultmod_date ASC ");
        Log.debug(qry.toString());
        AdmRegistros adm = new AdmRegistros(con,
                qry.toString(),
                8,
                new RFEnfermedadesCronicas()
        );
        adm.setColumna(1, cronica_n_id);
        adm.setColumna(2, cronica_c_name);
        adm.setColumna(3, cronica_c_obs);
        adm.setColumna(4, cronica_c_numuser_paciente);
        adm.setColumna(5, cronica_n_ultmod_numuser);
        adm.setColumna(6, cronica_d_ultmod_date);
        adm.setColumna(7, cronica_c_ultmod_username);
        adm.setColumna(8, cronica_c_empresa);

        return adm;
    }

    public static int deleteEnfermedadCronica(Connection con, String rowCronicaId, String empresa) {

        Log.debug(Thread.currentThread().getStackTrace()[1].getMethodName());
        StringBuilder qry = new StringBuilder();

        qry.append(" DELETE FROM health_ecronicas ");
        qry.append(" WHERE cronica_n_id=");
        qry.append(rowCronicaId);
        qry.append(" and cronica_c_empresa='");
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
