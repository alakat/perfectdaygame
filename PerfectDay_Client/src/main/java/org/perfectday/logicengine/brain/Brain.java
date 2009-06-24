/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.perfectday.logicengine.brain;

import org.perfectday.logicengine.model.minis.Mini;
import org.perfectday.logicengine.model.unittime.UnitTime;

/**
 *
 * @author Miguel Angel Lopez Montellano ( alakat@gmail.com )
 */
public interface Brain {
    public  Turn getTrun(Mini min,UnitTime ut);
}
