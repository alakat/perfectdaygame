/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.perfectday.gamebuilder;

import java.awt.Color;
import java.lang.reflect.InvocationTargetException;
import org.apache.log4j.Logger;
import org.perfectday.dashboard.exception.GameBuilderException;
import org.perfectday.dashboard.gui.quests.SelectQuestDialog;
import org.perfectday.gamebuilder.model.DashBoardMiniUtilities;
import org.perfectday.gamebuilder.model.MiniDescription;
import org.perfectday.logicengine.core.Game;
import org.perfectday.logicengine.model.minis.Mini;
import org.perfectday.oneplayer.battleloaders.model.Quest;

/**
 *
 * @author Miguel (alakat@gmail.com)
 */
public class GameBuilderSelectionQuest extends GameBuilderOnePlayer{

    private Logger logger = Logger.getLogger(GameBuilderSelectionQuest.class);
    private Quest quest;
    
    public GameBuilderSelectionQuest() throws NoSuchMethodException {
    }

    @Override
    public void configAIArmy() throws IllegalAccessException, InvocationTargetException {
        this.move();
    }

    @Override
    public void configPlayerArmy() {
        SelectQuestDialog dialog = new SelectQuestDialog(null, true);
        dialog.PDshow();
        //TODO Cancelar
        if(dialog.isQuestSelected()){
            this.quest = dialog.getSelectedQuest();
        }else{
            logger.warn("TODO: CANCELAR PARTIDAS!!");
        }
        logger.info("QUEST:"+dialog.getSelectedQuest());
        try {
            this.move();
        } catch (IllegalAccessException ex) {
            logger.error(ex.getMessage(),ex);
        } catch (InvocationTargetException ex) {
            logger.error(ex.getMessage(),ex);
        }

    }

    @Override
    public void generateMap(){
        
        //Campo de batalla
        this.battlefield =quest.getBattleField().getBattlefield();
        
        //Cargamos el ejercito del mal
        for (MiniDescription miniDescription : this.quest.getArmy().getMinisEvil()) {
            try {
                Mini mini =  DashBoardMiniUtilities.buildMini(miniDescription);
                this.battlefield.getBattelfield()[miniDescription.getStartX()][miniDescription.getStartY()].setMiniOcupant(mini); 
                this.getTrueClientArmy().add( mini );
            } catch (GameBuilderException ex) {
                logger.error(ex.getMessage(),ex);
            }
        }
        
        //cargamos los heroes
        for (MiniDescription miniDescription : this.quest.getArmy().getMinisHeores()) {
            try {
                Mini mini =  DashBoardMiniUtilities.buildMini(miniDescription);
                this.battlefield.getBattelfield()[miniDescription.getStartX()][miniDescription.getStartY()].setMiniOcupant(mini); 
                this.getTrueServerArmy().add(mini);
            } catch (GameBuilderException ex) {
                logger.error(ex.getMessage(),ex);
            }
        }
        
        //Despliegue
        //Implicito en la creaci?n del ejecirto
        
        
        //Banderas de bandos
        
        
        //Movemos el motor de estado
         try {
            this.move();
        } catch (IllegalAccessException ex) {
            logger.error(ex.getMessage(),ex);
        } catch (InvocationTargetException ex) {
            logger.error(ex.getMessage(),ex);
        }

    }

    @Override
    public void starGame() {
        super.starGame(); 
        Game.getGame().getPlayerByMini(this.getTrueClientArmy().get(0)).setDistingibleObjectBand(Color.RED);
        Game.getGame().getPlayerByMini(this.getTrueServerArmy().get(0)).setDistingibleObjectBand(Color.BLUE);
    }
    
    
    
    
    
    
}
