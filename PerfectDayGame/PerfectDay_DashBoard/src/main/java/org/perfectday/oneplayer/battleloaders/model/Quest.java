/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.perfectday.oneplayer.battleloaders.model;

import es.bitsonfire.PDMinisDatabase;
import java.io.IOException;
import java.util.Properties;
import org.apache.log4j.Logger;
import org.perfectday.gamebuilder.model.Mission;
import org.perfectday.logicengine.core.configuration.Configuration;
import org.perfectday.oneplayer.battleloaders.exception.LoadQuestException;

/**
 * Informaci?n sobre la quest
 * @author Miguel (alakat@gmail.com)
 */
public class Quest {
    
    private Logger logger = Logger.getLogger(Quest.class);
    
    private String title;
    private String description;
    private int point;
    private Mission mission;
    private String tooltips;
    private Army army;
    private BattleFieldQuest battleField;
    private String quest;

    /**
     * Constructor de la quest
     * @param quest nombre de la quest, identifica a la carpeta y a los ficheros
     */
    public Quest(String quest) {
        this.army=new Army();
        this.battleField = new BattleFieldQuest();
        this.quest=quest;
        
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the point
     */
    public int getPoint() {
        return point;
    }

    /**
     * @param point the point to set
     */
    public void setPoint(int point) {
        this.point = point;
    }

    /**
     * @return the mission
     */
    public Mission getMission() {
        return mission;
    }

    /**
     * @param mission the mission to set
     */
    public void setMission(Mission mission) {
        this.mission = mission;
    }

    /**
     * @return the army
     */
    public Army getArmy() {
        return army;
    }

    /**
     * @param army the army to set
     */
    public void setArmy(Army army) {
        this.army = army;
    }

    /**
     * @return the battleField
     */
    public BattleFieldQuest getBattleField() {
        return battleField;
    }

    /**
     * @param battleField the battleField to set
     */
    public void setBattleField(BattleFieldQuest battleField) {
        this.battleField = battleField;
    }
    
    /**
     * Se solcita la quest por par?metro por homogeneizar con los método gemelos 
     * de army y battlefield
     * @param quest 
     */
    private void loadQuest(String quest) throws IOException{
        Properties questDescription = new Properties();
        logger.info("loading: quest:"+quest);
        logger.debug("File:"+Configuration.QUEST_PATH+Configuration.QUEST_TOKEN_SEPARATOR+quest //Por ejemplo /data/quest/quest
                        +Configuration.DESCRIPTION_QUEST+quest+Configuration.PROPERTIES_EXTENSION);
        questDescription.load(PDMinisDatabase.class.getResourceAsStream(
                Configuration.QUEST_PATH+Configuration.QUEST_TOKEN_SEPARATOR+quest //Por ejemplo /data/quest/
                        +Configuration.DESCRIPTION_QUEST+quest+Configuration.PROPERTIES_EXTENSION)); //por ejemplo /army_tutorial.properties
        this.description = questDescription.getProperty("description");
        this.title= questDescription.getProperty("title");
        this.mission = Mission.valueOf(questDescription.getProperty("mission"));
        this.point = Integer.parseInt(questDescription.getProperty("points"));
        this.tooltips = questDescription.getProperty("tooltips");
        
        
    }
    
    public void load() throws IOException, LoadQuestException{
        //load QuestDescription
        this.loadQuest(this.quest);
        //load army
        this.army.loadArmy(this.quest);
        //load battlefield
        this.battleField.loadBattlefield(this.quest);
    }

    public String getTooltips() {
        return tooltips;
    }

    public void setTooltips(String tooltips) {
        this.tooltips = tooltips;
    }

    @Override
    public String toString() {
        return "QUEST: ["+this.title+"]: "+this.description;
    }

    public String getQuest() {
        return quest;
    }
    
    
    
    
    
}
