/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.perfectday.logicengine.model.minis.support.modifiers;
import org.perfectday.logicengine.combat.*;
import org.perfectday.logicengine.model.minis.Mini;


/**
 *
 * @author Miguel Angel Lopez Montellano ( alakat@gmail.com )
 */
public abstract class Modifier {
    protected Number number;

    public Modifier(Number number) {
        this.number = number;
    }

    public Modifier() {
    }
    
    /**
     * Aplica el modificador sobre el mini
     * @param target
     */
    public abstract void doModifier(Mini target);
    
    /**
     * 
     * @param target
     */
    public abstract void reverModifier(Mini target);

    public Number getNumber() {
        return number;
    }

    public void setNumber(Number number) {
        this.number = number;
    }
    
    
    

    
    
}
