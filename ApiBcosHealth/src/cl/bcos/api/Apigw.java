/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.bcos.api;

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

        System.setProperty("http.proxyHost", "proxyfal.falabella.cl");
        System.setProperty("http.proxyPort", "8080");
        System.setProperty("http.proxyUser", "aacantero");
        System.setProperty("http.proxyPassword", "Kantero32.");

        System.setProperty("https.proxyHost", "proxyfal.falabella.cl");
        System.setProperty("https.proxyPort", "8080");
        System.setProperty("https.proxyUser", "aacantero");
        System.setProperty("https.proxyPassword", "Kantero32.");

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
                component.getDefaultHost().attach("/bcos/api/json/SSO", ApiSSO.class);
                component.getDefaultHost().attach("/bcos/api/json/planes", ApiCrearPlanes.class);
                component.getDefaultHost().attach("/bcos/api/json/listarPlanes", ApiListarPlanes.class);
                component.getDefaultHost().attach("/bcos/api/json/crearSuscripcion", ApiCrearSuscripcion.class);
                component.getDefaultHost().attach("/bcos/api/json/listarSuscripcion", ApiListarSuscripciones.class);
                component.getDefaultHost().attach("/bcos/api/json/updateSuscripcion", ApiUpdateSuscripcion.class);
                component.getDefaultHost().attach("/bcos/api/json/listarFichas", ApiListarFichas.class);

                component.start();
            } else {
                Log.error("No se ha especificado el puerto");
            }
        } else {
            Log.error("No se ha especificado el puerto");
        }

    }

}
