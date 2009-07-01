/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.perfectday.logicengine.brain.simple;

import java.util.ArrayList;
import java.util.List;
import org.perfectday.logicengine.brain.MovedFunction;
import org.perfectday.logicengine.core.Game;
import org.perfectday.logicengine.core.player.Player;
import org.perfectday.logicengine.model.battelfield.Field;
import org.perfectday.logicengine.model.minis.Mini;

/**
 *
 * @author Miguel Angel Lopez Montellano ( alakat@gmail.com )
 */
public class SimpleMovedFunction implements MovedFunction{

    private List<Mini> enemies;
    private List<Field> enemyPosition;

    public SimpleMovedFunction() {
        enemies = new ArrayList<Mini>();
        enemyPosition = new ArrayList<Field>();
    }
   
    
    
    /**
     * Devuelve la calidad de field para el movimiento actual.
     * @param field de destino
     * @param mini
     * @return
     */
    public double getCualityOfField(Field field, Mini mini) {
        if( !(enemies!=null &&
                !enemies.isEmpty())){
            //1ºObtenemos el jugador del mini
            //2ºObtenemos el listado de minis de la banda enemiga
            configure(mini);
            
        }        
        //3ºObtenemos cual esta mas derca de field
        double[] distancias = new double[enemyPosition.size()];
        int i=0;
        for(Field f: enemyPosition){
            distancias[i]=f.getDistance(field);
            i++;
        }
        //4ºDevolvemos la menor distancia a field
        double d = Double.MAX_VALUE;        
        for (int j = 0; j < distancias.length; j++) {
            double e = distancias[j];
            if(e<d){                
                d = e;
            }
        }
        return d;
    }

    private void configure(Mini mini) {
        Game game = Game.getGame();
        Player p = game.getPlayerByMini(mini);
        Player pe =null;
        for(Player player: game.getPlayers()){
            if (!player.equals(p) && !player.isObserver()){
                pe = player;
                break;
            }
        }
        this.enemies.addAll(pe.getBand());
        for (Mini mini1 : enemies) {
            this.enemyPosition.add(game.getBattelField().getField(mini1));
        }

    }

    public void setEnemies(List<Mini> enemies) {
        this.enemies = enemies;
    }

    public void setEnemyPosition(List<Field> enemyPosition) {
        this.enemyPosition = enemyPosition;
    }

    public void initFunction() {
        this.enemies.clear();
        this.enemyPosition.clear();
        
    }
    
    

}
