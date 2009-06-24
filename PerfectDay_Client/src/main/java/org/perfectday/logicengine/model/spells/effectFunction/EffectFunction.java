/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.perfectday.logicengine.model.spells.effectFunction;

import java.util.List;
import org.perfectday.logicengine.model.command.Command;
import org.perfectday.logicengine.model.minis.Mini;
import org.perfectday.logicengine.model.spells.Spell;
import org.perfectday.logicengine.model.state.State;
import org.perfectday.logicengine.model.state.commands.RegisterStateCommand;
import org.perfectday.logicengine.model.state.factories.StateFactory;

/**
 * Realiza el efecto de un conjuro.
 *  Causa daño o añade estados.
 * @author Miguel Angel Lopez Montellano ( alakat@gmail.com )
 */
public abstract class EffectFunction {

    /**
     * Realiza el efecto concreto a la clase
     * @param commands
     * @param impactModification
     * @param caster
     * @param spell
     * @param target
     */
    protected  abstract void doAbstractEffect(List<Command> commands,double impactModification,Mini caster,Spell spell, Mini target);
    
    public void doEffect(List<Command> commands,double impactModification,Mini caster,Spell spell, Mini target){
       this.doAbstractEffect(commands, impactModification, caster, spell, target);
       this.doAppliEffect(commands, target, spell.getSpellState());
    }
    public void doAppliEffect(List<Command> commands,Mini mini,State state){
        if(state!=null){
            commands.add(new RegisterStateCommand(state, mini));
            StateFactory.getInstance().registerState(mini, state,commands);
        }        
    }
}
