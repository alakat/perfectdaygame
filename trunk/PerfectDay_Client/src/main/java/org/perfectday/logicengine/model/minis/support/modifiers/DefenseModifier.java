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
public class DefenseModifier extends Modifier{

    public DefenseModifier(Number number) {
        super(number);
    }

    public DefenseModifier() {
    }
    
    

    
    @Override
    public void doModifier(Mini target) {
        target.setDefense(target.getDefense()+number.intValue());
    }

    @Override
    public void reverModifier(Mini target) {
       target.setDefense(target.getDefense()-number.intValue());
    }

    
}
