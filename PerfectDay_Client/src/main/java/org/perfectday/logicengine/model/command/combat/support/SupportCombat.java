/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.perfectday.logicengine.model.command.combat.support;

import org.perfectday.logicengine.model.command.Command;
import org.perfectday.logicengine.model.minis.Mini;
import org.perfectday.logicengine.model.minis.support.Support;

/**
 *
 * @author Miguel Angel Lopez Montellano ( alakat@gmail.com )
 */
public class SupportCombat extends Command {

    private String msg;

    public SupportCombat() {
    }

    public SupportCombat(Mini mini, Support support) {

        this.msg = "" + mini.getName() + " with " + support.toString()
                + " support in combat " + support.infoSupport();
    }

    @Override
    public String info() {
        return msg;
    }

}
