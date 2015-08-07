/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.perfectday.logicengine.core.industry;

import java.io.File;
import java.io.InputStream;
import java.util.Set;
import org.perfectday.logicengine.core.configuration.Configuration;

/**
 *
 * @author Miguel Angel Lopez Montellano ( alakat@gmail.com )
 */
public class CombatKeepFactory extends IndexFactory{

    private static CombatKeepFactory instance;
    
    private CombatKeepFactory(InputStream is) {
        super(is,false);
    }

    public static CombatKeepFactory getInstance() {
        if(instance == null)
            instance=new CombatKeepFactory(Configuration.getInstance().getCombatKeepFile());
        return instance;
    }
    
    
    

    @Override
    public Object create(String key) throws Exception {
        return ((Class)this.database.get(key)).getConstructor().newInstance();
    }

}
