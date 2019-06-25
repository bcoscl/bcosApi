/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.bcos.LF;

import cl.bcos.CF.CFEvaluaciones;
import cl.bcos.CF.CFEvaluaciones;
import java.util.Iterator;
import org.apache.log4j.Logger;

/**
 *
 * @author aacantero
 */
public class LFEvaluaciones {

    private static final Logger Log = Logger.getLogger(LFEvaluaciones.class);

    public static int insertEvaluacion(String eva_paciente,
            String eva_fecha,
            String eva_talla,
            String eva_peso,
            String eva_fat,
            String eva_fatv,
            String eva_musc,
            String eva_obs,
            String usuario_creador,
            String nombre_completo,
            String empresa) {

        Log.debug(Thread.currentThread().getStackTrace()[1].getMethodName());
        return CFEvaluaciones.insertEvaluacion(eva_paciente,
                eva_fecha,
                eva_talla,
                eva_peso,
                eva_fat,
                eva_fatv,
                eva_musc,
                eva_obs,
                usuario_creador,
                nombre_completo,
                empresa);
    }

    public static Iterator selectEvaluacion(String Paciente, String empresa) {
        Log.debug(Thread.currentThread().getStackTrace()[1].getMethodName());
        return CFEvaluaciones.selectEvaluacion(Paciente, empresa);

    }

    public static int deleteEvaluacion(String eva_id, String eva_empresa) {
        Log.debug(Thread.currentThread().getStackTrace()[1].getMethodName());
        return CFEvaluaciones.deleteEvaluacion(eva_id, eva_empresa);
    }

    public static int updateEvaluacion(String eva_id, /*String eva_date,*/
            String eva_talla, String eva_peso,
            String eva_fat, String eva_fatv, String eva_musc,
            String eva_obs_evaluacion, String eva_ultmod_numuser,
            String eva_ultmod_username, String eva_empresa) {
        Log.debug(Thread.currentThread().getStackTrace()[1].getMethodName());
        return CFEvaluaciones.updateEvaluacion(eva_id, /*eva_date,*/
                eva_talla, eva_peso,
                eva_fat, eva_fatv, eva_musc,
                eva_obs_evaluacion, eva_ultmod_numuser,
                eva_ultmod_username, eva_empresa);
    }
}
