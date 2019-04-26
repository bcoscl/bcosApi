/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.bcos.api;

import cl.bcos.get.ApiProfile;
import cl.bcos.get.ApiPacienteProfile;
import cl.bcos.option.ApiUpdateSuscripcion;
import cl.bcos.option.ApiUpdateSucursales;
import cl.bcos.option.ApiUpdateUsuarios;
import cl.bcos.option.ApiUpdateAttentionList;
import cl.bcos.get.ApiListarPacientes;
import cl.bcos.get.ApiListarPlanes;
import cl.bcos.get.ApiListarAttentionList;
import cl.bcos.get.ApiListarConsultas;
import cl.bcos.get.ApiListarEnfermedadesCronicas;
import cl.bcos.get.ApiListarExamenes;
import cl.bcos.get.ApiListarFarmacos;
import cl.bcos.get.ApiListarUsuarios;
import cl.bcos.get.ApiListarSuscripciones;
import cl.bcos.get.ApiListarRole;
import cl.bcos.get.ApiListarProfesiones;
import cl.bcos.get.ApiListarSucursales;
import cl.bcos.get.ApiListarFichas;
import cl.bcos.get.ApiListarMenu;
import cl.bcos.get.ApiListarParam;
import cl.bcos.option.ApiUpdateConsultas;
import cl.bcos.option.ApiUpdateEnfermedadesCronicas;
import cl.bcos.option.ApiUpdateExamenes;
import cl.bcos.option.ApiUpdateFarmacos;
import cl.bcos.option.ApiUpdatePacientes;
import cl.bcos.option.ApiUpdateParam;
import cl.bcos.put.ApiCrearSucursales;
import cl.bcos.put.ApiAddAttentionList;
import cl.bcos.put.ApiCrearConsultas;
import cl.bcos.put.ApiCrearEnfermedadesCronicas;
import cl.bcos.put.ApiCrearExamenes;
import cl.bcos.put.ApiCrearFarmacos;
import cl.bcos.put.ApiCrearUsuarios;
import cl.bcos.put.ApiCrearSuscripcion;
import cl.bcos.put.ApiCrearProfesiones;
import cl.bcos.put.ApiCrearRole;
import cl.bcos.put.ApiCrearPaciente;
import cl.bcos.put.ApiCrearParam;
import cl.bcos.put.ApiCrearPlanes;
import org.restlet.Component;
import org.restlet.data.Protocol;
import org.restlet.resource.ServerResource;
import org.apache.log4j.Logger;

/**
 *
 * @author aacantero
 */
public class Apigw extends ServerResource {

    private static final Logger Log = Logger.getLogger(Apigw.class);

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        // TODO code application Logic here


        int port = 0;
        if (args != null && args.length > 0) {

            try {
                port = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                Log.error(e.toString());
            }
            if (port != 0) {
                Component component = new Component();
                component.getServers().add(Protocol.HTTP, port);
                /*End Point de Autenticacion*/
                component.getDefaultHost().attach("/bcos/api/json/SSO", ApiSSO.class);
                
                /*End Point Control de Configuracione avanzadas*/
                component.getDefaultHost().attach("/bcos/api/json/crearParam", ApiCrearParam.class);
                component.getDefaultHost().attach("/bcos/api/json/listarParam", ApiListarParam.class);                
                component.getDefaultHost().attach("/bcos/api/json/updateParam", ApiUpdateParam.class); 
                
                component.getDefaultHost().attach("/bcos/api/json/listarMenu", ApiListarMenu.class); 
                
                /*End Point Control de Planes*/
                component.getDefaultHost().attach("/bcos/api/json/planes", ApiCrearPlanes.class);
                component.getDefaultHost().attach("/bcos/api/json/listarPlanes", ApiListarPlanes.class);
                
                /*End Point Suscripciones*/
                component.getDefaultHost().attach("/bcos/api/json/crearSuscripcion", ApiCrearSuscripcion.class);
                component.getDefaultHost().attach("/bcos/api/json/listarSuscripcion", ApiListarSuscripciones.class);
                component.getDefaultHost().attach("/bcos/api/json/updateSuscripcion", ApiUpdateSuscripcion.class);
                
                
                /*End Point de registro de Fichas*/
                component.getDefaultHost().attach("/bcos/api/json/listarFichas", ApiListarFichas.class);
                
                /*End Point Administracion de Roles*/
                component.getDefaultHost().attach("/bcos/api/json/crearRole", ApiCrearRole.class);
                component.getDefaultHost().attach("/bcos/api/json/listarRoles", ApiListarRole.class);
                
                /*End Point Administracion de Profesiones*/
                component.getDefaultHost().attach("/bcos/api/json/crearProfesiones", ApiCrearProfesiones.class);
                component.getDefaultHost().attach("/bcos/api/json/listarProfesiones", ApiListarProfesiones.class);
                
                /*End Point Administracion de usuarios de la aplicacion*/
                component.getDefaultHost().attach("/bcos/api/json/crearUsuarios", ApiCrearUsuarios.class);
                component.getDefaultHost().attach("/bcos/api/json/updateUsuarios", ApiUpdateUsuarios.class);
                component.getDefaultHost().attach("/bcos/api/json/ListarUsuarios", ApiListarUsuarios.class);
                
                
                /*End Point controla la informacion del componente profile*/
                component.getDefaultHost().attach("/bcos/api/json/Profile", ApiProfile.class);
                /*End Point Profile de pacientes*/
                component.getDefaultHost().attach("/bcos/api/json/PacienteProfile", ApiPacienteProfile.class);
                
                
                /*End Point Adminsitracion de Sucursales*/
                component.getDefaultHost().attach("/bcos/api/json/listarSucursales", ApiListarSucursales.class);
                component.getDefaultHost().attach("/bcos/api/json/updateSucursales", ApiUpdateSucursales.class);
                component.getDefaultHost().attach("/bcos/api/json/crearSucursales", ApiCrearSucursales.class);
                
                /*End Point Administracion de Pacientes*/
                component.getDefaultHost().attach("/bcos/api/json/crearPaciente", ApiCrearPaciente.class);
                component.getDefaultHost().attach("/bcos/api/json/ListarPacientes", ApiListarPacientes.class);
                component.getDefaultHost().attach("/bcos/api/json/updatePacientes", ApiUpdatePacientes.class);
                
                
                /*End Point gestiona la Lista de Atencion*/
                component.getDefaultHost().attach("/bcos/api/json/addAttentionList", ApiAddAttentionList.class);
                component.getDefaultHost().attach("/bcos/api/json/updateAttentionList", ApiUpdateAttentionList.class);
                component.getDefaultHost().attach("/bcos/api/json/listarAttentionList", ApiListarAttentionList.class);
                
                /*End Point Administracion de las enfermedades cronicas*/
                component.getDefaultHost().attach("/bcos/api/json/listarEnfermedadesCronicas", ApiListarEnfermedadesCronicas.class);
                component.getDefaultHost().attach("/bcos/api/json/updateEnfermedadesCronicas", ApiUpdateEnfermedadesCronicas.class);
                component.getDefaultHost().attach("/bcos/api/json/crearEnfermedadesCronicas", ApiCrearEnfermedadesCronicas.class);
                
                /*End Point Administracion de los farmacos*/
                component.getDefaultHost().attach("/bcos/api/json/listarFarmacos", ApiListarFarmacos.class);
                component.getDefaultHost().attach("/bcos/api/json/updateFarmacos", ApiUpdateFarmacos.class);
                component.getDefaultHost().attach("/bcos/api/json/crearFarmacos", ApiCrearFarmacos.class);
                
                
                /*End Point Administracion de las consultas del paciente*/
                component.getDefaultHost().attach("/bcos/api/json/listarConsultas", ApiListarConsultas.class);
                component.getDefaultHost().attach("/bcos/api/json/crearConsultas", ApiCrearConsultas.class);
                component.getDefaultHost().attach("/bcos/api/json/updateConsultas", ApiUpdateConsultas.class);
                
                /*End Point Administracion de los examenes*/
                component.getDefaultHost().attach("/bcos/api/json/listarExamenes", ApiListarExamenes.class);
                component.getDefaultHost().attach("/bcos/api/json/updateExamenes", ApiUpdateExamenes.class);
                component.getDefaultHost().attach("/bcos/api/json/crearExamenes", ApiCrearExamenes.class);

                
                    
                component.start();

            } else {
                Log.error("No se ha especificado el puerto");
            }
        } else {
            Log.error("No se ha especificado el puerto");
        }

    }

}
