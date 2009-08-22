/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.perfectday.logicengine.model.battelfield.generator;

import java.util.Random;
import org.perfectday.logicengine.model.battelfield.Field;

/**
 * Generador de Mapas abtracto
 * @author Miguel Angel Lopez Montellano (alakat@gmail.com)
 */
public abstract class MapGenerator {

    protected  Random rand;
    protected int xlen;
    protected int ylen;
    protected MapGenerator(int xlen, int ylen) {
        this.xlen = xlen;
        this.ylen = ylen;
        this.rand = new Random(System.currentTimeMillis());
    }

    /**
     * Este método provoca la generación del mapa por parte del generador
     * y posteriormente es usado por BattleField para obtener el campo de
     * batalla
     * @return
     */
    public abstract Field[][] getBattelfield();

}
