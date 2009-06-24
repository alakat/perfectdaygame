/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.perfectday.logicengine.core.event.manager.processors;

import org.perfectday.logicengine.core.event.Event;

/**
 * Interfaz de clase procesadora de eventos
 * @author Miguel Angel Lopez Montellano (alakat@gmail.com)
 */
public interface Processor {
    
    
    public void eventRequest(Event event);
    public void eventResponse(Event event);

}
