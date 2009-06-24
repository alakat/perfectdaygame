/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.perfectday.logicengine.core.event;
import org.apache.log4j.Logger;
import org.perfectday.message.model.Message;
/**
 * Representa un evento interno del juego, por ejemplo mover un mini
 * O esperar    
 * @author Miguel Angel Lopez Montellano ( alakat@gmail.com )
 */
public abstract class Event {

    public enum EventType{
        REQUEST, /** Petición de acción **/
        RESPONSE /** Representación de la acción **/
    }
    
    /** 
     * Se añade el tipo de evento para que EventManager pueda distinguir 
     * si es una solicitud al servidor o si es una acción ordenada por este
     */
    private EventType eventType;

    public Event() {
        this.eventType = EventType.REQUEST;
    }
    
    

    public EventType getEventType() {
        return eventType;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }
    
    
    /**
     * Genera el evento de respuesta para this. en los evento tipo Response 
     * este método no es dummy, todos los eventos deben sobre escribir
     * este método.
     * @return
     */
    public Event generateEventResponse(){
        Logger.getLogger(Event.class).warn("Este método es la implementación" +
                " tonta de generateEventResponse,no debe ser usado");
        this.setEventType(EventType.RESPONSE);
        return this;
    }    
    
}
