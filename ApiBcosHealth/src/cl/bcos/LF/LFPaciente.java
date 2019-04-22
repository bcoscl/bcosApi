/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.bcos.LF;

import cl.bcos.CF.CFPaciente;
import cl.bcos.RF.RFPaciente;
import cl.bcos.data.Registro;
import java.util.Iterator;
import org.apache.log4j.Logger;

/**
 *
 * @author aacantero
 */
public class LFPaciente {

    private static final Logger Log = Logger.getLogger(LFPaciente.class);

    public static String getNewPacienteId() {
        Log.debug(Thread.currentThread().getStackTrace()[1].getMethodName());
        String Response = CFPaciente.getNewPacienteId();

        return Response;
    }

    public static String existeRegistro(String numuser, String empresa) {
        Log.debug(Thread.currentThread().getStackTrace()[1].getMethodName());
        Iterator it = CFPaciente.existeRegistro(numuser, empresa);
        String existeRegistro = "NO";

        while (it.hasNext()) {
            Registro reg = (Registro) it.next();
            existeRegistro = reg.get(RFPaciente.existeRegistro);
        }
        Log.debug("existeRegistro :" + existeRegistro);
        return existeRegistro;
    }

    public static int insertPaciente(String numuser_paciente, String nombre_paciente, String apellido_paciente, String email_contacto_paciente, String numero_telefono_paciente, String profesion_paciente, String estado_civil_paciente, String fecha_nacimiento_paciente, String edad_paciente, String prevision, String aboutme_obs_paciente, String usuario_creador, String nombre_completo, String empresaName, String rowPacienteId, String direccion_paciente, String sexo) {
        Log.debug(Thread.currentThread().getStackTrace()[1].getMethodName());
        return CFPaciente.insertPaciente(numuser_paciente, nombre_paciente, apellido_paciente, email_contacto_paciente, numero_telefono_paciente, profesion_paciente, estado_civil_paciente, fecha_nacimiento_paciente, edad_paciente, prevision, aboutme_obs_paciente, usuario_creador, nombre_completo, empresaName, rowPacienteId, direccion_paciente, sexo);
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
        return CFPaciente.updatePacienteInformation(paciente_c_direccion,
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
    }

    public static void rollBack(String rowPacienteId, String empresa) {
        Log.debug(Thread.currentThread().getStackTrace()[1].getMethodName());
        CFPaciente.rollBack(rowPacienteId, empresa);
    }

    public static Iterator selectPacientes(String empresa) {
        Log.debug(Thread.currentThread().getStackTrace()[1].getMethodName());
        Iterator it = CFPaciente.selectPacientes(empresa);
        return it;
    }

    public static Iterator getUserInformation(String numuser, String empresa) {
        Log.debug(Thread.currentThread().getStackTrace()[1].getMethodName());
        Iterator it = CFPaciente.getPacienteInformation(numuser, empresa);
        return it;
    }

}
