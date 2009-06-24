/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.perfectday.logicengine.model.minis.support;

import org.perfectday.logicengine.model.minis.support.modifiers.Modifier;
import java.util.List;
import org.perfectday.logicengine.combat.InstanceCombat;
import org.perfectday.logicengine.combat.model.combatkeep.CombatKeep;
import org.perfectday.logicengine.model.command.Command;
import org.perfectday.logicengine.model.minis.Mini;

/**
 *
 * @author Miguel Angel Lopez Montellano ( alakat@gmail.com )
 */
public abstract class Support implements Comparable{
    /**
     * Support type
     */
    private SupportType supportType;
    /**
     * Genera la lista de campos que están apollados.
     */
    private CombatKeep supportKeep;  
    /**
     * indica si origina propagaciones
     */
    private boolean spread;
    /**
     * Datos necesarios por el support
     */
    private Object supportData;
    /**
     * Mini que origina el apoyo
     */
    private Mini apoyador;
    /**
     * Descripción del apoyo
     * 
     */
    private String description;
    
    public String infoSupport(){
        return description;
    }

    public Support() {
    }
    
    

    public Support(SupportType supportType,            
            CombatKeep supportKeep,
            boolean spread,            
            Mini apoyador,
            String description) {
        this.supportType = supportType;        
        this.supportKeep = supportKeep;
        this.spread = spread;   
        this.apoyador = apoyador;
        this.description=description;
    }

    public Mini getApoyador() {
        return apoyador;
    }

    public void setApoyador(Mini apoyador) {
        this.apoyador = apoyador;
    }

    public Object getSupportData() {
        return supportData;
    }

    public void setSupportData(Object supportData) {
        this.supportData = supportData;
    }

    public boolean isSpread() {
        return spread;
    }

    public void setSpread(boolean spread) {
        this.spread = spread;
    }

    public SupportType getSupportType() {
        return supportType;
    }

    public void setSupportType(SupportType supportType) {
        this.supportType = supportType;
    }

   /**
    * compare Support with other 
    * @param o
    * @return
    */
    public int compareTo(Object o) {
        if( o instanceof Support){
            Support s = (Support)o;
            SupportType type = this.getSupportType();
            SupportType oType = s.getSupportType();
            switch(type){
                case ACTIVE_DEFENSIVE: return oType==SupportType.ACTIVE_DEFENSIVE? 0 : 1;
                case ACTIVE_OFENSIVE: 
                    if(oType == SupportType.ACTIVE_DEFENSIVE)
                        return -1;
                    else if(oType == SupportType.ACTIVE_OFENSIVE)
                        return 0;
                    else 
                        return 1;
                case PASIVE_DEFENSIVE:
                    if(oType == SupportType.ACTIVE_DEFENSIVE || oType==supportType.ACTIVE_OFENSIVE)
                        return -1;
                    else if(oType == SupportType.PASIVE_DEFENSIVE)
                        return 0;
                    else 
                        return 1;
                case PASIVE_OFENSIVE: return oType==SupportType.PASIVE_OFENSIVE? 0: -1;
            }
            
        }
        return 0;
    }
    
    
    /**
     * realiza la acción
     * @return devuelve la lista de  commandos que códifican lo realizado en 
     */
    public abstract List<Command> doSupport(InstanceCombat instanceCombat);
    
    /**
     * Get Modifiers by support
     * @return
     */
    public abstract List<Modifier> getModifierBySupport();

    public CombatKeep getSupportKeep() {
        return supportKeep;
    }

    public void setSupportKeep(CombatKeep supportKeep) {
        this.supportKeep = supportKeep;
    }

    @Override
    public String toString() {
        return ""+this.supportType.toString()+"";
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    
    
    
}
