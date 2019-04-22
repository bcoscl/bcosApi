/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.bcos.option;

import cl.bcos.Jwt.ImplementacionJWT;
import cl.bcos.Jwt.ValidarTokenJWT;
import cl.bcos.LF.LFSuscripcion;
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
public class ApiUpdateSuscripcion extends ServerResource {

    private static final Logger Log = Logger.getLogger(ApiUpdateSuscripcion.class);

    private ImplementacionJWT jwt = null;
    private Map s = new HashMap();
    private final String ERROR_TOKEN = "TOKEN_NO_VALIDO";

    public ApiUpdateSuscripcion() {
        jwt = new ImplementacionJWT();
    }

    @Post
    public Representation updateSuscripcion() {
        Log.debug(Thread.currentThread().getStackTrace()[1].getMethodName());
        Status status = null;
        String message = "ok";
        Gson gson = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create();
        Map map = new HashMap();
        
        String id = getQuery().getValues("id");
        String accion = getQuery().getValues("accion");
        
        String nombre_empresa = getQuery().getValues("nombre_empresa");
        String contacto_empresa = getQuery().getValues("contacto_empresa");
        String email_contacto = getQuery().getValues("email_contacto");
        String numero_telefono = getQuery().getValues("numero_telefono");
        String fecha_inicio = getQuery().getValues("fecha_inicio");
        String select_plan_code = getQuery().getValues("select_plan_code");
        String select_plan_name = getQuery().getValues("select_plan_name");
        String checkbox_activo = getQuery().getValues("checkbox_activo");

        String token = getQuery().getValues("token");

        Log.info("id :" + id);
        Log.info("accion :" + accion);
        Log.info("nombre_empresa :" + nombre_empresa);
        Log.info("contacto_empresa :" + contacto_empresa);
        Log.info("email_contacto :" + email_contacto);
        Log.info("numero_telefono :" + numero_telefono);
        Log.info("fecha_inicio :" + fecha_inicio);
        Log.info("select_plan_code :" + select_plan_code);
        Log.info("select_plan_name :" + select_plan_name);
        Log.info("checkbox_activo :" + checkbox_activo);

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
                    
                    if(accion.equalsIgnoreCase("ESTADO")){
                        LFSuscripcion.updateEstado(id,checkbox_activo,nombre_completo);
                    
                        Log.info("UPDATE OK");
                        status = Status.SUCCESS_OK;
                        message = "UPDATE_OK";
                    }
//                    if (LFSuscripcion.insertSuscripcion(nombre_empresa,
//                            contacto_empresa, email_contacto, numero_telefono, 
//                            fecha_inicio, select_plan_code, select_plan_name, 
//                            checkbox_activo, nombre_completo, usuario_creador) == 1) {
//
//                        Log.info("UPDATE OK");
//                        status = Status.SUCCESS_OK;
//                        message = "UPDATE_OK";
//
//                    } 
                    else {

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
