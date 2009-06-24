/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.perfectday.logicengine.model.command.combat;

import org.perfectday.logicengine.model.command.Command;
import org.perfectday.logicengine.model.minis.Mini;
import org.perfectday.logicengine.model.minis.action.combat.CombatActionMini;

/**
 *
 * @author Miguel Angel Lopez Montellano ( alakat@gmail.com )
 */
public class CombatActionCommand extends Command{
    private String msg;

    public CombatActionCommand(Mini attacker, Mini defender, CombatActionMini atack) {
        
        this.msg = attacker.getName()+" golpe con "+atack.getName()+
                " vs "+defender.getName();
    }


    @Override
    public String info() {
        return this.msg;
    }
    
    
    
}
