/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.perfectday.logicengine.combat.core.functions.damage;

import org.perfectday.logicengine.combat.core.functions.DamageFunction;
import org.perfectday.logicengine.model.battelfield.Field;
import org.perfectday.logicengine.model.minis.Mini;
import org.perfectday.logicengine.model.minis.action.combat.CombatActionMini;

/**
 *
 * @author Miguel Angel Lopez Montellano ( alakat@gmail.com )
 */
public abstract class DistanceDamageFunction extends DamageFunction {

    public static final int DAMAGE_MODIFIRED_BY_CRITICAL = 2;
    public static final double DAMAGE_MODIFIRED_BY_WEIGH_ATACK = 0.5;
    public static final double MINIMAL_BASE_DAMAGE = 1.0;
    protected Field focus;
    protected Field target;

    public DistanceDamageFunction() {
    }
    
    public abstract double getModifierDamageByDistance(double distance, double damage);
    
    
    
    

    /**
     * 
     * @param atacker
     * @param defensor
     * @param modifiredDamage
     * @param actionAtack
     * @param lockyRoll
     * @return
     */
    @Override
    public double getDamageAtack(Mini atacker, Mini defensor, double modifiredDamage, CombatActionMini actionAtack, double luckyRoll) {          
        if(this.focus!=null && this.target!=null){
                double distance = this.focus.getDistance(this.target);
                org.apache.log4j.Logger.getLogger(DistanceDamageFunction.class).
                    warn("Lucky::"+luckyRoll);
            int f = atacker.getStrength()+actionAtack.getStrength();
            org.apache.log4j.Logger.getLogger(DistanceDamageFunction.class).
                    warn("Fueza de ataque"+f);
            double damage = f - defensor.getResistance();
            org.apache.log4j.Logger.getLogger(DistanceDamageFunction.class).
                    warn("daño:f - defensor.getResistance()"+(damage));
            damage = Math.max(damage,MINIMAL_BASE_DAMAGE);
            damage = damage * modifiredDamage;
            org.apache.log4j.Logger.getLogger(DistanceDamageFunction.class).
                    warn("daño:f - defensor.getResistance()"+(damage));
            if (isCriticAtack(luckyRoll)){
                org.apache.log4j.Logger.getLogger(DistanceDamageFunction.class).
                    warn("CRITICO!!!!");
                damage = damage * DAMAGE_MODIFIRED_BY_CRITICAL;
            }if (isWeighAtack(luckyRoll)){
                org.apache.log4j.Logger.getLogger(DistanceDamageFunction.class).
                    warn("LEVE!!!");
                damage = damage * DAMAGE_MODIFIRED_BY_WEIGH_ATACK;
            }if (damage<0)
                damage=0.1;
            org.apache.log4j.Logger.getLogger(DistanceDamageFunction.class).
                    warn("daño:f - defensor.getResistance()"+(damage));
            return this.getModifierDamageByDistance(distance, damage);
            
        }
        return 0.0;
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

    public Field getFocus() {
        return focus;
    }

    public void setFocus(Field focus) {
        this.focus = focus;
    }

    public Field getTarget() {
        return target;
    }

    public void setTarget(Field target) {
        this.target = target;
    }

    
    
}
