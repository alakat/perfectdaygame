/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.perfectday.logicengine.core.event.action.spells;

import org.perfectday.logicengine.core.event.action.ActionEvent;
import org.perfectday.logicengine.model.minis.Mini;
import org.perfectday.logicengine.model.minis.action.ActionMini;
import org.perfectday.logicengine.model.spells.InstanceCastSpell;

/**
 *
 * @author Miguel Angel Lopez Montellano (alakat@gmail.com)
 */
public class CastSpellEvent extends ActionEvent {

    private InstanceCastSpell instanceCastSpell;

    public CastSpellEvent() {
    }
    
    
    
    
    public CastSpellEvent(Mini worker, Mini target, ActionMini action, InstanceCastSpell instanceCastSpell) {
        super(worker, target, action);
        this.instanceCastSpell = instanceCastSpell;
    }

    public InstanceCastSpell getInstanceCastSpell() {
        return instanceCastSpell;
    }

    public void setInstanceCastSpell(InstanceCastSpell instanceCastSpell) {
        this.instanceCastSpell = instanceCastSpell;
    }
    
    
    

}
