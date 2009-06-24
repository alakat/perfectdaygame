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
 * Conjuros donde no exista ningún efecto a parte de asignar el estado del
 * conjuro
 * @author Miguel Angel Lopez Montellano (alakat@gmail.com)
 */
public class NothingMagicEffectFunction extends EffectFunction{

    @Override
    protected void doAbstractEffect(List<Command> commands, double impactModification, Mini caster, Spell spell, Mini target) {
        //Do nothing
    }
    
    

}
