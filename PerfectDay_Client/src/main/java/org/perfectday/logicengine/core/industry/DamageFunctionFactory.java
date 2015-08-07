/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.perfectday.logicengine.core.industry;

import java.io.File;
import java.io.InputStream;
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
        
    public DamageFunctionFactory(InputStream is) {
        super(is,false);
    }

    @Override
    public Object create(String key) throws Exception {
        return ((Class)this.database.get(key)).getConstructor().newInstance();
    }
    
    

}
