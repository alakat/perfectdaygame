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
public class AtackerModifier extends Modifier{

    
    public AtackerModifier(Number number) {
        super(number);        
    }

    public AtackerModifier() {
    }
    
    
    @Override
    public void doModifier(Mini target) {
        target.setAtak(target.getAtak()+this.number.intValue());
    }

    @Override
    public void reverModifier(Mini target) {
        target.setAtak(target.getAtak()-this.number.intValue());
    }

}
