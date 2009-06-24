/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.perfectday.logicengine.model.minis.action.combat;

import java.util.logging.Level;
import org.apache.log4j.Logger;
import org.perfectday.logicengine.combat.InstanceCombat;
import org.perfectday.logicengine.model.minis.action.ActionMini;
import org.perfectday.logicengine.combat.core.functions.HitFunction;
import org.perfectday.logicengine.combat.core.functions.DamageFunction;
import org.perfectday.logicengine.combat.model.combatkeep.CombatKeep;
import org.perfectday.logicengine.core.Game;
import org.perfectday.logicengine.core.event.action.combat.CombatEvent;
import org.perfectday.logicengine.core.event.manager.EventManager;
import org.perfectday.logicengine.model.activationstack.accidents.OffensiveAction;
import org.perfectday.logicengine.model.battelfield.Field;
import org.perfectday.logicengine.model.minis.Mini;
import org.perfectday.logicengine.model.unittime.UnitTime;

/**
 * Es una acción que desencadena un mini que genera en combate.
 * @author Miguel Angel Lopez Montellano ( alakat@gmail.com )
 */
public class CombatActionMini extends ActionMini{
    private static final Logger logger = Logger.getLogger(CombatActionMini.class);
    protected int strength;
    protected HitFunction hitFunction;
    protected DamageFunction damageFunction;
    protected CombatKeep combatKeep;
    protected boolean needPreparation;
    protected UnitTime costPreparation;

    public CombatActionMini(String name) {
        super(name);
    }

    
    public CombatActionMini(String name,
            int strength, 
            HitFunction hitFunction,
            DamageFunction damageFunction, 
            CombatKeep combatKeep, 
            boolean needPreparation, 
            UnitTime costPreparation) {
        super(name);
        this.strength = strength;
        this.hitFunction = hitFunction;
        this.damageFunction = damageFunction;
        this.combatKeep = combatKeep;
        this.needPreparation = needPreparation;
        this.costPreparation = costPreparation;
    }

    /**
     * Es el server el que comprueba que si es preparado o no.
     * @param defender
     * @param ataker
     * @param isConterAtack
     * @return
     * @throws java.lang.CloneNotSupportedException
     */
    public boolean createCombat(Mini defender,Mini ataker,boolean isConterAtack) throws CloneNotSupportedException{

        CombatEvent ce = new CombatEvent(ataker, defender, this, isConterAtack);
        EventManager.getInstance().addEvent(ce);

        return true;      
    }

    public InstanceCombat createInstanceCombat(Mini defender,Mini ataker,boolean isConterAtack) {
        Field fDefender = Game.getInstance().getBattelField().getField(defender);
        Field fAtaker = Game.getInstance().getBattelField().getField(ataker);
        if (isDefenederInRange(fDefender,fAtaker)){
            //Es alcanzable
            //Si es un conterAtack, no se permiten conter atack
            InstanceCombat instanceCombat =
                    new InstanceCombat(defender, fDefender, ataker, fAtaker,
                    this, this.hitFunction,this.damageFunction,!isConterAtack);
            return instanceCombat;
        }
        return null;
    }
    
    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public DamageFunction getDamageFunction() {
        return damageFunction;
    }

    public void setDamageFunction(DamageFunction damageFunction) {
        this.damageFunction = damageFunction;
    }

    public HitFunction getHitFunction() {
        return hitFunction;
    }

    public void setHitFunction(HitFunction hitFunction) {
        this.hitFunction = hitFunction;
    }

    public boolean isDefenederInRange(Field fDefender, Field fAtaker) {
        return this.combatKeep.isDefenderKeeped(fDefender, fAtaker);
    }

    public CombatKeep getCombatKeep() {
        return combatKeep;
    }

    public void setCombatKeep(CombatKeep combatKeep) {
        this.combatKeep = combatKeep;
    }

    public UnitTime getCostPreparation() {
        return costPreparation;
    }

    public void setCostPreparation(UnitTime costPreparation) {
        this.costPreparation = costPreparation;
    }

    public boolean isNeedPreparation() {
        return needPreparation;
    }

    public void setNeedPreparation(boolean needPreparation) {
        this.needPreparation = needPreparation;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof CombatActionMini) {
            CombatActionMini combatActionMini = (CombatActionMini) obj;
            if(this.getName().equals(combatActionMini.getName() ))
                return true;
        }
        return false;

    }

    @Override
    public CombatKeep getActionKeep() {
        return this.combatKeep;
    }

    /**
     * Usa el actiondata para determinar si es un contra ataque o si es un ataque 
     * estandar
     * @param worker
     * @param target
     */
    @Override
    public void doAction(Mini worker, Mini target) {
        try {
            logger.info("do combat action mini");
            logger.debug("Target"+target.toString());
            logger.debug("Worker"+worker);
            this.createCombat(target, worker, ((Boolean)this.getActionData()).booleanValue());
            if (this.isNeedPreparation() && this.getCostPreparation() != null) {
                Game.getInstance().getTurnTime().plus(this.getCostPreparation());
            }
        } catch (CloneNotSupportedException ex) {
           logger.error("Error",ex);
           
        }catch(Exception ex){
            logger.error("Error inesperado",ex);
        }
    }

    
    
    
    

    

}
