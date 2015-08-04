package org.perfectday.logicengine.model.activationstack.accidents;

import java.util.List;
import org.perfectday.logicengine.core.Game;
import org.perfectday.logicengine.model.command.Command;
import org.perfectday.logicengine.model.unittime.UnitTime;


/**
 * 
 * @author Miguel
 */
public class Action extends Accident{    
    public Action (UnitTime ut) {
        super(ut);
    }

    @Override
    public void doAccident(List<Command> commands, Game game) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void doAccidentWithEvent(Game game) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    
}

