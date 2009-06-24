/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.perfectday.logicengine.brain;

import java.util.ArrayList;
import java.util.List;
import org.perfectday.logicengine.model.activationstack.accidents.Accident;
import org.perfectday.logicengine.model.command.Command;
import org.perfectday.logicengine.model.unittime.UnitTime;

/**
 * Abstracción de un turno realizado por la computadora.
 * @author Miguel Angel Lopez Montellano ( alakat@gmail.com )
 */
public class Turn {

    private List<Command> acciones;
    private List<Accident> accidents;
    private UnitTime ut;
    public Turn() {
        acciones = new ArrayList();
    }

    public List<Command> getAcciones() {
        return acciones;
    }

    public void setAcciones(List<Command> acciones) {
        this.acciones = acciones;
    }

    public List<Accident> getAccidents() {
        return accidents;
    }

    public void setAccidents(List<Accident> accidents) {
        this.accidents = accidents;
    }

    public UnitTime getUt() {
        return ut;
    }

    public void setUt(UnitTime ut) {
        this.ut = ut;
    }
    
    
    
    
}
