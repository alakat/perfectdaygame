/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.perfectday.logicengine.model.state.commands;

import org.perfectday.logicengine.model.minis.Mini;
import org.perfectday.logicengine.model.state.State;

/**
 *
 * @author Miguel Angel Lopez Montellano ( alakat@gmail.com )
 */
public class ActionStateCommand extends StateCommand{

    private String effect;
    private Object data;
    public ActionStateCommand(State state, Mini mini,String effect,Object data) {
        super(state, mini);
        this.effect = effect;
        this.data=data;       
    }

    @Override
    public String info() {
        return " Do +"+super.info()+":"+this.effect;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getEffect() {
        return effect;
    }

    public void setEffect(String effect) {
        this.effect = effect;
    }
    
    
    
    
    

}
