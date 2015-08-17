package org.perfectday.logicengine.model.activationstack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.apache.log4j.Logger;
import org.perfectday.logicengine.core.Game;
import org.perfectday.logicengine.model.activationstack.accidents.Accident;
import org.perfectday.logicengine.model.activationstack.accidents.Activation;
import org.perfectday.logicengine.model.activationstack.accidents.OffensiveAction;
import org.perfectday.logicengine.model.minis.Mini;
import org.perfectday.logicengine.model.state.State;
import org.perfectday.logicengine.model.state.accident.StateAccident;

/**
 * 
 * @author Miguel
 */
public    class  ActivationStack {

    /**
     * accident stackt 
     */
    private List<Accident> stack;
    
    /**
     * 
     */
    public ActivationStack () {
        stack = new ArrayList<Accident>();
    }

    /**
     * Elimina los eventos referentes a un estado
     * @param state
     */
    public void clearState(State state){
        List<Accident> toRemove = new ArrayList<Accident>();
        for (Accident accident : this.getStack()) {
            if (accident instanceof StateAccident) {
                StateAccident stateAccident = (StateAccident) accident;
                if(stateAccident.getState().equals(state)){
                    toRemove.add(accident);
                }
            }
        }
        for (Accident accident : toRemove) {
            this.getStack().remove(accident);
        }
    }
    
    /**
     * Elimina todos los eventos referencetes a un mini
     * @param miniDead
     */
    public void deadClear(Mini miniDead) {
        List<Accident> deadAccident=new ArrayList<Accident>();
        for(Accident accident: this.getStack() ){
            if( accident instanceof Activation ){
                if( ((Activation)accident).getMini().equals(miniDead)){
                    deadAccident.add(accident);
                }
            }
            if( accident instanceof OffensiveAction ){
                if (((OffensiveAction)accident).getAtacker().equals(miniDead)||
                        ((OffensiveAction)accident).getDefender().equals(miniDead)){
                    deadAccident.add(accident);
                }
            }
        }
        for(Accident accident:deadAccident){
            this.getStack().remove(accident);
        }
    }
    
    /**
     * Put new accident and reorder accident
     * @param accident
     */
    public void put(Accident accident){
        getStack().add(accident);
        Collections.sort(getStack());
        Logger.getLogger(ActivationStack.class).debug("********************{");
        Logger.getLogger(ActivationStack.class).debug(this.toString());
        Logger.getLogger(ActivationStack.class).debug("}********************");
    }
    
    /**
     * return new accident
     * @return
     */
    public Accident pop(){
        return getStack().remove(0);
    }
    
    /**
     * return copy of accident stack to visualized;
     * @return
     */
    public List<Accident> viewStack(){
        return null;
    }

   

    @Override
    public synchronized  String  toString() {
        StringBuffer sbuff =new StringBuffer();
        sbuff.append("{"+Game.getGame().getActualTime()+"}");
        sbuff.append("\n");
        for(Accident accident:getStack()){
            sbuff.append(accident.toString());
            sbuff.append("\n");
        }
        return sbuff.toString();
    }

    public synchronized List<Accident> getStack() {
        return stack;
    }

    private synchronized  void setStack(List<Accident> stack) {
        this.stack = stack;
    }
    
}

