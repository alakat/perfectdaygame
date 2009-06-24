/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.perfectday.logicengine.model.state.commands;

import org.perfectday.logicengine.model.command.Command;

/**
 *
 * @author Miguel Angel Lopez Montellano (alakat@gmail.com)
 */
public class NoPowerMagicToCast extends Command {

    @Override
    public String info() {
        return "No pudo lanzar el conjuro le faltab fuerza magica";
    }

}
