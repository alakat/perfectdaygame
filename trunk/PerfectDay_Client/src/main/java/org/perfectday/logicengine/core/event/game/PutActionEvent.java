/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.perfectday.logicengine.core.event.game;

import org.perfectday.logicengine.core.event.Event;
import org.perfectday.logicengine.model.activationstack.accidents.Activation;

/**
 * Evento de apilación en la pila de activación
 * @author Miguel Angel Lopez Montellano (alakat@gmail.com)
 */
public class PutActionEvent extends Event {
    
    private Activation activation;

    public PutActionEvent() {
    }
    
    

    public Activation getActivation() {
        return activation;
    }

    public void setActivation(Activation activation) {
        this.activation = activation;
    }
}
