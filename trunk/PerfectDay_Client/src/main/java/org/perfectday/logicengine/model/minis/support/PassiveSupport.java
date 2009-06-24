/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.perfectday.logicengine.model.minis.support;

import java.util.ArrayList;
import java.util.List;
import org.perfectday.logicengine.combat.InstanceCombat;
import org.perfectday.logicengine.combat.model.combatkeep.CombatKeep;
import org.perfectday.logicengine.core.Game;
import org.perfectday.logicengine.core.player.Player;
import org.perfectday.logicengine.model.command.Command;
import org.perfectday.logicengine.model.command.combat.support.PassiveDefensiveSupportCommand;
import org.perfectday.logicengine.model.command.combat.support.PassiveOfensiveSupportCommand;
import org.perfectday.logicengine.model.minis.Mini;
import org.perfectday.logicengine.model.minis.support.modifiers.Modifier;

/**
 *
 * @author Miguel Angel Lopez Montellano ( alakat@gmail.com )
 */
public class PassiveSupport extends Support {
    private Modifier modifier;

    public PassiveSupport(SupportType supportType,
            CombatKeep supportKeep, 
            boolean spread, 
            Mini apoyador,
            Modifier modifier,String description) {
        super(supportType, supportKeep, spread, apoyador,description);
        this.modifier = modifier;
    }
    
    
    /**
     * Comprueba que el apoyador sea del bando adecuado
     * @param instanceCombat
     * @return
     */
    @Override
    public List<Command> doSupport(InstanceCombat instanceCombat) {
        List<Command> list = new ArrayList<Command>();
        if((this.getSupportType()==SupportType.PASIVE_DEFENSIVE)){
            Player p =Game.getInstance().getPlayerByMini(
                    instanceCombat.getDefensor());
            if( p.isBandMember(this.getApoyador())){                
                instanceCombat.getModifierDefender().add(this.modifier);
                list.add(new PassiveDefensiveSupportCommand(this.getApoyador(),this));
            }
        }
        if ((this.getSupportType()== SupportType.PASIVE_OFENSIVE)){
            Player p =Game.getInstance().getPlayerByMini(
                    instanceCombat.getAtacker());
            if( p.isBandMember(this.getApoyador())){
                instanceCombat.getModifierAtaquer().add(this.modifier);
                list.add(new PassiveOfensiveSupportCommand(this.getApoyador(),this));
            }
            
        }
        return list;    
    }

    @Override
    public List<Modifier> getModifierBySupport() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
  
}
