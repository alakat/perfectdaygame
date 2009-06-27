/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.perfectday.threads;

import java.util.PriorityQueue;
import java.util.Queue;
import org.apache.log4j.Logger;


/**
 *
 * Esta clase permite apilar y ejecutar comando provenientes del sistema sin acusar
 * interbloqueos
 * @author Miguel Angel Lopez Montellano (alakat@gmail.com)
 */
public class CommandRunner implements Runnable {

    private static Logger logger = Logger.getLogger(CommandRunner.class);
    private Queue<Command> commands;
    private final Object lock = new Object();
    private boolean alive;
    public CommandRunner() {
        alive = true;
        this.commands =new PriorityQueue<Command>();
    }

    /**
     * Se apila un nuevo commando en la pila de commandos
     * @param command
     */
    public void sendCommand(Command command){
        this.commands.add(command);
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
                Command command = this.commands.poll();
                if(command!=null){
                    Thread t = new Thread(command);
                    t.start();
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
