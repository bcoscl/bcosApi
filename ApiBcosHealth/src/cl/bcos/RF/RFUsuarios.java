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
public class RFUsuarios extends Registro {

    private static final Logger Log = Logger.getLogger(RFSuscripcion.class);

    public static final int user_c_nombres = 1;
    public static final int user_c_apellido = 2;
    public static final int user_c_numuser = 3;
    public static final int user_c_role = 4;
    public static final int user_c_celular = 5;
    public static final int user_n_cod_oficia = 6;
    public static final int user_c_nombre_oficina = 7;
    public static final int user_n_cod_empresa = 8;
    public static final int user_c_email = 9;
    public static final int user_n_createuser = 10;
    public static final int user_d_createdate = 11;
    public static final int user_n_iduser = 12;
    public static final int user_c_createusername = 13;
    public static final int user_c_img = 14;
    public static final int user_c_profesion = 15;
    public static final int user_c_obs = 16;
    public static final int existeRegistro = 17;
    public static final int user_id = 18;
    public static final int user_c_empresaname = 19;
    public static final int pass_n_id = 20;
    public static final int pass_c_activo = 21;
    public static final int count = 22;


    public RFUsuarios() {
        super(23);
    }

    public static int insertUsuarios(Connection con,
            String numuser_user,
            String nombre_user,
            String apellido_user,
            String email_contacto_user,
            String numero_telefono_user,
            String profesion_select,
            String textarea_obs,
            String sucursal_select,
            String roles_select,
            String password,
            String checkbox_activo,
            String usuario_creador,
            String nombre_completo,
            String empresaName,
            String rowUserId) {
        Log.debug(Thread.currentThread().getStackTrace()[1].getMethodName());
        StringBuilder qry = new StringBuilder();

        qry.append(" INSERT INTO health_user( ");
        qry.append(" user_c_nombres, ");
        qry.append(" user_c_apellido, ");
        qry.append(" user_c_numuser, ");
        qry.append(" user_c_role, ");
        qry.append(" user_c_celular, ");
        //qry.append(" user_n_cod_sucursal, ");- no se puede porque podria tener mas de una sucursal y se guardara las sucursale separadas por coma
        qry.append(" user_c_nombre_sucursal,");
        qry.append(" user_n_codempresa, user_c_empresaname,");
        qry.append(" user_c_email, ");
        qry.append(" user_c_createuser, ");
        qry.append(" user_d_createdate, ");
        qry.append(" user_n_iduser, ");
        qry.append(" user_c_createusername, ");
        qry.append(" user_c_img, ");
        qry.append(" user_c_profesion, ");
        qry.append(" user_c_obs)");
        qry.append(" VALUES ('");
        qry.append(nombre_user);
        qry.append("','");
        qry.append(apellido_user);
        qry.append("','");
        qry.append(numuser_user);
        qry.append("','");
        qry.append(roles_select);
        qry.append("','");
        qry.append(numero_telefono_user);
        qry.append("','");
        qry.append(sucursal_select);
        qry.append("',");
        qry.append("null,'");/*codigo de Empresa, falta el nombre*/
        qry.append(empresaName);/*nombre de la empresa*/
        qry.append("','");
        qry.append(email_contacto_user);
        qry.append("','");
        qry.append(usuario_creador);
        qry.append("',");
        qry.append("NOW()  at time zone (select params_n_param1 from health_params where params_n_grupo='UTC'and params_n_subgrupo='TIMEZONE' ),");
        qry.append(rowUserId);
        qry.append(",'");
        qry.append(nombre_completo);
        qry.append("',");
        qry.append("null,'");/*URL DE LA IMAGEN*/
        qry.append(profesion_select);
        qry.append("','");
        qry.append(textarea_obs);
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

    /*Valida que el registro no este duplicado*/
    public static AdmRegistros existeRegistro(Connection con, String numuser, String empresa) {
        Log.debug(Thread.currentThread().getStackTrace()[1].getMethodName());
        StringBuilder qry = new StringBuilder();

        qry.append(" select coalesce((select 'SI' Existe from health_user u where u.user_c_empresaname='");
        qry.append(empresa);
        qry.append("' and u.user_c_numuser='");
        qry.append(numuser);
        qry.append("'),'NO') ");
        Log.debug(qry.toString());
        AdmRegistros adm = new AdmRegistros(con,
                qry.toString(),
                1,
                new RFUsuarios()
        );
        adm.setColumna(1, existeRegistro);
        return adm;
    }
    public static AdmRegistros existeRegistrobyEmail(Connection con) {
        Log.debug(Thread.currentThread().getStackTrace()[1].getMethodName());
        StringBuilder qry = new StringBuilder();

        qry.append(" select coalesce((select 'SI' Existe from health_user u where u.user_c_email=? and user_c_numuser = ? limit 1),'NO') ");
        Log.debug(qry.toString());
        AdmRegistros adm = new AdmRegistros(con,
                qry.toString(),
                1,
                new RFUsuarios()
        );
        adm.setColumna(1, existeRegistro);
        return adm;
    }
 
    /*Informacion del usuario*/
    public static AdmRegistros getUserInformation(Connection con) {
        Log.debug(Thread.currentThread().getStackTrace()[1].getMethodName());
        StringBuilder qry = new StringBuilder();

        qry.append(" SELECT user_c_nombres, ");
        qry.append(" user_c_apellido,  ");
        qry.append(" user_c_numuser,  ");
        qry.append(" user_c_role,  ");
        qry.append(" user_c_celular,  ");
        qry.append(" user_c_nombre_sucursal,  ");
        qry.append(" user_c_email,  ");
        qry.append(" user_d_createdate,  ");
        qry.append(" user_c_createusername,  ");
        qry.append(" user_c_img,  ");
        qry.append(" user_c_profesion,  ");
        qry.append(" user_c_obs,  ");
        qry.append(" user_c_empresaname,  ");
        qry.append(" user_c_createuser,  ");
        qry.append(" user_n_codempresa,  ");
        qry.append(" user_n_iduser ,pass_n_id, pass_c_activo");
        qry.append(" FROM health_user INNER JOIN health_pass  ON user_c_numuser = pass_c_numuser ");
        qry.append(" where user_c_numuser = ? ");
        Log.debug(qry.toString());
        AdmRegistros adm = new AdmRegistros(con,
                qry.toString(),
                18,
                new RFUsuarios()
        );
        adm.setColumna(1, user_c_nombres);
        adm.setColumna(2, user_c_apellido);
        adm.setColumna(3, user_c_numuser);
        adm.setColumna(4, user_c_role);
        adm.setColumna(5, user_c_celular);
        adm.setColumna(6, user_c_nombre_oficina);
        adm.setColumna(7, user_c_email);
        adm.setColumna(8, user_d_createdate);
        adm.setColumna(9, user_c_createusername);
        adm.setColumna(10, user_c_img);
        adm.setColumna(11, user_c_profesion);
        adm.setColumna(12, user_c_obs);
        adm.setColumna(13, user_c_empresaname);
        adm.setColumna(14, user_n_createuser);
        adm.setColumna(15, user_n_cod_empresa);
        adm.setColumna(16, user_n_iduser);
        adm.setColumna(17, pass_n_id);
        adm.setColumna(18, pass_c_activo);
        return adm;
    }

    public static AdmRegistros selectUsuarios(Connection con) {
        Log.debug(Thread.currentThread().getStackTrace()[1].getMethodName());
        StringBuilder qry = new StringBuilder();

        qry.append(" SELECT user_c_nombres, ");
        qry.append(" user_c_apellido,  ");
        qry.append(" user_c_numuser,  ");
        qry.append(" user_c_role,  ");
        qry.append(" user_c_celular,  ");
        qry.append(" user_c_nombre_sucursal,  ");
        qry.append(" user_c_email,  ");
        qry.append(" user_d_createdate,  ");
        qry.append(" user_c_createusername,  ");
        qry.append(" user_c_img,  ");
        qry.append(" user_c_profesion,  ");
        qry.append(" user_c_obs,  ");
        qry.append(" user_c_empresaname,  ");
        qry.append(" user_c_createuser,  ");
        qry.append(" user_n_codempresa,  ");
        qry.append(" user_n_iduser ,pass_n_id, pass_c_activo");
        qry.append(" FROM health_user INNER JOIN health_pass  ON user_c_numuser = pass_c_numuser ");
        qry.append(" where user_c_empresaname=? order by user_c_nombres ASC ");
        Log.debug(qry.toString());
        AdmRegistros adm = new AdmRegistros(con,
                qry.toString(),
                18,
                new RFUsuarios()
        );
        adm.setColumna(1, user_c_nombres);
        adm.setColumna(2, user_c_apellido);
        adm.setColumna(3, user_c_numuser);
        adm.setColumna(4, user_c_role);
        adm.setColumna(5, user_c_celular);
        adm.setColumna(6, user_c_nombre_oficina);
        adm.setColumna(7, user_c_email);
        adm.setColumna(8, user_d_createdate);
        adm.setColumna(9, user_c_createusername);
        adm.setColumna(10, user_c_img);
        adm.setColumna(11, user_c_profesion);
        adm.setColumna(12, user_c_obs);
        adm.setColumna(13, user_c_empresaname);
        adm.setColumna(14, user_n_createuser);
        adm.setColumna(15, user_n_cod_empresa);
        adm.setColumna(16, user_n_iduser);
        adm.setColumna(17, pass_n_id);
        adm.setColumna(18, pass_c_activo);
        return adm;
    }

    /*Obtiene el ID de Insercion de la secuencia*/
    public static AdmRegistros getNewUserId(Connection con) {
        Log.debug(Thread.currentThread().getStackTrace()[1].getMethodName());
        StringBuilder qry = new StringBuilder();

        qry.append("select nextval('health_seq_user')");
        Log.debug(qry.toString());
        try {
            AdmRegistros adm = new AdmRegistros(con,
                    qry.toString(),
                    1,
                    new RFUsuarios());

            adm.setColumna(1, user_id);

            return adm;
        } catch (Exception e) {
            Log.error(e.getMessage());
            throw e;
        }

    }

    public static void rollBack(Connection con, String user_n_iduser, String empresa) {

        Log.debug(Thread.currentThread().getStackTrace()[1].getMethodName());
        StringBuilder qry = new StringBuilder();

        qry.append(" DELETE FROM health_user ");
        qry.append(" WHERE user_n_iduser=");
        qry.append(user_n_iduser);
        qry.append(" and user_c_empresaname='");
        qry.append(empresa);
        qry.append("'");
        
        
        
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

    public static int updateEstado(Connection con, String id, String checkbox_activo, String nombre_completo, String empresa) {
        Log.debug(Thread.currentThread().getStackTrace()[1].getMethodName());
        StringBuilder qry = new StringBuilder();

        qry.append(" UPDATE health_pass ");
        qry.append(" SET  pass_c_activo= '");
        qry.append(checkbox_activo);
        qry.append("' WHERE pass_n_id = ");
        qry.append(id);
        qry.append(" and user_c_empresaname='");
        qry.append(empresa);
        qry.append("'");
        
        Log.debug(qry.toString());
        PreparedStatement ps = null;

        try {
            ps = con.prepareStatement(qry.toString());
            Log.debug(qry.toString());
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
    public static int updateImg(Connection con, String id, String imgName,String empresa) {
        Log.debug(Thread.currentThread().getStackTrace()[1].getMethodName());
        StringBuilder qry = new StringBuilder();

        qry.append(" UPDATE health_user ");
        qry.append(" SET  user_c_img= '");
        qry.append(imgName);
        qry.append("' WHERE user_n_iduser = ");
        qry.append(id);
        qry.append(" and user_c_empresaname='");
        qry.append(empresa);
        qry.append("'");
        
        Log.debug(qry.toString());
        PreparedStatement ps = null;

        try {
            ps = con.prepareStatement(qry.toString());
            Log.debug(qry.toString());
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
    
      
    public static int changePass(Connection con, String usuario, String passs) {
        Log.debug(Thread.currentThread().getStackTrace()[1].getMethodName());
        StringBuilder qry = new StringBuilder();

        qry.append(" UPDATE health_pass ");
        qry.append(" SET  pass_c_password= '");
        qry.append(passs);
        qry.append("' WHERE pass_c_numuser = '");
        qry.append(usuario);
        qry.append("' ");
        
        
        Log.debug(qry.toString());
        PreparedStatement ps = null;

        try {
            ps = con.prepareStatement(qry.toString());
            Log.debug(qry.toString());
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

    
    public static AdmRegistros cuentaUsuarios(Connection con) {
        Log.debug(Thread.currentThread().getStackTrace()[1].getMethodName());
        StringBuilder qry = new StringBuilder();

        qry.append("SELECT count(1) FROM health_user where user_c_empresaname = ? ");
        Log.debug(qry.toString());
        try {
            AdmRegistros adm = new AdmRegistros(con,
                    qry.toString(),
                    1,
                    new RFUsuarios());

            adm.setColumna(1, count);

            return adm;
        } catch (Exception e) {
            Log.error(e.getMessage());
            throw e;
        }

    }
    
}
