/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.perfectday.logicengine.core.industry;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.Properties;
import org.perfectday.logicengine.combat.model.combatkeep.CombatKeep;
import org.perfectday.logicengine.core.configuration.Configuration;
import org.perfectday.logicengine.core.industry.exception.BadConfigurationFile;
import org.perfectday.logicengine.model.minis.support.PassiveSupport;
import org.perfectday.logicengine.model.minis.support.Support;
import org.perfectday.logicengine.model.minis.support.SupportType;
import org.perfectday.logicengine.model.minis.support.modifiers.Modifier;


/**
 *
 * @author Miguel Angel Lopez Montellano ( alakat@gmail.com )
 */
public class SupportFactory extends IndexFactory {

    private static final String OFFENSIVE_SUPPORT="offensive";
    private static final String DEFENSIVE_SUPPORT="defensive";
    private static final String PASSIVE_SUPPORT="passive";
    private static final String ACTIVE_SUPPORT="active";
    private static final String DESCRIPTION="description";
    private static final String ORDEN="orden";
    private static final String TYPE="type";
    private static final String CLASS="class";
    private static final String INFLUENCE="influence";
    private static final String PROPAGABLE="propagable";
    private static final String MODIFIER="modifier";
    private static final String NUMBER="number";
    
    private static SupportFactory instance;
    
    public static SupportFactory getInstance(){
        if (instance == null)
            instance = new SupportFactory(Configuration.getInstance().getSupportFile());
        return instance;
    }
    
    private SupportFactory(File f)  {        
        super(f,true);
    }   
    
    @Override
    public Object create(String key) throws Exception {
        Properties p = (Properties) this.database.get(key);
        try{
            if(p.getProperty(SupportFactory.ORDEN).
                    equals(SupportFactory.ACTIVE_SUPPORT)){
                return createSupportByClass(p);                
            }else{
                return createSupportByFile(p);
            }
        }catch(Exception ex){
            logger.error("El ficherode configuración para ["+key+"] está mal escrito");
            throw new BadConfigurationFile(SupportFactory.class.getName()+":"+key);
        }
        
    }


    
    private Object createSupportByClass(Properties p) throws ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, Exception {
        String clazz = p.getProperty(SupportFactory.CLASS);
        Support s = (Support)  Class.forName(clazz).getConstructor().newInstance();
        s.setDescription(p.getProperty(SupportFactory.DESCRIPTION));
        s.setSpread(getSpread(p.getProperty(SupportFactory.PROPAGABLE)));
        s.setSupportKeep(getSupportKeep(p.getProperty(SupportFactory.INFLUENCE)));
        s.setSupportType(getSupportType(p.getProperty(SupportFactory.TYPE),p.getProperty(SupportFactory.ORDEN)));
        return s;
    }

    private Object createSupportByFile(Properties p) throws Exception {
        SupportType supportType = getSupportType(p.getProperty(SupportFactory.TYPE),p.getProperty(SupportFactory.ORDEN));
        CombatKeep supportKeep = getSupportKeep(p.getProperty(SupportFactory.INFLUENCE));
        boolean spread = getSpread(p.getProperty(SupportFactory.PROPAGABLE));
        Modifier modifier = getModifier(    p.getProperty(SupportFactory.MODIFIER),
                p.getProperty(SupportFactory.NUMBER));
        String description = p.getProperty(SupportFactory.DESCRIPTION);
        return new PassiveSupport(supportType, supportKeep, spread, null, modifier, description);
    }

    private Modifier getModifier(String modifier,String number) throws Exception {
        Modifier m = (Modifier) ModifierFactory.getInstance().create(modifier);
        m.setNumber(generateNumber(number));
        return m;
    }
    
    public Number generateNumber(String number){
        try{
            Long l = new Long(number);
            return l;
        }catch(Exception ex){}
        try{
            Integer i = new Integer(number);
            return i;
        }catch(Exception ex){}
        
        try{
            Double d = new Double(number);
            return d;
        }catch(Exception ex){}
        return null;
    }

    private boolean getSpread(String propagable) {
        return Boolean.parseBoolean(propagable);
    }

    private CombatKeep getSupportKeep(String property) throws Exception {
        return (CombatKeep) CombatKeepFactory.getInstance().create(property);
    }

   

    private SupportType getSupportType(String type, String orden) {
        if(type.equals(SupportFactory.PASSIVE_SUPPORT)){
            if(orden.equals(SupportFactory.OFFENSIVE_SUPPORT)){
                return SupportType.PASIVE_OFENSIVE;
            }else{
                return SupportType.PASIVE_DEFENSIVE;
            }
        }else{
            if(orden.equals(SupportFactory.OFFENSIVE_SUPPORT)){
                return SupportType.ACTIVE_OFENSIVE;
            }else{
                return SupportType.ACTIVE_DEFENSIVE;
            }
        }
        
            
    }

}
