package org.perfectday.logicengine.model.unittime;


/**
 * Unidad de medida para las acciones en Perfect Day
 * @author Miguel Angel Lopez Montellano ( alakat@gmail.com )
 */

public abstract class UnitTime  implements Comparable, Cloneable{

    public UnitTime () {
    }
    
    public abstract boolean isMenorAt(UnitTime ut);
    
    public abstract boolean isEqualAt(UnitTime ut);
    
    public abstract boolean isMayorAt(UnitTime ut);
    
    public abstract String toString();
    
    public abstract void plus(UnitTime ut);

    
    public abstract Object clone() throws CloneNotSupportedException;
        
    
    
    

}

