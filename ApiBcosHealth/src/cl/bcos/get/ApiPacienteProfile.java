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
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.HashMap;
import java.util.Iterator;
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
public class ApiPacienteProfile extends ServerResource {

    private static final Logger Log = Logger.getLogger(ApiPacienteProfile.class);

    private ImplementacionJWT jwt = null;
    private Map s = new HashMap();
    private final String ERROR_TOKEN = "TOKEN_NO_VALIDO";

    public ApiPacienteProfile() {
        jwt = new ImplementacionJWT();
    }

    @Post
    public Representation getPacienteProfile() {
        Log.debug(Thread.currentThread().getStackTrace()[1].getMethodName());
        Status status = null;
        String message = "ok";
        Gson gson = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create();
        Map map = new HashMap();
        Map prof = new HashMap();

        String numuser = getQuery().getValues("numUser");
        String accion = getQuery().getValues("accion");
        String token = getQuery().getValues("token");

        //Log.info("nombrePlan : " + nombre_plan);
        //Log.info("userMax : " + numero_maximo);
        Log.info("numuser : " + numuser);
        Log.info("accion : " + accion);
        Log.info("token : " + token);

        ValidarTokenJWT validaJWT = jwt.getJwt();
        try {
            if (token != null && !token.equals("") && validaJWT.validarTokenRS(token)) {
                try {
                    if (accion.equalsIgnoreCase("CP-BYUSER")) {
                        //consulta  por usuario
                        
                        String empresa = jwt.getJwt().getValue("empresaName").toString();
                        
                        Iterator it = LFPaciente.getUserInformation(numuser,empresa);

                        while (it.hasNext()) {

                            Registro reg = (Registro) it.next();

                            prof.put("n_id", reg.get(RFPaciente.paciente_n_id));
                            prof.put("c_numuser", reg.get(RFPaciente.paciente_c_numuser));
                            prof.put("c_direccion", reg.get(RFPaciente.paciente_c_direccion));
                            prof.put("c_prevision", reg.get(RFPaciente.paciente_c_prevision));
                            prof.put("c_profesion", reg.get(RFPaciente.paciente_c_profesion));
                            prof.put("c_estado_civil", reg.get(RFPaciente.paciente_c_estado_civil));
                            prof.put("c_obs", reg.get(RFPaciente.paciente_c_obs));
                            prof.put("c_pacientename", reg.get(RFPaciente.paciente_c_pacientename));
                            prof.put("c_apellidos", reg.get(RFPaciente.paciente_c_apellidos));
                            prof.put("c_email", reg.get(RFPaciente.paciente_c_email));
                            prof.put("c_celular", reg.get(RFPaciente.paciente_c_celular));
                            prof.put("c_createusername", reg.get(RFPaciente.paciente_c_createusername));
                            prof.put("d_createdate", reg.get(RFPaciente.paciente_d_createdate));
                            prof.put("c_createuser", reg.get(RFPaciente.paciente_c_createuser));
                            prof.put("c_img", reg.get(RFPaciente.paciente_c_img));
                            prof.put("c_sexo", reg.get(RFPaciente.paciente_c_sexo));
                            prof.put("d_fechaNacimiento", reg.get(RFPaciente.paciente_d_fechaNacimiento));
                            prof.put("n_edad", reg.get(RFPaciente.paciente_n_edad));
                        }
                    } 
//                        else if (accion.equalsIgnoreCase("CP-PERFIL")) {
//
//                        prof.put("name", jwt.getJwt().getValue("name").toString());
//                        prof.put("LastName", jwt.getJwt().getValue("LastName").toString());
//                        prof.put("numUser", jwt.getJwt().getValue("numUser").toString());
//                        prof.put("Roles", jwt.getJwt().getValue("Roles").toString());
//                        prof.put("celular", jwt.getJwt().getValue("celular").toString());
//                        prof.put("nombreOficina", jwt.getJwt().getValue("nombreOficina").toString());
//                        prof.put("email", jwt.getJwt().getValue("email").toString());
//                        prof.put("fechaCracion", jwt.getJwt().getValue("fechaCracion").toString());
//                        prof.put("creadoPor", jwt.getJwt().getValue("creadoPor").toString());
//                        prof.put("imgUrl", jwt.getJwt().getValue("imgUrl").toString());
//                        prof.put("Profesion", jwt.getJwt().getValue("Profesion").toString());
//                        prof.put("AboutMe", jwt.getJwt().getValue("AboutMe").toString());
//                        prof.put("empresaName", jwt.getJwt().getValue("empresaName").toString());
//                        prof.put("numUserCreador", jwt.getJwt().getValue("numUserCreador").toString());
//                        prof.put("CodEmpresa", jwt.getJwt().getValue("CodEmpresa").toString());
//                        prof.put("userId", jwt.getJwt().getValue("userId").toString());
//                        // String nombre_usuario = jwt.getJwt().getValue("name").toString();
//                        // String apellido_usuario = jwt.getJwt().getValue("LastName").toString();
//                        // if (roles.contains("SUPER-ADMIN")) {
//                    }
                    map.put("Paciente", prof);

                    Log.info("SELECT OK");
                    Log.info("Paciente : "+prof.toString());
                    status = Status.SUCCESS_OK;
                    message = "SELECT_OK";

//                    } else {
//
//                        Log.info("EL perfil no tiene acceso");
//                        message = "SELECT_NO_OK";
//                        status = Status.CLIENT_ERROR_BAD_REQUEST;
//
//                    }
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
