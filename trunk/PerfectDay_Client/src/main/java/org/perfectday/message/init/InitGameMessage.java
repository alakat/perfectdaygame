/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.perfectday.message.init;

import java.io.Serializable;
import org.perfectday.message.model.Message;
import org.perfectday.message.model.MessageType;

/**
 *
 * @author Lobo <inmortalland83@gmail.com>
 */
public class InitGameMessage extends Message implements Serializable {

    
    /*
     * When the Server receives this message, the game in the 
     * Client side is set up and ready for playin
     */
    
    public InitGameMessage() {
        this.type=MessageType.InitGame;
    }
     @Override
    public MessageType getType() {
        return this.type;
    }

    @Override
    public void setType(MessageType type) {
        this.type=type;
    }

}
