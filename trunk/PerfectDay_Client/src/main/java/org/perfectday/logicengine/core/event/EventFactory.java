package org.perfectday.logicengine.core.event;

/**
 * Constructora de todos los eventos dejuego que se producen en PerfectDay
 * @author Miguel Angel Lopez Montellano ( alakat@gmail.com )
 */
public class EventFactory {
    private static final EventFactory instance = new EventFactory();

    public static EventFactory getInstance() {
        return instance;
    }
    
}
