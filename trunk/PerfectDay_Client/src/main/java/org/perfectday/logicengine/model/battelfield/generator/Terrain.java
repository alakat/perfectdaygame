/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.perfectday.logicengine.model.battelfield.generator;

import java.util.Hashtable;

/**
 *
 * @author Miguel Angel Lopez Montellano (alakat@gmail.com)
 */
public class Terrain {
    public static final Terrain TYPE_MASK = new  Terrain(0X00FF);
    public static final Terrain OBSTACULE_MASK = new  Terrain(0XFF00);
    public static final Terrain GRASS = new  Terrain(0x0001);
    public static final Terrain PLAIN = new  Terrain(0X0002);
    public static final Terrain ROAD = new  Terrain(0X0004);
    public static final Terrain MONTAIN = new  Terrain(0X0008);
    public static final Terrain COBBLED = new  Terrain(0X0009);
    public static final Terrain WOOD_HOUSE = new  Terrain(0X0100);
    public static final Terrain STONE_HOUSE = new  Terrain(0X0200);
    public static final Terrain ROCK  = new  Terrain(0X0400);
    public static final Terrain TREE  = new  Terrain(0X8000);
    public static final Terrain NULL_TERRAIN = new  Terrain(0X0000);
    private final int value;

    private Terrain(int value) {
        this.value = value;
    }

    public Terrain or(Terrain t){
        return new Terrain(this.getValue()|t.getValue());
    }

    public Terrain and(Terrain t){
        return new Terrain(this.getValue()&t.getValue());
    }

    public boolean isAnd(Terrain t){
        return (this.getValue()&t.getValue())>0;
    }
    public boolean isOr(Terrain t){
        return (this.getValue()|t.getValue())>0;
    }

    public int getValue() {
        return value;
    }


    protected Hashtable getIntTable() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    protected Hashtable getStringTable() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    
    public String toTag() {
        String s  = "";
        if(this.getValue() == this.NULL_TERRAIN.getValue()){
            s+="N";
        }
        if(this.isAnd(Terrain.COBBLED))
            s+="C";
        else if(this.isAnd(Terrain.GRASS))
            s+="G";
        else if(this.isAnd(Terrain.MONTAIN))
            s+="M";
        else if(this.isAnd(Terrain.PLAIN))
            s+="P";
        else if(this.isAnd(Terrain.ROAD))
            s+="R";
        if(this.and(Terrain.OBSTACULE_MASK).isAnd(Terrain.ROCK))
            s+="[K]";
        else if(this.and(Terrain.OBSTACULE_MASK).isAnd(Terrain.STONE_HOUSE))
            s+="[SH]";
        else if(this.and(Terrain.OBSTACULE_MASK).isAnd(Terrain.WOOD_HOUSE))
            s+="[WH]";
        else if(this.and(Terrain.OBSTACULE_MASK).isAnd(Terrain.TREE))
            s+="[T]";

        return s;
    }

    /**
     * devuelve true si el valor es cero
     * @return
     */
    public boolean isNull(){
        return this.value==0;
    }




}
