/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.perfectday.logicengine.model.minis.action;

import org.apache.log4j.Logger;
import org.perfectday.logicengine.combat.model.combatkeep.CombatKeep;
import org.perfectday.logicengine.model.minis.Mini;

/**
 * Es la generalizacion de las acciones que hacen los minis
 * @author Miguel Angel Lopez Montellano ( alakat@gmail.com )
 */
public class ActionMini  {
    
    private Object actionData; //información para realizar las acciones
    private String name;

    public ActionMini(String name) {
        this.name = name;
    }

    public ActionMini(Object actionData, String name) {
        this.actionData = actionData;
        this.name = name;
    }
    

    public Object getActionData() {
        return actionData;
    }

    public void setActionData(Object actionData) {
        this.actionData = actionData;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.getName();
    }
    
    /**
     * Override this method in all actionMini with combatKeep
     * @return
     */
    public CombatKeep getActionKeep(){
        return null;
    }
    
    public void doAction(Mini worker, Mini target){
        Logger.getLogger(ActionMini.class).info("Dummy metod. Si ocurre este metodo es por que existe un error en código y la acción correcta no lo tiene sobre escrito");        
    }
    

}
