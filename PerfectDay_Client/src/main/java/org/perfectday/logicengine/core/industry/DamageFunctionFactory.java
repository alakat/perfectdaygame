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
public class DamageFunctionFactory extends IndexFactory {

    private static DamageFunctionFactory instance;

    public static DamageFunctionFactory getInstance() {
        if ( instance == null)
           instance = new DamageFunctionFactory(Configuration.getInstance().getDamageFunctionFile());
        return instance;
    }
        
    public DamageFunctionFactory(File f) {
        super(f,false);
    }

    @Override
    public Object create(String key) throws Exception {
        return ((Class)this.database.get(key)).getConstructor().newInstance();
    }
    
    

}
