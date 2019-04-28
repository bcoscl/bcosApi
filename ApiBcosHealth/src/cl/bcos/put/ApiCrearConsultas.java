/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.bcos.put;

import cl.bcos.Jwt.ImplementacionJWT;
import cl.bcos.Jwt.ValidarTokenJWT;
import cl.bcos.LF.LFConsultas;
import cl.bcos.LF.LFFichas;
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
public class ApiCrearConsultas extends ServerResource {

    private static final Logger Log = Logger.getLogger(ApiCrearConsultas.class);

    private ImplementacionJWT jwt = null;
    private Map s = new HashMap();
    private final String ERROR_TOKEN = "TOKEN_NO_VALIDO";
    private final String INSERT_OK = "INSERT_OK";
    private final String INSERT_NO_OK = "INSERT_NO_OK";
    private final String CREATE = "PUP-CONSULTA-CREATE";

    public ApiCrearConsultas() {
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
        String empresa = "";

        String accion = getQuery().getValues("accion");
        String consult_c_titulo = getQuery().getValues("consult_c_titulo");
        String consult_c_obs_consulta = getQuery().getValues("consult_c_obs_consulta");
        String consult_c_tipoconsulta = getQuery().getValues("consult_c_tipoconsulta");
        String consult_c_numuser_paciente = getQuery().getValues("consult_c_numuser_paciente");
        String consult_paciente_name = getQuery().getValues("consult_c_paciente_name");
        String consult_c_createdate = getQuery().getValues("consult_c_createdate");

        String token = getQuery().getValues("token");

        Log.info("accion :" + accion);
        Log.info("consult_c_titulo :" + consult_c_titulo);
        Log.info("consult_c_obs_consulta :" + consult_c_obs_consulta);
        Log.info("consult_c_tipoconsulta :" + consult_c_tipoconsulta);
        Log.info("consult_c_numuser_paciente :" + consult_c_numuser_paciente);
        Log.info("consult_paciente_name :" + consult_paciente_name);
        Log.info("consult_c_createdate :" + consult_c_createdate);

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

                    Log.info("usuario Creador:" + usuario_creador);
                    if (roles.contains("SUPER-ADMIN") || roles.contains("ADMIN") || roles.contains("MEDICO")) {
                        switch (accion) {

                            case CREATE:

                                if (LFConsultas.insertConsultas(consult_c_titulo,
                                        consult_c_obs_consulta,
                                        consult_c_tipoconsulta,
                                        consult_c_numuser_paciente,
                                        consult_paciente_name,
                                        usuario_creador,
                                        nombre_completo,
                                        consult_c_createdate,
                                        empresa) == 1) {

                                    Log.info("Insert OK");
                                    status = Status.SUCCESS_OK;
                                    message = INSERT_OK;

                                } else {

                                    Log.info("Error de insercion");

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
