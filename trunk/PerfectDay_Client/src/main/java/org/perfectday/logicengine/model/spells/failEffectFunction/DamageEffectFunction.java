/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.perfectday.logicengine.model.spells.failEffectFunction;

import org.perfectday.logicengine.model.minis.Mini;
import org.perfectday.logicengine.model.spells.commands.FailSpellCommand;

/**
 * Causa daño a un lanzador de conjuros por su fallo
 * @author Miguel Angel Lopez Montellano (alakat@gmail.com)
 */
public class DamageEffectFunction extends FailEffectFunction {

    @Override
    public FailSpellCommand doeffect(Mini caster) {
        double damage = caster.getMagicAfinity() * 0.1;
        caster.setDamage(damage+caster.getDamage());
        return new FailSpellCommand("El lanzador sufre "+damage+" de daño");
    }

}
