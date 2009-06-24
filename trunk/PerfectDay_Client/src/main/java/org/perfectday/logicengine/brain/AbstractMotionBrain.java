/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.perfectday.logicengine.brain;

import org.perfectday.logicengine.model.battelfield.Field;
import org.perfectday.logicengine.model.minis.Mini;

/**
 *
 * @author Miguel Angel Lopez Montellano ( alakat@gmail.com )
 */
public abstract class AbstractMotionBrain {

    protected MovedFunction movedFunction;

    public AbstractMotionBrain() {
    }

    public MovedFunction getMovedFunction() {
        return movedFunction;
    }

    public void setMovedFunction(MovedFunction movedFunction) {
        this.movedFunction = movedFunction;
    }
    
    public abstract Field getBestField( Mini mini);
    
    
}
