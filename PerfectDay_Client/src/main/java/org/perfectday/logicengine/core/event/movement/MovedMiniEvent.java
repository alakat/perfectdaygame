/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.perfectday.logicengine.core.event.movement;

import org.perfectday.logicengine.core.event.Event;
import org.perfectday.logicengine.model.battelfield.Field;
import org.perfectday.logicengine.model.minis.Mini;
import org.perfectday.logicengine.model.unittime.UnitTime;

/**
 *
 * @author Miguel Angel Lopez Montellano ( alakat@gmail.com )
 */
public class MovedMiniEvent extends Event{
    private Mini mini;
    private Field dest;
    private UnitTime utMoved;

    public MovedMiniEvent(Mini mini, Field dest) {
        this.mini = mini;
        this.dest = dest;
    }

    public Field getDest() {
        return dest;
    }

    public void setDest(Field dest) {
        this.dest = dest;
    }

    public Mini getMini() {
        return mini;
    }

    public void setMini(Mini mini) {
        this.mini = mini;
    }

    public UnitTime getUtMoved() {
        return utMoved;
    }

    public void setUtMoved(UnitTime utMoved) {
        this.utMoved = utMoved;
    }
}
