package org.perfectday.logicengine.model.activationstack.accidents;

import java.util.List;
import org.perfectday.logicengine.combat.InstanceCombat;
import org.perfectday.logicengine.combat.MasterOfCombatImpl;
import org.perfectday.logicengine.core.Game;
import org.perfectday.logicengine.core.event.accident.OffensiveActionEvent;
import org.perfectday.logicengine.core.event.manager.EventManager;
import org.perfectday.logicengine.core.event.manager.EventManagerRunnable;
import org.perfectday.logicengine.model.command.Command;
import org.perfectday.logicengine.model.command.combat.PreparedCombatCommand;
import org.perfectday.logicengine.model.minis.Mini;
import org.perfectday.logicengine.model.minis.action.combat.CombatActionMini;
import org.perfectday.logicengine.model.unittime.UnitTime;
// #[regen=yes,id=DCE.89249049-26B1-5735-38D0-EEA596ED17B6]
// </editor-fold> 
public class OffensiveAction extends Action {


    private InstanceCombat instanceCombat;

    public OffensiveAction() {
        super(null);
    }

    
    
    public OffensiveAction (UnitTime ut,InstanceCombat ic) {
        super(ut);
        this.instanceCombat = ic;
    }
    

  
    /**
     * Mini that do offensiveAction
     * @return
     */
    public Mini getAtacker(){
        return this.instanceCombat.getAtacker();
    }

    /**
     * Target Mini in this Offesive action
     * @return
     */
    public Mini getDefender(){
        return this.instanceCombat.getDefensor();
    }

    public InstanceCombat getInstanceCombat() {
        return instanceCombat;
    }

    public void setInstanceCombat(InstanceCombat instanceCombat) {
        this.instanceCombat = instanceCombat;
    }


    @Override
    public void doAccident(List<Command> commands, Game game) throws Exception {
        //Comprobamos que el combate aun es valido
        InstanceCombat instanceCombat = this.getInstanceCombat();
        //1º Atacante vivo
        if( instanceCombat.getAtacker()==null )
            return;
        if( instanceCombat.getDefensor()==null)
            return;
        if ( !instanceCombat.getAtacker().isAlive() )
            return; //Atacante muerto
        if ( !instanceCombat.getDefensor().isAlive())
            return;
        //2º El defensor está en alcance
        if ( instanceCombat.getAtack() instanceof CombatActionMini){
            CombatActionMini combatActionMini =
                    (CombatActionMini) instanceCombat.getAtack();
            if(!combatActionMini.isDefenederInRange(                            
                    Game.getGame().getBattelField().
                    getField(instanceCombat.getDefensor()),
                    instanceCombat.getAtackerField()))
                return;                            
        }
        //Un combate Atrasado
        commands.add(new PreparedCombatCommand(instanceCombat.getAtacker(),
                (CombatActionMini)instanceCombat.getAtack()));
        Game.getGame().getMasterOfCombat().putInstanceCombat(instanceCombat);
        game.resolvedCombat(commands);
        commands.addAll(game.searchDead()); //search mini dead in this turn
    }

    @Override
    public void doAccidentWithEvent(Game game) throws Exception {
        OffensiveActionEvent actionEvent = 
                new OffensiveActionEvent(this.getUnitTime(),this.getInstanceCombat());
        EventManager.getInstance().addEvent(actionEvent);
        synchronized(EventManagerRunnable.getEventManagerThread()){
            EventManagerRunnable.getEventManagerThread().notifyAll();
        }
    }

    @Override
    public String toString() {
        return "{OffensiveAction :"+this.getAtacker()+"}"+"{ut:"+this.getUnitTime()+"}";
    }
    
    
    
    
}

