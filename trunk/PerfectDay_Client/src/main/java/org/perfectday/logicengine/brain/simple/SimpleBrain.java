/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.perfectday.logicengine.brain.simple;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.perfectday.logicengine.brain.AbstractBrain;
import org.perfectday.logicengine.brain.Turn;
import org.perfectday.logicengine.core.Game;
import org.perfectday.logicengine.core.player.Player;
import org.perfectday.logicengine.model.battelfield.Field;
import org.perfectday.logicengine.model.command.Command;
import org.perfectday.logicengine.model.command.move.MovementCommand;
import org.perfectday.logicengine.model.minis.Mini;
import org.perfectday.logicengine.model.minis.action.ActionMini;
import org.perfectday.logicengine.model.minis.action.combat.CombatActionMini;
import org.perfectday.logicengine.model.unittime.UnitTime;
import org.perfectday.logicengine.model.unittime.factories.LongUnitTimeFactory;
import org.perfectday.main.laboratocGUI.LaboratoryGUI;

/**
 *
 * @author Miguel Angel Lopez Montellano ( alakat@gmail.com )
 */
public class SimpleBrain extends AbstractBrain {

    
    public SimpleBrain(Properties p) {
        super(p);
    }
    
    @Override
    public Turn getTrun(Mini mini,UnitTime ut) {
        Turn  t = new Turn();
        List<Command> commands = t.getAcciones();
        /*
         * 1ºPensamos con el cerebro de juego y cargamos las funciones de selección 
         * Adecuadas
         */
        
        
        this.gameBrain.setBestFunction();
        /*
         *Mover si es necesario
         */
        Field field = this.motionBrain.getBestField(mini);
        if( field !=null){
            //Movemos
            Field oldField = Game.getInstance().getBattelField().getField(mini);
            oldField.setMiniOcupant(null);
            field.setMiniOcupant(mini);
            commands.add(new MovementCommand(mini, field));
            //TODO: Búsqueda de "A por ellos"            
            /*
             *Si hemos movido pintar el campo de batalla nuevo
             */
            LaboratoryGUI.me.getJBattelField1().repaint();
            ut.plus(LongUnitTimeFactory.getInstance().doMovementAction(mini));
        }else{          
            ut.plus(LongUnitTimeFactory.getInstance().doNothing(mini));
            field = Game.getInstance().getBattelField().getField(mini);
        }
        //Atacar si tenemos enemigos al alcance
        ActionMini selectedAction = this.actionBrain.getBestAction(mini);
        if (enemyNear(selectedAction, field, 
                Game.getInstance().getPlayerByMini(mini))){
            try {
                Mini targetMini = getTargetMini(field, selectedAction,mini);
                ((CombatActionMini) selectedAction).createCombat(targetMini, mini, false);

                ut.plus(LongUnitTimeFactory.getInstance().doCombat(Game.getInstance().getSelectedMini()));
                if (((CombatActionMini) selectedAction).isNeedPreparation() && ((CombatActionMini) selectedAction).getCostPreparation() != null) {
                    ut.plus(((CombatActionMini) selectedAction).getCostPreparation());
                }
            } catch (CloneNotSupportedException ex) {
                org.apache.log4j.Logger.getLogger(SimpleBrain.class).
                        error("Error procesando un combate:"+ex.getClass().getName(),ex);
                System.exit(0);
            }
            
        }        
        //devolvemos la instancia de un Turno.
        t.setUt(ut);
        return t;
    }

    private boolean enemyNear(ActionMini primaryAction, Field f,Player miniOwner) {
        if (primaryAction instanceof CombatActionMini) {
            CombatActionMini combatActionMini = (CombatActionMini) primaryAction;
            List<Field> keepFields = 
                    combatActionMini.getCombatKeep().getFieldKeeped(f);
            for(Field field : keepFields){
                if(field.getMiniOcupant()!=null){
                    Player p = Game.getInstance().getPlayerByMini(field.getMiniOcupant());
                    if ( !p.equals(miniOwner))
                        return true;
                }
            }

        }
        return false;

    }

    private Mini getTargetMini(Field field, ActionMini selectedAction,Mini mini) {
        List<Field> fields = ((CombatActionMini)selectedAction).
                getCombatKeep().getFieldKeeped(field);
        Player p = Game.getInstance().getPlayerByMini(mini);
        Mini targetMini=null;
        List<Mini> enemies= new ArrayList<Mini>();
        for (Field f : fields) {
            if ( f.getMiniOcupant()!=null &&
                    !(Game.getInstance().getPlayerByMini(
                    f.getMiniOcupant()).equals(p)))
                enemies.add(f.getMiniOcupant());
        }
        targetMini = enemies.get(0);
        for (Mini mini1 : enemies) {
            if((mini1.getVitality()-mini1.getDamage())<
                    (targetMini.getVitality()-targetMini.getDamage()))
                targetMini = mini1;
        }        
        return targetMini;      
    }
    

}
