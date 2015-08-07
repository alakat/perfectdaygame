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
 * @author Miguel Angel Lopez Montellano (alakat@gmail.com)
 */
public class FailEffectFunctionFactory  extends IndexFactory{
    
    private static FailEffectFunctionFactory instance;

    private FailEffectFunctionFactory(InputStream is, boolean indexProperties) {
        super(is, indexProperties);
    }

    public static FailEffectFunctionFactory getInstance() {
        if (instance==null)
            instance = new FailEffectFunctionFactory(
                    Configuration.getInstance().getFailEffectFile(),
                    false);
        return instance;
    }

    @Override
    public Object create(String key) throws Exception {
        key = key.trim();
        return ((Class)this.database.get(key)).getConstructor().newInstance();
    }
    
    
    
    

}
