/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.perfectday.logicengine.core.event.manager.processors;

import org.apache.log4j.Logger;
import org.perfectday.communication.masterCommunication.MasterCommunication;
import org.perfectday.logicengine.core.Game;
import org.perfectday.logicengine.core.event.Event;
import org.perfectday.logicengine.core.event.manager.EventManager;
import org.perfectday.logicengine.core.event.state.ClearStateEvent;
import org.perfectday.logicengine.model.minis.MiniModificable;

/**
 * Procesador para el evento ClearState, que limpia los stados de un mini.
 * @author Miguel Angel Lopez Montellano (alakat@gmail.com)
 */
public class ClearStateProcessor implements Processor{

    private static final Logger logger = Logger.getLogger(ClearStateProcessor.class);
    
    @Override
    public void eventRequest(Event event) {
        if (!Game.getGame().isServer()){
            logger.warn("El cliente no debería procesar este evento en Request.");
        }
        event = event.generateEventResponse();
        EventManager.getInstance().addEvent(event);
        EventManager.getInstance().eventWaitTest();
        if(Game.getGame().isServer()){
            Game.getGame().getMasterCommunication().sendEvent(event);
        }
    }

    @Override
    public void eventResponse(Event event) {
       ClearStateEvent cse = (ClearStateEvent) event;
       if (Game.getGame().isServer()){
           logger.info("Servidor..");
            MiniModificable miniModificable = (MiniModificable) cse.getAffectedMini();
            miniModificable.removeState(cse.getState());
            Game.getGame().getActivationStack().clearState(cse.getState());
            Game.getGame().nextAccident();
       }else{
           logger.info("Cliente...");
           Game.getGame().getActivationStack().clearState(cse.getState());
           Game.getGame().getPerfectDayGUI().redraw();
       }       
    }

}
