/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.perfectday.logicengine.model.spells;

import org.perfectday.logicengine.combat.core.functions.HitFunction;
import org.perfectday.logicengine.combat.model.combatkeep.CombatKeep;
import org.perfectday.logicengine.model.spells.effectFunction.EffectFunction;
import org.perfectday.logicengine.model.spells.failEffectFunction.FailEffectFunction;
import org.perfectday.logicengine.model.state.State;
import org.perfectday.logicengine.model.unittime.UnitTime;

/**
 * Representa la abstracción de un conjuro en PerfectDay.
 * Todo conjuro estÃ¡ descrito por :
 *  + Nombre del conjuto
 *  + Función de impacto.
 *      - Devuelve un valor de modificación del efecto en el conjuro basado en
 *      como impacta el conjuro en el objetivo (Conjuros de ataque)
 *  + Tipo del conjuro 
 *  + Elemento del conjuro 
 *  + Alcance
 *  + Ãrea de efecto
 *  + Coste de preparación
 *  + Estado que causa.
 *  + Función de efecto si fracasa el conjuro
 * @author Miguel Angel Lopez Montellano ( alakat@gmail.com )
 */
public class Spell {
    
    private String name;
    private HitFunction hitfunction;
    private EffectFunction effectFunction;
    private SpellType spellType;
    private SpellElement spellElement;
    private CombatKeep spellKeep;
    private CombatKeep spellEffectArea;
    private UnitTime spellCastTime;
    private State spellState;
    private double failProbability;
    private FailEffectFunction failEffectFunction;
    private int magicPointCost;

    public Spell() {
    }

    
    
    
    
    public Spell(String name, HitFunction hitfunction, EffectFunction effectFunction, SpellType spellType, SpellElement spellElement, CombatKeep spellKeep, CombatKeep spellEffectArea, UnitTime spellCastTime, State spellState, double failProbability, FailEffectFunction failEffectFunction, int magicPointCost) {
        this.name = name;
        this.hitfunction = hitfunction;
        this.effectFunction = effectFunction;
        this.spellType = spellType;
        this.spellElement = spellElement;
        this.spellKeep = spellKeep;
        this.spellEffectArea = spellEffectArea;
        this.spellCastTime = spellCastTime;
        this.spellState = spellState;
        this.failProbability = failProbability;
        this.failEffectFunction = failEffectFunction;
        this.magicPointCost = magicPointCost;
    }


   

   

    public EffectFunction getEffectFunction() {
        return effectFunction;
    }

    public void setEffectFunction(EffectFunction effectFunction) {
        this.effectFunction = effectFunction;
    }

    public HitFunction getHitfunction() {
        return hitfunction;
    }

    public void setHitfunction(HitFunction hitfunction) {
        this.hitfunction = hitfunction;
    }

    public UnitTime getSpellCastTime() {
        return spellCastTime;
    }

    public void setSpellCastTime(UnitTime spellCastTime) {
        this.spellCastTime = spellCastTime;
    }

    public CombatKeep getSpellEffectArea() {
        return spellEffectArea;
    }

    public void setSpellEffectArea(CombatKeep spellEffectArea) {
        this.spellEffectArea = spellEffectArea;
    }

    public SpellElement getSpellElement() {
        return spellElement;
    }

    public void setSpellElement(SpellElement spellElement) {
        this.spellElement = spellElement;
    }

    public CombatKeep getSpellKeep() {
        return spellKeep;
    }

    public void setSpellKeep(CombatKeep spellKeep) {
        this.spellKeep = spellKeep;
    }

    public SpellType getSpellType() {
        return spellType;
    }

    public void setSpellType(SpellType spellType) {
        this.spellType = spellType;
    }

    public FailEffectFunction getFailEffectFunction() {
        return failEffectFunction;
    }

    public void setFailEffectFunction(FailEffectFunction failEffectFunction) {
        this.failEffectFunction = failEffectFunction;
    }

    public double getFailProbability() {
        return failProbability;
    }

    public void setFailProbability(double failProbability) {
        this.failProbability = failProbability;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public State getSpellState() {
        return spellState;
    }

    public void setSpellState(State spellState) {
        this.spellState = spellState;
    }

    public int getMagicPointCost() {
        return magicPointCost;
    }

    public void setMagicPointCost(int magicPointCost) {
        this.magicPointCost = magicPointCost;
    }
    
    
    
}
