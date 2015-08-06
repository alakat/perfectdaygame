package org.perfectday.logicengine.combat;

import org.perfectday.logicengine.model.minis.support.modifiers.Modifier;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.perfectday.logicengine.combat.core.functions.DamageFunction;
import org.perfectday.logicengine.combat.core.functions.HitFunction;
import org.perfectday.logicengine.combat.model.SupportStack;
import org.perfectday.logicengine.core.Game;
import org.perfectday.logicengine.core.MasterRoll;
import org.perfectday.logicengine.model.activationstack.*;
import org.perfectday.logicengine.model.battelfield.Field;
import org.perfectday.logicengine.model.command.Command;
import org.perfectday.logicengine.model.command.combat.CombatActionCommand;
import org.perfectday.logicengine.model.command.combat.CombatResolutionCommand;
import org.perfectday.logicengine.model.command.combat.ConterAttackCommand;
import org.perfectday.logicengine.model.minis.Mini;
import org.perfectday.logicengine.model.minis.action.ActionMini;
import org.perfectday.logicengine.model.minis.action.combat.CombatActionMini;
import org.perfectday.logicengine.model.minis.action.combat.StateCombatActionMini;
import org.perfectday.logicengine.model.minis.support.Support;
import org.perfectday.logicengine.model.state.factories.StateFactory;
import org.perfectday.main.dummyengine.DummyGraphicsEngine;

/**
 * Instancia de un combate entre dos minis
 * @author Miguel Angel Lopez Montellano (alakat@gmail.com)
 */
public class InstanceCombat extends CarringOut {

    private static final Logger logger = Logger.getLogger(InstanceCombat.class);
    private Mini defensor;
    private Field defensorField;
    private Mini atacker;
    private Field atackerField;
    private ActionMini atack;
    private SupportStack supportStack;
    private HitFunction hitFunction;
    private DamageFunction damageFunction;
    private List<Command> commandList;
    private List<Modifier> modifierAtaquer;
    private List<Modifier> modifierDefender;
    private boolean ableConterAtack;

    public InstanceCombat() {
    }
    
    
    
    
    public InstanceCombat(Mini defensor,
            Field defensorField,
            Mini atacker,
            Field atackerField,
            ActionMini atack,
            HitFunction hitFunction,
            DamageFunction damageFunction,
            boolean conterAtack) {
        this.defensor = defensor;
        this.defensorField = defensorField;
        this.atacker = atacker;
        this.atackerField = atackerField;
        this.atack = atack;
        this.hitFunction = hitFunction;
        this.damageFunction = damageFunction;
        this.supportStack = new SupportStack();
        this.commandList = new ArrayList<Command>();
        this.modifierAtaquer = new ArrayList<Modifier>();
        this.modifierDefender = new ArrayList<Modifier>();
        this.ableConterAtack = conterAtack;
    }
    
    public List<Command> doCombat(){       
        //add combact command  to inform de combat
        this.commandList.add(new CombatActionCommand(atacker, defensor,(CombatActionMini) atack));
        Game.getPerfectDayGUI().addInfo("Combate "+this.atack.toString()+
                " con "+this.atack+" a"+this.defensor.toString());
        logger.info("Combate "+this.atack.toString()+
                " con "+this.atack+" a"+this.defensor.toString());
        //modify combat in support function        
        applySupporStack();
        //do commbat
        Game.getPerfectDayGUI().addInfo("ResolverCombate..");
        resolveCommbat();
        //registerState
        if (this.atack instanceof StateCombatActionMini &&
                this.defensor.isAlive()) {
            
            StateCombatActionMini stateCombatActionMini =
                    (StateCombatActionMini) this.atack;
            StateFactory.getInstance().registerState(defensor, stateCombatActionMini.getState(),commandList);

        }
        
        //defender conter atack
        Game.getPerfectDayGUI().addInfo("Contra ataque:"+isConterAtack());
        if (isConterAtack())
            //do conteratack
            resolveConterAtack();
        return commandList;
        
    }

    
    /**
     * Aplicamos los apoyos de la pila de apoyos. 
     * Se crean los subcombates necesarios
     */
    private void applySupporStack() {
        Game.getPerfectDayGUI().addInfo("Apoyos:["+this.supportStack.getStackList().size()+"]");
        //Se refresca la lista de apoyos 
        this.supportStack.buildSupportStack(defensorField, atackerField);
        
        Iterator<Support> iterator = this.supportStack.iterator();
        while(iterator.hasNext()){
            Support support = iterator.next();
            logger.info("Apoyo:"+support.toString());
            Game.getPerfectDayGUI().addInfo("Aplicamos apoyo :"+support);
            this.commandList.addAll(support.doSupport(this));            
        }
    }

    /**
     * Indica si en el combate existe contra ataque. Defensoer vivo, y posee 
     * contra ataque.
     * @return
     */
    private boolean isConterAtack() {
        return this.defensor.isAlive() && this.defensor.isConterAtack() && this.ableConterAtack;
    }

    /**
     * efectua el ataque, modifica el daño.
     */
    private void resolveCommbat() {   
        Game.getPerfectDayGUI().addInfo("Combat:");
        for(Modifier m: this.modifierAtaquer)
            m.doModifier(atacker);
        for(Modifier m: this.modifierDefender)
            m.doModifier(defensor);
        logger.info("Atacante:"+atacker);
        logger.info("Defensor:"+defensor);
        double lucky = MasterRoll.getInstance().nextDouble();
        double modifierDamage = this.hitFunction.modifiedDamage(atacker, defensor,lucky);
        logger.info("Modifier damage:"+modifierDamage);
        Game.getPerfectDayGUI().addInfo("Modificador de daño:"+modifierDamage);
        
        double damage = this.damageFunction.getDamageAtack(atacker, defensor, modifierDamage,(CombatActionMini) this.atack,MasterRoll.getInstance().nextDouble());
        Game.getPerfectDayGUI().addInfo("Daño:"+damage);
        logger.info("Daño:"+damage);
        this.defensor.setDamage(damage+this.defensor.getDamage());
        for(Modifier m: this.modifierAtaquer)
            m.reverModifier(atacker);
        for(Modifier m: this.modifierDefender)
            m.reverModifier(defensor);
        //Add command to informatión 
        logger.warn("Lucky:"+(lucky-0.5)+":-->"+((lucky-0.5)<0));
        this.commandList.add(
                new CombatResolutionCommand(atacker, defensor, damage, !defensor.isAlive(),((lucky-0.5)<0)));
    }

    /**
     * crea una instancia de contra ataque y lo ejecuta.
     */
    private void resolveConterAtack() {
        try {
            this.commandList.add(new ConterAttackCommand());
            Game.getPerfectDayGUI().addInfo("resolver Contra ataque");
            CombatActionMini conterAtack = this.defensor.getConterAtackAction();
            conterAtack.createCombat(this.atacker, this.defensor,true);
        } catch (CloneNotSupportedException ex) {
            logger.error("In resolveConterAtack", ex);
        }
                
    }

    public ActionMini getAtack() {
        return atack;
    }

    public Mini getAtacker() {
        return atacker;
    }

    public Field getAtackerField() {
        return atackerField;
    }

    public List<Command> getCommandList() {
        return commandList;
    }

    public DamageFunction getDamageFunction() {
        return damageFunction;
    }

    public Mini getDefensor() {
        return defensor;
    }

    public Field getDefensorField() {
        return defensorField;
    }

    public HitFunction getHitFunction() {
        return hitFunction;
    }

    public SupportStack getSupportStack() {
        return supportStack;
    }

    public List<Modifier> getModifierAtaquer() {
        return modifierAtaquer;
    }

    public void setModifierAtaquer(List<Modifier> modifierAtaquer) {
        this.modifierAtaquer = modifierAtaquer;
    }

    public List<Modifier> getModifierDefender() {
        return modifierDefender;
    }

    public void setModifierDefender(List<Modifier> modifierDefender) {
        this.modifierDefender = modifierDefender;
    }

    public void setDefensor(Mini defensor) {
        this.defensor = defensor;
    }

    public void setAbleConterAtack(boolean ableConterAtack) {
        this.ableConterAtack = ableConterAtack;
    }
    
}

