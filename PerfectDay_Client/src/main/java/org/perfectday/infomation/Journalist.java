/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.perfectday.infomation;

import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.perfectday.logicengine.model.command.Command;
import org.perfectday.logicengine.model.command.move.MovementCommand;
import org.perfectday.main.laboratocGUI.LaboratoryGUI;
import org.perfectday.main.laboratocGUI.PerfectDayGUI;

/**
 * ppp
 * Encargado de informar al jugador de lo que esta ocurriendo en el sistema
 * @author Miguel Angel Lopez Montellano ( alakat@gmail.com )
 */
public class Journalist {
    
    private static Journalist instance;
    private PerfectDayGUI gui;
    private Journalist(){
        gui = LaboratoryGUI.me;    
    }

    public static Journalist getInstance(){
        if (instance==null)
            instance = new Journalist();
        return instance;
    }
    
    public void infoCombat (List<Command> commands){
            List<String> paper = new ArrayList<String>();
       int i = 0;
       for(Command command:commands){
           Logger.getLogger(Journalist.class).info(command.info());
           if (command instanceof MovementCommand) {
               continue;
           }            
           paper.add(i+":\t"+command.info());
           i++;
       }
       gui.showInfoCombat(paper);     
       
    }
}
