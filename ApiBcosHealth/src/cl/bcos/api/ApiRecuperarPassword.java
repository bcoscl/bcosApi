/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.bcos.api;

import cl.bcos.Jwt.GenerarTokenJWT;
import cl.bcos.Jwt.ImplementacionJWT;
import cl.bcos.LF.LFParams;
import cl.bcos.LF.LFUsuarios;
import cl.bcos.RF.RFParams;
import cl.bcos.data.Registro;
import cl.bcos.utils.JavaMail;
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
public class ApiRecuperarPassword extends ServerResource {

    private static final Logger Log = Logger.getLogger(ApiRecuperarPassword.class);

    private ImplementacionJWT jwt = null;
    private Map s = new HashMap();
    private final String ERROR_TOKEN = "TOKEN_NO_VALIDO";
    private final String ACCION = "SENDPASSWORD";
    private final String ASUNTO = "Recuperacion de Password";
    private GenerarTokenJWT token = null;
    private String tokken = "";
    private String SEGUNDOS = "180";
    
    

    public ApiRecuperarPassword() {
        jwt = new ImplementacionJWT();
        token = jwt.getGeneraJWT();
       
    }

    @Post
    public Representation recuperarPassword() {
        Log.debug(Thread.currentThread().getStackTrace()[1].getMethodName());
        String path = getRequest().getResourceRef().getHostIdentifier() + getRequest().getResourceRef().getPath();
        Log.info("path : " + path);

        Status status = null;
        String message = "ok";
        Gson gson = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create();
        Map map = new HashMap();

        String email = getQuery().getValues("email");
        String accion = getQuery().getValues("accion");
        String numuser = getQuery().getValues("numuser");

        String accion_firmado = getQuery().getValues("token");

        Log.info("email : " + email);
        Log.info("accion : " + accion);
        Log.info("numuser : " + numuser);
        Log.info("token : " + accion_firmado);

        //ValidarTokenJWT validaJWT = jwt.getJwt();
        try {
            if (accion_firmado.equals(ACCION)) {
                try {

                    if (LFUsuarios.existeRegistrobyEmail(email,numuser).equalsIgnoreCase("SI")) {

                        //rescatar la configuracion de emial y enviar el mail
                        String body = LFParams.getParams("EMAIL", "BODY", "RESET_PASS");

                        String mail_smtp_host = "";
                        String mail_smtp_user = "";
                        String mail_smtp_clave = "";
                        String mail_smtp_auth = "";
                        String mail_smtp_port = "";
                        String mail_smtp_transport = "";
                        String mail_smtp_url = "";

                        Iterator it = LFParams.getEmailConfig();

                        while (it.hasNext()) {
                            Registro reg = (Registro) it.next();

                            mail_smtp_host = reg.get(RFParams.mail_smtp_host);
                            mail_smtp_user = reg.get(RFParams.mail_smtp_user);
                            mail_smtp_clave = reg.get(RFParams.mail_smtp_clave);
                            mail_smtp_auth = reg.get(RFParams.mail_smtp_auth);
                            mail_smtp_port = reg.get(RFParams.mail_smtp_port);
                            mail_smtp_transport = reg.get(RFParams.mail_smtp_transport);
                            mail_smtp_url = reg.get(RFParams.mail_smtp_url);

                        }
                        JavaMail mail;
                        mail = new JavaMail(mail_smtp_host, mail_smtp_user, mail_smtp_clave, mail_smtp_port);
                        
                        //genera token automatico solo por 30 min
                        token.AddItem("origen", ASUNTO);
                        token.AddItem("numUser", numuser);
                        tokken = token.generaToken("bcosHealth", "bearer", "public",  SEGUNDOS);
                        // 60 segundos
                        Log.info(tokken);
                        
                        String URLTOKEN = mail_smtp_url+"?token="+tokken;                        
                        
                        body = body.replaceAll("_URL_RESET_", URLTOKEN);
                        
                        mail.mailSentTo(email,ASUNTO,body);

                        message = "SEND_OK";
                        status = Status.SUCCESS_OK;
                    } else {

                        Log.info("NO EXISTE");
                        message = "SEND_NO_OK";
                        status = new Status(400);

                    }

                } catch (Exception e) {
                    Log.error(e.toString());
                    message = "EMAIL_NO_REGISTRADO";
                    status = Status.CLIENT_ERROR_BAD_REQUEST;

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
