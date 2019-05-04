/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.bcos.get;

import cl.bcos.Jwt.ImplementacionJWT;
import cl.bcos.Jwt.ValidarTokenJWT;
import cl.bcos.LF.LFPaciente;
import cl.bcos.RF.RFPaciente;
import cl.bcos.data.Registro;
import cl.bcos.entity.Paciente;
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
public class ApiListarPacientes extends ServerResource {

    private static final Logger Log = Logger.getLogger(ApiListarPacientes.class);

    private ImplementacionJWT jwt = null;
    private Map s = new HashMap();
    private final String ERROR_TOKEN = "TOKEN_NO_VALIDO";

    public ApiListarPacientes() {
        jwt = new ImplementacionJWT();
    }

    @Post
    public Representation getPacientes() {
        Log.debug(Thread.currentThread().getStackTrace()[1].getMethodName());
        Status status = null;
        String message = "ok";
        Gson gson = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create();
        Map map = new HashMap();

        //String nombre_plan = getQuery().getValues("planName");
        //String numero_maximo = getQuery().getValues("userMax");
        String token = getQuery().getValues("token");
        String empresasession = getQuery().getValues("empresasession");

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

                    if (roles.contains("SUPER-ADMIN")) {
                        empresa = empresasession;
                    }
                    Log.info("empresa :" + empresa);

                    Log.info("roles :" + roles);

                    if (roles.contains("SUPER-ADMIN") || roles.contains("MEDICO") || roles.contains("ADMIN") || roles.contains("RECEPCION")) {

                        Iterator it = LFPaciente.selectPacientes(empresa);
                        List<Paciente> l = new ArrayList();
                        while (it.hasNext()) {
                            Registro reg = (Registro) it.next();
                            Paciente s = new Paciente();

                            s.setC_apellidos(reg.get(RFPaciente.paciente_c_apellidos));
                            s.setC_celular(reg.get(RFPaciente.paciente_c_celular));
                            s.setC_createuser(reg.get(RFPaciente.paciente_c_createuser));
                            s.setC_direccion(reg.get(RFPaciente.paciente_c_direccion));
                            s.setC_email(reg.get(RFPaciente.paciente_c_email));
                            s.setC_estado_civil(reg.get(RFPaciente.paciente_c_estado_civil));
                            s.setC_img(reg.get(RFPaciente.paciente_c_img));
                            s.setC_numuser(reg.get(RFPaciente.paciente_c_numuser));

                            s.setC_obs(reg.get(RFPaciente.paciente_c_obs));
                            s.setC_pacientename(reg.get(RFPaciente.paciente_c_pacientename));
                            s.setC_prevision(reg.get(RFPaciente.paciente_c_prevision));
                            s.setC_profesion(reg.get(RFPaciente.paciente_c_profesion));
                            s.setC_sexo(reg.get(RFPaciente.paciente_c_sexo));
                            s.setD_createdate(reg.get(RFPaciente.paciente_d_createdate));
                            s.setN_id(reg.get(RFPaciente.paciente_n_id));
                            s.setD_fechaNacimiento(reg.get(RFPaciente.paciente_d_fechaNacimiento));
                            s.setN_edad(reg.get(RFPaciente.paciente_n_edad));

                            l.add(s);

                        }
                        if (l.size() > 0) {
                            map.put("Paciente", l);
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
