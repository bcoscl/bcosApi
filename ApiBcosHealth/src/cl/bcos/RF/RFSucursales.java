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
public class RFSucursales extends Registro {

    private static final Logger Log = Logger.getLogger(RFSucursales.class);

    public static final int suc_c_nombre = 1;
    public static final int suc_c_comuna = 2;
    public static final int suc_c_telefono = 3;
    public static final int suc_c_email = 4;
    public static final int suc_c_contacname = 5;
    public static final int suc_n_cod_empresa = 6;
    public static final int suc_c_nombre_empresa = 7;
    public static final int suc_c_estado = 8;
    public static final int suc_c_createusername = 9;
    public static final int suc_c_createuser = 10;
    public static final int suc_n_cod = 11;
    public static final int suc_d_createdate = 12;

    public RFSucursales() {
        super(13);
    }

    public static int insertSucursales(Connection con,
            String suc_c_nombre,
            String suc_c_comuna,
            String suc_c_telefono,
            String suc_c_email,
            String suc_c_contacname,
            String suc_n_cod_empresa,
            String suc_c_nombre_empresa,
            String suc_c_estado,
            String suc_c_createusername,
            String suc_c_createuser) {
        Log.debug(Thread.currentThread().getStackTrace()[1].getMethodName());
        StringBuilder qry = new StringBuilder();

        qry.append(" INSERT INTO health_sucursal(");
        qry.append(" suc_n_cod, ");
        qry.append(" suc_c_nombre, ");
        qry.append(" suc_c_comuna, ");
        qry.append(" suc_c_telefono, ");
        qry.append(" suc_n_cod_empresa, ");
        qry.append(" suc_c_nombre_empresa, ");
        qry.append(" suc_c_email, ");
        qry.append(" suc_c_createuser, ");
        qry.append(" suc_d_createdate, ");
        qry.append(" suc_c_createusername, ");
        qry.append(" suc_c_contacname,suc_c_estado)");
        qry.append(" VALUES (nextval('health_seq_sucursal'),UPPER('");
        qry.append(suc_c_nombre);
        qry.append("'),'");
        qry.append(suc_c_comuna);
        qry.append("','");
        qry.append(suc_c_telefono);
        qry.append("','");
        qry.append(suc_n_cod_empresa);
        qry.append("','");
        qry.append(suc_c_nombre_empresa);
        qry.append("','");
        qry.append(suc_c_email);
        qry.append("','");
        qry.append(suc_c_createuser);
        qry.append("',NOW(),'");
        qry.append(suc_c_createusername);
        qry.append("','");
        qry.append(suc_c_contacname);
        qry.append("','");
        qry.append(suc_c_estado);
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

    public static AdmRegistros selectSucursales(Connection con) {
        Log.debug(Thread.currentThread().getStackTrace()[1].getMethodName());
        StringBuilder qry = new StringBuilder();

        qry.append(" SELECT suc_n_cod, ");
        qry.append(" suc_c_nombre, ");
        qry.append(" suc_c_comuna,  ");
        qry.append(" suc_c_telefono,  ");
        qry.append(" suc_n_cod_empresa,  ");
        qry.append(" suc_c_nombre_empresa,  ");
        qry.append(" suc_c_email,  ");
        qry.append(" suc_c_createuser,  ");
        qry.append(" suc_d_createdate,  ");
        qry.append(" suc_c_createusername, ");
        qry.append(" suc_c_contacname,suc_c_estado ");
        qry.append(" FROM health_sucursal where suc_c_nombre_empresa=? order by suc_c_nombre ASC ");
        Log.debug(qry.toString());

        AdmRegistros adm = new AdmRegistros(con,
                qry.toString(),
                12,
                new RFSuscripcion()
        );
        adm.setColumna(1, suc_n_cod);
        adm.setColumna(2, suc_c_nombre);
        adm.setColumna(3, suc_c_comuna);
        adm.setColumna(4, suc_c_telefono);
        adm.setColumna(5, suc_n_cod_empresa);
        adm.setColumna(6, suc_c_nombre_empresa);
        adm.setColumna(7, suc_c_email);
        adm.setColumna(8, suc_c_createuser);
        adm.setColumna(9, suc_d_createdate);
        adm.setColumna(10, suc_c_createusername);
        adm.setColumna(11, suc_c_contacname);
        adm.setColumna(12, suc_c_estado);

        return adm;
    }

    public static int updateEstado(Connection con, String id, String checkbox_activo, String nombre_completo,String empresa) {
        Log.debug(Thread.currentThread().getStackTrace()[1].getMethodName());
        StringBuilder qry = new StringBuilder();

        qry.append(" UPDATE health_sucursal");
        qry.append(" SET  suc_c_estado= '");
        qry.append(checkbox_activo);
        qry.append("' WHERE suc_n_cod = ");
        qry.append(id);
        qry.append("and  suc_c_nombre_empresa = '");
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

}
