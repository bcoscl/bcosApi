/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.bcos.get;

import cl.bcos.Jwt.ImplementacionJWT;
import cl.bcos.Jwt.ValidarTokenJWT;
import cl.bcos.entity.Rol;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.ArrayList;
import java.util.HashMap;
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
public class ApiListarMenu extends ServerResource {

    private static final Logger Log = Logger.getLogger(ApiListarMenu.class);

    private ImplementacionJWT jwt = null;
    private Map s = new HashMap();
    private final String ERROR_TOKEN = "TOKEN_NO_VALIDO";

    public ApiListarMenu() {
        jwt = new ImplementacionJWT();
    }

    @Post
    public Representation getMenu() {
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
                try {

                    String roles = jwt.getJwt().getValue("Roles").toString();
                    String nombre_usuario = jwt.getJwt().getValue("name").toString();
                    String apellido_usuario = jwt.getJwt().getValue("LastName").toString();

                    String nombre_completo = nombre_usuario.split(" ")[0] + " " + apellido_usuario.split(" ")[0];

                    String empresa = jwt.getJwt().getValue("empresaName").toString();
                    String pre = "";
                    String pos = "";

                    if (roles.contains("MEDICO")) {
                        pre = "Dr. ";
                    } else {
                        pre = "Mr. ";
                    }
                    
                    if (roles.contains("SUPER-ADMIN")) {
                        pos = " - Super Admin ";
                    }else if(roles.contains("ADMIN")) {
                        pos = " - Admin ";
                    }else if(roles.contains("RECEPCION")) {
                        pos = " - Recepcionista";
                    }
                    
                    nombre_completo = pre+nombre_completo+pos;

                    Log.info("roles :" + roles);
                    List<Rol> l = new ArrayList();

                    try {
                        String[] roles_ = roles.split(",");

                        for (int i = 0; i < roles_.length; i++) {
                            Rol r = new Rol();
                            r.setRol(roles_[i]);
                            l.add(r);
                        }

                        map.put("Rol", l);
                        map.put("NOMBRE", nombre_completo);
                        Log.info("SELECT OK");
                        status = Status.SUCCESS_OK;
                        message = "SELECT_OK";

                    } catch (Exception e) {

                        map.put("Acceso", "");
                        Log.info("SIN ROLES");
                        message = "SELECT_NO_OK";
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
