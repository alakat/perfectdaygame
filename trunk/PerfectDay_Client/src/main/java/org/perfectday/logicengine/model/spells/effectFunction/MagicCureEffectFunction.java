/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.perfectday.logicengine.model.spells.effectFunction;

import java.util.List;
import org.perfectday.logicengine.model.command.Command;
import org.perfectday.logicengine.model.minis.Mini;
import org.perfectday.logicengine.model.spells.Spell;

/**
 * Existe un efecto de curación
 * @author Miguel Angel Lopez Montellano (alakat@gmail.com)
 */
public class MagicCureEffectFunction extends EffectFunction {

    public MagicCureEffectFunction() {
    }

    
    
    
    /**
     * Curación simple
     * @param commands
     * @param impactModification
     * @param caster
     * @param spell
     * @param target
     */
    @Override
    protected void doAbstractEffect(List<Command> commands, double impactModification, Mini caster, Spell spell, Mini target) {
        double dmg = target.getDamage();
        dmg= dmg-caster.getMagicAfinity();
        dmg = Math.max(dmg, 0.0);
        target.setDamage(dmg);
    }
    
    

}
