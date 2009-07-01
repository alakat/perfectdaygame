/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.perfectday.main.dummyengine;

import java.util.List;
import org.perfectday.logicengine.core.Game;
import org.perfectday.logicengine.core.player.Player;
import org.perfectday.logicengine.model.command.Command;
import org.perfectday.logicengine.model.unittime.UnitTime;
import org.perfectday.logicengine.model.minis.Mini;
import org.perfectday.error.Error;
import org.perfectday.logicengine.model.battelfield.Field;
import org.perfectday.message.TextMessage.PDTextMessage;

/**
 *
 * @author Miguel Angel Lopez Montellano ( alakat@gmail.com )
 */
public interface GraphicsEngine {

    /**
     * Modifica los {@link Field} accesible por el {@link Mini} seleccionado
     * @param mini
     * @param access
     */
    public void addMiniAccess(Mini mini, List<Field> access);

    public void activateMini(Mini mini);

    public void addInfo(String string);

    public boolean combatFinishWithPlayerWin(Player winer);
    
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
