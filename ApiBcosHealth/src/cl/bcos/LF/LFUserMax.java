/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.bcos.LF;

import cl.bcos.RF.RFSuscripcion;
import cl.bcos.data.Registro;
import java.util.Iterator;
import org.apache.log4j.Logger;

/**
 *
 * @author aacantero
 */
public class LFUserMax {

    private static final Logger Log = Logger.getLogger(LFUserMax.class);

    public static boolean quedanCuposSuscripcion(String empresa) {
        Log.debug(Thread.currentThread().getStackTrace()[1].getMethodName());
        
        Iterator it = null;
        Integer MaximoSuscripcion = 0;
        Integer Uconfigurados=0;
        boolean quedanCupos = false;
        it = LFSuscripcion.selectSuscripcionesbyempresa(empresa);
        
        while (it.hasNext()) {
            Registro reg = (Registro) it.next();          
            MaximoSuscripcion= Integer.valueOf(reg.get(RFSuscripcion.usermax));    
        }
        Uconfigurados = LFUsuarios.cuentaUsuarios(empresa);
        Log.info("Uconfigurados -" + Uconfigurados);
        Log.info("MaximoSuscripcion -" + MaximoSuscripcion);
        
        
        if(Uconfigurados>=MaximoSuscripcion){
            
            Log.info("NO QUEDAN CUPOS");
            quedanCupos= false;
        }if(Uconfigurados<MaximoSuscripcion){
           
            Log.info("CUPOS OK");
            quedanCupos= true;
        }
        return quedanCupos;
        
    }

}
