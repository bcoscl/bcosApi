/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.bcos.option;

import cl.bcos.Jwt.ImplementacionJWT;
import cl.bcos.Jwt.ValidarTokenJWT;
import cl.bcos.LF.LFAttentionList;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.HashMap;
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
public class ApiUpdateAttentionList extends ServerResource {

    private static final Logger Log = Logger.getLogger(ApiUpdateAttentionList.class);

    private ImplementacionJWT jwt = null;
    private Map s = new HashMap();
    private final String ERROR_TOKEN = "TOKEN_NO_VALIDO";

    public ApiUpdateAttentionList() {
        jwt = new ImplementacionJWT();
    }

    @Post
    public Representation updateSucursales() {
        Log.debug(Thread.currentThread().getStackTrace()[1].getMethodName());
        Status status = null;
        String message = "ok";
        Gson gson = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create();
        Map map = new HashMap();

        String row = getQuery().getValues("row");
        String accion = getQuery().getValues("accion");

        String token = getQuery().getValues("token");

        Log.info("row :" + row);
        Log.info("accion :" + accion);

        Log.info("token : " + token);

        ValidarTokenJWT validaJWT = jwt.getJwt();
        try {
            if (token != null && !token.equals("") && validaJWT.validarTokenRS(token)) {
                try {

                    String usuario_creador = jwt.getJwt().getValue("numUser").toString();
                    String nombre_usuario = jwt.getJwt().getValue("name").toString();
                    String apellido_usuario = jwt.getJwt().getValue("LastName").toString();
                    String nombre_completo = nombre_usuario + " " + apellido_usuario;
                    String empresa = jwt.getJwt().getValue("empresaName").toString();

                    Log.info(usuario_creador);

                    if (accion.equalsIgnoreCase("AT-DELETE_FROM_LIST")) {
                        LFAttentionList.deleteFromAttentionList(row, empresa);

                        Log.info("UPDATE OK");
                        status = Status.SUCCESS_OK;
                        message = "UPDATE_OK";
                    } else if (accion.equalsIgnoreCase("AT-SEND_TO_FINAL_LIST")) {
                        LFAttentionList.moverAlfinal(row, empresa);

                        Log.info("UPDATE OK");
                        status = Status.SUCCESS_OK;
                        message = "UPDATE_OK";
                    } else {

                        Log.info("Error de insercion");
                        message = "UPDATE_NO_OK";
                        status = Status.CLIENT_ERROR_BAD_REQUEST;

                    }

                    /* if ("CLAVE OK".equals(arr[0].trim())) {
                    
                    map.put("CORREO", arr[14].trim());
                    map.put("POSLOCAL", arr[15].trim());
                    map.put("ESTADO", arr[16].trim());
                }*/
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
