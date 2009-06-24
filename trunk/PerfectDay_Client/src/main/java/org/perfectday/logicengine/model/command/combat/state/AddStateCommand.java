/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.perfectday.logicengine.model.command.combat.state;

import org.perfectday.logicengine.model.ReferenceObject;
import org.perfectday.logicengine.model.command.Command;
import org.perfectday.logicengine.model.state.State;

/**
 * Command que códifica el que un mini posea un nuevo estado
 * @author Miguel Angel Lopez Montellano (alakat@gmail.com)
 */
public class AddStateCommand extends Command{
    
    private State state;
    private ReferenceObject mini;

    public AddStateCommand(State state, ReferenceObject mini) {
        this.state = state;
        this.mini = mini;
    }
    

    public ReferenceObject getMini() {
        return mini;
    }

    public void setMini(ReferenceObject mini) {
        this.mini = mini;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    @Override
    public String info() {
        return "Se añade el estado"+ state;
    }
    
    
    

}
