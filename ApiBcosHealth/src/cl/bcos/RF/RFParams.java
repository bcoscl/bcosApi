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
    public static final int params_d_ultmod = 8;
    public static final int params_c_numuser_utlmod = 9;
    public static final int params_c_nombre_ultmod = 10;
    public static final int mail_smtp_host = 11;
    public static final int mail_smtp_user = 12;
    public static final int mail_smtp_clave = 13;
    public static final int mail_smtp_auth = 14;
    public static final int mail_smtp_port = 15;
    public static final int mail_smtp_transport = 16;
    public static final int mail_smtp_url = 17;
    public static final int params_now = 18;
    public static final int ACCESS_KEY_ID = 19;
    public static final int ACCESS_SEC_KEY = 20;
    public static final int FOLDER_NAME_EXAMENES = 21;
    public static final int FOLDER_NAME_PROFILE = 22;
    public static final int POLICY_RULES = 23;
    public static final int BUCKETNAME = 24;

    public RFParams() {
        super(25);
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
        qry.append(" params_n_param4,params_d_ultmod,params_c_numuser_utlmod,params_c_nombre_ultmod ");
        qry.append(" FROM health_params ");
        qry.append(" where params_n_grupo= ? ");/*'TOKEN'*/
        qry.append(" and params_n_subgrupo= ? ");/*'BLOWFISH'*/
        qry.append(" and params_n_param1= ? ");/*'CRYPT' */
        Log.debug(qry.toString());
        try {
            AdmRegistros adm = new AdmRegistros(con,
                    qry.toString(),
                    10,
                    new RFParams());

            adm.setColumna(1, params_n_id);
            adm.setColumna(2, params_n_grupo);
            adm.setColumna(3, params_n_subgrupo);
            adm.setColumna(4, params_n_param1);
            adm.setColumna(5, params_n_param2);
            adm.setColumna(6, params_n_param3);
            adm.setColumna(7, params_n_param4);
            adm.setColumna(8, params_d_ultmod);
            adm.setColumna(9, params_c_numuser_utlmod);
            adm.setColumna(10, params_c_nombre_ultmod);

            return adm;
        } catch (Exception e) {
            Log.error(e.getMessage());
            throw e;
        }

    }

    /*Validacion de si existe para el ingreso de la persona, Autenticacion*/
    public static AdmRegistros getAllParams(Connection con) {
        Log.debug(Thread.currentThread().getStackTrace()[1].getMethodName());
        StringBuilder qry = new StringBuilder();

        qry.append(" SELECT params_n_id, ");
        qry.append(" params_n_grupo,  ");
        qry.append(" params_n_subgrupo,  ");
        qry.append(" params_n_param1,  ");
        qry.append(" params_n_param2,  ");
        qry.append(" params_n_param3,  ");
        qry.append(" params_n_param4,params_d_ultmod,params_c_numuser_utlmod,params_c_nombre_ultmod ");
        qry.append(" FROM health_params ");

        Log.debug(qry.toString());
        try {
            AdmRegistros adm = new AdmRegistros(con,
                    qry.toString(),
                    10,
                    new RFParams());

            adm.setColumna(1, params_n_id);
            adm.setColumna(2, params_n_grupo);
            adm.setColumna(3, params_n_subgrupo);
            adm.setColumna(4, params_n_param1);
            adm.setColumna(5, params_n_param2);
            adm.setColumna(6, params_n_param3);
            adm.setColumna(7, params_n_param4);
            adm.setColumna(8, params_d_ultmod);
            adm.setColumna(9, params_c_numuser_utlmod);
            adm.setColumna(10, params_c_nombre_ultmod);

            return adm;
        } catch (Exception e) {
            Log.error(e.getMessage());
            throw e;
        }

    }

    public static AdmRegistros getEmailConfig(Connection con) {
        Log.debug(Thread.currentThread().getStackTrace()[1].getMethodName());
        StringBuilder qry = new StringBuilder();

        qry.append(" select (SELECT params_n_param1 ");
        qry.append("FROM health_params ");
        qry.append("where params_n_grupo='EMAIL' ");
        qry.append("and params_n_subgrupo='MAIL.SMTP.HOST') mail_smtp_host, ");
        qry.append("(SELECT params_n_param1 ");
        qry.append("FROM health_params ");
        qry.append("where params_n_grupo='EMAIL' ");
        qry.append("and params_n_subgrupo='MAIL.SMTP.USER') mail_smtp_user, ");
        qry.append("(SELECT params_n_param1 ");
        qry.append("FROM health_params ");
        qry.append("where params_n_grupo='EMAIL' ");
        qry.append("and params_n_subgrupo='MAIL.SMTP.CLAVE') mail_smtp_clave, ");
        qry.append("(SELECT params_n_param1 ");
        qry.append("FROM health_params ");
        qry.append("where params_n_grupo='EMAIL' ");
        qry.append("and params_n_subgrupo='MAIL.SMTP.AUTH') mail_smtp_auth, ");
        qry.append("(SELECT params_n_param1 ");
        qry.append("FROM health_params ");
        qry.append("where params_n_grupo='EMAIL' ");
        qry.append("and params_n_subgrupo='MAIL.SMTP.PORT') mail_smtp_port, ");
        qry.append("(SELECT params_n_param1 ");
        qry.append("FROM health_params ");
        qry.append("where params_n_grupo='EMAIL' ");
        qry.append("and params_n_subgrupo='TRANSPORT') mail_smtp_transport, ");
        qry.append("(SELECT params_n_param1 ");
        qry.append("FROM health_params ");
        qry.append("where params_n_grupo='EMAIL' ");
        qry.append("and params_n_subgrupo='URL.RESET') mail_smtp_url");

        Log.debug(qry.toString());
        try {
            AdmRegistros adm = new AdmRegistros(con,
                    qry.toString(),
                    7,
                    new RFParams());

            adm.setColumna(1, mail_smtp_host);
            adm.setColumna(2, mail_smtp_user);
            adm.setColumna(3, mail_smtp_clave);
            adm.setColumna(4, mail_smtp_auth);
            adm.setColumna(5, mail_smtp_port);
            adm.setColumna(6, mail_smtp_transport);
            adm.setColumna(7, mail_smtp_url);

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

    public static AdmRegistros getsysdate(Connection con) {
        Log.debug(Thread.currentThread().getStackTrace()[1].getMethodName());
        StringBuilder qry = new StringBuilder();

        qry.append("select NOW()  at time zone (select params_n_param1 from health_params where params_n_grupo='UTC'and params_n_subgrupo='TIMEZONE' ) || ' - ' ||(select params_n_param1 from health_params where params_n_grupo='UTC'and params_n_subgrupo='TIMEZONE' )");
        Log.debug(qry.toString());
        try {
            AdmRegistros adm = new AdmRegistros(con,
                    qry.toString(),
                    1,
                    new RFParams());

            adm.setColumna(1, params_now);

            return adm;
        } catch (Exception e) {
            Log.error(e.getMessage());
            throw e;
        }

    }

    public static int insertParam(Connection con,
            String params_n_grupo,
            String params_n_subgrupo,
            String params_n_param1,
            String params_n_param2,
            String params_n_param3,
            String params_n_param4,
            String params_c_numuser_utlmod,
            String params_c_nombre_ultmod) {
        Log.debug(Thread.currentThread().getStackTrace()[1].getMethodName());
        StringBuilder qry = new StringBuilder();

        qry.append(" insert into health_params  ");
        qry.append(" (params_n_id,params_n_grupo,params_n_subgrupo,params_n_param1,params_n_param2,params_n_param3,params_n_param4,params_d_ultmod,params_c_numuser_utlmod,params_c_nombre_ultmod)  ");
        qry.append(" values (nextval('health_seq_params'), upper('");
        qry.append(params_n_grupo);
        qry.append("'),upper('");
        qry.append(params_n_subgrupo);
        qry.append("'),'");
        qry.append(params_n_param1);
        qry.append("', '");
        qry.append(params_n_param2);
        qry.append("', '");
        qry.append(params_n_param3);
        qry.append("', '");
        qry.append(params_n_param4);
        qry.append("',NOW() at time zone (select params_n_param1 from health_params where params_n_grupo='UTC'and params_n_subgrupo='TIMEZONE' ),'");
        qry.append(params_c_numuser_utlmod);
        qry.append("','");
        qry.append(params_c_nombre_ultmod);
        qry.append("')");

        Log.debug(qry.toString());
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement(qry.toString());
            return ps.executeUpdate();

        } catch (Exception e) {
            Log.error(e.getMessage());
            return 0;
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException x) {
                    Log.error(x.toString());
                }
            }
        }
    }

    public static int deleteParams(Connection con, String rowParamId) {

        Log.debug(Thread.currentThread().getStackTrace()[1].getMethodName());
        StringBuilder qry = new StringBuilder();

        qry.append(" DELETE FROM health_params ");
        qry.append(" WHERE params_n_id=");
        qry.append(rowParamId);

        Log.debug(qry.toString());
        PreparedStatement ps = null;

        try {
            ps = con.prepareStatement(qry.toString());
            return ps.executeUpdate();

        } catch (Exception e) {
            Log.error(e.getMessage());
            return 0;
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException x) {
                    Log.error(x.toString());
                }
            }
        }
    }

    public static AdmRegistros getS3Params(Connection con) {
        Log.debug(Thread.currentThread().getStackTrace()[1].getMethodName());
        StringBuilder qry = new StringBuilder();

        qry.append(" select (SELECT params_n_param2 ");
        qry.append(" FROM public.health_params  ");
        qry.append(" where params_n_grupo='AWS'  ");
        qry.append(" and params_n_subgrupo='S3' ");
        qry.append(" and params_n_param1='ACCESS_KEY_ID') ACCESS_KEY_ID, ");
        qry.append(" (SELECT params_n_param2	 ");
        qry.append(" FROM public.health_params  ");
        qry.append(" where params_n_grupo='AWS'  ");
        qry.append(" and params_n_subgrupo='S3' ");
        qry.append(" and params_n_param1='ACCESS_SEC_KEY') ACCESS_SEC_KEY, ");
        qry.append(" (SELECT lower(params_n_param2)	 ");
        qry.append(" FROM public.health_params  ");
        qry.append(" where params_n_grupo='AWS'  ");
        qry.append(" and params_n_subgrupo='S3' ");
        qry.append(" and params_n_param1='FOLDER_NAME-PROFILE') FOLDER_NAME_PROFILE, ");
        qry.append(" (SELECT lower(params_n_param2)	 ");
        qry.append(" FROM public.health_params  ");
        qry.append(" where params_n_grupo='AWS'  ");
        qry.append(" and params_n_subgrupo='S3' ");
        qry.append(" and params_n_param1='FOLDER_NAME-EXAMENES') FOLDER_NAME_EXAMENES, ");
        qry.append(" (SELECT params_n_param2	 ");
        qry.append(" FROM public.health_params  ");
        qry.append(" where params_n_grupo='AWS'  ");
        qry.append(" and params_n_subgrupo='S3' ");
        qry.append(" and params_n_param1='POLICY-RULES') POLICY_RULES, ");
        qry.append(" (SELECT subscr_c_bucketname ");
        qry.append(" 	FROM public.health_subscription ");
        qry.append(" 	where subscr_c_empresaname=upper(?)) ");

        Log.debug(qry.toString());
        try {
            AdmRegistros adm = new AdmRegistros(con,
                    qry.toString(),
                    6,
                    new RFParams());

            adm.setColumna(1, ACCESS_KEY_ID);
            adm.setColumna(2, ACCESS_SEC_KEY);
            adm.setColumna(3, FOLDER_NAME_PROFILE);
            adm.setColumna(4, FOLDER_NAME_EXAMENES);
            adm.setColumna(5, POLICY_RULES);
            adm.setColumna(6, BUCKETNAME);

            return adm;
        } catch (Exception e) {
            Log.error(e.getMessage());
            throw e;
        }

    }

}
