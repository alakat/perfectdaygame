package org.perfectday.logicengine.model.activationstack.accidents;

import java.util.List;
import org.perfectday.logicengine.core.Game;
import org.perfectday.logicengine.model.ReferenceObject;
import org.perfectday.logicengine.model.unittime.UnitTime;
import org.perfectday.logicengine.model.activationstack.*;
import org.perfectday.logicengine.model.command.Command;


/**
 * Succeso generico en Perfect day
 * @author Miguel
 */
public abstract class Accident extends ReferenceObject implements Comparable {

    
    private UnitTime mUnitTime;

    public Accident() {
    }

    
    public Accident(UnitTime mUnitTime) {
        this.mUnitTime = mUnitTime;
    }


    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,regenBody=yes,id=DCE.8EC53445-C684-E7AB-FF63-6DE36F8871CA]
    // </editor-fold> 
    public UnitTime getUnitTime () {
        return mUnitTime;
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,regenBody=yes,id=DCE.667E701A-F8DC-8A6B-3877-588AFDF2B81E]
    // </editor-fold> 
    public void setUnitTime (UnitTime val) {
        this.mUnitTime = val;
    }

    /**
     * Order Accident about UnitTime
     * @param o
     * @return
     */
    public int compareTo(Object o) {
        if( o instanceof Accident){
            Accident accident = (Accident)o;
            return this.getUnitTime().compareTo(accident.getUnitTime());
        }else{
            return 0;
        }
    }
    
    public String toString(){
        return "Accident ( ut:{"+this.getUnitTime().toString()+"}";
    }
    
    /**
     * Antiguo modo de secuecias del turno
     * @deprecated 
     * @param commands
     * @param game
     * @throws java.lang.Exception
     */
    public abstract void doAccident(List<Command> commands,Game game) throws Exception;
    
    
    /**
     * Nuevo modo de secuencia del turno basado en los eventos del juego.
     * @param game
     * @throws java.lang.Exception
     */
    public abstract void doAccidentWithEvent(Game game)throws Exception;
}

