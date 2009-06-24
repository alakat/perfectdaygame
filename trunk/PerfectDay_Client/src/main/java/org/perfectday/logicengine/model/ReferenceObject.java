/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.perfectday.logicengine.model;

import java.util.Random;
import org.perfectday.logicengine.core.MasterReferenceObject;
import org.perfectday.message.ReferenceObjectVO;

/**
 *
 * @author Miguel Angel Lopez Montellano ( alakat@gmail.com )
 */
public class ReferenceObject implements Cloneable{

    private long id;

    public ReferenceObject() {
        this.id = MasterReferenceObject.getInstance().getNewReference();
    }
    
    public ReferenceObject(long id){
        this.id = id;
    }

    public long getId() {
        return id;
    }
    
    public void setId(long id){
        this.id=id;
    }

    @Override
    public boolean equals(Object obj) {
        if( obj instanceof ReferenceObject){
            ReferenceObject referenceObject = (ReferenceObject) obj;
            return referenceObject.getId()==this.getId();
        }            
        return false;
    }
    
    
    public ReferenceObjectVO createReferenceObjectVO(){
        return new ReferenceObjectVO(id);
    }

    @Override
    public ReferenceObject clone()  {
        return new ReferenceObject(id);
    }
    
    /**
     * Devuelve un Nuevo objeto del tipo ReferenceObject pero con la misma ID 
     * @return new ReferenceObject(id);
     */
    public ReferenceObject getReferenceObject(){
        return new ReferenceObject(id);
    }
    
}
