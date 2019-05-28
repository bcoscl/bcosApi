/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.bcos.LF;

import org.apache.log4j.Logger;
import cl.bcos.CF.*;
import java.util.Iterator;

/**
 *
 * @author aacantero LOGIC FILE LF
 */
public class LFSuscripcion {

    private static final Logger Log = Logger.getLogger(LFSuscripcion.class);

    public static int insertSuscripcion(String nombre_empresa, String contacto_empresa, String email_contacto, String numero_telefono, String fecha_inicio, String select_plan_code, String select_plan_name, String checkbox_activo, String nombre_completo, String usuario_creador, String bucketName) {
        Log.debug(Thread.currentThread().getStackTrace()[1].getMethodName());
        return CFSuscripcion.insertSuscripcion(nombre_empresa, contacto_empresa, email_contacto, numero_telefono, fecha_inicio, select_plan_code, select_plan_name, checkbox_activo, nombre_completo, usuario_creador,bucketName);
    }

    public static Iterator selectSuscripciones() {
        Log.debug(Thread.currentThread().getStackTrace()[1].getMethodName());
        return CFSuscripcion.selectSuscripciones();
    }
    public static Iterator selectSuscripcionesbyempresa(String empresa) {
        Log.debug(Thread.currentThread().getStackTrace()[1].getMethodName());
        return CFSuscripcion.selectSuscripcionesbyEmpresa(empresa);
    }


    public static int updateEstado(String id, String checkbox_activo, String nombre_completo) {
        Log.debug(Thread.currentThread().getStackTrace()[1].getMethodName());
        return CFSuscripcion.updateEstado(id,checkbox_activo, nombre_completo);
    }

    public static int updatePlanSuscripcion(String id, String nombre_empresa, String contacto_empresa, String email_contacto, String numero_telefono, String select_plan_name, String select_plan_code) {
        Log.debug(Thread.currentThread().getStackTrace()[1].getMethodName());
        return CFSuscripcion.updatePlanSuscripcion( id,  nombre_empresa,  contacto_empresa,  email_contacto,  numero_telefono,  select_plan_name,  select_plan_code);
    }

}
