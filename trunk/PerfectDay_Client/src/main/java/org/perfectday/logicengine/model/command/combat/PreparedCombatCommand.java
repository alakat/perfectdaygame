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
public class PreparedCombatCommand extends Command {
    
    private String msg;
    public PreparedCombatCommand(Mini mini, CombatActionMini action) {
        msg = mini.getName()+" prepara un ataque con "+action.getName()+"!";
    }
    
    @Override
    public String info() {
        return msg;
    }

}
