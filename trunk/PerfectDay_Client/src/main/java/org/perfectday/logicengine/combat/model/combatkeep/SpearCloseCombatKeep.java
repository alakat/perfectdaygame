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
public class SpearCloseCombatKeep extends CombatKeep{

    @Override
    public boolean isDefenderKeeped(Field defender, Field ataker) {
        return ((Math.abs((defender.getX()-ataker.getX()))<=2)||
                (Math.abs((defender.getY()-ataker.getY())))<=2);
    }
    
    @Override
    public List<Field> getFieldKeeped(Field ataker) {
        List<Field> accessFields = new ArrayList<Field>();
        BattelField bf = Game.getInstance().getBattelField();
        
        addFieldNotNull(accessFields,bf.getNorth(ataker));
        addFieldNotNull(accessFields,bf.getSourth(ataker));
        addFieldNotNull(accessFields,bf.getEast(ataker));
        addFieldNotNull(accessFields,bf.getWest(ataker));
        if(bf.getNorth(ataker)!=null)
            addFieldNotNull(accessFields,bf.getNorth(bf.getNorth(ataker)));
        if(bf.getSourth(ataker)!=null)
            addFieldNotNull(accessFields,bf.getSourth(bf.getSourth(ataker)));
        if(bf.getEast(ataker)!=null)
            addFieldNotNull(accessFields,bf.getEast(bf.getEast(ataker)));
        if(bf.getWest(ataker)!=null)
            addFieldNotNull(accessFields,bf.getWest(bf.getWest(ataker)));
        return accessFields;
    }

    private void addFieldNotNull(List list,Field f){
        if(f!=null){
            list.add(f);
        }
    }
}
