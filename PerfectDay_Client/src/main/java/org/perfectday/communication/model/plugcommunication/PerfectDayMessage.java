/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.perfectday.communication.model.plugcommunication;

/**
 *
 * @author Miguel Angel Lopez Montellano (alakat@gmail.com)
 */
public class PerfectDayMessage {

    private int type;
    private String Message;
    private Class clazz;
    /**
     * Instante de envio, sin uso en el juego nos permite acotar la latencia 
     * del mensaje
     */
    private long sendtime;

    public String getMessage() {
        return Message;
    }

    public void setMessage(String Message) {
        this.Message = Message;
    }

    public Class getClazz() {
        return clazz;
    }

    public void setClazz(Class clazz) {
        this.clazz = clazz;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public long getSendtime() {
        return sendtime;
    }

    public void setSendtime(long sendtime) {
        this.sendtime = sendtime;
    }
    
    
    
    
}
