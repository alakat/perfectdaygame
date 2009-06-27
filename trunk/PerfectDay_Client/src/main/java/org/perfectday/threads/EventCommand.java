/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.perfectday.threads;

import org.apache.log4j.Logger;

/**
 * Esta clase al igual que {@link Command} Permite la ejecución de eventos entre
 * hebras principales del sistema sin causar interbloqueos
 *
 * La principal diferencia que existe con Command es que la ejecución de este
 * Evento no es hebrado por lo que las distintas ejecuciones de Eventos serán
 * sincronizados
 *
 * Al igual que {@link  Command} la integridad del sistema está garantizada en
 * la ejecución del evento.
 * @author Miguel Angel Lopez Montellano (alakat@gmail.com)
 */
public abstract class EventCommand {

    private static Logger logger = Logger.getLogger(EventCommand.class);
    
    /**
     * Código del evento
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
