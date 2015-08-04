/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.perfectday.logicengine.model.state;

import java.util.List;
import org.perfectday.logicengine.core.Game;
import org.perfectday.logicengine.core.event.accident.PutAccidentEvent;
import org.perfectday.logicengine.core.event.manager.EventManager;
import org.perfectday.logicengine.model.command.Command;
import org.perfectday.logicengine.model.command.combat.SetDamageCommand;
import org.perfectday.logicengine.model.minis.Mini;
import org.perfectday.logicengine.model.state.accident.StateActivationAccident;
import org.perfectday.logicengine.model.unittime.LongUnitTime;
import org.perfectday.logicengine.model.unittime.UnitTime;

/**
 *
 * @author Miguel Angel Lopez Montellano ( alakat@gmail.com )
 */
public class PoisonedState extends ActiveState{

    private static final UnitTime POISONED_STATE_CLEAR_TIME = new LongUnitTime(30l);
    private static final UnitTime POISONED_STATE_DELAY_TIME = new LongUnitTime(10l);
        
    
    public PoisonedState( Mini mini) {
        super("Poisond", mini,POISONED_STATE_CLEAR_TIME,POISONED_STATE_DELAY_TIME);
    }

    public PoisonedState() {
        super("Poisond", POISONED_STATE_CLEAR_TIME,POISONED_STATE_DELAY_TIME);
    }
    

    /**
     * Diminuye la vida del affectado en un 10%
     * @param data
     * @param commands
     */
    @Override
    public Object doState(Object data, List<Command> commands) {
        double v = this.getMini().getVitality();
        v = v*0.1; //10% de la vitalidad
        this.getMini().setDamage(this.getMini().getDamage()+v); 
        UnitTime t = Game.getGame().getActualTime();
        t.plus(POISONED_STATE_DELAY_TIME);
        PutAccidentEvent accident = new PutAccidentEvent();
        accident.setAccident(new StateActivationAccident(this, t));
        EventManager.getInstance().addEvent(accident);
        EventManager.getInstance().eventWaitTest();
        commands.add(new SetDamageCommand(this.getMini().getReferenceObject(), this.getMini().getDamage()));
        return null;
    }

    @Override
    public Object generateDataEffect() {
        return null;
    }

    @Override
    public String generateStringEffect() {
        return " EL veneno resta 10% de la vida";
    }
    
    
        
}
