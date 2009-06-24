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
public class InitializeMessage extends Message implements Serializable {
    private String players; /* this will contain the list of players parsed in XML */
    private String battelField; /* this will contain the battlefield parsed in XML */

    public InitializeMessage(String players, String battelField) {
        this.players = players;
        this.battelField = battelField;
        this.type=MessageType.Initialize;
    }

    public String getBattelField() {
        return battelField;
    }

    public void setBattelField(String battelField) {
        this.battelField = battelField;
    }

    public String getPlayers() {
        return players;
    }

    public void setPlayers(String players) {
        this.players = players;
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
