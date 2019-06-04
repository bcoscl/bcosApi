/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.bcos.CF;

import cl.bcos.RF.RFAttentionList;
import cl.bcos.bd.Pool;
import cl.bcos.data.TabRegistros;
import java.sql.Connection;
import java.util.Iterator;
import org.apache.log4j.Logger;

/**
 *
 * @author aacantero
 */
public class CFAttentionList {

    private static final Logger Log = Logger.getLogger(CFAttentionList.class);
    private static final String conexionName = "ConexionBcos";

    public static Iterator selectAttentionList(String doctor, String empresa) {
        Log.debug(Thread.currentThread().getStackTrace()[1].getMethodName());
        Connection con = null;
        Iterator it = null;
        try {
            con = Pool.getInstancia().getConnection(conexionName);
            String[] param = {doctor, empresa};/*Colocar el filtro de medico segun la session*/
            TabRegistros tab = new TabRegistros();
            tab.setContext(RFAttentionList.selectAttentionList(con));
            tab.execute(tab.USE_RS, param);
            it = tab.getRegistros();
            return it;
        } catch (Exception e) {
            Log.error(e.toString());
        } finally {
            Pool.getInstancia().free(con);
        }
        return it;
    }



    public static int insertAttentionList(
            String at_c_numuser_paciente,
            String at_c_pacientename, String at_c_mediconame,
            String at_c_numuser_medico, String motivo, String empresa, String hora){
        Log.debug(Thread.currentThread().getStackTrace()[1].getMethodName());
        
        Connection con = null;

        try {
            con = Pool.getInstancia().getConnection(conexionName);
            return RFAttentionList.insertAttentionList(con,   at_c_numuser_paciente,
             at_c_pacientename,  at_c_mediconame,
             at_c_numuser_medico,motivo, empresa, hora);

        } catch (Exception e) {
            Log.error(e.toString());
            return 0;
        } finally {
            Pool.getInstancia().free(con);
        }
    }

    
    public static void deleteFromAttentionList(String rowAt, String empresa) {
        Log.debug(Thread.currentThread().getStackTrace()[1].getMethodName());
        Connection con = null;

        try {
            con = Pool.getInstancia().getConnection(conexionName);
            RFAttentionList.deleteFromAttentionList(con, rowAt, empresa);

        } catch (Exception e) {
            Log.error(e.toString());
            //return 0;
        } finally {
            Pool.getInstancia().free(con);
        }
    }
    
    
    public static void moverAlfinal(String rowAt, String empresa) {
        Log.debug(Thread.currentThread().getStackTrace()[1].getMethodName());
        Connection con = null;

        try {
            con = Pool.getInstancia().getConnection(conexionName);
            if(RFAttentionList.moverAlfinal(con, rowAt)==1){
                RFAttentionList.deleteFromAttentionList(con, rowAt, empresa);
            }

        } catch (Exception e) {
            Log.error(e.toString());
            //return 0;
        } finally {
            Pool.getInstancia().free(con);
        }
    }

}

