/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.perfectday.logicengine.brain;

import org.perfectday.logicengine.model.minis.Mini;
import org.perfectday.logicengine.model.minis.action.ActionMini;

/**
 *
 * @author Miguel Angel Lopez Montellano ( alakat@gmail.com )
 */
public abstract class AbstractActionBrain {
    
    protected SelectedActionFunction actionFunction;

    public AbstractActionBrain() {
    }

    public SelectedActionFunction getActionFunction() {
        return actionFunction;
    }

    public void setActionFunction(SelectedActionFunction actionFunction) {
        this.actionFunction = actionFunction;
    }
    
    public abstract ActionMini getBestAction(Mini mini);

}
