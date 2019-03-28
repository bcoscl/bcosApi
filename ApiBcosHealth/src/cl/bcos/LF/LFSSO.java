/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.bcos.LF;

import cl.bcos.CF.CFSSO;
import org.apache.log4j.Logger;

/**
 *
 * @author aacantero
 */
public class LFSSO {

    private static final Logger Log = Logger.getLogger(LFSSO.class);

    public static String autenticacion(String user, String pass) {
        String Response = CFSSO.usuarioValido(user, pass);
         
        return Response;
    }

}
