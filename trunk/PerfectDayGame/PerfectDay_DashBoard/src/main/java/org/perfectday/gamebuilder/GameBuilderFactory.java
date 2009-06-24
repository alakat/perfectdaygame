/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.perfectday.gamebuilder;

import java.util.HashMap;
import java.util.Map;
import org.perfectday.dashboard.communication.GameBuilderCommunicator;
import org.perfectday.dashboard.communication.GameBuilderGateWay;

/**
 *
 * @author Miguel Angel Lopez Montellano (alakat@gmail.com)
 */
public class GameBuilderFactory {
    
    private static final GameBuilderFactory instance = new GameBuilderFactory();
    private Map<String,GameBuilder> builders;

    private GameBuilderFactory() {
        this.builders = new HashMap<String, GameBuilder>();
    }

    public void delete(String participant) {
       if(this.builders.containsKey(participant))
           this.builders.remove(participant);
    }
    
    public GameBuilder getGameBuilder(String userDestiny){
        if(this.builders.containsKey(userDestiny))
            return this.builders.get(userDestiny);
        return null;
    }

    public static GameBuilderFactory getInstance() {
        return instance;
    }
    
    public GameBuilderServer createGameBuilderServer(String userDestiny) throws NoSuchMethodException{
        GameBuilderCommunicator bgc =
                GameBuilderGateWay.getInstance().getCommunication(userDestiny);
        GameBuilderServer gb = new GameBuilderServer(bgc);
        bgc.setGameBuilder(gb);
        this.builders.put(userDestiny, gb);
        return gb;
    }
    
    
    public GameBuilderClient createGameBuilderClient(String userDestiny) throws NoSuchMethodException{
        GameBuilderCommunicator bgc =
                GameBuilderGateWay.getInstance().getCommunication(userDestiny);
        GameBuilderClient gb = new GameBuilderClient(bgc);
        bgc.setGameBuilder(gb);
        this.builders.put(userDestiny, gb);
        return gb;
    }

}
