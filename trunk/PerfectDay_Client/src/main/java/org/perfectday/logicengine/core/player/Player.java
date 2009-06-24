/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.perfectday.logicengine.core.player;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import org.apache.log4j.Logger;
import org.perfectday.logicengine.brain.Brain;
import org.perfectday.logicengine.brain.simple.SimpleBrain;
import org.perfectday.logicengine.core.Game;
import org.perfectday.logicengine.model.ReferenceObject;
import org.perfectday.logicengine.model.minis.Mini;
import org.perfectday.main.laboratocGUI.model.CreateBandDialog;

/**
 *
 * @author Miguel Angel Lopez Montellano ( alakat@gmail.com )
 */
public class Player extends ReferenceObject{

    private String name;
    private List<Mini> band;
    private boolean observer;
    private boolean ia;
    private Object distingibleObjectBand;
    private Brain brain;
    private boolean local;


    public Player(String name,boolean ia) {
        this.name = name;
        this.band = new ArrayList<Mini>();
        this.ia = ia;      
        this.local=true;
        this.distingibleObjectBand = Color.BLACK;
        if(ia){
            Logger.getLogger(Player.class).info("Player game with IA");
            loadBrain();
        }
    }


    public List<Mini> getBand() {
        return band;
    }

    public Object getDitingibleBandObject() {
        return this.distingibleObjectBand;
    }

    public void setBand(List<Mini> band) {
        this.band = band;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isObserver() {
        return observer;
    }

    public void setObserver(boolean observer) {
        this.observer = observer;
    }

    public void setDistingibleObjectBand(Object distingibleObjectBand) {
        this.distingibleObjectBand = distingibleObjectBand;
    }
    
    
    
    public static Player createPlayerRojo(){
        CreateBandDialog bandDialog = new CreateBandDialog(null,true,100);
        bandDialog.setVisible(true);
        Player player = bandDialog.getPlayer();
//        player.setDistingibleObjectBand(Color.RED);
        return player;
    }
    
    public static Player createPlayerAzul(){
        CreateBandDialog bandDialog = new CreateBandDialog(null,true,100);
        bandDialog.setVisible(true);
        Player player = bandDialog.getPlayer();
//        player.setDistingibleObjectBand(Color.BLUE);
        return player;
    }

    @Override
    public String toString() {
        return "Jugador:"+this.name;
    }
    
    /**
     * 
     * @param mini
     * @return
     */
    public boolean isBandMember(Mini mini){
        return this.band.contains(mini);
    }

    public Brain getBrain() {
        return brain;
    }

    public void setBrain(Brain brain) {
        this.brain = brain;
    }

    public boolean isIa() {
        return ia;
    }

    public void setIa(boolean ia) {
        this.ia = ia;
    }

    /**setPlugCommunication tiene un tipo abstracto como argumento
     * Load I.A, when I.A change, this method should be change too
     */
    private void loadBrain() {
        try {
            String nProperties = Game.BRAIN_PROPERTIES;
            Properties properties = new Properties();
            properties.load(Player.class.getClassLoader().getSystemResourceAsStream(nProperties));
            this.brain = new SimpleBrain(properties);
        } catch (IOException ex) {
            Logger.getLogger(Player.class.getName()).
                    error("Load Brain:"+ex.getClass().getName(),ex);
        }
    }

    public boolean isLocal() {
        return local;
    }

    public void setLocal(boolean local) {
        this.local = local;
    }
    
    
    
    
}
