/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.perfectday.threads;

import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

/**
 *
 * @author Miguel Angel Lopez Montellano (alakat@gmail.com)
 */
public class EventRunner implements Runnable {
private static Logger logger = Logger.getLogger(CommandRunner.class);
    private List<EventCommand> commands;
    private final Object lock = new Object();
    private boolean alive;

    public EventRunner() {
        alive =true;
        this.commands =new ArrayList<EventCommand>();
    }

    /**
     * Se apila un nuevo commando en la pila de commandos
     * @param command
     */
    public void sendCommand(EventCommand event){
        this.commands.add(event);
        lock.notifyAll();
    }

    /**
     * Ejecición de todos los commandso
     */
    @Override
    public void run() {
        while(alive){
            if(this.commands.isEmpty()){
                synchronized(lock){
                    try {
                        lock.wait();
                    } catch (InterruptedException ex) {
                        logger.fatal(ex.getMessage(),ex);
                    }
                }
            }else{
                EventCommand event = this.commands.remove(0);
                if(event!=null){
                   event.do_();
                }
            }
        }

    }

    /**
     * Stop a thread
     */
    public void stop() {
        this.alive = false;
        lock.notifyAll();
    }


}
