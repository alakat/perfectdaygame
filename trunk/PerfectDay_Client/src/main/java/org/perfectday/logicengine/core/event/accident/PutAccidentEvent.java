/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.perfectday.logicengine.core.event.accident;

import org.perfectday.logicengine.core.event.Event;
import org.perfectday.logicengine.model.activationstack.accidents.Accident;

/**
 * Evento de apilación de nuevos "accident" en la pila de activación
 * @author Miguel Angel Lopez Montellano (alakat@gmail.com)
 */
public class PutAccidentEvent extends Event{
    
    private Accident accident;

    public PutAccidentEvent() {
    }

    public Accident getAccident() {
        return accident;
    }

    public void setAccident(Accident accident) {
        this.accident = accident;
    }
    
}
