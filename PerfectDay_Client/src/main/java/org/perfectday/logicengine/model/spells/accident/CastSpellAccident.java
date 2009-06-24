/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.perfectday.logicengine.model.spells.accident;

import java.util.List;
import org.perfectday.logicengine.core.Game;
import org.perfectday.logicengine.core.event.action.spells.CastSpellEvent;
import org.perfectday.logicengine.core.event.manager.EventManager;
import org.perfectday.logicengine.core.event.manager.EventManagerThread;
import org.perfectday.logicengine.model.activationstack.accidents.Accident;
import org.perfectday.logicengine.model.command.Command;
import org.perfectday.logicengine.model.spells.InstanceCastSpell;
import org.perfectday.logicengine.model.spells.action.SpellCastAction;
import org.perfectday.logicengine.model.unittime.UnitTime;

/**
 *
 * @author Miguel Angel Lopez Montellano ( alakat@gmail.com )
 */
public class CastSpellAccident extends Accident {

    private InstanceCastSpell instanceCastSpell;
    private SpellCastAction spellCastAction;
    public CastSpellAccident(UnitTime mUnitTime, InstanceCastSpell instanceCastSpell,SpellCastAction spellCastAction) {
        super(mUnitTime);
        this.instanceCastSpell = instanceCastSpell;
        this.spellCastAction = spellCastAction;
    }

    /**
     * deprecated.
     * @param commands
     * @param game
     * @throws java.lang.Exception
     */
    @Override
    public void doAccident(List<Command> commands, Game game) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void doAccidentWithEvent(Game game) throws Exception {
        //El target es nulo ya que podría ser un Field.
        CastSpellEvent castEvent = new CastSpellEvent(
                instanceCastSpell.getCaster(),null, spellCastAction, instanceCastSpell);
        EventManager.getInstance().addEvent(castEvent);
        synchronized(EventManagerThread.getEventManagerThread()){
            EventManagerThread.getEventManagerThread().notifyAll();
        }
        
    }

    public InstanceCastSpell getInstanceCastSpell() {
        return instanceCastSpell;
    }

    public void setInstanceCastSpell(InstanceCastSpell instanceCastSpell) {
        this.instanceCastSpell = instanceCastSpell;
    }

    public SpellCastAction getSpellCastAction() {
        return spellCastAction;
    }

    public void setSpellCastAction(SpellCastAction spellCastAction) {
        this.spellCastAction = spellCastAction;
    }
    
    
 
    
}
