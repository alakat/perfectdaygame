/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.perfectday.logicengine.model.spells.hitfunction;

import org.perfectday.logicengine.combat.core.functions.HitFunction;
import org.perfectday.logicengine.model.minis.Mini;

/**
 *
 * @author Miguel Angel Lopez Montellano ( alakat@gmail.com )
 */
public class MagicHitFunction extends HitFunction {

    @Override
    public double luckyModifired(double roll) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public double modifiedDamage(Mini atacker, Mini defender, double luckyRoll) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    
}
