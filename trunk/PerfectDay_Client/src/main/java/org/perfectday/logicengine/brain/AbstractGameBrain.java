/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.perfectday.logicengine.brain;

/**
 *
 * @author Miguel Angel Lopez Montellano ( alakat@gmail.com )
 */
public abstract class AbstractGameBrain {

    protected AbstractMotionBrain motionBrain;
    protected AbstractActionBrain actionBrain;

    public AbstractGameBrain(AbstractMotionBrain motionBrain, AbstractActionBrain actionBrain) {
        this.motionBrain = motionBrain;
        this.actionBrain = actionBrain;
    }
    
    public abstract void setBestFunction();

}
