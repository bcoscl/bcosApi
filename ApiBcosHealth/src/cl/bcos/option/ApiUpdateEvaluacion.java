/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.bcos.option;

import cl.bcos.Jwt.ImplementacionJWT;
import cl.bcos.Jwt.ValidarTokenJWT;
import cl.bcos.LF.LFConsultas;
import cl.bcos.LF.LFEvaluaciones;
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
public class ApiUpdateEvaluacion extends ServerResource {

    private static final Logger Log = Logger.getLogger(ApiUpdateEvaluacion.class);

    private ImplementacionJWT jwt = null;
    private Map s = new HashMap();
    private final String ERROR_TOKEN = "TOKEN_NO_VALIDO";

    private final String UPDATE = "PUP-UPDATE-EVA";
    private final String DELETE = "PUP-DELETE-EVA";

    public ApiUpdateEvaluacion() {
        jwt = new ImplementacionJWT();
    }

    @Post
    public Representation UpdateConsulta() {
        Log.debug(Thread.currentThread().getStackTrace()[1].getMethodName());

        String path = getRequest().getResourceRef().getHostIdentifier() + getRequest().getResourceRef().getPath();
        Log.info("path : " + path);

        Status status = null;
        String message = "ok";
        Gson gson = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create();
        Map map = new HashMap();

        String accion = getQuery().getValues("accion");
        String eva_id = getQuery().getValues("eva_id");
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

        eva_obs = eva_obs.replace("\n", " - ");
        eva_obs = eva_obs.replace(" -  - ", " - ");

        Log.info("accion :" + accion);
        Log.info("eva_id :" + eva_id);
        Log.info("eva_paciente :" + eva_paciente);
        Log.info("eva_fecha :" + eva_fecha);
        Log.info("eva_talla :" + eva_talla);
        Log.info("eva_peso :" + eva_peso);
        Log.info("eva_fat :" + eva_fat);
        Log.info("eva_fatv :" + eva_fatv);
        Log.info("eva_musc :" + eva_musc);
        Log.info("eva_obs :" + eva_obs);

        Log.info("token : " + token);
        Log.info("token bearer:" + token);

        ValidarTokenJWT validaJWT = jwt.getJwt();
        try {
            if (token != null && !token.equals("") && validaJWT.validarTokenRS(token)) {
                try {

                    String usuario_creador = jwt.getJwt().getValue("numUser").toString();
                    String nombre_usuario = jwt.getJwt().getValue("name").toString();
                    String apellido_usuario = jwt.getJwt().getValue("LastName").toString();
                    String nombre_completo = nombre_usuario + " " + apellido_usuario;
                    String empresa = jwt.getJwt().getValue("empresaName").toString();
                    String roles = jwt.getJwt().getValue("Roles").toString();

                    if (roles.contains("SUPER-ADMIN")) {
                        empresa = empresasession;
                    }
                    Log.info("empresa :" + empresa);

                    if (roles.contains("SUPER-ADMIN") || roles.contains("MEDICO") || roles.contains("ADMIN") || roles.contains("RECEPCION")) {

                        Log.info("usaurio creador : " + usuario_creador);

                        if (accion.equalsIgnoreCase(UPDATE)) {

                            if (LFEvaluaciones.updateEvaluacion(eva_id, /*String eva_date,*/
                                    eva_talla, eva_peso,
                                    eva_fat, eva_fatv,
                                    eva_musc, eva_obs, usuario_creador,
                                    nombre_completo, empresa) == 1) {

                                Log.info("UPDATE OK");
                                status = Status.SUCCESS_OK;
                                message = "UPDATE_OK";
                            } else {
                                Log.info("Error de Udpate/Delete");
                                message = "UPDATE_NO_OK";
                                status = Status.CLIENT_ERROR_BAD_REQUEST;
                            }
                        } else if (accion.equalsIgnoreCase(DELETE)) {

                            if (LFEvaluaciones.deleteEvaluacion(eva_id, empresa) == 1) {

                                Log.info("UPDATE OK");
                                status = Status.SUCCESS_OK;
                                message = "UPDATE_OK";
                            } else {
                                Log.info("Error de Udpate/Delete");
                                message = "UPDATE_NO_OK";
                                status = Status.CLIENT_ERROR_BAD_REQUEST;
                            }
                        } else {

                            Log.info("No Soportada");
                            Log.info("Error de Udpate/Delete");
                            message = "UPDATE_NO_OK";
                            status = Status.CLIENT_ERROR_BAD_REQUEST;

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
