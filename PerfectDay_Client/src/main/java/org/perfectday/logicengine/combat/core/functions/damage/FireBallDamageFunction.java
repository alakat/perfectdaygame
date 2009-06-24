/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.perfectday.logicengine.combat.core.functions.damage;

import org.apache.log4j.Logger;
import org.perfectday.logicengine.model.minis.Mini;
import org.perfectday.logicengine.model.minis.action.combat.CombatActionMini;

/**
 *
 * @author Miguel Angel Lopez Montellano ( alakat@gmail.com )
 */
public class FireBallDamageFunction extends DistanceDamageFunction{

    private static double DISTANCE_DAMAGE_MODIFIER = 1.0;
    private static final Logger logger =
            Logger.getLogger(FireBallDamageFunction.class);
    @Override
    public double getModifierDamageByDistance(double distance, double damage) {
        logger.info("Distancia:"+distance+","+damage);
        if(distance > 0 ){
            damage = damage -(distance*DISTANCE_DAMAGE_MODIFIER);
        }else{
            return damage;
        }
        logger.info("Damage:"+damage);
        if(damage <0)
            return 0.0;
        return damage;
        
    }

    /**
     * Es un hechizo y usa la afinidad magica para calcular el daño
     * @param atacker
     * @param defensor
     * @param modifiredDamage
     * @param actionAtack
     * @param luckyRoll
     * @return
     */
    @Override
    public double getDamageAtack(Mini atacker, Mini defensor, double modifiredDamage, CombatActionMini actionAtack, double luckyRoll) {
        if(this.focus!=null && this.target!=null){
                double distance = this.focus.getDistance(this.target);
                org.apache.log4j.Logger.getLogger(FireBallDamageFunction.class).
                    warn("Lucky::"+luckyRoll);
            double f = (atacker.getMagicAfinity() + actionAtack.getStrength());
            org.apache.log4j.Logger.getLogger(FireBallDamageFunction.class).
                    warn("Fueza de ataque"+f);
            double damage = f - defensor.getMagicAfinity();
            org.apache.log4j.Logger.getLogger(FireBallDamageFunction.class).
                    warn("daño:f - defensor.getResistance()"+(damage));
            damage = Math.max(damage,MINIMAL_BASE_DAMAGE);
            damage = damage * modifiredDamage;
            org.apache.log4j.Logger.getLogger(FireBallDamageFunction.class).
                    warn("daño:f - defensor.getResistance()"+(damage));
            if (isCriticAtack(luckyRoll)){
                org.apache.log4j.Logger.getLogger(FireBallDamageFunction.class).
                    warn("CRITICO!!!!");
                damage = damage * DAMAGE_MODIFIRED_BY_CRITICAL;
            }if (isWeighAtack(luckyRoll)){
                org.apache.log4j.Logger.getLogger(FireBallDamageFunction.class).
                    warn("LEVE!!!");
                damage = damage * DAMAGE_MODIFIRED_BY_WEIGH_ATACK;
            }if (damage<0)
                damage=0.1;
            org.apache.log4j.Logger.getLogger(FireBallDamageFunction.class).
                    warn("daño:f - defensor.getResistance()"+(damage));
            return this.getModifierDamageByDistance(distance, damage);
            
        }
        return 0.0;
    }
    
    

}
