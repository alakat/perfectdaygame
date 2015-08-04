/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.perfectday.logicengine.model.command.combat;

import org.perfectday.logicengine.model.command.Command;
import org.perfectday.logicengine.model.minis.Mini;


/**
 * Este comando encapsulta la resolución del combate.
 * @author Miguel Angel Lopez Montellano ( alakat@gmail.com )
 */
public class CombatResolutionCommand extends Command{
    
    private String msg;
    private String oMsg;
    private double damage;
    private boolean critical;
    private boolean dead;
    private boolean luckysupportDefender;
    private Mini atacker;
    private Mini defensor;

    public CombatResolutionCommand() {
    }
    
    
    

    public CombatResolutionCommand(Mini atacker,
            Mini defender, 
            double damage,
            boolean dead,boolean luckySupportTo) {
        
        this.damage = damage;
        this.dead = dead;
        this.luckysupportDefender=luckySupportTo;
        String luck ="";
        if(luckysupportDefender)
            luck = "La suerte favorece la defensa";
        else
            luck= "La suerte favorece el ataque";
        String cad =  luck+" "+atacker.getName()+" set "+((int)(this.damage*100));
        if (this.critical)
            cad+=" ¡¡Critico!!";
        if (this.dead)
            cad+=" defender dead";
         this.msg= cad;
        if( this.dead ){
            this.oMsg = defender.toString()+" muerto";
        }else if (this.critical){
            this.oMsg =  "Ataque critico!!! daño:"+((int)this.damage*100);
        }else {
            this.oMsg =  "Daño: "+((int)this.damage*100);
        }
        this.atacker = new Mini("");
        this.atacker.setId(atacker.getId());
        this.defensor = new Mini("");
        this.defensor.setId(defender.getId());
    }

    public double getDamage() {
        return damage;
    }

    public void setDamage(double damage) {
        this.damage = damage;
    }

    public boolean isDead() {
        return dead;
    }

    public void setDead(boolean dead) {
        this.dead = dead;
    }

    @Override
    public String toString() {
        return this.oMsg;
    }

    @Override
    public String info() {
        return  msg;
    }

    public Mini getAtacker() {
        return atacker;
    }

    public Mini getDefensor() {
        return defensor;
    }
    
    

}
