/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.bcos.LF;

import cl.bcos.CF.CFExamenes;
import java.util.Iterator;
import org.apache.log4j.Logger;

/**
 *
 * @author aacantero
 */
public class LFExamenes {

    private static final Logger Log = Logger.getLogger(LFExamenes.class);

    public static Iterator selectExamenesbyPaciente(String Paciente, String empresa) {
        Log.debug(Thread.currentThread().getStackTrace()[1].getMethodName());
        return CFExamenes.selectExamenesbyPaciente(Paciente,empresa);

    }
    public static Iterator selectExamenesAll(String empresa) {
        Log.debug(Thread.currentThread().getStackTrace()[1].getMethodName());
        return CFExamenes.selectExamenesAll(empresa);

    }

    public static int insertExamenes(String exa_c_name, String exa_c_obs,
            String exa_c_numuser_paciente, String exa_n_ultmod_numuser,
             String exa_c_ultmod_username, String exa_c_url, String nombre, String empresa) {

        Log.debug(Thread.currentThread().getStackTrace()[1].getMethodName());
        return CFExamenes.insertExamenes(exa_c_name, exa_c_obs, exa_c_numuser_paciente,
                exa_n_ultmod_numuser, exa_c_ultmod_username,
                exa_c_url,nombre, empresa);
    }

    public static int deleteExamenes(String exa_n_id, String empresa) {
        Log.debug(Thread.currentThread().getStackTrace()[1].getMethodName());
        return CFExamenes.deleteExamenes(exa_n_id, empresa);
    }

    public static int updateExamenes(String exa_n_id, String exa_c_name,
            String exa_c_obs, String exa_n_ultmod_numuser, String exa_c_ultmod_username,
            String exa_c_url, String empresa) {
        Log.debug(Thread.currentThread().getStackTrace()[1].getMethodName());
        return CFExamenes.updateExamenes(exa_n_id, exa_c_name,
                exa_c_obs, exa_n_ultmod_numuser, exa_c_ultmod_username,
                exa_c_url, empresa);
    }

    public static String selectExamenesbyData(String exa_c_name, String exa_c_obs, String exa_c_numuser_paciente, String usuario_creador, String nombre_completo, String exa_c_url, String examen_pacientename, String empresa) {
        Log.debug(Thread.currentThread().getStackTrace()[1].getMethodName());
        return CFExamenes.selectExamenesbyData( exa_c_name,  exa_c_obs,  exa_c_numuser_paciente, 
                usuario_creador,  nombre_completo,  exa_c_url,  examen_pacientename,  empresa);

    }

    public static void updateExamenUrlFile(String id, String examenUrl, String empresa) {
         Log.debug(Thread.currentThread().getStackTrace()[1].getMethodName());
         CFExamenes.updateExamenUrlFile(id, examenUrl,
                empresa);
    }
}
