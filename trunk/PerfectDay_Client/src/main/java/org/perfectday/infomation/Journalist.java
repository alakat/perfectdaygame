/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.perfectday.infomation;

import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.perfectday.logicengine.core.Game;
import org.perfectday.logicengine.model.command.Command;
import org.perfectday.logicengine.model.command.move.MovementCommand;
import org.perfectday.main.dummyengine.DummyGraphicsEngine;
import org.perfectday.main.dummyengine.GraphicsEngine;

/**
 * ppp
 * Encargado de informar al jugador de lo que esta ocurriendo en el sistema
 * @author Miguel Angel Lopez Montellano ( alakat@gmail.com )
 */
public class Journalist {
    
    private static Journalist instance;
    private GraphicsEngine gui;
    private Journalist(){
        gui = Game.getPerfectDayGUI();
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
