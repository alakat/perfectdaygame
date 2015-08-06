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
        REQUEST, /** Petici贸n de acci贸n **/
        RESPONSE /** Representaci贸n de la acci贸n **/
    }
    
    /** 
     * Se a帽ade el tipo de evento para que EventManager pueda distinguir 
     * si es una solicitud al servidor o si es una acci贸n ordenada por este
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
     * este m茅todo no es dummy, todos los eventos deben sobre escribir
     * este m茅todo.
     * @return
     */
    public Event generateEventResponse(){
        Logger.getLogger(Event.class).warn("Este m閠odo es la implementaci贸n" +
                " tonta de generateEventResponse,no debe ser usado");
        this.setEventType(EventType.RESPONSE);
        return this;
    }    

    @Override
    public String toString() {
        return "Evento ("+this.getClass().getName()+")";
    }
    
    
}
