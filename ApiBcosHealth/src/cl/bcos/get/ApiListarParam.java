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
import cl.bcos.entity.Param;
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
public class ApiListarParam extends ServerResource {

    private static final Logger Log = Logger.getLogger(ApiListarParam.class);

    private ImplementacionJWT jwt = null;
    private Map s = new HashMap();
    private final String ERROR_TOKEN = "TOKEN_NO_VALIDO";

    public ApiListarParam() {
        jwt = new ImplementacionJWT();
    }

    @Post
    public Representation getParams() {
        Log.debug(Thread.currentThread().getStackTrace()[1].getMethodName());
        Status status = null;
        String message = "ok";
        Gson gson = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create();
        Map map = new HashMap();

        String token = getQuery().getValues("token");

        Log.info("token : " + token);
        
        String path = getRequest().getResourceRef().getHostIdentifier() + getRequest().getResourceRef().getPath();
        Log.info("path : " + path);

        ValidarTokenJWT validaJWT = jwt.getJwt();
        try {
            if (token != null && !token.equals("") && validaJWT.validarTokenRS(token)) {
                try {

                    String roles = jwt.getJwt().getValue("Roles").toString();
                    
                    String empresa = jwt.getJwt().getValue("empresaName").toString();

                    Log.info("roles :" + roles);

                    if (roles.contains("SUPER-ADMIN") ) {

                        Iterator it = LFParams.getAllParams();
                        List<Param> l = new ArrayList();
                        while (it.hasNext()) {
                            Registro reg = (Registro) it.next();
                            
                            Param param = new Param();
                            
                            param.setParams_n_grupo(reg.get(RFParams.params_n_grupo));
                            param.setParams_n_id(reg.get(RFParams.params_n_id));
                            param.setParams_n_param1(reg.get(RFParams.params_n_param1));
                            param.setParams_n_param2(reg.get(RFParams.params_n_param2));
                            param.setParams_n_param3(reg.get(RFParams.params_n_param3));
                            param.setParams_n_param4(reg.get(RFParams.params_n_param4));
                            param.setParams_n_subgrupo(reg.get(RFParams.params_n_subgrupo));
                            param.setParams_d_ultmod(reg.get(RFParams.params_d_ultmod));
                            param.setParams_c_numuser_utlmod(reg.get(RFParams.params_c_numuser_utlmod));
                            param.setParams_c_nombre_ultmod(reg.get(RFParams.params_c_nombre_ultmod));
                           
                           l.add(param);
                           
                        }
                        if(l.size()>0){
                            map.put("Param", l);
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
