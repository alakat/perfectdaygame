/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.perfectday.logicengine.core.industry;

import java.io.File;
import java.util.Properties;
import org.perfectday.logicengine.combat.core.functions.DamageFunction;
import org.perfectday.logicengine.combat.core.functions.HitFunction;
import org.perfectday.logicengine.combat.model.combatkeep.CombatKeep;
import org.perfectday.logicengine.core.configuration.Configuration;
import org.perfectday.logicengine.model.minis.action.combat.CombatActionMini;
import org.perfectday.logicengine.model.minis.action.combat.MultiCombatActionMini;
import org.perfectday.logicengine.model.minis.action.combat.StateCombatActionMini;
import org.perfectday.logicengine.model.state.State;
import org.perfectday.logicengine.model.unittime.LongUnitTime;
import org.perfectday.logicengine.model.unittime.UnitTime;

/**
 *
 * @author Miguel Angel Lopez Montellano ( alakat@gmail.com )
 */
public class WeaponsFactory extends IndexFactory {

    private static WeaponsFactory instance;
    private static final String NAME="name";
    private static final String COMBAT_KEEP="combat_keep";
    private static final String AFFECTED_AREA="affected_area";
    private static final String STATE="state";
    private static final String STRENGTH="strength";
    private static final String NEED_PREPARATION="need_preparation";
    private static final String PREPARATION_TIME="preparation_time";
    private static final String HIT_FUNCTION="hit_function";
    private static final String DAMAGE_FUNCTION="damage_function";

    public WeaponsFactory(File f) {
        super(f,true);
    }

    public static WeaponsFactory getInstance() {
        if(instance==null)
            instance = new WeaponsFactory(Configuration.getInstance().getWeaponsFile());
        return instance;
    }
    
    
    
    @Override
    public Object create(String key) throws Exception {
        Properties p = (Properties) this.database.get(key);
        String name = p.getProperty(WeaponsFactory.NAME);
        CombatKeep combatKeep = (CombatKeep) CombatKeepFactory.getInstance().create(p.getProperty(WeaponsFactory.COMBAT_KEEP));
        int strength = Integer.parseInt(p.getProperty(WeaponsFactory.STRENGTH));
        boolean needPreparation = Boolean.parseBoolean(p.getProperty(WeaponsFactory.NEED_PREPARATION));
        UnitTime t = new LongUnitTime(Long.parseLong(p.getProperty(WeaponsFactory.PREPARATION_TIME)));
        HitFunction ht = (HitFunction) HitFunctionFactory.getInstance().create(p.getProperty(WeaponsFactory.HIT_FUNCTION));
        DamageFunction df = (DamageFunction) DamageFunctionFactory.getInstance().create(p.getProperty(WeaponsFactory.DAMAGE_FUNCTION));
        State state = null;
        if(!p.getProperty(WeaponsFactory.STATE).equals(IndexFactory.NONE))
            state = (State) StateFactory.getInstance().create(p.getProperty(WeaponsFactory.STATE));
        CombatKeep area = null;
        if(!p.getProperty(WeaponsFactory.AFFECTED_AREA).equals(IndexFactory.NONE))
            area = (CombatKeep) CombatKeepFactory.getInstance().create(p.getProperty(WeaponsFactory.AFFECTED_AREA));
        CombatActionMini cam = null;
        if (area==null && state == null){
            cam = new CombatActionMini(name);
        }else if(area == null){
            cam  = new StateCombatActionMini(name);
            ((StateCombatActionMini)cam).setState(state);
        }else {
            cam = new MultiCombatActionMini(name);
        }
        cam.setCombatKeep(combatKeep);
        cam.setCostPreparation(t);
        cam.setDamageFunction(df);
        cam.setHitFunction(ht);
        cam.setNeedPreparation(needPreparation);
        cam.setStrength(strength);
      
        return cam;
    }

}
