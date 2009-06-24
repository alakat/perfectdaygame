/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.perfectday.logicengine.model.minis.action;

import org.perfectday.logicengine.combat.core.functions.damage.FireBallDamageFunction;
import org.perfectday.logicengine.combat.core.functions.damage.HightDamageFunction;
import org.perfectday.logicengine.combat.core.functions.damage.SimpleDamageFunction;
import org.perfectday.logicengine.combat.core.functions.hit.DistanceHitFunction;
import org.perfectday.logicengine.combat.core.functions.hit.SimpleHitFunction;
import org.perfectday.logicengine.combat.model.combatkeep.BowBasicCombatKeep;
import org.perfectday.logicengine.combat.model.combatkeep.CloseCombatKeep;
import org.perfectday.logicengine.combat.model.combatkeep.SpearCloseCombatKeep;
import org.perfectday.logicengine.model.minis.action.combat.CombatActionMini;
import org.perfectday.logicengine.model.minis.action.combat.MultiCombatActionMini;
import org.perfectday.logicengine.model.minis.action.combat.StateCombatActionMini;
import org.perfectday.logicengine.model.state.MiniAttribute;
import org.perfectday.logicengine.model.state.PoisonedState;
import org.perfectday.logicengine.model.state.factories.StateFactory;
import org.perfectday.logicengine.model.unittime.LongUnitTime;

/**
 *
 * @author Miguel Angel Lopez Montellano ( alakat@gmail.com )
 */
public class CombatActionFactory {
    private static CombatActionFactory instance;

    public CombatActionFactory() {
    }

    public static CombatActionFactory getInstance() {
        if(instance==null)
            instance=new CombatActionFactory();
        return instance;
    }
    
    public CombatActionMini createSimpleBladeAtack(){
        return new CombatActionMini("Blade +0",
                3,new SimpleHitFunction(),
                new SimpleDamageFunction(), 
                new CloseCombatKeep(),
                false, 
                null);
    }
    
    public CombatActionMini createKnifSimpleAtack(){
        return new CombatActionMini("Knif +0",
                0,new SimpleHitFunction(),
                new SimpleDamageFunction(), 
                new CloseCombatKeep(),
                false, 
                null);
    }
    
    public CombatActionMini createSimpleSpearAtack(){
        return new CombatActionMini("Spear +0",
                2,new SimpleHitFunction(),
                new SimpleDamageFunction(), 
                new SpearCloseCombatKeep(),
                false, 
                null);
    }
    
    public CombatActionMini createSimpleBowAtack(){
        return new CombatActionMini("Bow +0",
                1, new SimpleHitFunction(), new SimpleDamageFunction(),
                new BowBasicCombatKeep(),
                false, null);
    }
    
    public CombatActionMini createSimpleAxeAtack(){
        return new CombatActionMini("Axe +0",
                1,new SimpleHitFunction(), new HightDamageFunction(),
                new CloseCombatKeep(),true,new LongUnitTime(10l)
                );
    }
    
    public CombatActionMini createFireBallAction(){
        return new MultiCombatActionMini(
                "Fireball +0",
                1, new DistanceHitFunction(), new FireBallDamageFunction(), new BowBasicCombatKeep(), true,new LongUnitTime(10l) , new CloseCombatKeep());
    }
    
    public CombatActionMini createPoisonedDager(){
        return new StateCombatActionMini(" Poisoned +0",
                0, 
                new SimpleHitFunction(),
                new SimpleDamageFunction(),
                new CloseCombatKeep(),
                false, 
                null,
                StateFactory.getInstance().createStatePoisoned());
    }
    
    public CombatActionMini createParalizedDager(){
        return new StateCombatActionMini(" Paralized +0",
                0, 
                new SimpleHitFunction(),
                new SimpleDamageFunction(),
                new CloseCombatKeep(),
                false, 
                null,
                StateFactory.getInstance().createPasiveState(MiniAttribute.MOVEMENT, new Integer(-2), "Paralized"));
    }
    
    
    
    
}
