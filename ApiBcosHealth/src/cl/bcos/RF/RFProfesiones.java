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
public class RFProfesiones extends Registro {

    private static final Logger Log = Logger.getLogger(RFSuscripcion.class);

    public static final int prof_n_id = 1;
    public static final int prof_c_nombre = 2;
    public static final int prof_c_empresaname = 3;
    public static final int prof_c_createuser = 4;
    public static final int prof_d_createdate = 5;
    public static final int prof_c_createusername = 6;

    public RFProfesiones() {
        super(7);
    }

    public static int insertProfesion(Connection con, String profesionName,
            String nombre_completo, String usuario_creador, String empresa) {
        Log.debug(Thread.currentThread().getStackTrace()[1].getMethodName());
        StringBuilder qry = new StringBuilder();

        qry.append(" INSERT INTO health_profesiones( ");
        qry.append(" prof_n_id, ");
        qry.append(" prof_c_nombre, ");
        qry.append(" prof_c_empresaname, ");
        qry.append(" prof_c_createuser, ");
        qry.append(" prof_d_createdate, ");
        qry.append(" prof_c_createusername)");
        qry.append(" VALUES ( ");
        qry.append(" nextval('health_seq_profesion') ,UPPER('");
        qry.append(profesionName);
        qry.append("'),'");
        qry.append(empresa);
        qry.append("','");
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

    public static AdmRegistros getProfesiones(Connection con) {
        Log.debug(Thread.currentThread().getStackTrace()[1].getMethodName());
        StringBuilder qry = new StringBuilder();

        qry.append(" SELECT prof_n_id,  ");
        qry.append(" prof_c_nombre,  ");
        qry.append(" prof_c_empresaname,  ");
        qry.append(" prof_c_createuser,  ");
        qry.append(" prof_d_createdate,  ");
        qry.append(" prof_c_createusername ");
        qry.append(" FROM health_profesiones where prof_c_empresaname=? order by prof_c_nombre ASC ");
        Log.debug(qry.toString());
        AdmRegistros adm = new AdmRegistros(con,
                qry.toString(),
                6,
                new RFProfesiones()
        );
        adm.setColumna(1, prof_n_id);
        adm.setColumna(2, prof_c_nombre);
        adm.setColumna(3, prof_c_empresaname);
        adm.setColumna(4, prof_c_createuser);
        adm.setColumna(5, prof_d_createdate);
        adm.setColumna(6, prof_c_createusername);

        return adm;
    }

}
