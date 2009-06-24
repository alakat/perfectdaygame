/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.perfectday.logicengine.model.spells.failEffectFunction;

import org.perfectday.logicengine.model.minis.Mini;
import org.perfectday.logicengine.model.spells.commands.FailSpellCommand;

/**
 * No hay efecto adverso sobre el lanzador
 * @author Miguel Angel Lopez Montellano (alakat@gmail.com)
 */
public class NullEffectFunction extends FailEffectFunction {

    @Override
    public FailSpellCommand doeffect(Mini caster) {
        //nothing
        return new FailSpellCommand("Fallo sin efectos en el lanzador");
    }
    
    

}
