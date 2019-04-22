/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.bcos.CF;

import cl.bcos.RF.RFSuscripcion;
import cl.bcos.bd.Pool;
import cl.bcos.data.TabRegistros;
import java.sql.Connection;
import java.util.Iterator;
import org.apache.log4j.Logger;

/**
 *
 * @author aacantero
 */
public class CFSuscripcion {

    private static final Logger Log = Logger.getLogger(CFSuscripcion.class);
    private static final String conexionName = "conexionOCT";

    public static int insertSuscripcion(String nombre_empresa, String contacto_empresa, String email_contacto, String numero_telefono, String fecha_inicio, String select_plan_code, String select_plan_name, String checkbox_activo, String nombre_completo, String usuario_creador) {
        Log.debug(Thread.currentThread().getStackTrace()[1].getMethodName());
        Connection con = null;

        try {
            con = Pool.getInstancia().getConnection(conexionName);
            return RFSuscripcion.insertSuscripcion(con, nombre_empresa, contacto_empresa, email_contacto, numero_telefono, fecha_inicio, select_plan_code, select_plan_name, checkbox_activo, nombre_completo, usuario_creador);

        } catch (Exception e) {
            Log.error(e.toString());
            return 0;
        } finally {
            Pool.getInstancia().free(con);
        }
    }

    public static Iterator selectSuscripciones() {
        Log.debug(Thread.currentThread().getStackTrace()[1].getMethodName());
        Connection con = null;
        Iterator it = null;
        try {
            con = Pool.getInstancia().getConnection(conexionName);
            String[] param = {};
            TabRegistros tab = new TabRegistros();
            tab.setContext(RFSuscripcion.selectSuscripciones(con));
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

    public static int updateEstado(String id,String checkbox_activo, String nombre_completo) {
        Log.debug(Thread.currentThread().getStackTrace()[1].getMethodName());
        
        Connection con = null;

        try {
            con = Pool.getInstancia().getConnection(conexionName);
            return RFSuscripcion.updateEstado(con, id,checkbox_activo, nombre_completo);

        } catch (Exception e) {
            Log.error(e.toString());
            return 0;
        } finally {
            Pool.getInstancia().free(con);
        }
    }

}
