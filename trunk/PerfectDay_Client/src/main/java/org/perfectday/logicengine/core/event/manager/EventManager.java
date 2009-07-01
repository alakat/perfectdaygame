/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.perfectday.logicengine.core.event.manager;


import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.perfectday.core.threads.KernellThreadGroup;
import org.perfectday.logicengine.core.event.Event;
import org.perfectday.logicengine.core.event.accident.ActivationEvent;
import org.perfectday.logicengine.core.event.accident.OffensiveActionEvent;
import org.perfectday.logicengine.core.event.accident.PutAccidentEvent;
import org.perfectday.logicengine.core.event.action.combat.CombatEvent;
import org.perfectday.logicengine.core.event.action.spells.CastSpellEvent;
import org.perfectday.logicengine.core.event.game.EndBattleEvent;
import org.perfectday.logicengine.core.event.game.EndTurnEvent;
import org.perfectday.logicengine.core.event.game.PutActionEvent;
import org.perfectday.logicengine.core.event.manager.processors.ActivationProcessor;
import org.perfectday.logicengine.core.event.manager.processors.ActivationStateProcessor;
import org.perfectday.logicengine.core.event.manager.processors.CastSpellsProcessor;
import org.perfectday.logicengine.core.event.manager.processors.ClearStateProcessor;
import org.perfectday.logicengine.core.event.manager.processors.CombatProcessor;
import org.perfectday.logicengine.core.event.manager.processors.EndBattleProcessor;
import org.perfectday.logicengine.core.event.manager.processors.EndTurnProcessor;
import org.perfectday.logicengine.core.event.manager.processors.MoveMiniProcessor;
import org.perfectday.logicengine.core.event.manager.processors.OffensiveActionProcesor;
import org.perfectday.logicengine.core.event.manager.processors.Processor;
import org.perfectday.logicengine.core.event.manager.processors.PutAccidentProcessor;
import org.perfectday.logicengine.core.event.manager.processors.PutActivationProcessor;
import org.perfectday.logicengine.core.event.movement.MovedMiniEvent;
import org.perfectday.logicengine.core.event.state.ActivationStateEvent;
import org.perfectday.logicengine.core.event.state.ClearStateEvent;
import org.perfectday.logicengine.model.command.Command;
import org.perfectday.logicengine.model.spells.accident.CastSpellAccident;
import org.perfectday.main.dummyengine.threads.GraphicsEngineThreadGroup;

/**
 *
 * @author Miguel Angel Lopez Montellano ( alakat@gmail.com )
 */
public class EventManager  {

    private static Logger logger = Logger.getLogger(EventManager.class);
    private List<Event> events;
    private boolean deadThread;
    private List<Command> commands;
    private Processor moveMiniProcessor = new MoveMiniProcessor();
    private Processor endTurn = new EndTurnProcessor();
    private Processor putActivationProcessor = new PutActivationProcessor();
    private Processor activationProcessor = new ActivationProcessor();
    private Processor combatProcessor = new CombatProcessor();
    private Processor endBattleProcessor = new EndBattleProcessor();
    private Processor putAccidentProcesor = new PutAccidentProcessor();
    private Processor offensiveActionProcessor = new OffensiveActionProcesor();
    private Processor clearStateProcessor = new ClearStateProcessor();
    private Processor activationStateProcessor = new ActivationStateProcessor();
    private Processor castSpellsProcessor = new CastSpellsProcessor();

    public EventManager() {
        events = new ArrayList<Event>();        
        this.deadThread = false;
         commands = new ArrayList<Command>();
    }

    public synchronized  void eventWaitTest() {
        synchronized(EventManagerRunnable.getEventManagerThread()){
            EventManagerRunnable.getEventManagerThread().notifyAll();
        }
    }

    public List<Event> getEvents() {
        return events;
    }
    
    public void addEvent(Event e){
        this.events.add(e);
    }

    public static EventManager getInstance() {
        logger.info("Buscamos en el KernellThreadGroup");
        if (Thread.currentThread().getThreadGroup() instanceof KernellThreadGroup) {
            KernellThreadGroup kernellThreadGroup = (KernellThreadGroup) Thread.currentThread().getThreadGroup();
            return kernellThreadGroup.getEventManager();
        }else  if (Thread.currentThread().getThreadGroup() instanceof GraphicsEngineThreadGroup) {
            GraphicsEngineThreadGroup graphicsEngineThreadGroup = (GraphicsEngineThreadGroup) Thread.currentThread().getThreadGroup();
            return graphicsEngineThreadGroup.getKernellThreadGroup().getEventManager();
        }else{
            StackTraceElement[] trace = Thread.currentThread().getStackTrace();
            logger.fatal("Una hebra que no pertencia a Kernell o Graphics entro " +
                    "getPerfectDayGUI["+Thread.currentThread().getName()+","
                    +Thread.currentThread().getThreadGroup().getName()+"]");
            for (StackTraceElement stackTraceElement : trace) {
                logger.fatal(stackTraceElement.toString());
            }
            return null;
        }
    }

    

    public boolean isDeadThread() {
        return deadThread;
    }

    public void setDeadThread(boolean deadThread) {
        this.deadThread = deadThread;
    }

    /**
     * Processa un evento, distingue entre evento de tipo REQUEST y evento de
     * tipo RESPONSE
     * @param event
     */
    public void proccessEvent_(Event event){
        if(event.getEventType() == Event.EventType.RESPONSE){
                processEventResponse(event);
        }else{
            processEventRequest(event);
        }
    }
    
    /**
     * Procesa un evento de tipo RESPONSE. Esto implica que hay q actualizar
     * la información ya que la operación ha sido llevada a cabo.
     * @param event
     */
    private void processEventResponse(Event event) {
        if (event instanceof MovedMiniEvent) {
            this.moveMiniProcessor.eventResponse(event);                        
        }  
        if (event instanceof EndTurnEvent) {
            this.endTurn.eventResponse(event);            
        }
        if (event instanceof PutActionEvent){
            this.putActivationProcessor.eventResponse(event);
        }
        if (event instanceof ActivationEvent){
            this.activationProcessor.eventResponse(event);
        }
        if (event instanceof CombatEvent){
            this.combatProcessor.eventResponse(event);
        }
        if (event instanceof EndBattleEvent){
            this.endBattleProcessor.eventResponse(event);
        }
        if (event instanceof PutAccidentEvent){
            this.putAccidentProcesor.eventResponse(event);
        }
        if (event instanceof OffensiveActionEvent){
            this.offensiveActionProcessor.eventResponse(event);
        }
        if (event instanceof ClearStateEvent){
            this.clearStateProcessor.eventResponse(event);
        }
        if (event instanceof ActivationStateEvent){
            this.activationStateProcessor.eventResponse(event);
        }
        if (event instanceof CastSpellEvent){
            this.castSpellsProcessor.eventResponse(event);
        }
    }
    
    /**
     * Procesa un evento de tipo REQUEST, estos evento lleva información para
     * realizar operaciones como combates, movimientos etc.
     * @param event
     */
    private void processEventRequest(Event event){
        if (event instanceof MovedMiniEvent) {
            this.moveMiniProcessor.eventRequest(event);                        
        }
        if (event instanceof EndTurnEvent) {
            this.endTurn.eventRequest(event);            
        }
        if (event instanceof PutActionEvent){
            this.putActivationProcessor.eventRequest(event);
        }
        if (event instanceof ActivationEvent){
            this.activationProcessor.eventRequest(event);
        }
        if (event instanceof CombatEvent){
            this.combatProcessor.eventRequest(event);
        }
        if (event instanceof EndBattleEvent){
            this.endBattleProcessor.eventRequest(event);
        }
        if (event instanceof PutAccidentEvent){
            this.putAccidentProcesor.eventRequest(event);
        }
        if (event instanceof OffensiveActionEvent){
            this.offensiveActionProcessor.eventRequest(event);
        }
        if (event instanceof ClearStateEvent){
            this.clearStateProcessor.eventRequest(event);
        }
        if (event instanceof ActivationStateEvent){
            this.activationStateProcessor.eventRequest(event);
        }
        if (event instanceof CastSpellEvent){
            this.castSpellsProcessor.eventRequest(event);
        }
    }
    
    
    
}
