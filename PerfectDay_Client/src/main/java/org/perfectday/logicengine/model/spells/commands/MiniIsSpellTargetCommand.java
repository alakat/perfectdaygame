/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.perfectday.logicengine.model.spells.commands;

import org.perfectday.logicengine.model.command.Command;
import org.perfectday.logicengine.model.minis.Mini;
import org.perfectday.logicengine.model.spells.Spell;

/**
 *
 * @author Miguel Angel Lopez Montellano (alakat@gmail.com)
 */
public class MiniIsSpellTargetCommand extends Command{
    private Mini mini;
    private Spell spell;

    public MiniIsSpellTargetCommand(Mini mini, Spell spell) {
        this.mini = mini;
        this.spell = spell;
    }

    @Override
    public String info() {
        return ""+this.mini.getName()+" es objetivo de "+spell.getName();
    }
    
    
    

}
