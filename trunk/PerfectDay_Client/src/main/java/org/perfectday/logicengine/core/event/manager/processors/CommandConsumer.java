/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.perfectday.logicengine.core.event.manager.processors;

import java.util.List;
import org.apache.log4j.Logger;
import org.perfectday.logicengine.core.Game;
import org.perfectday.logicengine.model.command.Command;
import org.perfectday.logicengine.model.command.combat.CombatResolutionCommand;
import org.perfectday.logicengine.model.command.combat.SetDamageCommand;
import org.perfectday.logicengine.model.minis.Mini;

/**
 * Esta clase contiene los métodos para actualizar el cliente en función de los
 * comandos ofrecidos por el servidor
 * @author Miguel Angel Lopez Montellano (alakat@gmail.com)
 */
public class CommandConsumer {

    private static final CommandConsumer instance = new CommandConsumer();
    private static final Logger logger =
            Logger.getLogger(CommandConsumer.class);

    public static CommandConsumer getInstance() {
        return instance;
    }
    
    
     public static void process(List<Command> commands) {
        logger.info("Procesador de commandos estatica");
        getInstance().process_(commands);
       
     }

     /**
      * Procesa un commando de tipo resultado de combate
      * @param combatResolutionCommand
      */
    private void process(CombatResolutionCommand combatResolutionCommand) {
        logger.info("Comando de resolución de combate");
        Mini defensor = Game.getGame().getMiniByReferneceObject(combatResolutionCommand.getDefensor());
        if(combatResolutionCommand.isDead()){
            logger.info("Mini muerto");
            //Lo eliminamos del campo de batalla
            Game.getGame().getBattelField().getField(defensor).setMiniOcupant(null);
            logger.info("Eliminado del battelfield");
            //Lo eliminamos del equipo del player
            Game.getGame().getPlayerByMini(defensor).getBand().remove(defensor);
            logger.info("Eliminado del ejercito del jugador");
            //Eliminamos todos las activaciones
            Game.getGame().getActivationStack().deadClear(defensor);
            logger.info("Eliminamos las activaciones del mini");
        }else{
            logger.info("Se modifica los daños");
            logger.info("Daño antes del combate: "+defensor.getDamage());
            logger.info("Daño del combate:"+combatResolutionCommand.getDamage());
            defensor.setDamage(defensor.getDamage()+combatResolutionCommand.getDamage());
            logger.info("Daño despues del combate"+defensor.getDamage());
        }
    }

    /**
     * Procesa el comando de asignación de daño.
     * @param setDamageCommand
     */
    private void process(SetDamageCommand setDamageCommand) {
        logger.debug("Se modifica el daño del mini.");
        Mini mini = Game.getGame().getMiniByReferneceObject(setDamageCommand.getMini());
        mini.setDamage(setDamageCommand.getDamage());
    }
     
     private void process_(List<Command> commands){
        logger.info("process commands");
        for (Command command : commands) {
            if (command instanceof CombatResolutionCommand){
                process((CombatResolutionCommand)command);
            }else if(command instanceof SetDamageCommand){
                process((SetDamageCommand)command);
            }
        }
     }
     
    
}
