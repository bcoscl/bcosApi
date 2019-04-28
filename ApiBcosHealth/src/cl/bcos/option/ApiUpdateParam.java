/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.bcos.option;

import cl.bcos.Jwt.ImplementacionJWT;
import cl.bcos.Jwt.ValidarTokenJWT;
import cl.bcos.LF.LFParams;
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
public class ApiUpdateParam extends ServerResource {

    private static final Logger Log = Logger.getLogger(ApiUpdateParam.class);

    private ImplementacionJWT jwt = null;
    private Map s = new HashMap();
    private final String ERROR_TOKEN = "TOKEN_NO_VALIDO";
    private final String DELETE_PARAM = "DELETE-PARAM";
    private final String UPDATE_PARAM = "UPDATE-PARAM";

    public ApiUpdateParam() {
        jwt = new ImplementacionJWT();
    }

    @Post
    public Representation updateSucursales() {
        Log.debug(Thread.currentThread().getStackTrace()[1].getMethodName());
        String path = getRequest().getResourceRef().getHostIdentifier() + getRequest().getResourceRef().getPath();
        Log.info("path : " + path);

        Status status = null;
        String message = "ok";
        Gson gson = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create();
        Map map = new HashMap();

        String params_n_grupo = getQuery().getValues("params_grupo");
        String params_n_subgrupo = getQuery().getValues("params_subgrupo");
        String params_n_param1 = getQuery().getValues("params_param1");
        String params_n_param2 = getQuery().getValues("params_param2");
        String params_n_param3 = getQuery().getValues("params_param3");
        String params_n_param4 = getQuery().getValues("params_param4");
        String params_n_id = getQuery().getValues("params_id");
        String accion = getQuery().getValues("accion");

        String token = getQuery().getValues("token");

        Log.info("params_n_grupo : " + params_n_grupo);
        Log.info("params_n_subgrupo : " + params_n_subgrupo);
        Log.info("params_n_param1 : " + params_n_param1);
        Log.info("params_n_param2 : " + params_n_param2);
        Log.info("params_n_param3 : " + params_n_param3);
        Log.info("params_n_param4 : " + params_n_param4);
        Log.info("params_n_id : " + params_n_id);
        Log.info("accion : " + accion);
        Log.info("token : " + token);

        ValidarTokenJWT validaJWT = jwt.getJwt();
        try {
            if (token != null && !token.equals("") && validaJWT.validarTokenRS(token)) {
                try {

                    String usuario_creador = jwt.getJwt().getValue("numUser").toString();
                    String nombre_usuario = jwt.getJwt().getValue("name").toString();
                    String apellido_usuario = jwt.getJwt().getValue("LastName").toString();
                    String nombre_completo = nombre_usuario + " " + apellido_usuario;
                    String empresa = jwt.getJwt().getValue("empresaName").toString();
                    String roles = jwt.getJwt().getValue("Roles").toString();

                    Log.info(usuario_creador);
                    if (roles.contains("SUPER-ADMIN")) {
                        if (accion.equalsIgnoreCase(DELETE_PARAM)) {
                            if (LFParams.deleteParams(params_n_id) == 1) {

                                Log.info("DELETE_OK");
                                status = Status.SUCCESS_OK;
                                message = "UPDATE_OK";

                            } else {
                                Log.info("Error de Udpate/Delete");
                                message = "UPDATE_NO_OK";
                                status = Status.CLIENT_ERROR_BAD_REQUEST;
                            }

                        } /*else if (accion.equalsIgnoreCase(UPDATE_PARAM)) {

                        if (LFParams..updateFarmacos(params_n_id) == 1) {

                            Log.info("UPDATE OK");
                            status = Status.SUCCESS_OK;
                            message = "UPDATE_OK";
                        } else {
                            Log.info("Error de Udpate/Delete");
                            message = "UPDATE_NO_OK";
                            status = Status.CLIENT_ERROR_BAD_REQUEST;
                        }
                    } */ else {

                            Log.info("Error NO SOPORTADO");
                            message = "UPDATE_NO_OK";
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
