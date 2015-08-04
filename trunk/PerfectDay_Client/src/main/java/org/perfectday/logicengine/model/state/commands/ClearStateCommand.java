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
public class ClearStateCommand extends StateCommand {

    public ClearStateCommand() {
    }
    
    

    public ClearStateCommand(State state, Mini mini) {
        super(state, mini);
    }

    @Override
    public String info() {
        return "Remove ;"+super.info();
    }

    
}
