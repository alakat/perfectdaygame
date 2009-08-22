/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.bitsonfire.perfectday.autoplay;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.List;
import org.perfectday.logicengine.core.event.Event;
import org.perfectday.logicengine.core.player.Player;
import org.perfectday.logicengine.model.battelfield.BattelField;
import org.perfectday.logicengine.model.minis.Mini;

/**
 * Representa la información que es necesario almacenar para poder reproducir
 * una partida.
 * @author Miguel Angel Lopez Montellano (alakat@gmail.com)
 */
public class GameBackUpData {

    private Player redPlayer;
    private Player bluePlayer;
    private List<Mini> redArmy;
    private List<Mini> blueArmy;
    private List<Event> gameEvents;
    private BattelField battleField;

    public GameBackUpData() {
    }

    public BattelField getBattleField() {
        return battleField;
    }

    public void setBattleField(BattelField battleField) {
        this.battleField = battleField;
    }

    public List<Mini> getBlueArmy() {
        return blueArmy;
    }

    public void setBlueArmy(List<Mini> blueArmy) {
        this.blueArmy = blueArmy;
    }

    public Player getBluePlayer() {
        return bluePlayer;
    }

    public void setBluePlayer(Player bluePlayer) {
        this.bluePlayer = bluePlayer;
    }

    public List<Mini> getRedArmy() {
        return redArmy;
    }

    public void setRedArmy(List<Mini> redArmy) {
        this.redArmy = redArmy;
    }

    public Player getRedPlayer() {
        return redPlayer;
    }

    public void setRedPlayer(Player redPlayer) {
        this.redPlayer = redPlayer;
    }

    public List<Event> getGameEvents() {
        return gameEvents;
    }

    public void setGameEvents(List<Event> gameEvents) {
        this.gameEvents = gameEvents;
    }


    /**
     * Salva los datos en un fichero seleccionado.
     * @param file
     * @throws java.io.IOException
     */
    public void save(File file) throws IOException {
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
        oos.writeObject(this);
    }
}
