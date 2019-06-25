/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.bcos.put;

import cl.bcos.Jwt.ImplementacionJWT;
import cl.bcos.Jwt.ValidarTokenJWT;
import cl.bcos.LF.LFConsultas;
import cl.bcos.LF.LFEvaluaciones;
import cl.bcos.LF.LFFichas;
import cl.bcos.LF.LFPaciente;
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
public class ApiCrearEvaluacion extends ServerResource {

    private static final Logger Log = Logger.getLogger(ApiCrearEvaluacion.class);

    private ImplementacionJWT jwt = null;
    private Map s = new HashMap();
    private final String ERROR_TOKEN = "TOKEN_NO_VALIDO";
    private final String INSERT_OK = "INSERT_OK";
    private final String INSERT_NO_OK = "INSERT_NO_OK";
    private final String CREATE = "PUP-EVA-CREATE";

    public ApiCrearEvaluacion() {
        jwt = new ImplementacionJWT();
    }

    @Post
    public Representation crearUsuario() {
        Log.debug(Thread.currentThread().getStackTrace()[1].getMethodName());
        String path = getRequest().getResourceRef().getHostIdentifier() + getRequest().getResourceRef().getPath();
        Log.info("path : " + path);

        Status status = null;
        String message = "ok";
        Gson gson = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create();
        Map map = new HashMap();

        String rowFichaId = "";
        String rowPacienteId = "";
        String empresa = "";

        String accion = getQuery().getValues("accion");
        String eva_paciente = getQuery().getValues("eva_paciente");
        String eva_fecha = getQuery().getValues("eva_fecha");
        String eva_talla = getQuery().getValues("eva_talla");
        String eva_peso = getQuery().getValues("eva_peso");
        String eva_fat = getQuery().getValues("eva_fat");
        String eva_fatv = getQuery().getValues("eva_fatv");
        String eva_musc = getQuery().getValues("eva_musc");
        String eva_obs = getQuery().getValues("eva_obs");

        String token = getQuery().getValues("token");
        String empresasession = getQuery().getValues("empresasession");
             
              
        
        Log.info("accion :" + accion);
        Log.info("eva_paciente :" + eva_paciente);
        Log.info("eva_fecha :" + eva_fecha);
        Log.info("eva_talla :" + eva_talla);
        Log.info("eva_peso :" + eva_peso);
        Log.info("eva_fat :" + eva_fat);
        Log.info("eva_fatv :" + eva_fatv);
        Log.info("eva_musc :" + eva_musc);
        Log.info("eva_obs :" + eva_obs);

        Log.info("token : " + token);

        ValidarTokenJWT validaJWT = jwt.getJwt();
        try {
            if (token != null && !token.equals("") && validaJWT.validarTokenRS(token)) {
                try {

                    String usuario_creador = jwt.getJwt().getValue("numUser").toString();
                    String nombre_usuario = jwt.getJwt().getValue("name").toString();
                    String apellido_usuario = jwt.getJwt().getValue("LastName").toString();
                    String nombre_completo = nombre_usuario + " " + apellido_usuario;
                    empresa = jwt.getJwt().getValue("empresaName").toString();
                    String roles = jwt.getJwt().getValue("Roles").toString();

                    if (roles.contains("SUPER-ADMIN")) {
                        empresa = empresasession;
                    }
                    Log.info("empresa :" + empresa);

                    Log.info("usuario Creador:" + usuario_creador);
                    if (roles.contains("SUPER-ADMIN") || roles.contains("ADMIN") || roles.contains("MEDICO")) {
                        switch (accion) {

                            case CREATE:

                                if (LFEvaluaciones.insertEvaluacion(eva_paciente,
                                        eva_fecha,
                                        eva_talla,
                                        eva_peso,
                                        eva_fat,
                                        eva_fatv,
                                        eva_musc,
                                        eva_obs,
                                        usuario_creador,
                                        nombre_completo,
                                        empresa) == 1) {

                                    Log.info("Insert OK");
                                    status = Status.SUCCESS_OK;
                                    message = INSERT_OK;

                                } else {

                                    Log.info("Error de insercion");

                                    message = INSERT_NO_OK;
                                    status = Status.CLIENT_ERROR_BAD_REQUEST;

                                }
                                break;
                            default:
                                Log.error("no Soportada :" + accion);
                        }
                    } else {
                        status = Status.CLIENT_ERROR_UNAUTHORIZED;
                        Log.error("Perfil sin acceso");
                        message = ERROR_TOKEN;
                    }
                } catch (Exception e) {
                    Log.error("getMessage :" + e.getMessage());
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
