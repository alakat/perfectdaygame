/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.perfectday.logicengine.core.event.action.combat;

import java.util.ArrayList;
import java.util.List;
import org.perfectday.logicengine.core.event.action.ActionEvent;
import org.perfectday.logicengine.model.command.Command;
import org.perfectday.logicengine.model.minis.Mini;

import org.perfectday.logicengine.model.minis.action.combat.CombatActionMini;

/**
 * Abstracción de un combate
 * @author Miguel Angel Lopez Montellano ( alakat@gmail.com )
 */
public class CombatEvent  extends ActionEvent{

    private boolean conterAtack;
    private List<Command> commands;
    public CombatEvent(Mini atacker, Mini target, CombatActionMini action,Boolean conterAtack) {        
        super(atacker, target, action);
        this.conterAtack = conterAtack.booleanValue();
        this.commands = new ArrayList<Command>();
    }

    public boolean isConterAtack() {
        return conterAtack;
    }

    public void setConterAtack(boolean conterAtack) {
        this.conterAtack = conterAtack;
    }
    
    
    

    @Override
    public CombatActionMini getAction() {
        return (CombatActionMini) super.getAction();
    }

    public List<Command> getCommands() {
        return commands;
    }
      

}
