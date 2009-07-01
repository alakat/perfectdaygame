/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.perfectday.logicengine.core.event.manager;

import org.apache.log4j.Logger;
import org.perfectday.core.threads.KernellThreadGroup;
import org.perfectday.logicengine.core.event.Event;
import org.perfectday.main.dummyengine.threads.GraphicsEngineThreadGroup;

/**
 * Hebra responsable de la ejecuciónde los eventos apilados en el EventMAnager
 * @author Miguel Angel Lopez Montellano (alakat@gmail.com)
 */
public class EventManagerRunnable implements Runnable {
    
    private EventManager eventManager;
    private static final Logger logger = Logger.getLogger(EventManagerRunnable.class);

    
    public EventManagerRunnable(EventManager eventManager) {
        this.eventManager = eventManager;
    }
    
    public static EventManagerRunnable getEventManagerThread() {
        logger.info("Buscamos en el KernellThreaddGroup");
        if (Thread.currentThread().getThreadGroup() instanceof KernellThreadGroup) {
            KernellThreadGroup kernellThreadGroup = (KernellThreadGroup) Thread.currentThread().getThreadGroup();
            return kernellThreadGroup.getEventManagerRunnable();
        }else  if (Thread.currentThread().getThreadGroup() instanceof GraphicsEngineThreadGroup) {
            GraphicsEngineThreadGroup graphicsEngineThreadGroup = (GraphicsEngineThreadGroup) Thread.currentThread().getThreadGroup();
            return graphicsEngineThreadGroup.getKernellThreadGroup().getEventManagerRunnable();
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
