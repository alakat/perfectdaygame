/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.perfectday.logicengine.core.event.accident;

import java.util.ArrayList;
import java.util.List;
import org.perfectday.logicengine.combat.InstanceCombat;
import org.perfectday.logicengine.model.command.Command;
import org.perfectday.logicengine.model.unittime.UnitTime;

/**
 *
 * @author Miguel Angel Lopez Montellano ( alakat@gmail.com )
 */
public class OffensiveActionEvent extends AccidentEvent {

    private InstanceCombat instanceCombat;
    private List<Command> commands;

    public OffensiveActionEvent() {
        super(null);
    }

    
    public OffensiveActionEvent(UnitTime unitTime, InstanceCombat instanceCombat) {
        super(unitTime);
        this.instanceCombat = instanceCombat;
        this.commands = new ArrayList<Command>();
    }

    public InstanceCombat getInstanceCombat() {
        return instanceCombat;
    }

    public void setInstanceCombat(InstanceCombat instanceCombat) {
        this.instanceCombat = instanceCombat;
    }

    public List<Command> getCommands() {
        return commands;
    }

    public void setCommands(List<Command> commands) {
        this.commands = commands;
    }
    
    
    

    
}
