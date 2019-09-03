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
public class RFSSO extends Registro {

    private static final Logger Log = Logger.getLogger(RFSSO.class);

    public static final int usuariovalido = 1;
    public static final int user_id = 2;

    public RFSSO() {
        super(3);
    }

    /*Validacion de si existe para el ingreso de la persona, Autenticacion*/
    public static AdmRegistros autenticacion(Connection con) {
        Log.debug(Thread.currentThread().getStackTrace()[1].getMethodName());
        StringBuilder qry = new StringBuilder();

        qry.append("SELECT 'OK' FROM public.health_pass p ");
        qry.append("where p.pass_c_numuser=?");
        qry.append("and p.pass_c_password=? and upper(p.pass_c_activo)='TRUE'");
        Log.debug(qry.toString());
        try {
            AdmRegistros adm = new AdmRegistros(con,
                    qry.toString(),
                    1,
                    new RFSSO());

            adm.setColumna(1, usuariovalido);

            return adm;
        } catch (Exception e) {
            Log.error(e.getMessage());
            throw e;
        }

    }

    /*Obtiene el ID de Insercion de la secuencia*/
    public static AdmRegistros getNewUserId(Connection con) {
        Log.debug(Thread.currentThread().getStackTrace()[1].getMethodName());
        StringBuilder qry = new StringBuilder();

        qry.append("select nextval('health_seq_pass') ");
        Log.debug(qry.toString());
        try {
            AdmRegistros adm = new AdmRegistros(con,
                    qry.toString(),
                    1,
                    new RFSSO());

            adm.setColumna(1, user_id);

            return adm;
        } catch (Exception e) {
            Log.error(e.getMessage());
            throw e;
        }

    }

    /*insert de Usuarios*/
    public static int insertUserPass(Connection con,
            String pass_c_numuser,
            String pass_c_password,
            //String pass_d_vencimiento,
            String pass_c_createuser,
            String pass_c_activo,
            String pass_c_createusername,
            String pass_n_id) {

        Log.debug(Thread.currentThread().getStackTrace()[1].getMethodName());
        StringBuilder qry = new StringBuilder();

        qry.append(" INSERT INTO health_pass( ");
        qry.append(" pass_c_numuser,  ");
        qry.append(" pass_c_password, ");
        qry.append(" pass_d_vencimiento, ");
        qry.append(" pass_c_createuser,  ");
        qry.append(" pass_d_createdate,  ");
        qry.append(" pass_c_activo,  ");
        qry.append(" pass_c_createusername,  ");
        qry.append(" pass_n_id) ");

        qry.append(" VALUES ('");
        qry.append(pass_c_numuser);
        qry.append("','");
        qry.append(pass_c_password);
        qry.append("',NOW()  at time zone (select params_n_param1 from health_params where params_n_grupo='UTC'and params_n_subgrupo='TIMEZONE' ),'");
        qry.append(pass_c_createuser);
        qry.append("',NOW() at time zone (select params_n_param1 from health_params where params_n_grupo='UTC'and params_n_subgrupo='TIMEZONE' ),'");
        qry.append(pass_c_activo);
        qry.append("','");
        qry.append(pass_c_createusername);
        qry.append("',");
        qry.append(pass_n_id);
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

    public static void rollBack(Connection con, String pass_n_id) {

        Log.debug(Thread.currentThread().getStackTrace()[1].getMethodName());
        StringBuilder qry = new StringBuilder();

        qry.append(" DELETE FROM health_pass ");
        qry.append(" WHERE pass_n_id=");

        qry.append(pass_n_id);
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
