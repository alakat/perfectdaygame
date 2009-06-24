/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.perfectday.logicengine.model.spells.failEffectFunction;

import org.perfectday.logicengine.model.minis.Mini;
import org.perfectday.logicengine.model.spells.commands.FailSpellCommand;

/**
 * El fallo ocasiona la muerte del lanzador. 
 * @author Miguel Angel Lopez Montellano (alakat@gmail.com)
 */
public class DeadEffectFunction extends FailEffectFunction {

    @Override
    public FailSpellCommand doeffect(Mini caster) {
        caster.setDamage((caster.getVitality()+1)*100);
        return new FailSpellCommand("El lanzador muere en el lanzamiento");
    }

    
}
