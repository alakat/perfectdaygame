/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.perfectday.logicengine.brain.simple;

import org.perfectday.logicengine.brain.AbstractActionBrain;
import org.perfectday.logicengine.model.minis.Mini;
import org.perfectday.logicengine.model.minis.action.ActionMini;

/**
 *
 * @author Miguel Angel Lopez Montellano ( alakat@gmail.com )
 */
public class SimpleActionBrain extends AbstractActionBrain {

    @Override
    public ActionMini getBestAction(Mini mini) {
        double primary = this.actionFunction.
                getCualityOfAction(mini.getPrimaryAction(), mini);
        double secondary = this.actionFunction.
                getCualityOfAction(mini.getSecondaryAction(), mini);
        return primary>secondary? mini.getPrimaryAction():mini.getSecondaryAction();
    }
    

}
