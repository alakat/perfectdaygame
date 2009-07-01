/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.perfectday.threads.commands.kernell;

import org.perfectday.logicengine.core.event.Event;
import org.perfectday.logicengine.core.event.manager.EventManager;
import org.perfectday.threads.Command;

/**
 *
 * Comando para la apilación de un Evento genérico en el sistema
 * @author Miguel Angel Lopez Montellano (alakat@gmail.com)
 */
public class PutEventCommand extends Command {

    /**
     * Evento a apilar
     */
    private Event event;

    public PutEventCommand(Event event) {
        this.event = event;
    }



    @Override
    public void trueRun() throws Exception {
        EventManager.getInstance().addEvent(event);
        EventManager.getInstance().eventWaitTest();
    }


}
