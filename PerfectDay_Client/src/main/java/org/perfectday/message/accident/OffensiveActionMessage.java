/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.perfectday.message.accident;

import java.io.Serializable;
import org.perfectday.logicengine.combat.InstanceCombat;
import org.perfectday.logicengine.model.unittime.UnitTime;
import org.perfectday.message.model.Message;
import org.perfectday.message.model.MessageType;

/**
 *
 * @author Lobo <inmortalland83@gmail.com>
 */
public class OffensiveActionMessage extends Message implements Serializable{
    private UnitTime unitTime;
    private InstanceCombat instanceCombat;
    public OffensiveActionMessage(){
        this.setType(MessageType.OffensiveAction);
        
    }

    public InstanceCombat getInstanceCombat() {
        return instanceCombat;
    }

    public void setInstanceCombat(InstanceCombat instanceCombat) {
        this.instanceCombat = instanceCombat;
    }

    public UnitTime getUnitTime() {
        return unitTime;
    }

    public void setUnitTime(UnitTime unitTime) {
        this.unitTime = unitTime;
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
