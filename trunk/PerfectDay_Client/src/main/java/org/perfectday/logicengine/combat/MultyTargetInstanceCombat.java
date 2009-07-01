/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.perfectday.logicengine.combat;

import java.util.List;
import org.apache.log4j.Logger;
import org.perfectday.logicengine.combat.core.functions.damage.DistanceDamageFunction;
import org.perfectday.logicengine.combat.core.functions.hit.DistanceHitFunction;
import org.perfectday.logicengine.combat.model.combatkeep.CombatKeep;
import org.perfectday.logicengine.core.Game;
import org.perfectday.logicengine.model.battelfield.Field;
import org.perfectday.logicengine.model.command.Command;
import org.perfectday.logicengine.model.minis.Mini;
import org.perfectday.logicengine.model.minis.action.ActionMini;

/**
 * Combate que afecta a varias unidades.
 * @author Miguel Angel Lopez Montellano ( alakat@gmail.com )
 */
public class MultyTargetInstanceCombat extends InstanceCombat {

    private CombatKeep effectArea;
    private Field targetField;
    private static Logger logger = 
            Logger.getLogger(MultyTargetInstanceCombat.class);
    public MultyTargetInstanceCombat(Mini defensor,
            Field defensorField,
            Mini atacker,
            Field atackerField,
            ActionMini atack,
            DistanceHitFunction hitFunction,
            DistanceDamageFunction damageFunction,
            boolean conterAtack,
            CombatKeep effectArea) {
        super(defensor, 
                defensorField,
                atacker, 
                atackerField, 
                atack, 
                hitFunction, 
                damageFunction, 
                conterAtack);
        this.effectArea=effectArea;
        this.targetField = defensorField;
        hitFunction.setFocus(targetField);
        damageFunction.setFocus(targetField);
    }

    @Override
    public List<Command> doCombat() {
        List<Field> effectField = this.effectArea.getFieldKeeped(targetField,Game.getGame());
        effectField.add(targetField);
        for (Field field : effectField) {
            logger.info("Afecta el field: "+field.toString());
            if(field.getMiniOcupant()!=null){
                logger.info("Mini atacado:"+field.getMiniOcupant().getName()+","+field.getMiniOcupant().toString());
                ((DistanceHitFunction)this.getHitFunction()).setTarger(field);
                ((DistanceDamageFunction)this.getDamageFunction()).setTarget(field);
                this.setDefensor(field.getMiniOcupant());
                this.setAbleConterAtack(false);
                super.doCombat();
            }            
        }
        return this.getCommandList();

    }
    
    
    

    
}
