/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.perfectday.logicengine.core.industry;

import java.io.File;
import java.util.Properties;
import org.apache.log4j.Logger;
import org.perfectday.logicengine.combat.core.functions.HitFunction;
import org.perfectday.logicengine.combat.model.combatkeep.CombatKeep;
import org.perfectday.logicengine.core.configuration.Configuration;
import org.perfectday.logicengine.model.spells.Spell;
import org.perfectday.logicengine.model.spells.SpellElement;
import org.perfectday.logicengine.model.spells.SpellType;
import org.perfectday.logicengine.model.spells.action.SpellCastAction;
import org.perfectday.logicengine.model.spells.effectFunction.EffectFunction;
import org.perfectday.logicengine.model.spells.failEffectFunction.FailEffectFunction;
import org.perfectday.logicengine.model.state.State;
import org.perfectday.logicengine.model.unittime.LongUnitTime;
import org.perfectday.logicengine.model.unittime.UnitTime;
/**
 *
 * @author Miguel Angel Lopez Montellano ( alakat@gmail.com )
 */
public class SpellFactory extends IndexFactory {

    public static SpellFactory instance;
    public static final String NAME="name";
    public static final String COMBAT_KEEP="combat_keep";
    public static final String PREPARATION_TIME="preparation_time";
    public static final String HIT_FUNCTION="hit_function";
    public static final String EFFECT_FUNCTION="effect_function";
    public static final String FAIL_PROBABILITY="fail_probability";
    public static final String FAIL_EFFECT_FUNCTION="faileffect_function";
    public static final String AFFECTED_AREA="affected_area";
    public static final String STATE="state";
    public static final String SPELL_TYPE="spell_type";
    public static final String SPELL_ELEMENT="spell_element";
    public static final String SPELL_PM_COST="spell_pm_cost";
    private static final Logger logger = Logger.getLogger(SpellFactory.class);
    
    private SpellFactory(File f, boolean indexProperties) {
        super(f, indexProperties);
    }

    public static SpellFactory getInstance() {
        if(instance==null)
            instance = new SpellFactory(
                    Configuration.getInstance().getSpellFile(), true);
        return instance;
    }
    
    
    
    

    @Override
    public Object create(String key) throws Exception {
        key = key.trim();
        try{
            Properties p = (Properties) this.database.get(key);
            String name = p.getProperty(NAME);
            HitFunction hitfunction = (HitFunction) HitFunctionFactory.getInstance().create(p.getProperty(HIT_FUNCTION));
            EffectFunction effectFunction = (EffectFunction) EffectedFunctionFactory.getInstance().create(p.getProperty(EFFECT_FUNCTION));
            SpellType spellType = processSpellType(p.getProperty(SPELL_TYPE));
            SpellElement spellElement = processSpellElement(p.getProperty(SPELL_ELEMENT));
            CombatKeep spellKeep  = (CombatKeep) CombatKeepFactory.getInstance().create(p.getProperty(COMBAT_KEEP));
            CombatKeep spellEffectArea=null;
            if(!p.getProperty(AFFECTED_AREA).equals(NONE))
                spellEffectArea = (CombatKeep) CombatKeepFactory.getInstance().create(p.getProperty(AFFECTED_AREA));
            UnitTime spellCastTime = processSpellCastTime(p.getProperty(PREPARATION_TIME));
            State spellState =null;
            if(!p.getProperty(STATE).equals(NONE))
                  spellState  = (State) StateFactory.getInstance().create(p.getProperty(STATE));
            double failProbability = processFailProbability(p.getProperty(FAIL_PROBABILITY));
            FailEffectFunction failEffectFunction = (FailEffectFunction) FailEffectFunctionFactory.getInstance().create(p.getProperty(FAIL_EFFECT_FUNCTION));
            int magicPointCost = processMagicPointCost(p.getProperty(SPELL_PM_COST));
            Spell spell = new Spell(name, hitfunction, effectFunction, spellType, spellElement, spellKeep, spellEffectArea, spellCastTime, spellState, failProbability, failEffectFunction, magicPointCost);
            SpellCastAction spellCastAction = new SpellCastAction(spell);            
            return spellCastAction;
        }catch(Exception ex){
            SpellFactory.logger.warn("Spell :"+key+" no se pudo cargar:"+ex.getMessage());            
            throw ex;
        }
        
    }

    private double processFailProbability(String property) {
        return Double.parseDouble(property);
    }

    private int processMagicPointCost(String property) {
        return Integer.parseInt(property );
    }

    private UnitTime processSpellCastTime(String property) {
        long ut =  Long.parseLong(property);
        return new LongUnitTime(ut);
    }

    private SpellElement processSpellElement(String property) {
        int opc = Integer.parseInt(property);
        switch(opc){
            case 0: return SpellElement.FIRE;
            case 1: return SpellElement.WATER;
            case 2: return SpellElement.AIR;
            case 3: return SpellElement.EARTH;
            case 4: return SpellElement.LIGHT;
            case 5: return SpellElement.SHADOW;
            default: return SpellElement.WATER;
        }
    }

    private SpellType processSpellType(String property) {
        int opc = Integer.parseInt(property);
        switch(opc){
            case 0: return SpellType.OFFENSIVE;
            case 1: return SpellType.PASIVE;
            default:return SpellType.OFFENSIVE;
        }
    }

}
