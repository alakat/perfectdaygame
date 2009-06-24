/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.perfectday.message.game;

import java.io.Serializable;

import org.perfectday.logicengine.model.unittime.UnitTime;
import org.perfectday.message.model.Message;
import org.perfectday.message.model.MessageType;

/**
 *
 * @author Lobo <inmortalland83@gmail.com>
 */
public class EndTurnMessage extends Message implements Serializable{
    private UnitTime  ut;

    public UnitTime getUt() {
        return ut;
    }

    public void setUt(UnitTime ut) {
        this.ut = ut;
    }
    public EndTurnMessage(){
        this.type=MessageType.EndTurn;
        
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
