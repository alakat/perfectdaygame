/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.perfectday.logicengine.core.event.manager.processors;

import org.apache.log4j.Logger;
import org.perfectday.communication.masterCommunication.MasterCommunication;
import org.perfectday.infomation.Journalist;
import org.perfectday.logicengine.combat.InstanceCombat;
import org.perfectday.logicengine.combat.MasterOfCombatImpl;
import org.perfectday.logicengine.core.Game;
import org.perfectday.logicengine.core.event.Event;
import org.perfectday.logicengine.core.event.accident.OffensiveActionEvent;
import org.perfectday.logicengine.core.event.manager.EventManager;
import org.perfectday.logicengine.model.command.combat.TargetOutOfRangeCommand;

/**
 *

 * @author Miguel Angel Lopez Montellano (alakat@gmail.com)
 */
public class OffensiveActionProcesor extends CombatProcessor implements Processor {

    private static final Logger logger = Logger.getLogger(OffensiveActionProcesor.class);
    
    @Override
    public void eventRequest(Event event) {
        if (!Game.getGame().isServer()){
            logger.warn("Esta es cliente y no debería procesar un OffensiveEventResquest");
        }
        OffensiveActionEvent oae = (OffensiveActionEvent)event;
        InstanceCombat ic = oae.getInstanceCombat();
        if(ic.getAtack().getActionKeep().isDefenderKeeped(
                Game.getGame().getBattelField().getField(ic.getDefensor()),
                Game.getGame().getBattelField().getField(ic.getAtacker()),Game.getGame())){
         Game.getGame().getMasterOfCombat().putInstanceCombat(oae.getInstanceCombat());
            resolvedCombat(oae.getCommands());
            oae.getCommands().addAll(searchDead()); //search mini dead in this turn
        }else{
            oae.getCommands().add(new TargetOutOfRangeCommand("Ataque fallo, fuera de rango"));
        }
        event = oae.generateEventResponse();
        Game.getGame().getMasterCommunication().sendEvent(event);
        EventManager.getInstance().addEvent(event);
        EventManager.getInstance().eventWaitTest();        
    }

    @Override
    public void eventResponse(Event event) {
        logger.info("Response");
        OffensiveActionEvent ce = (OffensiveActionEvent)event;
        if(Game.getGame().isServer()){
            //Server.
            logger.info("Server");
            Journalist.getInstance().infoCombat(ce.getCommands()); //Informamos del combate
            Game.getGame().nextAccident();
        }else{
            logger.info("Cliente");            
            //Desapilamos el combate preparado
            synchronized(Game.getGame().getActivationStack()){
                Game.getGame().getActivationStack().pop();
            }
            CommandConsumer.process(ce.getCommands());
            Journalist.getInstance().infoCombat(ce.getCommands()); //Informamos del combate
        }
        
    }

    
    
}
