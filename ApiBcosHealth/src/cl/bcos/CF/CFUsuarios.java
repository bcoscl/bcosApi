/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.bcos.CF;

import cl.bcos.RF.RFUsuarios;
import cl.bcos.bd.Pool;
import cl.bcos.data.Registro;
import cl.bcos.data.TabRegistros;
import java.sql.Connection;
import java.util.Iterator;
import org.apache.log4j.Logger;

/**
 *
 * @author aacantero
 */
public class CFUsuarios {

    private static final Logger Log = Logger.getLogger(CFUsuarios.class);
    private static final String conexionName = "conexionOCT";

    public static int insertUsuario(String numuser_user, String nombre_user, String apellido_user, String email_contacto_user, String numero_telefono_user, String profesion_select, String textarea_obs, String sucursal_select, String roles_select, String password, String checkbox_activo, String usuario_creador, String nombre_completo, String empresaName, String rowUserId) {
        Log.debug(Thread.currentThread().getStackTrace()[1].getMethodName());
        Connection con = null;

        try {
            con = Pool.getInstancia().getConnection(conexionName);
            return RFUsuarios.insertUsuarios(con, numuser_user, nombre_user, apellido_user, email_contacto_user, numero_telefono_user, profesion_select, textarea_obs, sucursal_select, roles_select, password, checkbox_activo, usuario_creador, nombre_completo, empresaName, rowUserId);

        } catch (Exception e) {
            Log.error(e.toString());
            return 0;
        } finally {
            Pool.getInstancia().free(con);
        }
    }

    public static Iterator existeRegistro(String numuser, String empresa) {
        Log.debug(Thread.currentThread().getStackTrace()[1].getMethodName());
        Connection con = null;
        Iterator it = null;
        try {
            con = Pool.getInstancia().getConnection(conexionName);
            String[] param = {};
            TabRegistros tab = new TabRegistros();
            tab.setContext(RFUsuarios.existeRegistro(con, numuser, empresa));
            tab.execute(tab.USE_RS, param);
            it = tab.getRegistros();
            return it;
        } catch (Exception e) {
            Log.error(e.toString());
        } finally {
            Pool.getInstancia().free(con);
        }
        return it;
    }

    public static Iterator getUserInformation(String numuser) {
        Log.debug(Thread.currentThread().getStackTrace()[1].getMethodName());
        Connection con = null;
        Iterator it = null;
        try {
            con = Pool.getInstancia().getConnection(conexionName);
            String[] param = {numuser};
            TabRegistros tab = new TabRegistros();
            tab.setContext(RFUsuarios.getUserInformation(con));
            tab.execute(tab.USE_RS, param);
            it = tab.getRegistros();
            return it;
        } catch (Exception e) {
            Log.error(e.toString());
        } finally {
            Pool.getInstancia().free(con);
        }
        return it;
    }

    public static String getNewUserId() {
        Log.debug(Thread.currentThread().getStackTrace()[1].getMethodName());
        Connection con = null;
        String ret = "";
        try {
            con = Pool.getInstancia().getConnection(conexionName);
            String[] param = {};
            TabRegistros tab = new TabRegistros();
            tab.setContext(RFUsuarios.getNewUserId(con));
            tab.execute(tab.USE_RS, param);
            Iterator it = tab.getRegistros();

            while (it.hasNext()) {
                Registro reg = (Registro) it.next();
                ret = reg.get(RFUsuarios.user_id);
            }
        } catch (Exception e) {
            Log.error(e.toString());
        } finally {
            Pool.getInstancia().free(con);
        }
        return ret;
    }

    public static void rollBack(String id, String empresa) {
        Log.debug(Thread.currentThread().getStackTrace()[1].getMethodName());
        Connection con = null;

        try {
            con = Pool.getInstancia().getConnection(conexionName);
            RFUsuarios.rollBack(con, id, empresa);

        } catch (Exception e) {
            Log.error(e.toString());
            //return 0;
        } finally {
            Pool.getInstancia().free(con);
        }
    }

    public static Iterator selectUsuarios(String empresa) {
        Log.debug(Thread.currentThread().getStackTrace()[1].getMethodName());
        Connection con = null;
        Iterator it = null;
        try {
            con = Pool.getInstancia().getConnection(conexionName);
            String[] param = {empresa};
            TabRegistros tab = new TabRegistros();
            tab.setContext(RFUsuarios.selectUsuarios(con));
            tab.execute(tab.USE_RS, param);
            it = tab.getRegistros();
            return it;
        } catch (Exception e) {
            Log.error(e.toString());
        } finally {
            Pool.getInstancia().free(con);
        }
        return it;
    }

     public static int updateEstado(String id,String checkbox_activo, String nombre_completo, String empresa) {
        Log.debug(Thread.currentThread().getStackTrace()[1].getMethodName());
        
        Connection con = null;

        try {
            con = Pool.getInstancia().getConnection(conexionName);
            return RFUsuarios.updateEstado(con, id,checkbox_activo, nombre_completo, empresa);

        } catch (Exception e) {
            Log.error(e.toString());
            return 0;
        } finally {
            Pool.getInstancia().free(con);
        }
    }
     public static int updateImg(String id,String imgName,String empresa) {
        Log.debug(Thread.currentThread().getStackTrace()[1].getMethodName());
        
        Connection con = null;

        try {
            con = Pool.getInstancia().getConnection(conexionName);
            return RFUsuarios.updateImg(con, id,imgName, empresa);

        } catch (Exception e) {
            Log.error(e.toString());
            return 0;
        } finally {
            Pool.getInstancia().free(con);
        }
    }

    public static Iterator existeRegistrobyEmail(String email, String numuser) {
        Log.debug(Thread.currentThread().getStackTrace()[1].getMethodName());
        Connection con = null;
        Iterator it = null;
        try {
            con = Pool.getInstancia().getConnection(conexionName);
            String[] param = {email, numuser};
            TabRegistros tab = new TabRegistros();
            tab.setContext(RFUsuarios.existeRegistrobyEmail(con));
            tab.execute(tab.USE_RS, param);
            it = tab.getRegistros();
            return it;
        } catch (Exception e) {
            Log.error(e.toString());
        } finally {
            Pool.getInstancia().free(con);
        }
        return it;
    }

    public static int changePass(String usuario, String passs) {
        Log.debug(Thread.currentThread().getStackTrace()[1].getMethodName());
        
        Connection con = null;

        try {
            con = Pool.getInstancia().getConnection(conexionName);
            return RFUsuarios.changePass(con, usuario,passs);

        } catch (Exception e) {
            Log.error(e.toString());
            return 0;
        } finally {
            Pool.getInstancia().free(con);
        }
    }
    public static int cuentaUsuarios(String empresa) {
        Log.debug(Thread.currentThread().getStackTrace()[1].getMethodName());
        Connection con = null;
        Iterator it = null;
        try {
            con = Pool.getInstancia().getConnection(conexionName);
            String[] param = {empresa};
            TabRegistros tab = new TabRegistros();
            tab.setContext(RFUsuarios.cuentaUsuarios(con));
            tab.execute(tab.USE_RS, param);
            it = tab.getRegistros();
            String max="0";
            while (it.hasNext()) {
                Registro reg = (Registro) it.next();
                max = reg.get(RFUsuarios.count);
            }
            return Integer.valueOf(max);
        } catch (Exception e) {
            Log.error(e.toString());
        } finally {
            Pool.getInstancia().free(con);
        }
        return 0;
    }
}
