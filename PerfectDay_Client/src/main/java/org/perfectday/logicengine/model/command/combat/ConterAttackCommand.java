/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.perfectday.logicengine.model.command.combat;

import org.perfectday.logicengine.model.command.Command;

/**
 *
 * @author Miguel Angel Lopez Montellano ( alakat@gmail.com )
 */
public class ConterAttackCommand extends Command{

    public ConterAttackCommand() {
    }

    
    
    @Override
    public String info() {
        return "Conter Atack!!";
    }

}
