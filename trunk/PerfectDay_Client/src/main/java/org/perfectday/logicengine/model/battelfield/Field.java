/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.perfectday.logicengine.model.battelfield;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.perfectday.logicengine.model.ReferenceObject;
import org.perfectday.logicengine.model.minis.Mini;
import org.perfectday.logicengine.model.minis.support.Support;

/**
 *
 * @author Miguel Angel Lopez Montellano ( alakat@gmail.com )
 */
public class Field extends ReferenceObject{

    private int x;
    private int y;
    private double z;
    /**
     * support register in that field
     */
    private List<Support> supports;
    /**
     * Mini in this field
     */
    private Mini miniOcupant;
    
    /**
     * type of field
     */
    private TypeField typeField;
    /**
     * 
     * @param x
     * @param y
     * @param z
     */
    public Field(int x, int y, double z, TypeField typeField) {        
        this.x = x;
        this.y = y;
        this.z = z;
        this.typeField = typeField;
        supports = new ArrayList<Support>();
    }
    
    /**
     * 
     * @param x
     * @param y
     * @param z
     */
    public Field(int x, int y, double z, TypeField typeField,long id) {
        
        this.x = x;
        this.y = y;
        this.z = z;
        this.typeField = typeField;
        supports = new ArrayList<Support>();
        this.setId(id);
    }

    private Field(long id) {
        super(id);
    }

    public double getDistance(Field field) {
        double x = this.x-field.getX();
        double y = this.y-field.getY();
        x = x*x;
        y = y*y;
        return Math.sqrt((x+y));
        
    }

    public int getPassCost() {
       switch(this.typeField){
           case GRASS:return 1;
           case ROCK:return 3;
           case TREE:return 1;
           default:return 1;
       }
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
    }
    
    public void addSupport(Support s){
        this.supports.add(s);
    }
    
    public Support getSupport(int i){
        return this.supports.get(i);
    }
    
    public Iterator<Support> getIterator(){
        return this.supports.iterator();
    }

    public Mini getMiniOcupant() {
        return miniOcupant;
    }

    public TypeField getTypeField() {
        return typeField;
    }

    public void setMiniOcupant(Mini miniOcupant) {
        this.miniOcupant = miniOcupant;
    }
    
    /**
     * return true if this field can be occuped by any mini
     * @return
     */
    public boolean isOccupiblement(){
       
        return this.getMiniOcupant()==null;
    }
    
    /**
     * return true if this terrain block to see line
     * @return
     */
    public boolean isBlock(){
         //return this.typeField==TypeField.ROCK;
        return false;
    }

    public List<Support> getSupports() {
        return supports;
    }

    public void setTypeField(TypeField typeField) {
        this.typeField = typeField;
    }
    
    

    @Override
    public String toString() {
        return ""+this.getTypeField()+" ["+this.x+","+this.y+","+this.z+"]";
    }
    
    /**
     * Generion de la mini expresión de un Field para la comunicación
     * @return
     */
    public Field generateSimpleField(){
        return new Field(this.getId());
    }
}
