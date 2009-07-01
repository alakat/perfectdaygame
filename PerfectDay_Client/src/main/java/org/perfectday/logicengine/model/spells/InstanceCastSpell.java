/*
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.perfectday.logicengine.model.spells;

import java.util.ArrayList;
import java.util.List;
import org.perfectday.logicengine.core.Game;
import org.perfectday.logicengine.core.MasterRoll;
import org.perfectday.logicengine.model.ReferenceObject;
import org.perfectday.logicengine.model.battelfield.Field;
import org.perfectday.logicengine.model.command.Command;
import org.perfectday.logicengine.model.minis.Mini;
import org.perfectday.logicengine.model.spells.commands.MiniIsSpellTargetCommand;
import org.perfectday.logicengine.model.state.commands.NoPowerMagicToCast;

/**
 *
 * Acción de lanza un conjuro, es en esta objeto donde se modela el lanzamiento 
 * de un conjuro.
 * @author Miguel Angel Lopez Montellano (alakat@gmail.com)
 */
public class InstanceCastSpell {
    
    private Spell spellcast;
    private Mini caster;
    private ReferenceObject target;

    public InstanceCastSpell(Spell spellcast, Mini caster, ReferenceObject target) {
        this.spellcast = spellcast;
        this.caster = caster;
        this.target = target;
    }

    
    
    
    
    
    public void doCast(List<Command> commands){
        if (target instanceof Field) {
            doCastTargetField(commands);
        }
        if (target instanceof Mini) {        
            doCastTargetMini(commands);
        }        
    }

    private void doCastTargetField(List<Command> commands) {
        Field field = (Field) target;
        Field fCaster = Game.getGame().getBattelField().getField(caster);
        
        //restamos el coste del conjuro al lanzador.Si no tiene suficiente 
        //origina un fallo automático
        int pm = this.caster.getPointMagic();
        pm = pm-this.spellcast.getMagicPointCost();
        if(pm<0){
            commands.add(new NoPowerMagicToCast());
            //No tiene para pagar el hechizo fallo automático
            commands.add(this.spellcast.getFailEffectFunction().doeffect(caster));
            return;
        }
        
        double roll =MasterRoll.getInstance().nextDouble();
        //Comprobamos que el conjuro no falla
        if(roll<this.spellcast.getFailProbability()){
            //falla
            commands.add(this.spellcast.getFailEffectFunction().doeffect(caster));
            return;
        }
        
        if( this.spellcast.getSpellKeep().isDefenderKeeped(fCaster, field,Game.getGame()) ){
            List<Mini> targetMinis = new ArrayList<Mini>();
            List<Field> fieldTarget = this.spellcast.getSpellEffectArea().getFieldKeeped(fCaster,Game.getGame());
            for (Field field1 : fieldTarget) {
                if(field1.getMiniOcupant()!=null)
                    targetMinis.add(field1.getMiniOcupant());
            }
            for (Mini mini : targetMinis) {
                commands.add(new MiniIsSpellTargetCommand(mini, spellcast));
                roll = MasterRoll.getInstance().nextDouble();
                double mImpact = this.spellcast.getHitfunction().modifiedDamage(caster, mini, roll);
                this.spellcast.getEffectFunction().doEffect(
                        commands, mImpact,caster, spellcast, mini);
            }
            
        }
    }

    private void doCastTargetMini(List<Command> commands) {
        Field field = (Field) Game.getGame().getBattelField().getField((Mini) this.target);
        Field fCaster = Game.getGame().getBattelField().getField(caster);
        
        //restamos el coste del conjuro al lanzador.Si no tiene suficiente 
        //origina un fallo automático
        int pm = this.caster.getPointMagic();
        pm = pm-this.spellcast.getMagicPointCost();
        if(pm<0){
            //No tiene para pagar el hechizo fallo automático
            commands.add(this.spellcast.getFailEffectFunction().doeffect(caster));
            return;
        }
        
        double roll =MasterRoll.getInstance().nextDouble();
        //Comprobamos que el conjuro no falla
        if(roll<this.spellcast.getFailProbability()){
            //falla
            commands.add(this.spellcast.getFailEffectFunction().doeffect(caster));
            return;
        }
        if( this.spellcast.getSpellKeep().isDefenderKeeped(fCaster, field,Game.getGame()) ){
            Mini mini = (Mini) this.target;
            commands.add(new MiniIsSpellTargetCommand(mini, spellcast));
                roll = MasterRoll.getInstance().nextDouble();
                double mImpact = this.spellcast.getHitfunction().modifiedDamage(caster, mini, roll);
                this.spellcast.getEffectFunction().doEffect(
                        commands, mImpact,caster, spellcast, mini);
        }
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
    
    

}
