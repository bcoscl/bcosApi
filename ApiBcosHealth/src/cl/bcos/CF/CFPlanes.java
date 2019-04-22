/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.bcos.CF;

import cl.bcos.RF.RFPlanes;
import cl.bcos.bd.Pool;
import cl.bcos.data.TabRegistros;
import java.sql.Connection;
import java.util.Iterator;
import org.apache.log4j.Logger;

/**
 *
 * @author aacantero
 */
public class CFPlanes {

    private static final Logger Log = Logger.getLogger(CFPlanes.class);
    private static final String conexionName = "conexionOCT";

    public static int insertPlan(String nombre_plan, String numero_maximo, String usuario_creador, String nombreUsuario) {
        Log.debug(Thread.currentThread().getStackTrace()[1].getMethodName());
        Connection con = null;
        
        try {
            con = Pool.getInstancia().getConnection(conexionName);
            return RFPlanes.insertPlanes(con, nombre_plan, numero_maximo, usuario_creador,nombreUsuario);

        } catch (Exception e) {
            Log.error(e.toString());
            return 0;
        } finally {
            Pool.getInstancia().free(con);
        }
    }

    public static Iterator selectPlanes() {
        Log.debug(Thread.currentThread().getStackTrace()[1].getMethodName());
        Connection con = null;
        Iterator it = null;
        try {
            con = Pool.getInstancia().getConnection(conexionName);
            String[] param = {};
            TabRegistros tab = new TabRegistros();
            tab.setContext(RFPlanes.selectPlanes(con));
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

}
