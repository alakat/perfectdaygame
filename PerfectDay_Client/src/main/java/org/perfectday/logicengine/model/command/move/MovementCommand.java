/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.perfectday.logicengine.model.command.move;

import org.perfectday.logicengine.model.ReferenceObject;
import org.perfectday.logicengine.model.command.Command;

/**
 *
 * @author Miguel Angel Lopez Montellano ( alakat@gmail.com )
 */
public class MovementCommand extends Command {
    
    private ReferenceObject mini;
    private ReferenceObject targetField;

    public MovementCommand() {
    }

    
    
    
    public MovementCommand(ReferenceObject mini, ReferenceObject targetField) {
        this.mini = mini;
        this.targetField = targetField;
    }

    public ReferenceObject getMini() {
        return mini;
    }

    public void setMini(ReferenceObject mini) {
        this.mini = mini;
    }

    public ReferenceObject getTargetField() {
        return targetField;
    }

    public void setTargetField(ReferenceObject targetField) {
        this.targetField = targetField;
    }

    @Override
    public String info() {
        return mini.toString()+" move to "+targetField.toString();
    }
    
    

}
