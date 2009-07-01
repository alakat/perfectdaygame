/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.perfectday.threads.commands.kernell;

import org.perfectday.logicengine.core.event.game.EndTurnEvent;
import org.perfectday.logicengine.core.event.manager.EventManager;
import org.perfectday.logicengine.model.minis.Mini;
import org.perfectday.logicengine.model.minis.action.ActionMini;
import org.perfectday.logicengine.model.unittime.UnitTime;
import org.perfectday.threads.Command;

/**
 *  Este comando realiza la acción de poner una acción a ejecutar en el sistema
 *  es usado para todo tipo de acciones y ataques.
 *
 * @author Miguel Angel Lopez Montellano (alakat@gmail.com)
 */
public class DoActionCommand extends Command {

    /**
     * Acción a ejecutar
     */
    private ActionMini actionMini;
    /**
     * Mini objectivo de la acción
     * TODO: Puede ser cambiada por ReferenceObject
     */
    private Mini targetMini;
    /**
     * Mini que ejecuta la acción
     */
    private Mini actionWorker;

    /**
     * Tiempo de coste de ejecución, tiene sumado el movimiento si lo hubo
     */
    private UnitTime unitTime;

    /**
     * Constructor
     * @param actionMini
     * @param targetMini
     * @param actionWorker
     * @param unitTime
     */
    public DoActionCommand(ActionMini actionMini, Mini targetMini, Mini actionWorker, UnitTime unitTime) {
        this.actionMini = actionMini;
        this.targetMini = targetMini;
        this.actionWorker = actionWorker;
        this.unitTime = unitTime;
    }
    
    @Override
    public void trueRun() throws Exception {
        actionMini.doAction(actionWorker, targetMini);
        EndTurnEvent endTurnEvent = new EndTurnEvent(unitTime, actionWorker);
        EventManager.getInstance().addEvent(endTurnEvent);
        EventManager.getInstance().eventWaitTest();
    }
}
