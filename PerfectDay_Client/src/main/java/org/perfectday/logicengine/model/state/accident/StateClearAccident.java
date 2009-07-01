/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.perfectday.logicengine.model.state.accident;

import java.util.List;
import org.perfectday.logicengine.core.Game;
import org.perfectday.logicengine.core.event.manager.EventManager;
import org.perfectday.logicengine.core.event.manager.EventManagerRunnable;
import org.perfectday.logicengine.core.event.state.ClearStateEvent;
import org.perfectday.logicengine.model.command.Command;
import org.perfectday.logicengine.model.minis.Mini;
import org.perfectday.logicengine.model.minis.MiniModificable;
import org.perfectday.logicengine.model.state.State;
import org.perfectday.logicengine.model.state.commands.ClearStateCommand;
import org.perfectday.logicengine.model.unittime.UnitTime;

/**
 * Evento para eliminar de un mini un estado 
 * @author Miguel Angel Lopez Montellano ( alakat@gmail.com )
 */
public class StateClearAccident extends StateAccident{
    private  Mini mini;

    public StateClearAccident(State state, UnitTime mUnitTime, Mini mini) {
        super(state, mUnitTime);
        this.mini = mini;        
    }

    public Mini getMini() {
        return mini;
    }

    public void setMini(Mini mini) {
        this.mini = mini;
    }

    @Override
    public void doAccident(List<Command> commands) {
        if (mini instanceof MiniModificable) {
            org.apache.log4j.Logger.getLogger(StateClearAccident.class).
                    info("Se elimina el estado["+this.getState()+"] del mini["+this.mini+"]");
            MiniModificable miniModificable = (MiniModificable) mini;
            miniModificable.removeState(this.getState());
            Game.getGame().getActivationStack().clearState(this.getState());
            commands.add(new ClearStateCommand(this.getState(), this.getMini()));
            
        }else{
            org.apache.log4j.Logger.getLogger(StateClearAccident.class).
                    error("El mini [] no es de un tipo que soporta estados");
        }
    }

    @Override
    public void doAccident(List<Command> commands, Game game) throws Exception {
        this.doAccident(commands);
    }

    @Override
    public void doAccidentWithEvent(Game game) throws Exception {
        ClearStateEvent clearStateEvent = new ClearStateEvent(this.getState(), mini);
        EventManager.getInstance().addEvent(clearStateEvent);
        synchronized(EventManagerRunnable.getEventManagerThread()){
            EventManagerRunnable.getEventManagerThread().notifyAll();
        }
    }

    @Override
    public String toString() {
        return "{ClearState  "+this.getState().getName()+" en mini"+this.getMini()+"}"+"{ut:"+this.getUnitTime()+"}";
    }
    
    
    
    
}
