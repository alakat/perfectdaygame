/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.perfectday.logicengine.core.event.state;

import org.perfectday.logicengine.model.minis.Mini;
import org.perfectday.logicengine.model.state.State;

/**
 *  Evento que limpia un estado en un mini
 * @author Miguel Angel Lopez Montellano (alakat@gmail.com)
 */
public class ClearStateEvent extends StateEvent {

    public ClearStateEvent() {
        
    }
    
    
    

    public ClearStateEvent(State state, Mini affectedMini) {
        super(state, affectedMini);
    }

    
}
