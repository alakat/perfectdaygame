/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.perfectday.logicengine.model.unittime.factories;

import org.apache.log4j.Logger;
import org.perfectday.logicengine.model.minis.Mini;
import org.perfectday.logicengine.model.unittime.LongUnitTime;
import org.perfectday.logicengine.model.unittime.UnitTime;

/**
 *
 * @author Miguel Angel Lopez Montellano ( alakat@gmail.com )
 */
public class LongUnitTimeFactory {
    public static final long MOVEMENT_COST = 10L;
    public static final long MOVEMENT_FAIL_A_POR_ELLOS = 22L;
    public static final long DO_NOTHING=5l;
    public static final long COMBAT=12l;
    public static final long DO_CAST_SPELL=8;
    

    private static LongUnitTimeFactory instance;

    public LongUnitTimeFactory() {
    }

    public static LongUnitTimeFactory getInstance() {
        if(instance==null)
            instance = new LongUnitTimeFactory();
        return instance;
    }

    public UnitTime doCastSpell(Mini mini) {
        return new LongUnitTime((getMiniSpeed(DO_CAST_SPELL,mini)));
    }
    
    public LongUnitTime doMovementAction(Mini mini){
        return new LongUnitTime((getMiniSpeed(MOVEMENT_COST,mini)));
    }
    
    public LongUnitTime doMovementFailAPorEllos(Mini mini){
        return new LongUnitTime((getMiniSpeed(MOVEMENT_FAIL_A_POR_ELLOS,mini)));
    }  
    
    public LongUnitTime doNothing(Mini mini){
        return new LongUnitTime((getMiniSpeed(DO_NOTHING,mini)));
    }
    
    public LongUnitTime doCombat(Mini mini){
        return new LongUnitTime((getMiniSpeed(COMBAT,mini)));
    }

    private long getMiniSpeed(long cost,Mini mini) {
        Logger.getLogger(LongUnitTimeFactory.class).info("Cost Base:"+cost);
        double halfcost = ((double)cost)/2;
        Logger.getLogger(LongUnitTimeFactory.class).info("halfcost:"+halfcost);
        halfcost =halfcost -(mini.getIniciative()<2?0:((int)mini.getIniciative()));
        Logger.getLogger(LongUnitTimeFactory.class).info("Halfcost.mod"+halfcost);
        halfcost = (((double)cost)/2) + (halfcost<0?0:halfcost);
        Logger.getLogger(LongUnitTimeFactory.class).info("Cost:"+halfcost);
        return new Double( halfcost ).longValue();
    }
}
