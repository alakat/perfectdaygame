/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.perfectday.core.asf;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import org.apache.log4j.Logger;
import org.perfectday.dashboard.gui.LoadingDialog;
import org.perfectday.dashboard.gui.model.LoadingDialogEvent;

/**
 *
 * @author Miguel Angel Lopez Montellano (alakat@gmail.com)
 */
public abstract class FiniteAutomatonState {
    
    private List<State> states;
    private State initial;
    private State end;    
    private State actual;
    private static Logger logger = Logger.getLogger(FiniteAutomatonState.class);
    private LoadingDialog ld;

    public FiniteAutomatonState(List<State> states, State initial, State end) {
        this.states = states;
        this.initial = initial;
        this.end = end;
        this.actual=initial;
        ld = new LoadingDialog(null,false);//no queremos que sea modal
        this.ld.setVisible(false);
    }

    public State getActual() {
        return actual;
    }

    public void setActual(State actual) {
        this.actual = actual;
    }

    public State getEnd() {
        return end;
    }

    public void setEnd(State end) {
        this.end = end;
    }

    public State getInitial() {
        return initial;
    }

    public void setInitial(State initial) {
        this.initial = initial;
    }

    public List<State> getStates() {
        return states;
    }

    public void setStates(List<State> states) {
        this.states = states;
    }
    
    
    public void move(State state) throws IllegalAccessException,
            InvocationTargetException{
        State ax = this.actual;
        this.actual = state;
        ax.move(state);
        if(this.ld!=null && !this.ld.isVisible()){
            this.ld.setVisible(true);
        }
    }
    
    public void move() throws IllegalAccessException,
            InvocationTargetException{
        logger.info("Actual:"+this.getActual().getName());
        logger.info("Movemos a "+this.getActual().getMovements().get(0).getName());
//        LoadingDialogEvent evt = new LoadingDialogEvent(this, "evt");
//        evt.setDescription("Movemos a "+this.getActual().getMovements().get(0).getName());
        
        if(!this.getActual().getMovements().get(0).getName().equals("Go!!!")){
            this.ld.addState(this.getActual());//"\nEstado actual: "+.getMovements().get(0).getName()
        }else{
            
            this.ld.closeWindows();
            this.ld = null;
        }
        this.move(this.getActual().getMovements().get(0));
    }
    

}
