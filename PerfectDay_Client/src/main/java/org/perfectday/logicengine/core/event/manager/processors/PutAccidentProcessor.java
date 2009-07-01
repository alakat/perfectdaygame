/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.perfectday.logicengine.core.event.manager.processors;

import org.apache.log4j.Logger;
import org.perfectday.communication.masterCommunication.MasterCommunication;
import org.perfectday.logicengine.core.Game;
import org.perfectday.logicengine.core.event.Event;
import org.perfectday.logicengine.core.event.accident.PutAccidentEvent;
import org.perfectday.logicengine.core.event.manager.EventManager;
import org.perfectday.logicengine.model.activationstack.accidents.Accident;

/**
 *
 * @author Miguel Angel Lopez Montellano (alakat@gmail.com)
 */
public class PutAccidentProcessor implements Processor {

    private static final Logger logger = Logger.getLogger(PutAccidentProcessor.class);
    
    @Override
    public void eventRequest(Event event) {
        if(!Game.getGame().isServer()){
            logger.info("En cliente. Un resquest nunca debería ocurrir");
            event = event.generateEventResponse();
            Game.getGame().getMasterCommunication().sendEvent(event);
        }else{
            logger.info("Server.");
            event=event.generateEventResponse();
            EventManager.getInstance().addEvent(event);
            EventManager.getInstance().eventWaitTest();
            Game.getGame().getMasterCommunication().sendEvent(event);
        }
        
    }

    @Override
    public void eventResponse(Event event) {
        logger.info("Response igual en cliente y servidor");
        Accident acc = ((PutAccidentEvent)event).getAccident();
        Game.getGame().getActivationStack().put(acc);
    }

    
}
