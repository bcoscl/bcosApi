/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.bcos.LF;

import cl.bcos.CF.CFFarmacos;
import java.util.Iterator;
import org.apache.log4j.Logger;

/**
 *
 * @author aacantero
 */
public class LFFarmacos {

    private static final Logger Log = Logger.getLogger(LFFarmacos.class);

    public static Iterator selectFarmacos(String Paciente, String empresa) {
        Log.debug(Thread.currentThread().getStackTrace()[1].getMethodName());
        return CFFarmacos.selectFarmacos(Paciente, empresa);

    }

    public static int insertFarmacos(String farmaco_c_name, String farmaco_c_obs,
            String farmaco_numuser_paciente, String farmaco_n_ultmod_numuser,
            String farmaco_c_ultmod_username, String empresa) {

        Log.debug(Thread.currentThread().getStackTrace()[1].getMethodName());
        return CFFarmacos.insertFarmacos(farmaco_c_name, farmaco_c_obs, farmaco_numuser_paciente,
                farmaco_n_ultmod_numuser, farmaco_c_ultmod_username, empresa);
    }

    public static int deleteFarmacos(String rowFarmacoId, String empresa) {
        Log.debug(Thread.currentThread().getStackTrace()[1].getMethodName());
        return CFFarmacos.deleteFarmacos(rowFarmacoId, empresa);
    }

    public static int updateFarmacos(String farmaco_n_id, String farmaco_c_name,
            String farmaco_c_obs, String farmaco_n_ultmod_numuser, String farmaco_c_ultmod_username, String empresa) {
        Log.debug(Thread.currentThread().getStackTrace()[1].getMethodName());
        return CFFarmacos.updateFarmacos(farmaco_n_id, farmaco_c_name,
                farmaco_c_obs, farmaco_n_ultmod_numuser, farmaco_c_ultmod_username, empresa);
    }

}
