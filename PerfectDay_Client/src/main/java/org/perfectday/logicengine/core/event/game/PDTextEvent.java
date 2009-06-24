/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.perfectday.logicengine.core.event.game;

/**
 *
 * @author Lobo <inmortalland83@gmail.com>
 */
public class PDTextEvent extends GameEvent {
    
    private String message;
    private String user;
    
    public PDTextEvent(String user, String message){
        this.message=message;
        this.user=user;
    }
    public PDTextEvent(){
        
    }
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
    

}
