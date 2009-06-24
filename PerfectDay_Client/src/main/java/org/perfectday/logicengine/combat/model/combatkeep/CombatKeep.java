/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.perfectday.logicengine.combat.model.combatkeep;

import java.util.List;
import org.perfectday.logicengine.model.battelfield.Field;


/**
 *
 * @author Miguel Angel Lopez Montellano ( alakat@gmail.com )
 */
public abstract class CombatKeep {
    
    public abstract boolean isDefenderKeeped(Field defender, Field ataker);
    
    public abstract List<Field> getFieldKeeped(Field ataker);

}
