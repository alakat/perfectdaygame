package org.perfectday.logicengine.model.unittime;

import org.perfectday.logicengine.model.activationstack.*;


/**
 * Unit time by long
 * @author Miguel
 */
public class LongUnitTime extends UnitTime {

    private long value;
    
    public LongUnitTime (long ut) {
        this.value = ut;
    }

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }
    
    

    /**
     * Indica si esta (this) unidad de tiempo es menor que ut
     * @param ut
     * @return
     */
    @Override
    public boolean isMenorAt(UnitTime ut) {
        return ((LongUnitTime)ut).getValue()>this.getValue(); 
    }

    /**
     * Indica si esta (this)  unidad de tiempo es igual a ut
     * @param ut
     * @return
     */
    @Override
    public boolean isEqualAt(UnitTime ut) {
        return ((LongUnitTime)ut).getValue()==this.getValue();
    }

    /**
     * Indica si esta (this) unidad de tiempo es mayor que ut
     * @param ut
     * @return
     */
    @Override
    public boolean isMayorAt(UnitTime ut) {
        return ((LongUnitTime)ut).getValue()<this.getValue();
    }

    /**
     * 
     * @param o
     * @return
     */
    public int compareTo(Object o) {
        if (o instanceof UnitTime){
            UnitTime unitTime = (UnitTime) o;
            if (isMayorAt(unitTime))
                return 1;
            if (isMenorAt(unitTime))
                return -1;
            return 0;
        }
        return 0;
    }

    @Override
    public String toString() {
        return " ut:"+this.getValue()+" ";
    }

    @Override
    public void plus(UnitTime ut) {
        if ( ut instanceof LongUnitTime)
            this.value = this.value + ((LongUnitTime)ut).getValue();
    }

    
    public Object clone() throws CloneNotSupportedException {
        return new LongUnitTime(value);
    }

    
}

