/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.bcos.api;

import cl.bcos.Jwt.ImplementacionJWT;
import cl.bcos.Jwt.ValidarTokenJWT;
import cl.bcos.LF.LFFichas;
import cl.bcos.RF.RFFichas;
import cl.bcos.data.Registro;
import cl.bcos.entity.Fichas;
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
public class ApiListarFichas extends ServerResource {

    private static final Logger Log = Logger.getLogger(ApiListarFichas.class);

    private ImplementacionJWT jwt = null;
    private Map s = new HashMap();
    private final String ERROR_TOKEN = "TOKEN_NO_VALIDO";

    public ApiListarFichas() {
        jwt = new ImplementacionJWT();
    }

    @Post
    public Representation getSuscripciones() {
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

        ValidarTokenJWT validaJWT = jwt.getJwt();
        try {
            if (token != null && !token.equals("") && validaJWT.validarTokenRS(token)) {
                try {

                    String roles = jwt.getJwt().getValue("Roles").toString();
                    // String nombre_usuario = jwt.getJwt().getValue("name").toString();
                    // String apellido_usuario = jwt.getJwt().getValue("LastName").toString();

                    Log.info("roles :" + roles);

                    if (roles.contains("SUPER-ADMIN")) {

                        Iterator it = LFFichas.selectSFichas();
                        List<Fichas> l = new ArrayList();
                        while (it.hasNext()) {
                            Registro reg = (Registro) it.next();
                            Fichas s = new Fichas();

                            s.setFicha_c_createuser(reg.get(RFFichas.ficha_c_createuser));
                            s.setFicha_c_createusername(reg.get(RFFichas.ficha_c_createusername));
                            s.setFicha_c_empresaname(reg.get(RFFichas.ficha_c_empresaname));
                            s.setFicha_c_numuser_paciente(reg.get(RFFichas.ficha_c_numuser_paciente));
                            s.setFicha_c_paciente_name(reg.get(RFFichas.ficha_c_paciente_name));
                            s.setFicha_c_role_userultimamod(reg.get(RFFichas.ficha_c_role_userultimamod));
                            s.setFicha_c_userultimamod(reg.get(RFFichas.ficha_c_userultimamod));
                            s.setFicha_d_createdate(reg.get(RFFichas.ficha_d_createdate));
                            s.setFicha_n_id(reg.get(RFFichas.ficha_n_id));
                            s.setFicha_d_dateultimamod(reg.get(RFFichas.ficha_d_dateultimamod));
                           

                            l.add(s);

                        }
                        if (l.size() > 0) {
                            map.put("Fichas", l);
                        }
                        Log.info("SELECT OK");
                        status = Status.SUCCESS_OK;
                        message = "SELECT_OK";

                    } else {

                        Log.info("EL perfil no tiene acceso");
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
