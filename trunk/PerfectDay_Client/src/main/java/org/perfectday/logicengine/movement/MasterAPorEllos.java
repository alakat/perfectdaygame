/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.perfectday.logicengine.movement;

import org.perfectday.logicengine.model.ReferenceObject;

/**
 * Controla en el desarollo de un turno como funciona "A Por Ellos" para un mini
 * @author Miguel Angel Lopez Montellano ( alakat@gmail.com )
 */
public class MasterAPorEllos {

    public static final int MAX_NUMBER_OF_A_POR_ELLOS = 3;    
    public static final double INITIAL_A_POR_ELLOS_PROBABILITY=0.9;
    private ReferenceObject mini;
    private int count;
    private double probability;
    private double lastProbability;
    public MasterAPorEllos(ReferenceObject mini) {
        this.mini = mini;
        this.count =0;
        this.probability = INITIAL_A_POR_ELLOS_PROBABILITY;
        lastProbability = 1;
    }
    
    public double getNewProbability(){
        double ax = 0.5*lastProbability*probability;
        this.lastProbability = ax;
        return ax;
    }
    
}
