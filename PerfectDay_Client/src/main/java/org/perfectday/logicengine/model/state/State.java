/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.perfectday.logicengine.model.state;

import java.util.List;
import org.perfectday.logicengine.model.ReferenceObject;
import org.perfectday.logicengine.model.command.Command;
import org.perfectday.logicengine.model.minis.Mini;
import org.perfectday.logicengine.model.state.commands.ActionStateCommand;
import org.perfectday.logicengine.model.unittime.UnitTime;

/**
 * Abstraccion de un estado de un mini. Como por ejemplo envenenado oarmadura
 * mejorada....
 * @author Miguel Angel Lopez Montellano ( alakat@gmail.com )
 */
public abstract class State extends ReferenceObject{

    private String name;
    private Mini mini;
    private UnitTime timeEffect;
    private double percentAffected;

    public State() {
    }
    
    

    public State(String name,Mini mini,UnitTime ut) {
        super();
        this.name = name;
        this.mini = mini;
        this.timeEffect =ut;
        this.percentAffected=1.0;
    }

    public State(String name, Mini mini, UnitTime timeEffect, double percentAffected) {
        this.name = name;
        this.mini = mini;
        this.timeEffect = timeEffect;
        this.percentAffected = percentAffected;
    }
    
    

    public State(String name, UnitTime timeEffect) {
        this.name = name;
        this.timeEffect = timeEffect;
    }
    
    
    /**
     * Realizar la acción que tiene codificada un Estado
     * @param data dato necesario en la acción del estado
     */
    public abstract Object doState(Object data,List<Command> commands);

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Mini getMini() {
        return mini;
    }

    public void setMini(Mini mini) {
        this.mini = mini;
    }

    public UnitTime getTimeEffect() {
        return timeEffect;
    }

    public void setTimeEffect(UnitTime timeEffect) {
        this.timeEffect = timeEffect;
    }
    
    
    public abstract String generateStringEffect();
    
    public abstract Object generateDataEffect();
    
    public Command generateActionCommand(){        
        return new ActionStateCommand(this,
                mini,
                generateStringEffect(),
                generateDataEffect());
        
    }
    
}
