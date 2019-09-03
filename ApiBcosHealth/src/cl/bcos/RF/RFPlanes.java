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
public class RFPlanes extends Registro {

    private static final Logger Log = Logger.getLogger(RFPlanes.class);

    public static final int nombre_plan = 1;
    public static final int numero_maximo = 2;
    public static final int fecha_creacion = 3;
    public static final int nombre_usuario_creador = 4;

    public RFPlanes() {
        super(5);
    }

    public static int insertPlanes(Connection con, String nombre_plan, String numero_maximo, String usuario_creador, String nombreUsuario) {
        Log.debug(Thread.currentThread().getStackTrace()[1].getMethodName());
        StringBuilder qry = new StringBuilder();

        qry.append(" insert into health_plan  ");
        qry.append(" (plan_n_id,plan_c_name,plan_n_max,plan_c_createuser,plan_d_createdate,plan_c_createusername)  ");
        qry.append(" values (nextval('health_seq_plan'), upper('");
        qry.append(nombre_plan);
        qry.append("'),");
        qry.append(numero_maximo);
        qry.append(",'");
        qry.append(usuario_creador);
        qry.append("', NOW()  at time zone (select params_n_param1 from health_params where params_n_grupo='UTC'and params_n_subgrupo='TIMEZONE' ),'");
        qry.append(nombreUsuario);
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

    public static AdmRegistros selectPlanes(Connection con) {
        Log.debug(Thread.currentThread().getStackTrace()[1].getMethodName());
        StringBuilder qry = new StringBuilder();

        qry.append(" SELECT plan_c_name,plan_n_max,to_char(plan_d_createdate, 'yyyy-mm-dd HH24:MI:SS'),plan_c_createusername  ");
        qry.append(" FROM public.health_plan p order by plan_n_max ASC ");
        Log.debug(qry.toString());
        AdmRegistros adm = new AdmRegistros(con,
                qry.toString(),
                4,
                new RFPlanes()
        );
        adm.setColumna(1, nombre_plan);
        adm.setColumna(2, numero_maximo);
        adm.setColumna(3, fecha_creacion);
        adm.setColumna(4, nombre_usuario_creador);

        return adm;
    }

}
