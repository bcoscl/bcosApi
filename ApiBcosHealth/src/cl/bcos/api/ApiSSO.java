/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.bcos.api;

import cl.bcos.BlowFish;
import cl.bcos.Jwt.GenerarTokenJWT;
import cl.bcos.Jwt.ImplementacionJWT;
import cl.bcos.Jwt.ValidarTokenJWT;
import cl.bcos.LF.LFParams;
import cl.bcos.LF.LFSSO;
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
public class ApiSSO extends ServerResource {

    private static final Logger Log = Logger.getLogger(ApiSSO.class);

    private ImplementacionJWT jwt = null;
    private Map s = new HashMap();
    private Map b = new HashMap();
    private final String ERROR_TOKEN = "Credenciale incorrectas";
    private String tokken = "";
    private String userr = "";
    private String passs = "";
    private String accion = "";
    private String ValidaPassword = "";
    private GenerarTokenJWT token = null;
    private Map map = null;
    private Status status = null;
    private String message = "ok";

    public ApiSSO() {
        jwt = new ImplementacionJWT();
        token = jwt.getGeneraJWT();
        map = new HashMap();
    }

    @Post
    public Representation SSO() {
        Log.debug(Thread.currentThread().getStackTrace()[1].getMethodName());

        Gson gson = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create();

        jwt = new ImplementacionJWT();

        tokken = "";
        userr = getQuery().getValues("User");
        passs = getQuery().getValues("Password");
        accion = getQuery().getValues("accion");
        tokken = getQuery().getValues("token");
        ValidaPassword = "OK";

        Log.info("user : " + userr);
        Log.info("pass : " + passs);
        Log.info("accion : " + accion);
        Log.info("Validar Datos con BD, pass encritar antes de comprar");

        try {

            switch (accion) {

                case "Auth":
                    String key = LFParams.getParams("PASS", "BLOWFISH", "CRYPT");
                    passs = BlowFish.encryptNoPadding(key, passs);

                    //Log.info("Encriptada :" + passs);

                    //Log.info("DesEncriptada :" + BlowFish.decryptNoPadding(key, passs));

                    if (LFSSO.autenticacion(userr, passs).equalsIgnoreCase("OK")) {
                        Log.info("USUARIO OK");
                        Log.info("agregar informacion basica en el token");

                        getUserInformation();

                    } else {
                        Log.error("USUARIO NO AUTORIZADO");
                        map.put("login", "NO-OK");
                        status = Status.CLIENT_ERROR_UNAUTHORIZED;
                        message = ERROR_TOKEN;
                    }
                    break;
                case "NewID":
                    getNewUserId();
                    break;
                default:
                    Log.info("Accion no soportada");
                    break;
            }

        } catch (Exception e) {
            Log.error(e.toString());
            status = Status.CLIENT_ERROR_BAD_REQUEST;
            message = ERROR_TOKEN;
        }
//        } else {
//            status = Status.CLIENT_ERROR_UNAUTHORIZED;
//            message = ERROR_TOKEN;
//        }
        s.put("statusCode", status.getCode());
        s.put("name", "");
        s.put("code", "");
        s.put("stack", "");
        s.put("message", message);
        map.put("status", s);

        setStatus(status, message);
        return new StringRepresentation(gson.toJson(map), MediaType.APPLICATION_JSON);
    }

    private void getUserInformation() {

        Iterator it = LFUsuarios.getUserInformation(userr);
       
        while (it.hasNext()) {
            
            Registro reg = (Registro) it.next();
           
                token.AddItem("name",reg.get(RFUsuarios.user_c_nombres) );     
                token.AddItem("LastName",reg.get(RFUsuarios.user_c_apellido));       
                token.AddItem("numUser",reg.get(RFUsuarios.user_c_numuser));       
                token.AddItem("Roles",reg.get(RFUsuarios.user_c_role));           
                token.AddItem("celular",reg.get(RFUsuarios.user_c_celular) );       
                token.AddItem("nombreOficina",reg.get(RFUsuarios.user_c_nombre_oficina) );
                token.AddItem("email",reg.get(RFUsuarios.user_c_email));          
                token.AddItem("fechaCracion",reg.get(RFUsuarios.user_d_createdate));     
                token.AddItem("creadoPor",reg.get(RFUsuarios.user_c_createusername)); 
                token.AddItem("imgUrl",reg.get(RFUsuarios.user_c_img));           	
                token.AddItem("Profesion",reg.get(RFUsuarios.user_c_profesion));       
                token.AddItem("AboutMe",reg.get(RFUsuarios.user_c_obs));           	
                token.AddItem("empresaName",reg.get(RFUsuarios.user_c_empresaname) );    
                token.AddItem("numUserCreador",reg.get(RFUsuarios.user_n_createuser));      
                token.AddItem("CodEmpresa",reg.get(RFUsuarios.user_n_cod_empresa));     
                token.AddItem("userId",reg.get(RFUsuarios.user_n_iduser));          
                token.AddItem("pUserId",reg.get(RFUsuarios.pass_n_id));          
                token.AddItem("status",reg.get(RFUsuarios.pass_c_activo));          

        }
       

        tokken = token.generaToken("bcosHealth", "bearer", "public", "54000");
        Log.info(tokken);
        // tokken = token.generaToken("bcosHealth", "bearer", "public","15");

        //Log.info(tokken);
        // ValidarTokenJWT validaJWT = jwt.getJwt();
//        if (token != null && !token.equals("") && validaJWT.validarTokenRS(token)) {
//            try {
//
//                String user = jwt.getJwt().getValue("user").toString();
//                String pass = jwt.getJwt().getValue("pass").toString();
//                String iss = jwt.getJwt().getValue("iss").toString();
//
//                Log.info("user : " + user);
//                Log.info("pass : " + pass);
//
//                token.AddItem("login", );
//
        map.put("login", "OK");
        map.put("token", tokken);
//
        message = "ok";
        status = Status.SUCCESS_OK;

    }

    private void getNewUserId() {
        ValidarTokenJWT validaJWT = jwt.getJwt();
        Map info = new HashMap();
        try {
            if (token != null && !token.equals("") && validaJWT.validarTokenRS(tokken)) {
                try {

                    String usuario_creador = jwt.getJwt().getValue("numUser").toString();
//                    String nombre_usuario = jwt.getJwt().getValue("name").toString();
//                    String apellido_usuario = jwt.getJwt().getValue("LastName").toString();

                    Log.info(usuario_creador);

                    String roles = jwt.getJwt().getValue("Roles").toString();

                    Log.info("roles :" + roles);

                    if (roles.contains("SUPER-ADMIN") || roles.contains("ADMIN")) {

                        info.put("valor", LFSSO.getNewUserId());
                        map.put("BasicResponse", info);

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
    }

}
