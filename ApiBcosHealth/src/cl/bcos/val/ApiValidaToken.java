/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.bcos.val;

import cl.bcos.Jwt.ImplementacionJWT;
import cl.bcos.Jwt.ValidarTokenJWT;
import cl.bcos.LF.LFAttentionList;
import cl.bcos.RF.RFAttentionList;
import cl.bcos.data.Registro;
import cl.bcos.entity.Attention;
import cl.bcos.get.ApiListarAttentionList;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
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
public class ApiValidaToken extends ServerResource {

    private static final Logger Log = Logger.getLogger(ApiListarAttentionList.class);

    private ImplementacionJWT jwt = null;
    private Map s = new HashMap();
    private final String ERROR_TOKEN = "TOKEN_NO_VALIDO";

    public ApiValidaToken() {
        jwt = new ImplementacionJWT();
    }

    @Post
    public Representation ApiValidaToken() {
        Log.debug(Thread.currentThread().getStackTrace()[1].getMethodName());
        Status status = null;
        String message = "ok";
        Gson gson = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create();
        Map map = new HashMap();

        String token = getQuery().getValues("token");

        Log.info("token : " + token);
        String path = getRequest().getResourceRef().getHostIdentifier() + getRequest().getResourceRef().getPath();
        Log.info("path : " + path);

        ValidarTokenJWT validaJWT = jwt.getJwt();
        try {
            if (token != null && !token.equals("") && validaJWT.validarTokenRS(token)) {

                String usuario = jwt.getJwt().getValue("numUser").toString();
                s.put("numuser", usuario);
                Log.info("TOKEN VALIDO");
                status = Status.SUCCESS_OK;
                message = "TOKEN_OK";

            } else {
                status = Status.CLIENT_ERROR_UNAUTHORIZED;
                Log.error("Perfil sin acceso");
                message = ERROR_TOKEN;
            }
        } catch (Exception e) {
            Log.error(e.toString());
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
