/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.perfectday.logicengine.core.event.accident;

import org.perfectday.logicengine.core.event.Event;
import org.perfectday.logicengine.model.unittime.UnitTime;

/**
 * Abstraccion de un evento producido por la obtencion de un nuevo accident de la
 * pila de activacion
 * @author Miguel Angel Lopez Montellano ( alakat@gmail.com )
 */
public class AccidentEvent extends Event {
    
    private UnitTime unitTime;

    public AccidentEvent(UnitTime unitTime) {
        this.unitTime = unitTime;
    }

    public UnitTime getUnitTime() {
        return unitTime;
    }

    public void setUnitTime(UnitTime unitTime) {
        this.unitTime = unitTime;
    }
    

}
