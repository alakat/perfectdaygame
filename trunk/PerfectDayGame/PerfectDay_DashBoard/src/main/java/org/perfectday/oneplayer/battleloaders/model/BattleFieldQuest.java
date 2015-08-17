/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.perfectday.oneplayer.battleloaders.model;

import es.bitsonfire.PDMinisDatabase;
import java.io.IOException;
import java.util.Properties;
import org.perfectday.logicengine.core.configuration.Configuration;
import org.perfectday.logicengine.model.battelfield.BattelField;
import org.perfectday.logicengine.model.battelfield.Field;
import org.perfectday.logicengine.model.battelfield.TypeField;
import org.perfectday.oneplayer.battleloaders.exception.LoadQuestException;

/**
 *
 * @author Miguel (alakat@gmail.com)
 */
public class BattleFieldQuest {

    private BattelField battlefield;
    private int x;
    private int y;
    private final String TOKEN_GRASS = "-";
    private final String TOKEN_ROCK = "X";
    private final String TOKEN_TREE = "T";

    public BattleFieldQuest() {
        
    }
    
    

    /**
     * Carga un campo de batalla
     * @param quest
     * @throws IOException
     * @throws LoadQuestException 
     */
    public void loadBattlefield(String quest) throws IOException, LoadQuestException {
        Properties battlefieldDescription = new Properties();
        battlefieldDescription.load(PDMinisDatabase.class.getResourceAsStream(
                Configuration.QUEST_PATH + Configuration.QUEST_TOKEN_SEPARATOR + quest //Por ejemplo /data/quest/
                + Configuration.BATTLEFIELD_QUEST + quest + Configuration.PROPERTIES_EXTENSION)); //por ejemplo /army_tutorial.properties
        String sizeBattlefield = battlefieldDescription.getProperty("battlefield.size");
        this.x = Integer.parseInt(sizeBattlefield.trim().split(",")[0].trim());
        this.y = Integer.parseInt(sizeBattlefield.trim().split(",")[1].trim());
        this.battlefield = new BattelField(y, x);
        for (int j = 0; j < this.y; j++) {                  
            String keyRow="battlefield.row." + j;
            String row = battlefieldDescription.getProperty(keyRow).trim();
            if (row.length() != this.x) {
                throw new LoadQuestException("size battlefield error: " + sizeBattlefield + " ["+keyRow+"] in row("+row.length()+"):" + row);
            }
            for (int i = 0; i < this.x; i++) {
                TypeField type = TypeField.GRASS;
                String typeString = row.substring(i, i + 1);
                if (typeString.trim().equalsIgnoreCase(TOKEN_GRASS)) {
                    type = TypeField.GRASS;
                } else if (typeString.trim().equalsIgnoreCase(TOKEN_ROCK)) {
                    type = TypeField.ROCK;
                } else if (typeString.trim().equalsIgnoreCase(TOKEN_TREE)) {
                    type = TypeField.TREE;
                }
                this.battlefield.getBattelfield()[i][j]=new Field(i, j, 1, type);
            }
        }

       
    }

    public BattelField getBattlefield() {
        return battlefield;
    }
    
    

}
