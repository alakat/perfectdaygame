/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.perfectday.threads.commands.kernell;

import java.util.List;
import java.util.Random;
import org.apache.log4j.Logger;
import org.perfectday.core.threads.KernellThreadGroup;
import org.perfectday.logicengine.core.Game;
import org.perfectday.logicengine.core.event.game.EndTurnEvent;
import org.perfectday.logicengine.core.event.manager.EventManager;
import org.perfectday.logicengine.model.battelfield.Field;
import org.perfectday.logicengine.model.minis.Mini;
import org.perfectday.logicengine.model.unittime.UnitTime;
import org.perfectday.logicengine.model.unittime.factories.LongUnitTimeFactory;
import org.perfectday.logicengine.movement.MasterMovement;
import org.perfectday.threads.Command;
import org.perfectday.threads.commands.graphics.AddMiniFieldAccessCommand;

/**
 * Este comando ejecuta la acción de testear si un a por ellos es ejecutado
 * correctamente. Si el checkeo de a por ellos pasa entonces se actualiza el
 * motor gráfico con las nuevas casillas adyascentes. Por otro lado si el checkeo
 * falla entonces acaba el turno con el coste asociado a fallar un "A por ellos"
 *
 * @author Miguel Angel Lopez Montellano (alakat@gmail.com)
 */
public class GoForAllCommand extends Command{

    private static Logger logger = Logger.getLogger(GoForAllCommand.class);
    /**
     * Mini que ejecuta el "A por ellos"
     */
    private Mini mini;

    /**
     *
     * @param mini
     */
    public GoForAllCommand(Mini mini) {
        this.mini = mini;
    }



    @Override
    public void trueRun() throws Exception {
        double x = (new Random(System.currentTimeMillis())).nextDouble();
        if(Game.getGame().getMasterAPorEllos().getNewProbability()>x){
            logger.info(" chekeo de a por ellos pasado!");
            List<Field> aporEllosField = MasterMovement.getInstance().
                    getAPorEllosField(Game.getGame().getSelectedMini(), Game.getGame().getBattelField());
            KernellThreadGroup.sendGraphicsCommand(new AddMiniFieldAccessCommand(aporEllosField, mini));
        }
        else{
            //Fail a por ellos
            logger.info(" chekeo de a por ellos fallado");
            UnitTime ut =
                    LongUnitTimeFactory.getInstance().
                    doMovementFailAPorEllos(Game.getGame().getSelectedMini());
            EventManager.getInstance().addEvent(new EndTurnEvent(ut, mini));
            EventManager.getInstance().eventWaitTest();
        }
    }



}
