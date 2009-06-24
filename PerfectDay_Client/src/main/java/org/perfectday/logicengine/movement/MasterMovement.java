/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.perfectday.logicengine.movement;

import java.util.ArrayList;
import java.util.List;
import org.perfectday.logicengine.model.battelfield.BattelField;
import org.perfectday.logicengine.model.battelfield.Field;
import org.perfectday.logicengine.model.minis.Mini;

/**
 *
 * 
 * @author Miguel Angel Lopez Montellano ( alakat@gmail.com )
 */
public class MasterMovement {

    /**
     * fistt search mini´s field
     * @param selectedMini
     * @param battelField
     * @return
     */
    public List<Field> getNearbyField(Mini selectedMini, BattelField battelField) {
        Field field = battelField.getField(selectedMini);
        if (field==null)
            return null;
        return this.getNearbyField(selectedMini, battelField, field);
    }
    
    /**
     * A por ellos no influeye el coste.
     * @param selectedMini
     * @param battelField
     * @return
     */
    public List<Field> getAPorEllosField(Mini selectedMini, BattelField battelField) {
        Field field = battelField.getField(selectedMini);
        if (field==null)
            return null;
        List<Field> fields =new ArrayList<Field>();
        if(battelField.getNorth(field)!=null)
            fields.add(battelField.getNorth(field));
        if(battelField.getSourth(field)!=null)
            fields.add(battelField.getSourth(field));
        if(battelField.getEast(field)!=null)
            fields.add(battelField.getEast(field));
        if(battelField.getWest(field)!=null)
            fields.add(battelField.getWest(field));
        
        return fields;         
    }
    

    private MasterMovement.FieldCost getOtherFieldCost(FieldCost fieldCost,List<FieldCost> list) {
        for(FieldCost fc:list){
            if(fc.equals(fieldCost))
                return fc;                
        }
        //Dumy return
        return null;
    }
    
    class FieldCost{
        private Field field;
        private int cost;

        public FieldCost(Field field, int cost) {
            this.field = field;
            this.cost = cost;
        }

        public int getCost() {
            return cost;
        }

        public void setCost(int cost) {
            this.cost = cost;
        }

        public Field getField() {
            return field;
        }

        public void setField(Field field) {
            this.field = field;
        }

        @Override
        public boolean equals(Object obj) {
            if(obj instanceof FieldCost){
                FieldCost fc = (MasterMovement.FieldCost) obj;
                return fc.getField().equals(this.getField());
            }
            return false;
        }
        
        
    }
    
    private static MasterMovement instance;
    
    private MasterMovement(){
        
    }
    
    public static MasterMovement getInstance(){
        if (instance==null)
            instance = new MasterMovement();
        return instance;
    }
    
    
    
    public List<Field> getNearbyField( Mini mini, BattelField battelField, Field fieldOcupped){
        List<Field> fields =new ArrayList<Field>();
        List<FieldCost> fieldsSearch = new ArrayList<FieldCost>();
        int movementSpend=0;
        componerViaje(movementSpend, fieldsSearch, mini, battelField, fieldOcupped);       
        for(FieldCost fc:fieldsSearch)
            fields.add(fc.getField());
        if(fields.contains(fieldOcupped))
            fields.remove(fieldOcupped);
        return fields;        
    }

    private void componerViaje(int movementSpend, List<FieldCost> fields, Mini mini, BattelField battelField, Field fieldOcupped) {
        if ((battelField.getNorth(fieldOcupped)!=null ) && battelField.getNorth(fieldOcupped).isOccupiblement()) {            
            viajarToField(movementSpend +  battelField.getNorth(fieldOcupped).getPassCost(), battelField, mini, fields, battelField.getNorth(fieldOcupped));
        }
        if ((battelField.getSourth(fieldOcupped)!=null ) && battelField.getSourth(fieldOcupped).isOccupiblement()) {
            viajarToField(movementSpend + battelField.getSourth(fieldOcupped).getPassCost(), battelField, mini, fields, battelField.getSourth(fieldOcupped));
        }
        if ((battelField.getEast(fieldOcupped)!=null ) && battelField.getEast(fieldOcupped).isOccupiblement()) {
            viajarToField(movementSpend + battelField.getEast(fieldOcupped).getPassCost(), battelField, mini, fields, battelField.getEast(fieldOcupped));
        }
        if ((battelField.getWest(fieldOcupped)!=null ) && battelField.getWest(fieldOcupped).isOccupiblement()) {
            viajarToField(movementSpend + battelField.getWest(fieldOcupped).getPassCost(), battelField, mini, fields, battelField.getWest(fieldOcupped));
        }
    }

    private void viajarToField(int movementSpend, BattelField battelField, Mini mini, List<FieldCost> fields, Field newField) {
        FieldCost fc = new FieldCost(newField, movementSpend);
        if(mini.getMoviment()>=movementSpend){
            if(!fields.contains(fc)){
                fields.add(fc);
                componerViaje(movementSpend, fields, mini, battelField, newField);
            } else{
                FieldCost otherFC =getOtherFieldCost(fc,fields);
                if(otherFC.getCost()>fc.getCost()){
                    fields.add(fc);
                    componerViaje(movementSpend, fields, mini, battelField, newField);
                }else{
                    fields.add(otherFC);
                }
            }
           
            
        }
            
    }
    
    

}
