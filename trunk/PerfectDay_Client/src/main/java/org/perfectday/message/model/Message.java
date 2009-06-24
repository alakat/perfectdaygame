/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.perfectday.message.model;

import java.io.Serializable;


/**
 *
 * @author Lobo <inmortalland83@gmail.com>
 */
public class Message implements Serializable {
    protected  MessageType type;
    private String message;
    private String clazz;
    
    /*
     * This Method returns the Message´s type
     */ 
    public MessageType getType(){
        return this.type;
    }
    
    /*
     * This Method set the Message´s type
     */ 
    public void setType(MessageType type){
        this.type=type;
    }

    public String getClazz() {
        return clazz;
    }

    public void setClazz(String clazz) {
        this.clazz = clazz;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    
}
