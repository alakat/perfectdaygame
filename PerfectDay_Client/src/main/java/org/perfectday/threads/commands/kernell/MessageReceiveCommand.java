/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.perfectday.threads.commands.kernell;

import org.perfectday.communication.masterCommunication.MasterCommunication;
import org.perfectday.communication.model.plugcommunication.PerfectDayMessage;
import org.perfectday.logicengine.core.Game;
import org.perfectday.threads.Command;

/**
 * Un nuevo PerfectDayMessage ha sido recivido por la hebra de comunicaciónes.
 * Su procesamiento se delega a {@link MasterCommunication}
 * @author Miguel Angel Lopez Montellano (alakat@gmail.com)
 */
public class MessageReceiveCommand extends Command {

    /**
     * Mensaje recivido
     */
    private PerfectDayMessage message;

    public MessageReceiveCommand(PerfectDayMessage message) {
        this.message = message;
    }

    

    @Override
    public void trueRun() throws Exception {
        Game.getGame().getMasterCommunication().receiveMessage(message);
    }


}
