/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.bitonfire.perfectday.battlefield.generator.sector.model;

import java.util.Iterator;
import java.util.Set;
import org.perfectday.logicengine.model.battelfield.generator.Terrain;

/**
 * Esta clase codifica la asociaciones:
 *  terreno prioridad
 *  obstaculos prioridad
 *

 * @author Miguel Angel Lopez Montellano (alakat@gmail.com)
 */
public class PriorityTerrain {

    /**
     * Obtiene el Terrain de acuerdo al conjunto de propiedades y al valor 
     * de azar dado
     * @param obstacules conjunto de elementos priorizado
     * @param nextDouble número al azar
     * @return
     */
    public static Terrain getTerrain(Set<PriorityTerrain> obstacules, double nextDouble) {

        double p=0;
        Iterator<PriorityTerrain> i = obstacules.iterator();
        while (i.hasNext()) {
            PriorityTerrain priorityTerrain = i.next();
            p+=priorityTerrain.getPriority();
            if(nextDouble<=p)
                return priorityTerrain.getTerrain();
        }
        return Terrain.NULL_TERRAIN;
    }

    static Terrain getMaxProprityTerrain(Set<PriorityTerrain> terrainType) {
        double p=0;
        Terrain t =Terrain.NULL_TERRAIN;
        Iterator<PriorityTerrain> i = terrainType.iterator();
        while (i.hasNext()) {
            PriorityTerrain priorityTerrain = i.next();
            if(priorityTerrain.getPriority()>p){
                t= priorityTerrain.getTerrain();
                p = priorityTerrain.getPriority();
            }
        }
        return t;

    }

    private Terrain terrain;
    private double priority;

    public PriorityTerrain(Terrain terrain, double priority) {
        this.terrain = terrain;
        this.priority = priority;
    }

    public double getPriority() {
        return priority;
    }

    public Terrain getTerrain() {
        return terrain;
    }
}
