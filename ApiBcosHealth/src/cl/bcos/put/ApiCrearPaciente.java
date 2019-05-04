/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.bcos.put;

import cl.bcos.Jwt.ImplementacionJWT;
import cl.bcos.Jwt.ValidarTokenJWT;
import cl.bcos.LF.LFFichas;
import cl.bcos.LF.LFPaciente;
import cl.bcos.LF.LFUsuarios;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.HashMap;
import java.util.Map;
import org.restlet.resource.ServerResource;
import org.apache.log4j.Logger;
import org.restlet.data.MediaType;
import org.restlet.data.Status;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Post;

/**
 *
 * @author aacantero
 */
public class ApiCrearPaciente extends ServerResource {

    private static final Logger Log = Logger.getLogger(ApiCrearPaciente.class);

    private ImplementacionJWT jwt = null;
    private Map s = new HashMap();
    private final String ERROR_TOKEN = "TOKEN_NO_VALIDO";
    private final String INSERT_OK = "INSERT_OK";
    private final String INSERT_NO_OK = "INSERT_NO_OK";
    private final String USUARIO_NO_EXISTE = "USUARIO_NO_EXISTE";
    private final String USUARIO_EXISTE = "USUARIO_EXISTE";

    public ApiCrearPaciente() {
        jwt = new ImplementacionJWT();
    }

    @Post
    public Representation crearUsuario() {
        Log.debug(Thread.currentThread().getStackTrace()[1].getMethodName());
        String path = getRequest().getResourceRef().getHostIdentifier() + getRequest().getResourceRef().getPath();
        Log.info("path : " + path);

        Status status = null;
        String message = "ok";
        Gson gson = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create();
        Map map = new HashMap();

        String rowFichaId = "";
        String rowPacienteId = "";

        String accion = getQuery().getValues("accion");
        String numuser_paciente = getQuery().getValues("numuser_paciente");
        String nombre_paciente = getQuery().getValues("nombre_paciente");
        String apellido_paciente = getQuery().getValues("apellido_paciente");
        String email_contacto_paciente = getQuery().getValues("email_contacto_paciente");
        String numero_telefono_paciente = getQuery().getValues("numero_telefono_paciente");
        String profesion_paciente = getQuery().getValues("profesion_paciente");
        String estado_civil_paciente = getQuery().getValues("estado_civil_paciente");
        String fecha_nacimiento_paciente = getQuery().getValues("fecha_nacimiento_paciente");
        String edad_paciente = getQuery().getValues("edad_paciente");
        String prevision = getQuery().getValues("prevision");
        String aboutme_obs_paciente = getQuery().getValues("aboutme_obs_paciente");
        String direccion_paciente = getQuery().getValues("direccion_paciente");
        String sexo = getQuery().getValues("sexo");

        String token = getQuery().getValues("token");

        String empresasession = getQuery().getValues("empresasession");

        String empresa = "";

        Log.info("accion :" + accion);
        Log.info("numuser_paciente :" + numuser_paciente);
        Log.info("nombre_paciente :" + nombre_paciente);
        Log.info("apellido_paciente :" + apellido_paciente);
        Log.info("email_contacto_paciente :" + email_contacto_paciente);
        Log.info("numero_telefono_paciente :" + numero_telefono_paciente);
        Log.info("profesion_paciente :" + profesion_paciente);
        Log.info("estado_civil_paciente :" + estado_civil_paciente);
        Log.info("fecha_nacimiento_paciente :" + fecha_nacimiento_paciente);
        Log.info("edad_paciente :" + edad_paciente);
        Log.info("prevision :" + prevision);
        Log.info("aboutme_obs_paciente :" + aboutme_obs_paciente);
        Log.info("direccion_paciente :" + direccion_paciente);
        Log.info("sexo :" + sexo);

        Log.info("token : " + token);

        ValidarTokenJWT validaJWT = jwt.getJwt();
        try {
            if (token != null && !token.equals("") && validaJWT.validarTokenRS(token)) {
                try {

                    String usuario_creador = jwt.getJwt().getValue("numUser").toString();
                    String nombre_usuario = jwt.getJwt().getValue("name").toString();
                    String apellido_usuario = jwt.getJwt().getValue("LastName").toString();
                    String nombre_completo = nombre_usuario + " " + apellido_usuario;
                    empresa = jwt.getJwt().getValue("empresaName").toString();
                    String roles = jwt.getJwt().getValue("Roles").toString();

                    if (roles.contains("SUPER-ADMIN")) {
                        empresa = empresasession;
                    }
                    Log.info("empresa :" + empresa);

                    Log.info("usuario Creador:" + usuario_creador);
                    if (roles.contains("SUPER-ADMIN") || roles.contains("ADMIN") || roles.contains("MEDICO") || roles.contains("RECEPCION")) {
                        switch (accion) {
                            case "EXISTE_PACIENTE":
                                if (LFPaciente.existeRegistro(numuser_paciente, empresa).equalsIgnoreCase("NO")
                                        && LFFichas.existeRegistro(numuser_paciente, empresa).equalsIgnoreCase("NO")) {

                                    Log.info("USUARIO NO REGISTRADO");
                                    status = Status.SUCCESS_OK;
                                    message = USUARIO_NO_EXISTE;
                                } else {
                                    Log.info("USUARIO REGISTRADO");
                                    status = Status.SUCCESS_OK;
                                    message = USUARIO_EXISTE;
                                }
                                break;
                            case "CREATE":

                                rowFichaId = LFFichas.getNewFichaId();
                                rowPacienteId = LFPaciente.getNewPacienteId();

                                Log.info("rowFichaId : " + rowFichaId);
                                Log.info("rowPacienteId : " + rowPacienteId);

                                if (!rowPacienteId.isEmpty() && (!rowFichaId.isEmpty())
                                        && (LFPaciente.insertPaciente(numuser_paciente,
                                                nombre_paciente, apellido_paciente, email_contacto_paciente,
                                                numero_telefono_paciente, profesion_paciente, estado_civil_paciente,
                                                fecha_nacimiento_paciente, edad_paciente, prevision, aboutme_obs_paciente,
                                                usuario_creador, nombre_completo, empresa, rowPacienteId, direccion_paciente, sexo) == 1)
                                        && (LFFichas.insertFicha(rowFichaId, numuser_paciente,
                                                nombre_paciente, apellido_paciente,
                                                nombre_completo, roles, usuario_creador, nombre_completo,
                                                empresa) == 1)) {

                                    Log.info("Insert OK");
                                    status = Status.SUCCESS_OK;
                                    message = INSERT_OK;

                                } else {

                                    Log.info("Error de insercion");
                                    //Roll back - delete
                                    LFFichas.rollBack(rowFichaId, empresa);

                                    LFUsuarios.rollBack(rowPacienteId, empresa);
                                    // rowFichaId ;
                                    //rowUserId ;
                                    message = INSERT_NO_OK;
                                    status = Status.CLIENT_ERROR_BAD_REQUEST;

                                }
                                break;
                            default:
                                Log.error("no Soportada :" + accion);
                        }
                    } else {
                        status = Status.CLIENT_ERROR_UNAUTHORIZED;
                        Log.error("Perfil sin acceso");
                        message = ERROR_TOKEN;
                    }
                } catch (Exception e) {
                    Log.error("getMessage :" + e.getMessage());
                    Log.error(e.toString());
                    //Roll back - delete
                    LFFichas.rollBack(rowFichaId, empresa);
                    LFPaciente.rollBack(rowPacienteId, empresa);
                    // rowFichaId ;
                    //rowUserId ;
                    status = Status.CLIENT_ERROR_BAD_REQUEST;
                    message = ERROR_TOKEN;
                }
            } else {
                status = Status.CLIENT_ERROR_UNAUTHORIZED;
                message = ERROR_TOKEN;
            }
        } catch (Exception e) {
            status = Status.CLIENT_ERROR_UNAUTHORIZED;
            message = ERROR_TOKEN;
        }

        s.put("code", status.getCode());
        s.put("message", message);
        map.put("status", s);

        setStatus(status, message);

        return new StringRepresentation(gson.toJson(map), MediaType.APPLICATION_JSON);
    }
}
