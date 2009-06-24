/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.perfectday.logicengine.missions;

import org.perfectday.logicengine.core.Game;
import org.perfectday.logicengine.core.player.Player;

/**
 *
 * @author Miguel Angel Lopez Montellano ( alakat@gmail.com )
 */
public class SearchAndDestroyMission extends Mission {

    
    public SearchAndDestroyMission(Game game) {
        super(game);
        
    }

    
    @Override
    public boolean missionCompleted() {
        boolean result = false;
        for(Player player:this.game.getPlayers()){            
            if((!player.isObserver())){
                if (player.getBand().isEmpty()){                    
                    result= true;
                }
            }
        }
        return result;
    }

    @Override
    public Player getWiner() {
        for(Player player:this.game.getPlayers()){
            if((!player.isObserver()) && !player.getBand().isEmpty()){
                return player;
            }
        }
        return null;
    }
    

}
