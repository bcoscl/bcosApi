/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.bcos.LF;

import cl.bcos.CF.CFConsultas;
import java.util.Iterator;
import org.apache.log4j.Logger;

/**
 *
 * @author aacantero
 */
public class LFConsultas {

    private static final Logger Log = Logger.getLogger(LFConsultas.class);

    public static Iterator selectConsultas(String Paciente, String empresa) {
        Log.debug(Thread.currentThread().getStackTrace()[1].getMethodName());
        return CFConsultas.selectConsultas(Paciente, empresa);

    }

    public static int insertConsultas(String consult_c_titulo, String consult_c_obs_consulta,
            String consult_c_tipoconsulta, String consult_c_numuser_paciente,
            String consult_c_paciente_name, String consult_c_ultmod_numuser, String consult_c_ultmod_username,
            String consult_c_createdate, String empresa) {

        Log.debug(Thread.currentThread().getStackTrace()[1].getMethodName());
        return CFConsultas.insertConsultas(consult_c_titulo, consult_c_obs_consulta, consult_c_tipoconsulta,
                consult_c_numuser_paciente, consult_c_paciente_name, consult_c_ultmod_numuser,
                consult_c_ultmod_username,consult_c_createdate, empresa);
    }

    public static int deleteConsultas(String consult_n_id, String empresa) {
        Log.debug(Thread.currentThread().getStackTrace()[1].getMethodName());
        return CFConsultas.deleteConsultas(consult_n_id, empresa);
    }

    public static int updateConsultas(String consult_n_id, String consult_c_titulo,
            String consult_c_obs_consulta,  String consult_c_ultmod_numuser, String consult_c_ultmod_username, String empresa,
            String consult_c_createdate) {
        Log.debug(Thread.currentThread().getStackTrace()[1].getMethodName());
        return CFConsultas.updateConsultas(consult_n_id, consult_c_titulo,
                consult_c_obs_consulta,  
                 consult_c_ultmod_numuser, consult_c_ultmod_username, empresa,consult_c_createdate);
    }
}
