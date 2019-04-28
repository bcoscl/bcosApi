/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.bcos.get;

import cl.bcos.Jwt.ImplementacionJWT;
import cl.bcos.Jwt.ValidarTokenJWT;
import cl.bcos.LF.LFRoles;
import cl.bcos.RF.RFRoles;
import cl.bcos.data.Registro;
import cl.bcos.entity.Roles;
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
public class ApiListarRole extends ServerResource {

    private static final Logger Log = Logger.getLogger(ApiListarRole.class);

    private ImplementacionJWT jwt = null;
    private Map s = new HashMap();
    private final String ERROR_TOKEN = "TOKEN_NO_VALIDO";

    public ApiListarRole() {
        jwt = new ImplementacionJWT();
    }

    @Post
    public Representation getRoles() {
        Log.debug(Thread.currentThread().getStackTrace()[1].getMethodName());
        Status status = null;
        String message = "ok";
        Gson gson = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create();
        Map map = new HashMap();

        //String nombre_plan = getQuery().getValues("planName");
        //String numero_maximo = getQuery().getValues("userMax");
        String token = getQuery().getValues("token");

        //Log.info("nombrePlan : " + nombre_plan);
        //Log.info("userMax : " + numero_maximo);
        Log.info("token : " + token);
        String path = getRequest().getResourceRef().getHostIdentifier() + getRequest().getResourceRef().getPath();
        Log.info("path : " + path);
        

        ValidarTokenJWT validaJWT = jwt.getJwt();
        try {
            if (token != null && !token.equals("") && validaJWT.validarTokenRS(token)) {
                try {

                    String roles = jwt.getJwt().getValue("Roles").toString();
                    // String nombre_usuario = jwt.getJwt().getValue("name").toString();
                    // String apellido_usuario = jwt.getJwt().getValue("LastName").toString();
                    String empresa = jwt.getJwt().getValue("empresaName").toString();

                    Log.info("roles :" + roles);

                    if (roles.contains("SUPER-ADMIN")) {

                        Iterator it = LFRoles.getRoles();
                        List<Roles> l = new ArrayList();
                        while (it.hasNext()) {
                            Registro reg = (Registro) it.next();
                            Roles rol = new Roles();

                            rol.setRol_c_createuser(reg.get(RFRoles.rol_c_createuser));
                            rol.setRol_c_createusername(reg.get(RFRoles.rol_c_createusername));
                            rol.setRol_c_empresaname(reg.get(RFRoles.rol_c_empresaname));
                            rol.setRol_c_rolename(reg.get(RFRoles.rol_c_rolename));
                            rol.setRol_d_createdate(reg.get(RFRoles.rol_d_createdate));
                            rol.setRol_n_id(reg.get(RFRoles.rol_n_id));

                            l.add(rol);

                        }
                        if (l.size() > 0) {
                            map.put("Roles", l);
                        }
                        Log.info("SELECT OK");
                        status = Status.SUCCESS_OK;
                        message = "SELECT_OK";

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
