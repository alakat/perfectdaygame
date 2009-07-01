/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.perfectday.logicengine.combat.model.combatkeep;

import java.util.ArrayList;
import java.util.List;
import org.perfectday.logicengine.core.Game;
import org.perfectday.logicengine.model.battelfield.BattelField;
import org.perfectday.logicengine.model.battelfield.Field;

/**
 *
 * @author Miguel Angel Lopez Montellano ( alakat@gmail.com )
 */
public class CloseCombatKeep extends CombatKeep{

    @Override
    public boolean isDefenderKeeped(Field defender, Field ataker,Game game) {
        return (((Math.abs((defender.getX()-ataker.getX()))==1)&&
                    (Math.abs((defender.getY()-ataker.getY())))==0))    ||
                (((Math.abs((defender.getY()-ataker.getY())))==1)&&
                (Math.abs((defender.getX()-ataker.getX()))==0));
    }

    @Override
    public List<Field> getFieldKeeped(Field ataker,Game game) {
        List<Field> accessFields = new ArrayList<Field>();
        BattelField bf = game.getBattelField();
        if(bf.getNorth(ataker)!=null)
            accessFields.add(bf.getNorth(ataker));
        if(bf.getSourth(ataker)!=null)
            accessFields.add(bf.getSourth(ataker));
        if(bf.getEast(ataker)!=null)
            accessFields.add(bf.getEast(ataker));
        if(bf.getWest(ataker)!=null)
            accessFields.add(bf.getWest(ataker));
        return accessFields;
    }

}
