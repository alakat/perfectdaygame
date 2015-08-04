/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.perfectday.logicengine.core.event.init;

import org.perfectday.logicengine.core.event.game.GameEvent;

/**
 *
 * @author Lobo <inmortalland83@gmail.com>
 */
public class InitializeEvent extends GameEvent{
     private String players; /* this will contain the list of players parsed in XML */
     private String battleField; /* this will contain the battlefield parsed in XML */

    public InitializeEvent() {
    }

     
     
    public String getBattelField() {
        return battleField;
    }

    public void setBattelField(String battelField) {
        this.battleField = battelField;
    }

    public String getPlayers() {
        return players;
    }

    public void setPlayers(String players) {
        this.players = players;
    }


}
