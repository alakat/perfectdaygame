package org.perfectday.logicengine.model.activationstack.accidents;

import org.perfectday.logicengine.model.unittime.UnitTime;


// <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
// #[regen=yes,id=DCE.4044180D-1D32-DDCF-0C7A-E662CCEC33F1]
// </editor-fold> 
public class DefensiveAction extends Action {

    public DefensiveAction() {
        super(null);
    }

    
    
    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.34E8D9D2-D5BA-0BB4-FEEE-FCB3B43FD34E]
    // </editor-fold> 
    public DefensiveAction (UnitTime ut) {
        super(ut);
    }

    @Override
    public String toString() {
        return "{DefensiveAction }"+"{ut:"+this.getUnitTime()+"}";
    }
    
    

}

