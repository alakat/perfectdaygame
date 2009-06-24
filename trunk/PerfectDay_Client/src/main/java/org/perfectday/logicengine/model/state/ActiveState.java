/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.perfectday.logicengine.model.state;

import java.util.List;
import org.perfectday.logicengine.model.command.Command;
import org.perfectday.logicengine.model.minis.Mini;
import org.perfectday.logicengine.model.unittime.UnitTime;

/**
 *
 * @author Miguel Angel Lopez Montellano ( alakat@gmail.com )
 */
public abstract class ActiveState extends State {

    private UnitTime delayEffect;
    public ActiveState(String name,Mini mini,UnitTime ut,UnitTime delayEffect) {
        super(name,mini,ut);
        this.delayEffect =delayEffect;
    }

    public ActiveState(String name, UnitTime timeEffect, UnitTime delayEffect) {
        super(name, timeEffect);
        this.delayEffect = delayEffect;
    }
    

    @Override
    public abstract Object doState(Object data,List<Command> commands);

    public UnitTime getDelayEffect() {
        return delayEffect;
    }

    public void setDelayEffect(UnitTime delayEffect) {
        this.delayEffect = delayEffect;
    }
    
    
    

}
