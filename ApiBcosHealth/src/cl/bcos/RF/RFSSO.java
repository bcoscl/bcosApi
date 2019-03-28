/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.bcos.RF;

import cl.bcos.data.AdmRegistros;
import cl.bcos.data.Registro;
import java.sql.Connection;
import org.apache.log4j.Logger;

/**
 *
 * @author aacantero
 */
public class RFSSO extends Registro {

    private static final Logger Log = Logger.getLogger(RFSSO.class);
    
    private static final int numCampos =2;
            
    public static final int usuariovalido = 1;
    public static final int pass = 2;

    public RFSSO() {
        super(numCampos);
    }


    public static AdmRegistros usuarioValido(Connection con) {

        StringBuilder qry = new StringBuilder();

        qry.append("SELECT 'OK' FROM public.health_pass p ");
        qry.append("where p.pass_c_numuser=?");
        qry.append("and p.pass_c_password=?");

        try {
            AdmRegistros adm = new AdmRegistros(con,
                    qry.toString(),
                    1,
                    new RFSSO());

            adm.setColumna(1, usuariovalido);
            
            return adm;
        } catch (Exception e) {
            Log.error(e.getMessage());
            throw e;
        }

    }

}
