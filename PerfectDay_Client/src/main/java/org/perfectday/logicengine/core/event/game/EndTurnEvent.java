/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.perfectday.logicengine.core.event.game;

import org.perfectday.logicengine.model.activationstack.accidents.Activation;
import org.perfectday.logicengine.model.minis.Mini;
import org.perfectday.logicengine.model.unittime.UnitTime;

/**
 * Indica que una jugada para un determinado Mini a terminado
 * @author Miguel Angel Lopez Montellano ( alakat@gmail.com )
 */
public class EndTurnEvent extends GameEvent{
    
    /**
     * Tipo gastado en el turno
     */
    private UnitTime  ut;
    private Mini mini;
    private Activation activation;

    public Activation getActivation() {
        return activation;
    }

    public void setActivation(Activation activation) {
        this.activation = activation;
    }
    public EndTurnEvent(UnitTime ut) {
        this.ut = ut;
    }

    public UnitTime getUt() {
        return ut;
    }

    public void setUt(UnitTime ut) {
        this.ut = ut;
    }

    public Mini getMini() {
        return mini;
    }

    public void setMini(Mini mini) {
        this.mini = mini;
    }
    
    

}
