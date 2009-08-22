/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.bitonfire.perfectday.battlefield.generator.sector.model;

import java.util.Iterator;
import java.util.Random;
import java.util.Set;
import org.perfectday.logicengine.model.battelfield.generator.Terrain;

/**
 * Un sector de la definición del mapa
 * @author Miguel Angel Lopez Montellano (alakat@gmail.com)
 */
public class Sector {

    protected Terrain[][] terrain;
    protected int xlen;
    protected int ylen;

    public Sector(int xlen, int ylen) {
        this.xlen = xlen;
        this.ylen = ylen;
        this.terrain = new Terrain[this.xlen][this.ylen];
        for (int i = 0; i < terrain.length; i++) {
            Terrain[] terrains = terrain[i];
            for (int j = 0; j < terrains.length; j++) {
                terrains[j] = Terrain.NULL_TERRAIN;
            }
        }
    }

    /**
     * Asigna los valores del terreno de este Sector a la situación que le
     * corresponden en el mapa
     * @param mapResult
     * @param x
     * @param y
     */
    public void setTerrains(Terrain[][] mapResult, int x, int y) {
        try {
            for (int i = 0; i < xlen; i++) {
                for (int j = 0; j < ylen; j++) {
                    mapResult[i + x][j + y] = this.terrain[i][j];
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error:" + x + "," + y + " ::: " + e.getMessage());
        }
    }

    @Override
    public String toString() {
        StringBuffer db = new StringBuffer();

        for (int i = 0; i < xlen; i++) {
            for (int j = 0; j < ylen; j++) {
                db.append("x\t");
            }
        }
        return db.toString();

    }

    /**
     * Paint a map
     * @param map
     * @param i_
     * @param j_
     */
    public void paint(String[][] map, int i_, int j_) {
        try {
            for (int i = 0; i < xlen; i++) {
                for (int j = 0; j < ylen; j++) {
                    if (this.terrain[i][j] != null) {
                        map[i + i_][j + j_] = this.terrain[i][j].toTag();
                    } else {
                        map[i + i_][j + j_] = "X";
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error:" + i_ + "," + j_ + " ::: " + e.getMessage());
        }
    }

    /**
     * Distribuye los obstaculos pro la pantalla
     * @param obstacules
     * @param df
     */
    public void distributeObstacules(Set<PriorityTerrain> obstacules, DistributionFunction df) {
        final Random r = new Random(System.currentTimeMillis());
        int[][] distribution = df.generateDistribution();
        for (int i = 0; i < this.xlen; i++) {
            for (int j = 0; j < this.ylen; j++) {
                if (distribution[i][j] > 0) {
                    this.terrain[i][j] = this.terrain[i][j].or(PriorityTerrain.getTerrain(obstacules, r.nextDouble()));
                }
            }
        }
    }

    /**
     * Distribuye el tipo de terreno en función de la función de distribución
     * @param terrainType
     * @param df
     */
    public void distributeTerrainType(Set<PriorityTerrain> terrainType, DistributionFunction df) {
        final Random r = new Random(System.currentTimeMillis());
        int[][] distribution = df.generateDistribution();
        for (int i = 0; i < this.xlen; i++) {
            for (int j = 0; j < this.ylen; j++) {
                Terrain t;
                if (distribution[i][j] == 0) {
                    t = PriorityTerrain.getTerrain(terrainType, r.nextDouble());
                } else {
                    t = PriorityTerrain.getMaxProprityTerrain(terrainType);
                }
                this.terrain[i][j] = this.terrain[i][j].or(t);
            }
        }
    }
}
