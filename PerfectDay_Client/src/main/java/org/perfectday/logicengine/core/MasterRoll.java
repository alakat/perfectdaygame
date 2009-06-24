/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.perfectday.logicengine.core;

import java.util.Random;

/**
 *
 * @author Miguel Angel Lopez Montellano ( alakat@gmail.com )
 */
public class MasterRoll {
    private static MasterRoll instance;
    private Random random;
    private MasterRoll(){
        this.random = new Random(System.currentTimeMillis());
    }

    public static MasterRoll getInstance() {
        if (instance==null)
            instance = new MasterRoll();
        return instance;
    }
    
    public double nextDouble(){
        return random.nextDouble();
    }
    
    public long nextLong(){
        return random.nextLong();
    }

    public Random getRandom() {
        return random;
    }
    
    

}
