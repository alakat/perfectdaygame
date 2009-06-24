/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.perfectday.logicengine.model.state.accident;

import java.util.List;
import org.perfectday.logicengine.model.activationstack.accidents.Accident;
import org.perfectday.logicengine.model.command.Command;
import org.perfectday.logicengine.model.state.State;
import org.perfectday.logicengine.model.unittime.UnitTime;

/**
 *
 * Evento de la pila de activacion referente a un estado
 * @author Miguel Angel Lopez Montellano ( alakat@gmail.com )
 */
public abstract class StateAccident extends Accident{
    
    private State state;
    
    public StateAccident(State state,UnitTime mUnitTime) {
        super(mUnitTime);
        this.state = state;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }    
    
    public abstract void doAccident(List<Command> commands);

}
