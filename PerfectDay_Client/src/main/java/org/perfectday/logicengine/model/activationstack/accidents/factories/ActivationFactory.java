/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.perfectday.logicengine.model.activationstack.accidents.factories;

import org.perfectday.logicengine.model.activationstack.accidents.Accident;
import org.perfectday.logicengine.model.activationstack.accidents.Activation;
import org.perfectday.logicengine.model.unittime.UnitTime;
import org.perfectday.logicengine.model.minis.Mini;
import org.perfectday.logicengine.model.state.State;
import org.perfectday.logicengine.model.state.accident.StateActivationAccident;
import org.perfectday.logicengine.model.state.accident.StateClearAccident;

/**
 *
 * @author Miguel Angel Lopez Montellano ( alakat@gmail.com )
 */
public class ActivationFactory {

    private static ActivationFactory instance;

    public ActivationFactory() {
    }

    public static ActivationFactory getInstance() {
        if( instance ==null)
            instance = new ActivationFactory();
        return instance;
    }
    
    /**
     * Create a activation event. 
     * This event triget in unitTime  and activated mini
     * @param mini
     * @param unitTime
     * @return
     */
    public Activation createActivation(Mini mini, UnitTime unitTime){
        return new Activation(unitTime, mini);        
    }
    
    
    public Accident createStateClearAccident(Mini mini, State state,UnitTime ut){
        return new StateClearAccident(state, ut, mini);
    }
    
    public Accident createStateActivationAccident(State state, UnitTime ut){
        return  new  StateActivationAccident(state, ut);
    }
    
}
