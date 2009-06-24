/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.perfectday.logicengine.model.spells.action;

import org.apache.log4j.Logger;
import org.perfectday.logicengine.combat.model.combatkeep.CombatKeep;
import org.perfectday.logicengine.core.Game;
import org.perfectday.logicengine.model.ReferenceObject;
import org.perfectday.logicengine.model.minis.Mini;
import org.perfectday.logicengine.model.minis.action.ActionMini;
import org.perfectday.logicengine.model.spells.InstanceCastSpell;
import org.perfectday.logicengine.model.spells.Spell;
import org.perfectday.logicengine.model.spells.accident.CastSpellAccident;
import org.perfectday.logicengine.model.unittime.UnitTime;
import org.perfectday.logicengine.model.unittime.factories.LongUnitTimeFactory;

/**
 *
 * @author Miguel Angel Lopez Montellano ( alakat@gmail.com )
 */
public class SpellCastAction extends ActionMini{
    private static final Logger logger = Logger.getLogger(SpellCastAction.class);
    private Spell spellcast;
    private Mini caster;
    /**
     * Mini o Field target
     */
    private ReferenceObject target;

    public SpellCastAction(Spell spellcast) {
        super("Lanzamiento de:"+spellcast.getName());
        this.spellcast = spellcast;
    }
    
    public void preparedSpell(ReferenceObject target){
        InstanceCastSpell ics = new InstanceCastSpell(spellcast, caster, target);
        Game game = Game.getInstance();
        UnitTime ut = game.getActualTime();
        ut.plus(game.getPerfectDayGUI().getUnitTime());
        ut.plus(this.spellcast.getSpellCastTime());
        game.getActivationStack().put(new CastSpellAccident(ut, ics,this));
    }
    
    
    public Mini getCaster() {
        return caster;
    }

    public void setCaster(Mini caster) {
        this.caster = caster;
    }

    public Spell getSpellcast() {
        return spellcast;
    }

    public void setSpellcast(Spell spellcast) {
        this.spellcast = spellcast;
    }

    public ReferenceObject getTarget() {
        
        return target;
    }

    public void setTarget(ReferenceObject target) {
        this.target = target;
    }
    
    @Override
    public CombatKeep getActionKeep() {
        return this.getSpellcast().getSpellKeep();
    }

    @Override
    public void doAction(Mini worker, Mini target) {
        logger.info("do spell cast action");
        logger.debug("preparedSpell");
        this.preparedSpell(target);
        logger.debug("sumamos el tipo de lanzamiento al coste del turno"+this.getSpellcast());
        //Es probable que este coste se elimine
        Game.getInstance().getTurnTime().plus(this.getSpellcast().getSpellCastTime());
        logger.debug("Sumamos el tipo de haber lanzado un conjuto al consumido en el turno");
        Game.getInstance().getTurnTime().plus(LongUnitTimeFactory.getInstance().doCastSpell(worker));
    }
    
    
    
    
    
    
}
