/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.bcos.healthCheck;

import cl.bcos.Jwt.ImplementacionJWT;
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
import org.restlet.resource.Get;

/**
 *
 * @author aacantero
 */
public class HealthCheck extends ServerResource {

    private static final Logger Log = Logger.getLogger(HealthCheck.class);
    private static final String AMBIENTE = "AMB";
    private static String ENV;

    private ImplementacionJWT jwt = null;
    private Map s = new HashMap();
    private final String ERROR_TOKEN = "TOKEN_NO_VALIDO";

    public HealthCheck() {
        
        jwt = new ImplementacionJWT();
    }

    @Get
    public Representation healthCheck() {
        //Log.debug(Thread.currentThread().getStackTrace()[1].getMethodName());
        Status status = null;
        String message = "ok";
        Gson gson = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create();
        Map map = new HashMap();
        ENV = System.getenv(AMBIENTE);
        String path = getRequest().getResourceRef().getHostIdentifier() + getRequest().getResourceRef().getPath();
        //Log.info("path : " + path);

        if (ENV == null || ENV.equals("")) {
            String Error = "Falta Variable de ambiente :" + AMBIENTE + " valor : " + ENV;
            Log.error(Error);

            status = Status.CLIENT_ERROR_NOT_FOUND;
            message = "ERROR - "+Error;

        } else {

            map.put("healthCheck", "OK");

            //Log.info("healthCheck OK");
            status = Status.SUCCESS_OK;
            message = "Ambiente : "+ENV;

        }
        s.put("code", status.getCode());
        s.put("message", message);
        map.put("status", s);
        setStatus(status, message);

        return new StringRepresentation(gson.toJson(map), MediaType.APPLICATION_JSON);
    }
}
