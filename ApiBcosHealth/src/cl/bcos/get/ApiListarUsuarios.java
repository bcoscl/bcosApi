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
import cl.bcos.entity.Profile;
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
public class ApiListarUsuarios extends ServerResource {

    private static final Logger Log = Logger.getLogger(ApiListarUsuarios.class);

    private ImplementacionJWT jwt = null;
    private Map s = new HashMap();
    private final String ERROR_TOKEN = "TOKEN_NO_VALIDO";

    public ApiListarUsuarios() {
        jwt = new ImplementacionJWT();
    }

    @Post
    public Representation getUsuarios() {
        Log.debug(Thread.currentThread().getStackTrace()[1].getMethodName());
        Status status = null;
        String message = "ok";
        Gson gson = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create();
        Map map = new HashMap();

        //String nombre_plan = getQuery().getValues("planName");
        //String numero_maximo = getQuery().getValues("userMax");
        String token = getQuery().getValues("token");

        //Log.info("nombrePlan : " + nombre_plan);
        //Log.info("userMax : " + numero_maximo);
        Log.info("token : " + token);

        ValidarTokenJWT validaJWT = jwt.getJwt();
        try {
            if (token != null && !token.equals("") && validaJWT.validarTokenRS(token)) {
                try {

                    String roles = jwt.getJwt().getValue("Roles").toString();
                    // String nombre_usuario = jwt.getJwt().getValue("name").toString();
                    // String apellido_usuario = jwt.getJwt().getValue("LastName").toString();
                    String empresa = jwt.getJwt().getValue("empresaName").toString();

                    Log.info("roles :" + roles);

                    if (roles.contains("SUPER-ADMIN") || roles.contains("ADMIN")) {

                        Iterator it = LFUsuarios.selectUsuarios(empresa);
                        List<Profile> l = new ArrayList();
                        while (it.hasNext()) {
                            Registro reg = (Registro) it.next();
                            Profile s = new Profile();

                            s.setName(reg.get(RFUsuarios.user_c_nombres));
                            s.setLastName(reg.get(RFUsuarios.user_c_apellido));
                            s.setNumUser(reg.get(RFUsuarios.user_c_numuser));
                            s.setRoles(reg.get(RFUsuarios.user_c_role));
                            s.setCelular(reg.get(RFUsuarios.user_c_celular));
                            s.setNombreOficina(reg.get(RFUsuarios.user_c_nombre_oficina));
                            s.setEmail(reg.get(RFUsuarios.user_c_email));
                            s.setFechaCracion(reg.get(RFUsuarios.user_d_createdate));
                            //s.set(reg.get(RFUsuarios.user_c_createusername)); 
                            s.setImgUrl(reg.get(RFUsuarios.user_c_img));
                            s.setProfesion(reg.get(RFUsuarios.user_c_profesion));
                            s.setAboutMe(reg.get(RFUsuarios.user_c_obs));
                            s.setEmpresaName(reg.get(RFUsuarios.user_c_empresaname));
                            s.setNumUserCreador(reg.get(RFUsuarios.user_n_createuser));
                            s.setCodEmpresa(reg.get(RFUsuarios.user_n_cod_empresa));
                            s.setUserId(reg.get(RFUsuarios.user_n_iduser));
                            s.setpUserId(reg.get(RFUsuarios.pass_n_id));
                            s.setStatus(reg.get(RFUsuarios.pass_c_activo));

                            l.add(s);

                        }
                        if (l.size() > 0) {
                            map.put("Profile", l);
                        }
                        Log.info("SELECT OK");
                        status = Status.SUCCESS_OK;
                        message = "SELECT_OK";

                    } else {

                        Log.info("EL perfil no tiene acceso");
                        message = "SELECT_NO_OK";
                        status = Status.CLIENT_ERROR_BAD_REQUEST;

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
