/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.perfectday.oneplayer.battleloaders;

import es.bitsonfire.PDMinisDatabase;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import org.apache.log4j.Logger;
import org.perfectday.logicengine.core.configuration.Configuration;
import org.perfectday.oneplayer.battleloaders.exception.LoadQuestException;
import org.perfectday.oneplayer.battleloaders.model.Quest;

/**
 * Carga las partidas configuradas en una carpeta de descripci?n de "Quest" 
 * (Qué es nuestro equivalente a misiones, quest, etc)
 * @author Miguel (alakat@gmail.com)
 */
public class LoaderOfQuest {
    private Logger logger =Logger.getLogger(LoaderOfQuest.class);
    private Map<String,Quest> quests;
    private static  LoaderOfQuest instance;

    public static LoaderOfQuest getInstance() {
        if (instance==null){
            instance = new LoaderOfQuest();
        }
        return instance;
    }
    
    private LoaderOfQuest(){
        this.quests=new HashMap<String, Quest>();
        this.loadQuests();
    }
    
    
    private void loadQuests(){
        try {
            Properties quests = new Properties();
            System.out.println(Configuration.QUEST_PATH+Configuration.QUESTS_FILES+Configuration.PROPERTIES_EXTENSION);
            quests.load(PDMinisDatabase.class.getResourceAsStream(
                    Configuration.QUEST_PATH+Configuration.QUESTS_FILES+Configuration.PROPERTIES_EXTENSION));
            Set keys = quests.keySet();
            for (Object key : keys) {
                String value = quests.getProperty((String)key);
                this.quests.put(value, null);
            }
        } catch (IOException ex) {
            logger.error("Error al cargar las quests",ex);
        }
    }
    
    /**
     * Devuelve la quest cargadas en el sistema
     * @return 
     */
    public  Set<String> getQuest(){
        return this.quests.keySet();
    }
    
    
    /**
     * Devuevel la quest seleccionado por el nombre questKey
     * @param questKey Quest a cargar
     * @return Quest parseada
     * @throws IOException
     * @throws LoadQuestException 
     */
    public Quest getQuest(String questKey) throws IOException, LoadQuestException{
        Quest quest= this.quests.get(questKey);
        if(quest==null){
            quest = new Quest(questKey);
            quest.load();
        }
        return quest;
    }
    
    
    
}
