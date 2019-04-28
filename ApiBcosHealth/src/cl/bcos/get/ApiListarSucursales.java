/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.bcos.get;

import cl.bcos.Jwt.ImplementacionJWT;
import cl.bcos.Jwt.ValidarTokenJWT;
import cl.bcos.LF.LFSucursales;
import cl.bcos.RF.RFSucursales;
import cl.bcos.data.Registro;
import cl.bcos.entity.sucursales;
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
public class ApiListarSucursales extends ServerResource {

    private static final Logger Log = Logger.getLogger(ApiListarSucursales.class);

    private ImplementacionJWT jwt = null;
    private Map s = new HashMap();
    private final String ERROR_TOKEN = "TOKEN_NO_VALIDO";
    private static final String LISTAR_SELECT_MULTIPLE_BY_ACTIVE = "LS-SELECT-MULT-BY-ACTIVE";

    public ApiListarSucursales() {
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

        //Log.info("nombrePlan : " + nombre_plan);
        //Log.info("userMax : " + numero_maximo);
        Log.info("token : " + token);
        Log.info("accion : " + accion);
        
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
                    Iterator it = null;
                    Log.info("roles :" + roles);

                    if (roles.contains("SUPER-ADMIN")||roles.contains("ADMIN")) {

                        if (accion.equalsIgnoreCase(LISTAR_SELECT_MULTIPLE_BY_ACTIVE)) {
                            it = LFSucursales.selectSucursalesActive(empresa);
                        } else {
                            it = LFSucursales.selectSucursales(empresa);
                        }

                        List<sucursales> l = new ArrayList();
                        while (it.hasNext()) {
                            Registro reg = (Registro) it.next();
                            sucursales s = new sucursales();

                            s.setSuc_c_comuna(reg.get(RFSucursales.suc_c_comuna));
                            s.setSuc_c_contacname(reg.get(RFSucursales.suc_c_contacname));
                            s.setSuc_c_createuser(reg.get(RFSucursales.suc_c_createuser));
                            s.setSuc_c_createusername(reg.get(RFSucursales.suc_c_createusername));
                            s.setSuc_c_email(reg.get(RFSucursales.suc_c_email));
                            s.setSuc_c_estado(reg.get(RFSucursales.suc_c_estado));
                            s.setSuc_c_nombre(reg.get(RFSucursales.suc_c_nombre));
                            s.setSuc_c_nombre_empresa(reg.get(RFSucursales.suc_c_nombre_empresa));
                            s.setSuc_c_telefono(reg.get(RFSucursales.suc_c_telefono));
                            s.setSuc_d_createdate(reg.get(RFSucursales.suc_d_createdate));
                            s.setSuc_n_cod(reg.get(RFSucursales.suc_n_cod));
                            s.setSuc_n_cod_empresa(reg.get(RFSucursales.suc_n_cod_empresa));

                            l.add(s);

                        }
                        if (l.size() > 0) {
                            map.put("sucursales", l);
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
