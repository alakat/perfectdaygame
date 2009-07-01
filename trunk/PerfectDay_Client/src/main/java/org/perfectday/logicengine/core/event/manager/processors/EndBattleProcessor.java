/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.perfectday.logicengine.core.event.manager.processors;

import javax.swing.JOptionPane;
import org.apache.log4j.Logger;
import org.perfectday.logicengine.core.Game;
import org.perfectday.logicengine.core.event.Event;
import org.perfectday.logicengine.core.event.game.EndBattleEvent;
import org.perfectday.logicengine.core.event.manager.EventManager;

/**
 * Procesador del evento fin de batalla.
 * @author Miguel Angel Lopez Montellano (alakat@gmail.com)
 */
public class EndBattleProcessor implements Processor {

    private static final Logger logger = Logger.getLogger(EndBattleProcessor.class);
    @Override
    public void eventRequest(Event event) {
        if(Game.getGame().isServer()){
            logger.info("Server fin de batalla.");
            event = event.generateEventResponse();
            EventManager.getInstance().addEvent(event);
            EventManager.getInstance().eventWaitTest();
        }
    }

    @Override
    public void eventResponse(Event event) {
        logger.info("Response Evento <<End Battlarg3e>>");
        EndBattleEvent ebe = (EndBattleEvent)event;
        JOptionPane.showMessageDialog(null, ebe.getMsgWiner(),"Fin de la partida" , JOptionPane.INFORMATION_MESSAGE);
        Game.getGame().closeGame();
        
    }

}
