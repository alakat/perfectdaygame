/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.perfectday.threads;

import org.apache.log4j.Logger;

/**
 * Esta clase al igual que {@link Command} Permite la ejecuci�n de eventos entre
 * hebras principales del sistema sin causar interbloqueos
 *
 * La principal diferencia que existe con Command es que la ejecuci�n de este
 * Evento no es hebrado por lo que las distintas ejecuciones de Eventos ser�n
 * sincronizados
 *
 * Al igual que {@link  Command} la integridad del sistema est� garantizada en
 * la ejecuci�n del evento.
 * @author Miguel Angel Lopez Montellano (alakat@gmail.com)
 */
public abstract class EventCommand {

    private static Logger logger = Logger.getLogger(EventCommand.class);
    
    /**
     * C�digo del evento
     * @throws java.lang.Exception
     */
    public abstract void trueDo()throws Exception;

    public void do_(){
        try{
            this.trueDo();
        }catch(Throwable t){
            logger.fatal(t.getMessage(),t);
        }
    }
}
