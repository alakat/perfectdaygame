/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.perfectday.logicengine.core.event.state;

import org.perfectday.logicengine.core.event.Event;
import org.perfectday.logicengine.model.minis.Mini;
import org.perfectday.logicengine.model.state.State;
import org.perfectday.message.model.Message;

/**
 *  Evento asociado a un estado.
 * @author Miguel Angel Lopez Montellano (alakat@gmail.com)
 */
public class StateEvent extends Event {

    private State state;
    private Mini affectedMini;

    public StateEvent(State state, Mini affectedMini) {
        this.state = state;
        this.affectedMini = affectedMini;
    }

    public Mini getAffectedMini() {
        return affectedMini;
    }

    public void setAffectedMini(Mini affectedMini) {
        this.affectedMini = affectedMini;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }
    
}
