/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.perfectday.logicengine.core.event.manager.processors;

import java.util.logging.Level;
import org.apache.log4j.Logger;
import org.perfectday.communication.masterCommunication.MasterCommunication;
import org.perfectday.dashboard.threads.DashBoardThreadGroup;
import org.perfectday.logicengine.brain.Turn;
import org.perfectday.logicengine.core.Game;
import org.perfectday.logicengine.core.event.Event;
import org.perfectday.logicengine.core.event.accident.ActivationEvent;
import org.perfectday.logicengine.core.event.game.EndTurnEvent;
import org.perfectday.logicengine.core.event.manager.EventManager;
import org.perfectday.logicengine.core.player.Player;
import org.perfectday.logicengine.model.activationstack.accidents.Accident;
import org.perfectday.logicengine.model.minis.Mini;
import org.perfectday.logicengine.model.unittime.LongUnitTime;
import org.perfectday.logicengine.model.unittime.factories.LongUnitTimeFactory;
import org.perfectday.logicengine.movement.MasterAPorEllos;
import org.perfectday.threads.commands.kernell.PutEventCommand;

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
            Player player= Game.getGame().getPlayerByMini(mini);
            if(player.isIa()){
                
                Game.getGame().setMasterAPorEllos(new MasterAPorEllos(mini));
                Game.getGame().setActualTime(ae.getUnitTime());
                Game.getGame().setTurnTime(new LongUnitTime(0l));
                Game.getPerfectDayGUI().redraw();
                Turn turn = player.getBrain().getTrun(mini, new LongUnitTime(0l));
                logger.debug(turn);
               
                synchronized(this){
                    try {
                        this.wait(1500);
                    } catch (InterruptedException ex) {
                        logger.error("ERROR en la espera de la IA",ex);
                    }
                }
                LongUnitTime unitTime = LongUnitTimeFactory.getInstance().doNothing(
                    mini);
                unitTime.plus(turn.getUt());
                Game.getPerfectDayGUI().redraw();
                
                EndTurnEvent endTurnEvent = new EndTurnEvent(unitTime, mini);
                
                DashBoardThreadGroup.sendEventToKernell(new PutEventCommand(endTurnEvent));
                return;
            }
        }
        Game.getGame().setMasterAPorEllos(new MasterAPorEllos(mini));
        Game.getGame().setActualTime(ae.getUnitTime());
        Game.getGame().setTurnTime(new LongUnitTime(0l));
        Game.getPerfectDayGUI().redraw();
    }

}
