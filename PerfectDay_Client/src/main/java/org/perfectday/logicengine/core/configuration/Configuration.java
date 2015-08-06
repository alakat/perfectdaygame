/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.perfectday.logicengine.core.configuration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author Miguel Angel Lopez Montellano ( alakat@gmail.com )
 */
public class Configuration {
    
    private static Configuration instance;
    private static final String PATH_BASE = "../src/main/minis/";

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
    
    public File getSupportFile(){
        return new File(PATH_BASE+"supports.properties");
    }
    
    public File getCombatKeepFile(){
        return new File(PATH_BASE+"combatkeep.properties");
    }
    
    public File getDamageFunctionFile(){
        return new File(PATH_BASE+"damagefunctions.properties");
    }
    
    public File getHitFunctionFile(){
        return new File(PATH_BASE+"hitfunctions.properties");
    }
    
    public File getMiniFile(){
        return new File(PATH_BASE+"minis.properties");
    }
    
    public File getSpellFile(){
        return new File(PATH_BASE+"spellsbook.properties");
    }
    
    public File getStateFile(){
        return new File(PATH_BASE+"states.properties");
    }
    
    public File getWeaponsFile(){
        return new File(PATH_BASE+"weapons.properties");
    }
    
    public File getModifierFile(){
         return new File(PATH_BASE+"supports"+File.separator+"modifiers.properties");
    }
    
    public File getEffectFile(){
        return new File(PATH_BASE+"spells"+File.separator+"effectfunctions.properties");
    }
    public File getFailEffectFile(){
        return new File(PATH_BASE+"spells"+File.separator+"faileffectfunctions.properties");
    }

}
