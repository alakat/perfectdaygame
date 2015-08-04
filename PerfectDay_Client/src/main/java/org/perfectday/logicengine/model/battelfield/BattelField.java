/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.perfectday.logicengine.model.battelfield;

import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.perfectday.logicengine.core.Game;
import org.perfectday.logicengine.model.battelfield.generator.MapGenerator;
import org.perfectday.logicengine.model.battelfield.generator.simple.SimpleMapGenerator;
import org.perfectday.logicengine.model.minis.Mini;
import org.perfectday.logicengine.model.minis.support.Support;

/**
 *
 * Basic battelfield representation
 * @author Miguel Angel Lopez Montellano ( alakat@gmail.com )
 */
public class BattelField {
    private transient static final Logger logger = Logger.getLogger(BattelField.class);
    private Field [][]battelfield;
    private int higth;
    private int weidth;

    public BattelField() {
    }

    
    
    public BattelField(int higth, int weidth) {
        this.higth = higth;
        this.weidth = weidth;
        this.battelfield = new Field[this.higth][this.weidth];
        logger.info("["+higth+","+weidth+"]("+battelfield.length+","+battelfield[0].length+")");
        
    }

    /**
     * Add support to field that it is within reach of support
     * @param selectedMini
     */
    public void applySupport(Mini selectedMini,Support support) {
        Field f = this.getField(selectedMini);
        
        if(f!=null){//If mini is death
            List<Field> list=support.getSupportKeep().getFieldKeeped(f,Game.getGame());
            for (Field field : list) {
                logger.info("Apply Support["+support.toString()+"] en"+field.toString());
                field.getSupports().add(support);
            }
        }
    }

    /**
     * Get field that is occupant by mini
     * @param selectedMini
     * @return
     */
    public Field getField(Mini selectedMini) {
        for(int i=0;i<battelfield.length;i++){
            for(int j=0;j<battelfield[i].length;j++){
                if(battelfield[i][j].getMiniOcupant()!=null
                        && battelfield[i][j].getMiniOcupant().equals(selectedMini))
                    return battelfield[i][j];
            }
        }
        return null;
    }

    public List<Field> getNeighboures(Field fx) {
        List<Field> fields = new ArrayList<Field>();
        Field n = this.getEast(fx);
        if(n!=null)
            fields.add(n);
        n = this.getWest(fx);
        if(n!=null)
            fields.add(n);
        n = this.getNorth(fx);
        if(n!=null)
            fields.add(n);
        n = this.getSourth(fx);
        if(n!=null)
            fields.add(n);
        return fields;
    }
    
    /**
     * North--> positivo en el eje x
     * @param f
     * @return
     */
    public Field getNorth(Field f){
        int x=f.getX();
        int y=f.getY();
        if((x+1)<higth)
            return this.battelfield[x+1][y];
        else 
            return null;
    }
    
    /**
     * Shouth--> negativo en el eje x
     * @param f
     * @return
     */
    public Field getSourth(Field f){
        int x=f.getX();
        int y=f.getY();
        if((x-1)>=0)
            return this.battelfield[x-1][y];
        else 
            return null;
    }
    
    /**
     * West--> positivo en el eje y
     * @param f
     * @return
     */
    public Field getWest(Field f){
        int x=f.getX();
        int y=f.getY();        
        if((y+1)<weidth)
            return this.battelfield[x][y+1];
        else 
            return null;
    }
    
    /**
     * West--> positivo en el eje y
     * @param f
     * @return
     */
    public Field getEast(Field f){
        int x=f.getX();
        int y=f.getY();
        if((y-1)>=0)
            return this.battelfield[x][y-1];
        else 
            return null;
    }

    public Field[][] getBattelfield() {
        return battelfield;
    }

    /**
     *  Remove selectedMini's support 
     * @param selectedMini
     */
    public void removeSupport(Mini selectedMini,Support support) {
        Field f = this.getField(selectedMini);
        List<Field> list=support.getSupportKeep().getFieldKeeped(f,Game.getGame());
        for (Field field : list) {
            logger.info("Remove Support["+support.toString()+"] en"+field.toString());
            field.getSupports().remove(support);
        }

    }

    public void setBattelfield(Field[][] battelfield) {
        this.battelfield = battelfield;
    }

    public int getHigth() {
        return higth;
    }

    public void setHigth(int higth) {
        this.higth = higth;
    }

    public int getWeidth() {
        return weidth;
    }

    public void setWeidth(int weidth) {
        this.weidth = weidth;
    }
    
    public void generateBattelField(MapGenerator simpleMapGenerator){
        
        this.battelfield = simpleMapGenerator.getBattelfield();
    }

    
    
    
}
