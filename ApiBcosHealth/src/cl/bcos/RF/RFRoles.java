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
public class RFRoles extends Registro {

    private static final Logger Log = Logger.getLogger(RFSuscripcion.class);

    public static final int rol_n_id = 1;
    public static final int rol_c_rolename = 2;
    public static final int rol_c_empresaname = 3;
    public static final int rol_c_createuser = 4;
    public static final int rol_d_createdate = 5;
    public static final int rol_c_createusername = 6;

    public RFRoles() {
        super(7);
    }

    public static int insertRoles(Connection con, String RoleName,
            String nombre_completo, String usuario_creador) {
        Log.debug(Thread.currentThread().getStackTrace()[1].getMethodName());

        StringBuilder qry = new StringBuilder();

        qry.append(" INSERT INTO health_roles(rol_n_id, rol_c_rolename, rol_c_empresaname, rol_c_createuser, rol_d_createdate, rol_c_createusername) ");
        qry.append(" VALUES ( ");
        qry.append(" nextval('health_seq_rol') ,UPPER('");
        qry.append(RoleName);
        /* subscr_c_empresaname */
        qry.append("'),null,'");
        qry.append(usuario_creador);
        qry.append("',NOW(),'");
        qry.append(nombre_completo);
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

    public static AdmRegistros getRoles(Connection con) {
        Log.debug(Thread.currentThread().getStackTrace()[1].getMethodName());
        StringBuilder qry = new StringBuilder();

        qry.append(" SELECT rol_n_id,  ");
        qry.append(" rol_c_rolename,  ");
        qry.append(" rol_c_empresaname,  ");
        qry.append(" rol_c_createuser,  ");
        qry.append(" rol_d_createdate,  ");
        qry.append(" rol_c_createusername ");
        qry.append(" FROM health_roles order by rol_c_rolename ASC ");
        Log.debug(qry.toString());
        AdmRegistros adm = new AdmRegistros(con,
                qry.toString(),
                6,
                new RFRoles()
        );
        adm.setColumna(1, rol_n_id);
        adm.setColumna(2, rol_c_rolename);
        adm.setColumna(3, rol_c_empresaname);
        adm.setColumna(4, rol_c_createuser);
        adm.setColumna(5, rol_d_createdate);
        adm.setColumna(6, rol_c_createusername);

        return adm;
    }

}
