/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.perfectday.logicengine.combat.core.functions.hit;

import java.util.List;
import org.perfectday.logicengine.combat.AttributeToModifier;
import org.perfectday.logicengine.model.minis.support.modifiers.Modifier;
import org.perfectday.logicengine.combat.core.functions.*;
import org.perfectday.logicengine.model.minis.Mini;

/**
 *
 * @author Miguel Angel Lopez Montellano ( alakat@gmail.com )
 */
public class SimpleHitFunction extends HitFunction {
    public static final double BASIC_MODIFIRED = 1.0;
    public static final double MODIFIRED_BY_CUALITY_OF_ATACK = 0.1;
    

    public SimpleHitFunction() {
        
    }

    /**
     * return damage modificatión by looky Modifired is [-0.5,05.]
     * @param roll
     * @return
     */
    public double luckyModifired(double roll) {
        return roll - 0.5;
    }

    public double modifiedDamage(Mini atacker, Mini defender,double luckyRoll) {
        double modifired = BASIC_MODIFIRED;
        
        double luckyModifire = luckyModifired(luckyRoll);
        org.apache.log4j.Logger.getLogger(SimpleHitFunction.class).
                warn("luckyModifire"+luckyModifire);
        int cualityOfAtack = atacker.getAtak() - defender.getDefense();
        org.apache.log4j.Logger.getLogger(SimpleHitFunction.class).
                warn("cualityOfAtack: atacker.getAtak() - defender.getDefense()"
                +(atacker.getAtak() - defender.getDefense()));
        modifired = BASIC_MODIFIRED + (MODIFIRED_BY_CUALITY_OF_ATACK * cualityOfAtack) + luckyModifire;
        org.apache.log4j.Logger.getLogger(SimpleHitFunction.class).
                warn("modifier"
                +(BASIC_MODIFIRED + (MODIFIRED_BY_CUALITY_OF_ATACK * cualityOfAtack) + luckyModifire));
        if(modifired<0.1)
            modifired=0.1;
        return modifired;
    }

  
}
