/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.bcos.get;

import cl.bcos.Jwt.ImplementacionJWT;
import cl.bcos.Jwt.ValidarTokenJWT;
import cl.bcos.LF.LFExamenes;
import cl.bcos.RF.RFExamenes;
import cl.bcos.data.Registro;
import cl.bcos.entity.Examenes;
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
public class ApiListarExamenes extends ServerResource {

    private static final Logger Log = Logger.getLogger(ApiListarExamenes.class);

    private ImplementacionJWT jwt = null;
    private Map s = new HashMap();
    private final String ERROR_TOKEN = "TOKEN_NO_VALIDO";
    private final String LE_TABLA = "LE-TABLA";
    private final String CE_EXAMENES_PROFILE = "CE-EXAMENES-PROFILE";
    private final String LE_BY_PACIENTE_TABLA = "LE-TABLA-BY-PACIENTE";

    public ApiListarExamenes() {
        jwt = new ImplementacionJWT();
    }

    @Post
    public Representation getExamenes() {
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

        String path = getRequest().getResourceRef().getHostIdentifier() + getRequest().getResourceRef().getPath();
        Log.info("path : " + path);

        ValidarTokenJWT validaJWT = jwt.getJwt();
        try {
            if (token != null && !token.equals("") && validaJWT.validarTokenRS(token)) {
                try {

                    String roles = jwt.getJwt().getValue("Roles").toString();
                    String usuario_creador = jwt.getJwt().getValue("numUser").toString();
                    String apellido_usuario = jwt.getJwt().getValue("LastName").toString();
                    String empresa = jwt.getJwt().getValue("empresaName").toString();

                    Log.info("roles :" + roles);
                    Iterator it = null;
                    if (roles.contains("SUPER-ADMIN") || roles.contains("MEDICO") || roles.contains("ADMIN")) {
                        if (accion.equalsIgnoreCase(LE_TABLA)) {
                            /*todos*/
                            it = LFExamenes.selectExamenesAll(empresa);
                        } else if (accion.equalsIgnoreCase(CE_EXAMENES_PROFILE) || accion.equalsIgnoreCase(LE_BY_PACIENTE_TABLA)) {
                            /*Por usuario*/
                            it = LFExamenes.selectExamenesbyPaciente(Paciente, empresa);
                        }

                        List<Examenes> l = new ArrayList();
                        while (it.hasNext()) {
                            Registro reg = (Registro) it.next();
                            Examenes s = new Examenes();

                            s.setExa_c_name(reg.get(RFExamenes.exa_c_name));
                            s.setExa_c_numuser_paciente(reg.get(RFExamenes.exa_c_numuser_paciente));
                            s.setExa_c_obs(reg.get(RFExamenes.exa_c_obs));
                            s.setExa_n_ultmod_numuser(reg.get(RFExamenes.exa_n_ultmod_numuser));
                            s.setExa_c_url(reg.get(RFExamenes.exa_c_url));
                            s.setExa_d_ultmod_date(reg.get(RFExamenes.exa_d_ultmod_date));
                            s.setExa_n_id(reg.get(RFExamenes.exa_n_id));
                            s.setExa_c_ultmod_username(reg.get(RFExamenes.exa_c_ultmod_username));
                            s.setExa_c_paciente_name(reg.get(RFExamenes.exa_c_paciente_name));

                            l.add(s);

                        }
                        if (l.size() > 0) {
                            map.put("Examenes", l);
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
