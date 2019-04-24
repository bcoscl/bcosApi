/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.bcos.option;

import cl.bcos.Jwt.ImplementacionJWT;
import cl.bcos.Jwt.ValidarTokenJWT;
import cl.bcos.LF.LFExamenes;
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
public class ApiUpdateExamenes extends ServerResource {

    private static final Logger Log = Logger.getLogger(ApiUpdateExamenes.class);

    private ImplementacionJWT jwt = null;
    private Map s = new HashMap();
    private final String ERROR_TOKEN = "TOKEN_NO_VALIDO";
    private final String DELETE_EXAMEN = "PUP-DELETE-EXAMEN";
    private final String UPDATE_EXAMEN = "PUP-UPDATE-EXAMENES";

    public ApiUpdateExamenes() {
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

        String exa_n_id = getQuery().getValues("exa_n_id");
        String exa_c_name = getQuery().getValues("exa_c_name");
        String exa_c_obs = getQuery().getValues("exa_c_obs");
        String exa_c_url = getQuery().getValues("exa_c_url");
        String examen_pacientename = getQuery().getValues("examen_pacientename");

        String accion = getQuery().getValues("accion");

        String token = getQuery().getValues("token");

        Log.info("exa_n_id :" + exa_n_id);
        Log.info("exa_c_name :" + exa_c_name);
        Log.info("exa_c_obs :" + exa_c_obs);
        Log.info("exa_c_url :" + exa_c_url);
        Log.info("accion :" + accion);
        Log.info("examen_pacientename :" + examen_pacientename);

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
                    

                    Log.info(usuario_creador);

                    if (accion.equalsIgnoreCase(DELETE_EXAMEN)) {
                        if (LFExamenes.deleteExamenes(exa_n_id,empresa) == 1) {

                            Log.info("DELETE_OK");
                            status = Status.SUCCESS_OK;
                            message = "UPDATE_OK";

                        } else {
                            Log.info("Error de Udpate/Delete");
                            message = "UPDATE_NO_OK";
                            status = Status.CLIENT_ERROR_BAD_REQUEST;
                        }

                    } else if (accion.equalsIgnoreCase(UPDATE_EXAMEN)) {

                        if (LFExamenes.updateExamenes(exa_n_id, exa_c_name,
                                exa_c_obs, usuario_creador, nombre_completo, exa_c_url,empresa) == 1) {

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
