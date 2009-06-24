/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.perfectday.message.movement;

import java.io.Serializable;
import org.perfectday.message.ReferenceObjectVO;
import org.perfectday.message.model.Message;
import org.perfectday.message.model.MessageType;

/**
 *
 * @author Lobo <inmortalland83@gmail.com>
 */
public class MovedMiniMessage extends Message implements Serializable{
    private ReferenceObjectVO mini;
    private ReferenceObjectVO dest;

    public ReferenceObjectVO getDest() {
        return dest;
    }

    public void setDest(ReferenceObjectVO dest) {
        this.dest = dest;
    }

    public ReferenceObjectVO getMini() {
        return mini;
    }

    public void setMini(ReferenceObjectVO mini) {
        this.mini = mini;
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
