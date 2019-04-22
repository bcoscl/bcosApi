/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.bcos.get;

import cl.bcos.Jwt.ImplementacionJWT;
import cl.bcos.Jwt.ValidarTokenJWT;
import cl.bcos.LF.LFFarmacos;
import cl.bcos.RF.RFFarmacos;
import cl.bcos.data.Registro;
import cl.bcos.entity.Farmacos;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
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
public class ApiListarFarmacos extends ServerResource {

    private static final Logger Log = Logger.getLogger(ApiListarFarmacos.class);

    private ImplementacionJWT jwt = null;
    private Map s = new HashMap();
    private final String ERROR_TOKEN = "TOKEN_NO_VALIDO";

    public ApiListarFarmacos() {
        jwt = new ImplementacionJWT();
    }

    @Post
    public Representation getListarFarmacos() {
        Log.debug(Thread.currentThread().getStackTrace()[1].getMethodName());
        Status status = null;
        String message = "ok";
        Gson gson = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create();
        Map map = new HashMap();

        String accion = getQuery().getValues("accion");
        String Paciente = getQuery().getValues("Paciente");// numuser Paciente
        String token = getQuery().getValues("token");

        Log.info("accion : " + accion);
        Log.info("Paciente : " + Paciente);
        Log.info("token : " + token);

        ValidarTokenJWT validaJWT = jwt.getJwt();
        try {
            if (token != null && !token.equals("") && validaJWT.validarTokenRS(token)) {
                try {

                    String roles = jwt.getJwt().getValue("Roles").toString();
                    String usuario_creador = jwt.getJwt().getValue("numUser").toString();
                    // String apellido_usuario = jwt.getJwt().getValue("LastName").toString();
                    String empresa = jwt.getJwt().getValue("empresaName").toString();

                    Log.info("roles :" + roles);

                    if (roles.contains("SUPER-ADMIN")) {
                        Iterator it = LFFarmacos.selectFarmacos(Paciente, empresa);
                        List<Farmacos> l = new ArrayList();
                        while (it.hasNext()) {
                            Registro reg = (Registro) it.next();
                            Farmacos s = new Farmacos();

                            s.setFarmaco_c_name(reg.get(RFFarmacos.farmaco_c_name));
                            s.setFarmaco_c_obs(reg.get(RFFarmacos.farmaco_c_obs));
                            s.setFarmaco_c_ultmod_username(reg.get(RFFarmacos.farmaco_c_ultmod_username));
                            s.setFarmaco_d_ultmod_date(reg.get(RFFarmacos.farmaco_d_ultmod_date));
                            s.setFarmaco_n_id(reg.get(RFFarmacos.farmaco_n_id));
                            s.setFarmaco_n_ultmod_numuser(reg.get(RFFarmacos.farmaco_n_ultmod_numuser));
                            s.setFarmaco_numuser_paciente(reg.get(RFFarmacos.farmaco_numuser_paciente));

                            l.add(s);

                        }
                        if (l.size() > 0) {
                            map.put("Farmacos", l);
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
