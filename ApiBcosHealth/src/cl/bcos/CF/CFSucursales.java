/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.bcos.CF;

import cl.bcos.RF.RFSucursales;
import cl.bcos.bd.Pool;
import cl.bcos.data.TabRegistros;
import java.sql.Connection;
import java.util.Iterator;
import org.apache.log4j.Logger;

/**
 *
 * @author aacantero
 */
public class CFSucursales {
    
    private static final Logger Log = Logger.getLogger(CFSuscripcion.class);
    private static final String conexionName = "conexionOCT";

    public static int insertSucursales(String nombre_sucursal, String comuna_sucursal, String numero_telefono, String correo_sucursal, String contacto_sucursal, String select_empresa, String select_empresa_name, String checkbox_activo, String nombre_completo, String usuario_creador) {
         Log.debug(Thread.currentThread().getStackTrace()[1].getMethodName());
        Connection con = null;

        try {
            con = Pool.getInstancia().getConnection(conexionName);
            return RFSucursales.insertSucursales(con,  nombre_sucursal,  comuna_sucursal,  numero_telefono,  correo_sucursal,  contacto_sucursal,  select_empresa,  select_empresa_name,  checkbox_activo,  nombre_completo,  usuario_creador);

        } catch (Exception e) {
            Log.error(e.toString());
            return 0;
        } finally {
            Pool.getInstancia().free(con);
        }
    }
    
    
    public static Iterator selectSucursales(String empresa) {
        Log.debug(Thread.currentThread().getStackTrace()[1].getMethodName());
        Connection con = null;
        Iterator it = null;
        try {
            con = Pool.getInstancia().getConnection(conexionName);
            String[] param = {empresa};
            TabRegistros tab = new TabRegistros();
            tab.setContext(RFSucursales.selectSucursales(con));
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
            return RFSucursales.updateEstado(con, id,checkbox_activo, nombre_completo, empresa);

        } catch (Exception e) {
            Log.error(e.toString());
            return 0;
        } finally {
            Pool.getInstancia().free(con);
        }
    }
    
}
