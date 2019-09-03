/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.bcos.get;

import cl.bcos.Jwt.ImplementacionJWT;
import cl.bcos.Jwt.ValidarTokenJWT;
import cl.bcos.LF.LFEvaluaciones;
import cl.bcos.RF.RFEvaluaciones;
import cl.bcos.data.Registro;
import cl.bcos.entity.Evaluaciones;
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
public class ApiListarEvaluaciones extends ServerResource {

    private static final Logger Log = Logger.getLogger(ApiListarEvaluaciones.class);

    private ImplementacionJWT jwt = null;
    private Map s = new HashMap();
    private final String ERROR_TOKEN = "TOKEN_NO_VALIDO";

    public ApiListarEvaluaciones() {
        jwt = new ImplementacionJWT();
    }

    @Post
    public Representation getConsultas() {
        Log.debug(Thread.currentThread().getStackTrace()[1].getMethodName());
        Status status = null;
        String message = "ok";
        Gson gson = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create();
        Map map = new HashMap();

        String accion = getQuery().getValues("accion");
        String Paciente = getQuery().getValues("Paciente");// numuser Paciente
        String token = getQuery().getValues("token");
        String empresasession = getQuery().getValues("empresasession");

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
                    // String apellido_usuario = jwt.getJwt().getValue("LastName").toString();
                    String empresa = jwt.getJwt().getValue("empresaName").toString();

                    if (roles.contains("SUPER-ADMIN")) {
                        empresa = empresasession;
                    }
                    Log.info("empresa :" + empresa);

                    Log.info("roles :" + roles);

                    if (roles.contains("SUPER-ADMIN") || roles.contains("MEDICO") || roles.contains("ADMIN")) {

                        Iterator it = LFEvaluaciones.selectEvaluacion(Paciente, empresa);
                        List<Evaluaciones> l = new ArrayList();
                        while (it.hasNext()) {
                            Registro reg = (Registro) it.next();
                            Evaluaciones s = new Evaluaciones();

                            s.setEva_c_empresa(reg.get(RFEvaluaciones.eva_c_empresa));
                            s.setEva_c_numuser_paciente(reg.get(RFEvaluaciones.eva_c_numuser_paciente));
                            s.setEva_c_ultmod_numuser(reg.get(RFEvaluaciones.eva_c_ultmod_numuser));
                            s.setEva_c_ultmod_username(reg.get(RFEvaluaciones.eva_c_ultmod_username));
                            s.setEva_d_createdate(reg.get(RFEvaluaciones.eva_d_createdate));
                            s.setEva_d_eva_date(reg.get(RFEvaluaciones.eva_d_eva_date));
                            s.setEva_d_ultmod_date(reg.get(RFEvaluaciones.eva_d_ultmod_date));
                            s.setEva_n_fat(reg.get(RFEvaluaciones.eva_n_fat));
                            s.setEva_n_fatv(reg.get(RFEvaluaciones.eva_n_fatv));
                            s.setEva_n_id(reg.get(RFEvaluaciones.eva_n_id));
                            s.setEva_n_musc(reg.get(RFEvaluaciones.eva_n_musc));
                            s.setEva_n_obs_evaluacion(reg.get(RFEvaluaciones.eva_n_obs_evaluacion));
                            s.setEva_n_peso(reg.get(RFEvaluaciones.eva_n_peso));
                            s.setEva_n_talla(reg.get(RFEvaluaciones.eva_n_talla));
                            s.setEva_n_imc(reg.get(RFEvaluaciones.eva_n_imc));

                            l.add(s);

                        }
                        if (l.size() > 0) {
                            map.put("Evaluaciones", l);
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
