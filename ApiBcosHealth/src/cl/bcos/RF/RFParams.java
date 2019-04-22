/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.bcos.RF;

import cl.bcos.data.AdmRegistros;
import cl.bcos.data.Registro;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.apache.log4j.Logger;

/**
 *
 * @author aacantero
 */
public class RFParams extends Registro {

    private static final Logger Log = Logger.getLogger(RFParams.class);

    public static final int params_n_id = 1;
    public static final int params_n_grupo = 2;
    public static final int params_n_subgrupo = 3;
    public static final int params_n_param1 = 4;
    public static final int params_n_param2 = 5;
    public static final int params_n_param3 = 6;
    public static final int params_n_param4 = 7;

    public RFParams() {
        super(8);
    }

    /*Validacion de si existe para el ingreso de la persona, Autenticacion*/
    public static AdmRegistros getParams(Connection con) {
        Log.debug(Thread.currentThread().getStackTrace()[1].getMethodName());
        StringBuilder qry = new StringBuilder();

        qry.append(" SELECT params_n_id, ");
        qry.append(" params_n_grupo,  ");
        qry.append(" params_n_subgrupo,  ");
        qry.append(" params_n_param1,  ");
        qry.append(" params_n_param2,  ");
        qry.append(" params_n_param3,  ");
        qry.append(" params_n_param4 ");
        qry.append(" FROM public.health_params ");
        qry.append(" where params_n_grupo= ? ");/*'TOKEN'*/
        qry.append(" and params_n_subgrupo= ? ");/*'BLOWFISH'*/
        qry.append(" and params_n_param1= ? ");/*'CRYPT' */
        Log.debug(qry.toString());
        try {
            AdmRegistros adm = new AdmRegistros(con,
                    qry.toString(),
                    7,
                    new RFParams());

            adm.setColumna(1, params_n_id);
            adm.setColumna(2, params_n_grupo);
            adm.setColumna(3, params_n_subgrupo);
            adm.setColumna(4, params_n_param1);
            adm.setColumna(5, params_n_param2);
            adm.setColumna(6, params_n_param3);
            adm.setColumna(7, params_n_param4);

            return adm;
        } catch (Exception e) {
            Log.error(e.getMessage());
            throw e;
        }

    }

    /*Obtiene el ID de Insercion de la secuencia*/
    public static AdmRegistros getNewParamId(Connection con) {
        Log.debug(Thread.currentThread().getStackTrace()[1].getMethodName());
        StringBuilder qry = new StringBuilder();

        qry.append("select nextval('health_seq_params')");
        Log.debug(qry.toString());
        try {
            AdmRegistros adm = new AdmRegistros(con,
                    qry.toString(),
                    1,
                    new RFParams());

            adm.setColumna(1, params_n_id);

            return adm;
        } catch (Exception e) {
            Log.error(e.getMessage());
            throw e;
        }

    }

}
