/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.perfectday.logicengine.core.event.accident;

import org.perfectday.logicengine.model.minis.Mini;
import org.perfectday.logicengine.model.unittime.UnitTime;

/**
 *
 * @author Miguel Angel Lopez Montellano ( alakat@gmail.com )
 */
public class ActivationEvent extends AccidentEvent {

    private Mini activateMini;

    public ActivationEvent() {
        super(null);
    }

    
    
    
    public ActivationEvent(UnitTime unitTime, Mini activateMini) {
        super(unitTime);
        this.activateMini = activateMini;
    }

    public Mini getActivateMini() {
        return activateMini;
    }
    
    
    
}
