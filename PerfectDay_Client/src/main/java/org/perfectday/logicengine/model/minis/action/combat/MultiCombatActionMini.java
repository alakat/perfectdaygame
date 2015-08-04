/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.perfectday.logicengine.model.minis.action.combat;

import org.perfectday.logicengine.combat.InstanceCombat;
import org.perfectday.logicengine.combat.MasterOfCombatImpl;
import org.perfectday.logicengine.combat.MultyTargetInstanceCombat;
import org.perfectday.logicengine.combat.core.functions.DamageFunction;
import org.perfectday.logicengine.combat.core.functions.HitFunction;
import org.perfectday.logicengine.combat.core.functions.damage.DistanceDamageFunction;
import org.perfectday.logicengine.combat.core.functions.hit.DistanceHitFunction;
import org.perfectday.logicengine.combat.model.combatkeep.CombatKeep;
import org.perfectday.logicengine.core.Game;
import org.perfectday.logicengine.model.activationstack.accidents.OffensiveAction;
import org.perfectday.logicengine.model.battelfield.Field;
import org.perfectday.logicengine.model.minis.Mini;
import org.perfectday.logicengine.model.state.State;
import org.perfectday.logicengine.model.unittime.UnitTime;

/**
 *
 * @author Miguel Angel Lopez Montellano ( alakat@gmail.com )
 */
public class MultiCombatActionMini extends CombatActionMini{

    private CombatKeep effectArea;
    private State state;

    public MultiCombatActionMini() {
    }
    
    

    public MultiCombatActionMini(String name) {
        super(name);
    }
    
    
    public MultiCombatActionMini(String name, int strength, HitFunction hitFunction, DamageFunction damageFunction, CombatKeep combatKeep, boolean needPreparation, UnitTime costPreparation,CombatKeep effectArea) {
        super(name, strength, hitFunction, damageFunction, combatKeep, needPreparation, costPreparation);
        this.effectArea = effectArea;
    }

    @Override
    public InstanceCombat createInstanceCombat(Mini defender, Mini ataker, boolean isConterAtack) {
        Field fDefender = Game.getGame().getBattelField().getField(defender);
        Field fAtaker = Game.getGame().getBattelField().getField(ataker);
        MultyTargetInstanceCombat instanceCombat =
                    new MultyTargetInstanceCombat(defender, fDefender, ataker, fAtaker,
                    this,(DistanceHitFunction) this.hitFunction,(DistanceDamageFunction)this.damageFunction,!isConterAtack,this.effectArea);
        return instanceCombat;
    }
    

    public CombatKeep getEffectArea() {
        return effectArea;
    }

    public void setEffectArea(CombatKeep effectArea) {
        this.effectArea = effectArea;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }
    
    

}
