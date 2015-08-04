/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.perfectday.logicengine.model.minis.support;

import java.util.ArrayList;
import java.util.List;
import org.perfectday.logicengine.model.battelfield.BattelField;
import org.perfectday.logicengine.model.battelfield.Field;

/**
 *  Define una posición a partir del mini en el apoyo esta activo
 * @author Miguel Angel Lopez Montellano ( alakat@gmail.com )
 */
public class SupportTarget {
    /**
     * movimiento en el eje x+
     */
    private int xMovement;
    /**
     * movimiento en el eje y
     */
    private int yMovement;

    public SupportTarget() {
    }
    
    

    public SupportTarget(int xMovement, int yMovement) {
        this.xMovement = xMovement;
        this.yMovement = yMovement;
    }

    public Field getFieldReachSupport(Field field, BattelField aThis) {
        int x = field.getX() + xMovement;
        int y = field.getY() + yMovement;        
        Field[][] fields = aThis.getBattelfield();        
        if((x>=0)&& (x<fields.length) && (y>=0)&&(y<fields[0].length)){
            return fields[x][y];
        }
        return null;
    }

    public int getXMovement() {
        return xMovement;
    }

    public void setXMovement(int xMovement) {
        this.xMovement = xMovement;
    }

    public int getYMovement() {
        return yMovement;
    }

    public void setYMovement(int yMovement) {
        this.yMovement = yMovement;
    }
    
    
    

}
