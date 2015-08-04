/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.perfectday.logicengine.core.event.game;

import org.perfectday.logicengine.core.event.Event;

/**
 *
 * Evento de definición de fin de batalla.
 * @author Miguel Angel Lopez Montellano (alakat@gmail.com)
 */
public class EndBattleEvent extends Event{
    
    private String msgWiner;

    public EndBattleEvent() {
    }
    
    
    

    public String getMsgWiner() {
        return msgWiner;
    }

    public void setMsgWiner(String msgWiner) {
        this.msgWiner = msgWiner;
    }
    
    

}
