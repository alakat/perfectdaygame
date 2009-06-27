/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.perfectday.threads;

import org.apache.log4j.Logger;

/**
 * Esta clase es usada para realizar operaciones en otro grupo de hebras distinto
 * permitiendo así la comunicación entre dos hebras principales sin causar interbloqueo
 *
 * Adicionalmente este comando ya asegura la integridad del sistema ya que
 * toda excepción es recogida
 * @author Miguel Angel Lopez Montellano (alakat@gmail.com)
 */
public abstract class Command implements  Runnable{

    private static Logger logger = Logger.getLogger(Command.class);

    @Override
    public void run() {
        try{
            trueRun();
        }catch(Throwable t){
            logger.fatal(t.getMessage(), t);
        }
    }

    /**
     * Este método es el que realiza la ejecución real. Las operaciones
     * que se ejecuten en este método serán lanzadas en hebras independientes
     * @throws java.lang.Exception
     */
    public abstract void trueRun() throws Exception;


}
