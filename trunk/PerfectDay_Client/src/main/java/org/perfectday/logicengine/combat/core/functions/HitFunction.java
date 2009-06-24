/*
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.perfectday.logicengine.combat.core.functions;

import java.util.List;
import org.perfectday.logicengine.model.minis.support.modifiers.Modifier;
import org.perfectday.logicengine.model.minis.Mini;

/**
 *
 * @author Miguel Angel Lopez Montellano ( alakat@gmail.com )
 */
public abstract class HitFunction {

    /**
     * return damage modificatión by looky Modifired.
     * @param roll
     * @return
     */
    public abstract double luckyModifired(double roll);

    /**
     * calculate of modifiref damage
     * @param atacker
     * @param defender
     * @return
     */
    public abstract double modifiedDamage(Mini atacker, Mini defender,double luckyRoll);
    
    
}
