/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.perfectday.logicengine.model.state.commands;

import org.perfectday.logicengine.model.minis.Mini;
import org.perfectday.logicengine.model.state.State;

/**
 *
 * @author Miguel Angel Lopez Montellano ( alakat@gmail.com )
 */
public class RegisterStateCommand extends StateCommand{

    public RegisterStateCommand() {
    }

    
    
    public RegisterStateCommand(State state, Mini mini) {
        super(state, mini);
    }

    @Override
    public String info() {
        return "Set state: "+super.info();
    }

    
}
