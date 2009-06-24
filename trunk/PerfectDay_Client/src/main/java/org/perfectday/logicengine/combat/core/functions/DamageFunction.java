/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.perfectday.logicengine.combat.core.functions;

import java.util.List;
import org.perfectday.logicengine.model.minis.support.modifiers.Modifier;
import org.perfectday.logicengine.model.minis.Mini;
import org.perfectday.logicengine.model.minis.action.combat.CombatActionMini;

/**
 *
 * @author Miguel Angel Lopez Montellano ( alakat@gmail.com )
 */
public abstract class DamageFunction {

    

    /**
     *
     * @param atacker
     * @param defensor
     * @param modifiredDamage
     * @return
     */
    public abstract double getDamageAtack(Mini atacker, Mini defensor, double modifiredDamage, CombatActionMini actionAtack,double lockyRoll);

    
    
    /**
     *
     * @param roll
     * @return
     */
    public abstract boolean isCriticAtack(double roll);

    /**
     *
     * @param roll
     * @return
     */
    public abstract boolean isWeighAtack(double roll);

}
