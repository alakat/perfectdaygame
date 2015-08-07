/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.perfectday.logicengine.core.industry;

import java.io.InputStream;
import org.perfectday.logicengine.core.configuration.Configuration;

/**
 *
 * @author Miguel Angel Lopez Montellano ( alakat@gmail.com )
 */
public class ModifierFactory extends IndexFactory {

    private static ModifierFactory instance;
    
    private ModifierFactory(InputStream is)  {
        super(is ,false);
    }

    public static ModifierFactory getInstance()  {
        if(instance==null)
            instance = new ModifierFactory(Configuration.getInstance().getModifierFile());
        return instance;
    }
    
    

    @Override
    public Object create(String key) throws Exception {
        return ((Class)this.database.get(key)).getConstructor().newInstance();
    }

    
    
    

    
    
}
