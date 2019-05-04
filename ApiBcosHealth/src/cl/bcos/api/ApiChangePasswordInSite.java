/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.bcos.api;

import cl.bcos.BlowFish;
import cl.bcos.Jwt.ImplementacionJWT;
import cl.bcos.Jwt.ValidarTokenJWT;
import cl.bcos.LF.LFParams;
import cl.bcos.LF.LFUsuarios;
import cl.bcos.get.ApiListarAttentionList;
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
public class ApiChangePasswordInSite extends ServerResource {

    private static final Logger Log = Logger.getLogger(ApiChangePasswordInSite.class);

    private ImplementacionJWT jwt = null;
    private Map s = new HashMap();
    private final String ERROR_TOKEN = "TOKEN_NO_VALIDO";

    public ApiChangePasswordInSite() {
        jwt = new ImplementacionJWT();
    }

    @Post
    public Representation changePassword() {
        Log.debug(Thread.currentThread().getStackTrace()[1].getMethodName());
        Status status = null;
        String message = "ok";
        Gson gson = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create();
        Map map = new HashMap();

        String token = getQuery().getValues("token");
        String passs = getQuery().getValues("password");

        Log.info("token : " + token);
        Log.info("passs : " + passs);
        String path = getRequest().getResourceRef().getHostIdentifier() + getRequest().getResourceRef().getPath();
        Log.info("path : " + path);

        ValidarTokenJWT validaJWT = jwt.getJwt();
        try {
            if (token != null && !token.equals("") && validaJWT.validarTokenRS(token)) {

                String usuario = jwt.getJwt().getValue("numUser").toString();
                String key = LFParams.getParams("PASS", "BLOWFISH", "CRYPT");
                passs = BlowFish.encryptNoPadding(key, passs);

                if (LFUsuarios.changePass(usuario, passs) == 1) {

                    s.put("numuser", usuario);
                    Log.info("RESET_OK");
                    status = Status.SUCCESS_OK;
                    message = "RESET_OK";
                } else {

                    Log.info("RESET_NO_OK");
                    status = Status.CLIENT_ERROR_BAD_REQUEST;
                    message = "RESET_NO_OK";

                }

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
