/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.bcos.put;

import cl.bcos.BlowFish;
import cl.bcos.Jwt.ImplementacionJWT;
import cl.bcos.Jwt.ValidarTokenJWT;
import cl.bcos.LF.LFParams;
import cl.bcos.LF.LFSSO;
import cl.bcos.LF.LFUsuarios;
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
public class ApiCrearUsuarios extends ServerResource {

    private static final Logger Log = Logger.getLogger(ApiCrearUsuarios.class);

    private ImplementacionJWT jwt = null;
    private Map s = new HashMap();
    private final String ERROR_TOKEN = "TOKEN_NO_VALIDO";
    private final String INSERT_OK = "INSERT_OK";
    private final String INSERT_NO_OK = "INSERT_NO_OK";
    private final String USUARIO_NO_EXISTE = "USUARIO_NO_EXISTE";
    private final String USUARIO_EXISTE = "USUARIO_EXISTE";

    public ApiCrearUsuarios() {
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

        String rowSSOId = "";
        String rowUserId = "";

        String accion = getQuery().getValues("accion");
        String numuser_user = getQuery().getValues("numuser_user");
        String nombre_user = getQuery().getValues("nombre_user");
        String apellido_user = getQuery().getValues("apellido_user");
        String email_contacto_user = getQuery().getValues("email_contacto_user");
        String numero_telefono_user = getQuery().getValues("numero_telefono_user");
        String profesion_select = getQuery().getValues("profesion_select");
        String textarea_obs = getQuery().getValues("textarea_obs");
        String sucursal_select = getQuery().getValues("sucursal_select");
        String roles_select = getQuery().getValues("roles_select");
        String password = getQuery().getValues("password");
        String checkbox_activo = getQuery().getValues("checkbox_activo");

        String token = getQuery().getValues("token");
        String empresa="";

        Log.info("accion :" + accion);
        Log.info("numuser_user :" + numuser_user);
        Log.info("nombre_user :" + nombre_user);
        Log.info("apellido_user :" + apellido_user);
        Log.info("email_contacto_user :" + email_contacto_user);
        Log.info("numero_telefono_user :" + numero_telefono_user);
        Log.info("profesion_select :" + profesion_select);
        Log.info("textarea_obs :" + textarea_obs);
        Log.info("sucursal_select :" + sucursal_select);
        Log.info("roles_select :" + roles_select);
        Log.info("password :" + password);
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
                    empresa = jwt.getJwt().getValue("empresaName").toString();

                    Log.info(usuario_creador);

                    switch (accion) {
                        case "EXISTE_REGISTRO":
                            if (LFUsuarios.existeRegistro(numuser_user, empresa).equalsIgnoreCase("NO")) {
                                Log.info("USUARIO NO REGISTRADO");
                                status = Status.SUCCESS_OK;
                                message = USUARIO_NO_EXISTE;
                            } else {
                                Log.info("USUARIO REGISTRADO");
                                status = Status.SUCCESS_OK;
                                message = USUARIO_EXISTE;
                            }
                            break;
                        case "CREATE":

                            //Encriptacion password
                            String key = LFParams.getParams("PASS", "BLOWFISH", "CRYPT");
                            password = BlowFish.encryptNoPadding(key, password);

                            Log.info("Encriptada :" + password);

                            Log.info("DesEncriptada :" + BlowFish.decryptNoPadding(key, password));

                            rowSSOId = LFSSO.getNewUserId();
                            rowUserId = LFUsuarios.getNewUserId();

                            Log.info("rowSSOId : " + rowSSOId);
                            Log.info("rowUserId : " + rowUserId);

                            if (!rowUserId.isEmpty() && (!rowSSOId.isEmpty())
                                    && (LFUsuarios.insertUsuario(numuser_user,
                                            nombre_user, apellido_user, email_contacto_user,
                                            numero_telefono_user, profesion_select, textarea_obs,
                                            sucursal_select, roles_select, password, checkbox_activo,
                                            usuario_creador, nombre_completo, empresa, rowUserId) == 1)
                                    && (LFSSO.insertUserPass(numuser_user/* pass_c_numuser*/,
                                            password/*pass_c_password*/,
                                            /*pass_d_vencimiento,*/
                                            usuario_creador/*pass_c_createuser*/,
                                            checkbox_activo/*pass_c_activo*/,
                                            nombre_completo/*pass_c_createusername*/,
                                            rowSSOId/*pass_n_id*/) == 1)) {

                                Log.info("Insert OK");
                                status = Status.SUCCESS_OK;
                                message = INSERT_OK;

                            } else {

                                Log.info("Error de insercion");
                                //Roll back - delete
                                LFSSO.rollBack(rowSSOId);
                                LFUsuarios.rollBack(rowUserId, empresa);
                                // rowSSOId ;
                                //rowUserId ;
                                message = INSERT_NO_OK;
                                status = Status.CLIENT_ERROR_BAD_REQUEST;

                            }
                            break;
                        default:
                            Log.error("no Soportada :" + accion);
                    }

                } catch (Exception e) {
                    Log.error("getMessage :" + e.getMessage());
                    Log.error(e.toString());
                    //Roll back - delete
                    LFSSO.rollBack(rowSSOId);
                    LFUsuarios.rollBack(rowUserId, empresa);
                    // rowSSOId ;
                    //rowUserId ;
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
