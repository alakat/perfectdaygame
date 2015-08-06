/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.perfectday.threads;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Properties;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
/**
 *
 * Esta clase permite apilar y ejecutar comando provenientes del sistema sin acusar
 * interbloqueos
 * @author Miguel Angel Lopez Montellano (alakat@gmail.com)
 */
import org.apache.log4j.PropertyConfigurator;
public class CommandRunner implements Runnable {

    
    private static Logger logger = Logger.getLogger(CommandRunner.class);
    private List<Command> commands;
    private boolean alive;
    public CommandRunner() {
        logger.info("CommandRunner es construida");
        alive = true;
        this.commands =new ArrayList<Command>();
    }

    /**
     * Se apila un nuevo commando en la pila de commandos
     * @param command
     */
    public synchronized  void sendCommand(Command command){
        this.commands.add(command);
        this.notifyAll();
    }

    /**
     * Ejecicioón de todos los commandso
     */
    @Override
    public void run() {
        while(alive){
            if(this.commands.isEmpty()){
                synchronized(this){
                    try {
                        logger.trace("lock wait");
                        this.wait(2000);
                    } catch (InterruptedException ex) {
                        logger.fatal(ex.getMessage(),ex);
                    }
                }
            }else{
                Command command = this.commands.remove(0);
                logger.info("desapilamos "+command);
                if(command!=null){
                    Thread t = new Thread(Thread.currentThread().getThreadGroup(),command,"Command:"+command.getClass().getSimpleName());
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
        this.notifyAll();
    }




}
