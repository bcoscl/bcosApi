/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.bcos.put;

import cl.bcos.Jwt.ImplementacionJWT;
import cl.bcos.Jwt.ValidarTokenJWT;
import cl.bcos.LF.LFRoles;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.HashMap;
import java.util.Map;
import org.apache.log4j.Logger;
import org.restlet.data.MediaType;
import org.restlet.data.Status;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;

/**
 *
 * @author aacantero
 */
public class ApiCrearRole extends ServerResource {

    private static final Logger Log = Logger.getLogger(ApiCrearRole.class);

    private ImplementacionJWT jwt = null;
    private Map s = new HashMap();
    private final String ERROR_TOKEN = "TOKEN_NO_VALIDO";
    private final String PARAMA_REQUIERED = "PARAMETROS_REQUERIDOS";

    public ApiCrearRole() {
        jwt = new ImplementacionJWT();
    }

    @Post
    public Representation crearRole() {
        Log.debug(Thread.currentThread().getStackTrace()[1].getMethodName());
        String path = getRequest().getResourceRef().getHostIdentifier() + getRequest().getResourceRef().getPath();
        Log.info("path : " + path);

        Status status = null;
        String message = "ok";
        Gson gson = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create();
        Map map = new HashMap();

        String RoleName = getQuery().getValues("RoleName");

        String token = getQuery().getValues("token");
        String empresasession = getQuery().getValues("empresasession");

        Log.info("RoleName : " + RoleName);

        Log.info("token : " + token);

        ValidarTokenJWT validaJWT = jwt.getJwt();
        try {
            if (!RoleName.isEmpty()) {
                if (token != null && !token.equals("") && validaJWT.validarTokenRS(token)) {
                    try {

                        String usuario_creador = jwt.getJwt().getValue("numUser").toString();
                        String nombre_usuario = jwt.getJwt().getValue("name").toString();
                        String apellido_usuario = jwt.getJwt().getValue("LastName").toString();
                        String empresa = jwt.getJwt().getValue("empresaName").toString();
                        String roles = jwt.getJwt().getValue("Roles").toString();

                        if (roles.contains("SUPER-ADMIN")) {
                            empresa = empresasession;
                        }
                        Log.info("empresa :" + empresa);

                        Log.info("usuario Creador:" + usuario_creador);
                        if (roles.contains("SUPER-ADMIN")) {
                            if (LFRoles.insertRole(RoleName, usuario_creador, nombre_usuario + " " + apellido_usuario) == 1) {

                                Log.info("Insert OK");
                                status = Status.SUCCESS_OK;
                                message = "INSERT_OK";

                            } else {

                                Log.info("Error de insercion");
                                message = "INSERT_NO_OK";
                                status = Status.CLIENT_ERROR_BAD_REQUEST;

                            }
                        } else {
                            status = Status.CLIENT_ERROR_UNAUTHORIZED;
                            Log.error("Perfil sin acceso");
                            message = ERROR_TOKEN;
                        }
                    } catch (Exception e) {
                        Log.error(e.toString());
                        status = Status.CLIENT_ERROR_BAD_REQUEST;
                        message = ERROR_TOKEN;
                    }
                } else {
                    status = Status.CLIENT_ERROR_UNAUTHORIZED;
                    message = ERROR_TOKEN;
                }
            } else {
                status = Status.CLIENT_ERROR_BAD_REQUEST;
                message = PARAMA_REQUIERED;
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
