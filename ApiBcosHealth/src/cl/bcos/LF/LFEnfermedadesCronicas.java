/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.bcos.LF;

import cl.bcos.CF.CFEnfermedadesCronicas;
import java.util.Iterator;
import org.apache.log4j.Logger;

/**
 *
 * @author aacantero
 */
public class LFEnfermedadesCronicas {

    private static final Logger Log = Logger.getLogger(LFEnfermedadesCronicas.class);

    public static Iterator selectEnfermedadesCronicas(String Paciente, String empresa) {
        Log.debug(Thread.currentThread().getStackTrace()[1].getMethodName());
        return CFEnfermedadesCronicas.selectEnfermedadesCronicas(Paciente, empresa);

    }

    public static int insertEnfermedadesCronicas(String cronica_c_name, String cronica_c_obs,
            String cronica_c_numuser_paciente, String cronica_n_ultmod_numuser,
            String cronica_c_ultmod_username, String empresa) {

        Log.debug(Thread.currentThread().getStackTrace()[1].getMethodName());
        return CFEnfermedadesCronicas.insertEnfermedadesCronicas(cronica_c_name, cronica_c_obs, cronica_c_numuser_paciente,
                cronica_n_ultmod_numuser, cronica_c_ultmod_username, empresa);
    }

    public static int deleteEnfermedadCronica(String rowCronicaId, String empresa) {
        Log.debug(Thread.currentThread().getStackTrace()[1].getMethodName());
        return CFEnfermedadesCronicas.deleteEnfermedadCronica(rowCronicaId, empresa);
    }

    public static int updateEnfermedadesCronicas(String cronica_n_id, String cronica_c_name,
            String cronica_c_obs, String cronica_n_ultmod_numuser, String cronica_c_ultmod_username, String empresa) {
        Log.debug(Thread.currentThread().getStackTrace()[1].getMethodName());
        return CFEnfermedadesCronicas.updateEnfermedadesCronicas(cronica_n_id, cronica_c_name,
                cronica_c_obs, cronica_n_ultmod_numuser, cronica_c_ultmod_username, empresa);
    }
}
