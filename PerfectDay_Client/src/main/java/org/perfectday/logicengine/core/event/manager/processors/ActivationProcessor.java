/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.perfectday.logicengine.core.event.manager.processors;

import org.apache.log4j.Logger;
import org.perfectday.communication.masterCommunication.MasterCommunication;
import org.perfectday.logicengine.core.Game;
import org.perfectday.logicengine.core.event.Event;
import org.perfectday.logicengine.core.event.accident.ActivationEvent;
import org.perfectday.logicengine.core.event.manager.EventManager;
import org.perfectday.logicengine.model.activationstack.accidents.Accident;
import org.perfectday.logicengine.model.minis.Mini;
import org.perfectday.logicengine.model.unittime.LongUnitTime;
import org.perfectday.logicengine.model.unittime.factories.LongUnitTimeFactory;
import org.perfectday.logicengine.movement.MasterAPorEllos;

/**
 * Procesa los eventos de activación de un mini.
 * @author Miguel Angel Lopez Montellano (alakat@gmail.com)
 */
public class ActivationProcessor implements Processor{

    private static final Logger logger = Logger.getLogger(ActivationProcessor.class);
    
    @Override
    public void eventRequest(Event event) {
        logger.info("Request");
        ActivationEvent ae  = (ActivationEvent)event;
        Mini mini = ae.getActivateMini();
        //Reques solo se procesa en Server
        if(Game.getGame().isServer()){
            event = event.generateEventResponse();
            EventManager.getInstance().addEvent(event);
            EventManager.getInstance().eventWaitTest();
        }else{
            logger.error("Nunca se procesa un ActivationEvent.Request en cliente");
        }
    }

    @Override
    public void eventResponse(Event event) {
        logger.info("Response");
        ActivationEvent ae  = (ActivationEvent)event;
        Game.getGame().setActualTime(ae.getUnitTime());
        Mini mini = Game.getGame().getMiniByReferneceObject(ae.getActivateMini());
        logger.info("Activado("+mini.toString()+")");
        if(!Game.getGame().isServer()){
            logger.info("Los clientes desapilan la activación del mini");
            Accident a =Game.getGame().getActivationStack().pop();
            logger.info("Desapilamos: "+a.toString());
        }else{
            logger.info("Se envía el evento al cliente.");
            Game.getGame().getMasterCommunication().sendEvent(event);
        }
        if(Game.getGame().getPlayerByMini(mini).isLocal()){
            //Activamos el mini
            logger.info("Se activa el mini");
            Game.getGame().setSelectedMini(mini);
            Game.getPerfectDayGUI().activateMini(mini);
        }
        Game.getGame().setMasterAPorEllos(new MasterAPorEllos(mini));
        Game.getGame().setActualTime(ae.getUnitTime());
        Game.getGame().setTurnTime(new LongUnitTime(0l));
        Game.getPerfectDayGUI().redraw();
    }

}
