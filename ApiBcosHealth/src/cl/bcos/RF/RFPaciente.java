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
public class RFPaciente extends Registro {

    private static final Logger Log = Logger.getLogger(RFPaciente.class);

    public static final int existeRegistro = 1;
    public static final int paciente_c_apellidos = 2;
    public static final int paciente_n_id = 3;
    public static final int paciente_c_numuser = 4;
    public static final int paciente_c_direccion = 5;
    public static final int paciente_c_prevision = 6;
    public static final int paciente_c_profesion = 7;
    public static final int paciente_c_estado_civil = 8;
    public static final int paciente_c_obs = 9;
    public static final int paciente_c_pacientename = 10;

    public static final int paciente_c_email = 11;
    public static final int paciente_c_celular = 12;
    public static final int paciente_c_createusername = 13;
    public static final int paciente_d_createdate = 14;
    public static final int paciente_c_createuser = 15;
    public static final int paciente_c_img = 16;
    public static final int paciente_c_sexo = 17;
    public static final int paciente_d_fechaNacimiento = 18;
    public static final int paciente_n_edad = 19;
    public static final int paciente_c_empresa = 20;

    public RFPaciente() {
        super(21);
    }

    public static int insertPaciente(Connection con,
            String numuser_paciente,
            String nombre_paciente,
            String apellido_paciente,
            String email_contacto_paciente,
            String numero_telefono_paciente,
            String profesion_paciente,
            String estado_civil_paciente,
            String fecha_nacimiento_paciente,
            String edad_paciente,
            String prevision,
            String aboutme_obs_paciente,
            String usuario_creador,
            String nombre_completo,
            String empresaName,
            String rowPacienteId,
            String direccion_paciente,
            String sexo) {
        Log.debug(Thread.currentThread().getStackTrace()[1].getMethodName());
        StringBuilder qry = new StringBuilder();

        qry.append(" INSERT INTO health_pacientes_user( ");
        qry.append(" paciente_n_id, ");
        qry.append(" paciente_c_numuser, ");
        qry.append(" paciente_c_direccion, ");
        qry.append(" paciente_c_prevision, ");
        qry.append(" paciente_c_profesion, ");
        qry.append(" paciente_c_estado_civil, ");
        qry.append(" paciente_c_obs, ");
        qry.append(" paciente_c_pacientename, ");
        qry.append(" paciente_c_apellidos, ");
        qry.append(" paciente_c_email, ");
        qry.append(" paciente_c_celular, ");
        qry.append(" paciente_c_createusername, ");
        qry.append(" paciente_d_createdate, ");
        qry.append(" paciente_c_createuser, ");
        qry.append(" paciente_c_img,paciente_c_sexo,paciente_d_fechanacimiento,paciente_n_edad,paciente_c_empresa)");
        qry.append(" VALUES (");
        qry.append(rowPacienteId);
        qry.append(",'");
        qry.append(numuser_paciente);//paciente_c_numuser
        qry.append("','");
        qry.append(direccion_paciente);//paciente_c_direccion
        qry.append("','");
        qry.append(prevision);//paciente_c_prevision
        qry.append("','");
        qry.append(profesion_paciente);//paciente_c_profesion
        qry.append("','");
        qry.append(estado_civil_paciente);//paciente_c_estado_civil
        qry.append("','");
        qry.append(aboutme_obs_paciente);//paciente_c_obs
        qry.append("','");
        qry.append(nombre_paciente);//paciente_c_pacientename
        qry.append("','");
        qry.append(apellido_paciente);//paciente_c_apellidos
        qry.append("','");
        qry.append(email_contacto_paciente);//paciente_c_email
        qry.append("','");
        qry.append(numero_telefono_paciente);//paciente_c_celular
        qry.append("','");
        qry.append(nombre_completo);//paciente_c_createusername
        qry.append("',");
        qry.append("NOW()");//paciente_d_createdate
        qry.append(",'");
        qry.append(usuario_creador);//paciente_c_createuser
        qry.append("',");
        qry.append("NULL");//paciente_c_img
        qry.append(",'");
        qry.append(sexo);
        qry.append("','");
        qry.append(fecha_nacimiento_paciente);
        qry.append("',");
        qry.append(edad_paciente);        
        qry.append(",'");
        qry.append(empresaName);
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

    /*Valida que el registro no este duplicado*/
    public static AdmRegistros existeRegistro(Connection con, String numuser, String empresa) {
        Log.debug(Thread.currentThread().getStackTrace()[1].getMethodName());
        StringBuilder qry = new StringBuilder();

        qry.append(" select coalesce((select 'SI' Existe from health_pacientes_user u where u.paciente_c_empresa='");
        qry.append(empresa);
        qry.append("' and u.paciente_c_numuser='");
        qry.append(numuser);
        qry.append("'),'NO') ");
        Log.debug(qry.toString());
        AdmRegistros adm = new AdmRegistros(con,
                qry.toString(),
                1,
                new RFPaciente()
        );
        adm.setColumna(1, existeRegistro);
        return adm;
    }

    /*Informacion del usuario*/
    public static AdmRegistros getPacienteInformation(Connection con) {
        Log.debug(Thread.currentThread().getStackTrace()[1].getMethodName());
        StringBuilder qry = new StringBuilder();

        qry.append(" SELECT paciente_n_id, ");
        qry.append(" paciente_c_numuser,  ");
        qry.append(" paciente_c_direccion,  ");
        qry.append(" paciente_c_prevision,  ");
        qry.append(" paciente_c_profesion,  ");
        qry.append(" paciente_c_estado_civil,  ");
        qry.append(" paciente_c_obs,  ");
        qry.append(" paciente_c_pacientename,  ");
        qry.append(" paciente_c_apellidos,  ");
        qry.append(" paciente_c_email,  ");
        qry.append(" paciente_c_celular,  ");
        qry.append(" paciente_c_createusername,  ");
        qry.append(" to_char(paciente_d_createdate, 'yyyy-mm-dd HH24:MI:SS'), ");
        qry.append(" paciente_c_createuser, ");
        qry.append(" paciente_c_img,paciente_c_sexo,paciente_d_fechanacimiento,paciente_n_edad,paciente_c_empresa ");
        qry.append(" FROM health_pacientes_user ");
        qry.append(" where paciente_c_numuser = ? and paciente_c_empresa =? ");
        Log.debug(qry.toString());
        AdmRegistros adm = new AdmRegistros(con,
                qry.toString(),
                19,
                new RFPaciente()
        );
        adm.setColumna(1, paciente_n_id);
        adm.setColumna(2, paciente_c_numuser);
        adm.setColumna(3, paciente_c_direccion);
        adm.setColumna(4, paciente_c_prevision);
        adm.setColumna(5, paciente_c_profesion);
        adm.setColumna(6, paciente_c_estado_civil);
        adm.setColumna(7, paciente_c_obs);
        adm.setColumna(8, paciente_c_pacientename);
        adm.setColumna(9, paciente_c_apellidos);
        adm.setColumna(10, paciente_c_email);
        adm.setColumna(11, paciente_c_celular);
        adm.setColumna(12, paciente_c_createusername);
        adm.setColumna(13, paciente_d_createdate);
        adm.setColumna(14, paciente_c_createuser);
        adm.setColumna(15, paciente_c_img);
        adm.setColumna(16, paciente_c_sexo);
        adm.setColumna(17, paciente_d_fechaNacimiento);
        adm.setColumna(18, paciente_n_edad);
        adm.setColumna(19, paciente_c_empresa);

        return adm;
    }

    public static AdmRegistros selectPacientes(Connection con) {
        Log.debug(Thread.currentThread().getStackTrace()[1].getMethodName());
        StringBuilder qry = new StringBuilder();

        qry.append(" SELECT paciente_n_id, ");
        qry.append(" paciente_c_numuser,  ");
        qry.append(" paciente_c_direccion,  ");
        qry.append(" paciente_c_prevision,  ");
        qry.append(" paciente_c_profesion,  ");
        qry.append(" paciente_c_estado_civil,  ");
        qry.append(" paciente_c_obs,  ");
        qry.append(" paciente_c_pacientename,  ");
        qry.append(" paciente_c_apellidos,  ");
        qry.append(" paciente_c_email,  ");
        qry.append(" paciente_c_celular,  ");
        qry.append(" paciente_c_createusername,  ");
        qry.append(" to_char(paciente_d_createdate, 'yyyy-mm-dd HH24:MI:SS'), ");
        qry.append(" paciente_c_createuser, ");
        qry.append(" paciente_c_img,paciente_c_sexo ,paciente_d_fechanacimiento,paciente_n_edad,paciente_c_empresa");
        qry.append(" FROM health_pacientes_user where paciente_c_empresa =? order by paciente_c_pacientename ASC ");
        Log.debug(qry.toString());
        AdmRegistros adm = new AdmRegistros(con,
                qry.toString(),
                19,
                new RFPaciente()
        );
        adm.setColumna(1, paciente_n_id);
        adm.setColumna(2, paciente_c_numuser);
        adm.setColumna(3, paciente_c_direccion);
        adm.setColumna(4, paciente_c_prevision);
        adm.setColumna(5, paciente_c_profesion);
        adm.setColumna(6, paciente_c_estado_civil);
        adm.setColumna(7, paciente_c_obs);
        adm.setColumna(8, paciente_c_pacientename);
        adm.setColumna(9, paciente_c_apellidos);
        adm.setColumna(10, paciente_c_email);
        adm.setColumna(11, paciente_c_celular);
        adm.setColumna(12, paciente_c_createusername);
        adm.setColumna(13, paciente_d_createdate);
        adm.setColumna(14, paciente_c_createuser);
        adm.setColumna(15, paciente_c_img);
        adm.setColumna(16, paciente_c_sexo);
        adm.setColumna(17, paciente_d_fechaNacimiento);
        adm.setColumna(18, paciente_n_edad);
        adm.setColumna(19, paciente_c_empresa);

        return adm;
    }

    /*Obtiene el ID de Insercion de la secuencia*/
    public static AdmRegistros getNewPacienteId(Connection con) {
        Log.debug(Thread.currentThread().getStackTrace()[1].getMethodName());
        StringBuilder qry = new StringBuilder();

        qry.append("select nextval('health_seq_paciente_user')");
        Log.debug(qry.toString());
        try {
            AdmRegistros adm = new AdmRegistros(con,
                    qry.toString(),
                    1,
                    new RFPaciente());

            adm.setColumna(1, paciente_n_id);

            return adm;
        } catch (Exception e) {
            Log.error(e.getMessage());
            throw e;
        }

    }

    public static void rollBack(Connection con, String rowPacienteId, String empresa) {

        Log.debug(Thread.currentThread().getStackTrace()[1].getMethodName());
        StringBuilder qry = new StringBuilder();

        qry.append(" DELETE FROM health_pacientes_user ");
        qry.append(" WHERE paciente_n_id=");
        qry.append(rowPacienteId);
        qry.append(" and  paciente_c_empresa='");
        qry.append(empresa);
        qry.append("' ");
        
        Log.debug(qry.toString());
        PreparedStatement ps = null;

        try {
            ps = con.prepareStatement(qry.toString());
            ps.executeUpdate();

        } catch (Exception e) {
            Log.error(e.getMessage());
            //return 0;
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
 public static int updatePacienteInformation(Connection con, 
         String paciente_c_direccion, 
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
         String empresa
 ) {
        Log.debug(Thread.currentThread().getStackTrace()[1].getMethodName());
        StringBuilder qry = new StringBuilder();

        qry.append(" UPDATE health_pacientes_user ");
        qry.append(" SET paciente_c_direccion ='");
        qry.append(paciente_c_direccion);
        qry.append("',paciente_c_prevision='");
        qry.append(paciente_c_prevision);
        qry.append("',paciente_c_profesion='");
        qry.append(paciente_c_profesion);
        qry.append("',paciente_c_estado_civil='");
        qry.append(paciente_c_estado_civil);
        qry.append("',paciente_c_obs='");
        qry.append(paciente_c_obs);
        qry.append("',paciente_c_email='");
        qry.append(paciente_c_email);
        qry.append("',paciente_c_celular='");
        qry.append(paciente_c_celular);
        qry.append("',paciente_c_sexo='");
        qry.append(paciente_c_sexo);
        qry.append("',paciente_d_fechanacimiento='");
        qry.append(paciente_d_fechanacimiento);
        qry.append("',paciente_n_edad='");
        qry.append(paciente_n_edad);
        qry.append("',paciente_d_ultmod_date=NOW(),paciente_c_ultmod_username='");
        qry.append(paciente_c_ultmod_username);
        qry.append("' where paciente_c_numuser='");
        qry.append(paciente_c_numuser);
        qry.append("' and paciente_c_empresa='");
        qry.append(empresa);
        qry.append("' ");

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
}
