/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.perfectday.oneplayer.battleloaders.model;

import es.bitsonfire.PDMinisDatabase;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import org.apache.log4j.Logger;
import org.perfectday.gamebuilder.model.MiniDescription;
import org.perfectday.logicengine.core.configuration.Configuration;
import org.perfectday.logicengine.model.minis.MiniLevel;

/**
 * Almacena la informaci?n sobre el ejecirto para la partida
 * @author Miguel (alakat@gmail.com)
 */
public class Army {
    
    

    private Logger logger=Logger.getLogger(Army.class);
    
    private List<MiniDescription> minisEvil;
    private List<MiniDescription> minisHeores;

    public Army() {
        this.minisEvil = new ArrayList<MiniDescription>();
        this.minisHeores = new ArrayList<MiniDescription>();
    }

    
    public void loadArmy(String namequest) throws IOException{
        Properties armydescription = new Properties();
        armydescription.load(PDMinisDatabase.class.getResourceAsStream(
                Configuration.QUEST_PATH+Configuration.QUEST_TOKEN_SEPARATOR+namequest //Por ejemplo /data/quest/
                        +Configuration.PRE_NAME_ARMY+namequest+Configuration.PROPERTIES_EXTENSION)); //por ejemplo /army_tutorial.properties
        
        //Bando del mal
        int nMinis = Integer.parseInt(armydescription.getProperty("army_evil_size"));
        for(int i=0;i<nMinis;i++){
            parseMini("evil_",i, armydescription,this.minisEvil);
        }
        nMinis = Integer.parseInt(armydescription.getProperty("army_heroes_size"));
        for(int i=0;i<nMinis;i++){
            parseMini("heroes_",i, armydescription,this.minisHeores);
        }
        
    }

    /**
     * Lee un mini del fichero de descripci?n del ejercito de la quest
     * @param band indicador de bando
     * @param i indice del mini a parsear
     * @param armydescription properties del ejercito
     * @param army lista donde se cargar? el mini
     * @throws NumberFormatException 
     */
    public void parseMini(String band,int i, Properties armydescription, List<MiniDescription> army) throws NumberFormatException {
        String key = "army_"+band+"mini."+i;
        logger.debug("Mini to load :"+key);
        String mini =
                armydescription.getProperty(key+".type");
        MiniLevel miniLevel = MiniLevel.valueOf(
                armydescription.getProperty(key+".level"));
        int x = Integer.parseInt(
                armydescription.getProperty(key+".x"));
        int y = Integer.parseInt(
                armydescription.getProperty(key+".y"));
        String name = armydescription.getProperty(key+".name");
        MiniDescription miniDescription = new MiniDescription();
        miniDescription.setLevel(miniLevel);
        miniDescription.setMini(mini);
        miniDescription.setStartX(x);
        miniDescription.setStartY(y);
        miniDescription.setName(name);
        if(armydescription.keySet().contains(key+".description")){
            miniDescription.setDescription(armydescription.getProperty(key+".description"));
        }
        army.add(miniDescription);
    }

    public List<MiniDescription> getMinisEvil() {
        return minisEvil;
    }

    public List<MiniDescription> getMinisHeores() {
        return minisHeores;
    }
    
    
    
}
