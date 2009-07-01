/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.perfectday.logicengine.core.event.manager.processors;

import org.apache.log4j.Logger;
import org.perfectday.communication.masterCommunication.MasterCommunication;
import org.perfectday.logicengine.core.Game;
import org.perfectday.logicengine.core.event.Event;
import org.perfectday.logicengine.core.event.game.PutActionEvent;
import org.perfectday.logicengine.core.event.manager.EventManager;
import org.perfectday.logicengine.model.activationstack.accidents.Activation;

/**
 *
 * Procesa un evento de apilamiento de un evento en la pila.
 * @author Miguel Angel Lopez Montellano (alakat@gmail.com)
 */
public class PutActivationProcessor implements  Processor{

    private Logger logger = Logger.getLogger(PutActivationProcessor.class);
    
    @Override
    public void eventRequest(Event event) {
        logger.error("No debe existir un request");
        event = event.generateEventResponse();
        EventManager.getInstance().addEvent(event);
        EventManager.getInstance().eventWaitTest();
    }

    @Override
    public void eventResponse(Event event) {
        logger.info("response");
        if(Game.getGame().isServer()){
            Game.getGame().getMasterCommunication().sendEvent(event);
        }
        PutActionEvent popActionEvent = (PutActionEvent)event;
        Activation activation = popActionEvent.getActivation();
        Game.getGame().getActivationStack().put(activation);
        Game.getPerfectDayGUI().redraw();
        logger.debug( "Activation Stack{"+ Game.getGame().getActivationStack().toString()+"}");
    }

}
