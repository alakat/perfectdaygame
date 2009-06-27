/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.perfectday.logicengine.core.event.manager;

import org.apache.log4j.Logger;
import org.perfectday.logicengine.core.event.Event;

/**
 * Hebra responsable de la ejecuciónde los eventos apilados en el EventMAnager
 * @author Miguel Angel Lopez Montellano (alakat@gmail.com)
 */
public class EventManagerThread implements Runnable {
    
    private EventManager eventManager;
    private static final Logger logger = Logger.getLogger(EventManagerThread.class);
    private static EventManagerThread eventManagerThread = new EventManagerThread();

    private EventManagerThread() {
        this.eventManager = EventManager.getInstance();
    }

    public static EventManagerThread getEventManagerThread() {
        return eventManagerThread;
    }
    

    @Override
    public void run() {
        while(!this.eventManager.isDeadThread()){
            //game loop
            while(!this.eventManager.getEvents().isEmpty()){
                Event ev = this.eventManager.getEvents().remove(0);
//              Antiguo modo de juego local  this.eventManager.processEvent(ev);
                //Nuevo modo de gestión de eventos
                this.eventManager.proccessEvent_(ev);
                logger.info("Ev"+ev);
            }
            synchronized (this){
                logger.info("Duermo!!!");
                try {
                    this.wait();
                } catch (InterruptedException ex) {
                   logger.error("Error de interrupcion",ex);
                }
            }
            logger.info("Me despiertan!!!");
        }
    }

    public EventManager getEventManager() {
        return eventManager;
    }


    
    
    

    
    
    

}
