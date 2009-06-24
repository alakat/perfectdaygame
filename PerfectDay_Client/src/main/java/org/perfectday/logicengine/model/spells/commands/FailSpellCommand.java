/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.perfectday.logicengine.model.spells.commands;

/**
 * Commando que indica que un conjuro ha fallado.
 * @author Miguel Angel Lopez Montellano (alakat@gmail.com)
 */
public class FailSpellCommand extends SpellCommand{
    private String failDescription;

    public FailSpellCommand(String failDescription) {
        this.failDescription = failDescription;
    }

    @Override
    public String info() {
        return failDescription;
    }
    
    
    
    
}
