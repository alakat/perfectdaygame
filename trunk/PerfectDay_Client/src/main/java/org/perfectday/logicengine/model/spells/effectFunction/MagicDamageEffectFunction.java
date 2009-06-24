/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.perfectday.logicengine.model.spells.effectFunction;

import java.util.List;
import org.perfectday.logicengine.model.command.Command;
import org.perfectday.logicengine.model.minis.Mini;
import org.perfectday.logicengine.model.spells.Spell;
import org.perfectday.logicengine.model.spells.commands.MagicEffectCommand;

/**
 * Efecto que aplica daño a una unidad basado en magia.
 * @author Miguel Angel Lopez Montellano (alakat@gmail.com)
 */
public class MagicDamageEffectFunction extends EffectFunction {

    public MagicDamageEffectFunction() {
    }

    /**
     * El efecto es daño en un mini.
     * @param commands
     * @param impactModification
     * @param caster
     * @param spell
     * @param target
     */
    @Override
    protected void doAbstractEffect(List<Command> commands, 
            double impactModification, Mini caster, Spell spell, Mini target) {    
        //La fuerza del impacto depende de la fuerza magica del lanzador y la del objetivo
        double str = Math.max(caster.getMagicAfinity() - target.getMagicAfinity() , 1.0); 
        str = str * impactModification;
        target.setDamage(target.getDamage()+(str));
        commands.add(new MagicEffectCommand(target, "Recive "+str*100+" de daño"));
    }


    
    
    

}
