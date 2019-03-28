/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.bcos.test;

import static cl.bcos.CF.CFPlanes.selectPlanes;
import cl.bcos.bd.Pool;
import java.sql.Connection;
import java.util.Iterator;
import org.apache.log4j.Logger;


/**
 *
 * @author aacantero
 */
public class testPostgres {
    
    private static final Logger Log = Logger.getLogger(testPostgres.class);
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
   
        try{
            Iterator it = selectPlanes();
        }catch(Exception e){
            Log.error(e);
        }
//        Log.info("incio GF");
//
//        Connection con = null;
//        Iterator it = null;
//
//        try {
//           con = Pool.getInstancia().getConnection("conexionOCT");
//           //SEDRFLabel.insertLabel(con,"1");
//           Log.info("Insert ok");
//        } catch (Exception e) {
//            Log.info(e.toString());
//        } finally {
//            Pool.getInstancia().free(con);
//        }

        //return it;
    
        
        
    }
    
}
