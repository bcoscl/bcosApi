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
public class RFSuscripcion extends Registro {

    private static final Logger Log = Logger.getLogger(RFSuscripcion.class);

    private static final int numCampos = 13;

    public static final int nombre_empresa = 1;
    public static final int nombre_contacto = 2;
    public static final int email = 3;
    public static final int telefono = 4;
    public static final int id = 5;
    public static final int fecha_inicio = 6;
    public static final int nombre_plan = 7;
    public static final int usermax = 8;
    public static final int estado = 9;
    public static final int usuario_creador = 10;
    public static final int fecha_creacion = 11;
    public static final int nombre_usuario_creador = 12;

    public RFSuscripcion() {
        super(13);
    }

    public static int insertSuscripcion(Connection con, String nombre_empresa, String contacto_empresa,
            String email_contacto, String numero_telefono, String fecha_inicio,
            String select_plan_code, String select_plan_name, String checkbox_activo,
            String nombre_completo, String usuario_creador) {
        Log.debug(Thread.currentThread().getStackTrace()[1].getMethodName());
        StringBuilder qry = new StringBuilder();

        qry.append(" INSERT INTO health_subscription( ");
        qry.append(" subscr_n_id, ");
        qry.append(" subscr_c_empresaname, ");
        qry.append(" subscr_c_contacto, ");
        qry.append(" subscr_c_email, ");
        qry.append(" subscr_c_telefono, ");
        qry.append(" subscr_d_fechainicio, ");
        qry.append(" subscr_c_planname, ");
        qry.append(" subscr_n_max, ");
        qry.append(" subscr_c_estado, ");
        qry.append(" subscr_n_createuser, ");
        qry.append(" subscr_d_createdate, ");
        qry.append(" subscr_c_createusername) ");
        qry.append(" VALUES ( ");
        qry.append(" nextval('health_seq_subcription') /* subscr_n_id */,UPPER('");
        qry.append(nombre_empresa);
        /* subscr_c_empresaname */
        qry.append("'),'");
        qry.append(contacto_empresa);/* subscr_c_contacto */
        qry.append("','");
        qry.append(email_contacto);/* subscr_c_email */
        qry.append("','");
        qry.append(numero_telefono);/* subscr_c_telefono */
        qry.append("','");
        qry.append(fecha_inicio);/* subscr_d_fechainicio */
        qry.append("','");
        qry.append(select_plan_name);/* subscr_c_planname */
        qry.append("','");
        qry.append(select_plan_code);/* subscr_n_max */
        qry.append("','");
        qry.append(checkbox_activo);/* subscr_c_estado */
        qry.append("','");
        qry.append(usuario_creador);/* subscr_n_createuser */
        qry.append("',");
        qry.append("NOW()/* subscr_d_createdate */,'");
        qry.append(nombre_completo/* subscr_c_createusername */);
        qry.append("')");

        PreparedStatement ps = null;
        Log.debug(qry.toString());
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

    public static AdmRegistros selectSuscripciones(Connection con) {
        Log.debug(Thread.currentThread().getStackTrace()[1].getMethodName());
        StringBuilder qry = new StringBuilder();

        qry.append(" SELECT subscr_n_id,  ");
        qry.append(" subscr_c_empresaname,  ");
        qry.append(" subscr_c_contacto,  ");
        qry.append(" subscr_c_email,  ");
        qry.append(" subscr_c_telefono,  ");
        qry.append(" subscr_d_fechainicio,  ");
        qry.append(" subscr_c_planname,  ");
        qry.append(" subscr_n_max,  ");
        qry.append(" subscr_c_estado,  ");
        qry.append(" subscr_n_createuser,  ");
        qry.append(" subscr_d_createdate,  ");
        qry.append(" subscr_c_createusername ");
        qry.append(" FROM health_subscription order by subscr_c_empresaname ASC ");
        Log.debug(qry.toString());
        AdmRegistros adm = new AdmRegistros(con,
                qry.toString(),
                12,
                new RFSuscripcion()
        );
        adm.setColumna(1, id);
        adm.setColumna(2, nombre_empresa);
        adm.setColumna(3, nombre_contacto);
        adm.setColumna(4, email);
        adm.setColumna(5, telefono);
        adm.setColumna(6, fecha_inicio);
        adm.setColumna(7, nombre_plan);
        adm.setColumna(8, usermax);
        adm.setColumna(9, estado);
        adm.setColumna(10, usuario_creador);
        adm.setColumna(11, fecha_creacion);
        adm.setColumna(12, nombre_usuario_creador);

        return adm;
    }
    public static AdmRegistros selectSuscripcionesByEmpresa(Connection con) {
        Log.debug(Thread.currentThread().getStackTrace()[1].getMethodName());
        StringBuilder qry = new StringBuilder();

        qry.append(" SELECT subscr_n_id,  ");
        qry.append(" subscr_c_empresaname,  ");
        qry.append(" subscr_c_contacto,  ");
        qry.append(" subscr_c_email,  ");
        qry.append(" subscr_c_telefono,  ");
        qry.append(" subscr_d_fechainicio,  ");
        qry.append(" subscr_c_planname,  ");
        qry.append(" subscr_n_max,  ");
        qry.append(" subscr_c_estado,  ");
        qry.append(" subscr_n_createuser,  ");
        qry.append(" subscr_d_createdate,  ");
        qry.append(" subscr_c_createusername ");
        qry.append(" FROM health_subscription where  subscr_c_empresaname=? order by subscr_c_empresaname ASC ");
        Log.debug(qry.toString());
        AdmRegistros adm = new AdmRegistros(con,
                qry.toString(),
                12,
                new RFSuscripcion()
        );
        adm.setColumna(1, id);
        adm.setColumna(2, nombre_empresa);
        adm.setColumna(3, nombre_contacto);
        adm.setColumna(4, email);
        adm.setColumna(5, telefono);
        adm.setColumna(6, fecha_inicio);
        adm.setColumna(7, nombre_plan);
        adm.setColumna(8, usermax);
        adm.setColumna(9, estado);
        adm.setColumna(10, usuario_creador);
        adm.setColumna(11, fecha_creacion);
        adm.setColumna(12, nombre_usuario_creador);

        return adm;
    }

    public static int updateEstado(Connection con, String id, String checkbox_activo, String nombre_completo) {
        Log.debug(Thread.currentThread().getStackTrace()[1].getMethodName());
        StringBuilder qry = new StringBuilder();

        qry.append(" UPDATE health_subscription ");
        qry.append(" SET  subscr_c_estado= '");
        qry.append(checkbox_activo);
        qry.append("' WHERE subscr_n_id = ");
        qry.append(id);
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
