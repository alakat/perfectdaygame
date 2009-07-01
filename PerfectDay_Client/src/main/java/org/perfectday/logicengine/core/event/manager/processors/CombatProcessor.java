/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.perfectday.logicengine.core.event.manager.processors;

import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.perfectday.communication.masterCommunication.MasterCommunication;
import org.perfectday.infomation.Journalist;
import org.perfectday.logicengine.combat.InstanceCombat;
import org.perfectday.logicengine.combat.MasterOfCombatImpl;
import org.perfectday.logicengine.core.Game;
import org.perfectday.logicengine.core.event.Event;
import org.perfectday.logicengine.core.event.accident.PutAccidentEvent;
import org.perfectday.logicengine.core.event.action.combat.CombatEvent;
import org.perfectday.logicengine.core.event.manager.EventManager;
import org.perfectday.logicengine.core.player.Player;
import org.perfectday.logicengine.model.activationstack.accidents.OffensiveAction;
import org.perfectday.logicengine.model.battelfield.Field;
import org.perfectday.logicengine.model.command.Command;
import org.perfectday.logicengine.model.command.combat.CombatResolutionCommand;
import org.perfectday.logicengine.model.command.combat.DeathMiniCommand;
import org.perfectday.logicengine.model.command.combat.PreparedCombatCommand;
import org.perfectday.logicengine.model.command.combat.TargetOutOfRangeCommand;
import org.perfectday.logicengine.model.minis.Mini;
import org.perfectday.logicengine.model.minis.action.combat.CombatActionMini;
import org.perfectday.logicengine.model.unittime.UnitTime;
import org.perfectday.main.dummyengine.DummyGraphicsEngine;

/**
 * Procesador de eventos de combate. El cliente envia la solicitud de combate al
 * server y el este notifica la resolución de combate. 
 * @author Miguel Angel Lopez Montellano (alakat@gmail.com)
 */
public class CombatProcessor implements Processor{
    private static final Logger logger = Logger.getLogger(CombatProcessor.class);
    /**
     * 
     * @param event
     */
    @Override
    public void eventRequest(Event event) {
        logger.info("Request");   
        CombatEvent combatEvent = (CombatEvent)event;
        if(Game.getGame().isServer()){
            if(!combatEvent.getAction().isNeedPreparation()){
                //Si no necesita preparación el combate se ejecuta
                generateCombat(combatEvent, event);
            }else{
                processPreparedCombat(combatEvent);
            }
            event = event.generateEventResponse();
            EventManager.getInstance().addEvent(event);
            EventManager.getInstance().eventWaitTest();
            logger.info("Server ...");
            Game.getGame().getMasterCommunication().sendEvent(event);
            logger.info("Enviado!");
        }else{
            logger.info("Client ...");
            Game.getGame().getMasterCommunication().sendEvent(event);
            logger.info("Enviado!");
        }
    }

    @Override
    public void eventResponse(Event event) {
        logger.info("Response");
        CombatEvent ce = (CombatEvent)event;
        if(Game.getGame().isServer()){
            //Server.
            logger.info("Server");
            Journalist.getInstance().infoCombat(ce.getCommands()); //Informamos del combate
        }else{
            logger.info("Cliente");            
            CommandConsumer.process(ce.getCommands());
            Journalist.getInstance().infoCombat(ce.getCommands()); //Informamos del combate
        }
        
    }

    protected void generateCombat(CombatEvent combatEvent, Event event) {
        logger.info("Server");
        Mini aMini = Game.getGame().getMiniByReferneceObject(combatEvent.getWorker());
        Mini dMini = Game.getGame().getMiniByReferneceObject(combatEvent.getTarget());
        CombatActionMini combatActionMini = combatEvent.getAction();
        Field aField = Game.getGame().getBattelField().getField(aMini);
        Field dField = Game.getGame().getBattelField().getField(dMini);
        if (combatActionMini.isDefenederInRange(dField, aField)) {
            InstanceCombat instanceCombat = combatActionMini.createInstanceCombat(dMini, aMini, combatEvent.isConterAtack());
            Game.getGame().getMasterOfCombat().putInstanceCombat(instanceCombat);
            resolvedCombat(combatEvent.getCommands());
            combatEvent.getCommands().addAll(searchDead()); //search mini dead in this turn
        } else {
            //Fuera del alcance
            logger.info("Combate fuera de alcance");
            combatEvent.getCommands().add(new TargetOutOfRangeCommand());
        }

    }

    protected void processPreparedCombat(CombatEvent combatEvent) {
        //Si el comabte es preparado...
        UnitTime ut = Game.getGame().getActualTime();
        ut.plus(Game.getGame().getPerfectDayGUI().getUnitTime());
        ut.plus(combatEvent.getAction().getCostPreparation());
        OffensiveAction oa = new OffensiveAction(ut, combatEvent.getAction().createInstanceCombat(combatEvent.getTarget(), combatEvent.getWorker(), false));
        //Con evento de apilación
        //Atigua version: Game.getGame().getActivationStack().put(oa);
        PutAccidentEvent putAccidentEvent = new PutAccidentEvent();
        putAccidentEvent.setAccident(oa);
        EventManager.getInstance().addEvent(putAccidentEvent);       
        EventManager.getInstance().eventWaitTest();
        Mini atacker = Game.getGame().getMiniByReferneceObject(combatEvent.getWorker());
        combatEvent.getCommands().add(new PreparedCombatCommand(atacker, combatEvent.getAction()));
    }

  
    
    /**
     * Ejecuta el combate
     * @param commands
     */
    protected void resolvedCombat(List<Command> commands) {
        //Any Combat
        InstanceCombat combat = Game.getGame().getMasterOfCombat().getInstanceCombat();
        while (combat != null) {
            //do combat
            //ListCommand
            commands.addAll(combat.doCombat());
            combat = Game.getGame().getMasterOfCombat().getInstanceCombat();
        }
        combat=null;
    }

    /**
     * REcolectamos muertos
     * @return
     */
    protected List<Command> searchDead() {
        Game.getPerfectDayGUI().addInfo("Muerte recolecta espiritus...");
        List<Mini> minis = new ArrayList<Mini>();
        List<Command> commands = new ArrayList<Command>();
        for(Player player: Game.getGame().getPlayers()){
            minis.addAll(player.getBand());
        }
        List<Mini> death = new ArrayList<Mini>();
        for(Mini mini:minis){
            Game.getPerfectDayGUI().addInfo("Mini:"+mini.toString()+" vivo:"+mini.isAlive());
            if(!mini.isAlive()){
                commands.add(new DeathMiniCommand(mini.toString()));
                Game.getPerfectDayGUI().addInfo("Lo eliminamos de:"+ Game.getGame().getBattelField().getField(mini));
                Game.getGame().getBattelField().getField(mini).setMiniOcupant(null);
                death.add(mini);                
            }
        }
        Game.getPerfectDayGUI().addInfo("Muerto:"+death.size());
        for(Mini miniDead:death){            
            for(Player player:Game.getGame().getPlayers()){
                if(player.getBand().contains(miniDead)){
                    player.getBand().remove(miniDead);                                        
                    break;
                }
            }
            //TODO: Buscar activaciones o acciones de los muertos
            Game.getGame().getActivationStack().deadClear(miniDead);
        }
        
        death.clear();
        minis.clear();
        death=null;
        minis = null;
        return commands;
    }
}
