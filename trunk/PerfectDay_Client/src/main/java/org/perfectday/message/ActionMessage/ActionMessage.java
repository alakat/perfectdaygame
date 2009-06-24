/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.perfectday.message.ActionMessage;

import java.io.Serializable;
import org.perfectday.logicengine.model.minis.Mini;

import org.perfectday.logicengine.model.minis.action.ActionMini;
import org.perfectday.message.model.Message;
import org.perfectday.message.model.MessageType;

/**
 *
 * @author Lobo <inmortalland83@gmail.com>
 */
public class ActionMessage extends Message implements Serializable{
    private Mini worker;
    private Mini target;
    private ActionMini action;
    public ActionMessage(){
        this.setType(MessageType.Action);
        
        
                
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
    

    @Override
    public MessageType getType() {
        return this.type;
    }

    @Override
    public void setType(MessageType type) {
        this.type=type;
    }

}
