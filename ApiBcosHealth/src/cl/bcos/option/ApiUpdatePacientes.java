/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.bcos.option;

import cl.bcos.Jwt.ImplementacionJWT;
import cl.bcos.Jwt.ValidarTokenJWT;
import cl.bcos.LF.LFPaciente;
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
public class ApiUpdatePacientes extends ServerResource {

    private static final Logger Log = Logger.getLogger(ApiUpdatePacientes.class);

    private ImplementacionJWT jwt = null;
    private Map s = new HashMap();
    private final String ERROR_TOKEN = "TOKEN_NO_VALIDO";

    private final String UPDATE_PACIENTE_PROFILE = "UPDATE-PACIENTE-PROFILE";

    public ApiUpdatePacientes() {
        jwt = new ImplementacionJWT();
    }

    @Post
    public Representation crearUsuario() {
        Log.debug(Thread.currentThread().getStackTrace()[1].getMethodName());
        Status status = null;
        String message = "ok";
        Gson gson = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create();
        Map map = new HashMap();

        String accion = getQuery().getValues("accion");
        String numuser_paciente = getQuery().getValues("numuser_paciente");
        //String nombre_paciente = getQuery().getValues("nombre_paciente");
        //String apellido_paciente = getQuery().getValues("apellido_paciente");
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

        Log.info("accion :" + accion);
        Log.info("numuser_paciente :" + numuser_paciente);
        //Log.info("nombre_paciente :" + nombre_paciente);
        //Log.info("apellido_paciente :" + apellido_paciente);
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
        Log.info("token bearer:" + token);

     

        ValidarTokenJWT validaJWT = jwt.getJwt();
        try {
            if (token != null && !token.equals("") && validaJWT.validarTokenRS(token)) {
                try {

                    String usuario_creador = jwt.getJwt().getValue("numUser").toString();
                    String nombre_usuario = jwt.getJwt().getValue("name").toString();
                    String apellido_usuario = jwt.getJwt().getValue("LastName").toString();
                    String nombre_completo = nombre_usuario + " " + apellido_usuario;
                    String empresa = jwt.getJwt().getValue("empresaName").toString();

                    Log.info(usuario_creador);

                    if (accion.equalsIgnoreCase(UPDATE_PACIENTE_PROFILE)) {

                        if (LFPaciente.updatePacienteInformation(direccion_paciente,
                                prevision,
                                profesion_paciente,
                                estado_civil_paciente,
                                aboutme_obs_paciente,
                                email_contacto_paciente,
                                numero_telefono_paciente,
                                sexo,
                                fecha_nacimiento_paciente,
                                edad_paciente,
                                usuario_creador,
                                numuser_paciente,
                                empresa) == 1) {

                            Log.info("UPDATE OK");
                            status = Status.SUCCESS_OK;
                            message = "UPDATE_OK";
                        } else {
                            Log.info("Error de Udpate/Delete");
                            message = "UPDATE_NO_OK";
                            status = Status.CLIENT_ERROR_BAD_REQUEST;
                        }
                    } else {

                        Log.info("Error de Udpate/Delete");
                        message = "UPDATE_NO_OK";
                        status = Status.CLIENT_ERROR_BAD_REQUEST;

                    }
                } catch (Exception e) {
                    Log.error("getMessage :" + e.getMessage());
                    Log.error(e.toString());

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
