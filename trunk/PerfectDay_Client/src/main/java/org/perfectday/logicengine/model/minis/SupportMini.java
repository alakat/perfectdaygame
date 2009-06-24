/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.perfectday.logicengine.model.minis;

import org.perfectday.logicengine.model.minis.action.ActionMini;
import org.perfectday.logicengine.model.minis.action.combat.CombatActionMini;
import org.perfectday.logicengine.model.minis.support.Support;

/**
 * Abstracción de un mini en combate,es en este en el que se aplican mejoras.
 * @author Miguel Angel Lopez Montellano ( alakat@gmail.com )
 */
public class SupportMini extends Mini{

    public SupportMini(int atak,
            int defense, 
            int strength, 
            int resistance, 
            int vitality,
            double iniciative,
            double magicAfinity,
            int moviment,
            MiniLevel miniLevel,
            MiniType miniType,
            ActionMini primaryAction,
            ActionMini secondaryAction,
            Support support,
            boolean conterAtack,
            CombatActionMini aconterAtack) {
        super(atak, defense, strength, resistance, vitality, iniciative,
                magicAfinity, moviment, miniLevel, miniType, primaryAction,
                secondaryAction, support, conterAtack, aconterAtack,"name");
    }

    
}
