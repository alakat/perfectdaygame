/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.perfectday.dashboard.communication;

import java.util.HashMap;
import java.util.Map;
import org.apache.log4j.Logger;

/**
 * 
 * @author Miguel Angel Lopez Montellano (alakat@gmail.com)
 */
public class GameBuilderGateWay {

    private static final GameBuilderGateWay instance = new GameBuilderGateWay();
    private static final Logger logger = Logger.getLogger(GameBuilderGateWay.class);
    
    
    private Map<String,GameBuilderCommunicator> gameInConstruction;
    public GameBuilderGateWay() {
        this.gameInConstruction = new HashMap<String,GameBuilderCommunicator>();
    }

    public GameBuilderCommunicator getCommunication(String userDestiny) {
        if(gameInConstruction.containsKey(userDestiny))
            return gameInConstruction.get(userDestiny);
        GameBuilderCommunicator bgc = new GameBuilderCommunicator();
        this.gameInConstruction.put(userDestiny, bgc);
        return bgc;
    }

   
    public static GameBuilderGateWay getInstance() {
        return instance;
    }

    /**
     * Nuevo enemigo solicita partida
     * @param from
     * @param string
     */
    void newHomeMessage(String from, String string) {
        logger.info("New Home message["+from+"]");
    }

    public Map<String, GameBuilderCommunicator> getGameInConstruction() {
        return gameInConstruction;
    }

    public void setGameInConstruction(Map<String, GameBuilderCommunicator> gameInConstruction) {
        this.gameInConstruction = gameInConstruction;
    }
    
    
    
    
    
    
    
    
    
}
