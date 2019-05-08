/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.bcos.get;

import cl.bcos.Jwt.ImplementacionJWT;
import cl.bcos.Jwt.ValidarTokenJWT;
import cl.bcos.LF.LFConsultas;
import cl.bcos.LF.LFPaciente;
import cl.bcos.RF.RFConsultas;
import cl.bcos.RF.RFPaciente;
import cl.bcos.data.Registro;
import cl.bcos.entity.Consultas;
import cl.bcos.entity.ExportarFichas;
import cl.bcos.entity.Paciente;
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
public class ApiExportFichas extends ServerResource {

    private static final Logger Log = Logger.getLogger(ApiExportFichas.class);

    private ImplementacionJWT jwt = null;
    private Map s = new HashMap();
    private final String ERROR_TOKEN = "TOKEN_NO_VALIDO";

    public ApiExportFichas() {
        jwt = new ImplementacionJWT();
    }

    @Post
    public Representation ExportFichas() {
        Log.debug(Thread.currentThread().getStackTrace()[1].getMethodName());
        Status status = null;
        String message = "ok";
        Gson gson = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create();
        Map map = new HashMap();

        String accion = getQuery().getValues("accion");
        String fichas = getQuery().getValues("fichas");// numuser Paciente
        String token = getQuery().getValues("token");
        String empresasession = getQuery().getValues("empresasession");

        Log.info("accion : " + accion);
        Log.info("fichas : " + fichas);
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

                        Iterator itP = LFPaciente.selectPacientesBynumuser(empresa,fichas);
                        List<ExportarFichas> exportarFichas = new ArrayList();

                        while (itP.hasNext()) {
                            Registro reg = (Registro) itP.next();
                            Paciente paciente = new Paciente();

                            //if (paciente.getC_numuser()!=null && fichas.contains(paciente.getC_numuser())) {

                                ExportarFichas exp = new ExportarFichas();

                                paciente.setC_apellidos(reg.get(RFPaciente.paciente_c_apellidos));
                                paciente.setC_celular(reg.get(RFPaciente.paciente_c_celular));
                                paciente.setC_createuser(reg.get(RFPaciente.paciente_c_createuser));
                                paciente.setC_direccion(reg.get(RFPaciente.paciente_c_direccion));
                                paciente.setC_email(reg.get(RFPaciente.paciente_c_email));
                                paciente.setC_estado_civil(reg.get(RFPaciente.paciente_c_estado_civil));
                                paciente.setC_img(reg.get(RFPaciente.paciente_c_img));
                                paciente.setC_numuser(reg.get(RFPaciente.paciente_c_numuser));

                                paciente.setC_obs(reg.get(RFPaciente.paciente_c_obs));
                                paciente.setC_pacientename(reg.get(RFPaciente.paciente_c_pacientename));
                                paciente.setC_prevision(reg.get(RFPaciente.paciente_c_prevision));
                                paciente.setC_profesion(reg.get(RFPaciente.paciente_c_profesion));
                                paciente.setC_sexo(reg.get(RFPaciente.paciente_c_sexo));
                                paciente.setD_createdate(reg.get(RFPaciente.paciente_d_createdate));
                                paciente.setN_id(reg.get(RFPaciente.paciente_n_id));
                                paciente.setD_fechaNacimiento(reg.get(RFPaciente.paciente_d_fechaNacimiento));
                                paciente.setN_edad(reg.get(RFPaciente.paciente_n_edad));

                                String Paciente = reg.get(RFPaciente.paciente_c_numuser);

                                Iterator it = LFConsultas.selectConsultas(Paciente, empresa);
                                List<Consultas> consultaList = new ArrayList();
                                while (it.hasNext()) {
                                    Registro reg2 = (Registro) it.next();
                                    Consultas consulta = new Consultas();

                                    consulta.setConsult_c_numuser_paciente(reg2.get(RFConsultas.consult_c_numuser_paciente));
                                    consulta.setConsult_c_obs_consulta(reg2.get(RFConsultas.consult_c_obs_consulta));
                                    consulta.setConsult_c_paciente_name(reg2.get(RFConsultas.consult_c_paciente_name));
                                    consulta.setConsult_c_tipoconsulta(reg2.get(RFConsultas.consult_c_tipoconsulta));
                                    consulta.setConsult_c_titulo(reg2.get(RFConsultas.consult_c_titulo));
                                    consulta.setConsult_c_ultmod_numuser(reg2.get(RFConsultas.consult_c_ultmod_numuser));
                                    consulta.setConsult_c_ultmod_username(reg2.get(RFConsultas.consult_c_ultmod_username));
                                    consulta.setConsult_d_ultmod_date(reg2.get(RFConsultas.consult_d_ultmod_date));
                                    consulta.setConsult_n_id(reg2.get(RFConsultas.consult_n_id));
                                    consulta.setConsult_d_createdate(reg2.get(RFConsultas.consult_d_createdate));

                                    consultaList.add(consulta);

                                }
                                exp.setPaciente(paciente);
                                exp.setConsultas(consultaList);
                                exportarFichas.add(exp);
                           // }

                        }

                        if (exportarFichas.size() > 0) {
                            map.put("ExportarFichas", exportarFichas);
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
