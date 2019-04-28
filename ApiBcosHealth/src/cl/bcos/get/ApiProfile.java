/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.bcos.get;

import cl.bcos.Jwt.ImplementacionJWT;
import cl.bcos.Jwt.ValidarTokenJWT;
import cl.bcos.LF.LFUsuarios;
import cl.bcos.RF.RFUsuarios;
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
public class ApiProfile extends ServerResource {

    private static final Logger Log = Logger.getLogger(ApiProfile.class);

    private ImplementacionJWT jwt = null;
    private Map s = new HashMap();
    private final String ERROR_TOKEN = "TOKEN_NO_VALIDO";

    public ApiProfile() {
        jwt = new ImplementacionJWT();
    }

    @Post
    public Representation getSuscripciones() {
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

        String path = getRequest().getResourceRef().getHostIdentifier() + getRequest().getResourceRef().getPath();
        Log.info("path : " + path);

        ValidarTokenJWT validaJWT = jwt.getJwt();
        try {
            if (token != null && !token.equals("") && validaJWT.validarTokenRS(token)) {
                try {
                    String roles = jwt.getJwt().getValue("Roles").toString();
                    if (roles.contains("SUPER-ADMIN") || roles.contains("MEDICO") || roles.contains("ADMIN") || roles.contains("RECEPCION")) {

                        if (accion.equalsIgnoreCase("CP-BYUSER")) {
                            //consulta  por usuario
                            Iterator it = LFUsuarios.getUserInformation(numuser);

                            while (it.hasNext()) {

                                Registro reg = (Registro) it.next();

                                prof.put("name", reg.get(RFUsuarios.user_c_nombres));
                                prof.put("LastName", reg.get(RFUsuarios.user_c_apellido));
                                prof.put("numUser", reg.get(RFUsuarios.user_c_numuser));
                                prof.put("Roles", reg.get(RFUsuarios.user_c_role));
                                prof.put("celular", reg.get(RFUsuarios.user_c_celular));
                                prof.put("nombreOficina", reg.get(RFUsuarios.user_c_nombre_oficina));
                                prof.put("email", reg.get(RFUsuarios.user_c_email));
                                prof.put("fechaCracion", reg.get(RFUsuarios.user_d_createdate));
                                prof.put("creadoPor", reg.get(RFUsuarios.user_c_createusername));
                                prof.put("imgUrl", reg.get(RFUsuarios.user_c_img));
                                prof.put("Profesion", reg.get(RFUsuarios.user_c_profesion));
                                prof.put("AboutMe", reg.get(RFUsuarios.user_c_obs));
                                prof.put("empresaName", reg.get(RFUsuarios.user_c_empresaname));
                                prof.put("numUserCreador", reg.get(RFUsuarios.user_n_createuser));
                                prof.put("CodEmpresa", reg.get(RFUsuarios.user_n_cod_empresa));
                                prof.put("userId", reg.get(RFUsuarios.user_n_iduser));
                                prof.put("pUserId", reg.get(RFUsuarios.pass_n_id));
                                prof.put("status", reg.get(RFUsuarios.pass_c_activo));
                            }
                        } else if (accion.equalsIgnoreCase("CP-PERFIL")) {

                            prof.put("name", jwt.getJwt().getValue("name").toString());
                            prof.put("LastName", jwt.getJwt().getValue("LastName").toString());
                            prof.put("numUser", jwt.getJwt().getValue("numUser").toString());
                            prof.put("Roles", jwt.getJwt().getValue("Roles").toString());
                            prof.put("celular", jwt.getJwt().getValue("celular").toString());
                            prof.put("nombreOficina", jwt.getJwt().getValue("nombreOficina").toString());
                            prof.put("email", jwt.getJwt().getValue("email").toString());
                            prof.put("fechaCracion", jwt.getJwt().getValue("fechaCracion").toString());
                            prof.put("creadoPor", jwt.getJwt().getValue("creadoPor").toString());
                            prof.put("imgUrl", jwt.getJwt().getValue("imgUrl").toString());
                            prof.put("Profesion", jwt.getJwt().getValue("Profesion").toString());
                            prof.put("AboutMe", jwt.getJwt().getValue("AboutMe").toString());
                            prof.put("empresaName", jwt.getJwt().getValue("empresaName").toString());
                            prof.put("numUserCreador", jwt.getJwt().getValue("numUserCreador").toString());
                            prof.put("CodEmpresa", jwt.getJwt().getValue("CodEmpresa").toString());
                            prof.put("userId", jwt.getJwt().getValue("userId").toString());
                            // String nombre_usuario = jwt.getJwt().getValue("name").toString();
                            // String apellido_usuario = jwt.getJwt().getValue("LastName").toString();
                            // if (roles.contains("SUPER-ADMIN")) {
                        }
                        map.put("Profile", prof);

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
