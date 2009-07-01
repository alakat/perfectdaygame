/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.perfectday.logicengine.model.state.accident;

import java.util.List;
import org.apache.log4j.Logger;
import org.perfectday.logicengine.core.Game;
import org.perfectday.logicengine.core.event.manager.EventManager;
import org.perfectday.logicengine.core.event.manager.EventManagerRunnable;
import org.perfectday.logicengine.core.event.state.ActivationStateEvent;
import org.perfectday.logicengine.model.command.Command;
import org.perfectday.logicengine.model.state.State;
import org.perfectday.logicengine.model.unittime.UnitTime;

/**
 * Evento que en los estados de tipo activos implica la ejecuciónde su acción
 * @author Miguel Angel Lopez Montellano ( alakat@gmail.com )
 */
public class StateActivationAccident extends StateAccident{

    private static final Logger logger = Logger.getLogger(StateActivationAccident.class);
    public StateActivationAccident(State state, UnitTime mUnitTime) {
        super(state, mUnitTime);
    }

    @Override
    public void doAccident(List<Command> commands) {
        this.getState().doState(null,commands);
    }

    @Override
    public void doAccident(List<Command> commands, Game game) throws Exception {
        logger.info("Se desapila un estado  "+this.toString());
        this.doAccident(commands);
        if(!this.getState().getMini().isAlive()){
            commands.addAll(game.searchDead()); //search mini dead in this turn
        }
        
    }

    @Override
    public void doAccidentWithEvent(Game game) throws Exception {
         ActivationStateEvent activationStateEvent = new ActivationStateEvent(this.getState(), null);
        EventManager.getInstance().addEvent(activationStateEvent);
        synchronized(EventManagerRunnable.getEventManagerThread()){
            EventManagerRunnable.getEventManagerThread().notifyAll();
        }
    }

    @Override
    public String toString() {
        return "{ActivationState "+this.getState().getName()+" en mini:"+this.getState().getMini()+"}"+"{ut:"+this.getUnitTime()+"}";
    }
    
    
}
