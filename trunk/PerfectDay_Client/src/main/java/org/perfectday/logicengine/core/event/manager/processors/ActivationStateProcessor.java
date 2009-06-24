/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.perfectday.logicengine.core.event.manager.processors;

import org.apache.log4j.Logger;
import org.perfectday.communication.masterCommunication.MasterCommunication;
import org.perfectday.infomation.Journalist;
import org.perfectday.logicengine.core.Game;
import org.perfectday.logicengine.core.event.Event;
import org.perfectday.logicengine.core.event.manager.EventManager;
import org.perfectday.logicengine.core.event.state.ActivationStateEvent;

/**
 * Porcesador de activaciones de eventos.
 * @author Miguel Angel Lopez Montellano (alakat@gmail.com)
 */
public class ActivationStateProcessor implements Processor {

    private static final Logger logger = Logger.getLogger(ActivationStateProcessor.class);
    
    @Override
    public void eventRequest(Event event) {
        logger.info("ActivationStateProcessor request");
        if(!Game.getInstance().isServer()){
            logger.warn("El cliente no debería ejecutar un ActivationStateEvent en modo Request");
        }
        ActivationStateEvent ase = (ActivationStateEvent)event;
        ase.getState().doState(null, ase.getCommands());
        event  = event.generateEventResponse();
        MasterCommunication.getInstance().sendEvent(event);
        EventManager.getInstance().addEvent(event);
        EventManager.getInstance().eventWaitTest();
    }

    @Override
    public void eventResponse(Event event) {
        logger.info("ActivationStateProcessor response");
        ActivationStateEvent ase = (ActivationStateEvent)event;
        Journalist.getInstance().infoCombat(ase.getCommands()); //Informamos del combate
            if(Game.getInstance().isServer()){
            Game.getInstance().nextAccident();
        }else{
            //quitamos el evento superior de la pila de activación 
            Game.getInstance().getActivationStack().pop();
            CommandConsumer.process(ase.getCommands());
            Journalist.getInstance().infoCombat(ase.getCommands());
        }
    }

}
