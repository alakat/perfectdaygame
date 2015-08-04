package org.perfectday.logicengine.model.activationstack.accidents;

import java.util.List;
import org.apache.log4j.Logger;
import org.perfectday.logicengine.brain.Turn;
import org.perfectday.logicengine.core.Game;
import org.perfectday.logicengine.core.event.accident.ActivationEvent;
import org.perfectday.logicengine.core.event.manager.EventManager;
import org.perfectday.logicengine.core.event.manager.EventManagerRunnable;
import org.perfectday.logicengine.core.player.Player;

import org.perfectday.logicengine.model.activationstack.accidents.factories.ActivationFactory;
import org.perfectday.logicengine.model.command.Command;
import org.perfectday.logicengine.model.unittime.UnitTime;
import org.perfectday.logicengine.model.minis.Mini;
import org.perfectday.logicengine.model.unittime.LongUnitTime;
import org.perfectday.logicengine.movement.MasterAPorEllos;

/**
 * Activation a mini in game
 * @author Miguel
 */
public class Activation extends Accident {

    private static final Logger logger = Logger.getLogger(Activation.class);
    /**
     * Mini that going to be activated
     */
    private Mini mini;

    public Activation() {
    }

    
    
    
    /**
     * 
     * @param mUnitTime
     * @param mini
     */
    public Activation(UnitTime mUnitTime, Mini mini) {
        super(mUnitTime);
        this.mini = mini;
    }

    public Mini getMini() {
        return mini;
    }

    public void setMini(Mini mini) {
        this.mini = mini;
    }

    @Override
    public String toString() {
        String cad="{";
        try{         
         cad+="Activar(Jugador:"+Game.getGame().getPlayerByMini(this.mini)+" mini:{"+this.mini.toString()+"}ut:{"+this.getUnitTime().toString()+"}";
         cad+="}";
        }catch(Exception ex){cad="Error en to String";}
         return cad;
    }

    @Override
    public void doAccident(List<Command> commands, Game game) throws InterruptedException, CloneNotSupportedException {
        Mini selectedMini = this.getMini();
        game.setSelectedMini(selectedMini);
        //Incluimos juego con I.A.
        Player player = game.getPlayerByMini(selectedMini);
        if(!player.isIa()){
            logger.info("Activado: "+game.getSelectedMini().toString());
            //remove actual support by selectedMini
            if(game.getSelectedMini().getSupport()!=null)
                game.getBattelField().removeSupport(game.getSelectedMini(),game.getSelectedMini().getSupport());
            game.setMasterAPorEllos(new MasterAPorEllos(game.getSelectedMini()));
            UnitTime costTime = game.proccessTurn(game.getSelectedMini(),commands);
            game.resolvedCombat(commands);                
            commands.addAll(game.searchDead()); //search mini dead in this turn
            if(game.getSelectedMini()!=null && game.getSelectedMini().isAlive()){
                UnitTime newActualTime = (UnitTime) game.getActualTime().clone();
                newActualTime.plus(costTime);
                Activation activation = ActivationFactory.getInstance().
                        createActivation(game.getSelectedMini(), newActualTime);
                game.getActivationStack().put(activation);
            }
            if(game.getSelectedMini().getSupport()!=null )
                game.getBattelField().applySupport(game.getSelectedMini(),game.getSelectedMini().getSupport());
        }else{
            logger.info("Juega la maquina");
            //Juega la maquina.
            logger.info("Activado: "+game.getSelectedMini().toString());
            //remove actual support by selectedMini
            if(game.getSelectedMini().getSupport()!=null)
                game.getBattelField().removeSupport(game.getSelectedMini(),game.getSelectedMini().getSupport());
            game.setMasterAPorEllos(new MasterAPorEllos(selectedMini));
            //Juega la maquina
            Turn turn = player.getBrain().getTrun(selectedMini, new LongUnitTime(0l));
            commands.addAll(turn.getAcciones());
            game.resolvedCombat(commands);                
            commands.addAll(game.searchDead()); //search mini dead in this turn
            if(game.getSelectedMini()!=null && game.getSelectedMini().isAlive()){
                UnitTime newActualTime = (UnitTime) game.getActualTime().clone();
                newActualTime.plus(turn.getUt());
                Activation activation = ActivationFactory.getInstance().
                        createActivation(game.getSelectedMini(), newActualTime);
                game.getActivationStack().put(activation);
            }
            if(game.getSelectedMini().getSupport()!=null )
                game.getBattelField().applySupport(game.getSelectedMini(),game.getSelectedMini().getSupport());
                       
            //Esperamos un segundo para que lo vea el usuarios
            synchronized(this){
                this.wait(1000);
            }
        }
    }

    @Override
    public void doAccidentWithEvent(Game game) throws Exception {
        ActivationEvent activationEvent = new ActivationEvent(
                this.getUnitTime(),this.getMini());
        EventManager.getInstance().addEvent(activationEvent);
        synchronized(EventManagerRunnable.getEventManagerThread()){
            EventManagerRunnable.getEventManagerThread().notifyAll();
        }
    }
    
    
}

