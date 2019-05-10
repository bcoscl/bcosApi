/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.bcos.healthCheck;

import cl.bcos.Jwt.ImplementacionJWT;
import cl.bcos.LF.LFParams;
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
public class HealthCheckBD extends ServerResource {

    private static final Logger Log = Logger.getLogger(HealthCheckBD.class);
    private static final String AMBIENTE = "AMB";
    private static String ENV;

    private ImplementacionJWT jwt = null;
    private Map s = new HashMap();
    private final String ERROR_TOKEN = "TOKEN_NO_VALIDO";

    public HealthCheckBD() {
        jwt = new ImplementacionJWT();
    }

    @Get
    public Representation HealthCheckBD() {
        //Log.debug(Thread.currentThread().getStackTrace()[1].getMethodName());
        Status status = null;
        String message = "ok";
        Gson gson = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create();
        Map map = new HashMap();
        ENV = System.getenv(AMBIENTE);
        String token = getQuery().getValues("token");

        String path = getRequest().getResourceRef().getHostIdentifier() + getRequest().getResourceRef().getPath();

        try {
            /*Consulta a la BD*/
            //map.put("sysdate", LFParams.getsysdate());

            map.put("healthCheck", "OK");

            status = Status.SUCCESS_OK;
            message = "sysdate : " + LFParams.getsysdate() + " - Ambiente : " + ENV;

        } catch (Exception e) {
            Log.error(e.getMessage());
            //map.put("Error", e.getMessage());
            map.put("healthCheck", "ERROR - " + e.getCause());

            status = Status.CLIENT_ERROR_BAD_REQUEST;
            message = e.getMessage();
        }

        s.put("code", status.getCode());
        s.put("message", message);
        map.put("status", s);

        setStatus(status, message);

        return new StringRepresentation(gson.toJson(map), MediaType.APPLICATION_JSON);
    }
}
