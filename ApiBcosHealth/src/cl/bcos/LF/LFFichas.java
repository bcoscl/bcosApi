/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.bcos.LF;

import cl.bcos.CF.CFFichas;
import java.util.Iterator;
import org.apache.log4j.Logger;

/**
 *
 * @author aacantero
 */
public class LFFichas {

    private static final Logger Log = Logger.getLogger(LFFichas.class);
    
    public static Iterator selectSFichas() {
        Log.info("selectSFichas");
        return CFFichas.selectFichas();
    }
    
}
