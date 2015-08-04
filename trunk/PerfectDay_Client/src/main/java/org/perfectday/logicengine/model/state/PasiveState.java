/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.perfectday.logicengine.model.state;

import java.util.List;
import org.perfectday.logicengine.model.command.Command;
import org.perfectday.logicengine.model.minis.Mini;
import org.perfectday.logicengine.model.unittime.UnitTime;



/**
 * Los estado pasivos son los que no realizan nada, solo modifican los atributos
 * del mini en el que estan registrados
 * @author Miguel Angel Lopez Montellano ( alakat@gmail.com )
 */
public class PasiveState  extends State{
    
    
    private MiniAttribute attribute;
    private Number modifier;

    public PasiveState() {
    }
    
    

    public PasiveState( MiniAttribute attribute, Number modifier,String name,Mini mini,UnitTime ut) {    
        super(name,mini,ut);
        this.attribute = attribute;
        this.modifier = modifier;
    }

    public PasiveState(String name, UnitTime timeEffect, MiniAttribute attribute, Number modifier) {
        super(name, timeEffect);
        this.attribute = attribute;
        this.modifier = modifier;
    }

    
    /**
     * Recibe como parametro el valor en unNumber del attributo a modificar.
     * @param data
     */
    @Override
    public Object doState(Object data,List<Command> commands) {
        if (data instanceof Number) {
            if (data instanceof Integer) {
                
                data = new Integer(((Integer)data).intValue()+this.modifier.intValue());                
                noZeroLess((Number)data);
            }else if(data instanceof Double) {
                
                data = new Double(((Double)data).doubleValue()+this.modifier.doubleValue());                
                noZeroLess((Number)data);
            }else if(data instanceof Long) {
               
                data = new Long(((Long)data).longValue()+this.modifier.longValue());                
                noZeroLess((Number)data);
            }
            if(commands!=null)
                commands.add(generateActionCommand());
        }
        return data;
        
    }

    public MiniAttribute getAttribute() {
        return attribute;
    }

    public void setAttribute(MiniAttribute attribute) {
        this.attribute = attribute;
    }

    public Number getModifier() {
        return modifier;
    }

    public void setModifier(Number modifier) {
        this.modifier = modifier;
    }

    public String generateStringEffect() {
        return ""+this.modifier+" in "+this.getAttribute().toString();
    }

    @Override
    public Object generateDataEffect() {
        return null;
    }

    private void noZeroLess(Number number) {
        if (number instanceof Integer) {                              
                if(number.intValue()<0)
                    number = new Integer(0);
            }else if(number instanceof Double) {
                
                if(number.doubleValue()<0.0)
                    number=new Double(0.0);                
            }else if(number instanceof Long) {
                
                if(number.longValue()<0l)
                    number= new Long(0l);
            }
    }

  
    
    
    
}
