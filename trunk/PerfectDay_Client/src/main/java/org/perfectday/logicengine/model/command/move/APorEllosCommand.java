/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.perfectday.logicengine.model.command.move;

import org.perfectday.logicengine.model.ReferenceObject;

/**
 *
 * @author Miguel Angel Lopez Montellano ( alakat@gmail.com )
 */
public class APorEllosCommand  extends MovementCommand{

    private boolean successful;
    public APorEllosCommand(ReferenceObject mini,
            ReferenceObject targetField,boolean successful) {
        super(mini, targetField);
        this.successful=successful;
    }

    @Override
    public String info() {
        if (successful)
           return " ¡¡¡A Por Ellos works!!!"+super.info();
        else
            return " 'A Por Ellos' fails";
    }

}
