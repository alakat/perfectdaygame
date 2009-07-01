/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.perfectday.logicengine.combat;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Miguel Angel Lopez Montellano ( alakat@gmail.com )
 */
public class MasterOfCombatImpl {
    
    private List<InstanceCombat> combatStack;

    public MasterOfCombatImpl() {
        combatStack = new ArrayList<InstanceCombat>();
    }
    
    public synchronized  void putInstanceCombat(InstanceCombat combat){
        this.combatStack.add(combat);
    }
    
    public synchronized InstanceCombat getInstanceCombat(){
        if(this.combatStack.isEmpty())
            return null;
        return this.combatStack.remove(0);
    }
    
    

}
