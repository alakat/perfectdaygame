/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.perfectday.logicengine.model.spells.failEffectFunction;

import org.perfectday.logicengine.model.minis.Mini;
import org.perfectday.logicengine.model.spells.commands.FailSpellCommand;

/**
 * Abstracción del efecto que puede sufrir el lanzador de un conjuro ante un 
 * fracaso
 * @author Miguel Angel Lopez Montellano (alakat@gmail.com)
 */
public abstract class FailEffectFunction {
    /**
     * Raliza un efecto negativo sobre el lanzador
     * @param caster
     */
    public abstract FailSpellCommand doeffect(Mini caster);

}
