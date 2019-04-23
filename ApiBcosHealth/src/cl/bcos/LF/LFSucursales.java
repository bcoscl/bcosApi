/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.bcos.LF;

import cl.bcos.CF.CFSucursales;
import java.util.Iterator;
import org.apache.log4j.Logger;

/**
 *
 * @author aacantero
 */
public class LFSucursales {
    private static final Logger Log = Logger.getLogger(LFSucursales.class);

    public static int insertSucursales(String nombre_sucursal, String comuna_sucursal, String numero_telefono, String correo_sucursal, String contacto_sucursal, String select_empresa, String select_empresa_name, String checkbox_activo, String nombre_completo, String usuario_creador) {
        Log.debug(Thread.currentThread().getStackTrace()[1].getMethodName());
        return CFSucursales.insertSucursales(nombre_sucursal, comuna_sucursal, numero_telefono, correo_sucursal, contacto_sucursal, select_empresa, select_empresa_name, checkbox_activo, nombre_completo, usuario_creador);
    
    }
    
    public static Iterator selectSucursales(String empresa) {
        Log.debug(Thread.currentThread().getStackTrace()[1].getMethodName());
        return CFSucursales.selectSucursales(empresa);
    }
    public static Iterator selectSucursalesActive(String empresa) {
        Log.debug(Thread.currentThread().getStackTrace()[1].getMethodName());
        return CFSucursales.selectSucursalesActive(empresa);
    }


    public static int updateEstado(String id, String checkbox_activo, String nombre_completo, String empresa) {
        return CFSucursales.updateEstado(id,checkbox_activo, nombre_completo, empresa);
    }
    
}
