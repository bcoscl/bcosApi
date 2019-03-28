/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.bcos.api;

import cl.bcos.Jwt.GenerarTokenJWT;
import cl.bcos.Jwt.ImplementacionJWT;
import cl.bcos.LF.LFSSO;
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
public class ApiSSO extends ServerResource {

    private static final Logger Log = Logger.getLogger(ApiSSO.class);

    private ImplementacionJWT jwt = null;
    private Map s = new HashMap();
    private Map b = new HashMap();
    private final String ERROR_TOKEN = "Credenciale incorrectas";

    public ApiSSO() {
        jwt = new ImplementacionJWT();
    }

    @Post
    public Representation SSO() {
        Status status = null;
        String message = "ok";
        Gson gson = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create();
        Map map = new HashMap();
        Map info = new HashMap();

        jwt = new ImplementacionJWT();
        GenerarTokenJWT token = jwt.getGeneraJWT();

        String tokken = "";
        String userr = getQuery().getValues("user");
        String passs = getQuery().getValues("pass");
        String ValidaPassword = "OK";

        Log.info("user : " + userr);
        Log.info("pass : " + passs);
        Log.info("Validar Datos con BD, pass encritar antes de comprar");

        try {

            if (LFSSO.autenticacion(userr, passs).equalsIgnoreCase("OK")) {
                Log.info("USUARIO OK");
                Log.info("agregar informacion basica en el token");

                token.AddItem("name", "Alexis Antonio");
                token.AddItem("LastName", "Cantero Castro");
                token.AddItem("numUser", "17043788");
                token.AddItem("Roles", "ADMIN, DOCTOR,SUPER-ADMIN");
                //token.AddItem("phoneNumber", "963344468");
                //token.AddItem("officeCode", "21");
                //token.AddItem("officeName", "Coelemu");
                //token.AddItem("companyCode", "1");
                //token.AddItem("companyName", "kineira");

                tokken = token.generaToken("bcosHealth", "bearer", "public","54000");
                //tokken = token.generaToken("bcosHealth", "bearer", "public","15");

                //Log.info(tokken);
                // ValidarTokenJWT validaJWT = jwt.getJwt();
//        if (token != null && !token.equals("") && validaJWT.validarTokenRS(token)) {
//            try {
//
//                String user = jwt.getJwt().getValue("user").toString();
//                String pass = jwt.getJwt().getValue("pass").toString();
//                String iss = jwt.getJwt().getValue("iss").toString();
//
//                Log.info("user : " + user);
//                Log.info("pass : " + pass);
//
//                token.AddItem("login", );
//
                map.put("login", "OK");
                map.put("token", tokken);
//
                message = "ok";
                status = Status.SUCCESS_OK;

            } else {
                Log.error("USUARIO NO AUTORIZADO");
                map.put("login", "NO-OK");
                status = Status.CLIENT_ERROR_UNAUTHORIZED;
                message = ERROR_TOKEN;
            }

        } catch (Exception e) {
            Log.error(e.toString());
            status = Status.CLIENT_ERROR_BAD_REQUEST;
            message = ERROR_TOKEN;
        }
//        } else {
//            status = Status.CLIENT_ERROR_UNAUTHORIZED;
//            message = ERROR_TOKEN;
//        }
        s.put("statusCode", status.getCode());
        s.put("name", "");
        s.put("code", "");
        s.put("stack", "");
        s.put("message", message);
        map.put("status", s);

        setStatus(status, message);
        return new StringRepresentation(gson.toJson(map), MediaType.APPLICATION_JSON);
    }
}
