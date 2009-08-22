/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.perfectday.logicengine.model.battelfield.generator.simple;

import org.perfectday.logicengine.model.battelfield.generator.*;
import java.util.Random;

import org.perfectday.logicengine.model.battelfield.BattelField;
import org.perfectday.logicengine.model.battelfield.Field;
import org.perfectday.logicengine.model.battelfield.TypeField;


/**
 * Modo de generación simple
 * @author Miguel Angel Lopez Montellano ( alakat@gmail.com )
 */
public class SimpleMapGenerator extends MapGenerator {
    public static final double CHANGE_TYPE_PROBABILITY = 0.001;
    public static final int MAX_HEIGHT = 3;
    private Field[][] battelfield;
    
    public SimpleMapGenerator(int xlen, int ylen,BattelField bf) {
        super(xlen, ylen);
        this.battelfield=bf.getBattelfield();
    }
    
    protected boolean equalNeighbor(){
        double roll = rand.nextDouble();
        return roll<CHANGE_TYPE_PROBABILITY;
    }
    
    protected TypeField getNewTypwField(){        
        int steps = TypeField.values().length;
        int roll = rand.nextInt(steps);
        return TypeField.values()[roll];
    }
    
    protected double calculateHeight(){
        return this.rand.nextDouble()*MAX_HEIGHT;
    }
    
    public void generateFieldAndNeihbor(int x,int y, TypeField tf){
        TypeField thisTypeField =tf;
        if(equalNeighbor()){
            this.battelfield[x][y] = new Field(x, y, calculateHeight(), tf);
        }else{
            thisTypeField = getNewTypwField();
            this.battelfield[x][y] = new Field(x, y, calculateHeight(),
                    thisTypeField);
        }
        if ((x>0) && GenerateYet(x-1,y) ){
            generateFieldAndNeihbor(x-1, y, thisTypeField);
        }
        if ((x<(this.xlen-1)) && GenerateYet(x+1,y)){
            generateFieldAndNeihbor(x+1, y, thisTypeField);
        }
        if( (y>0) && GenerateYet(x,y-1) ){
            generateFieldAndNeihbor(x, y-1, thisTypeField);
        }
        if( (y<(this.ylen-1)) && GenerateYet(x,y+1)){
            generateFieldAndNeihbor(x, y+1, thisTypeField);
        }
    }
    
    public void generateBattelField(){
        int x = this.xlen/2;
        int y = this.ylen/2;
        TypeField tf = getNewTypwField();
        
        generateFieldAndNeihbor(x, y, TypeField.GRASS);
        SimpleBattelFieldVerifier battelFieldVerifier =
                new SimpleBattelFieldVerifier(battelfield);
        battelFieldVerifier.verifier();
    }

    public Field[][] getBattelfield() {
        this.generateBattelField();
        return battelfield;
    }

    public void setBattelfield(Field[][] battelfield) {
        this.battelfield = battelfield;
    }

    public Random getRand() {
        return rand;
    }

    public void setRand(Random rand) {
        this.rand = rand;
    }

    public int getXlen() {
        return xlen;
    }

    public void setXlen(int xlen) {
        this.xlen = xlen;
    }

    public int getYlen() {
        return ylen;
    }

    public void setYlen(int ylen) {
        this.ylen = ylen;
    }

    private boolean GenerateYet(int i, int j) {
        return this.battelfield[i][j]==null;
    }
    
}
