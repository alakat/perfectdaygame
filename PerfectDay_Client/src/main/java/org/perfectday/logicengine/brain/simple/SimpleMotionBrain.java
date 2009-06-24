/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.perfectday.logicengine.brain.simple;

import java.util.List;
import org.perfectday.logicengine.brain.AbstractMotionBrain;
import org.perfectday.logicengine.core.Game;
import org.perfectday.logicengine.core.player.Player;
import org.perfectday.logicengine.model.battelfield.Field;
import org.perfectday.logicengine.model.minis.Mini;
import org.perfectday.logicengine.model.minis.action.ActionMini;
import org.perfectday.logicengine.model.minis.action.combat.CombatActionMini;
import org.perfectday.logicengine.movement.MasterMovement;

/**
 * Sistema locomotor del SimpleBrain donde moveremos siempre en dirección
 * al más cercano.
 * @author Miguel Angel Lopez Montellano ( alakat@gmail.com )
 */
public class SimpleMotionBrain extends AbstractMotionBrain{

    @Override
    public Field getBestField(Mini mini) {
        Game game = Game.getInstance();
        Player miniOwner = game.getPlayerByMini(mini);
        Field miniPosition = game.getBattelField().getField(mini);        
        //Comprobamos que no haya enemigos al alcance de nuestro ataque
        if (enemyNear(mini.getPrimaryAction(),miniPosition,miniOwner))
            return null; //Hay enemigos al alcance del ataque primario
        //Obtenemos la lista de field accesibles.
        List<Field> fields = 
                MasterMovement.getInstance().
                getNearbyField(mini, game.getBattelField(), miniPosition);
        //Calculamos sus calidades
        double[] cualities = new double[fields.size()];
        int i=0;
        org.apache.log4j.Logger.getLogger(SimpleMotionBrain.class).
                info("Limpiamos la lista de enemigos y fields de enemigos");
        this.movedFunction.initFunction();
        for (Field f : fields) {
            cualities[i] = this.movedFunction.getCualityOfField(f, mini);
            i++;
        }
        double d = Double.MAX_VALUE;
        int index = 0;
        //Devolvemos la mejor.--> El menor nuemero ya que es la distancia.
        for (int j=0; j<cualities.length;j++) {
            double e= cualities[j];
            if(e<d){
                index=j;
                d=e;
            }
        }        
        if (fields.isEmpty())
            return null;
        return fields.get(index);
        
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

    
}
