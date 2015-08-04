/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.perfectday.logicengine.model.command.combat;

import org.perfectday.logicengine.model.ReferenceObject;
import org.perfectday.logicengine.model.command.Command;

/**
 *  Este commando códifica el cambio en el daño que un mini pose.
 * @author Miguel Angel Lopez Montellano (alakat@gmail.com)
 */
public class SetDamageCommand extends Command {
    
    private ReferenceObject mini;
    private double damage;

    public SetDamageCommand() {
    }

    
    public SetDamageCommand(ReferenceObject mini, double damage) {
        this.mini = mini;
        this.damage = damage;
    }

    public double getDamage() {
        return damage;
    }

    public void setDamage(double damage) {
        this.damage = damage;
    }

    public ReferenceObject getMini() {
        return mini;
    }

    public void setMini(ReferenceObject mini) {
        this.mini = mini;
    }

    @Override
    public String info() {
        return "Daño "+damage;
    }
    
    

}
