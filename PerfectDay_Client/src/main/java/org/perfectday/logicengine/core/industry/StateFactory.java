/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.perfectday.logicengine.core.industry;

import java.io.File;
import java.util.Properties;
import org.perfectday.logicengine.core.configuration.Configuration;
import org.perfectday.logicengine.model.state.MiniAttribute;
import org.perfectday.logicengine.model.state.PasiveState;
import org.perfectday.logicengine.model.state.StateType;
import org.perfectday.logicengine.model.unittime.LongUnitTime;
import org.perfectday.logicengine.model.unittime.UnitTime;
import org.perfectday.logicengine.model.unittime.UnitTime;
import org.perfectday.logicengine.model.unittime.UnitTime;

/**
 *
 * @author Miguel Angel Lopez Montellano ( alakat@gmail.com )
 */
public class StateFactory extends IndexFactory{
    
    private static StateFactory instance;
    private static final String NAME="name";
    private static final String TYPE="type";
    private static final String CLASS="class";
    private static final String ATTRIBUTE="attribute";
    private static final String NUMBER="number";
    private static final String EFFECT_TIME="effect_time";
    private static final String ACTIVE_TYPE="active";
    private static final String PASSIVE_TYPE="passive";

    public StateFactory(File f) {
        super(f,true);
    }

    public static StateFactory getInstance() {
        if(instance ==null)
            instance = new StateFactory(Configuration.getInstance().getStateFile());
        return instance;
    }

    @Override
    public Object create(String key) throws Exception {
        Properties p = (Properties) this.database.get(key);
        String name = p.getProperty(StateFactory.NAME);
        StateType st = generateStateType(p.getProperty(StateFactory.TYPE));
        
        if(st == StateType.ATTRIBUTE){
            MiniAttribute ma = generateMiniAttribute(p.getProperty(StateFactory.ATTRIBUTE));
            Number number = generateNumber(p.getProperty(StateFactory.NUMBER));
            UnitTime ut= generateTime(p.getProperty(StateFactory.EFFECT_TIME));
            PasiveState ps = new PasiveState(name,ut,ma,number);
            return ps;
        }else{
            return Class.forName(p.getProperty(StateFactory.CLASS)).
                    getConstructor().newInstance();
        }
        
    }

    private MiniAttribute generateMiniAttribute(String property) {
        int opc = Integer.parseInt(property);
        switch(opc){
            case 0: return MiniAttribute.ATACK;
            case 1: return MiniAttribute.DEFENSE;
            case 2: return MiniAttribute.STRENGTH;
            case 3: return MiniAttribute.RESISTENCE;
            case 4: return MiniAttribute.INICIATIVE;
            case 5: return MiniAttribute.VITALITY;
            case 6: return MiniAttribute.MAGIC_AFFINITY;
            case 7: return MiniAttribute.MOVEMENT;
            default: return MiniAttribute.ATACK;                
        }
    }

    private Number generateNumber(String property) {
        try{
            Long l = new Long(property);
            return l;
        }catch(Exception ex){}
        try{
            Integer i = new Integer(property);
            return i;
        }catch(Exception ex){}
        
        try{
            Double d = new Double(property);
            return d;
        }catch(Exception ex){}
        return null;
    }

    private StateType generateStateType(String property) {
        return property.equals(StateFactory.PASSIVE_TYPE)?StateType.ATTRIBUTE:StateType.OTHER;
    }

    private UnitTime generateTime(String property) {
        return new LongUnitTime(new Long(property));
    }
    
    
    
    
    

}
