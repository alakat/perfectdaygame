/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.perfectday.logicengine.core.configuration;

import es.bitsonfire.PDMinisDatabase;
import java.io.InputStream;
import java.util.Properties;


/**
 *
 * @author Miguel Angel Lopez Montellano ( alakat@gmail.com )
 */
public class Configuration {
    
    private static Configuration instance;
    public static final String ASSETS_PATH = "assets";
    public static final String DATA_MINI_PATH = "/data/minis/";
    public static final String QUEST_PATH ="/data/quest";
    public static final String QUESTS_FILES = "/quests";
    public static final String QUEST_TOKEN_SEPARATOR = "/";
    public static final String PRE_NAME_ARMY = "/army_";
    public static final String DESCRIPTION_QUEST="/description_";
    public static final String BATTLEFIELD_QUEST="/battlefield_";
    public static final String PROPERTIES_EXTENSION = ".properties";

    public Configuration() {
        try {
            Properties p = new Properties();
            p.load(Configuration.class.getClassLoader().getResourceAsStream("assets/log4j.properties"));
            org.apache.log4j.PropertyConfigurator.configure(p);
        } catch (Exception ex) {
            ex.printStackTrace(System.err);
        }
    }

    public static Configuration getInstance() {
        
        if(instance ==null)
            instance =new Configuration();
        return instance;
    }
    
    public InputStream getSupportFile(){
        return PDMinisDatabase.class.getResourceAsStream(DATA_MINI_PATH+"supports.properties");
    }
    
    public InputStream getCombatKeepFile(){
        return PDMinisDatabase.class.getResourceAsStream(DATA_MINI_PATH+"combatkeep.properties");
    }
    
    public InputStream getDamageFunctionFile(){
        return PDMinisDatabase.class.getResourceAsStream(DATA_MINI_PATH+"damagefunctions.properties");
    }
    
    public InputStream getHitFunctionFile(){
        return PDMinisDatabase.class.getResourceAsStream(DATA_MINI_PATH+"hitfunctions.properties");
    }
    
    public InputStream getMiniFile(){
        return PDMinisDatabase.class.getResourceAsStream(DATA_MINI_PATH+"minis.properties");
    }
    
    public InputStream getSpellFile(){
        return PDMinisDatabase.class.getResourceAsStream(DATA_MINI_PATH+"spellsbook.properties");
    }
    
    public InputStream getStateFile(){
        return PDMinisDatabase.class.getResourceAsStream(DATA_MINI_PATH+"states.properties");
    }
    
    public InputStream getWeaponsFile(){
        return PDMinisDatabase.class.getResourceAsStream(DATA_MINI_PATH+"weapons.properties");
    }
    
    public InputStream getModifierFile(){
         return PDMinisDatabase.class.getResourceAsStream(DATA_MINI_PATH+"supports/modifiers.properties");
    }
    
    public InputStream getEffectFile(){
        return PDMinisDatabase.class.getResourceAsStream(DATA_MINI_PATH+"spells/effectfunctions.properties");
    }
    public InputStream getFailEffectFile(){
        return PDMinisDatabase.class.getResourceAsStream(DATA_MINI_PATH+"spells/faileffectfunctions.properties");
    }

    public InputStream getBasicAttribute() {
        return PDMinisDatabase.class.getResourceAsStream("/data/conf/basic-attributes.properties");
    }

}
