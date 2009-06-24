/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.perfectday.logicengine.combat.model;

import java.util.Iterator;
import org.perfectday.logicengine.model.minis.support.Support;

/**
 *
 * @author Miguel Angel Lopez Montellano ( alakat@gmail.com )
 */
public class IteratorSupportStack  implements Iterator<Support> {

    private SupportStack supportStack;
    private int index;

    public IteratorSupportStack(SupportStack supportStack) {
        this.supportStack = supportStack;
        index=0;
    }

    public boolean hasNext() {
        return index < this.supportStack.getStackList().size();
    }

    public Support next() {
        Support support = null;
        if (this.hasNext()){
            support = this.supportStack.getStackList().get(index);
            index++;
        }
        return support;
    }

    public void remove() {
        Support support = null;
        if (this.hasNext()){
            support = this.supportStack.getStackList().get(index);
            this.supportStack.getStackList().remove(support);
            index--;            
        }
        
    }
    
    
}
