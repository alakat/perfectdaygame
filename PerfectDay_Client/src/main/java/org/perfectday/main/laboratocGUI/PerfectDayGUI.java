/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.perfectday.main.laboratocGUI;

import java.util.List;
import org.perfectday.logicengine.core.Game;
import org.perfectday.logicengine.model.command.Command;
import org.perfectday.logicengine.model.unittime.UnitTime;
import org.perfectday.logicengine.model.minis.Mini;
import org.perfectday.error.Error;
import org.perfectday.message.TextMessage.PDTextMessage;

/**
 *
 * @author Miguel Angel Lopez Montellano ( alakat@gmail.com )
 */
public interface PerfectDayGUI {

    public void activateMini(Mini mini);
    
    public void desactivateMini();
    
    /**
     * Tiempo del turno que cierrael turno.
     * @return
     */
    public UnitTime getTurnCost();

    /**
     * Tiempo actual de las acciones del usuarios
     * @return
     */
    public UnitTime getUnitTime();

    public void redraw();


    
    public void setGame(Game game);
    
    public void proccessCommand(List<Command> commands);

    public void setTurnCost(UnitTime ut);

    public void showInfoCombat(List<String> paper);
    
    public void showError(Error error);
    
    public void showTextMessage(PDTextMessage message);
    
    public void showChatArea();
    
}
