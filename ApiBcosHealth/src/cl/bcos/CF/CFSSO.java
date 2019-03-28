/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.bcos.CF;

import cl.bcos.RF.RFSSO;
import cl.bcos.bd.Pool;
import cl.bcos.data.Registro;
import cl.bcos.data.TabRegistros;
import java.sql.Connection;
import java.util.Iterator;
import org.apache.log4j.Logger;

/**
 *
 * @author aacantero
 */
public class CFSSO {

    private static final Logger Log = Logger.getLogger(CFSSO.class);

    
    public static String usuarioValido(String numuser, String pass) {
        Connection con = null;
        String ret = "";
        try {
            con = Pool.getInstancia().getConnection("conexionOCT");
            String[] param = {numuser,pass};
            TabRegistros tab = new TabRegistros();
            tab.setContext(RFSSO.usuarioValido(con));
            tab.execute(tab.USE_RS, param);
            Iterator it = tab.getRegistros();
            
            while (it.hasNext()) {
                Registro reg = (Registro) it.next();
                ret = reg.get(RFSSO.usuariovalido);
            }
        } catch (Exception e) {
            Log.error(e.toString());
        } finally {
            Pool.getInstancia().free(con);
        }
        return ret;
    }
}
