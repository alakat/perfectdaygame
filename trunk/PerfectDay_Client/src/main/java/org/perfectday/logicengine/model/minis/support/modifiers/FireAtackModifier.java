/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.perfectday.logicengine.model.minis.support.modifiers;

import org.perfectday.logicengine.model.minis.Mini;

/**
 *
 * @author Miguel Angel Lopez Montellano ( alakat@gmail.com )
 */
public class FireAtackModifier extends Modifier{

    public FireAtackModifier(Number number) {
        super(number);
    }

    public FireAtackModifier() {
    }
    
    

    
    @Override
    public void doModifier(Mini target) {
        target.setStrength(target.getStrength()+number.intValue());
    }

    @Override
    public void reverModifier(Mini target) {
        target.setStrength(target.getStrength()-number.intValue());
    }
    

}
