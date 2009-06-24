/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.perfectday.logicengine.combat.core.functions.damage;

import org.perfectday.logicengine.combat.core.functions.DamageFunction;
import org.perfectday.logicengine.model.minis.Mini;
import org.perfectday.logicengine.model.minis.action.combat.CombatActionMini;

/**
 *
 * @author Miguel Angel Lopez Montellano ( alakat@gmail.com )
 */
public class HightDamageFunction extends DamageFunction{

    public static final int DAMAGE_MODIFIRED_BY_CRITICAL = 4;
    public static final double DAMAGE_MODIFIRED_BY_WEIGH_ATACK = 0.1;
    public static final double MINIMAL_BASE_DAMAGE = 1.0;
    @Override
    public double getDamageAtack(Mini atacker,
            Mini defensor,
            double modifiredDamage,
            CombatActionMini actionAtack,
            double lockyRoll) {
         int f = (2*atacker.getStrength())+actionAtack.getStrength();
        double damage = f - defensor.getResistance();
        damage = Math.max(damage,MINIMAL_BASE_DAMAGE);
        damage = damage * modifiredDamage;
        if (isCriticAtack(lockyRoll))
            damage = damage * DAMAGE_MODIFIRED_BY_CRITICAL;
        if (isWeighAtack(lockyRoll))
            damage = damage * DAMAGE_MODIFIRED_BY_WEIGH_ATACK;
        if (damage<0)
            damage=0.1;
        return damage;
    }

     /**
     * 
     * @param roll
     * @return
     */
    public boolean isCriticAtack(double roll){
        if(roll>=0.9)
            return true;
        return false;
    }
    
    /**
     * 
     * @param roll
     * @return
     */
    public boolean isWeighAtack(double roll){
        if(roll<=0.1)
            return true;
        return false;
    }

}
