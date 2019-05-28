/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.bcos.get;

import cl.bcos.Jwt.ImplementacionJWT;
import cl.bcos.Jwt.ValidarTokenJWT;
import cl.bcos.LF.LFParams;
import cl.bcos.RF.RFParams;
import cl.bcos.data.Registro;
import cl.bcos.entity.S3Config;
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
public class ApiS3Params extends ServerResource {

    private static final Logger Log = Logger.getLogger(ApiS3Params.class);

    private ImplementacionJWT jwt = null;
    private Map s = new HashMap();
    private final String ERROR_TOKEN = "TOKEN_NO_VALIDO";

    public ApiS3Params() {
        jwt = new ImplementacionJWT();
    }

    @Post
    public Representation getS3Params() {
        Log.debug(Thread.currentThread().getStackTrace()[1].getMethodName());
        Status status = null;
        String message = "ok";
        Gson gson = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create();
        Map map = new HashMap();

        String token = getQuery().getValues("token");
        String empresasession = getQuery().getValues("empresasession");
        String accion = getQuery().getValues("accion");
        String empresas = getQuery().getValues("nombre_empresa");

        Log.info("token : " + token);

        String path = getRequest().getResourceRef().getHostIdentifier() + getRequest().getResourceRef().getPath();
        Log.info("path : " + path);

        ValidarTokenJWT validaJWT = jwt.getJwt();
        try {
            if (token != null && !token.equals("") && validaJWT.validarTokenRS(token)) {
                try {

                    String roles = jwt.getJwt().getValue("Roles").toString();

                    String empresa = jwt.getJwt().getValue("empresaName").toString();
                    
                   
                        empresa = empresasession;
                   
                    Log.info("empresa :" + empresa);

                    Log.info("roles :" + roles);

                    if (roles.contains("SUPER-ADMIN") || roles.contains("MEDICO") || roles.contains("ADMIN")) {

                        Iterator it = LFParams.getS3Params(empresa);

                        Registro reg = (Registro) it.next();

                        S3Config s3 = new S3Config();

                        s3.setACCESS_KEY_ID(reg.get(RFParams.ACCESS_KEY_ID));
                        s3.setACCESS_SEC_KEY(reg.get(RFParams.ACCESS_SEC_KEY));
                        s3.setFOLDER_NAME_EXAMENES(reg.get(RFParams.FOLDER_NAME_EXAMENES));
                        s3.setFOLDER_NAME_PROFILE(reg.get(RFParams.FOLDER_NAME_PROFILE));
                        s3.setPOLICY_RULES(reg.get(RFParams.POLICY_RULES));
                        s3.setBUCKETNAME(reg.get(RFParams.BUCKETNAME));

                        map.put("S3", s3);

                        Log.info("S3 OK");
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
