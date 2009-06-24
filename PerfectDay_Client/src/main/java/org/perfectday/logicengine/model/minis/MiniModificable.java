/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.perfectday.logicengine.model.minis;

import java.util.ArrayList;
import java.util.List;
import org.perfectday.logicengine.model.minis.action.ActionMini;
import org.perfectday.logicengine.model.minis.action.combat.CombatActionMini;
import org.perfectday.logicengine.model.state.State;
import org.perfectday.logicengine.model.minis.support.Support;
import org.perfectday.logicengine.model.state.MiniAttribute;
import org.perfectday.logicengine.model.state.PasiveState;

/**
 * Es la abtracción de un mini donde sus caracteristicas pueden ser modificadas.
 * Sobre escribe los métodos Get para modificar las caracteristicas en función 
 * de lo especificados por sus state.
 * @author Miguel Angel Lopez Montellano ( alakat@gmail.com )
 */
public class MiniModificable  extends Mini{

    private List<State> states;

    public MiniModificable(String name) {
        super(name);
        this.states = new ArrayList<State>();        
    }
    
    public MiniModificable(int atak, int defense, int strength, int resistance, int vitality, double iniciative, double magicAfinity, int moviment, MiniLevel miniLevel, MiniType miniType, ActionMini primaryAction, ActionMini secondaryAction, Support support, boolean conterAtack, CombatActionMini aconterAtack, String name) {
        super(atak, defense, strength, resistance, vitality, iniciative, magicAfinity, moviment, miniLevel, miniType, primaryAction, secondaryAction, support, conterAtack, aconterAtack, name);
        this.states = new ArrayList<State>();        
    }
    
    @Override
    public void removeState(State state) {
        states.remove(state);
    }

    @Override
    public void addState(State state) {
        states.add(state);
    }

    @Override
    public int getAtak() {
        return this.appliState(MiniAttribute.ATACK,new Integer(super.getAtak())).intValue();
    }

    @Override
    public CombatActionMini getConterAtackAction() {
        return super.getConterAtackAction();
    }

    @Override
    public double getDamage() {
        return super.getDamage();
    }

    @Override
    public int getDefense() {
        return this.appliState(MiniAttribute.DEFENSE,new Integer(super.getDefense())).intValue();
    }

    @Override
    public double getIniciative() {
        return this.appliState(MiniAttribute.INICIATIVE,new Double(super.getIniciative())).doubleValue();
    }

    @Override
    public double getMagicAfinity() {
        return this.appliState(MiniAttribute.MAGIC_AFFINITY,new Double(super.getMagicAfinity())).doubleValue();
    }

    @Override
    public MiniLevel getMiniLevel() {
        return super.getMiniLevel();
    }

    @Override
    public MiniType getMiniType() {
        return super.getMiniType();
    }

    @Override
    public int getMoviment() {
        return this.appliState(MiniAttribute.MOVEMENT,new Integer(super.getMoviment())).intValue();
    }

    @Override
    public ActionMini getPrimaryAction() {
        return super.getPrimaryAction();
    }

    @Override
    public int getResistance() {
        return this.appliState(MiniAttribute.RESISTENCE,new Integer(super.getResistance())).intValue();
    }

    @Override
    public ActionMini getSecondaryAction() {
        return super.getSecondaryAction();
    }

    @Override
    public int getStrength() {
        return this.appliState(MiniAttribute.STRENGTH,new Integer(super.getStrength())).intValue();
    }

    @Override
    public Support getSupport() {
        return super.getSupport();
    }

    @Override
    public int getVitality() {
        return this.appliState(MiniAttribute.VITALITY,new Integer(super.getVitality())).intValue();
    }

    public Number appliState(MiniAttribute attribute, Number value){     
        for (State state : states) {
            if (state instanceof PasiveState) {
                PasiveState pasiveState = (PasiveState) state;
                if(pasiveState.getAttribute().equals(attribute)){
                   value= (Number) pasiveState.doState(value, null);
                }
            }
        }        
        return value;     
    }

}
