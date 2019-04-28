/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.bcos.get;

import cl.bcos.Jwt.ImplementacionJWT;
import cl.bcos.Jwt.ValidarTokenJWT;
import cl.bcos.LF.LFAttentionList;
import cl.bcos.RF.RFAttentionList;
import cl.bcos.data.Registro;
import cl.bcos.entity.Attention;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.restlet.data.MediaType;
import org.restlet.data.Status;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;

/**
 *
 * @author aacantero
 */
public class ApiListarAttentionList extends ServerResource {
    
    private static final Logger Log = Logger.getLogger(ApiListarAttentionList.class);
    
    private ImplementacionJWT jwt = null;
    private Map s = new HashMap();
    private final String ERROR_TOKEN = "TOKEN_NO_VALIDO";
    
    public ApiListarAttentionList() {
        jwt = new ImplementacionJWT();
    }
    
    @Post
    public Representation getAttentionList() {
        Log.debug(Thread.currentThread().getStackTrace()[1].getMethodName());
        Status status = null;
        String message = "ok";
        Gson gson = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create();
        Map map = new HashMap();
        
        String accion = getQuery().getValues("accion");
        String doctor = getQuery().getValues("doctor");
        String token = getQuery().getValues("token");
        
        Log.info("accion : " + accion);
        Log.info("doctor : " + doctor);
        Log.info("token : " + token);
        String path = getRequest().getResourceRef().getHostIdentifier() + getRequest().getResourceRef().getPath();
        Log.info("path : " + path);
        
        ValidarTokenJWT validaJWT = jwt.getJwt();
        try {
            if (token != null && !token.equals("") && validaJWT.validarTokenRS(token)) {
                try {
                    
                    String roles = jwt.getJwt().getValue("Roles").toString();
                    String usuario_creador = jwt.getJwt().getValue("numUser").toString();
                    // String apellido_usuario = jwt.getJwt().getValue("LastName").toString();
                    String empresa = jwt.getJwt().getValue("empresaName").toString();
                    
                    if (doctor.equalsIgnoreCase("TOKEN")) {
                        doctor = usuario_creador;
                    }
                    
                    Log.info("roles :" + roles);
                    
                    if (roles.contains("SUPER-ADMIN") || roles.contains("MEDICO") || roles.contains("ADMIN") || roles.contains("RECEPCION")) {
                        Iterator it = LFAttentionList.selectAttentionList(doctor, empresa);
                        List<Attention> l = new ArrayList();
                        while (it.hasNext()) {
                            Registro reg = (Registro) it.next();
                            Attention s = new Attention();
                            
                            s.setAt_c_mediconame(reg.get(RFAttentionList.at_c_mediconame));
                            s.setAt_c_pacientename(reg.get(RFAttentionList.at_c_pacientename));
                            s.setAt_c_numuser_medico(reg.get(RFAttentionList.at_c_numuser_medico));
                            s.setAt_c_numuser_paciente(reg.get(RFAttentionList.at_c_numuser_paciente));
                            s.setAt_n_id(reg.get(RFAttentionList.at_n_id));
                            s.setAt_c_obs(reg.get(RFAttentionList.at_c_obs));
                            s.setAt_d_fechamod(reg.get(RFAttentionList.at_d_fechamod));
                            
                            l.add(s);
                            
                        }
                        if (l.size() > 0) {
                            map.put("Attention", l);
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
