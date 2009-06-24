/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.perfectday.logicengine.model.state.factories;

import java.util.List;
import org.perfectday.logicengine.core.Game;
import org.perfectday.logicengine.core.event.accident.PutAccidentEvent;
import org.perfectday.logicengine.core.event.manager.EventManager;
import org.perfectday.logicengine.model.activationstack.accidents.Accident;
import org.perfectday.logicengine.model.command.Command;
import org.perfectday.logicengine.model.command.combat.state.AddStateCommand;
import org.perfectday.logicengine.model.minis.Mini;
import org.perfectday.logicengine.model.state.ActiveState;
import org.perfectday.logicengine.model.state.MiniAttribute;
import org.perfectday.logicengine.model.state.PasiveState;
import org.perfectday.logicengine.model.state.PoisonedState;
import org.perfectday.logicengine.model.state.State;
import org.perfectday.logicengine.model.state.accident.StateActivationAccident;
import org.perfectday.logicengine.model.state.accident.StateClearAccident;
import org.perfectday.logicengine.model.unittime.LongUnitTime;
import org.perfectday.logicengine.model.unittime.UnitTime;

/**
 *
 * @author Miguel Angel Lopez Montellano ( alakat@gmail.com )
 */
public class StateFactory {
    
    private static StateFactory instance;
    private static final UnitTime STANDAR_CLEAR_PASIVE_STATE= 
            new LongUnitTime(30l);
    
    public static StateFactory getInstance(){
        if(instance==null)
            instance = new StateFactory();
        return instance;
    }
    
    public State createStatePoisoned(Mini mini){
        return new PoisonedState(mini);
    }
    
    public State createStatePoisoned(){
        return new PoisonedState();
    }
    
    public State createPasiveState(MiniAttribute attribute,Number number,String name){
        return new PasiveState(name, StateFactory.STANDAR_CLEAR_PASIVE_STATE, attribute, number);
    }
    
    public void registerState(Mini mini, State state,List<Command> commands){
        //Registramos el estado en el mini
        mini.addState(state);
        //Asignamos al estado el mini afectado;
        state.setMini(mini);
        //se añade a los comando la información de que un nuevo estado ha sido registrado
        commands.add(new AddStateCommand(state,mini.getReferenceObject()));
        
        //registramos su desaparición
        UnitTime t = Game.getInstance().getActualTime();
        UnitTime tclear = state.getTimeEffect();
        tclear.plus(t);
        //Apilamos el registro 
        Accident stateClearAccident = new StateClearAccident(state,tclear, mini);
        PutAccidentEvent putAccidentEvent = new PutAccidentEvent();
        putAccidentEvent.setAccident(stateClearAccident);
        EventManager.getInstance().addEvent(putAccidentEvent);
        
        //Si es un stado añadimos a la pila de activación la generación del efecto
        if (state instanceof ActiveState) {
            ActiveState activeState = (ActiveState) state;
            UnitTime tActivation = activeState.getDelayEffect();
            tActivation.plus(t);
            PutAccidentEvent putAccidentEvent_ = new PutAccidentEvent();
            putAccidentEvent_.setAccident(new StateActivationAccident(state, tActivation));
            EventManager.getInstance().addEvent(putAccidentEvent_);
        }
        EventManager.getInstance().eventWaitTest();

       
    }

}
