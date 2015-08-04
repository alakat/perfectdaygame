/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.perfectday.logicengine.model.spells.commands;

import org.perfectday.logicengine.model.command.Command;
import org.perfectday.logicengine.model.minis.Mini;

/**
 *  Commando de un efecto magico
 * @author Miguel Angel Lopez Montellano (alakat@gmail.com)
 */
public class MagicEffectCommand  extends Command{
    
    private Mini mini;
    private String efect;
    
    

    public MagicEffectCommand() {
    }
    
    

    public MagicEffectCommand(Mini mini, String efect) {
        this.mini = mini;
        this.efect = efect;
    }

    @Override
    public String info() {
        return mini+" sufre:"+efect;
    }
    
    

}
