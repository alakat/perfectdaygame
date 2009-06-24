/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.perfectday.logicengine.core.industry;

import java.io.File;
import org.perfectday.logicengine.core.configuration.Configuration;

/**
 *
 * @author Miguel Angel Lopez Montellano ( alakat@gmail.com )
 */
public class HitFunctionFactory extends IndexFactory{
    private static HitFunctionFactory instance;

    public static HitFunctionFactory getInstance() {
        if ( instance == null)
           instance = new HitFunctionFactory(Configuration.getInstance().getHitFunctionFile());
        return instance;
    }
        
    public HitFunctionFactory(File f) {
        super(f,false);
    }

    @Override
    public Object create(String key) throws Exception {
        return ((Class)this.database.get(key)).getConstructor().newInstance();
    }
    
}
