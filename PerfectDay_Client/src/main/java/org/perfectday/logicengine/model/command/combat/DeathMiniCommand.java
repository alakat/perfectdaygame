/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.perfectday.logicengine.model.command.combat;

import org.perfectday.logicengine.model.command.Command;

/**
 *
 * @author Miguel Angel Lopez Montellano ( alakat@gmail.com )
 */
public class DeathMiniCommand extends Command {

    private String miniDeath;

    public DeathMiniCommand(String miniDeath) {
        this.miniDeath = miniDeath;
    }
    
    
    @Override
    public String info() {
        return "Mini: "+this.miniDeath+" muerto!!";
    }

}
