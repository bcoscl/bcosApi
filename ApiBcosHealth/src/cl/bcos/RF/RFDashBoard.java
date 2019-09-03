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
public class RFDashBoard extends Registro {

    private static final Logger Log = Logger.getLogger(RFDashBoard.class);

    public static final int dash_empresa = 1;
    public static final int dash_count = 2;
    
    

    public RFDashBoard() {
        super(3);
    }

    /*Obtiene la cantidad de pacientes por empresa*/
    public static AdmRegistros getPacientesPorEmpresa(Connection con) {
        Log.debug(Thread.currentThread().getStackTrace()[1].getMethodName());
        StringBuilder qry = new StringBuilder();

        qry.append(" select paciente_c_empresa,count (1)  ");
        qry.append(" from health_pacientes_user  ");
        qry.append(" where paciente_c_empresa = coalesce(?,paciente_c_empresa)  ");
        qry.append(" group by paciente_c_empresa ");
        
        Log.debug(qry.toString());
        try {
            AdmRegistros adm = new AdmRegistros(con,
                    qry.toString(),
                    2,
                    new RFDashBoard());

            adm.setColumna(1, dash_empresa);
            adm.setColumna(2, dash_count);
            

            return adm;
        } catch (Exception e) {
            Log.error(e.getMessage());
            throw e;
        }

    }
    /*Obtiene promedio de pacientes por mes*/
    public static AdmRegistros getPromedioPacientesPorMes(Connection con) {
        Log.debug(Thread.currentThread().getStackTrace()[1].getMethodName());
        StringBuilder qry = new StringBuilder();

        /*Obtiene la canidad de personas, 
        divido en la cantidad de meses para tener un promedio*/
        
        qry.append(" select   ");
	qry.append(" (select count (1)/*Obtiene la cantidad de pacientes*/   ");
	qry.append(" from health_pacientes_user)/  ");
	qry.append(" (select count(1)   ");
	qry.append(" from (select distinct to_char(paciente_d_createdate,'YYYYMM') /*Obtiene la cantidad de meses*/   ");
	qry.append(" from health_pacientes_user) as meses) promedio  ");       
        
        
        Log.debug(qry.toString());
        try {
            AdmRegistros adm = new AdmRegistros(con,
                    qry.toString(),
                    1,
                    new RFDashBoard());

            adm.setColumna(1, dash_count); 
            
            return adm;
        } catch (Exception e) {
            Log.error(e.getMessage());
            throw e;
        }

    }


}
