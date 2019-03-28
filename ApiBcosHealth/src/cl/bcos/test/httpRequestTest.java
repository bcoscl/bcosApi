/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.bcos.test;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.apache.commons.httpclient.*;
import org.apache.commons.httpclient.methods.*;
import org.apache.log4j.Logger;

/**
 *
 * @author aacantero
 */
public class httpRequestTest {

    static String url
            = "http://localhost:9090/bcos/api/json/planes";

    private static final Logger Log = Logger.getLogger(httpRequestTest.class);

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        Map<String, String> s = new HashMap<String, String>();

        s.put("param1", "valor 1");
        s.put("param2", "valor 2");
        s.put("param3", "valor 3");

        for (Map.Entry<String, String> entry : s.entrySet()) {
            Log.info("clave=" + entry.getKey() + ", valor=" + entry.getValue());
        }

        //Instantiate an HttpClient
        HttpClient client = new HttpClient();

        //Instantiate a GET HTTP method
        //PostMethod method = new PostMethod(url);
        GetMethod method = new GetMethod(url);
        method.setRequestHeader("Content-type",
                "text/xml; charset=ISO-8859-1");
        method.setRequestHeader("Authorization", "Bearer " + "12345");

        //Define name-value pairs to set into the QueryString
        NameValuePair nvp1 = new NameValuePair("planName", "fname");
        NameValuePair nvp2 = new NameValuePair("userMax", "lname");
//        NameValuePair nvp3= new NameValuePair("email","email@email.com");

        method.setQueryString(new NameValuePair[]{nvp1, nvp2});

        try {
            int statusCode = client.executeMethod(method);

            System.out.println("Status Code = " + statusCode);
            System.out.println("QueryString>>> " + method.getQueryString());
            System.out.println("Status Text>>>"
                    + HttpStatus.getStatusText(statusCode));

            String response =  method.getResponseBodyAsString();
            method.releaseConnection();
           
           
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
