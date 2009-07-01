/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.perfectday.logicengine.core.event.manager.processors;

import org.apache.log4j.Logger;
import org.perfectday.communication.masterCommunication.MasterCommunication;
import org.perfectday.logicengine.core.Game;
import org.perfectday.logicengine.core.event.Event;
import org.perfectday.logicengine.core.event.game.EndTurnEvent;
import org.perfectday.logicengine.core.event.game.PutActionEvent;
import org.perfectday.logicengine.core.event.manager.EventManager;
import org.perfectday.logicengine.model.activationstack.accidents.Activation;
import org.perfectday.logicengine.model.activationstack.accidents.factories.ActivationFactory;
import org.perfectday.logicengine.model.minis.Mini;
import org.perfectday.logicengine.model.unittime.UnitTime;

/**
 * Codifica el evento de fin de turno.
 * @author Miguel Angel Lopez Montellano (alakat@gmail.com)
 */
public class EndTurnProcessor implements Processor{
    private Logger logger = Logger.getLogger(EndTurnProcessor.class);
    
    @Override
    public void eventRequest(Event event) {
        //Desactivamos el mini seleccionado 
        Game.getGame().setSelectedMini(null);
        Game.getGame().getPerfectDayGUI().desactivateMini();
        logger.info("request");
        if(Game.getGame().isServer()){
            logger.info("server");
            event= event.generateEventResponse();
            EventManager.getInstance().addEvent(event);
            EventManager.getInstance().eventWaitTest();
        }else{
            logger.info("client");
            event= event.generateEventResponse();
            Game.getGame().getMasterCommunication().sendEvent(event);
        }
    }

    @Override
    public void eventResponse(Event event) {
        EndTurnEvent endTurnEvent  = (EndTurnEvent)event;
        Mini mini = Game.getGame().getMiniByReferneceObject(endTurnEvent.getMini());
        if(Game.getGame().isServer()){
            if(mini!=null && mini.isAlive()){            
                try {
                    UnitTime newActualTime = (UnitTime) Game.getGame().getActualTime().clone();
                    newActualTime.plus(endTurnEvent.getUt());
                    Activation activation = ActivationFactory.getInstance().createActivation(mini, newActualTime);
                    logger.info("Nuevo apilamiento:"+activation.toString());
                    /** Send To client **/
                     
                    Game.getGame().getActivationStack().put(activation);
                    PutActionEvent pae = new  PutActionEvent();
                    pae.setActivation(activation);
                    pae.setEventType(Event.EventType.RESPONSE);
                    Game.getGame().getMasterCommunication().sendEvent(pae);
                } catch (CloneNotSupportedException ex) {
                    logger.error("No se pudo clonar una unidad de tiempo",ex);
                }
            }
            Game.getGame().nextAccident();
        }else{
            logger.error("un endturnResponse nunca se ha de ejecutar en cliente");
        }
    }

}
