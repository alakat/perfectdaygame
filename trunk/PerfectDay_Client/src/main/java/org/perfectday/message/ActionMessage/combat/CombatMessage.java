/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.perfectday.message.ActionMessage.combat;

import java.io.Serializable;
import org.perfectday.logicengine.model.minis.Mini;
import org.perfectday.logicengine.model.minis.action.combat.CombatActionMini;
import org.perfectday.message.model.Message;
import org.perfectday.message.model.MessageType;

/**
 *
 * @author Lobo <inmortalland83@gmail.com>
 */
public class CombatMessage extends Message implements Serializable{
    private Mini atacker;
    private Mini target;
    private CombatActionMini action;
    private Boolean conterAtack;

    public CombatActionMini getAction() {
        return action;
    }

    public void setAction(CombatActionMini action) {
        this.action = action;
    }

    public Mini getAtacker() {
        return atacker;
    }

    public void setAtacker(Mini atacker) {
        this.atacker = atacker;
    }

    public Boolean getConterAtack() {
        return conterAtack;
    }

    public void setConterAtack(Boolean conterAtack) {
        this.conterAtack = conterAtack;
    }

    public Mini getTarget() {
        return target;
    }

    public void setTarget(Mini target) {
        this.target = target;
    }
    

    public CombatMessage(){
        this.setType(MessageType.Combat);
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
