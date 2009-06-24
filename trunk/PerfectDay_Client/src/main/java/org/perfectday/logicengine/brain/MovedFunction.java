/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.perfectday.logicengine.brain;

import org.perfectday.logicengine.model.battelfield.Field;
import org.perfectday.logicengine.model.minis.Mini;

/**
 *
 * @author Miguel Angel Lopez Montellano ( alakat@gmail.com )
 */
public interface MovedFunction {

    public double getCualityOfField(Field field, Mini mini);
    public void initFunction();
}
