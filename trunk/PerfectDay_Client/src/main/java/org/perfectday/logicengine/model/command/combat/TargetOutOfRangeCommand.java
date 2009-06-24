/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.perfectday.logicengine.model.command.combat;

import org.perfectday.logicengine.model.command.Command;

/**
 *
 * @author Miguel Angel Lopez Montellano (alakat@gmail.com)
 */
public class TargetOutOfRangeCommand extends Command {

    private String msg;

    public TargetOutOfRangeCommand() {
        this.msg = "El combate no se ha producido, el objetivo esta fuera de alcance.";
    }
    
    public TargetOutOfRangeCommand(String msg) {
        this.msg = msg;
    }
    
    @Override
    public String info() {
        return msg;
    }
    

}
