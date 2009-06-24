/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.perfectday.logicengine.model.minis.action.combat;

import org.perfectday.logicengine.combat.core.functions.DamageFunction;
import org.perfectday.logicengine.combat.core.functions.HitFunction;
import org.perfectday.logicengine.combat.model.combatkeep.CombatKeep;
import org.perfectday.logicengine.model.state.State;
import org.perfectday.logicengine.model.unittime.UnitTime;

/**
 *
 * 
 * @author Miguel Angel Lopez Montellano ( alakat@gmail.com )
 */
public class StateCombatActionMini extends CombatActionMini {

    private State state;

    public StateCombatActionMini(String name) {
        super(name);
    }
    
    
    
    public StateCombatActionMini(String name, int strength, HitFunction hitFunction, DamageFunction damageFunction, CombatKeep combatKeep, boolean needPreparation, UnitTime costPreparation,State state) {
        super(name, strength, hitFunction, damageFunction, combatKeep, needPreparation, costPreparation);
        this.state = state;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }
    
    
    
}
