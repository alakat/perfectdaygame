/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.perfectday.message.TextMessage;

import java.io.Serializable;


import org.perfectday.message.model.Message;
import org.perfectday.message.model.MessageType;

/**
 *
 * @author Lobo <inmortalland83@gmail.com>
 */
public class PDTextMessage extends Message implements Serializable {
 private String message;

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
    private String user;
     public PDTextMessage(){
         this.setType(MessageType.Text);
     
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
