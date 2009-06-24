/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.perfectday.logicengine.combat.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.perfectday.logicengine.model.battelfield.Field;
import org.perfectday.logicengine.model.minis.support.Support;
import org.perfectday.logicengine.model.minis.support.SupportType;

/**
 *
 * @author Miguel Angel Lopez Montellano ( alakat@gmail.com )
 */
public class SupportStack implements Iterable<Support>{

    private List<Support> stack;
    private int index;

    public SupportStack() {
        this.stack = new ArrayList<Support>();
    }
    
    public void buildSupportStack(Field defensorfield,Field atackerField){
        for(Support s:defensorfield.getSupports()){
            if((s.getSupportType()==SupportType.PASIVE_DEFENSIVE) 
                ||(s.getSupportType()==SupportType.ACTIVE_DEFENSIVE))
                this.stack.add(s);                        
        }
        for(Support s:atackerField.getSupports()){
            if((s.getSupportType()==SupportType.ACTIVE_OFENSIVE) 
                ||(s.getSupportType()==SupportType.PASIVE_OFENSIVE))
                this.stack.add(s);                        
        }
        index=0;
    }
    
    
    public SupportStack(Field defensorfield,Field atackerField){
        this.stack = new ArrayList<Support>();
        for(Support s:defensorfield.getSupports()){
            if((s.getSupportType()==SupportType.PASIVE_DEFENSIVE) 
                ||(s.getSupportType()==SupportType.ACTIVE_DEFENSIVE))
                this.stack.add(s);                        
        }
        for(Support s:atackerField.getSupports()){
            if((s.getSupportType()==SupportType.ACTIVE_OFENSIVE) 
                ||(s.getSupportType()==SupportType.PASIVE_OFENSIVE))
                this.stack.add(s);                        
        }
        index=0;
    }

    public List<Support> getStackList(){
        return stack;
    }
    
    public Iterator<Support> iterator() {
        return new IteratorSupportStack(this);
        
    }


}
