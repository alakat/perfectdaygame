/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.perfectday.logicengine.core.event.action;

import org.perfectday.logicengine.core.event.Event;
import org.perfectday.logicengine.model.minis.Mini;
import org.perfectday.logicengine.model.minis.action.ActionMini;
import org.perfectday.message.model.Message;

/**
 *Abstracción de un evento de accion
 * @author Miguel Angel Lopez Montellano ( alakat@gmail.com )
 */
    public class ActionEvent extends Event{
    private Mini worker;
    private Mini target;
    private ActionMini action;

    public ActionEvent() {
    }

    
    
    public ActionEvent(Mini worker, Mini target, ActionMini action) {
        this.worker = worker;
        this.target = target;
        this.action = action;
    }

    public ActionMini getAction() {
        return action;
    }

    public void setAction(ActionMini action) {
        this.action = action;
    }

    public Mini getTarget() {
        return target;
    }

    public void setTarget(Mini target) {
        this.target = target;
    }

    public Mini getWorker() {
        return worker;
    }

    public void setWorker(Mini worker) {
        this.worker = worker;
    }   
    

}
