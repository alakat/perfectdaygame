/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.perfectday.logicengine.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author Miguel Angel Lopez Montellano ( alakat@gmail.com )
 */
public class MasterReferenceObject {

    private static MasterReferenceObject instance;
    private List<Long> list;
    private Random random;
    private int pointer;
    private static final int INITIAL_REFERENCES=10000;
    public MasterReferenceObject() {
        this.list = new ArrayList<Long>(INITIAL_REFERENCES);
        this.random = new Random(System.currentTimeMillis());
        for(int i=0;i<INITIAL_REFERENCES;i++)
            this.generateNewReference();
        pointer=0;
        
    }      
    
    public static MasterReferenceObject getInstance(){
        if (instance ==null ){
            instance= new MasterReferenceObject();
        }
        return instance;            
    }
    
    public long getNewReference(){
        long x = this.list.get(pointer);
        pointer++;                
        return x;
    }

    private void generateNewReference() {
        long x = this.random.nextLong();
        while (this.list.contains(new Long(x))) {
            x = this.random.nextLong();
        }
        this.list.add(new Long(x));        
    }
}
