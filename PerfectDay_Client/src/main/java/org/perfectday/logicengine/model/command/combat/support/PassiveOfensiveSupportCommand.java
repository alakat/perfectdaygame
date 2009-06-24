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
public class PassiveOfensiveSupportCommand extends SupportCombat {

    public PassiveOfensiveSupportCommand(Mini mini, Support support) {
        super(mini, support);
    }

}
