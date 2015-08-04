/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.perfectday.logicengine.model.state.commands;

import org.perfectday.logicengine.model.command.Command;
import org.perfectday.logicengine.model.minis.Mini;
import org.perfectday.logicengine.model.state.State;

/**
 *
 * @author Miguel Angel Lopez Montellano ( alakat@gmail.com )
 */
public class StateCommand extends Command {

    private State state;
    private Mini mini;

    public StateCommand() {
    }
    
    

    public StateCommand(State state, Mini mini) {
        this.state = state;
        this.mini = mini;
    }
    
    
    
    @Override
    public String info() {
        return ""+state.getName()+"in mini "+this.mini.getName()+"";
    }

    public Mini getMini() {
        return mini;
    }

    public void setMini(Mini mini) {
        this.mini = mini;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }
    
    

}
