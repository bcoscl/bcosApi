/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.bcos.get;

import cl.bcos.Jwt.ImplementacionJWT;
import cl.bcos.Jwt.ValidarTokenJWT;
import cl.bcos.LF.LFSuscripcion;
import cl.bcos.RF.RFSuscripcion;
import cl.bcos.data.Registro;
import cl.bcos.entity.suscripciones;
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
public class ApiListarSuscripciones extends ServerResource {

    private static final Logger Log = Logger.getLogger(ApiListarSuscripciones.class);

    private ImplementacionJWT jwt = null;
    private Map s = new HashMap();
    private final String ERROR_TOKEN = "TOKEN_NO_VALIDO";
    private static final String LISTAR_SELECT = "LS-SELECT";
    private static final String LISTAR_SELECT_BY = "LS-SELECT-BY-EMPRESA";

    public ApiListarSuscripciones() {
        jwt = new ImplementacionJWT();
    }

    @Post
    public Representation getSuscripciones() {
        Log.debug(Thread.currentThread().getStackTrace()[1].getMethodName());
        Status status = null;
        String message = "ok";
        Gson gson = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create();
        Map map = new HashMap();

        //String nombre_plan = getQuery().getValues("planName");
        //String numero_maximo = getQuery().getValues("userMax");
        String token = getQuery().getValues("token");
        String accion = getQuery().getValues("accion");
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
                    Iterator it = null;
                    if (roles.contains("SUPER-ADMIN")) {

                        if (accion.equalsIgnoreCase(LISTAR_SELECT_BY)) {
                            it = LFSuscripcion.selectSuscripcionesbyempresa(empresa);
                        } else {
                            it = LFSuscripcion.selectSuscripciones();
                        }

                        List<suscripciones> l = new ArrayList();
                        while (it.hasNext()) {
                            Registro reg = (Registro) it.next();
                            suscripciones s = new suscripciones();

                            s.setEmail(reg.get(RFSuscripcion.email));
                            s.setEstado(reg.get(RFSuscripcion.estado));
                            s.setFecha_creacion(reg.get(RFSuscripcion.fecha_creacion));
                            s.setFecha_inicio(reg.get(RFSuscripcion.fecha_inicio));
                            s.setId(reg.get(RFSuscripcion.id));
                            s.setNombre_contacto(reg.get(RFSuscripcion.nombre_contacto));
                            s.setNombre_empresa(reg.get(RFSuscripcion.nombre_empresa));
                            s.setNombre_plan(reg.get(RFSuscripcion.nombre_plan));
                            s.setNombre_usuario_creador(reg.get(RFSuscripcion.nombre_usuario_creador));
                            s.setTelefono(reg.get(RFSuscripcion.telefono));
                            s.setUsermax(reg.get(RFSuscripcion.usermax));
                            s.setUsuario_creador(reg.get(RFSuscripcion.usuario_creador));

                            l.add(s);

                        }
                        if (l.size() > 0) {
                            map.put("suscripciones", l);
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
