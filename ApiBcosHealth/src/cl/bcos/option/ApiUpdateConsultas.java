/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.bcos.option;

import cl.bcos.Jwt.ImplementacionJWT;
import cl.bcos.Jwt.ValidarTokenJWT;
import cl.bcos.LF.LFConsultas;
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
public class ApiUpdateConsultas extends ServerResource {

    private static final Logger Log = Logger.getLogger(ApiUpdateConsultas.class);

    private ImplementacionJWT jwt = null;
    private Map s = new HashMap();
    private final String ERROR_TOKEN = "TOKEN_NO_VALIDO";

    private final String UPDATE_CONSULTA_PROFILE = "PUP-UPDATE-CONSULTA";
    private final String DELETE_CONSULTA_PROFILE = "PUP-DELETE-CONSULTA";

    public ApiUpdateConsultas() {
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
        String ConsultaRowId = getQuery().getValues("ConsultaRowId");
        String Consulta_titulo = getQuery().getValues("Consulta_titulo");
        String Consulta_obs = getQuery().getValues("Consulta_obs");
        String consult_c_createdate = getQuery().getValues("fechaCreacion");
       
        String token = getQuery().getValues("token");
        String empresasession = getQuery().getValues("empresasession");
        
        
        Consulta_obs = Consulta_obs.replace("\n", " - ");
        Consulta_obs = Consulta_obs.replace(" -  - ", " - ");

        Log.info("accion :" + accion);
        Log.info("ConsultaRowId :" + ConsultaRowId);
        Log.info("Consulta_titulo :" + Consulta_titulo);
        Log.info("Consulta_obs :" + Consulta_obs);
        Log.info("consult_c_createdate :" + consult_c_createdate);
        

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

                        Log.info("usuario creador : " + usuario_creador);

                        if (accion.equalsIgnoreCase(UPDATE_CONSULTA_PROFILE)) {

                            if (LFConsultas.updateConsultas(
                                    ConsultaRowId,
                                    Consulta_titulo,
                                    Consulta_obs,
                                    usuario_creador,
                                    nombre_completo,
                                    empresa,
                                    consult_c_createdate) == 1) {

                                Log.info("UPDATE OK");
                                status = Status.SUCCESS_OK;
                                message = "UPDATE_OK";
                            } else {
                                Log.info("Error de Udpate/Delete");
                                message = "UPDATE_NO_OK";
                                status = Status.CLIENT_ERROR_BAD_REQUEST;
                            }
                        } else if (accion.equalsIgnoreCase(DELETE_CONSULTA_PROFILE)) {

                            if (LFConsultas.deleteConsultas(ConsultaRowId, empresa) == 1) {

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
