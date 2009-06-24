/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.perfectday.logicengine.combat.core.functions.hit;

import org.perfectday.logicengine.combat.core.functions.HitFunction;
import org.perfectday.logicengine.model.battelfield.Field;
import org.perfectday.logicengine.model.minis.Mini;

/**
 *
 * @author Miguel Angel Lopez Montellano ( alakat@gmail.com )
 */
public class DistanceHitFunction extends HitFunction{

    public static final double BASIC_MODIFIRED = 1.0;
    public static final double MODIFIRED_BY_CUALITY_OF_ATACK = 0.1;
    
    private Field focus;
    private Field targer;
    public DistanceHitFunction() {
    }

    @Override
    public double luckyModifired(double roll) {
        return roll - 0.5;
    }

    @Override
    public double modifiedDamage(Mini atacker, Mini defender, double luckyRoll) {
        
        double modifired = BASIC_MODIFIRED;
        if(this.focus!=null && this.targer!=null){
            
        }else{
            org.apache.log4j.Logger.getLogger(DistanceHitFunction.class)
                    .warn("No tengo field focus o target. Se aplica como  una SimpleHitFunction");
        }
        double luckyModifire = luckyModifired(luckyRoll);
        org.apache.log4j.Logger.getLogger(SimpleHitFunction.class).
                warn("luckyModifire"+luckyModifire);
        int cualityOfAtack = atacker.getAtak() - defender.getDefense();
        if(this.focus!=null && this.targer!=null){
            double distance = this.focus.getDistance(this.targer);
            cualityOfAtack = cualityOfAtack-((int)distance);            
        }else{
            org.apache.log4j.Logger.getLogger(DistanceHitFunction.class)
                    .warn("No tengo field focus o target. Se aplica como  una SimpleHitFunction");
        }
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

    public Field getFocus() {
        return focus;
    }

    public void setFocus(Field focus) {
        this.focus = focus;
    }

    public Field getTarger() {
        return targer;
    }

    public void setTarger(Field targer) {
        this.targer = targer;
    }
    
    

}
