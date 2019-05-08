/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.bcos.CF;

import cl.bcos.RF.RFPaciente;
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
public class CFPaciente {

    private static final Logger Log = Logger.getLogger(CFPaciente.class);
    private static final String conexionName = "conexionOCT";

    public static int insertPaciente(String numuser_paciente, String nombre_paciente, String apellido_paciente, String email_contacto_paciente, String numero_telefono_paciente, String profesion_paciente, String estado_civil_paciente, String fecha_nacimiento_paciente, String edad_paciente, String prevision, String aboutme_obs_paciente, String usuario_creador, String nombre_completo, String empresaName, String rowPacienteId, String direccion_paciente, String sexo) {
        Log.debug(Thread.currentThread().getStackTrace()[1].getMethodName());
        Connection con = null;

        try {
            con = Pool.getInstancia().getConnection(conexionName);
            return RFPaciente.insertPaciente(con, numuser_paciente, nombre_paciente, apellido_paciente, email_contacto_paciente, numero_telefono_paciente, profesion_paciente, estado_civil_paciente, fecha_nacimiento_paciente, edad_paciente, prevision, aboutme_obs_paciente, usuario_creador, nombre_completo, empresaName, rowPacienteId, direccion_paciente, sexo);

        } catch (Exception e) {
            Log.error(e.toString());
            return 0;
        } finally {
            Pool.getInstancia().free(con);
        }
    }

    public static int updatePacienteInformation(String paciente_c_direccion,
            String paciente_c_prevision,
            String paciente_c_profesion,
            String paciente_c_estado_civil,
            String paciente_c_obs,
            String paciente_c_email,
            String paciente_c_celular,
            String paciente_c_sexo,
            String paciente_d_fechanacimiento,
            String paciente_n_edad,
            String paciente_c_ultmod_username,
            String paciente_c_numuser,
            String empresa) {
        Log.debug(Thread.currentThread().getStackTrace()[1].getMethodName());
        Connection con = null;

        try {
            con = Pool.getInstancia().getConnection(conexionName);
            return RFPaciente.updatePacienteInformation(con, paciente_c_direccion,
                    paciente_c_prevision,
                    paciente_c_profesion,
                    paciente_c_estado_civil,
                    paciente_c_obs,
                    paciente_c_email,
                    paciente_c_celular,
                    paciente_c_sexo,
                    paciente_d_fechanacimiento,
                    paciente_n_edad,
                    paciente_c_ultmod_username,
                    paciente_c_numuser,
                    empresa);

        } catch (Exception e) {
            Log.error(e.toString());
            return 0;
        } finally {
            Pool.getInstancia().free(con);
        }
    }

    public static void rollBack(String rowPacienteId, String empresa) {
        Log.debug(Thread.currentThread().getStackTrace()[1].getMethodName());
        Connection con = null;

        try {
            con = Pool.getInstancia().getConnection(conexionName);
            RFPaciente.rollBack(con, rowPacienteId, empresa);

        } catch (Exception e) {
            Log.error(e.toString());
            //return 0;
        } finally {
            Pool.getInstancia().free(con);
        }
    }

    public static Iterator existeRegistro(String numuser,String  empresa) {
        Log.debug(Thread.currentThread().getStackTrace()[1].getMethodName());
        Connection con = null;
        Iterator it = null;
        try {
            con = Pool.getInstancia().getConnection(conexionName);
            String[] param = {};
            TabRegistros tab = new TabRegistros();
            tab.setContext(RFPaciente.existeRegistro(con, numuser, empresa));
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

    public static String getNewPacienteId() {
        Log.debug(Thread.currentThread().getStackTrace()[1].getMethodName());
        Connection con = null;
        String ret = "";
        try {
            con = Pool.getInstancia().getConnection(conexionName);
            String[] param = {};
            TabRegistros tab = new TabRegistros();
            tab.setContext(RFPaciente.getNewPacienteId(con));
            tab.execute(tab.USE_RS, param);
            Iterator it = tab.getRegistros();

            while (it.hasNext()) {
                Registro reg = (Registro) it.next();
                ret = reg.get(RFPaciente.paciente_n_id);
            }
        } catch (Exception e) {
            Log.error(e.toString());
        } finally {
            Pool.getInstancia().free(con);
        }
        return ret;
    }

    public static Iterator getPacienteInformation(String numuser, String empresa) {
        Log.debug(Thread.currentThread().getStackTrace()[1].getMethodName());
        Connection con = null;
        Iterator it = null;
        try {
            con = Pool.getInstancia().getConnection(conexionName);
            String[] param = {numuser, empresa};
            TabRegistros tab = new TabRegistros();
            tab.setContext(RFPaciente.getPacienteInformation(con));
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

    public static Iterator selectPacientes(String empresa) {
        Log.debug(Thread.currentThread().getStackTrace()[1].getMethodName());
        Connection con = null;
        Iterator it = null;
        try {
            con = Pool.getInstancia().getConnection(conexionName);
            String[] param = {empresa};
            TabRegistros tab = new TabRegistros();
            tab.setContext(RFPaciente.selectPacientes(con));
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
    public static Iterator selectPacientesBynumuser(String empresa, String numusers) {
        Log.debug(Thread.currentThread().getStackTrace()[1].getMethodName());
        Connection con = null;
        Iterator it = null;
        try {
            con = Pool.getInstancia().getConnection(conexionName);
            String[] param = {empresa};
            TabRegistros tab = new TabRegistros();
            tab.setContext(RFPaciente.selectPacientesBynumuser(con,numusers));
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

}
