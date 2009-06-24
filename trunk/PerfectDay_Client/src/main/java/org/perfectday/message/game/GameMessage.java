/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.perfectday.message.game;

import java.io.Serializable;

import org.perfectday.message.model.Message;
import org.perfectday.message.model.MessageType;

/**
 *
 * @author Lobo <inmortalland83@gmail.com>
 */
public class GameMessage extends Message implements Serializable {

    public GameMessage(){
        this.type=MessageType.Game;
        
    }
   

    @Override
    public MessageType getType() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setType(MessageType type) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
