/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.perfectday.message.accident;

import java.io.Serializable;
import org.perfectday.logicengine.model.minis.Mini;
import org.perfectday.logicengine.model.unittime.UnitTime;
import org.perfectday.message.model.Message;
import org.perfectday.message.model.MessageType;

/**
 *
 * @author Lobo <inmortalland83@gmail.com>
 */
public class ActivationMessage extends Message implements Serializable{
    private UnitTime unitTime;
    private Mini activateMini;

    public Mini getActivateMini() {
        return activateMini;
    }

    public void setActivateMini(Mini activateMini) {
        this.activateMini = activateMini;
    }

    public UnitTime getUnitTime() {
        return unitTime;
    }

    public void setUnitTime(UnitTime unitTime) {
        this.unitTime = unitTime;
    }
    public ActivationMessage(){
        this.setType(MessageType.Activation);
        
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
