/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.perfectday.logicengine.core.event.state;

import java.util.ArrayList;
import java.util.List;
import org.perfectday.logicengine.model.command.Command;
import org.perfectday.logicengine.model.minis.Mini;
import org.perfectday.logicengine.model.state.State;

/**
 *
 * @author Miguel Angel Lopez Montellano (alakat@gmail.com)
 */
public class ActivationStateEvent extends StateEvent {

    private List<Command> commands;
    public ActivationStateEvent(State state, Mini affectedMini) {
        super(state, affectedMini);
        this.commands = new ArrayList<Command>();
    }

    public List<Command> getCommands() {
        return commands;
    }

    public void setCommands(List<Command> commands) {
        this.commands = commands;
    }
    

    
}
