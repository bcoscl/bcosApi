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
public class RFFichas extends Registro {

    private static final Logger Log = Logger.getLogger(RFFichas.class);

    public static final int ficha_n_id = 1;
    public static final int ficha_c_numuser_paciente = 2;
    public static final int ficha_c_paciente_name = 3;
    public static final int ficha_d_dateultimamod = 4;
    public static final int ficha_c_userultimamod = 5;
    public static final int ficha_c_role_userultimamod = 6;
    public static final int ficha_c_createuser = 7;
    public static final int ficha_d_createdate = 8;
    public static final int ficha_c_createusername = 9;
    public static final int ficha_c_empresaname = 10;

    public RFFichas() {
        super(11);
    }

    public static AdmRegistros selectFichas(Connection con) {
        StringBuilder qry = new StringBuilder();

        qry.append(" SELECT ficha_n_id,  ");
        qry.append(" ficha_c_numuser_paciente,  ");
        qry.append(" ficha_c_paciente_name,  ");
        qry.append(" ficha_d_dateultimamod,  ");
        qry.append(" ficha_c_userultimamod,  ");
        qry.append(" ficha_c_role_userultimamod,  ");
        qry.append(" ficha_c_createuser,  ");
        qry.append(" ficha_d_createdate,  ");
        qry.append(" ficha_c_createusername,  ");
        qry.append(" ficha_c_empresaname ");
        qry.append(" FROM health_ficha  ");

        AdmRegistros adm = new AdmRegistros(con,
                qry.toString(),
                10,
                new RFFichas()
        );
        adm.setColumna(1, ficha_n_id);
        adm.setColumna(2, ficha_c_numuser_paciente);
        adm.setColumna(3, ficha_c_paciente_name);
        adm.setColumna(4, ficha_d_dateultimamod);
        adm.setColumna(5, ficha_c_userultimamod);
        adm.setColumna(6, ficha_c_role_userultimamod);
        adm.setColumna(7, ficha_c_createuser);
        adm.setColumna(8, ficha_d_createdate);
        adm.setColumna(9, ficha_c_createusername);
        adm.setColumna(10, ficha_c_empresaname);
        

        return adm;
    }

}
