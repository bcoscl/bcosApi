/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.bcos.LF;

import cl.bcos.CF.CFAttentionList;
import java.util.Iterator;
import org.apache.log4j.Logger;

/**
 *
 * @author aacantero
 */
public class LFAttentionList {

    private static final Logger Log = Logger.getLogger(LFAttentionList.class);

    public static Iterator selectAttentionList(String doctor, String empresa) {
        Log.debug(Thread.currentThread().getStackTrace()[1].getMethodName());
        return CFAttentionList.selectAttentionList(doctor, empresa);
    }

    public static int insertAttentionList(
            String at_c_numuser_paciente,
            String at_c_pacientename, String at_c_mediconame,
            String at_c_numuser_medico, String motivo, String empresa, String hora) {

        Log.debug(Thread.currentThread().getStackTrace()[1].getMethodName());
        return CFAttentionList.insertAttentionList(
                at_c_numuser_paciente,
                at_c_pacientename, at_c_mediconame,
                at_c_numuser_medico, motivo, empresa,hora);
    }

    public static void deleteFromAttentionList(String rowAt, String empresa) {
        Log.debug(Thread.currentThread().getStackTrace()[1].getMethodName());
        CFAttentionList.deleteFromAttentionList(rowAt, empresa);
    }

    public static void moverAlfinal(String rowAt, String empresa) {
        Log.debug(Thread.currentThread().getStackTrace()[1].getMethodName());
        CFAttentionList.moverAlfinal(rowAt, empresa);
    }

}
