/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.perfectday.logicengine.combat.model.combatkeep;

import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.perfectday.logicengine.core.Game;
import org.perfectday.logicengine.model.battelfield.BattelField;
import org.perfectday.logicengine.model.battelfield.Field;

/**
 *
 * @author Miguel Angel Lopez Montellano ( alakat@gmail.com )
 */
public class BowBasicCombatKeep extends CombatKeep{

    private int xMin;
    private int yMin;
    private int xMax;
    private int yMax;
    public BowBasicCombatKeep() {
        this.xMin=1;
        this.yMin=1;
        this.xMax=3;
        this.yMax=3;        
    }

    public BowBasicCombatKeep(int xMin, int yMin, int xMax, int yMax) {
        this.xMin = xMin;
        this.yMin = yMin;
        this.xMax = xMax;
        this.yMax = yMax;
    }
    

    @Override
    public boolean isDefenderKeeped(Field defender, Field ataker) {
        List<Field> fields=this.getFieldKeeped(ataker);
        boolean contains = fields.contains(defender);
        fields.clear();
        return contains;
    }

    @Override
    public List<Field> getFieldKeeped(Field ataker) {
        BattelField battelField = Game.getInstance().getBattelField();
        List<Field> fields = new ArrayList<Field>();        
        fields.addAll(battelField.getNeighboures(ataker));
        int dMax = Math.max(this.xMax, this.yMax);
        List<Field> ax=new ArrayList<Field>();
        //Add to max range
        for(int i=1;i<dMax;i++){
            ax.clear();
            for(Field f:fields){
                List<Field> fNeighbour = battelField.getNeighboures(f);                
                for(Field fn: fNeighbour){
                    if((!ataker.equals(fn))){
                        ax.add(fn);
                    }
                }                
            }
            for(Field fn:ax){
                if(!fields.contains(fn))
                    fields.add(fn);
            }
        }
        Logger.getLogger(BowBasicCombatKeep.class).info("Fields.size"+fields.size());
        //Eliminamos los que no esten acordes con la distancia
        ax.clear();
        for(Field field:fields){
            if(!isInMinRange(field,ataker)){
                ax.add(field);
            }
        }
        for (Field field : ax) {
            fields.remove(field);
        }
        ax.clear();
        ax = null;
        return fields;
  
    }

    private boolean isInMinRange(Field field,Field org) {
        //System.out.println("In Range:"+field);
        int xF=field.getX();
        int yF=field.getY();        
        xF = Math.abs(xF-org.getX());
        yF = Math.abs(yF-org.getY());
        
        boolean min =  (xF>this.xMin) || (yF>this.yMin); 
        //System.out.println("xF:"+xF+"yF:"+yF+"-->"+min);
        return min;
    }

    

}

