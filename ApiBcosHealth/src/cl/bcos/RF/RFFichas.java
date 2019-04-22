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
public class RFFichas extends Registro {

    private static final Logger Log = Logger.getLogger(RFFichas.class);

    public static final int ficha_n_id = 1;
    public static final int ficha_c_numuser_paciente = 2;
    public static final int ficha_c_paciente_name = 3;
    public static final int ficha_d_dateultimamod = 4;
    public static final int ficha_c_userultimamod = 5;
    public static final int ficha_c_role_userultimamod = 6;
    public static final int ficha_c_createuser = 7;
    public static final int ficha_d_createdate = 8;
    public static final int ficha_c_createusername = 9;
    public static final int ficha_c_empresaname = 10;
    public static final int existeRegistro = 11;

    public RFFichas() {
        super(12);
    }

    public static AdmRegistros getNewFichaId(Connection con) {
        Log.debug(Thread.currentThread().getStackTrace()[1].getMethodName());
        StringBuilder qry = new StringBuilder();

        qry.append("select nextval('health_seq_ficha')");
        Log.debug(qry.toString());
        try {
            AdmRegistros adm = new AdmRegistros(con,
                    qry.toString(),
                    1,
                    new RFFichas());

            adm.setColumna(1, ficha_n_id);

            return adm;
        } catch (Exception e) {
            Log.error(e.getMessage());
            throw e;
        }

    }

    public static AdmRegistros existeRegistro(Connection con, String numuser,String empresa ) {
        Log.debug(Thread.currentThread().getStackTrace()[1].getMethodName());
        StringBuilder qry = new StringBuilder();

        qry.append(" select coalesce((select 'SI' Existe from health_ficha u where u.ficha_c_empresaname='");
        qry.append(empresa);
        qry.append("'");
        qry.append(" and u.ficha_c_numuser_paciente='");
        qry.append(numuser);
        qry.append("'),'NO') ");
        Log.debug(qry.toString());
        AdmRegistros adm = new AdmRegistros(con,
                qry.toString(),
                1,
                new RFFichas()
        );
        adm.setColumna(1, existeRegistro);
        return adm;
    }

    public static void rollBack(Connection con, String rowFichaId, String empresa) {

        Log.debug(Thread.currentThread().getStackTrace()[1].getMethodName());
        StringBuilder qry = new StringBuilder();

        qry.append(" DELETE FROM health_ficha ");
        qry.append(" WHERE ficha_n_id=");
        qry.append(rowFichaId);
        qry.append(" and  ficha_c_empresaname='");
        qry.append(empresa);
        qry.append("' ");
        

        PreparedStatement ps = null;
        Log.debug(qry.toString());
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

    public static int insertFicha(Connection con, String rowFichaId, String numuser_paciente,
            String nombre_paciente, String apellido_paciente, String nombre_completo,
            String Roles, String usuario_creador, String createUsername, String empresaName) {

        Log.debug(Thread.currentThread().getStackTrace()[1].getMethodName());
        StringBuilder qry = new StringBuilder();
        nombre_paciente = nombre_paciente + " " + apellido_paciente;

     

        qry.append(" INSERT INTO health_ficha(");
        qry.append(" ficha_n_id, ");
        qry.append(" ficha_c_numuser_paciente, ");
        qry.append(" ficha_c_paciente_name, ");
        qry.append(" ficha_c_userultimamod, ");
        qry.append(" ficha_c_role_userultimamod, ");
        qry.append(" ficha_c_createuser, ");
        qry.append(" ficha_d_createdate, ");
        qry.append(" ficha_c_createusername,");
        qry.append(" ficha_c_empresaname, ");
        qry.append(" ficha_d_dateultimamod, ficha_c_empresaname)");
        qry.append(" VALUES (");
        qry.append(rowFichaId);
        qry.append(",'");
        qry.append(numuser_paciente);
        qry.append("','");
        qry.append(nombre_paciente);
        qry.append("','");
        qry.append(nombre_completo);
        qry.append("','");
        qry.append(Roles);
        qry.append("','");
        qry.append(usuario_creador);
        qry.append("',NOW(),'");
        qry.append(createUsername);
        qry.append("','");
        qry.append(empresaName);
        qry.append("',NOW())");
        

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

    public static AdmRegistros selectFichas(Connection con) {
        Log.debug(Thread.currentThread().getStackTrace()[1].getMethodName());
        StringBuilder qry = new StringBuilder();

        qry.append(" SELECT ficha_n_id,  ");
        qry.append(" ficha_c_numuser_paciente,  ");
        qry.append(" ficha_c_paciente_name,  ");
        qry.append(" to_char(ficha_d_dateultimamod, 'yyyy-mm-dd HH24:MI:SS'),  ");
        qry.append(" ficha_c_userultimamod,  ");
        qry.append(" ficha_c_role_userultimamod,  ");
        qry.append(" ficha_c_createuser,  ");
        qry.append(" to_char(ficha_d_createdate, 'yyyy-mm-dd HH24:MI:SS'),  ");
        qry.append(" ficha_c_createusername,  ");
        qry.append(" ficha_c_empresaname ");
        qry.append(" FROM health_ficha where ficha_c_empresaname=? order by ficha_c_paciente_name ASC ");
        Log.debug(qry.toString());
        AdmRegistros adm = new AdmRegistros(con,
                qry.toString(),
                10,
                new RFFichas()
        );
        adm.setColumna(1, ficha_n_id);
        adm.setColumna(2, ficha_c_numuser_paciente);
        adm.setColumna(3, ficha_c_paciente_name);
        adm.setColumna(4, ficha_d_dateultimamod);
        adm.setColumna(5, ficha_c_userultimamod);
        adm.setColumna(6, ficha_c_role_userultimamod);
        adm.setColumna(7, ficha_c_createuser);
        adm.setColumna(8, ficha_d_createdate);
        adm.setColumna(9, ficha_c_createusername);
        adm.setColumna(10, ficha_c_empresaname);

        return adm;
    }

}
