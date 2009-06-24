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
public abstract class Mission {
   
    protected Game game;
    public Mission(Game game){
        this.game=game;
    }
    public abstract boolean missionCompleted();

    public abstract Player getWiner();
}
