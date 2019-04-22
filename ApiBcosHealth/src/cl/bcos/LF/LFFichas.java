/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.bcos.LF;

import cl.bcos.CF.CFFichas;
import cl.bcos.RF.RFFichas;
import cl.bcos.data.Registro;
import java.util.Iterator;
import org.apache.log4j.Logger;

/**
 *
 * @author aacantero
 */
public class LFFichas {

    private static final Logger Log = Logger.getLogger(LFFichas.class);

    public static Iterator selectSFichas(String empresa) {
        Log.debug(Thread.currentThread().getStackTrace()[1].getMethodName());
        return CFFichas.selectFichas(empresa);
    }

    public static String getNewFichaId() {
        Log.debug(Thread.currentThread().getStackTrace()[1].getMethodName());
        String Response = CFFichas.getNewFichaId();

        return Response;
    }

    public static int insertFicha(String rowFichaId, String numuser_paciente,
            String nombre_paciente, String apellido_paciente,
            String nombre_completo, String Roles,
            String usuario_creador, String createUsername,
            String empresaName) {

        Log.debug(Thread.currentThread().getStackTrace()[1].getMethodName());
        return CFFichas.insertFicha(rowFichaId, numuser_paciente, nombre_paciente,
                apellido_paciente, nombre_completo, Roles,
                usuario_creador, createUsername, empresaName);
    }

    public static void rollBack(String rowFichaId, String empresa) {
        Log.debug(Thread.currentThread().getStackTrace()[1].getMethodName());
        CFFichas.rollBack(rowFichaId, empresa);
    }

    public static String existeRegistro(String numuser, String empresa) {
        Log.debug(Thread.currentThread().getStackTrace()[1].getMethodName());
        Iterator it = CFFichas.existeRegistro(numuser, empresa);
        String existeRegistro = "NO";

        while (it.hasNext()) {
            Registro reg = (Registro) it.next();
            existeRegistro = reg.get(RFFichas.existeRegistro);
        }
        Log.debug("existeRegistro :"+existeRegistro);
        return existeRegistro;
    }

}
